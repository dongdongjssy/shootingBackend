package io.goose.shooting.service.impl;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.ClubCategory;
import io.goose.shooting.mapper.ClubCategoryMapper;
import io.goose.shooting.mapper.ClubMapper;
import io.goose.shooting.service.IClubService;
import io.goose.shooting.service.impl.ext.ClubCoachServiceImplExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 俱乐部 服务层实现
 *
 * @author goose
 * @date 2020-04-28
 */
@Service
public class ClubServiceImpl implements IClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ClubCoachServiceImplExt clubCoachService;

    @Autowired
    private ClubCategoryMapper clubCategoryMapper;


    /**
     * 查询俱乐部信息
     *
     * @param id 俱乐部ID
     * @return 俱乐部信息
     */
    @Override
    public Club selectClubById(Long id) {
        Club club = clubMapper.selectClubById(id);
        setCoachCount(club);
        return club;
    }


    /**
     * 查询俱乐部信息 外键关联
     *
     * @param id 俱乐部ID
     * @return 俱乐部信息
     */
    @Override
    public Club selectClubByIdAssoc(Long id) {
        Club club = clubMapper.selectClubByIdAssoc(id);
        setCoachCount(club);
        return club;
    }


    /**
     * 查询所有俱乐部列表
     *
     * @param
     * @return 俱乐部集合
     */
    @Override
    public List<Club> selectClubAll() {

        List<Club> clubList = clubMapper.selectClubAll();

        setCoachCount(clubList);
        return clubList;
    }


    /**
     * 查询所有俱乐部列表 外键关联
     *
     * @param
     * @return 俱乐部集合
     */
    @Override
    public List<Club> selectClubAllAssoc() {

        List<Club> clubList = clubMapper.selectClubAllAssoc();

        setCoachCount(clubList);
        return clubList;
    }


    /**
     * 查询俱乐部列表
     *
     * @param club 俱乐部信息
     * @return 俱乐部集合
     */
    @Override
    public List<Club> selectClubList(Club club) {
        List<Club> clubList = clubMapper.selectClubList(club);
        setCoachCount(clubList);
        return clubList;
    }


    /**
     * 查询俱乐部列表 外键关联
     *
     * @param club 俱乐部信息
     * @return 俱乐部集合
     */
    @Override
    public List<Club> selectClubListAssoc(Club club) {
        List<Club> clubList = clubMapper.selectClubListAssoc(club);
        setCoachCount(clubList);
        return clubList;
    }


    /**
     * 新增俱乐部
     *
     * @param club 俱乐部信息
     * @return 结果
     */
    @Override
    public int insertClub(Club club) {
        return clubMapper.insertClub(club);
    }


    /**
     * 修改俱乐部
     *
     * @param club 俱乐部信息
     * @return 结果
     */
    @Override
    public int updateClub(Club club) {
        return clubMapper.updateClub(club);
    }


    /**
     * 删除俱乐部对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClubByIds(String ids) {
        return clubMapper.deleteClubByIds(Convert.toStrArray(ids));
    }


    /**
     * 删除俱乐部对象
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClubById(Long id) {
        return clubMapper.deleteClubById(id);
    }


    @Override
    public List<ClubCategory> getClubCategories(String type) {
        return clubCategoryMapper.selectClubCategory(type);
    }

    @Override
    public List<Club> selectClubListWithUserId(Long userId) {
        return clubMapper.selectClubListWithUserId(userId);
    }

    @Override
    public List<Club> selectClubListByAreaOrFans(Club club) {
        return  clubMapper.selectClubListByAreaOrFans(club);
    }


    private void setCoachCount(List<Club> clubList) {
        clubList.forEach(club -> setCoachCount(club));
    }


    private void setCoachCount(Club club) {
        if (club != null && club.getId() != null) {
            club.setCoachCount(clubCoachService.selectCoachListByClubId(club.getId()).size());
        } else {
            club.setCoachCount(0);
        }
    }
}
