package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframewor.security.access.prepost.PreAuthorize;


/**
 * 用户阅读 信息操作处理
 *
 * @author goose
 * @date 2020-05-28
 */
@RestController
@RequestMapping("/shooting/userRead")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户阅读 ", description = "用户阅读")
public class UserReadRestController extends BaseController {

   @Autowired
   private IUserReadService userReadService;

   @Autowired
   private IRecommendService recommendService;

   @Autowired
   private IPostService postService;

   @Autowired
   private IClubPostService clubPostService;

   @Autowired
   private IRecommendCoachService recommendCoachService;

   @Autowired
   private IRecommendJudgeService recommendJudgeService;


   /**
    * 查询用户阅读列表
    */
   @PostMapping("/list")
   @ApiOperation(value = "查询用户阅读列表")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userRead", value = "用户阅读", required = false, dataType = "UserRead")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list(@RequestBody UserRead userRead) {
      startPage();

      List<UserRead> list = userReadService.selectUserReadList(userRead);
      return getDataTable(list);
   }

   /**
    * 根据ID查询
    */
   @PostMapping("/getById/{id}")
   @ApiOperation(value = " 根据ID查询")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Long")})
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public UserRead getById(@PathVariable("id") Long id) {
      return userReadService.selectUserReadById(id);
   }

   /**
    * 导出用户阅读列表
    */
/*    @PostMapping("/export")
	@ApiOperation(value="导出用户阅读列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userRead", value = "用户阅读", required = false, dataType = "UserRead")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult export(UserRead userRead)
    {
    	List<UserRead> list = userReadService.selectUserReadList(userRead);
        ExcelUtil<UserRead> util = new ExcelUtil<UserRead>(UserRead.class);
        return util.exportExcel(list, "userRead");
    } */

   /**
    * 导入用户阅读列表
    */
/*    @PostMapping("/import")
	@ApiOperation(value="导入用户阅读列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "用户阅读文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {
    	try {
        	List<UserRead> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserRead.class);
        	for(UserRead obj : list) {
        		userReadService.insertUserRead(obj);
        	}
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }*/


   /**
    * 新增保存用户阅读
    */
   @PostMapping("/add")
   @ApiOperation(value = "新增保存用户阅读")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userRead", value = "用户阅读", required = true, dataType = "UserRead")
   })
   public AjaxResult add(@RequestBody UserRead userRead) {
      List<UserRead> userReads=userReadService.selectUserReadList(userRead);
      if(!userReads.isEmpty()){
         return toAjax(0);
      }
      int result = userReadService.insertUserRead(userRead);

      if (result > 0) {
         if ("recommend".equals(userRead.getReadType())) {
            Recommend recommend = recommendService.selectRecommendById(userRead.getReadId());
            UserRead updatedUserRead = new UserRead();
            updatedUserRead.setReadId(userRead.getReadId());
            updatedUserRead.setReadType("recommend");
            int count = userReadService.selectUserReadList(updatedUserRead).size();
            recommend.setReadCount(count);
            recommendService.updateRecommend(recommend);
         }

         if ("post".equals(userRead.getReadType())) {
            Post post = postService.selectPostById(userRead.getReadId());
            UserRead updatedUserRead = new UserRead();
            updatedUserRead.setReadId(userRead.getReadId());
            updatedUserRead.setReadType("post");
            int count = userReadService.selectUserReadList(updatedUserRead).size();
            post.setReadCount(count);
            postService.updatePost(post);
         }

         if ("clubPost".equals(userRead.getReadType())) {
            ClubPost clubPost = clubPostService.selectClubPostById(userRead.getReadId());
            UserRead updatedUserRead = new UserRead();
            updatedUserRead.setReadId(userRead.getReadId());
            updatedUserRead.setReadType("clubPost");
            int count = userReadService.selectUserReadList(updatedUserRead).size();
            clubPost.setReadCount(count);
            clubPostService.updateClubPost(clubPost);
         }

         if ("recommendCoach".equals(userRead.getReadType())) {
            RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById(userRead.getReadId());
            UserRead updatedUserRead = new UserRead();
            updatedUserRead.setReadId(userRead.getReadId());
            updatedUserRead.setReadType("recommendCoach");
            int count = userReadService.selectUserReadList(updatedUserRead).size();
            recommendCoach.setReadCount(count);
            recommendCoachService.updateRecommendCoach(recommendCoach);
         }

         if ("recommendJudge".equals(userRead.getReadType())) {
            RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById(userRead.getReadId());
            UserRead updatedUserRead = new UserRead();
            updatedUserRead.setReadId(userRead.getReadId());
            updatedUserRead.setReadType("recommendJudge");
            int count = userReadService.selectUserReadList(updatedUserRead).size();
            recommendJudge.setReadCount(count);
            recommendJudgeService.updateRecommendJudge(recommendJudge);
         }

      }

      return toAjax(result);
   }

   /**
    * 修改保存用户阅读
    */
   @PostMapping("/edit")
   @ApiOperation(value = "修改保存用户阅读")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userRead", value = "用户阅读", required = true, dataType = "UserRead")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit(@RequestBody UserRead userRead) {
      return toAjax(userReadService.updateUserRead(userRead));
   }

   /**
    * 删除用户阅读
    */
   @PostMapping("/remove")
   @ApiOperation(value = "删除用户阅读")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove(String ids) {
      return toAjax(userReadService.deleteUserReadByIds(ids));
   }

   /**
    * 根据id删除用户阅读
    */
   @PostMapping("/remove/{id}")
   @ApiOperation(value = "根据id删除用户阅读")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById(@PathVariable("id") Long id) {
      return toAjax(userReadService.deleteUserReadById(id));
   }


   /**
    * 查询用户阅读分页列表
    */
   @PostMapping("/list/page")
   @ApiOperation(value = "查询用户阅读分页列表")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userRead", value = "用户阅读", required = true, dataType = "UserRead")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage(@RequestBody UserRead userRead) {
      startPage(userRead.getPd());
      List<UserRead> list = userReadService.selectUserReadList(userRead);
      return getDataTable(list);
   }


}
