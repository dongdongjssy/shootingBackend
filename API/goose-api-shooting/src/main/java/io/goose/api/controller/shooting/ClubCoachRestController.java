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
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.ClubCoach;
import io.goose.shooting.domain.Coach;
import io.goose.shooting.service.impl.ext.ClubCoachServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 俱乐部教官 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/clubCoach" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "俱乐部教官 ", description = "俱乐部教官" )
public class ClubCoachRestController extends ClubRestBaseController {

   @Autowired
   private ClubCoachServiceImplExt clubCoachService;


   /**
    * 查询俱乐部教官列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询俱乐部教官列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubCoach", value = "俱乐部教官",
         required = false, dataType = "ClubCoach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody ClubCoach clubCoach ) {
      startPage();
      clubCoach = setClubId(clubCoach);
      List<ClubCoach> list = clubCoachService.selectClubCoachList( clubCoach );
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
   public ClubCoach getById( @PathVariable( "id" ) Long id ) {
      return clubCoachService.selectClubCoachById( id );
   }


   /**
    * 根据Club ID查询
    */
   @PostMapping( "/getByClubId/{clubId}" )
   @ApiOperation( value = " 根据外键ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clubId", value = "外键",
         required = true, dataType = "Long" ) } )
   public List<Coach> getByClubId( @PathVariable( "clubId" ) Long clubId ) {
      return clubCoachService.selectCoachListByClubId( clubId );
   }

   /**
    * 导出俱乐部教官列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出俱乐部教官列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "clubCoach", value = "俱乐部教官", required = false,
    * dataType = "ClubCoach") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(ClubCoach clubCoach) { List<ClubCoach> list =
    * clubCoachService.selectClubCoachList(clubCoach); ExcelUtil<ClubCoach> util = new
    * ExcelUtil<ClubCoach>(ClubCoach.class); return util.exportExcel(list, "clubCoach"); }
    */

   /**
    * 导入俱乐部教官列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入俱乐部教官列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "俱乐部教官文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<ClubCoach> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubCoach.class);
    * for(ClubCoach obj : list) { clubCoachService.insertClubCoach(obj); } }
    * catch(ExcelUtilException | IOException e) { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存俱乐部教官
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存俱乐部教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubCoach", value = "俱乐部教官",
         required = true, dataType = "ClubCoach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody ClubCoach clubCoach ) {
	   clubCoach = setClubId(clubCoach);
      return toAjax( clubCoachService.insertClubCoach( clubCoach ) );
   }


   /**
    * 修改保存俱乐部教官
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存俱乐部教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubCoach", value = "俱乐部教官",
         required = true, dataType = "ClubCoach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody ClubCoach clubCoach ) {
      return toAjax( clubCoachService.updateClubCoach( clubCoach ) );
   }


   /**
    * 删除俱乐部教官
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除俱乐部教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( clubCoachService.deleteClubCoachByIds( ids ) );
   }


   /**
    * 根据id删除俱乐部教官
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除俱乐部教官" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( clubCoachService.deleteClubCoachById( id ) );
   }


   /**
    * 查询俱乐部教官分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询俱乐部教官分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "clubCoach", value = "俱乐部教官",
         required = true, dataType = "ClubCoach" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody ClubCoach clubCoach ) {
      startPage( clubCoach.getPd() );
      clubCoach = setClubId(clubCoach);
      List<ClubCoach> list = clubCoachService.selectClubCoachList( clubCoach );
      return getDataTable( list );
   }

}
