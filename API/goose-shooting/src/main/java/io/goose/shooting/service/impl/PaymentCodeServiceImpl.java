package io.goose.shooting.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.PaymentCodeMapper;
import io.goose.shooting.domain.PaymentCode;

import io.goose.shooting.service.IPaymentCodeService;
import io.goose.common.support.Convert;

/**
 * 付款码管理 服务层实现
 * 
 * @author goose
 * @date 2021-01-21
 */
@Service
public class PaymentCodeServiceImpl implements IPaymentCodeService 
{
	@Autowired
	private PaymentCodeMapper paymentCodeMapper;


	/**
     * 查询付款码管理信息
     * 
     * @param id 付款码管理ID
     * @return 付款码管理信息
     */
    @Override
	public PaymentCode selectPaymentCodeById(Long id)
	{
	    return paymentCodeMapper.selectPaymentCodeById(id);
	}
	
	/**
     * 查询付款码管理信息 外键关联
     * 
     * @param id 付款码管理ID
     * @return 付款码管理信息
     */
    @Override
	public PaymentCode selectPaymentCodeByIdAssoc(Long id)
	{
	    return paymentCodeMapper.selectPaymentCodeByIdAssoc(id);
	}	
	
	/**
     * 查询所有付款码管理列表
     * 
     * @param 
     * @return 付款码管理集合
     */
	@Override 
	public List<PaymentCode> selectPaymentCodeAll()
	{
		return paymentCodeMapper.selectPaymentCodeAll();
	}	
	
	/**
     * 查询所有付款码管理列表 外键关联
     * 
     * @param 
     * @return 付款码管理集合
     */
	@Override 
	public List<PaymentCode> selectPaymentCodeAllAssoc()
	{
		return paymentCodeMapper.selectPaymentCodeAllAssoc();
	}		
	
	/**
     * 查询付款码管理列表
     * 
     * @param paymentCode 付款码管理信息
     * @return 付款码管理集合
     */
	@Override
	public List<PaymentCode> selectPaymentCodeList(PaymentCode paymentCode)
	{
	    return paymentCodeMapper.selectPaymentCodeList(paymentCode);
	}
	
	/**
     * 查询付款码管理列表 外键关联
     * 
     * @param paymentCode 付款码管理信息
     * @return 付款码管理集合
     */
	@Override
	public List<PaymentCode> selectPaymentCodeListAssoc(PaymentCode paymentCode)
	{
	    return paymentCodeMapper.selectPaymentCodeListAssoc(paymentCode);
	}	
	
    /**
     * 新增付款码管理
     * 
     * @param paymentCode 付款码管理信息
     * @return 结果
     */
	@Override
	public int insertPaymentCode(PaymentCode paymentCode)
	{
	    return paymentCodeMapper.insertPaymentCode(paymentCode);
	}
	
	/**
     * 修改付款码管理
     * 
     * @param paymentCode 付款码管理信息
     * @return 结果
     */
	@Override
	public int updatePaymentCode(PaymentCode paymentCode)
	{
	    return paymentCodeMapper.updatePaymentCode(paymentCode);
	}

	/**
     * 删除付款码管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePaymentCodeByIds(String ids)
	{
		return paymentCodeMapper.deletePaymentCodeByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除付款码管理对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deletePaymentCodeById(Long id)
	{
		return paymentCodeMapper.deletePaymentCodeById(id);
	}
	
	



}
