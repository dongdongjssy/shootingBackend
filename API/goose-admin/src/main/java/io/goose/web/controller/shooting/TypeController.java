package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Type;
import io.goose.shooting.service.ITypeService;


/**
 * 类别 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/type" )
public class TypeController extends BaseController {

   private String prefix = "shooting/type";

   @Autowired
   private ITypeService typeService;


   @RequiresPermissions( "shooting:type:view" )
   @GetMapping( )
   public String type( ModelMap mmap ) {
      return prefix + "/type";
   }


   /**
    * 查询类别列表
    */
   @RequiresPermissions( "shooting:type:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Type type ) {
      startPage();
      List<Type> list = typeService.selectTypeListAssoc( type );
      return getDataTable( list );
   }


   /**
    * 导出类别列表
    */
   @RequiresPermissions( "shooting:type:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Type type ) {
      List<Type> list = typeService.selectTypeList( type );
      ExcelUtil<Type> util = new ExcelUtil<Type>( Type.class );
      return util.exportExcel( list, "type" );
   }


   /**
    * 导入类别列表
    */
   @RequiresPermissions( "shooting:type:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Type> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Type.class );
         for ( Type obj : list ) {
            typeService.insertType( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增类别
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存类别
    */
   @RequiresPermissions( "shooting:type:add" )
   @Log( title = "类别", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Type type ) {

      type.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( typeService.insertType( type ) );
   }


   /**
    * 修改类别
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Type type = typeService.selectTypeByIdAssoc( id );
      mmap.put( "type", type );
      return prefix + "/edit";
   }


   /**
    * 修改保存类别
    */
   @RequiresPermissions( "shooting:type:edit" )
   @Log( title = "类别", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Type type ) {
      type.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( typeService.updateType( type ) );
   }


   /**
    * 删除类别
    */
   @RequiresPermissions( "shooting:type:remove" )
   @Log( title = "类别", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( typeService.deleteTypeByIds( ids ) );
   }


   /**
    * 查询类别分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Type type ) {
      startPage( type.getPd() );

      List<Type> list = typeService.selectTypeListAssoc( type );
      return getDataTable( list );
   }

}
