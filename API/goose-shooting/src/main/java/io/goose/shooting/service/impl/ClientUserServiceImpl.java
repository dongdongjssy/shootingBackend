package io.goose.shooting.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.constant.UserConstants;
import io.goose.common.support.Convert;
import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.mapper.ClientUserMapper;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.impl.ext.ServiceUtils;


/**
 * 客户端用户 服务层实现
 *
 * @author goose
 * @date 2020-04-28
 */
@Service
public class ClientUserServiceImpl implements IClientUserService {

   @Autowired
   private ClientUserMapper clientUserMapper;

   // @Autowired
   // private IUserRoleService userRoleService;


   /**
    * 查询客户端用户信息
    *
    * @param id
    *           客户端用户ID
    * @return 客户端用户信息
    */
   @Override
   public ClientUser selectClientUserById( Long id ) {
      return clientUserMapper.selectClientUserById( id );
   }


   /**
    * 查询客户端用户信息 外键关联
    *
    * @param id
    *           客户端用户ID
    * @return 客户端用户信息
    */
   @Override
   public ClientUser selectClientUserByIdAssoc( Long id ) {
      ClientUser user = clientUserMapper.selectClientUserByIdAssoc( id );

      // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      // user.setCertExpireDate( sdf. );
      return user;
   }


   /**
    * 查询所有客户端用户列表
    *
    * @param
    * @return 客户端用户集合
    */
   @Override
   public List<ClientUser> selectClientUserAll() {
      return clientUserMapper.selectClientUserAll();
   }


   @Override
   public List<ClientUser> selectClientUserAllFullDisplay() {
      List<ClientUser> users = clientUserMapper.selectClientUserAll();
      setFullDisplay( users );
      return users;
   }


   @Override
   public List<ClientUser> selectClientUserAllPassedFullDisplay() {
      List<ClientUser> users = clientUserMapper.selectClientUserAll();
      users = users.stream().filter( user -> isValidUser( user ) ).collect( Collectors.toList() );
      setFullDisplay( users );
      return users;
   }


   private boolean isValidUser( ClientUser user ) {
      if ( user.getStatus() != 2 ) {
         return false;
      }

      // UserRole userRole = new UserRole();
      // List<UserRole> userRoles = userRoleService.selectUserRoleList( userRole );
      //
      // if ( userRoles == null || userRoles.size() == 0 ) {
      // return true;
      // }
      //
      // if ( StringUtils.isEmpty( userRoles.get( 0 ).getRoleIds() ) ) {
      // return true;
      // }

      return true;
   }


   /**
    * 查询所有客户端用户列表 外键关联
    *
    * @param
    * @return 客户端用户集合
    */
   @Override
   public List<ClientUser> selectClientUserAllAssoc() {
      return clientUserMapper.selectClientUserAllAssoc();
   }

   @Override
   public List<ClientUser> selectClientUserListRemind(ClientUser clientUser) {
      return clientUserMapper.selectClientUserListRemind(clientUser);
   }

   @Override
   public List<ClientUser> selectRemindMessage(ClientUser clientUser) {
      return clientUserMapper.selectRemindMessage(clientUser);
   }


   /**
    * 查询客户端用户列表
    *
    * @param clientUser
    *           客户端用户信息
    * @return 客户端用户集合
    */
   @Override
   public List<ClientUser> selectClientUserList( ClientUser clientUser ) {
      return clientUserMapper.selectClientUserList( clientUser );
   }


   @Override
   public List<ClientUser> selectClientUserListFullDisplay( ClientUser clientUser ) {
      List<ClientUser> users = clientUserMapper.selectClientUserList( clientUser );
      setFullDisplay( users );
      return users;
   }


   /**
    * 查询客户端用户列表 外键关联
    *
    * @param clientUser
    *           客户端用户信息
    * @return 客户端用户集合
    */
   @Override
   public List<ClientUser> selectClientUserListAssoc( ClientUser clientUser ) {
      return clientUserMapper.selectClientUserListAssoc( clientUser );
   }


   /**
    * 新增客户端用户
    *
    * @param clientUser
    *           客户端用户信息
    * @return 结果
    */
   @Override
   public int insertClientUser( ClientUser clientUser ) {
      return clientUserMapper.insertClientUser( clientUser );
   }


   /**
    * 修改客户端用户
    *
    * @param clientUser
    *           客户端用户信息
    * @return 结果
    */
   @Override
   public int updateClientUser( ClientUser clientUser ) {
      return clientUserMapper.updateClientUser( clientUser );
   }

   @Override
   public int updateClientUserList(ClientUser clientUser) {
      return clientUserMapper.updateClientUserList( clientUser );
   }


   /**
    * 删除客户端用户对象
    *
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClientUserByIds( String ids ) {
      return clientUserMapper.deleteClientUserByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除客户端用户对象
    *
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteClientUserById( Long id ) {
      return clientUserMapper.deleteClientUserById( id );
   }


   /**
    * 校验是否唯一
    *
    * @param clientUser
    * @return 结果
    */
   @Override
   public String checkUserNameUnique( ClientUser clientUser ) {
      Long id = StringUtils.isNull( clientUser.getId() ) ? -1L : clientUser.getId();
      ClientUser result = clientUserMapper.checkUserNameUnique( clientUser );
      if ( StringUtils.isNotNull( result ) && result.getId().longValue() != id.longValue() ) {
         return UserConstants.GENERAL_NOT_UNIQUE;
      }
      return UserConstants.GENERAL_UNIQUE;
   }


   @Override
   public ClientUser selectClientUserByUsername( String username ) {
      return clientUserMapper.selectClientUserByUsername( username );
   }


   @Override
   public ClientUser selectClientUserByPhone( String phone ) {
      return clientUserMapper.selectClientUserByPhone( phone );
   }

   @Override
   public Integer selectClientUserCountByMemberId(String memberId) {
      return clientUserMapper.selectClientUserCountByMemberId(memberId);
   }

    @Override
    public List<ClientUser> selectClientUserByRealName(String realName) {
        return clientUserMapper.selectClientUserByRealName(realName);
    }


   private void setFullDisplay( List<ClientUser> users ) {
      users.forEach( user -> ServiceUtils.setUserFullDisplay( user ) );
   }

}
