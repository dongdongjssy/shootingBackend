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
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubJoinApplicationService;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.IMyClubService;
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
import java.util.List;

/**
 * 入群申请 信息操作处理
 *
 * @author goose
 * @date 2020-05-23
 */
@Controller
@RequestMapping("/shooting/clubJoinApplication")
public class ClubJoinApplicationController extends ClubBaseController {

   private static final Logger log = LoggerFactory.getLogger(ClubJoinApplicationController.class);

   private String prefix = "shooting/clubJoinApplication";

   @Autowired
   private IClubJoinApplicationService clubJoinApplicationService;

   @Autowired
   private IClubService clubService;

   @Autowired
   private IClientUserService clientUserService;

   @Autowired
   private IMyClubService myClubService;
   @Autowired
   private ISysDictDataService dictDataService;


   @RequiresPermissions("shooting:clubJoinApplication:view")
   @GetMapping()
   public String clubJoinApplication(ModelMap mmap) {
	   Long clubId = getClubId();
	   mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      return prefix + "/clubJoinApplication";
   }

   /**
    * 查询入群申请列表
    */
   @RequiresPermissions("shooting:clubJoinApplication:list")
   @PostMapping("/list")
   @ResponseBody
   public TableDataInfo list(ClubJoinApplication clubJoinApplication) {
      startPage();
      clubJoinApplication = setClubId(clubJoinApplication);
      List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationListAssoc(clubJoinApplication);
      return getDataTable(list);
   }


   /**
    * 导出入群申请列表
    */
   @RequiresPermissions("shooting:clubJoinApplication:export")
   @PostMapping("/export")
   @ResponseBody
   public AjaxResult export(ClubJoinApplication clubJoinApplication) {
	   clubJoinApplication = setClubId(clubJoinApplication);
      List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
      if(list.size()>0){
         for(int i =0;i<list.size();i++) {
            List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
            List<Club> clubList=clubService.selectClubAll();
            List<ClientUser> clientUserList=clientUserService.selectClientUserAll();
            for(SysDictData d : status) {
               Integer aS = list.get(i).getStatus();
               if(aS!=null) {
                  if(d.getDictValue().equals(aS.toString())) {
                     list.get(i).setStatusName(d.getDictLabel());
                  }
               }
            }
            for (Club club:clubList){
               if(club.getId()==list.get(i).getClubId()){
                  list.get(i).setClubName(club.getTitle());
               }
            }
            for (ClientUser clientUser:clientUserList){
               if(clientUser.getId()==list.get(i).getClientUserId()){
                  list.get(i).setClientUserName(clientUser.getNickname());
               }
            }
         }

      }
      ExcelUtil<ClubJoinApplication> util = new ExcelUtil<ClubJoinApplication>(ClubJoinApplication.class);
      return util.exportExcel(list, "clubJoinApplication");
   }

