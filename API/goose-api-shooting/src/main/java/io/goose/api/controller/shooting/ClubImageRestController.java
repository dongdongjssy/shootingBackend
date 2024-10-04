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
import io.goose.shooting.domain.ClubImage;
import io.goose.shooting.service.impl.ext.ClubImageServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 俱乐部图片 信息操作处理
 * 
 * @author goose
 * @date 2020-05-01
 */
@RestController
@RequestMapping( "/shooting/clubImage" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "俱乐部图片 ", description = "俱乐部图片" )
public class ClubImageRestController extends ClubRestBaseController {

   @Autowired
   private ClubImageServiceImplExt clubImageService;


   /**
    * 查询俱乐部图片列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询俱乐部图片列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubImage", value = "俱乐部图片",
         required = false, dataType = "ClubImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody ClubImage clubImage ) {
      startPage();
      clubImage = setClubId(clubImage);
      List<ClubImage> list = clubImageService.selectClubImageList( clubImage );
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
   public ClubImage getById( @PathVariable( "id" ) Long id ) {
      return clubImageService.selectClubImageById( id );
   }


   /**
    * 根据Club ID查询
    */
   @PostMapping( "/getByClubId/{clubId}" )
   @ApiOperation( value = " 根据外键ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clubId", value = "外键",
         required = true, dataType = "Long" ) } )
   public List<String> getByClubId( @PathVariable( "clubId" ) Long clubId ) {
      return clubImageService.selectImageListByClubId( clubId );
   }

   /**
    * 导出俱乐部图片列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出俱乐部图片列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "clubImage", value = "俱乐部图片", required = false,
    * dataType = "ClubImage") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(ClubImage clubImage) { List<ClubImage> list =
    * clubImageService.selectClubImageList(clubImage); ExcelUtil<ClubImage> util = new
    * ExcelUtil<ClubImage>(ClubImage.class); return util.exportExcel(list, "clubImage"); }
    */

   /**
    * 导入俱乐部图片列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入俱乐部图片列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "俱乐部图片文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<ClubImage> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubImage.class);
    * for(ClubImage obj : list) { clubImageService.insertClubImage(obj); } }
    * catch(ExcelUtilException | IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存俱乐部图片
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存俱乐部图片" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubImage", value = "俱乐部图片",
         required = true, dataType = "ClubImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody ClubImage clubImage ) {
	   clubImage = setClubId(clubImage);
      return toAjax( clubImageService.insertClubImage( clubImage ) );
   }


   /**
    * 修改保存俱乐部图片
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存俱乐部图片" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubImage", value = "俱乐部图片",
         required = true, dataType = "ClubImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody ClubImage clubImage ) {
      return toAjax( clubImageService.updateClubImage( clubImage ) );
   }


   /**
    * 删除俱乐部图片
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除俱乐部图片" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( clubImageService.deleteClubImageByIds( ids ) );
   }


   /**
    * 根据id删除俱乐部图片
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除俱乐部图片" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( clubImageService.deleteClubImageById( id ) );
   }


   /**
    * 查询俱乐部图片分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询俱乐部图片分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubImage", value = "俱乐部图片",
         required = true, dataType = "ClubImage" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody ClubImage clubImage ) {
      startPage( clubImage.getPd() );
      clubImage = setClubId(clubImage);
      List<ClubImage> list = clubImageService.selectClubImageList( clubImage );
      return getDataTable( list );
   }

}
