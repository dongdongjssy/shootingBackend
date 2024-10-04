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
import io.goose.shooting.domain.Judge;
import io.goose.shooting.service.IJudgeService;


/**
 * 裁判 信息操作处理
 * 
 * @author goose
 * @date 2020-05-21
 */
@Controller
@RequestMapping( "/shooting/judge" )
public class JudgeController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( JudgeController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/judge";

   @Autowired
   private IJudgeService judgeService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	
   @RequiresPermissions( "shooting:judge:view" )
   @GetMapping( )
   public String judge( ModelMap mmap ) {
      return prefix + "/judge";
   }


   /**
    * 查询裁判列表
    */
   @RequiresPermissions( "shooting:judge:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Judge judge ) {
      startPage();
      List<Judge> list = judgeService.selectJudgeListAssoc( judge );
      return getDataTable( list );
   }


   /**
    * 导出裁判列表
    */
   @RequiresPermissions( "shooting:judge:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Judge judge ) {
      List<Judge> list = judgeService.selectJudgeList( judge );
      ExcelUtil<Judge> util = new ExcelUtil<Judge>( Judge.class );
      return util.exportExcel( list, "judge" );
   }


   /**
    * 导入裁判列表
    */
   @RequiresPermissions( "shooting:judge:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Judge> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Judge.class );
         for ( Judge obj : list ) {
            judgeService.insertJudge( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增裁判
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存裁判
    */
   @RequiresPermissions( "shooting:judge:add" )
   @Log( title = "裁判", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Judge judge,
         @RequestParam( value = "avatarFile", required = false ) MultipartFile avatarFile ) {
      try {
         if ( avatarFile != null && !avatarFile.isEmpty() ) {
             String filePath = fileUploadDownloadService.upload(avatarFile, null,UploadTypeEnums.Judge.getValue() , true);
            judge.setAvatar( filePath );
         }
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      judge.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( judgeService.insertJudge( judge ) );
   }


   /**
    * 修改裁判
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Judge judge = judgeService.selectJudgeByIdAssoc( id );
      mmap.put( "judge", judge );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      return prefix + "/edit";
   }


   /**
    * 修改保存裁判
    */
   @RequiresPermissions( "shooting:judge:edit" )
   @Log( title = "裁判", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Judge judge,
         @RequestParam( value = "avatarFile", required = false ) MultipartFile avatarFile ) {
      try {
         if ( avatarFile != null && !avatarFile.isEmpty() ) {
             String filePath = fileUploadDownloadService.upload(avatarFile, null,UploadTypeEnums.Judge.getValue() , true);
            judge.setAvatar( filePath );
         }
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      judge.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( judgeService.updateJudge( judge ) );
   }


   /**
    * 删除裁判
    */
   @RequiresPermissions( "shooting:judge:remove" )
   @Log( title = "裁判", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( judgeService.deleteJudgeByIds( ids ) );
   }


   /**
    * 查询裁判分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Judge judge ) {
      startPage( judge.getPd() );

      List<Judge> list = judgeService.selectJudgeListAssoc( judge );
      return getDataTable( list );
   }
}
