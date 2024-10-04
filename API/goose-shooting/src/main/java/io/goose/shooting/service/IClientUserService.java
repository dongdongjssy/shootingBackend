package io.goose.shooting.service;

import java.util.List;

import io.goose.shooting.domain.ClientUser;


/**
 * 客户端用户 服务层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface IClientUserService {

    /**
     * 查询客户端用户信息
     *
     * @param id 客户端用户ID
     * @return 客户端用户信息
     */
    public ClientUser selectClientUserById(Long id);


    /**
     * 查询客户端用户信息 外键关联
     *
     * @param id 客户端用户ID
     * @return 客户端用户信息
     */
    public ClientUser selectClientUserByIdAssoc(Long id);


    /**
     * 查询所有客户端用户列表
     *
     * @param
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserAll();


    public List<ClientUser> selectClientUserAllFullDisplay();


    public List<ClientUser> selectClientUserAllPassedFullDisplay();


    /**
     * 查询所有客户端用户列表 外键关联
     *
     * @param
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserAllAssoc();


    /**
     * 查询客户端临期用户列表
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserListRemind(ClientUser clientUser);


    /**
     * 查询需要提醒的用户
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectRemindMessage(ClientUser clientUser);


   /**
    * 修改客户端用户
    *
    * @param clientUser
    *           客户端用户信息
    * @return 结果
    */
   public int updateClientUserList( ClientUser clientUser );


   /**
     * 查询客户端用户列表
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserList(ClientUser clientUser);

    public List<ClientUser> selectClientUserListFullDisplay(ClientUser clientUser);


    /**
     * 查询客户端用户列表 外键关联
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserListAssoc(ClientUser clientUser);


    /**
     * 新增客户端用户
     *
     * @param clientUser 客户端用户信息
     * @return 结果
     */
    public int insertClientUser(ClientUser clientUser);


    /**
     * 修改客户端用户
     *
     * @param clientUser 客户端用户信息
     * @return 结果
     */
    public int updateClientUser(ClientUser clientUser);


    /**
     * 删除客户端用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteClientUserByIds(String ids);


    /**
     * 删除客户端用户信息
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteClientUserById(Long id);


    /**
     * 校验是否唯一
     *
     * @param clientUser
     * @return 结果
     */
    public String checkUserNameUnique(ClientUser clientUser);


    public ClientUser selectClientUserByUsername(String username);


    public ClientUser selectClientUserByPhone(String phone);

    public List<ClientUser> selectClientUserByRealName(String realName);


    Integer selectClientUserCountByMemberId(String memberId);
}
