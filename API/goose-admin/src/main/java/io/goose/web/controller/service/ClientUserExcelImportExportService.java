package io.goose.web.controller.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import io.goose.common.config.Global;
import io.goose.common.utils.StringUtils;
import io.goose.framework.util.FileUploadUtils;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.domain.Role;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.IMyClubService;
import io.goose.shooting.service.IRoleService;


public class ClientUserExcelImportExportService extends AExcelImportExportService {

    private static final Logger log =
            LoggerFactory.getLogger(ClientUserExcelImportExportService.class);

    private IClientUserService clientUserService;
    private IMyClubService myClubService;
    private IClubService clubService;
    private IRoleService roleService;
    // private IUserRoleService userRoleService;
    private Global global;

    final String[] USER_ROLES = {"实弹射手", "气枪射手", "教官", "国家裁判", "国际裁判", "会长"};
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ;


    public void setGlobal(Global global) {
        this.global = global;
    }


    public void setClientUserService(IClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }


    public void setMyClubService(IMyClubService myClubService) {
        this.myClubService = myClubService;
    }


    public void setClubService(IClubService clubService) {
        this.clubService = clubService;
    }


    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    // public void setUserRoleService(IUserRoleService userRoleService) {
    // this.userRoleService = userRoleService;
    // }


    @Override
    protected boolean handleSpecialCells(String fieldName, Cell cell,
                                         Map<String, String> nameValue) {

        if ("结业日期".equals(fieldName)) {

            try {

                Date date = cell.getDateCellValue();
                String stringValue = formateDate(date);

                nameValue.put(fieldName, stringValue + " 00:00:00");
                // }
            } catch (Exception e) {
                // if any error occurs, ignore the value
            }

            return true;
        }

        return false;
    }


    private String formateDate(Date date) {
        return simpleDateFormat.format(date);
    }


    @Override
    protected Map<String, String>
    transferNameValueToDoaminNameValue(Map<String, String> nameValue) {

        if (nameValue == null) {
            return null;
        }

        Map<String, String> domainNameValue = new HashMap<>();

        AtomicReference<String> userType = new AtomicReference<>("");

        nameValue.forEach((key, value) -> {
            if ("所属俱乐部".equals(key)) {
                domainNameValue.put("address", value);
                return;
            }
            if ("姓名".equals(key)) {
                domainNameValue.put("realName", value);
                return;
            }
            if ("英文名".equals(key)) {
                domainNameValue.put("englishName", value);
                return;
            }
            if ("性别".equals(key)) {
                if ("M".equals(value))
                    domainNameValue.put("sex", "1");
                else if ("F".equals(value))
                    domainNameValue.put("sex", "2");
                else
                    domainNameValue.put("sex", "0");
                return;
            }
            if ("射手卡编号".equals(key)) {
                domainNameValue.put("memberId", value);
                return;
            }
            if ("结业日期".equals(key)) {
                domainNameValue.put("graduateDate", value);

                return;
            }
            if ("身份证号码".equals(key)) {
                domainNameValue.put("idNumber", value);
                return;
            }
            if ("护照号码".equals(key)) {
                domainNameValue.put("passportNo", value);
                return;
            }
            if ("手机号".equals(key)) {
                domainNameValue.put("phone", value);
                return;
            }
            if ("照片编号".equals(key)) {
                domainNameValue.put("selfieImage", value);
                return;
            }
            if ("邮箱".equals(key)) {
                domainNameValue.put("email", value);
                return;
            }
            if ("密码".equals(key)) {
                domainNameValue.put("password", value);
                return;
            }

            Arrays.asList(USER_ROLES).forEach((userRole) -> {
                if (userRole.equals(key) && "是".equals(value)) {
                    if (userRole.equals("国家裁判"))
                        userType.set(userType.get() + "国内裁判,");
                    else
                        userType.set(userType.get() + userRole + ",");
                    return;
                }
            });
        });

        domainNameValue.put("userType", userType.get());

        return domainNameValue;
    }


