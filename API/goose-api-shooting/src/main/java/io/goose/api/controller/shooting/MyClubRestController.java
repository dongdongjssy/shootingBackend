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
import io.goose.shooting.domain.MyClub;
import io.goose.shooting.service.impl.ext.MyClubServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 我所属的俱乐部 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/shooting/myClub" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "我所属的俱乐部 ", description = "我所属的俱乐部" )
public class MyClubRestController extends ClubRestBaseController {

   @Autowired
   private MyClubServiceImplExt myClubService;


   /**
    * 查询我所属的俱乐部列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询我所属的俱乐部列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "myClub", value = "我所属的俱乐部",
         required = false, dataType = "MyClub" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody MyClub myClub ) {
      startPage();
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubList( myClub );
      return getDataTable( list );
   }

   /**
    * 查询我所属的俱乐部列表
    */
   @PostMapping( "/listAssoc" )
   @ApiOperation( value = "查询我所属的俱乐部列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "myClub", value = "我所属的俱乐部",
         required = false, dataType = "MyClub" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listAssoc( @RequestBody MyClub myClub ) {
      startPage();
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubListAssoc( myClub );
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
   public MyClub getById( @PathVariable( "id" ) Long id ) {
      return myClubService.selectMyClubById( id );
   }


   /**
    * 根据ClientUser ID查询
    */
   @PostMapping( "/getPostByUserId/{clientUserid}" )
   @ApiOperation( value = " 根据ClientUser ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clientUserid",
         value = "外键", required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo getPostByUserId( @PathVariable( "clientUserid" ) Long clientUserid ) {
      startPage();

      List<?> list = myClubService.selectClubPostDetailsListByUserId( clientUserid );
      return getDataTable( list );
   }

   /**
    * 导出我所属的俱乐部列表
    */
   /*
    * @PostMapping("/export")
    *
    * @ApiOperation(value="导出我所属的俱乐部列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="body", name = "myClub", value = "我所属的俱乐部", required = false,
    * dataType = "MyClub") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * export(MyClub myClub) { List<MyClub> list = myClubService.selectMyClubList(myClub);
    * ExcelUtil<MyClub> util = new ExcelUtil<MyClub>(MyClub.class); return util.exportExcel(list,
    * "myClub"); }
    */

   /**
    * 导入我所属的俱乐部列表
    */
   /*
    * @PostMapping("/import")
    *
    * @ApiOperation(value="导入我所属的俱乐部列表")
    *
    * @ApiImplicitParams({
    *
    * @ApiImplicitParam(paramType="query", name = "file", value = "我所属的俱乐部文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<MyClub> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), MyClub.class);
    * for(MyClub obj : list) { myClubService.insertMyClub(obj); } } catch(ExcelUtilException |
    * IOException e) { return AjaxResult.error(e.getMessage()); }
    *
    * return AjaxResult.success("成功导入全部数据");
    *
    * }
    */


   /**
    * 新增保存我所属的俱乐部
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存我所属的俱乐部" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "myClub", value = "我所属的俱乐部",
         required = true, dataType = "MyClub" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody MyClub myClub ) {
	   myClub = setClubId(myClub);
      return toAjax( myClubService.insertMyClub( myClub ) );
   }


   /**
    * 修改保存我所属的俱乐部
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存我所属的俱乐部" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "myClub", value = "我所属的俱乐部",
         required = true, dataType = "MyClub" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody MyClub myClub ) {
      return toAjax( myClubService.updateMyClub( myClub ) );
   }


   /**
    * 删除我所属的俱乐部
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除我所属的俱乐部" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( myClubService.deleteMyClubByIds( ids ) );
   }


   /**
    * 根据id删除我所属的俱乐部
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除我所属的俱乐部" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( myClubService.deleteMyClubById( id ) );
   }


   /**
    * 查询我所属的俱乐部分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询我所属的俱乐部分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "myClub", value = "我所属的俱乐部",
         required = true, dataType = "MyClub" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody MyClub myClub ) {
      startPage( myClub.getPd() );
      myClub = setClubId(myClub);
      List<MyClub> list = myClubService.selectMyClubList( myClub );
      return getDataTable( list );
   }

}
