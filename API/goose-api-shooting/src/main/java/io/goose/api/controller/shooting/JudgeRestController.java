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
import io.goose.shooting.domain.Judge;
import io.goose.shooting.service.impl.ext.JudgeServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 裁判 信息操作处理
 * 
 * @author goose
 * @date 2020-05-21
 */
@RestController
@RequestMapping( "/shooting/judge" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "裁判 ", description = "裁判" )
public class JudgeRestController extends BaseController {

   @Autowired
   private JudgeServiceImplExt judgeService;


   /**
    * 查询裁判列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询裁判列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "judge", value = "裁判",
         required = false, dataType = "Judge" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Judge judge ) {
      startPage();

      List<Judge> list = judgeService.selectJudgeList( judge );
      return getDataTable( list );
   }


   /**
    * 查询裁判列表
    */
   @PostMapping( "/groupList" )
   @ApiOperation( value = "查询裁判列表" )
   public List<?> groupList() {
      startPage();

      List<?> list = judgeService.selectJudgeGroupList();
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
   public Judge getById( @PathVariable( "id" ) Long id ) {
      return judgeService.selectJudgeById( id );
   }

   /**
    * 导出裁判列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出裁判列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "judge", value = "裁判", required = false, dataType =
    * "Judge") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Judge
    * judge) { List<Judge> list = judgeService.selectJudgeList(judge); ExcelUtil<Judge> util = new
    * ExcelUtil<Judge>(Judge.class); return util.exportExcel(list, "judge"); }
    */

   /**
    * 导入裁判列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入裁判列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "裁判文件", required = false, dataType
    * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<Judge> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Judge.class);
    * for(Judge obj : list) { judgeService.insertJudge(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存裁判
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存裁判" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "judge", value = "裁判",
         required = true, dataType = "Judge" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Judge judge ) {
      return toAjax( judgeService.insertJudge( judge ) );
   }


   /**
    * 修改保存裁判
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存裁判" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "judge", value = "裁判",
         required = true, dataType = "Judge" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Judge judge ) {
      return toAjax( judgeService.updateJudge( judge ) );
   }


   /**
    * 删除裁判
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除裁判" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( judgeService.deleteJudgeByIds( ids ) );
   }


   /**
    * 根据id删除裁判
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除裁判" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( judgeService.deleteJudgeById( id ) );
   }


   /**
    * 查询裁判分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询裁判分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "judge", value = "裁判",
         required = true, dataType = "Judge" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Judge judge ) {
      startPage( judge.getPd() );
      List<Judge> list = judgeService.selectJudgeList( judge );
      return getDataTable( list );
   }

}
