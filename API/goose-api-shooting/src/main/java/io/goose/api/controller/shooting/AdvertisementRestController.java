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
import io.goose.shooting.domain.Advertisement;
import io.goose.shooting.service.IAdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 广告 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/advertisement" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "广告 ", description = "广告" )
public class AdvertisementRestController extends BaseController {

   @Autowired
   private IAdvertisementService advertisementService;


   /**
    * 查询广告列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询广告列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "advertisement",
         value = "广告", required = false, dataType = "Advertisement" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Advertisement advertisement ) {
      startPage();

      List<Advertisement> list = advertisementService.selectAdvertisementList( advertisement );
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
   public Advertisement getById( @PathVariable( "id" ) Long id ) {
      return advertisementService.selectAdvertisementById( id );
   }

   /**
    * 导出广告列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出广告列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "advertisement", value = "广告", required = false,
    * dataType = "Advertisement") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(Advertisement advertisement) { List<Advertisement> list =
    * advertisementService.selectAdvertisementList(advertisement); ExcelUtil<Advertisement> util =
    * new ExcelUtil<Advertisement>(Advertisement.class); return util.exportExcel(list,
    * "advertisement"); }
    */

   /**
    * 导入广告列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入广告列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "广告文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Advertisement> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
    * Advertisement.class); for(Advertisement obj : list) {
    * advertisementService.insertAdvertisement(obj); } } catch(ExcelUtilException | IOException e) {
    * return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存广告
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存广告" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "advertisement",
         value = "广告", required = true, dataType = "Advertisement" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Advertisement advertisement ) {
      return toAjax( advertisementService.insertAdvertisement( advertisement ) );
   }


   /**
    * 修改保存广告
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存广告" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "advertisement",
         value = "广告", required = true, dataType = "Advertisement" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Advertisement advertisement ) {
      return toAjax( advertisementService.updateAdvertisement( advertisement ) );
   }


   /**
    * 删除广告
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除广告" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( advertisementService.deleteAdvertisementByIds( ids ) );
   }


   /**
    * 根据id删除广告
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除广告" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( advertisementService.deleteAdvertisementById( id ) );
   }


   /**
    * 查询广告分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询广告分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "advertisement",
         value = "广告", required = true, dataType = "Advertisement" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Advertisement advertisement ) {
      startPage( advertisement.getPd() );
      List<Advertisement> list = advertisementService.selectAdvertisementList( advertisement );
      return getDataTable( list );
   }

}
