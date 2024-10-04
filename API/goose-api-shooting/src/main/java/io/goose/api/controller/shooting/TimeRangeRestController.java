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
import io.goose.shooting.domain.TimeRange;
import io.goose.shooting.service.ITimeRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 时间段 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/timeRange" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "时间段 ", description = "时间段" )
public class TimeRangeRestController extends BaseController {

   @Autowired
   private ITimeRangeService timeRangeService;


   /**
    * 查询时间段列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询时间段列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "timeRange", value = "时间段",
         required = false, dataType = "TimeRange" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody TimeRange timeRange ) {
      startPage();

      List<TimeRange> list = timeRangeService.selectTimeRangeList( timeRange );
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
   public TimeRange getById( @PathVariable( "id" ) Long id ) {
      return timeRangeService.selectTimeRangeById( id );
   }

   /**
    * 导出时间段列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出时间段列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "timeRange", value = "时间段", required = false,
    * dataType = "TimeRange") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(TimeRange timeRange) { List<TimeRange> list =
    * timeRangeService.selectTimeRangeList(timeRange); ExcelUtil<TimeRange> util = new
    * ExcelUtil<TimeRange>(TimeRange.class); return util.exportExcel(list, "timeRange"); }
    */

   /**
    * 导入时间段列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入时间段列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "时间段文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<TimeRange> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), TimeRange.class);
    * for(TimeRange obj : list) { timeRangeService.insertTimeRange(obj); } }
    * catch(ExcelUtilException | IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存时间段
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存时间段" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "timeRange", value = "时间段",
         required = true, dataType = "TimeRange" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody TimeRange timeRange ) {
      return toAjax( timeRangeService.insertTimeRange( timeRange ) );
   }


   /**
    * 修改保存时间段
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存时间段" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "timeRange", value = "时间段",
         required = true, dataType = "TimeRange" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody TimeRange timeRange ) {
      return toAjax( timeRangeService.updateTimeRange( timeRange ) );
   }


   /**
    * 删除时间段
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除时间段" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( timeRangeService.deleteTimeRangeByIds( ids ) );
   }


   /**
    * 根据id删除时间段
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除时间段" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( timeRangeService.deleteTimeRangeById( id ) );
   }


   /**
    * 查询时间段分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询时间段分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "timeRange", value = "时间段",
         required = true, dataType = "TimeRange" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody TimeRange timeRange ) {
      startPage( timeRange.getPd() );
      List<TimeRange> list = timeRangeService.selectTimeRangeList( timeRange );
      return getDataTable( list );
   }

}
