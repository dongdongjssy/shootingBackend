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
 * 用户点赞 信息操作处理
 *
 * @author goose
 * @date 2020-05-21
 */
@RestController
@RequestMapping("/shooting/userLike")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户点赞 ", description = "用户点赞")
public class UserLikeRestController extends BaseController {

   @Autowired
   private IUserLikeService userLikeService;

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
    * 查询用户点赞列表
    */
   @PostMapping("/list")
   @ApiOperation(value = "查询用户点赞列表")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userLike", value = "用户点赞", required = false, dataType = "UserLike")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list(@RequestBody UserLike userLike) {
      startPage();

      List<UserLike> list = userLikeService.selectUserLikeList(userLike);
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
   public UserLike getById(@PathVariable("id") Long id) {
      return userLikeService.selectUserLikeById(id);
   }

   /**
    * 导出用户点赞列表
    */
/*    @PostMapping("/export")
	@ApiOperation(value="导出用户点赞列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userLike", value = "用户点赞", required = false, dataType = "UserLike")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult export(UserLike userLike)
    {
    	List<UserLike> list = userLikeService.selectUserLikeList(userLike);
        ExcelUtil<UserLike> util = new ExcelUtil<UserLike>(UserLike.class);
        return util.exportExcel(list, "userLike");
    } */

   /**
    * 导入用户点赞列表
    */
/*    @PostMapping("/import")
	@ApiOperation(value="导入用户点赞列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "用户点赞文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {
    	try {
        	List<UserLike> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserLike.class);
        	for(UserLike obj : list) {
        		userLikeService.insertUserLike(obj);
        	}
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }*/


   /**
    * 新增保存用户点赞
    */
   @PostMapping("/add")
   @ApiOperation(value = "新增保存用户点赞")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userLike", value = "用户点赞", required = true, dataType = "UserLike")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add(@RequestBody UserLike userLike) {
      if (userLikeService.selectUserLikeList(userLike).size() > 0) {
         return AjaxResult.error("不能重复点赞");
      }

      int result = userLikeService.insertUserLike(userLike);

      if (result > 0) {
         if ("recommend".equals(userLike.getLikeType())) {
            Recommend recommend = recommendService.selectRecommendById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommend");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommend.setLikeCount(count);
            recommendService.updateRecommend(recommend);
         }

         if ("post".equals(userLike.getLikeType())) {
            Post post = postService.selectPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("post");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            post.setLikeCount(count);
            postService.updatePost(post);
         }

         if ("clubPost".equals(userLike.getLikeType())) {
            ClubPost clubPost = clubPostService.selectClubPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("clubPost");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            clubPost.setLikeCount(count);
            clubPostService.updateClubPost(clubPost);
         }
         if ("recommendCoach".equals(userLike.getLikeType())) {
            RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendCoach");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendCoach.setLikeCount(count);
            recommendCoachService.updateRecommendCoach(recommendCoach);
         }
         if ("recommendJudge".equals(userLike.getLikeType())) {
            RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendJudge");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendJudge.setLikeCount(count);
            recommendJudgeService.updateRecommendJudge(recommendJudge);
         }
      }

      return toAjax(result);
   }

   /**
    * 修改保存用户点赞
    */
   @PostMapping("/edit")
   @ApiOperation(value = "修改保存用户点赞")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userLike", value = "用户点赞", required = true, dataType = "UserLike")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit(@RequestBody UserLike userLike) {
      return toAjax(userLikeService.updateUserLike(userLike));
   }

   /**
    * 删除用户点赞
    */
   @PostMapping("/remove")
   @ApiOperation(value = "删除用户点赞")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove(String ids) {
      return toAjax(userLikeService.deleteUserLikeByIds(ids));
   }

   /**
    * 删除用户点赞
    */
   @PostMapping("/remove/userLike")
   @ApiOperation(value = "删除用户点赞")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userLike", value = "用户点赞", required = true, dataType = "UserLike")
   })
   public AjaxResult removeUserLike(@RequestBody UserLike userLike) {
         if ("recommend".equals(userLike.getLikeType())) {
            Recommend recommend = recommendService.selectRecommendById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommend");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommend.setLikeCount(count - 1);
            recommendService.updateRecommend(recommend);
         }

         if ("post".equals(userLike.getLikeType())) {
            Post post = postService.selectPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("post");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            post.setLikeCount(count - 1);
            postService.updatePost(post);
         }

         if ("clubPost".equals(userLike.getLikeType())) {
            ClubPost clubPost = clubPostService.selectClubPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("clubPost");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            clubPost.setLikeCount(count - 1);
            clubPostService.updateClubPost(clubPost);
         }
         if ("recommendCoach".equals(userLike.getLikeType())) {
            RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendCoach");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendCoach.setLikeCount(count-1);
            recommendCoachService.updateRecommendCoach(recommendCoach);
         }
         if ("recommendJudge".equals(userLike.getLikeType())) {
            RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendJudge");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendJudge.setLikeCount(count-1);
            recommendJudgeService.updateRecommendJudge(recommendJudge);
         }

         return toAjax(userLikeService.deleteUserLike(userLike));
   }

   /**
    * 根据id删除用户点赞
    */
   @PostMapping("/remove/{id}")
   @ApiOperation(value = "根据id删除用户点赞")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById(@PathVariable("id") Long id) {
      UserLike userLike = userLikeService.selectUserLikeById(id);

      if (userLike != null) {
         if ("recommend".equals(userLike.getLikeType())) {
            Recommend recommend = recommendService.selectRecommendById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommend");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommend.setLikeCount(count - 1);
            recommendService.updateRecommend(recommend);
         }

         if ("post".equals(userLike.getLikeType())) {
            Post post = postService.selectPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("post");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            post.setLikeCount(count - 1);
            postService.updatePost(post);
         }

         if ("clubPost".equals(userLike.getLikeType())) {
            ClubPost clubPost = clubPostService.selectClubPostById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("clubPost");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            clubPost.setLikeCount(count - 1);
            clubPostService.updateClubPost(clubPost);
         }
         if ("recommendCoach".equals(userLike.getLikeType())) {
            RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendCoach");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendCoach.setLikeCount(count-1);
            recommendCoachService.updateRecommendCoach(recommendCoach);
         }
         if ("recommendJudge".equals(userLike.getLikeType())) {
            RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById(userLike.getLikeId());
            UserLike updatedUserLike = new UserLike();
            updatedUserLike.setLikeId(userLike.getLikeId());
            updatedUserLike.setLikeType("recommendJudge");
            int count = userLikeService.selectUserLikeList(updatedUserLike).size();
            recommendJudge.setLikeCount(count-1);
            recommendJudgeService.updateRecommendJudge(recommendJudge);
         }

         return toAjax(userLikeService.deleteUserLikeById(id));
      }

      return AjaxResult.error("不能重复删除点赞");
   }


   /**
    * 查询用户点赞分页列表
    */
   @PostMapping("/list/page")
   @ApiOperation(value = "查询用户点赞分页列表")
   @ApiImplicitParams({
         @ApiImplicitParam(paramType = "body", name = "userLike", value = "用户点赞", required = true, dataType = "UserLike")
   })
   //@PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage(@RequestBody UserLike userLike) {
      startPage(userLike.getPd());
      List<UserLike> list = userLikeService.selectUserLikeList(userLike);
      return getDataTable(list);
   }


}
