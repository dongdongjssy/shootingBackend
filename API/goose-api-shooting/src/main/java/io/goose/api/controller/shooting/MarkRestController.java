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
import io.goose.shooting.domain.Mark;
import io.goose.shooting.service.IMarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 成绩 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/mark" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "成绩 ", description = "成绩" )
public class MarkRestController extends BaseController {

   @Autowired
   private IMarkService markService;


   /**
    * 查询成绩列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询成绩列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "mark", value = "成绩",
         required = false, dataType = "Mark" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Mark mark ) {
      startPage();

      List<Mark> list = markService.selectMarkList( mark );
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
   public Mark getById( @PathVariable( "id" ) Long id ) {
      return markService.selectMarkById( id );
   }

   /**
    * 导出成绩列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出成绩列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "mark", value = "成绩", required = false, dataType =
    * "Mark") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Mark mark) {
    * List<Mark> list = markService.selectMarkList(mark); ExcelUtil<Mark> util = new
    * ExcelUtil<Mark>(Mark.class); return util.exportExcel(list, "mark"); }
    */

   /**
    * 导入成绩列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入成绩列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "成绩文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Mark>
    * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Mark.class); for(Mark obj : list)
    * { markService.insertMark(obj); } } catch(ExcelUtilException | IOException e) { return
    * AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存成绩
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存成绩" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "mark", value = "成绩",
         required = true, dataType = "Mark" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Mark mark ) {
      return toAjax( markService.insertMark( mark ) );
   }


   /**
    * 修改保存成绩
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存成绩" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "mark", value = "成绩",
         required = true, dataType = "Mark" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Mark mark ) {
      return toAjax( markService.updateMark( mark ) );
   }


   /**
    * 删除成绩
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除成绩" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( markService.deleteMarkByIds( ids ) );
   }


   /**
    * 根据id删除成绩
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除成绩" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( markService.deleteMarkById( id ) );
   }


   /**
    * 查询成绩分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询成绩分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "mark", value = "成绩",
         required = true, dataType = "Mark" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Mark mark ) {
      startPage( mark.getPd() );
      List<Mark> list = markService.selectMarkList( mark );
      return getDataTable( list );
   }

}
