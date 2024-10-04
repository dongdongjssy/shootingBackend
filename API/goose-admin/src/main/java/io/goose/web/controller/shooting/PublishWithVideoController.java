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
import io.goose.shooting.domain.PublishWithVideo;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IPublishWithVideoService;


/**
 * 视频发布 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/publishWithVideo" )
public class PublishWithVideoController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( PublishWithVideoController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/publishWithVideo";

   @Autowired
   private IPublishWithVideoService publishWithVideoService;

   @Autowired
   private IClientUserService clientUserService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

   @RequiresPermissions( "shooting:publishWithVideo:view" )
   @GetMapping( )
   public String publishWithVideo( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/publishWithVideo";
   }


   /**
    * 查询视频发布列表
    */
   @RequiresPermissions( "shooting:publishWithVideo:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( PublishWithVideo publishWithVideo ) {
      startPage();
      List<PublishWithVideo> list =
            publishWithVideoService.selectPublishWithVideoListAssoc( publishWithVideo );
      return getDataTable( list );
   }


   /**
    * 导出视频发布列表
    */
   @RequiresPermissions( "shooting:publishWithVideo:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( PublishWithVideo publishWithVideo ) {
      List<PublishWithVideo> list =
            publishWithVideoService.selectPublishWithVideoList( publishWithVideo );
      ExcelUtil<PublishWithVideo> util = new ExcelUtil<PublishWithVideo>( PublishWithVideo.class );
      return util.exportExcel( list, "publishWithVideo" );
   }


   /**
    * 导入视频发布列表
    */
   @RequiresPermissions( "shooting:publishWithVideo:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<PublishWithVideo> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), PublishWithVideo.class );
         for ( PublishWithVideo obj : list ) {
            publishWithVideoService.insertPublishWithVideo( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增视频发布
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/add";
   }


   /**
    * 新增保存视频发布
    */
   @RequiresPermissions( "shooting:publishWithVideo:add" )
   @Log( title = "视频发布", businessType = BusinessType.INSERT )
   @PostMapping( "/add{subPath}" )
   @ResponseBody
   public AjaxResult addSave( PublishWithVideo publishWithVideo,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "videoFile", required = false ) MultipartFile videoFile ) {

      try {
          String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Publish.getValue() , true);
         publishWithVideo.setVideo( filePath );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      publishWithVideo.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( publishWithVideoService.insertPublishWithVideo( publishWithVideo ) );
   }


   /**
    * 修改视频发布
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      PublishWithVideo publishWithVideo =
            publishWithVideoService.selectPublishWithVideoByIdAssoc( id );
      mmap.put( "publishWithVideo", publishWithVideo );
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      return prefix + "/edit";
   }


   /**
    * 修改保存视频发布
    */
   @RequiresPermissions( "shooting:publishWithVideo:edit" )
   @Log( title = "视频发布", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit{subPath}" )
   @ResponseBody
   public AjaxResult editSave( PublishWithVideo publishWithVideo,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "videoFile", required = false ) MultipartFile videoFile ) {

      try {

          String filePath = fileUploadDownloadService.upload(videoFile, null,UploadTypeEnums.Publish.getValue() , true);

         publishWithVideo.setVideo( filePath );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      publishWithVideo.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( publishWithVideoService.updatePublishWithVideo( publishWithVideo ) );
   }


   /**
    * 删除视频发布
    */
   @RequiresPermissions( "shooting:publishWithVideo:remove" )
   @Log( title = "视频发布", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( publishWithVideoService.deletePublishWithVideoByIds( ids ) );
   }


   /**
    * 查询视频发布分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody PublishWithVideo publishWithVideo ) {
      startPage( publishWithVideo.getPd() );

      List<PublishWithVideo> list =
            publishWithVideoService.selectPublishWithVideoListAssoc( publishWithVideo );
      return getDataTable( list );
   }

}