    @Override
    protected boolean saveDomainObject(Object objectLoadFromExcel) throws Exception {

        ClientUser excelClientUser = (ClientUser) objectLoadFromExcel;

        String phone = excelClientUser.getPhone();
        if (StringUtils.isBlank(phone)) {
            return false;
        }

        if (StringUtils.isNotBlank(excelClientUser.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String rawPassword = excelClientUser.getPassword();
            excelClientUser.setPassword(encoder.encode(rawPassword));
        }

        if(excelClientUser.getGraduateDate() != null) {
            adjustGraduateDate(excelClientUser);
        }
        // uploadSelfieImage( excelClientUser );

        // we just use these two fields to save clubTitle and userRoles, so we need to set them as
        // null after get their values; otherwise the
        // user's actual address and userType would be overridden.
        String clubTitle = excelClientUser.getAddress();
        String userRoles = excelClientUser.getUserType();

        excelClientUser.setAddress(null);
        excelClientUser.setUserType(null);

        String roleIds = getRoleIds(userRoles);
        excelClientUser.setRoleIds(roleIds);
        excelClientUser.setStatus(StringUtils.isEmpty(roleIds) ? 0 : 2);

        ClientUser clientUser = clientUserService.selectClientUserByPhone(phone);

        AtomicLong clientUserId = new AtomicLong();

        // add new user
        if (clientUser == null) {

            excelClientUser.setNickname(excelClientUser.getRealName());
            excelClientUser.setUserName(phone);
            excelClientUser.setEnglishName(excelClientUser.getEnglishName() != null ? excelClientUser.getEnglishName() : "");
            excelClientUser.setJgUsername(phone);

            int no = clientUserService.insertClientUser(excelClientUser);

            if (no > 0) {
                clientUserId.set(
                        clientUserService.selectClientUserList(excelClientUser).get(0).getId());
                registerUserToJg(phone, excelClientUser.getRealName());
            }

        }
        // update existing user
        else {
            clientUserId.set(clientUser.getId());
            excelClientUser.setId(clientUserId.get());
            excelClientUser.setJgUsername(clientUser.getJgUsername());
            clientUserService.updateClientUser(excelClientUser);
        }

        // saveUserRoles( userRoles, clientUserId );
        setMyClub(clubTitle, clientUserId);

        return true;
    }


    /**
     * 添加用户到极光
     */
    private void registerUserToJg(String phone, String nickName) {
        try {
            // TBD - should move the constants to config file
            final String APP_KEY = "d27e90395d0fc5adac3b3c65";
            final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

            // 极光API
            final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

            RegisterInfo registerInfo = RegisterInfo.newBuilder().setUsername(phone)
                    .setPassword(phone).setNickname(nickName != null ? nickName : phone).addExtra("phone", phone).build();

            JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
            log.debug("***【极光】*** 添加用户到极光成功: " + registerInfo);
        } catch (APIConnectionException e) {
            log.error("***【极光】*** 添加用户到极光失败." + phone);
            e.printStackTrace();
        } catch (APIRequestException e) {
            log.error("***【极光】*** 添加用户到极光失败." + phone);
            log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                    + e.getErrorMessage() + " Status: " + e.getStatus());
            e.printStackTrace();
        }
    }


    private void adjustGraduateDate(ClientUser excelClientUser) throws ParseException {

        Date graduateDate = excelClientUser.getGraduateDate();

        Calendar cal = Calendar.getInstance();
        cal.setTime(simpleDateFormat.parse(formateDate(graduateDate)));
        //      cal.add( Calendar.DAY_OF_MONTH, 1 );
        graduateDate = cal.getTime();

        excelClientUser.setGraduateDate(graduateDate);
    }


    private void uploadSelfieImage(ClientUser excelClientUser)
            throws FileNotFoundException, IOException {

        String imageNo = excelClientUser.getSelfieImage();

        String fileName = global.getMediaPathImport() + imageNo + ".jpg";
        File file = new File(fileName);

        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

        String filePath =
                FileUploadUtils.uploadMediaFile(global.getMediaPathClientUser(), multipartFile);

        excelClientUser.setSelfieImage(filePath);
    }


    private void setMyClub(String clubTitle, AtomicLong clientUserId) {

        if (StringUtils.isBlank(clubTitle)) {
            return;
        }

        String[] clubTitles = clubTitle.split("、");

        Arrays.asList(clubTitles).forEach(title -> {

            Club club = new Club();
            club.setTitle(title);
            List<Club> clubList = clubService.selectClubList(club);

            if (!clubList.isEmpty()) {
                Long clubId = clubList.get(0).getId();

                MyClub myClub = new MyClub();
                myClub.setClientUserId(clientUserId.get());
                myClub.setClubId(clubId);

                if (myClubService.selectMyClubList(myClub).isEmpty()) {
                    myClubService.insertMyClub(myClub);
                }
            }
        });

    }


    private String getRoleIds(String userRoles) {

        if (StringUtils.isBlank(userRoles)) {
            return "";
        }

        String roleIds = "";

        String[] roleDescs = userRoles.split(",");

        for (String roleDesc : roleDescs) {

            Role role = new Role();
            role.setDescription(roleDesc);
            List<Role> roleList = roleService.selectRoleList(role);

            if (roleList != null && !roleList.isEmpty()) {
                Integer roleId = roleList.get(0).getId();

                roleIds += roleId + ",";
            }
        }

        if (StringUtils.isEmpty(roleIds)) {
            return "";
        }

        // remove the last ","
        return roleIds.substring(0, roleIds.length() - 1);
    }

    // private void saveUserRoles(String userRoles, AtomicLong clientUserId) {
    //
    // if (StringUtils.isBlank(userRoles)) {
    // return;
    // }
    //
    // String[] roleNames = userRoles.split(",");
    //
    // Arrays.asList(roleNames).forEach(roleName -> {
    // Role role = new Role();
    // role.setName(roleName);
    // List<Role> roleList = roleService.selectRoleList(role);
    //
    // if (!roleList.isEmpty()) {
    // Integer roleId = roleList.get(0).getId();
    //
    // UserRole userRole = new UserRole();
    // userRole.setUserId(clientUserId.get());
    // userRole.setRoleId(roleId);
    //
    // if (userRoleService.selectUserRoleList(userRole).isEmpty()) {
    // userRoleService.insertUserRole(userRole);
    // }
    // }
    // });
    // }

}
