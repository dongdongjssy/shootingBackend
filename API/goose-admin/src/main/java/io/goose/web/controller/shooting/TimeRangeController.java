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
import io.goose.shooting.domain.TimeRange;
import io.goose.shooting.service.ITimeRangeService;


/**
 * 时间段 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/timeRange" )
public class TimeRangeController extends BaseController {

   private String prefix = "shooting/timeRange";

   @Autowired
   private ITimeRangeService timeRangeService;


   @RequiresPermissions( "shooting:timeRange:view" )
   @GetMapping( )
   public String timeRange( ModelMap mmap ) {
      return prefix + "/timeRange";
   }


   /**
    * 查询时间段列表
    */
   @RequiresPermissions( "shooting:timeRange:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( TimeRange timeRange ) {
      startPage();
      List<TimeRange> list = timeRangeService.selectTimeRangeListAssoc( timeRange );
      return getDataTable( list );
   }


   /**
    * 导出时间段列表
    */
   @RequiresPermissions( "shooting:timeRange:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( TimeRange timeRange ) {
      List<TimeRange> list = timeRangeService.selectTimeRangeList( timeRange );
      ExcelUtil<TimeRange> util = new ExcelUtil<TimeRange>( TimeRange.class );
      return util.exportExcel( list, "timeRange" );
   }


   /**
    * 导入时间段列表
    */
   @RequiresPermissions( "shooting:timeRange:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<TimeRange> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), TimeRange.class );
         for ( TimeRange obj : list ) {
            timeRangeService.insertTimeRange( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增时间段
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存时间段
    */
   @RequiresPermissions( "shooting:timeRange:add" )
   @Log( title = "时间段", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( TimeRange timeRange ) {

      timeRange.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( timeRangeService.insertTimeRange( timeRange ) );
   }


   /**
    * 修改时间段
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      TimeRange timeRange = timeRangeService.selectTimeRangeByIdAssoc( id );
      mmap.put( "timeRange", timeRange );
      return prefix + "/edit";
   }


   /**
    * 修改保存时间段
    */
   @RequiresPermissions( "shooting:timeRange:edit" )
   @Log( title = "时间段", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( TimeRange timeRange ) {
      timeRange.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( timeRangeService.updateTimeRange( timeRange ) );
   }


   /**
    * 删除时间段
    */
   @RequiresPermissions( "shooting:timeRange:remove" )
   @Log( title = "时间段", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( timeRangeService.deleteTimeRangeByIds( ids ) );
   }


   /**
    * 查询时间段分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody TimeRange timeRange ) {
      startPage( timeRange.getPd() );

      List<TimeRange> list = timeRangeService.selectTimeRangeListAssoc( timeRange );
      return getDataTable( list );
   }

}
