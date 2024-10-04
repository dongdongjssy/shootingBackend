package io.goose.web.controller.shooting;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.Members;
import cn.jmessage.api.group.GroupClient;
import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.ClubJoinApplication;
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubJoinApplicationService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.IMyClubService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * 我所属的俱乐部 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping("/shooting/myClub")
public class MyClubController extends ClubBaseController {

   private static final Logger log = LoggerFactory.getLogger(MyClubController.class);

   private String prefix = "shooting/myClub";

   @Autowired
   private IMyClubService myClubService;

   @Autowired
   private IClubService clubService;

   @Autowired
   private IClientUserService clientUserService;

   @Autowired
   private IClubJoinApplicationService clubJoinApplicationService;

   @RequiresPermissions("shooting:myClub:view")
   @GetMapping()
   public String myClub(ModelMap mmap) {
      Long clubId = getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId(clubId);
      mmap.put("clientUserIdList", clubId != null ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
      mmap.put("clubIdList", clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
      return prefix + "/myClub";
   }


   /**
    * 查询我所属的俱乐部列表
    */
   @RequiresPermissions("shooting:myClub:list")
   @PostMapping("/list")
   @ResponseBody
   public TableDataInfo list(MyClub myClub) {
      startPage();
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubListAssoc(myClub);
      return getDataTable(list);
   }


   /**
    * 导出我所属的俱乐部列表
    */
   @RequiresPermissions("shooting:myClub:export")
   @PostMapping("/export")
   @ResponseBody
   public AjaxResult export(MyClub myClub) {
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubList(myClub);
      ExcelUtil<MyClub> util = new ExcelUtil<MyClub>(MyClub.class);
      return util.exportExcel(list, "myClub");
   }


   /**
    * 导入我所属的俱乐部列表
    */
   @RequiresPermissions("shooting:myClub:import")
   @PostMapping("/import")
   @ResponseBody
   public AjaxResult
   importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
      try {
         List<MyClub> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), MyClub.class);
         for (MyClub myClub : list) {
            myClub = setClubId(myClub);
            myClubService.insertMyClub(myClub);
         }
      } catch (ExcelUtilException | IOException e) {
         return AjaxResult.error(e.getMessage());
      }

      return AjaxResult.success("成功导入全部数据");

   }


   /**
    * 新增我所属的俱乐部
    */
   @GetMapping("/add")
   public String add(ModelMap mmap) {
      Long clubId = getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId(clubId);
      Club club = new Club();
      club.setId(clubId);
      mmap.put("clientUserIdList", clubId != null ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
      mmap.put("clubIdList", clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
      return prefix + "/add";
   }


   /**
    * 新增保存我所属的俱乐部
    */
   @RequiresPermissions("shooting:myClub:add")
   @Log(title = "我所属的俱乐部", businessType = BusinessType.INSERT)
   @PostMapping("/add")
   @ResponseBody
   public AjaxResult addSave(MyClub myClub) {

      myClub.setCreateBy(ShiroUtils.getLoginName());
      myClub = setClubId(myClub);
      return toAjax(myClubService.insertMyClub(myClub));
   }


   /**
    * 修改我所属的俱乐部
    */
   @GetMapping("/edit/{id}")
   public String edit(@PathVariable("id") Long id, ModelMap mmap) {
      MyClub myClub = myClubService.selectMyClubByIdAssoc(id);
      mmap.put("myClub", myClub);
      Long clubId = myClub.getClubId();
      ClientUser cu = new ClientUser();
      cu.setClubId(clubId);
      Club club = new Club();
      club.setId(clubId);
      mmap.put("clientUserIdList", (clubId != null && clubId != 0) ? clientUserService.selectClientUserList(cu) : clientUserService.selectClientUserAll());
      mmap.put("clubIdList", (clubId != null && clubId != 0) ? clubService.selectClubById(clubId) : clubService.selectClubAll());
      return prefix + "/edit";
   }


   /**
    * 修改保存我所属的俱乐部
    */
   @RequiresPermissions("shooting:myClub:edit")
   @Log(title = "我所属的俱乐部", businessType = BusinessType.UPDATE)
   @PostMapping("/edit")
   @ResponseBody
   public AjaxResult editSave(MyClub myClub) {
      myClub.setUpdateBy(ShiroUtils.getLoginName());
      return toAjax(myClubService.updateMyClub(myClub));
   }


   /**
    * 删除我所属的俱乐部
    */
   @RequiresPermissions("shooting:myClub:remove")
   @Log(title = "我所属的俱乐部", businessType = BusinessType.DELETE)
   @PostMapping("/remove")
   @ResponseBody
   public AjaxResult remove(String ids) {
      // TBD - should move the constants to config file
      String APP_KEY = "d27e90395d0fc5adac3b3c65";
      String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

      // 极光聊天API
      final GroupClient JMSG_GROUP_CLIENT = new GroupClient(APP_KEY, MASTER_SECRET);
      final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

      String[] idArr = Convert.toStrArray(ids);
      for (String id : idArr) {
         MyClub myClub = myClubService.selectMyClubById(Long.parseLong(id));
         if (myClub != null) {
            Club club = clubService.selectClubById(myClub.getClubId());
            ClientUser user = clientUserService.selectClientUserById(myClub.getClientUserId());

            ClubJoinApplication clubJoinApplication = new ClubJoinApplication();
            clubJoinApplication.setClientUserId(user.getId());
            clubJoinApplication.setClubId(club.getId());
            List<ClubJoinApplication> applications = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
            if (applications != null && applications.get(0) != null) {
               clubJoinApplicationService.deleteClubJoinApplicationById(applications.get(0).getId());
            }

            Members members = Members.newBuilder().addMember(user.getJgUsername()).build();

            try {
               JMSG_GROUP_CLIENT.addOrRemoveMembers(Long.parseLong(club.getJgGroupId()), null, members);
               log.debug("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 成功");
            } catch (APIConnectionException e) {
               log.error("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 失败");
               e.printStackTrace();
            } catch (APIRequestException e) {
               log.error("***【极光聊天】*** 移除用户 【" + user.getNickname() + "】 从群组 【" + club.getTitle() + "】 失败");
               log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                     " Error Message: " + e.getErrorMessage() +
                     " Status: " + e.getStatus());
               e.printStackTrace();
            }
         }
      }

      return toAjax(myClubService.deleteMyClubByIds(ids));
   }


   /**
    * 查询我所属的俱乐部分页列表
    */
   @PostMapping("/list/page")
   public TableDataInfo listPage(@RequestBody MyClub myClub) {
      startPage(myClub.getPd());
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubListAssoc(myClub);
      return getDataTable(list);
   }

}
