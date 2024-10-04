package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Stats;

import java.util.List;


/**
 * 客户端用户 数据层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface ClientUserMapper {

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


    /**
     * 查询所有客户端用户列表 外键关联
     *
     * @param
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserAllAssoc();


    /**
     * 查询客户端用户列表
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserList(ClientUser clientUser);


    /**
     * 查询客户端用户列表 外键关联
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserListAssoc(ClientUser clientUser);



    /**
     * 查询客户端临期用户列表
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectClientUserListRemind(ClientUser clientUser);


    /**
     * 查询需要发送提醒的用户
     *
     * @param clientUser 客户端用户信息
     * @return 客户端用户集合
     */
    public List<ClientUser> selectRemindMessage(ClientUser clientUser);


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


    public int updateClientUserList(ClientUser clientUser);

    /**
     * 删除客户端用户
     *
     * @param id 客户端用户ID
     * @return 结果
     */
    public int deleteClientUserById(Long id);


    /**
     * 批量删除客户端用户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteClientUserByIds(String[] ids);



    int selectClientUserCountByMemberId(String memberId);


    /**
     * 校验是否唯一
     *
     * @param clientUser
     * @return 结果
     */
    public ClientUser checkUserNameUnique(ClientUser clientUser);

    public ClientUser selectClientUserByPhone(String phone);

    public ClientUser selectClientUserByUsername(String userName);

    public List<ClientUser> selectClientUserByRealName(String realName);


	public List<Stats> selectClientUserAllByIsVip();
}
