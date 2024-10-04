package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Course;
import io.goose.shooting.service.ICourseService;


/**
 * 科目 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@Controller
@RequestMapping( "/shooting/course" )
public class CourseController extends BaseController {

   private String prefix = "shooting/course";

   @Autowired
   private ICourseService courseService;


   @RequiresPermissions( "shooting:course:view" )
   @GetMapping( )
   public String course( ModelMap mmap ) {
      return prefix + "/course";
   }


   /**
    * 查询科目列表
    */
   @RequiresPermissions( "shooting:course:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Course course ) {
      startPage();
      List<Course> list = courseService.selectCourseListAssoc( course );
      return getDataTable( list );
   }


   /**
    * 导出科目列表
    */
   @RequiresPermissions( "shooting:course:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Course course ) {
      List<Course> list = courseService.selectCourseList( course );
      ExcelUtil<Course> util = new ExcelUtil<Course>( Course.class );
      return util.exportExcel( list, "course" );
   }


   /**
    * 导入科目列表
    */
   @RequiresPermissions( "shooting:course:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Course> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Course.class );
         for ( Course obj : list ) {
            courseService.insertCourse( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增科目
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存科目
    */
   @RequiresPermissions( "shooting:course:add" )
   @Log( title = "科目", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Course course ) {

      course.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( courseService.insertCourse( course ) );
   }


   /**
    * 修改科目
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Long id, ModelMap mmap ) {
      Course course = courseService.selectCourseByIdAssoc( id );
      mmap.put( "course", course );
      return prefix + "/edit";
   }


   /**
    * 修改保存科目
    */
   @RequiresPermissions( "shooting:course:edit" )
   @Log( title = "科目", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Course course ) {
      course.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( courseService.updateCourse( course ) );
   }


   /**
    * 删除科目
    */
   @RequiresPermissions( "shooting:course:remove" )
   @Log( title = "科目", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( courseService.deleteCourseByIds( ids ) );
   }


   /**
    * 查询科目分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Course course ) {
      startPage( course.getPd() );

      List<Course> list = courseService.selectCourseListAssoc( course );
      return getDataTable( list );
   }

}
