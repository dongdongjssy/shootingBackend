package io.goose.api.controller.shooting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Area;
import io.goose.shooting.service.IAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 地区 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/area" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "地区 ", description = "地区" )
public class AreaRestController extends BaseController {

   @Autowired
   private IAreaService areaService;


   /**
    * 查询地区列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询地区列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "area", value = "地区",
         required = false, dataType = "Area" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Area area ) {
      startPage();

      List<Area> list = areaService.selectAreaList( area );
      return getDataTable( list );
   }

   @GetMapping( "/asd" )
   @ResponseBody
   public String asd() {
      return "啥啊大是大非";
   }

   /**
    * 根据ID查询
    */
   @PostMapping( "/getById/{id}" )
   @ApiOperation( value = " 根据ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "id", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public Area getById( @PathVariable( "id" ) Long id ) {
      return areaService.selectAreaById( id );
   }

   /**
    * 导出地区列表
    */
   /*
    * @PostMapping("/export")
    *
    * @ApiOperation(value="导出地区列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="body", name = "area", value = "地区", required = false, dataType =
    * "Area") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Area area) {
    * List<Area> list = areaService.selectAreaList(area); ExcelUtil<Area> util = new
    * ExcelUtil<Area>(Area.class); return util.exportExcel(list, "area"); }
    */

   /**
    * 导入地区列表
    */
   /*
    * @PostMapping("/import")
    *
    * @ApiOperation(value="导入地区列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="query", name = "file", value = "地区文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Area>
    * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Area.class); for(Area obj : list)
    * { areaService.insertArea(obj); } } catch(ExcelUtilException | IOException e) { return
    * AjaxResult.error(e.getMessage()); }
    *
    * return AjaxResult.success("成功导入全部数据");
    *
    * }
    */


   /**
    * 新增保存地区
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存地区" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "area", value = "地区",
         required = true, dataType = "Area" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Area area ) {
      return toAjax( areaService.insertArea( area ) );
   }


   /**
    * 修改保存地区
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存地区" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "area", value = "地区",
         required = true, dataType = "Area" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Area area ) {
      return toAjax( areaService.updateArea( area ) );
   }


   /**
    * 删除地区
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除地区" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( areaService.deleteAreaByIds( ids ) );
   }


   /**
    * 根据id删除地区
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除地区" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( areaService.deleteAreaById( id ) );
   }


   /**
    * 查询地区分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询地区分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "area", value = "地区",
         required = true, dataType = "Area" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Area area ) {
      startPage( area.getPd() );
      List<Area> list = areaService.selectAreaList( area );
      return getDataTable( list );
   }

}
