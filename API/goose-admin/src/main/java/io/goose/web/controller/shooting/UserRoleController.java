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

import com.fasterxml.jackson.core.JsonProcessingException;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Message;
import io.goose.shooting.domain.Role;
import io.goose.shooting.domain.UserRole;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IMessageService;
import io.goose.shooting.service.IRoleService;
import io.goose.shooting.service.IUserRoleService;
import io.goose.web.controller.service.JpushService;


/**
 * 客户端用户角色关联 信息操作处理
 *
 * @author goose
 * @date 2020-04-30
 */
@Controller
@RequestMapping( "/client/userRole" )
public class UserRoleController extends BaseController {

   private String prefix = "shooting/userRole";

   @Autowired
   private IUserRoleService userRoleService;

   @Autowired
   private IClientUserService clientUserService;

   @Autowired
   private IRoleService roleService;

   @Autowired
   private IMessageService messageService;

   @Autowired
   private JpushService pushService;


   @RequiresPermissions( "client:userRole:view" )
   @GetMapping( )
   public String userRole( ModelMap mmap ) {
      mmap.put( "userIdList", clientUserService.selectClientUserAllPassedFullDisplay() );
      mmap.put( "roleIdList", roleService.selectRoleAllFullDisplay() );
      return prefix + "/userRole";
   }


   /**
    * 查询客户端用户角色关联列表
    */
   @RequiresPermissions( "client:userRole:list" )
   @PostMapping( "/list" )
   @ResponseBody
   public TableDataInfo list( UserRole userRole ) {
      startPage();
      List<UserRole> list = userRoleService.selectUserRoleListAssocFullDisplay( userRole );
      return getDataTable( list );
   }


   /**
    * 导出客户端用户角色关联列表
    */
   @RequiresPermissions( "client:userRole:export" )
   @PostMapping( "/export" )
   @ResponseBody
   public AjaxResult export( UserRole userRole ) {
      List<UserRole> list = userRoleService.selectUserRoleList( userRole );
      ExcelUtil<UserRole> util = new ExcelUtil<UserRole>( UserRole.class );
      return util.exportExcel( list, "userRole" );
   }


   /**
    * 导入客户端用户角色关联列表
    */
   @RequiresPermissions( "client:userRole:import" )
   @PostMapping( "/import" )
   @ResponseBody
   public AjaxResult
         importExcel( @RequestParam( value = "file", required = false ) MultipartFile excl ) {
      try {
         List<UserRole> list =
               ExcelImpExpUtil.parseExcelFile( excl.getInputStream(), UserRole.class );
         for ( UserRole obj : list ) {
            userRoleService.insertUserRole( obj );
         }
      } catch ( ExcelUtilException | IOException e ) {
         return AjaxResult.error( e.getMessage() );
      }

      return AjaxResult.success( "成功导入全部数据" );

   }


   /**
    * 新增客户端用户角色关联
    */
   @GetMapping( "/add" )
   public String add( ModelMap mmap ) {
      mmap.put( "userIdList", clientUserService.selectClientUserAllPassedFullDisplay() );
      mmap.put( "roleIdList", roleService.selectRoleAllFullDisplay() );
      return prefix + "/add";
   }


   /**
    * 新增保存客户端用户角色关联
    */
   @RequiresPermissions( "client:userRole:add" )
   @Log( title = "客户端用户角色关联", businessType = BusinessType.INSERT )
   @PostMapping( "/add" )
   @ResponseBody
   public AjaxResult addSave( UserRole userRole ) throws JsonProcessingException {

      userRole.setCreateBy( ShiroUtils.getLoginName() );

      int result = userRoleService.insertUserRole( userRole );

      if ( result > 0 ) {
         Message message = new Message();
         message.setCreateBy( ShiroUtils.getLoginName() );
         message.setTitle( "角色更新" );
         message.setContent( "您有新角色添加" );

         message.setType( 2 );
         int success = messageService.insertMessage( message );
         if ( success > 0 ) {
            messageService.insertMessageUser( message.getId(), userRole.getUserId(),
                  message.getCreateBy() );
            // 推送
            Role role = roleService.selectRoleById( userRole.getRoleId() );
            pushService.jpush( message.getTitle(), message.getContent(),
                  userRole.getUserId().toString(), "6", "0",
                  role.getName() + ":" + role.getDescription() );
         } else {
            throw new RuntimeException( "编辑失败" );
         }
      }

      return toAjax( result );
   }


   /**
    * 修改客户端用户角色关联
    */
   @GetMapping( "/edit/{id}" )
   public String edit( @PathVariable( "id" ) Integer id, ModelMap mmap ) {
      UserRole userRole = userRoleService.selectUserRoleByIdAssoc( id );
      mmap.put( "userRole", userRole );
      mmap.put( "userIdList", clientUserService.selectClientUserAllPassedFullDisplay() );
      mmap.put( "roleIdList", roleService.selectRoleAllFullDisplay() );
      return prefix + "/edit";
   }


   /**
    * 修改保存客户端用户角色关联
    */
   @RequiresPermissions( "client:userRole:edit" )
   @Log( title = "客户端用户角色关联", businessType = BusinessType.UPDATE )
   @PostMapping( "/edit" )
   @ResponseBody
   public AjaxResult editSave( UserRole userRole ) {
      userRole.setUpdateBy( ShiroUtils.getLoginName() );
      return toAjax( userRoleService.updateUserRole( userRole ) );
   }


   /**
    * 删除客户端用户角色关联
    */
   @RequiresPermissions( "client:userRole:remove" )
   @Log( title = "客户端用户角色关联", businessType = BusinessType.DELETE )
   @PostMapping( "/remove" )
   @ResponseBody
   public AjaxResult remove( String ids ) {
      String[] idArr = Convert.toStrArray( ids );

      for ( String id : idArr ) {
         UserRole userRole = userRoleService.selectUserRoleById( Integer.parseInt( id ) );
         Long userId = userRole.getUserId();
         Role role = roleService.selectRoleById( userRole.getRoleId() );

         int result = userRoleService.deleteUserRoleById( Integer.parseInt( id ) );

         if ( result > 0 ) {
            Message message = new Message();
            message.setCreateBy( ShiroUtils.getLoginName() );
            message.setTitle( "角色更新" );
            message.setContent( "您有角色变动" );

            message.setType( 2 );
            int success = messageService.insertMessage( message );
            if ( success > 0 ) {
               messageService.insertMessageUser( message.getId(), userRole.getUserId(),
                     message.getCreateBy() );
               // 推送
               pushService.jpush( message.getTitle(), message.getContent(), userId.toString(), "6",
                     "1", role.getName() + ":" + role.getDescription() );
            } else {
               throw new RuntimeException( "编辑失败" );
            }
         }
      }

      return AjaxResult.success();
   }


   /**
    * 查询客户端用户角色关联分页列表
    */
   @PostMapping( "/list/page" )
   public TableDataInfo listPage( @RequestBody UserRole userRole ) {
      startPage( userRole.getPd() );

      List<UserRole> list = userRoleService.selectUserRoleListAssoc( userRole );
      return getDataTable( list );
   }

}
