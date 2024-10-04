package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.ClubPost;
import io.goose.shooting.domain.UserCollection;
import io.goose.shooting.domain.UserLike;
import io.goose.shooting.domain.details.ClubPostDetails;
import io.goose.shooting.service.IUserCollectionService;
import io.goose.shooting.service.IUserLikeService;
import io.goose.shooting.service.impl.ext.ClubPostServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 俱乐部动态 信息操作处理
 *
 * @author goose
 * @date 2020-05-09
 */
@RestController
@RequestMapping("/shooting/clubPost")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "俱乐部动态 ", description = "俱乐部动态")
public class ClubPostRestController extends ClubRestBaseController {

   @Autowired
   private ClubPostServiceImplExt clubPostService;

   @Autowired
   private IUserCollectionService userCollectionService;

   @Autowired
   private IUserLikeService userLikeService;

   /**
    * 查询俱乐部动态列表
    */
   @PostMapping("/list")
   @ApiOperation(value = "查询俱乐部动态列表")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubPost", value = "俱乐部动态",
         required = false, dataType = "ClubPost")})
   public TableDataInfo list(@RequestBody ClubPost clubPost) {
      startPage(clubPost.getPd());
      clubPost = setClubId(clubPost);
      Optional<Long> userId = getUserIdFromHeader();
      if (userId.isPresent()) {
         return getDataTable(clubPostService.selectClubPostSummaryList(userId.get()));
      } else {
         return getDataTable(clubPostService.selectClubPostList(clubPost));
      }
   }


   /**
    * 根据ID查询
    */
   @PostMapping("/getById/{id}")
   @ApiOperation(value = " 根据ID查询")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
         required = true, dataType = "Long")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public ClubPostDetails getById(@PathVariable("id") Long id) {
      return clubPostService.selectClubPostDetailsById(id);
   }

   @PostMapping("/getByIdAndUserId")
   public ClubPostDetails getByIdAndUserId(
         @RequestParam("userId") Long userId,
         @RequestParam("postId") Long postId
   ) {
      ClubPostDetails clubPostDetails = clubPostService.selectClubPostDetailsById(postId);

      UserLike userLike = new UserLike();
      userLike.setClientUserId(userId);
      userLike.setLikeId(postId);
      userLike.setLikeType("clubPost");
      clubPostDetails.setIsLike(!userLikeService.selectUserLikeList(userLike).isEmpty());

      UserCollection userCollection = new UserCollection();
      userCollection.setClientUserId(userId);
      userCollection.setCollectionId(postId);
      userCollection.setCollectionType("clubPost");
      clubPostDetails.setIsCollect(!userCollectionService.selectUserCollectionList(userCollection).isEmpty());

      return clubPostDetails;
   }


   /**
    * 根据CLUB ID查询
    */
   @PostMapping("/getByClubId/{clubId}")
   @ApiOperation(value = " 根据CLUB ID查询")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "clubId", value = "主键",
         required = true, dataType = "Long")})
   public TableDataInfo getByClubId(@PathVariable("clubId") Long clubId) {
      List<ClubPostDetails> list = clubPostService.selectClubPostDetailsListByClubId(clubId);
      return getDataTable(list);
   }

   /**
    * 导出俱乐部动态列表
    */
   /*
    * @PostMapping("/export")
    *
    * @ApiOperation(value="导出俱乐部动态列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="body", name = "clubPost", value = "俱乐部动态", required = false,
    * dataType = "ClubPost") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(ClubPost clubPost) { List<ClubPost> list =
    * clubPostService.selectClubPostList(clubPost); ExcelUtil<ClubPost> util = new
    * ExcelUtil<ClubPost>(ClubPost.class); return util.exportExcel(list, "clubPost"); }
    */

   /**
    * 导入俱乐部动态列表
    */
   /*
    * @PostMapping("/import")
    *
    * @ApiOperation(value="导入俱乐部动态列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="query", name = "file", value = "俱乐部动态文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<ClubPost> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubPost.class);
    * for(ClubPost obj : list) { clubPostService.insertClubPost(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    *
    * return AjaxResult.success("成功导入全部数据");
    *
    * }
    */


   /**
    * 新增保存俱乐部动态
    */
   @PostMapping("/add")
   @ApiOperation(value = "新增保存俱乐部动态")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubPost", value = "俱乐部动态",
         required = true, dataType = "ClubPost")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add(@RequestBody ClubPost clubPost) {
	   clubPost = setClubId(clubPost);
      return toAjax(clubPostService.insertClubPost(clubPost));
   }


   /**
    * 修改保存俱乐部动态
    */
   @PostMapping("/edit")
   @ApiOperation(value = "修改保存俱乐部动态")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubPost", value = "俱乐部动态",
         required = true, dataType = "ClubPost")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit(@RequestBody ClubPost clubPost) {
      return toAjax(clubPostService.updateClubPost(clubPost));
   }


   /**
    * 删除俱乐部动态
    */
   @PostMapping("/remove")
   @ApiOperation(value = "删除俱乐部动态")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove(String ids) {
      return toAjax(clubPostService.deleteClubPostByIds(ids));
   }


   /**
    * 根据id删除俱乐部动态
    */
   @PostMapping("/remove/{id}")
   @ApiOperation(value = "根据id删除俱乐部动态")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById(@PathVariable("id") Long id) {
      return toAjax(clubPostService.deleteClubPostById(id));
   }


   /**
    * 查询俱乐部动态分页列表
    */
   @PostMapping("/list/page")
   @ApiOperation(value = "查询俱乐部动态分页列表")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubPost", value = "俱乐部动态",
         required = true, dataType = "ClubPost")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage(@RequestBody ClubPost clubPost) {
      startPage(clubPost.getPd());
      clubPost = setClubId(clubPost);
      List<ClubPost> list = clubPostService.selectClubPostList(clubPost);
      return getDataTable(list);
   }

}
