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
import io.goose.shooting.domain.Area;
import io.goose.shooting.service.IAreaService;


/**
 * 地区 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/area" )
public class AreaController extends BaseController {

   private String prefix = "shooting/area";

   @Autowired
   private IAreaService areaService;


   @RequiresPermissions( "shooting:area:view" )
   @GetMapping( )
   public String area( ModelMap mmap ) {
      return prefix + "/area";
   }


   /**
    * 查询地区列表
    */
   @RequiresPermissions( "shooting:area:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Area area ) {
      startPage();
      List<Area> list = areaService.selectAreaListAssoc( area );
      return getDataTable( list );
   }


   /**
    * 导出地区列表
    */
   @RequiresPermissions( "shooting:area:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Area area ) {
      List<Area> list = areaService.selectAreaList( area );
      ExcelUtil<Area> util = new ExcelUtil<Area>( Area.class );
      return util.exportExcel( list, "area" );
   }


   /**
    * 导入地区列表
    */
   @RequiresPermissions( "shooting:area:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Area> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Area.class );
         for ( Area obj : list ) {
            areaService.insertArea( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增地区
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存地区
    */
   @RequiresPermissions( "shooting:area:add" )
   @Log( title = "地区", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Area area ) {

      area.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( areaService.insertArea( area ) );
   }


   /**
    * 修改地区
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Area area = areaService.selectAreaByIdAssoc( id );
      mmap.put( "area", area );
      return prefix + "/edit";
   }


   /**
    * 修改保存地区
    */
   @RequiresPermissions( "shooting:area:edit" )
   @Log( title = "地区", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Area area ) {
      area.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( areaService.updateArea( area ) );
   }


   /**
    * 删除地区
    */
   @RequiresPermissions( "shooting:area:remove" )
   @Log( title = "地区", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( areaService.deleteAreaByIds( ids ) );
   }


   /**
    * 查询地区分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Area area ) {
      startPage( area.getPd() );

      List<Area> list = areaService.selectAreaListAssoc( area );
      return getDataTable( list );
   }

}
