package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
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
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Role;
import io.goose.shooting.service.IRoleService;


/**
 * 客户端角色 信息操作处理
 * 
 * @author goose
 * @date 2020-04-30
 */
@Controller
@RequestMapping( "/client/role" )
public class RoleController extends BaseController {
   private static final Logger log = LoggerFactory.getLogger(RoleController.class);


   private String prefix = "shooting/role";

   @Autowired
   private IRoleService roleService;
   @Autowired
   private FileUploadDownloadOSSService fileUploadDownloadService;


   @RequiresPermissions( "client:role:view" )
   @GetMapping( )
   public String role( ModelMap mmap ) {
      return prefix + "/role";
   }


   /**
    * 查询客户端角色列表
    */
   @RequiresPermissions( "client:role:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( Role role ) {
      startPage();
      List<Role> list = roleService.selectRoleListAssoc( role );
      return getDataTable( list );
   }


   /**
    * 导出客户端角色列表
    */
   @RequiresPermissions( "client:role:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( Role role ) {
      List<Role> list = roleService.selectRoleList( role );
      ExcelUtil<Role> util = new ExcelUtil<Role>( Role.class );
      return util.exportExcel( list, "role" );
   }


   /**
    * 导入客户端角色列表
    */
   @RequiresPermissions( "client:role:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<Role> list = ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), Role.class );
         for ( Role obj : list ) {
            roleService.insertRole( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增客户端角色
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      return prefix + "/add";
   }


   /**
    * 新增保存客户端角色
    */
   @RequiresPermissions( "client:role:add" )
   @Log( title = "客户端角色", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( Role role, @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile ) {

      try {
         if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
            String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.Role.getValue() , true);
            role.setPictureUrl(filePath);
         }
      } catch (Exception e) {
         log.error("图像上传失败！", e);
         return error(e.getMessage());
      }
      role.setCreateBy( ShiroUtils.getLoginName() );

      return toAjax( roleService.insertRole( role ) );
   }


   /**
    * 修改客户端角色
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Integer id, ModelMap mmap ) {
      Role role = roleService.selectRoleByIdAssoc( id );
      mmap.put( "role", role );
      mmap.put("imageUrlPrefix", getImageUrlPrefix());
      return prefix + "/edit";
   }


   /**
    * 修改保存客户端角色
    */
   @RequiresPermissions( "client:role:edit" )
   @Log( title = "客户端角色", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( Role role, @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile ) {
      try {
         if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
            String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.Role.getValue() , true);
            role.setPictureUrl(filePath);
         }
      } catch (Exception e) {
         log.error("图像上传失败！", e);
         return error(e.getMessage());
      }
      role.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( roleService.updateRole( role ) );
   }


   /**
    * 删除客户端角色
    */
   @RequiresPermissions( "client:role:remove" )
   @Log( title = "客户端角色", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      return toAjax( roleService.deleteRoleByIds( ids ) );
   }


   /**
    * 查询客户端角色分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody Role role ) {
      startPage( role.getPd() );

      List<Role> list = roleService.selectRoleListAssoc( role );
      return getDataTable( list );
   }

}
