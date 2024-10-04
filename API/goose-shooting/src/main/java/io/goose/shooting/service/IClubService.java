package io.goose.shooting.service;

import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.ClubCategory;

import java.util.List;

/**
 * 俱乐部 服务层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface IClubService {
    /**
     * 查询俱乐部信息
     *
     * @param id 俱乐部ID
     * @return 俱乐部信息
     */
    public Club selectClubById(Long id);

    /**
     * 查询俱乐部信息 外键关联
     *
     * @param id 俱乐部ID
     * @return 俱乐部信息
     */
    public Club selectClubByIdAssoc(Long id);

    /**
     * 查询所有俱乐部列表
     *
     * @param
     * @return 俱乐部集合
     */
    public List<Club> selectClubAll();

    /**
     * 查询所有俱乐部列表  外键关联
     *
     * @param
     * @return 俱乐部集合
     */
    public List<Club> selectClubAllAssoc();

    /**
     * 查询俱乐部列表
     *
     * @param club 俱乐部信息
     * @return 俱乐部集合
     */
    public List<Club> selectClubList(Club club);

    /**
     * 查询俱乐部列表 外键关联
     *
     * @param club 俱乐部信息
     * @return 俱乐部集合
     */
    public List<Club> selectClubListAssoc(Club club);

    /**
     * 新增俱乐部
     *
     * @param club 俱乐部信息
     * @return 结果
     */
    public int insertClub(Club club);

    /**
     * 修改俱乐部
     *
     * @param club 俱乐部信息
     * @return 结果
     */
    public int updateClub(Club club);

    /**
     * 删除俱乐部信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteClubByIds(String ids);

    /**
     * 删除俱乐部信息
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    public int deleteClubById(Long id);

    public List<ClubCategory> getClubCategories(String type);

    public List<Club> selectClubListWithUserId(Long userId);

    /**
     * 查询热推俱乐部列表
     *
     * @param club
     *       俱乐部信息
     * @return 俱乐部集合
     */
    public List<Club> selectClubListByAreaOrFans(Club club);

}
