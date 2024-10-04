package io.goose.shooting.mapper;

import io.goose.shooting.domain.Advertisement;

import java.util.List;

/**
 * 广告 数据层
 *
 * @author goose
 * @date 2020-04-28
 */
public interface AdvertisementMapper {
    /**
     * 查询广告信息
     *
     * @param id 广告ID
     * @return 广告信息
     */
    public Advertisement selectAdvertisementById(Long id);

    /**
     * 查询广告信息 外键关联
     *
     * @param id 广告ID
     * @return 广告信息
     */
    public Advertisement selectAdvertisementByIdAssoc(Long id);

    /**
     * 查询所有广告列表
     *
     * @param
     * @return 广告集合
     */
    public List<Advertisement> selectAdvertisementAll();

    /**
     * 查询所有广告列表 外键关联
     *
     * @param
     * @return 广告集合
     */
    public List<Advertisement> selectAdvertisementAllAssoc();


    /**
     * 查询广告列表
     *
     * @param advertisement 广告信息
     * @return 广告集合
     */
    public List<Advertisement> selectAdvertisementList(Advertisement advertisement);

    /**
     * 查询广告列表 外键关联
     *
     * @param advertisement 广告信息
     * @return 广告集合
     */
    public List<Advertisement> selectAdvertisementListAssoc(Advertisement advertisement);

    /**
     * 新增广告
     *
     * @param advertisement 广告信息
     * @return 结果
     */
    public int insertAdvertisement(Advertisement advertisement);

    /**
     * 修改广告
     *
     * @param advertisement 广告信息
     * @return 结果
     */
    public int updateAdvertisement(Advertisement advertisement);

    /**
     * 删除广告
     *
     * @param id 广告ID
     * @return 结果
     */
    public int deleteAdvertisementById(Long id);

    /**
     * 批量删除广告
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisementByIds(String[] ids);

}