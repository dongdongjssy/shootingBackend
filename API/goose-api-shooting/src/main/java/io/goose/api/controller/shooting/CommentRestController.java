package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.ClubPost;
import io.goose.shooting.domain.Comment;
import io.goose.shooting.domain.Post;
import io.goose.shooting.domain.Recommend;
import io.goose.shooting.domain.details.CommentDetails;
import io.goose.shooting.service.IClubPostService;
import io.goose.shooting.service.IPostService;
import io.goose.shooting.service.IRecommendService;
import io.goose.shooting.service.impl.ext.CommentServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 评论 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/shooting/comment")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "评论 ", description = "评论")
public class CommentRestController extends ClubRestBaseController {

   @Autowired
   private CommentServiceImplExt commentService;

   /**
    * 查询评论列表
    */
   @PostMapping("/list")
   @ApiOperation(value = "查询评论列表")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "comment", value = "评论",
         required = false, dataType = "Comment")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list(@RequestBody Comment comment) {
      startPage();
      comment = setClubId(comment);
      List<Comment> list = commentService.selectCommentList(comment);
      return getDataTable(list);
   }

   /**
    * 查询评论列表
    */
   @PostMapping("/listAll")
   @ApiOperation(value = "查询所有评论以及回复")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "comment", value = "评论",
         required = false, dataType = "Comment")})
   public TableDataInfo listAll(@RequestBody Comment comment) {
      startPage(comment.getPd());
      comment = setClubId(comment);
      Optional<Long> recommendId = getIdFromHeader();
      if (recommendId.isPresent()) {
    	 comment.setFkId( recommendId.get() );
    	 comment.setFkTable( comment.getFkTable() );
         List<CommentDetails> list = commentService.selectCommentDetailsListPage(comment);
         return getDataTable(list);
      } else {
         return null;
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
   public Comment getById(@PathVariable("id") Long id) {
      return commentService.selectCommentById(id);
   }


   /**
    * 导出评论列表
    */
   /*
    * @PostMapping("/export")
    *
    * @ApiOperation(value="导出评论列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="body", name = "comment", value = "评论", required = false, dataType
    * = "Comment") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Comment
    * comment) { List<Comment> list = commentService.selectCommentList(comment); ExcelUtil<Comment>
    * util = new ExcelUtil<Comment>(Comment.class); return util.exportExcel(list, "comment"); }
    */

   /**
    * 导入评论列表
    */
   /*
    * @PostMapping("/import")
    *
    * @ApiOperation(value="导入评论列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="query", name = "file", value = "评论文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Comment> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Comment.class);
    * for(Comment obj : list) { commentService.insertComment(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    *
    * return AjaxResult.success("成功导入全部数据");
    *
    * }
    */


   /**
    * 新增保存评论
    */
   @PostMapping("/add")
   @ApiOperation(value = "新增保存评论")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "comment", value = "评论",
         required = true, dataType = "Comment")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add(@RequestBody Comment comment) {
	   comment = setClubId(comment);
      return toAjax(commentService.insertComment(comment));
   }


   /**
    * 修改保存评论
    */
   @PostMapping("/edit")
   @ApiOperation(value = "修改保存评论")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "comment", value = "评论",
         required = true, dataType = "Comment")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit(@RequestBody Comment comment) {
      return toAjax(commentService.updateComment(comment));
   }


   /**
    * 删除评论
    */
   @PostMapping("/remove")
   @ApiOperation(value = "删除评论")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove(String ids) {
      return toAjax(commentService.deleteCommentByIds(ids));
   }


   /**
    * 根据id删除评论
    */
   @PostMapping("/remove/{id}")
   @ApiOperation(value = "根据id删除评论")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById(@PathVariable("id") Long id) {
      return toAjax(commentService.deleteCommentById(id));
   }


   /**
    * 查询评论分页列表
    */
   @PostMapping("/list/page")
   @ApiOperation(value = "查询评论分页列表")
   @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "comment", value = "评论",
         required = true, dataType = "Comment")})
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage(@RequestBody Comment comment) {
      startPage(comment.getPd());
      comment = setClubId(comment);
      List<Comment> list = commentService.selectCommentList(comment);
      return getDataTable(list);
   }

}
