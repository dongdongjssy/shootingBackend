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
import io.goose.shooting.domain.PublishWithImage;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IPublishWithImageService;


/**
 * 图片发布 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/publishWithImage" )
public class PublishWithImageController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( PublishWithImageController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/publishWithImage";

   @Autowired
   private IPublishWithImageService publishWithImageService;

   @Autowired
   private IClientUserService clientUserService;
   
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

   @RequiresPermissions( "shooting:publishWithImage:view" )
   @GetMapping( )
   public String publishWithImage( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/publishWithImage";
   }


   /**
    * 查询图片发布列表
    */
   @RequiresPermissions( "shooting:publishWithImage:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( PublishWithImage publishWithImage ) {
      startPage();
      List<PublishWithImage> list =
            publishWithImageService.selectPublishWithImageListAssoc( publishWithImage );
      return getDataTable( list );
   }


   /**
    * 导出图片发布列表
    */
   @RequiresPermissions( "shooting:publishWithImage:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( PublishWithImage publishWithImage ) {
      List<PublishWithImage> list =
            publishWithImageService.selectPublishWithImageList( publishWithImage );
      ExcelUtil<PublishWithImage> util = new ExcelUtil<PublishWithImage>( PublishWithImage.class );
      return util.exportExcel( list, "publishWithImage" );
   }


   /**
    * 导入图片发布列表
    */
   @RequiresPermissions( "shooting:publishWithImage:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<PublishWithImage> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), PublishWithImage.class );
         for ( PublishWithImage obj : list ) {
            publishWithImageService.insertPublishWithImage( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增图片发布
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      return prefix + "/add";
   }


   /**
    * 新增保存图片发布
    */
   @RequiresPermissions( "shooting:publishWithImage:add" )
   @Log( title = "图片发布", businessType = BusinessType.INSERT )
   @PostMapping( "/add{subPath}" )
   @ResponseBody
   public AjaxResult addSave( PublishWithImage publishWithImage,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "image1File", required = false ) MultipartFile image1File,
         @RequestParam( value = "image2File", required = false ) MultipartFile image2File,
         @RequestParam( value = "image3File", required = false ) MultipartFile image3File,
         @RequestParam( value = "image4File", required = false ) MultipartFile image4File,
         @RequestParam( value = "image5File", required = false ) MultipartFile image5File,
         @RequestParam( value = "image6File", required = false ) MultipartFile image6File,
         @RequestParam( value = "image7File", required = false ) MultipartFile image7File,
         @RequestParam( value = "image8File", required = false ) MultipartFile image8File,
         @RequestParam( value = "image9File", required = false ) MultipartFile image9File ) {
      try {
         uploadImages( publishWithImage, subPath, image1File, image2File, image3File, image4File,
               image5File, image6File, image7File, image8File, image9File );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      publishWithImage.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( publishWithImageService.insertPublishWithImage( publishWithImage ) );
   }


   /**
    * 修改图片发布
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      PublishWithImage publishWithImage =
            publishWithImageService.selectPublishWithImageByIdAssoc( id );
      mmap.put( "publishWithImage", publishWithImage );
      mmap.put( "clientUserIdList", clientUserService.selectClientUserAll() );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      return prefix + "/edit";
   }


   /**
    * 修改保存图片发布
    */
   @RequiresPermissions( "shooting:publishWithImage:edit" )
   @Log( title = "图片发布", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit{subPath}" )
   @ResponseBody
   public AjaxResult editSave( PublishWithImage publishWithImage,
         @PathVariable( "subPath" ) String subPath,
         @RequestParam( value = "image1File", required = false ) MultipartFile image1File,
         @RequestParam( value = "image2File", required = false ) MultipartFile image2File,
         @RequestParam( value = "image3File", required = false ) MultipartFile image3File,
         @RequestParam( value = "image4File", required = false ) MultipartFile image4File,
         @RequestParam( value = "image5File", required = false ) MultipartFile image5File,
         @RequestParam( value = "image6File", required = false ) MultipartFile image6File,
         @RequestParam( value = "image7File", required = false ) MultipartFile image7File,
         @RequestParam( value = "image8File", required = false ) MultipartFile image8File,
         @RequestParam( value = "image9File", required = false ) MultipartFile image9File ) {

      try {

         uploadImages( publishWithImage, subPath, image1File, image2File, image3File, image4File,
               image5File, image6File, image7File, image8File, image9File );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      publishWithImage.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( publishWithImageService.updatePublishWithImage( publishWithImage ) );
   }


   /**
    * 删除图片发布
    */
   @RequiresPermissions( "shooting:publishWithImage:remove" )
   @Log( title = "图片发布", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( publishWithImageService.deletePublishWithImageByIds( ids ) );
   }


   /**
    * 查询图片发布分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody PublishWithImage publishWithImage ) {
      startPage( publishWithImage.getPd() );

      List<PublishWithImage> list =
            publishWithImageService.selectPublishWithImageListAssoc( publishWithImage );
      return getDataTable( list );
   }


   private void uploadImages( PublishWithImage post, String subPath, MultipartFile image1File,
         MultipartFile image2File, MultipartFile image3File, MultipartFile image4File,
         MultipartFile image5File, MultipartFile image6File, MultipartFile image7File,
         MultipartFile image8File, MultipartFile image9File ) throws IOException {

      String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage1( filePath );

      filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage2( filePath );

      filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage3( filePath );

      filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage4( filePath );

      filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage5( filePath );

      filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage6( filePath );

      filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage7( filePath );

      filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage8( filePath );

      filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Publish.getValue() , true);
      post.setImage9( filePath );
   }

}
