package io.goose.api.controller.shooting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.CommentFeedback;
import io.goose.shooting.service.ICommentFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 回复 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/commentFeedback" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "回复 ", description = "回复" )
public class CommentFeedbackRestController extends ClubRestBaseController {

   @Autowired
   private ICommentFeedbackService commentFeedbackService;


   /**
    * 查询回复列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询回复列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "commentFeedback",
         value = "回复", required = false, dataType = "CommentFeedback" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody CommentFeedback commentFeedback ) {
      startPage();
      commentFeedback = setClubId(commentFeedback);
      List<CommentFeedback> list =
            commentFeedbackService.selectCommentFeedbackList( commentFeedback );
      return getDataTable( list );
   }


   /**
    * 根据ID查询
    */
   @PostMapping( "/getById/{id}" )
   @ApiOperation( value = " 根据ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "id", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public CommentFeedback getById( @PathVariable( "id" ) Long id ) {
      return commentFeedbackService.selectCommentFeedbackById( id );
   }

   /**
    * 导出回复列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出回复列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "commentFeedback", value = "回复", required = false,
    * dataType = "CommentFeedback") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public
    * AjaxResult export(CommentFeedback commentFeedback) { List<CommentFeedback> list =
    * commentFeedbackService.selectCommentFeedbackList(commentFeedback); ExcelUtil<CommentFeedback>
    * util = new ExcelUtil<CommentFeedback>(CommentFeedback.class); return util.exportExcel(list,
    * "commentFeedback"); }
    */

   /**
    * 导入回复列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入回复列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "回复文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<CommentFeedback> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
    * CommentFeedback.class); for(CommentFeedback obj : list) {
    * commentFeedbackService.insertCommentFeedback(obj); } } catch(ExcelUtilException | IOException
    * e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存回复
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存回复" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "commentFeedback",
         value = "回复", required = true, dataType = "CommentFeedback" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody CommentFeedback commentFeedback ) {
      return toAjax( commentFeedbackService.insertCommentFeedback( commentFeedback ) );
   }


   /**
    * 修改保存回复
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存回复" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "commentFeedback",
         value = "回复", required = true, dataType = "CommentFeedback" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody CommentFeedback commentFeedback ) {
      return toAjax( commentFeedbackService.updateCommentFeedback( commentFeedback ) );
   }


   /**
    * 删除回复
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除回复" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( commentFeedbackService.deleteCommentFeedbackByIds( ids ) );
   }


   /**
    * 根据id删除回复
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除回复" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( commentFeedbackService.deleteCommentFeedbackById( id ) );
   }


   /**
    * 查询回复分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询回复分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "commentFeedback",
         value = "回复", required = true, dataType = "CommentFeedback" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody CommentFeedback commentFeedback ) {
      startPage( commentFeedback.getPd() );
      commentFeedback = setClubId(commentFeedback);
      List<CommentFeedback> list =
            commentFeedbackService.selectCommentFeedbackList( commentFeedback );
      return getDataTable( list );
   }

}
