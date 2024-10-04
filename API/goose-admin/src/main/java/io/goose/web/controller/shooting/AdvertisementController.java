package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.goose.common.config.Global;
import io.goose.common.enums.BusinessType;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Advertisement;
import io.goose.shooting.service.IAdvertisementService;


/**
 * 广告 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/advertisement" )
public class AdvertisementController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( AdvertisementController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/advertisement";

   @Autowired
   private IAdvertisementService advertisementService;
   
   @Autowired
   private FileUploadDownloadOSSService ossService;

   @Autowired
   private FileUploadDownloadOSSService fileUploadDownloadService;


   @RequiresPermissions( "shooting:advertisement:view" )
   @GetMapping( )
   public String advertisement( ModelMap mmap ) {
      return prefix + "/advertisement";
   }


   /**
    * 查询广告列表
    */
   @RequiresPermissions( "shooting:advertisement:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Advertisement advertisement ) {
      startPage();
      List<Advertisement> list = advertisementService.selectAdvertisementListAssoc( advertisement );
      return getDataTable( list );
   }


   /**
    * 导出广告列表
    */
   @RequiresPermissions( "shooting:advertisement:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Advertisement advertisement ) {
      List<Advertisement> list = advertisementService.selectAdvertisementList( advertisement );
      ExcelUtil<Advertisement> util = new ExcelUtil<Advertisement>( Advertisement.class );
      return util.exportExcel( list, "advertisement" );
   }


   /**
    * 导入广告列表
    */
   @RequiresPermissions( "shooting:advertisement:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Advertisement> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Advertisement.class );
         for ( Advertisement obj : list ) {
            advertisementService.insertAdvertisement( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增广告
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存广告
    */
   @RequiresPermissions( "shooting:advertisement:add" )
   @Log( title = "广告", businessType = BusinessType.INSERT )
   @PostMapping( "/add{subPath}" )
   @ResponseBody
   public AjaxResult addSave( Advertisement advertisement,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "mediaUrlFile", required = false ) MultipartFile mediaUrlFile ) {

      try {
    	  if(mediaUrlFile != null) {
    		  String filePath = ossService.upload(mediaUrlFile, null,  UploadTypeEnums.Advertisement.getValue(), true);
    	         advertisement.setMediaUrl( filePath );
    	  }
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      advertisement.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( advertisementService.insertAdvertisement( advertisement ) );
   }


   /**
    * 修改广告
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Advertisement advertisement = advertisementService.selectAdvertisementByIdAssoc( id );
      mmap.put( "advertisement", advertisement );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      return prefix + "/edit";
   }


   /**
    * 修改保存广告
    */
   @RequiresPermissions( "shooting:advertisement:edit" )
   @Log( title = "广告", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit{subPath}" )
   @ResponseBody
   public AjaxResult editSave( Advertisement advertisement,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "mediaUrlFile", required = false ) MultipartFile mediaUrlFile ) {
      try {
         String filePath = fileUploadDownloadService.upload(mediaUrlFile, null, UploadTypeEnums.Contest.getValue(), true);
/*
         String filePath =
               FileUploadUtils.uploadMediaFile( global.getMediaPathAdvertisement(), mediaUrlFile );*/
         advertisement.setMediaUrl( filePath );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      advertisement.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( advertisementService.updateAdvertisement( advertisement ) );
   }


   /**
    * 删除广告
    */
   @RequiresPermissions( "shooting:advertisement:remove" )
   @Log( title = "广告", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( advertisementService.deleteAdvertisementByIds( ids ) );
   }


   /**
    * 查询广告分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Advertisement advertisement ) {
      startPage( advertisement.getPd() );

      List<Advertisement> list = advertisementService.selectAdvertisementListAssoc( advertisement );
      return getDataTable( list );
   }

}
