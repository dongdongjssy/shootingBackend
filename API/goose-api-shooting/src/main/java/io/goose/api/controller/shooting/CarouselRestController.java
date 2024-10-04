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
import io.goose.shooting.domain.Carousel;
import io.goose.shooting.service.ICarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 轮播媒体 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/carousel" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "轮播媒体 ", description = "轮播媒体" )
public class    CarouselRestController extends BaseController {

   @Autowired
   private ICarouselService carouselService;


   /**
    * 查询轮播媒体列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询轮播媒体列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "carousel", value = "轮播媒体",
         required = false, dataType = "Carousel" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Carousel carousel ) {
      startPage();

      List<Carousel> list = carouselService.selectCarouselList( carousel );
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
   public Carousel getById( @PathVariable( "id" ) Long id ) {
      return carouselService.selectCarouselById( id );
   }

   /**
    * 导出轮播媒体列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出轮播媒体列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "carousel", value = "轮播媒体", required = false,
    * dataType = "Carousel") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(Carousel carousel) { List<Carousel> list =
    * carouselService.selectCarouselList(carousel); ExcelUtil<Carousel> util = new
    * ExcelUtil<Carousel>(Carousel.class); return util.exportExcel(list, "carousel"); }
    */

   /**
    * 导入轮播媒体列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入轮播媒体列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "轮播媒体文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Carousel> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Carousel.class);
    * for(Carousel obj : list) { carouselService.insertCarousel(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存轮播媒体
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存轮播媒体" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "carousel", value = "轮播媒体",
         required = true, dataType = "Carousel" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Carousel carousel ) {
      return toAjax( carouselService.insertCarousel( carousel ) );
   }


   /**
    * 修改保存轮播媒体
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存轮播媒体" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "carousel", value = "轮播媒体",
         required = true, dataType = "Carousel" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Carousel carousel ) {
      return toAjax( carouselService.updateCarousel( carousel ) );
   }


   /**
    * 删除轮播媒体
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除轮播媒体" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( carouselService.deleteCarouselByIds( ids ) );
   }


   /**
    * 根据id删除轮播媒体
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除轮播媒体" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( carouselService.deleteCarouselById( id ) );
   }


   /**
    * 查询轮播媒体分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询轮播媒体分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "carousel", value = "轮播媒体",
         required = true, dataType = "Carousel" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Carousel carousel ) {
      startPage( carousel.getPd() );
      List<Carousel> list = carouselService.selectCarouselList( carousel );
      return getDataTable( list );
   }

}
