package io.goose.shooting.mapper;

import io.goose.shooting.domain.BusinessInvestment;

import java.util.List;

/**
 * 招商 数据层
 *
 * @author goose
 * @date 2020-06-18
 */
public interface BusinessInvestmentMapper {
    /**
     * 查询招商信息
     *
     * @param id 招商ID
     * @return 招商信息
     */
    public BusinessInvestment selectBusinessInvestmentById(Long id);

    /**
     * 查询招商信息 外键关联
     *
     * @param id 招商ID
     * @return 招商信息
     */
    public BusinessInvestment selectBusinessInvestmentByIdAssoc(Long id);

    /**
     * 查询所有招商列表
     *
     * @param
     * @return 招商集合
     */
    public List<BusinessInvestment> selectBusinessInvestmentAll();

    /**
     * 查询所有招商列表 外键关联
     *
     * @param
     * @return 招商集合
     */
    public List<BusinessInvestment> selectBusinessInvestmentAllAssoc();


    /**
     * 查询招商列表
     *
     * @param businessInvestment 招商信息
     * @return 招商集合
     */
    public List<BusinessInvestment> selectBusinessInvestmentList(BusinessInvestment businessInvestment);

    /**
     * 查询招商列表 外键关联
     *
     * @param businessInvestment 招商信息
     * @return 招商集合
     */
    public List<BusinessInvestment> selectBusinessInvestmentListAssoc(BusinessInvestment businessInvestment);

    /**
     * 新增招商
     *
     * @param businessInvestment 招商信息
     * @return 结果
     */
    public int insertBusinessInvestment(BusinessInvestment businessInvestment);

    /**
     * 修改招商
     *
     * @param businessInvestment 招商信息
     * @return 结果
     */
    public int updateBusinessInvestment(BusinessInvestment businessInvestment);

    /**
     * 删除招商
     *
     * @param id 招商ID
     * @return 结果
     */
    public int deleteBusinessInvestmentById(Long id);

    /**
     * 批量删除招商
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusinessInvestmentByIds(String[] ids);

}