package io.goose.shooting.service;

import io.goose.shooting.domain.GoodsInvoice;
import java.util.List;

/**
 * 发票 服务层
 * 
 * @author goose
 * @date 2021-02-23
 */
public interface IGoodsInvoiceService 
{
	/**
     * 查询发票信息
     * 
     * @param id 发票ID
     * @return 发票信息
     */
	public GoodsInvoice selectGoodsInvoiceById(Long id);
	
	/**
     * 查询发票信息 外键关联
     * 
     * @param id 发票ID
     * @return 发票信息
     */
	public GoodsInvoice selectGoodsInvoiceByIdAssoc(Long id);	
	
	/**
     * 查询所有发票列表
     * 
     * @param 
     * @return 发票集合
     */
	public List<GoodsInvoice> selectGoodsInvoiceAll();		
	
	/**
     * 查询所有发票列表  外键关联
     * 
     * @param 
     * @return 发票集合
     */
	public List<GoodsInvoice> selectGoodsInvoiceAllAssoc();		
	
	/**
     * 查询发票列表
     * 
     * @param goodsInvoice 发票信息
     * @return 发票集合
     */
	public List<GoodsInvoice> selectGoodsInvoiceList(GoodsInvoice goodsInvoice);
	
	/**
     * 查询发票列表 外键关联
     * 
     * @param goodsInvoice 发票信息
     * @return 发票集合
     */
	public List<GoodsInvoice> selectGoodsInvoiceListAssoc(GoodsInvoice goodsInvoice);	
	
	/**
     * 新增发票
     * 
     * @param goodsInvoice 发票信息
     * @return 结果
     */
	public int insertGoodsInvoice(GoodsInvoice goodsInvoice);
	
	/**
     * 修改发票
     * 
     * @param goodsInvoice 发票信息
     * @return 结果
     */
	public int updateGoodsInvoice(GoodsInvoice goodsInvoice);
		
	/**
     * 删除发票信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsInvoiceByIds(String ids);
	
	/**
     * 删除发票信息
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsInvoiceById(Long id);
	

  
}