   /**
    * 导入入群申请列表
    */
   @RequiresPermissions("shooting:clubJoinApplication:import")
   @PostMapping("/import")
   @ResponseBody
   public AjaxResult importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
      try {
         List<ClubJoinApplication> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubJoinApplication.class);
         for (ClubJoinApplication clubJoinApplication : list) {
        	clubJoinApplication = setClubId(clubJoinApplication);
            clubJoinApplicationService.insertClubJoinApplication(clubJoinApplication);
         }
      } catch (ExcelUtilException | IOException e) {
         return AjaxResult.error(e.getMessage());
      }

      return AjaxResult.success("成功导入全部数据");

   }

   /**
    * 新增入群申请
    */
   @GetMapping("/add")
   public String add(ModelMap mmap) {
	   Long clubId = getClubId();
	   mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      return prefix + "/add";
   }

   /**
    * 新增保存入群申请
    */
   @RequiresPermissions("shooting:clubJoinApplication:add")
   @Log(title = "入群申请", businessType = BusinessType.INSERT)
   @PostMapping("/add")
   @ResponseBody
   public AjaxResult addSave(ClubJoinApplication clubJoinApplication
   ) {

      clubJoinApplication.setCreateBy(ShiroUtils.getLoginName());
      clubJoinApplication = setClubId(clubJoinApplication);
      return toAjax(clubJoinApplicationService.insertClubJoinApplication(clubJoinApplication));
   }

   /**
    * 修改入群申请
    */
   @GetMapping("/edit/{id}")
   public String edit(@PathVariable("id") Long id, ModelMap mmap) {
      ClubJoinApplication clubJoinApplication = clubJoinApplicationService.selectClubJoinApplicationByIdAssoc(id);
      mmap.put("clubJoinApplication", clubJoinApplication);
      Long clubId = getClubId();
      mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      return prefix + "/edit";
   }

   /**
    * 修改保存入群申请
    */
   @RequiresPermissions("shooting:clubJoinApplication:edit")
   @Log(title = "入群申请", businessType = BusinessType.UPDATE)
   @PostMapping("/edit")
   @ResponseBody
   public AjaxResult editSave(ClubJoinApplication clubJoinApplication) {
      Club club = clubService.selectClubById(clubJoinApplication.getClubId());
      ClientUser user = clientUserService.selectClientUserById(clubJoinApplication.getClientUserId());
      ClubJoinApplication oldClubJoinApplciation = clubJoinApplicationService.selectClubJoinApplicationById(clubJoinApplication.getId());

      boolean addToGroup = oldClubJoinApplciation.getStatus() == 0 && clubJoinApplication.getStatus() == 1;
      boolean removeFromGroup = oldClubJoinApplciation.getStatus() == 1 && clubJoinApplication.getStatus() == 0;

      clubJoinApplication.setUpdateBy(ShiroUtils.getLoginName());

      int result = clubJoinApplicationService.updateClubJoinApplication(clubJoinApplication);

      if (result > 0 && club != null && user != null) {
         // TBD - should move the constants to config file
         String APP_KEY = "d27e90395d0fc5adac3b3c65";
         String MASTER_SECRET = "da08bfd3a4e04fe6af5f383e";

         // 极光聊天API
         final GroupClient JMSG_GROUP_CLIENT = new GroupClient(APP_KEY, MASTER_SECRET);
         final JMessageClient JMSG_CLIENT = new JMessageClient(APP_KEY, MASTER_SECRET);

         Members members = Members.newBuilder().addMember(user.getJgUsername()).build();

         MyClub myClub = new MyClub();
         myClub.setClientUserId(clubJoinApplication.getClientUserId());
         myClub.setClubId(clubJoinApplication.getClubId());

         // 通过入群申请
         if (addToGroup) {
//            myClubService.insertMyClub(myClub);

            try {
               JMSG_GROUP_CLIENT.addOrRemoveMembers(Long.parseLong(club.getJgGroupId()), members, null);
               log.debug("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 成功");
            } catch (APIConnectionException e) {
               log.error("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 失败");
               e.printStackTrace();
            } catch (APIRequestException e) {
               log.error("***【极光聊天】*** 添加用户 【" + user.getNickname() + "】 到群组 【" + club.getTitle() + "】 失败");
               log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
                     " Error Message: " + e.getErrorMessage() +
                     " Status: " + e.getStatus());
               e.printStackTrace();
            }

            //            try {
            //               JMSG_CLIENT.sendMessage(
            //                     MessagePayload.newBuilder()
            //                           .setVersion(1)
            //                           .setTargetType("group")
            //                           .setTargetId(club.getJgGroupId())
            //                           .setFromType("user")
            //                           .setFromId(club.getJgUsername())
            //                           .setFromName(club.getTitle())
            //                           .setMessageType(MessageType.TEXT)
            //                           .setMessageBody(MessageBody.newBuilder().setText("欢迎【" + user.getNickname() + "】加入群组【" + club.getTitle() + "】").build())
            //                           .build()
            //               );
            //               log.debug("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 加入群组 【" + club.getTitle() + "】 成功");
            //            } catch (APIConnectionException e) {
            //               log.error("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 加入群组 【" + club.getTitle() + "】 失败");
            //               e.printStackTrace();
            //            } catch (APIRequestException e) {
            //               log.error("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 加入群组 【" + club.getTitle() + "】 失败");
            //               log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
            //                     " Error Message: " + e.getErrorMessage() +
            //                     " Status: " + e.getStatus());
            //               e.printStackTrace();
            //            }
         }

         // 移除出群
         if (removeFromGroup) {
//            List<MyClub> myClubs = myClubService.selectMyClubList(myClub);
//            if (myClubs.size() == 1) {
//               myClubService.deleteMyClubById(myClubs.get(0).getId());
//            }

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

            //            try {
            //               JMSG_CLIENT.sendMessage(
            //                     MessagePayload.newBuilder()
            //                           .setVersion(1)
            //                           .setTargetType("group")
            //                           .setTargetId(club.getJgGroupId())
            //                           .setFromType("user")
            //                           .setFromId(club.getJgUsername())
            //                           .setFromName(club.getTitle())
            //                           .setMessageType(MessageType.TEXT)
            //                           .setMessageBody(MessageBody.newBuilder().setText("你已退出群组【" + club.getTitle() + "】").build())
            //                           .build()
            //               );
            //               log.debug("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 退出群组 【" + club.getTitle() + "】 成功");
            //            } catch (APIConnectionException e) {
            //               log.error("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 退出群组 【" + club.getTitle() + "】 失败");
            //               e.printStackTrace();
            //            } catch (APIRequestException e) {
            //               log.error("***【极光聊天】*** 发送消息通知用户 【" + user.getNickname() + "】 退出群组 【" + club.getTitle() + "】 失败");
            //               log.error("***【极光聊天】*** Error Code: " + e.getErrorCode() +
            //                     " Error Message: " + e.getErrorMessage() +
            //                     " Status: " + e.getStatus());
            //               e.printStackTrace();
            //            }
         }
      }

      return toAjax(result);
   }

   /**
    * 删除入群申请
    */
   @RequiresPermissions("shooting:clubJoinApplication:remove")
   @Log(title = "入群申请", businessType = BusinessType.DELETE)
   @PostMapping("/remove")
   @ResponseBody
   public AjaxResult remove(String ids) {
      return toAjax(clubJoinApplicationService.deleteClubJoinApplicationByIds(ids));
   }


   /**
    * 查询入群申请分页列表
    */
   @PostMapping("/list/page")
   public TableDataInfo listPage(@RequestBody ClubJoinApplication clubJoinApplication) {
      startPage(clubJoinApplication.getPd());
      clubJoinApplication = setClubId(clubJoinApplication);
      List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationListAssoc(clubJoinApplication);
      return getDataTable(list);
   }


}
