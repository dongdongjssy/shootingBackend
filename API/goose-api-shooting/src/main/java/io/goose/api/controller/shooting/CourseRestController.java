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
import io.goose.shooting.domain.Course;
import io.goose.shooting.service.ICourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 科目 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/course" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "科目 ", description = "科目" )
public class CourseRestController extends BaseController {

   @Autowired
   private ICourseService courseService;


   /**
    * 查询科目列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询科目列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "course", value = "科目",
         required = false, dataType = "Course" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Course course ) {
      startPage();

      List<Course> list = courseService.selectCourseList( course );
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
   public Course getById( @PathVariable( "id" ) Long id ) {
      return courseService.selectCourseById( id );
   }

   /**
    * 导出科目列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出科目列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "course", value = "科目", required = false, dataType
    * = "Course") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Course
    * course) { List<Course> list = courseService.selectCourseList(course); ExcelUtil<Course> util =
    * new ExcelUtil<Course>(Course.class); return util.exportExcel(list, "course"); }
    */

   /**
    * 导入科目列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入科目列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "科目文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Course> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Course.class);
    * for(Course obj : list) { courseService.insertCourse(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存科目
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存科目" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "course", value = "科目",
         required = true, dataType = "Course" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Course course ) {
      return toAjax( courseService.insertCourse( course ) );
   }


   /**
    * 修改保存科目
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存科目" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "course", value = "科目",
         required = true, dataType = "Course" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Course course ) {
      return toAjax( courseService.updateCourse( course ) );
   }


   /**
    * 删除科目
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除科目" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( courseService.deleteCourseByIds( ids ) );
   }


   /**
    * 根据id删除科目
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除科目" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( courseService.deleteCourseById( id ) );
   }


   /**
    * 查询科目分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询科目分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "course", value = "科目",
         required = true, dataType = "Course" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Course course ) {
      startPage( course.getPd() );
      List<Course> list = courseService.selectCourseList( course );
      return getDataTable( list );
   }

}
