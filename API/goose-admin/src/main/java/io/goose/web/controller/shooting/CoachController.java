package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Club;
import io.goose.system.domain.SysDictData;
import io.goose.system.service.ISysDictDataService;
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
import io.goose.shooting.domain.Coach;
import io.goose.shooting.service.ICoachService;


/**
 * 教官 信息操作处理
 * 
 * @author goose
 * @date 2020-05-21
 */
@Controller
@RequestMapping( "/shooting/coach" )
public class CoachController extends BaseController {

   private static final Logger log = LoggerFactory.getLogger( CoachController.class );

   @Autowired
   private Global global;

   private String prefix = "shooting/coach";

   @Autowired
   private ICoachService coachService;
   @Autowired
   private ISysDictDataService dictDataService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	
   @RequiresPermissions( "shooting:coach:view" )
   @GetMapping( )
   public String coach( ModelMap mmap ) {
      return prefix + "/coach";
   }


   /**
    * 查询教官列表
    */
   @RequiresPermissions( "shooting:coach:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Coach coach ) {
      startPage();
      List<Coach> list = coachService.selectCoachListAssoc( coach );
      return getDataTable( list );
   }


   /**
    * 导出教官列表
    */
   @RequiresPermissions( "shooting:coach:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Coach coach ) {
      List<Coach> list = coachService.selectCoachList( coach );
      for(int i =0;i<list.size();i++) {
         List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
         List<SysDictData> category = dictDataService.selectDictDataByType("shooting_coach_category");

         for(SysDictData d : status) {
            Integer aS = list.get(i).getStatus();
            if(aS!=null) {
               if(d.getDictValue().equals(aS.toString())) {
                  list.get(i).setStatusName(d.getDictLabel());
               }
            }
         }

         for(SysDictData d:category){
            if(list.get(i).getCategory()!=null) {
               if(d.getDictValue().equals(list.get(i).getCategory())) {
                  list.get(i).setCategoryName(d.getDictLabel());
               }
            }
         }
      }
      ExcelUtil<Coach> util = new ExcelUtil<Coach>( Coach.class );
      return util.exportExcel( list, "coach" );
   }


   /**
    * 导入教官列表
    */
   @RequiresPermissions( "shooting:coach:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Coach> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Coach.class );
         for ( Coach obj : list ) {
            coachService.insertCoach( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增教官
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存教官
    */
   @RequiresPermissions( "shooting:coach:add" )
   @Log( title = "教官", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Coach coach,
         @RequestParam( value = "avatarFile", required = false ) MultipartFile avatarFile ) {
      try {
         if ( avatarFile != null && !avatarFile.isEmpty() ) {
             String filePath = fileUploadDownloadService.upload(avatarFile, null,UploadTypeEnums.Coach.getValue() , true);
            coach.setAvatar( filePath );
         }
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }

      coach.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( coachService.insertCoach( coach ) );
   }


   /**
    * 修改教官
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Coach coach = coachService.selectCoachByIdAssoc( id );
      mmap.put( "coach", coach );
      mmap.put( "imageUrlPrefix", getImageUrlPrefix() );
      return prefix + "/edit";
   }


   /**
    * 修改保存教官
    */
   @RequiresPermissions( "shooting:coach:edit" )
   @Log( title = "教官", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Coach coach,
         @RequestParam( value = "avatarFile", required = false ) MultipartFile avatarFile ) {
      try {
         if ( avatarFile != null && !avatarFile.isEmpty() ) {
             String filePath = fileUploadDownloadService.upload(avatarFile, null,UploadTypeEnums.Coach.getValue() , true);
            coach.setAvatar( filePath );
         }
      } catch ( Exception e ) {
         log.error( "图像上传失败！", e );
         return error( e.getMessage() );
      }
      coach.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( coachService.updateCoach( coach ) );
   }


   /**
    * 删除教官
    */
   @RequiresPermissions( "shooting:coach:remove" )
   @Log( title = "教官", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( coachService.deleteCoachByIds( ids ) );
   }


   /**
    * 查询教官分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Coach coach ) {
      startPage( coach.getPd() );

      List<Coach> list = coachService.selectCoachListAssoc( coach );
      return getDataTable( list );
   }

}
