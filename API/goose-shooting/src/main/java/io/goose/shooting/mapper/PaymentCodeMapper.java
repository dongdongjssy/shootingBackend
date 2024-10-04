package io.goose.shooting.mapper;

import io.goose.shooting.domain.PaymentCode;
import java.util.List;	

/**
 * 付款码管理 数据层
 * 
 * @author goose
 * @date 2021-01-21
 */
public interface PaymentCodeMapper 
{
	/**
     * 查询付款码管理信息
     * 
     * @param id 付款码管理ID
     * @return 付款码管理信息
     */
	public PaymentCode selectPaymentCodeById(Long id);
	
	/**
     * 查询付款码管理信息 外键关联
     * 
     * @param id 付款码管理ID
     * @return 付款码管理信息
     */
	public PaymentCode selectPaymentCodeByIdAssoc(Long id);	

	/**
     * 查询所有付款码管理列表
     * 
     * @param 
     * @return 付款码管理集合
     */
	public List<PaymentCode> selectPaymentCodeAll();	
	
	/**
     * 查询所有付款码管理列表 外键关联
     * 
     * @param 
     * @return 付款码管理集合
     */
	public List<PaymentCode> selectPaymentCodeAllAssoc();		

	
	/**
     * 查询付款码管理列表
     * 
     * @param paymentCode 付款码管理信息
     * @return 付款码管理集合
     */
	public List<PaymentCode> selectPaymentCodeList(PaymentCode paymentCode);
	
	/**
     * 查询付款码管理列表 外键关联
     * 
     * @param paymentCode 付款码管理信息
     * @return 付款码管理集合
     */
	public List<PaymentCode> selectPaymentCodeListAssoc(PaymentCode paymentCode);	
	
	/**
     * 新增付款码管理
     * 
     * @param paymentCode 付款码管理信息
     * @return 结果
     */
	public int insertPaymentCode(PaymentCode paymentCode);
	
	/**
     * 修改付款码管理
     * 
     * @param paymentCode 付款码管理信息
     * @return 结果
     */
	public int updatePaymentCode(PaymentCode paymentCode);
	
	/**
     * 删除付款码管理
     * 
     * @param id 付款码管理ID
     * @return 结果
     */
	public int deletePaymentCodeById(Long id);
	
	/**
     * 批量删除付款码管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deletePaymentCodeByIds(String[] ids);
	
}