package io.goose.shooting.mapper;

import java.util.List;

import io.goose.shooting.domain.Role;


/**
 * 客户端角色 数据层
 * 
 * @author goose
 * @date 2020-04-28
 */
public interface RoleMapper {

   /**
    * 查询客户端角色信息
    * 
    * @param id
    *           客户端角色ID
    * @return 客户端角色信息
    */
   public Role selectRoleById( Integer id );


   /**
    * 查询客户端角色信息 外键关联
    * 
    * @param id
    *           客户端角色ID
    * @return 客户端角色信息
    */
   public Role selectRoleByIdAssoc( Integer id );


   /**
    * 查询所有客户端角色列表
    * 
    * @param
    * @return 客户端角色集合
    */
   public List<Role> selectRoleAll();


   /**
    * 查询所有客户端角色列表 外键关联
    * 
    * @param
    * @return 客户端角色集合
    */
   public List<Role> selectRoleAllAssoc();


   /**
    * 查询客户端角色列表
    * 
    * @param role
    *           客户端角色信息
    * @return 客户端角色集合
    */
   public List<Role> selectRoleList( Role role );


   /**
    * 查询客户端角色列表 外键关联
    * 
    * @param role
    *           客户端角色信息
    * @return 客户端角色集合
    */
   public List<Role> selectRoleListAssoc( Role role );


   /**
    * 新增客户端角色
    * 
    * @param role
    *           客户端角色信息
    * @return 结果
    */
   public int insertRole( Role role );


   /**
    * 修改客户端角色
    * 
    * @param role
    *           客户端角色信息
    * @return 结果
    */
   public int updateRole( Role role );


   /**
    * 删除客户端角色
    * 
    * @param id
    *           客户端角色ID
    * @return 结果
    */
   public int deleteRoleById( Integer id );


   /**
    * 批量删除客户端角色
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   public int deleteRoleByIds( String[] ids );

}
