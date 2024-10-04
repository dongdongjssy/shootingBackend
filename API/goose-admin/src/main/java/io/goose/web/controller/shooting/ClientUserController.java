package io.goose.web.controller.shooting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.goose.common.enums.ClientUserStatus;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.shooting.domain.Role;
import io.goose.web.controller.common.MobileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.UserPayload;
import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.constant.Constants;
import io.goose.common.enums.BusinessType;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Message;
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.IMessageService;
import io.goose.shooting.service.IMyClubService;
import io.goose.shooting.service.IRoleService;
import io.goose.system.service.ISysDictDataService;
import io.goose.web.controller.service.ClientUserExcelImportExportService;
import io.goose.web.controller.service.JpushService;


/**
 * 客户端用户 信息操作处理
 *
 * @author goose
 * @date 2020-04-30
 */
@Controller
@RequestMapping("/client/clientUser")
public class ClientUserController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ClientUserController.class);

    // TBD - should move the constants to config file
    private static final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private static final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光API
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    @Autowired
    private Global global;

    private String prefix = "shooting/clientUser";

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMyClubService myClubService;
    @Autowired
    private IClubService clubService;
    @Autowired
    private IRoleService roleService;
    // @Autowired
    // private IUserRoleService userRoleService;

    @Autowired
    private JpushService pushService;
    
    @Autowired
    private ISysDictDataService dictDataService;
    
    @Autowired
    private FileUploadDownloadOSSService fileUploadService;


    @RequiresPermissions("client:clientUser:view")
    @GetMapping()
    public String clientUser(ModelMap mmap) {
        mmap.put("roleIdList", roleService.selectRoleAllFullDisplay());
        return prefix + "/clientUser";
    }


    @RequiresPermissions("client:clientUserDateRemind:view")
    @GetMapping("/dateRemind")
    public String clientUserDate(ModelMap mmap) {
        mmap.put("roleIdList", roleService.selectRoleAllFullDisplay());
        return prefix + "/clientUserDate";
    }


    /**
     * 查询客户端临期用户列表
     */
    @RequiresPermissions("client:clientUserDateRemind:list")
    @PostMapping("/remindList")
    @ResponseBody
    public TableDataInfo remindList(ClientUser clientUser) {
        startPage();
        List<ClientUser> list = clientUserService.selectClientUserListRemind(clientUser);
        return getDataTable(list);
    }


    /**
     * 查询客户端用户列表
     */
    @RequiresPermissions("client:clientUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClientUser clientUser) {
        startPage();
        // 推送
        pushService.jpush("dj", "dj麦克美",
                 "3050", "6", "0", "");
        List<ClientUser> list = clientUserService.selectClientUserListAssoc(clientUser);
        return getDataTable(list);
    }


    /**
     * 导出客户端用户列表
     */
    @RequiresPermissions("client:clientUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClientUser clientUser) {
        List<ClientUser> list = clientUserService.selectClientUserList(clientUser);
        for(ClientUser user : list) {
        	user.setStatusName(dictDataService.selectDictLabel("client_user_status", user.getStatus().toString()));
        	if(user.getRoleIds() != null) {
            	Role role = new Role();
            	role.setIds(user.getRoleIds());
        		user.setRoleNames(roleService.selectRoleListAssoc(role).stream().map(r -> r.getName()).collect(Collectors.joining(",")));
        	}
        	MyClub myClub = new MyClub();
        	myClub.setClientUserId(user.getId());
        	user.setMyClubNames(myClubService.selectMyClubList(myClub).stream().map(mb -> mb.getClub().getTitle()).collect(Collectors.joining(",")));
            if(MobileUtils.isMobileNO(user.getPhone())){
                user.setUserCardStatus("普通用户");
            }else{
                user.setUserCardStatus("境外用户");
            }
      
        }
        ExcelUtil<ClientUser> util = new ExcelUtil<ClientUser>(ClientUser.class);
        return util.exportExcel(list, "clientUser");
    }


    /**
     * 导入客户端用户列表
     */
    @RequiresPermissions("client:clientUser:importExcel")
    @PostMapping("/importExcel")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excelFile) {

        Instant start = Instant.now();

        ClientUserExcelImportExportService imporExportService =
                new ClientUserExcelImportExportService();
        imporExportService.setClientUserService(clientUserService);
        imporExportService.setClubService(clubService);
        imporExportService.setMyClubService(myClubService);
        imporExportService.setRoleService(roleService);
        // imporExportService.setUserRoleService( userRoleService );
        imporExportService.setGlobal(global);

        AjaxResult result = imporExportService.importExcel(excelFile, ClientUser.class);

        Instant finish = Instant.now();
        String timeElapsed = Duration.between(start, finish).toString();
        log.info("导入总共用时：" + timeElapsed);

        return result;

    }


    /**
     * 新增客户端用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("clubList", clubService.selectClubAll());

        mmap.put("roleIdList", roleService.selectRoleAllFullDisplay());
        return prefix + "/add";
    }


    /**
     * 新增保存客户端用户
     */
    @RequiresPermissions("client:clientUser:add")
    @Log(title = "客户端用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ClientUser clientUser,
                              @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                              @RequestParam(value = "selfieImageFile", required = false) MultipartFile selfieImage) {

        if (StringUtils.isBlank(clientUser.getPhone()) || clientUser.getPhone().length() < 4) {
            return error("手机号为空或格式错误");
        }

        if (StringUtils.isBlank(clientUser.getUserName())) {
            clientUser.setUserName(clientUser.getPhone());
        } else if (clientUser.getUserName().length() < 4) {
            return error("用户名必须4-128个字符");
        }

        if (StringUtils.isBlank(clientUser.getNickname())) {
            clientUser.setNickname(clientUser.getUserName());
        }

        if (StringUtils.isBlank(clientUser.getMemberId())) {
            return error("请填写射手编号");
        }

        if (StringUtils.isBlank(clientUser.getRoleIds()) || "null".equals(clientUser.getRoleIds())) {
            return error("请选择角色");
        }

        try {
            String filePath;
        	if(avatarFile != null) {
                filePath = fileUploadService.upload(avatarFile,null,UploadTypeEnums.ClientUser.getValue(),true);
                clientUser.setAvatar(filePath);
        	}
        	if(selfieImage != null) {
        		  filePath = fileUploadService.upload(selfieImage,null,UploadTypeEnums.ClientUser.getValue(),true);
                  clientUser.setSelfieImage(filePath);
        	}
        } catch (
                Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        clientUser.setCreateBy(ShiroUtils.getLoginName());

        if (clientUser.getJgUsername() == null) {
            clientUser.setJgUsername(clientUser.getPhone());
        }

        int success = clientUserService.insertClientUser(clientUser);
        if (success > 0) {
            String[] myClubIds = clientUser.getMyClubIds().split(",");
            for (String myClubId : myClubIds) {
                MyClub myClub = new MyClub();
                myClub.setClientUserId(clientUser.getId());
                try {
                    myClub.setClubId(Long.valueOf(myClubId));
                } catch (Exception e) {
                    continue;
                }
                myClubService.insertMyClub(myClub);
            }
        }



        if (clientUser.getId() != null) {
            try {
                JMSG_CLIENT.deleteUser(clientUser.getJgUsername().trim());
                log.debug("***【极光】*** 删除极光用户成功:" + clientUser.getJgUsername().trim());
            } catch (APIConnectionException e) {
                log.error("***【极光】*** 删除用户失败." + clientUser.getJgUsername().trim());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光】*** 删除用户失败." + clientUser.getJgUsername().trim());
                log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                        + e.getErrorMessage() + " Status: " + e.getStatus());
                e.printStackTrace();
            }


            // 添加用户到极光
            try {
                RegisterInfo registerInfo = RegisterInfo.newBuilder()
                        .setUsername(clientUser.getJgUsername())
                        .setPassword(clientUser.getJgUsername()).setNickname(clientUser.getNickname())
                        .setAddress(clientUser.getAddress() != null ? clientUser.getAddress() : "")
                        .setAvatar(clientUser.getAvatar() != null ? clientUser.getAvatar() : "")
                        .addExtra("phone", clientUser.getPhone()).addExtra("avatar",
                                clientUser.getAvatar() != null ? clientUser.getAvatar() : "")
                        .build();

                JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
                log.debug("***【极光】*** 添加用户到极光成功: " + registerInfo);
            } catch (APIConnectionException e) {
                clientUserService.deleteClientUserById(clientUser.getId());
                log.error("***【极光】*** 添加用户到极光失败." + clientUser.getJgUsername());
                e.printStackTrace();
            } catch (APIRequestException e) {
            	
            	JSONArray jsonArray= JSONArray.parseArray(e.getMessage());
            	JSONObject jsonObject =  (JSONObject)jsonArray.get(0);
            	Map<String,Object> mapError = (Map<String, Object>) jsonObject.get("error");
            	if(!mapError.get("code").toString().equals("899001")) {
                     clientUserService.deleteClientUserById(clientUser.getId());
                     log.error("***【极光】*** 添加用户到极光失败." + clientUser.getJgUsername());
                     log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                             + e.getErrorMessage() + " Status: " + e.getStatus());
                     e.printStackTrace();
            	}
            }

            return AjaxResult.success("成功添加用户：" + clientUser.getJgUsername());
        } else {
            return AjaxResult.error("添加用户失败！");
        }
    }


    @GetMapping("/edit/editDate")
    public String editDate(ModelMap mmap) {
        ClientUser user=new ClientUser();
        user.setStatus(2);
        List<ClientUser> clientUser = clientUserService.selectClientUserList(user);
        mmap.put("clientUser", clientUser);
        return prefix + "/editDate";
    }


    @RequiresPermissions("client:clientUser:edit")
    @Log(title = "批量会员时间修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editDate")
    @ResponseBody
    public AjaxResult editDate(String certExpireDate) throws ParseException {
            ClientUser clientUser=new ClientUser();
            clientUser.setCertExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse(certExpireDate));
            clientUserService.updateClientUserList(clientUser);

        return AjaxResult.success("批量会员时间修改成功！");
    }




    /**
     * 修改客户端用户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ClientUser clientUser = clientUserService.selectClientUserByIdAssoc(id);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        mmap.put("roleIdList", roleService.selectRoleAllFullDisplay());
        MyClub myClub = new MyClub();
        myClub.setClientUserId(id);
        List<MyClub> myClubList = myClubService.selectMyClubList(myClub);
        String myClubIds = null;
        for (int i = 0; i < myClubList.size(); i++) {
            if (i == myClubList.size() - 1) {
                myClubIds = myClubIds + "," + myClubList.get(i).getClubId().toString();
            } else if (i == 0) {
                myClubIds = myClubList.get(i).getClubId().toString();
            } else {
                myClubIds = myClubIds + "," + myClubList.get(i).getClubId().toString() + ",";
            }
        }
        clientUser.setMyClubIds(myClubIds);
        mmap.put("clientUser", clientUser);

        mmap.put("myClubList", clubService.selectClubAll());
        return prefix + "/edit";
    }


    /**
     * 修改保存客户端用户
     */
    @RequiresPermissions("client:clientUser:edit")
    @Log(title = "客户端用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ClientUser clientUser,
                               @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                               @RequestParam(value = "selfieImageFile", required = false) MultipartFile selfieImage) {
        if (StringUtils.isBlank(clientUser.getPhone()) || clientUser.getPhone().length() < 4) {
            return error("手机号为空或格式错误");
        }

        if (clientUser.getUserName() == null || clientUser.getUserName().length() < 4) {
            return error("用户名必须4-128个字符");
        }

        if (StringUtils.isBlank(clientUser.getMemberId())) {
            return error("请填写射手编号");
        }

        if (StringUtils.isBlank(clientUser.getRoleIds()) || "null".equals(clientUser.getRoleIds())) {
            return error("请选择角色");
        }

        try {
        	String filePath;
        	if(avatarFile != null) {
                filePath = fileUploadService.upload(avatarFile,null,UploadTypeEnums.ClientUser.getValue(),true);
                clientUser.setAvatar(filePath);
        	}
        	if(selfieImage != null) {
        		  filePath = fileUploadService.upload(selfieImage,null,UploadTypeEnums.ClientUser.getValue(),true);
                  clientUser.setSelfieImage(filePath);
        	}
          
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        // remove roles is the status is not 2
        if (clientUser.getStatus() != 2) {
            clientUser.setRoleIds("");
        }

        clientUser.setUpdateBy(ShiroUtils.getLoginName());
        // 查这条数据的状态是否发生了改变
        ClientUser uu = clientUserService.selectClientUserById(clientUser.getId());

        if(!clientUser.getPhone().equals(clientUser.getUserName())) {
        	clientUser.setUserName(clientUser.getPhone());
        }
        int result = clientUserService.updateClientUser(clientUser);
        if (result == 1) {
            if (uu.getStatus() == clientUser.getStatus()) {
                if ((uu.getCertExpireDate() == null && clientUser.getCertExpireDate() != null) ||
                        (uu.getCertExpireDate() != null && clientUser.getCertExpireDate() == null) ||
                        (uu.getCertExpireDate() != null && clientUser.getCertExpireDate() != null && uu.getCertExpireDate().compareTo(clientUser.getCertExpireDate()) != 0)) {
                    updateClientUserByJmsgAndJpush(clientUser, 4);
                } else {
                    updateClientUserByJmsgAndJpush(clientUser, 1);
                }
            } else {
                updateClientUserByJmsgAndJpush(clientUser, 2);
            }

            if ((uu.getRoleIds() == null && StringUtils.isNotBlank(clientUser.getRoleIds())) ||
                    (uu.getRoleIds() != null && clientUser.getRoleIds() != null && !uu.getRoleIds().equals(clientUser.getRoleIds()))) {
                updateClientUserByJmsgAndJpush(clientUser, 3);
            }

            if (clientUser.getMyClubIds() != null) {
                String[] myClubIds = clientUser.getMyClubIds().split(",");
                result = myClubService.deleteMyClubByClientUserId(clientUser.getId());
                //if(result > 0) {
                for (String myClubId : myClubIds) {
                    MyClub myClub = new MyClub();
                    myClub.setClientUserId(clientUser.getId());
                    try {
                        myClub.setClubId(Long.valueOf(myClubId));
                    } catch (Exception e) {
                        continue;
                    }
                    myClubService.insertMyClub(myClub);
                }
                //}
            }
            return AjaxResult.success("更新用户成功.");
        } else {
            return AjaxResult.error("更新用户失败！");
        }
    }


    public void updateClientUserByJmsgAndJpush(ClientUser clientUser, Integer type) {
        // 更新用户到极光
        try {
            ClientUser user = clientUserService.selectClientUserById(clientUser.getId());

            UserPayload userPayload = UserPayload.newBuilder().setNickname(user.getNickname())
                    .setAddress(user.getAddress() != null ? user.getAddress() : "")
                    .setAvatar(user.getAvatar() != null ? user.getAvatar() : "")
                    .addExtra("phone", user.getPhone()).addExtra("avatar",
                            user.getAvatar() != null ? user.getAvatar() : "")
                    .build();
            JMSG_CLIENT.updateUserInfo(user.getJgUsername(), userPayload);
            log.debug("***【极光】*** 更新极光用户信息成功: " + userPayload);
        } catch (APIConnectionException e) {
            log.error("***【极光】*** 更新极光用户信息失败." + clientUser.getJgUsername());
            e.printStackTrace();
        } catch (APIRequestException e) {
            log.error("***【极光】*** 更新极光用户信息失败." + clientUser.getJgUsername());
            log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                    + e.getErrorMessage() + " Status: " + e.getStatus());
            e.printStackTrace();
        }

        // 如果type == 2 需要推送+消息
        if (type == 2) {
            Message message = new Message();
            String certStatus = "fail";
            //            if (clientUser.getStatus() != 1) {
            message.setCreateBy(ShiroUtils.getLoginName());
            message.setTitle("身份认证更新");
            if (clientUser.getStatus().toString().equals(Constants.CLIENTUSER_STATUS_NO)) {
                message.setContent("您的身份认证未通过");
                certStatus = "fail";
            } else if (clientUser.getStatus() == ClientUserStatus.IN_REVIEW.getCode()) {
                message.setContent("您的身份认证申请正在审核中");
                certStatus = "inReview";
            } else {
                message.setContent("您的身份认证已通过");
                certStatus = "pass";
            }
            //            }
            message.setType(2);
            int success = messageService.insertMessage(message);
            if (success > 0) {
                messageService.insertMessageUser(message.getId(), clientUser.getId(),
                        message.getCreateBy());
                // 推送
                pushService.jpush(message.getTitle(), message.getContent(),
                        clientUser.getId().toString(), "5", "0", certStatus);
            } else {
                throw new RuntimeException("编辑失败");
            }
        }

        // 角色更新
        if (type == 3) {
            Message message = new Message();
            message.setCreateBy(ShiroUtils.getLoginName());
            message.setTitle("角色更新");
            message.setContent("您有角色更新");

            message.setType(2);
            int success = messageService.insertMessage(message);
            if (success > 0) {
                messageService.insertMessageUser(message.getId(), clientUser.getId(),
                        message.getCreateBy());
                // 推送
                //                    Role role = roleService.selectRoleById( userRole.getRoleId() );
                pushService.jpush(message.getTitle(), message.getContent(),
                        clientUser.getId().toString(), "6", "0",
                        "");
            } else {
                throw new RuntimeException("编辑失败");
            }
        }

        // 认证有效期更新
        if (type == 4) {
            Message message = new Message();
            message.setCreateBy(ShiroUtils.getLoginName());
            message.setTitle("认证有效期更新");
            message.setContent("您的认证有效期有更新");
            message.setType(2);
            int success = messageService.insertMessage(message);
            if (success > 0) {
                messageService.insertMessageUser(message.getId(), clientUser.getId(),
                        message.getCreateBy());
                // 推送
                pushService.jpush(message.getTitle(), message.getContent(),
                        clientUser.getId().toString(), "6", "0", "");
            } else {
                throw new RuntimeException("编辑失败");
            }
        }
    }

    /**
     * 删除客户端用户
     */
    @RequiresPermissions("client:clientUser:remove")
    @Log(title = "客户端用户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        // 从极光中删除用户
        String[] idArr = Convert.toStrArray(ids);
        Arrays.stream(idArr).forEach(id -> {
            ClientUser user = clientUserService.selectClientUserById(Long.parseLong(id));

            if (user != null) {
                try {
                    JMSG_CLIENT.deleteUser(user.getJgUsername().trim());
                    log.debug("***【极光】*** 删除极光用户成功:" + user.getJgUsername().trim());
                } catch (APIConnectionException e) {
                    log.error("***【极光】*** 删除用户失败." + user.getJgUsername().trim());
                    e.printStackTrace();
                } catch (APIRequestException e) {
                    log.error("***【极光】*** 删除用户失败." + user.getJgUsername().trim());
                    log.error("***【极光】*** Error Code: " + e.getErrorCode() + " Error Message: "
                            + e.getErrorMessage() + " Status: " + e.getStatus());
                    e.printStackTrace();
                }
            }
        });

        return toAjax(clientUserService.deleteClientUserByIds(ids));
    }


    /**
     * 查询客户端用户分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody ClientUser clientUser) {
        startPage(clientUser.getPd());

        List<ClientUser> list = clientUserService.selectClientUserListAssoc(clientUser);
        return getDataTable(list);
    }


    /**
     * 检查UserName惟一性
     */
    @PostMapping("/checkUserNameUnique")
    @ResponseBody
    public String checkUserNameUnique(ClientUser clientUser) {
        return clientUserService.checkUserNameUnique(clientUser);
    }

}
