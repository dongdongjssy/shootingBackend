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
import io.goose.shooting.domain.UserCollection;
import io.goose.shooting.service.impl.ext.UserCollectionServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

// import org.springframewor.security.access.prepost.PreAuthorize;


/**
 * 用户收藏 信息操作处理
 *
 * @author goose
 * @date 2020-05-21
 */
@RestController
@RequestMapping( "/shooting/userCollection" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "用户收藏 ", description = "用户收藏" )
public class UserCollectionRestController extends BaseController {

   @Autowired
   private UserCollectionServiceImplExt userCollectionService;


   /**
    * 查询用户收藏列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询用户收藏列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "userCollection",
         value = "用户收藏", required = false, dataType = "UserCollection" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody UserCollection userCollection ) {
      startPage();

      List<UserCollection> list = userCollectionService.selectUserCollectionList( userCollection );
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
   public UserCollection getById( @PathVariable( "id" ) Long id ) {
      return userCollectionService.selectUserCollectionById( id );
   }


   /**
    * 根据ClientUser ID查询
    */
   @PostMapping( "/getByUserId/{clientUserid}" )
   @ApiOperation( value = " 根据ClientUser ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clientUserid",
         value = "外键", required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo getByUserId( @PathVariable( "clientUserid" ) Long clientUserid ) {
      startPage();

      List<?> list = userCollectionService.selectUserCollectionDetailsList( clientUserid );
      return getDataTable( list );
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

      List<?> list = userCollectionService.selectPostCollectionDetailsList( clientUserid );
      return getDataTable( list );
   }


   /**
    * 根据ClientUser ID查询
    */
   @PostMapping( "/getActivityByUserId/{clientUserid}" )
   @ApiOperation( value = " 根据ClientUser ID查询" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clientUserid",
         value = "外键", required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo getActivityByUserId( @PathVariable( "clientUserid" ) Long clientUserid ) {
      startPage();

      List<?> list = userCollectionService.selectActivityCollectionDetailsList( clientUserid );
      return getDataTable( list );
   }

   /**
    * 导出用户收藏列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出用户收藏列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "userCollection", value = "用户收藏", required = false,
    * dataType = "UserCollection") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public
    * AjaxResult export(UserCollection userCollection) { List<UserCollection> list =
    * userCollectionService.selectUserCollectionList(userCollection); ExcelUtil<UserCollection> util
    * = new ExcelUtil<UserCollection>(UserCollection.class); return util.exportExcel(list,
    * "userCollection"); }
    */

   /**
    * 导入用户收藏列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入用户收藏列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "用户收藏文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
    * List<UserCollection> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
    * UserCollection.class); for(UserCollection obj : list) {
    * userCollectionService.insertUserCollection(obj); } } catch(ExcelUtilException | IOException e)
    * { return AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存用户收藏
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存用户收藏" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "userCollection",
         value = "用户收藏", required = true, dataType = "UserCollection" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody UserCollection userCollection ) {

      if ( userCollectionService.selectUserCollectionList( userCollection ).size() > 0 ) {
         return AjaxResult.error( "不能重复收藏" );
      }

      int result = userCollectionService.insertUserCollection( userCollection );

      return toAjax( result );
   }


   /**
    * 修改保存用户收藏
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存用户收藏" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "userCollection",
         value = "用户收藏", required = true, dataType = "UserCollection" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody UserCollection userCollection ) {
      return toAjax( userCollectionService.updateUserCollection( userCollection ) );
   }


   /**
    * 删除用户收藏
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除用户收藏" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( userCollectionService.deleteUserCollectionByIds( ids ) );
   }


   /**
    * 根据id删除用户收藏
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除用户收藏" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( userCollectionService.deleteUserCollectionById( id ) );
   }


   /**
    * 查询用户收藏分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询用户收藏分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "userCollection",
         value = "用户收藏", required = true, dataType = "UserCollection" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody UserCollection userCollection ) {
      startPage( userCollection.getPd() );
      List<UserCollection> list = userCollectionService.selectUserCollectionList( userCollection );
      return getDataTable( list );
   }

}
