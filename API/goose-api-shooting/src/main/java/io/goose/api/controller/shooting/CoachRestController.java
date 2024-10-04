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
import io.goose.shooting.domain.Coach;
import io.goose.shooting.service.impl.ext.CoachServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 教官 信息操作处理
 * 
 * @author goose
 * @date 2020-05-21
 */
@RestController
@RequestMapping( "/shooting/coach" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "教官 ", description = "教官" )
public class CoachRestController extends BaseController {

   @Autowired
   private CoachServiceImplExt coachService;


   /**
    * 查询教官列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询教官列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "coach", value = "教官",
         required = false, dataType = "Coach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Coach coach ) {
      startPage();

      List<Coach> list = coachService.selectCoachList( coach );
      return getDataTable( list );
   }


   /**
    * 查询教官列表
    */
   @PostMapping( "/groupList" )
   @ApiOperation( value = "查询教官列表" )
   public List<?> groupList() {
      startPage();

      List<?> list = coachService.selectCoachGroupList();
      return list;
   }


   /**
    * 根据ID查询
    */
   @PostMapping( "/getById/{id}" )
   @ApiOperation( value = " 根据ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "id", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public Coach getById( @PathVariable( "id" ) Long id ) {
      return coachService.selectCoachById( id );
   }

   /**
    * 导出教官列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出教官列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "coach", value = "教官", required = false, dataType =
    * "Coach") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Coach
    * coach) { List<Coach> list = coachService.selectCoachList(coach); ExcelUtil<Coach> util = new
    * ExcelUtil<Coach>(Coach.class); return util.exportExcel(list, "coach"); }
    */

   /**
    * 导入教官列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入教官列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "教官文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Coach> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Coach.class);
    * for(Coach obj : list) { coachService.insertCoach(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存教官
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "coach", value = "教官",
         required = true, dataType = "Coach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Coach coach ) {
      return toAjax( coachService.insertCoach( coach ) );
   }


   /**
    * 修改保存教官
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "coach", value = "教官",
         required = true, dataType = "Coach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Coach coach ) {
      return toAjax( coachService.updateCoach( coach ) );
   }


   /**
    * 删除教官
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( coachService.deleteCoachByIds( ids ) );
   }


   /**
    * 根据id删除教官
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( coachService.deleteCoachById( id ) );
   }


   /**
    * 查询教官分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询教官分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "coach", value = "教官",
         required = true, dataType = "Coach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Coach coach ) {
      startPage( coach.getPd() );
      List<Coach> list = coachService.selectCoachList( coach );
      return getDataTable( list );
   }

}
