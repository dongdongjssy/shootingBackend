package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.BusinessInvestmentMapper;
import io.goose.shooting.domain.BusinessInvestment;

import io.goose.shooting.service.IBusinessInvestmentService;
import io.goose.common.support.Convert;

/**
 * 招商 服务层实现
 * 
 * @author goose
 * @date 2020-06-18
 */
@Service
public class BusinessInvestmentServiceImpl implements IBusinessInvestmentService 
{
	@Autowired
	private BusinessInvestmentMapper businessInvestmentMapper;


	/**
     * 查询招商信息
     * 
     * @param id 招商ID
     * @return 招商信息
     */
    @Override
	public BusinessInvestment selectBusinessInvestmentById(Long id)
	{
	    return businessInvestmentMapper.selectBusinessInvestmentById(id);
	}
	
	/**
     * 查询招商信息 外键关联
     * 
     * @param id 招商ID
     * @return 招商信息
     */
    @Override
	public BusinessInvestment selectBusinessInvestmentByIdAssoc(Long id)
	{
	    return businessInvestmentMapper.selectBusinessInvestmentByIdAssoc(id);
	}	
	
	/**
     * 查询所有招商列表
     * 
     * @param 
     * @return 招商集合
     */
	@Override 
	public List<BusinessInvestment> selectBusinessInvestmentAll()
	{
		return businessInvestmentMapper.selectBusinessInvestmentAll();
	}	
	
	/**
     * 查询所有招商列表 外键关联
     * 
     * @param 
     * @return 招商集合
     */
	@Override 
	public List<BusinessInvestment> selectBusinessInvestmentAllAssoc()
	{
		return businessInvestmentMapper.selectBusinessInvestmentAllAssoc();
	}		
	
	/**
     * 查询招商列表
     * 
     * @param businessInvestment 招商信息
     * @return 招商集合
     */
	@Override
	public List<BusinessInvestment> selectBusinessInvestmentList(BusinessInvestment businessInvestment)
	{
	    return businessInvestmentMapper.selectBusinessInvestmentList(businessInvestment);
	}
	
	/**
     * 查询招商列表 外键关联
     * 
     * @param businessInvestment 招商信息
     * @return 招商集合
     */
	@Override
	public List<BusinessInvestment> selectBusinessInvestmentListAssoc(BusinessInvestment businessInvestment)
	{
	    return businessInvestmentMapper.selectBusinessInvestmentListAssoc(businessInvestment);
	}	
	
    /**
     * 新增招商
     * 
     * @param businessInvestment 招商信息
     * @return 结果
     */
	@Override
	public int insertBusinessInvestment(BusinessInvestment businessInvestment)
	{
	    return businessInvestmentMapper.insertBusinessInvestment(businessInvestment);
	}
	
	/**
     * 修改招商
     * 
     * @param businessInvestment 招商信息
     * @return 结果
     */
	@Override
	public int updateBusinessInvestment(BusinessInvestment businessInvestment)
	{
	    return businessInvestmentMapper.updateBusinessInvestment(businessInvestment);
	}

	/**
     * 删除招商对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessInvestmentByIds(String ids)
	{
		return businessInvestmentMapper.deleteBusinessInvestmentByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除招商对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessInvestmentById(Long id)
	{
		return businessInvestmentMapper.deleteBusinessInvestmentById(id);
	}
	
	



}
