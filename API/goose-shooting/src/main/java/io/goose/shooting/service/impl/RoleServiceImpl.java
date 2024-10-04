package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Role;
import io.goose.shooting.mapper.RoleMapper;
import io.goose.shooting.service.IRoleService;
import io.goose.shooting.service.impl.ext.ServiceUtils;


/**
 * 客户端角色 服务层实现
 * 
 * @author goose
 * @date 2020-04-28
 */
@Service
public class RoleServiceImpl implements IRoleService {

   @Autowired
   private RoleMapper roleMapper;


   /**
    * 查询客户端角色信息
    * 
    * @param id
    *           客户端角色ID
    * @return 客户端角色信息
    */
   @Override
   public Role selectRoleById( Integer id ) {
      return roleMapper.selectRoleById( id );
   }


   /**
    * 查询客户端角色信息 外键关联
    * 
    * @param id
    *           客户端角色ID
    * @return 客户端角色信息
    */
   @Override
   public Role selectRoleByIdAssoc( Integer id ) {
      return roleMapper.selectRoleByIdAssoc( id );
   }


   /**
    * 查询所有客户端角色列表
    * 
    * @param
    * @return 客户端角色集合
    */
   @Override
   public List<Role> selectRoleAll() {
      return roleMapper.selectRoleAll();
   }


   @Override
   public List<Role> selectRoleAllFullDisplay() {
      List<Role> roles = roleMapper.selectRoleAll();
      roles.forEach( role -> ServiceUtils.setRoleFullDisplay( role ) );
      return roles;
   }


   /**
    * 查询所有客户端角色列表 外键关联
    * 
    * @param
    * @return 客户端角色集合
    */
   @Override
   public List<Role> selectRoleAllAssoc() {
      return roleMapper.selectRoleAllAssoc();
   }


   /**
    * 查询客户端角色列表
    * 
    * @param role
    *           客户端角色信息
    * @return 客户端角色集合
    */
   @Override
   public List<Role> selectRoleList( Role role ) {
      return roleMapper.selectRoleList( role );
   }


   /**
    * 查询客户端角色列表 外键关联
    * 
    * @param role
    *           客户端角色信息
    * @return 客户端角色集合
    */
   @Override
   public List<Role> selectRoleListAssoc( Role role ) {
      return roleMapper.selectRoleListAssoc( role );
   }


   /**
    * 新增客户端角色
    * 
    * @param role
    *           客户端角色信息
    * @return 结果
    */
   @Override
   public int insertRole( Role role ) {
      return roleMapper.insertRole( role );
   }


   /**
    * 修改客户端角色
    * 
    * @param role
    *           客户端角色信息
    * @return 结果
    */
   @Override
   public int updateRole( Role role ) {
      return roleMapper.updateRole( role );
   }


   /**
    * 删除客户端角色对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRoleByIds( String ids ) {
      return roleMapper.deleteRoleByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除客户端角色对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRoleById( Integer id ) {
      return roleMapper.deleteRoleById( id );
   }

}
