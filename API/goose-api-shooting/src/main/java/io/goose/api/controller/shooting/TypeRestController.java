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
import io.goose.shooting.domain.Type;
import io.goose.shooting.service.ITypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 类别 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/type" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "类别 ", description = "类别" )
public class TypeRestController extends BaseController {

   @Autowired
   private ITypeService typeService;


   /**
    * 查询类别列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询类别列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "type", value = "类别",
         required = false, dataType = "Type" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Type type ) {
      startPage();

      List<Type> list = typeService.selectTypeList( type );
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
   public Type getById( @PathVariable( "id" ) Long id ) {
      return typeService.selectTypeById( id );
   }

   /**
    * 导出类别列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出类别列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "type", value = "类别", required = false, dataType =
    * "Type") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Type type) {
    * List<Type> list = typeService.selectTypeList(type); ExcelUtil<Type> util = new
    * ExcelUtil<Type>(Type.class); return util.exportExcel(list, "type"); }
    */

   /**
    * 导入类别列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入类别列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "类别文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Type>
    * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Type.class); for(Type obj : list)
    * { typeService.insertType(obj); } } catch(ExcelUtilException | IOException e) { return
    * AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存类别
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存类别" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "type", value = "类别",
         required = true, dataType = "Type" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Type type ) {
      return toAjax( typeService.insertType( type ) );
   }


   /**
    * 修改保存类别
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存类别" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "type", value = "类别",
         required = true, dataType = "Type" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Type type ) {
      return toAjax( typeService.updateType( type ) );
   }


   /**
    * 删除类别
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除类别" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( typeService.deleteTypeByIds( ids ) );
   }


   /**
    * 根据id删除类别
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除类别" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( typeService.deleteTypeById( id ) );
   }


   /**
    * 查询类别分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询类别分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "type", value = "类别",
         required = true, dataType = "Type" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Type type ) {
      startPage( type.getPd() );
      List<Type> list = typeService.selectTypeList( type );
      return getDataTable( list );
   }

}