package io.goose.web.controller.shooting;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.group.CreateGroupResult;
import cn.jmessage.api.group.GroupClient;
import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.enums.BusinessType;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.goose.system.domain.SysDictData;
import io.goose.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * 俱乐部 信息操作处理
 *
 * @author goose
 * @date 2020-05-09
 */
@Controller
@RequestMapping("/shooting/club")
public class ClubController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ClubController.class);

    // TBD - should move the constants to config file
    private static final String APP_KEY = "d27e90395d0fc5adac3b3c65";
    private static final String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

    // 极光API
    private final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

    @Autowired
    private Global global;

    private String prefix = "shooting/club";

    @Autowired
    private IClubService clubService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IClubJoinApplicationService clubJoinApplicationService;

    @Autowired
    private IUserFollowService userFollowService;

    @Autowired
    private FileUploadDownloadOSSService fileUploadDownloadService;

    @Autowired
    private IClientUserService clientUserService;

    @RequiresPermissions("shooting:club:view")
    @GetMapping()
    public String club(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        return prefix + "/club";
    }


    /**
     * 查询俱乐部列表
     */
    @RequiresPermissions("shooting:club:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Club club) {
        startPage();
        List<Club> list = clubService.selectClubListAssoc(club);
        return getDataTable(list);
    }


    /**
     * 导出俱乐部列表
     */
    @RequiresPermissions("shooting:club:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Club club) {
        List<Club> list = clubService.selectClubList(club);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
                List<Area> areaList = areaService.selectAreaAll();
                for (SysDictData d : status) {
                    Integer aS = list.get(i).getStatus();
                    if (aS != null) {
                        if (d.getDictValue().equals(aS.toString())) {
                            list.get(i).setStatusName(d.getDictLabel());
                        }
                    }
                }
                for (Area area : areaList) {
                    if (area.getId() == list.get(i).getAreaId()) {
                        list.get(i).setAreaName(area.getName());
                    }
                }
            }

        }
        ExcelUtil<Club> util = new ExcelUtil<Club>(Club.class);
        return util.exportExcel(list, "club");
    }


    /**
     * 导入俱乐部列表
     */
    @RequiresPermissions("shooting:club:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Club> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Club.class);
            for (Club obj : list) {
                clubService.insertClub(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增俱乐部
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("clientUserList", clientUserService.selectClientUserAll());
        return prefix + "/add";
    }


    /**
     * 新增保存俱乐部
     */
    @RequiresPermissions("shooting:club:add")
    @Log(title = "俱乐部", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Club club,
                              @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        String avatarFilePath = "";

        try {
            if (avatarFile != null && !avatarFile.isEmpty()) {
                avatarFilePath = fileUploadDownloadService.upload(avatarFile, null, UploadTypeEnums.Club.getValue(), true);
                club.setAvatar(avatarFilePath);
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageFile, null, UploadTypeEnums.Club.getValue(), true);
                club.setImage(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        String groupAdminName = null;
        if (club.getJgGroupAdminId() != null) {
            ClientUser clientUser = clientUserService.selectClientUserById(club.getJgGroupAdminId());
            if (clientUser != null)
                groupAdminName = clientUser.getJgUsername();
        }

        // 随机生成6位数，当做管理员的极光用户名
        if (groupAdminName == null) {
            groupAdminName = String.valueOf(new Random().nextInt(10000000));
        }

        club.setJgUsername(groupAdminName);

        club.setCreateBy(ShiroUtils.getLoginName());
        int insertResult = clubService.insertClub(club);

        if (club.getId() != null) {
            // 添加俱乐部管理员到极光
            try {
                if (club.getJgGroupAdminId() == null) {
                    RegisterInfo registerInfo = RegisterInfo.newBuilder()
                            .setUsername(groupAdminName)
                            .setPassword(groupAdminName)
                            .setNickname(groupAdminName)
                            .setAvatar(!StringUtils.isBlank(avatarFilePath) ? avatarFilePath : "")
                            .setRegion(club.getArea() != null ? club.getArea().getName() : "")
                            .addExtra("avatar", !StringUtils.isBlank(avatarFilePath) ? avatarFilePath : "")
                            .build();

                    JMSG_CLIENT.registerUsers(new RegisterInfo[]{registerInfo});
                    //            JMSG_CLIENT.registerAdmins(groupAdminName, groupAdminName);
                    log.debug("***【极光聊天】*** 添加群组管理员用户到极光成功: " + registerInfo);
                }
            } catch (APIConnectionException e) {
                log.error("***【极光聊天】*** 添加群组管理员用户到极光失败." + club.getTitle());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光聊天】*** 添加群组管理员用户到极光失败." + club.getTitle());
                log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                        " Error Message: " + e.getErrorMessage() +
                        " Status: " + e.getStatus());
                e.printStackTrace();
            }

            // 新建群组到极光
            try {
                final GroupClient JMSG_GROUP_CLIENT = new GroupClient(APP_KEY, MASTER_SECRET);
                CreateGroupResult result = JMSG_CLIENT.createGroup(
                        groupAdminName,
                        club.getJgGroupName() != null ? club.getJgGroupName() : club.getTitle(),
                        club.getJgGroupName() != null ? club.getJgGroupName() : club.getTitle(),
                        avatarFilePath,
                        1
                );
                club.setJgGroupId(String.valueOf(result.getGid()));
                clubService.updateClub(club);
                log.debug("***【极光聊天】*** 创建群组到极光成功: " + club.getTitle());


                Members members = Members.newBuilder().addMember("111111").build();
                JMSG_GROUP_CLIENT.addOrRemoveMembers(result.getGid(), members, null);
            } catch (APIConnectionException e) {
                log.error("***【极光聊天】*** 创建极光群组失败." + club.getTitle());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光聊天】*** 创建极光群组失败." + club.getTitle());
                log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                        " Error Message: " + e.getErrorMessage() +
                        " Status: " + e.getStatus());
                e.printStackTrace();
            }
        }

        return toAjax(insertResult);
    }


    /**
     * 修改俱乐部
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Club club = clubService.selectClubByIdAssoc(id);
        mmap.put("club", club);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        mmap.put("areaIdList", areaService.selectAreaAll());
        return prefix + "/edit";
    }


    /**
     * 修改保存俱乐部
     */
    @RequiresPermissions("shooting:club:edit")
    @Log(title = "俱乐部", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Club club,
                               @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(avatarFile, null, UploadTypeEnums.Club.getValue(), true);
                club.setAvatar(filePath);
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageFile, null, UploadTypeEnums.Club.getValue(), true);
                club.setImage(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        club.setUpdateBy(ShiroUtils.getLoginName());
        int result = clubService.updateClub(club);

        if (result == 1) {
            // 更新极光群组
            Club updatedClub = clubService.selectClubById(club.getId());
            try {
                JMSG_CLIENT.updateGroupInfo(
                        Long.parseLong(updatedClub.getJgGroupId()),
                        updatedClub.getTitle(),
                        updatedClub.getTitle(),
                        updatedClub.getAvatar()
                );
                log.debug("***【极光】*** 更新极光群组成功:" + updatedClub.getTitle());
            } catch (APIConnectionException e) {
                log.error("***【极光】*** 更新极光群组失败." + updatedClub.getTitle());
                e.printStackTrace();
            } catch (APIRequestException e) {
                log.error("***【极光】*** 更新极光群组失败." + updatedClub.getTitle());
                log.error("***【极光】*** Error Code: " + e.getErrorCode() +
                        " Error Message: " + e.getErrorMessage() +
                        " Status: " + e.getStatus());
                e.printStackTrace();
            }
        }

        return toAjax(result);
    }


    /**
     * 删除俱乐部
     */
    @RequiresPermissions("shooting:club:remove")
    @Log(title = "俱乐部", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        // 从极光中删除群组
        String[] idArr = Convert.toStrArray(ids);
        Arrays.stream(idArr).forEach(id -> {
            Club club = clubService.selectClubById(Long.parseLong(id));

            if (club != null) {
                try {
                    JMSG_CLIENT.deleteGroup(Long.parseLong(club.getJgGroupId()));
                    log.debug("***【极光】*** 删除极光群组成功:" + club.getTitle());

                    JMSG_CLIENT.deleteUser(club.getJgUsername());
                    log.debug("***【极光】*** 删除群组自动生成管理员成功:" + club.getJgUsername());
                } catch (APIConnectionException e) {
                    log.error("***【极光】*** 删除极光群组失败." + club.getTitle());
                    e.printStackTrace();
                } catch (APIRequestException e) {
                    log.error("***【极光】*** 删除极光群组失败." + club.getTitle());
                    log.error("***【极光】*** Error Code: " + e.getErrorCode() +
                            " Error Message: " + e.getErrorMessage() +
                            " Status: " + e.getStatus());
                    e.printStackTrace();
                }

                ClubJoinApplication clubJoinApplication = new ClubJoinApplication();
                clubJoinApplication.setClubId(club.getId());
                List<ClubJoinApplication> clubJoinApplications = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
                clubJoinApplications.forEach(cja -> clubJoinApplicationService.deleteClubJoinApplicationById(cja.getId()));

                UserFollow userFollow = new UserFollow();
                userFollow.setFollowId(club.getId());
                userFollow.setFollowType("club");
                List<UserFollow> userFollows = userFollowService.selectUserFollowList(userFollow);
                userFollows.forEach(uf -> userFollowService.deleteUserFollowById(uf.getId()));
            }
        });

        return toAjax(clubService.deleteClubByIds(ids));
    }


    /**
     * 查询俱乐部分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Club club) {
        startPage(club.getPd());

        List<Club> list = clubService.selectClubListAssoc(club);
        return getDataTable(list);
    }
}
