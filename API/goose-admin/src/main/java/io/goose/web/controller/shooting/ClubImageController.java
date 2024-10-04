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
import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.ClubImage;
import io.goose.shooting.service.IClubImageService;
import io.goose.shooting.service.IClubService;


/**
 * 俱乐部图片 信息操作处理
 * 
 * @author goose
 * @date 2020-05-01
 */
@Controller
@RequestMapping( "/shooting/clubImage" )
public class ClubImageController extends ClubBaseController {

   private static final Logger log = LoggerFactory.getLogger( ClubImageController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/clubImage";

   @Autowired
   private IClubImageService clubImageService;

   @Autowired
   private IClubService clubService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

   @RequiresPermissions( "shooting:clubImage:view" )
   @GetMapping( )
   public String clubImage( ModelMap mmap ) {
	   
      mmap.put( "clubIdList", clubService.selectClubAll() );
      return prefix + "/clubImage";
   }


   /**
    * 查询俱乐部图片列表
    */
   @RequiresPermissions( "shooting:clubImage:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( ClubImage clubImage ) {
      startPage();
      clubImage = setClubId(clubImage);
      List<ClubImage> list = clubImageService.selectClubImageListAssoc( clubImage );
      return getDataTable( list );
   }


   /**
    * 导出俱乐部图片列表
    */
   @RequiresPermissions( "shooting:clubImage:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( ClubImage clubImage ) {
	   clubImage = setClubId(clubImage);
      List<ClubImage> list = clubImageService.selectClubImageList( clubImage );
      ExcelUtil<ClubImage> util = new ExcelUtil<ClubImage>( ClubImage.class );
      return util.exportExcel( list, "clubImage" );
   }


   /**
    * 导入俱乐部图片列表
    */
   @RequiresPermissions( "shooting:clubImage:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<ClubImage> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), ClubImage.class );
         for ( ClubImage clubImage : list ) {
        	 clubImage = setClubId(clubImage);
            clubImageService.insertClubImage( clubImage );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增俱乐部图片
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
	   Long clubId = getClubId();
	   mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      return prefix + "/add";
   }


   /**
    * 新增保存俱乐部图片
    */
   @RequiresPermissions( "shooting:clubImage:add" )
   @Log( title = "俱乐部图片", businessType = BusinessType.INSERT )
   @PostMapping( "/add{subPath}" )
   @ResponseBody
   public AjaxResult addSave( ClubImage clubImage, @PathVariable( "subPath" ) String subPath,
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
         uploadImages( clubImage, subPath, image1File, image2File, image3File, image4File,
               image5File, image6File, image7File, image8File, image9File );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      clubImage.setCreateBy( ShiroUtils.getLoginName() );
      clubImage = setClubId(clubImage);
      return toAjax( clubImageService.insertClubImage( clubImage ) );
   }


   /**
    * 修改俱乐部图片
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      ClubImage clubImage = clubImageService.selectClubImageByIdAssoc( id );
      mmap.put( "clubImage", clubImage );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      Long clubId = getClubId();
	  mmap.put("clubIdList", clubId!=null?clubService.selectClubById(clubId):clubService.selectClubAll());
      return prefix + "/edit";
   }


   /**
    * 修改保存俱乐部图片
    */
   @RequiresPermissions( "shooting:clubImage:edit" )
   @Log( title = "俱乐部图片", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit{subPath}" )
   @ResponseBody
   public AjaxResult editSave( ClubImage clubImage, @PathVariable( "subPath" ) String subPath,
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
         uploadImages( clubImage, subPath, image1File, image2File, image3File, image4File,
               image5File, image6File, image7File, image8File, image9File );
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      clubImage.setUpdateBy( ShiroUtils.getLoginName() );
      clubImage = setClubId(clubImage);
      return toAjax( clubImageService.updateClubImage( clubImage ) );
   }


   /**
    * 删除俱乐部图片
    */
   @RequiresPermissions( "shooting:clubImage:remove" )
   @Log( title = "俱乐部图片", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( clubImageService.deleteClubImageByIds( ids ) );
   }


   /**
    * 查询俱乐部图片分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody ClubImage clubImage ) {
      startPage( clubImage.getPd() );
      clubImage = setClubId(clubImage);
      List<ClubImage> list = clubImageService.selectClubImageListAssoc( clubImage );
      return getDataTable( list );
   }


   private void uploadImages( ClubImage clubImage, String subPath, MultipartFile image1File,
         MultipartFile image2File, MultipartFile image3File, MultipartFile image4File,
         MultipartFile image5File, MultipartFile image6File, MultipartFile image7File,
         MultipartFile image8File, MultipartFile image9File ) throws IOException {

      String filePath = fileUploadDownloadService.upload(image1File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage1( filePath );

      filePath = fileUploadDownloadService.upload(image2File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage2( filePath );

      filePath = fileUploadDownloadService.upload(image3File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage3( filePath );

      filePath = fileUploadDownloadService.upload(image4File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage4( filePath );

      filePath = fileUploadDownloadService.upload(image5File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage5( filePath );

      filePath = fileUploadDownloadService.upload(image6File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage6( filePath );

      filePath = fileUploadDownloadService.upload(image7File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage7( filePath );

      filePath = fileUploadDownloadService.upload(image8File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage8( filePath );

      filePath = fileUploadDownloadService.upload(image9File, null,UploadTypeEnums.Club.getValue() , true);
      clubImage.setImage9( filePath );
   }

}
