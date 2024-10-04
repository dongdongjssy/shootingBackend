package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.Role;
import io.goose.shooting.domain.UserRole;
import io.goose.shooting.mapper.UserRoleMapper;
import io.goose.shooting.service.IRoleService;
import io.goose.shooting.service.IUserRoleService;
import io.goose.shooting.service.impl.ext.ServiceUtils;


/**
 * 客户端用户角色关联 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
@Deprecated
public class UserRoleServiceImpl implements IUserRoleService {

   @Autowired
   private UserRoleMapper userRoleMapper;

   @Autowired
   private IRoleService roleService;


   /**
    * 查询客户端用户角色关联信息
    * 
    * @param id
    *           客户端用户角色关联ID
    * @return 客户端用户角色关联信息
    */
   @Override
   public UserRole selectUserRoleById( Integer id ) {
      return userRoleMapper.selectUserRoleById( id );
   }


   /**
    * 查询客户端用户角色关联信息 外键关联
    * 
    * @param id
    *           客户端用户角色关联ID
    * @return 客户端用户角色关联信息
    */
   @Override
   public UserRole selectUserRoleByIdAssoc( Integer id ) {
      return userRoleMapper.selectUserRoleByIdAssoc( id );
   }


   /**
    * 查询所有客户端用户角色关联列表
    * 
    * @param
    * @return 客户端用户角色关联集合
    */
   @Override
   public List<UserRole> selectUserRoleAll() {
      return userRoleMapper.selectUserRoleAll();
   }


   /**
    * 查询所有客户端用户角色关联列表 外键关联
    * 
    * @param
    * @return 客户端用户角色关联集合
    */
   @Override
   public List<UserRole> selectUserRoleAllAssoc() {
      return userRoleMapper.selectUserRoleAllAssoc();
   }


   /**
    * 查询客户端用户角色关联列表
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 客户端用户角色关联集合
    */
   @Override
   public List<UserRole> selectUserRoleList( UserRole userRole ) {
      return userRoleMapper.selectUserRoleList( userRole );
   }


   /**
    * 查询客户端用户角色关联列表 外键关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 客户端用户角色关联集合
    */
   @Override
   public List<UserRole> selectUserRoleListAssoc( UserRole userRole ) {
      return userRoleMapper.selectUserRoleListAssoc( userRole );
   }


   @Override
   public List<UserRole> selectUserRoleListAssocFullDisplay( UserRole userRoleInput ) {
      List<UserRole> userRoles = userRoleMapper.selectUserRoleListAssoc( userRoleInput );
      userRoles.forEach( userRole -> {
         ServiceUtils.setUserFullDisplay( userRole.getClientUser() );
         userRole.setRoleIds( getRoleNames( userRole.getRoleIds() ) );
      } );
      return userRoles;
   }


   private String getRoleNames( String roleIds ) {
      if ( StringUtils.isEmpty( roleIds ) ) {
         return "";
      }

      String[] roleIdArr = roleIds.split( "," );

      String roles = "";
      for ( String roleId : roleIdArr ) {
         Role role = roleService.selectRoleById( Integer.parseInt( roleId ) );
         if ( role != null ) {
            ServiceUtils.setRoleFullDisplay( role );
            roles += role.getName() + ", ";
         }
      }

      if ( roles.length() > 0 ) {
         roles = roles.substring( 0, roles.length() - 2 );
      }

      return roles;

   }


   /**
    * 新增客户端用户角色关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 结果
    */
   @Override
   public int insertUserRole( UserRole userRole ) {
      return userRoleMapper.insertUserRole( userRole );
   }


   /**
    * 修改客户端用户角色关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 结果
    */
   @Override
   public int updateUserRole( UserRole userRole ) {
      return userRoleMapper.updateUserRole( userRole );
   }


   /**
    * 删除客户端用户角色关联对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserRoleByIds( String ids ) {
      return userRoleMapper.deleteUserRoleByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除客户端用户角色关联对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteUserRoleById( Integer id ) {
      return userRoleMapper.deleteUserRoleById( id );
   }

}
