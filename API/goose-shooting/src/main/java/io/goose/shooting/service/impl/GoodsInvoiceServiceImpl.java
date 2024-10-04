package io.goose.shooting.service.impl;

import java.util.List;

import io.goose.shooting.domain.GoodsAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsInvoiceMapper;
import io.goose.shooting.domain.GoodsInvoice;

import io.goose.shooting.service.IGoodsInvoiceService;
import io.goose.common.support.Convert;

/**
 * 发票 服务层实现
 * 
 * @author goose
 * @date 2021-02-23
 */
@Service
public class GoodsInvoiceServiceImpl implements IGoodsInvoiceService 
{
	@Autowired
	private GoodsInvoiceMapper goodsInvoiceMapper;


	/**
     * 查询发票信息
     * 
     * @param id 发票ID
     * @return 发票信息
     */
    @Override
	public GoodsInvoice selectGoodsInvoiceById(Long id)
	{
	    return goodsInvoiceMapper.selectGoodsInvoiceById(id);
	}
	
	/**
     * 查询发票信息 外键关联
     * 
     * @param id 发票ID
     * @return 发票信息
     */
    @Override
	public GoodsInvoice selectGoodsInvoiceByIdAssoc(Long id)
	{
	    return goodsInvoiceMapper.selectGoodsInvoiceByIdAssoc(id);
	}	
	
	/**
     * 查询所有发票列表
     * 
     * @param 
     * @return 发票集合
     */
	@Override 
	public List<GoodsInvoice> selectGoodsInvoiceAll()
	{
		return goodsInvoiceMapper.selectGoodsInvoiceAll();
	}	
	
	/**
     * 查询所有发票列表 外键关联
     * 
     * @param 
     * @return 发票集合
     */
	@Override 
	public List<GoodsInvoice> selectGoodsInvoiceAllAssoc()
	{
		return goodsInvoiceMapper.selectGoodsInvoiceAllAssoc();
	}		
	
	/**
     * 查询发票列表
     * 
     * @param goodsInvoice 发票信息
     * @return 发票集合
     */
	@Override
	public List<GoodsInvoice> selectGoodsInvoiceList(GoodsInvoice goodsInvoice)
	{
	    return goodsInvoiceMapper.selectGoodsInvoiceList(goodsInvoice);
	}
	
	/**
     * 查询发票列表 外键关联
     * 
     * @param goodsInvoice 发票信息
     * @return 发票集合
     */
	@Override
	public List<GoodsInvoice> selectGoodsInvoiceListAssoc(GoodsInvoice goodsInvoice)
	{
	    return goodsInvoiceMapper.selectGoodsInvoiceListAssoc(goodsInvoice);
	}	
	
    /**
     * 新增发票
     * 
     * @param goodsInvoice 发票信息
     * @return 结果
     */
	@Override
	public int insertGoodsInvoice(GoodsInvoice goodsInvoice)
	{
		if(goodsInvoice.getDefaultInvoice()!=null&&goodsInvoice.getDefaultInvoice()==0){
			GoodsInvoice ga=new GoodsInvoice();
			ga.setClientUserId(goodsInvoice.getClientUserId());
			goodsInvoiceMapper.updateGoodsDefaultInvoice(ga);
		}
	    return goodsInvoiceMapper.insertGoodsInvoice(goodsInvoice);
	}
	
	/**
     * 修改发票
     * 
     * @param goodsInvoice 发票信息
     * @return 结果
     */
	@Override
	public int updateGoodsInvoice(GoodsInvoice goodsInvoice)
	{
		if(goodsInvoice.getDefaultInvoice()!=null&&goodsInvoice.getDefaultInvoice()==0){
			GoodsInvoice ga=new GoodsInvoice();
			ga.setClientUserId(goodsInvoice.getClientUserId());
			goodsInvoiceMapper.updateGoodsDefaultInvoice(ga);
		}
	    return goodsInvoiceMapper.updateGoodsInvoice(goodsInvoice);
	}

	/**
     * 删除发票对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsInvoiceByIds(String ids)
	{
		return goodsInvoiceMapper.deleteGoodsInvoiceByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除发票对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsInvoiceById(Long id)
	{
		return goodsInvoiceMapper.deleteGoodsInvoiceById(id);
	}
	
	



}
