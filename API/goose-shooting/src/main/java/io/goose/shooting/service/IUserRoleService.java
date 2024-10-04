package io.goose.shooting.service;

import java.util.List;

import io.goose.shooting.domain.UserRole;


/**
 * 客户端用户角色关联 服务层
 * 
 * @author goose
 * @date 2020-04-28
 */
@Deprecated
public interface IUserRoleService {

   /**
    * 查询客户端用户角色关联信息
    * 
    * @param id
    *           客户端用户角色关联ID
    * @return 客户端用户角色关联信息
    */
   public UserRole selectUserRoleById( Integer id );


   /**
    * 查询客户端用户角色关联信息 外键关联
    * 
    * @param id
    *           客户端用户角色关联ID
    * @return 客户端用户角色关联信息
    */
   public UserRole selectUserRoleByIdAssoc( Integer id );


   /**
    * 查询所有客户端用户角色关联列表
    * 
    * @param
    * @return 客户端用户角色关联集合
    */
   public List<UserRole> selectUserRoleAll();


   /**
    * 查询所有客户端用户角色关联列表 外键关联
    * 
    * @param
    * @return 客户端用户角色关联集合
    */
   public List<UserRole> selectUserRoleAllAssoc();


   /**
    * 查询客户端用户角色关联列表
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 客户端用户角色关联集合
    */
   public List<UserRole> selectUserRoleList( UserRole userRole );


   /**
    * 查询客户端用户角色关联列表 外键关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 客户端用户角色关联集合
    */
   public List<UserRole> selectUserRoleListAssoc( UserRole userRole );


   public List<UserRole> selectUserRoleListAssocFullDisplay( UserRole userRole );


   /**
    * 新增客户端用户角色关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 结果
    */
   public int insertUserRole( UserRole userRole );


   /**
    * 修改客户端用户角色关联
    * 
    * @param userRole
    *           客户端用户角色关联信息
    * @return 结果
    */
   public int updateUserRole( UserRole userRole );


   /**
    * 删除客户端用户角色关联信息
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   public int deleteUserRoleByIds( String ids );


   /**
    * 删除客户端用户角色关联信息
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   public int deleteUserRoleById( Integer id );

}
