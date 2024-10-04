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
import io.goose.shooting.domain.Role;
import io.goose.shooting.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 客户端角色 信息操作处理
 * 
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping( "/client/role" )
@CrossOrigin( origins = "*", maxAge = 3600 )
@Api( value = "客户端角色 ", description = "客户端角色" )
public class RoleRestController extends BaseController {

   @Autowired
   private IRoleService roleService;


   /**
    * 查询客户端角色列表
    */
   @PostMapping( "/list" )
   @ApiOperation( value = "查询客户端角色列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "role", value = "客户端角色",
         required = false, dataType = "Role" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo list( @RequestBody Role role ) {
      startPage();

      List<Role> list = roleService.selectRoleList( role );
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
   public Role getById( @PathVariable( "id" ) Long id ) {
      return roleService.selectRoleById( id.intValue() );
   }

   /**
    * 导出客户端角色列表
    */
   /*
    * @PostMapping("/export")
    * 
    * @ApiOperation(value="导出客户端角色列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="body", name = "role", value = "客户端角色", required = false, dataType
    * = "Role") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Role role)
    * { List<Role> list = roleService.selectRoleList(role); ExcelUtil<Role> util = new
    * ExcelUtil<Role>(Role.class); return util.exportExcel(list, "role"); }
    */

   /**
    * 导入客户端角色列表
    */
   /*
    * @PostMapping("/import")
    * 
    * @ApiOperation(value="导入客户端角色列表")
    * 
    * @ApiImplicitParams({
    * 
    * @ApiImplicitParam(paramType="query", name = "file", value = "客户端角色文件", required = false,
    * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
    * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Role>
    * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Role.class); for(Role obj : list)
    * { roleService.insertRole(obj); } } catch(ExcelUtilException | IOException e) { return
    * AjaxResult.error(e.getMessage()); }
    * 
    * return AjaxResult.success("成功导入全部数据");
    * 
    * }
    */


   /**
    * 新增保存客户端角色
    */
   @PostMapping( "/add" )
   @ApiOperation( value = "新增保存客户端角色" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "role", value = "客户端角色",
         required = true, dataType = "Role" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult add( @RequestBody Role role ) {
      return toAjax( roleService.insertRole( role ) );
   }


   /**
    * 修改保存客户端角色
    */
   @PostMapping( "/edit" )
   @ApiOperation( value = "修改保存客户端角色" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "role", value = "客户端角色",
         required = true, dataType = "Role" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult edit( @RequestBody Role role ) {
      return toAjax( roleService.updateRole( role ) );
   }


   /**
    * 删除客户端角色
    */
   @PostMapping( "/remove" )
   @ApiOperation( value = "删除客户端角色" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "${ids}", value = "主键列表",
         required = true, dataType = "String" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult remove( String ids ) {
      return toAjax( roleService.deleteRoleByIds( ids ) );
   }


   /**
    * 根据id删除客户端角色
    */
   @PostMapping( "/remove/{id}" )
   @ApiOperation( value = "根据id删除客户端角色" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "path", name = "${id}", value = "主键",
         required = true, dataType = "Long" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public AjaxResult removeById( @PathVariable( "id" ) Long id ) {
      return toAjax( roleService.deleteRoleById( id.intValue() ) );
   }


   /**
    * 查询客户端角色分页列表
    */
   @PostMapping( "/list/page" )
   @ApiOperation( value = "查询客户端角色分页列表" )
   @ApiImplicitParams( { @ApiImplicitParam( paramType = "body", name = "role", value = "客户端角色",
         required = true, dataType = "Role" ) } )
   // @PreAuthorize("hasAnyRole('USER','ADMIN')")
   public TableDataInfo listPage( @RequestBody Role role ) {
      startPage( role.getPd() );
      List<Role> list = roleService.selectRoleList( role );
      return getDataTable( list );
   }

}
