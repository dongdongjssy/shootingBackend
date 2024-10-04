package io.goose.shooting.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.Gt;
import io.goose.shooting.mapper.GtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.GoodsOrderMapper;
import io.goose.shooting.domain.GoodsOrder;

import io.goose.shooting.service.IGoodsOrderService;
import io.goose.common.support.Convert;

/**
 * 商品订单 服务层实现
 * 
 * @author goose
 * @date 2021-03-19
 */
@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService 
{
	@Autowired
	private GoodsOrderMapper goodsOrderMapper;
	@Autowired
	private GtMapper gtMapper;


	private static final int[] R = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};

	/**
     * 查询商品订单信息
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
    @Override
	public GoodsOrder selectGoodsOrderById(Long id)
	{
	    return goodsOrderMapper.selectGoodsOrderById(id);
	}
	
	/**
     * 查询商品订单信息 外键关联
     * 
     * @param id 商品订单ID
     * @return 商品订单信息
     */
    @Override
	public GoodsOrder selectGoodsOrderByIdAssoc(Long id)
	{
	    return goodsOrderMapper.selectGoodsOrderByIdAssoc(id);
	}	
	
	/**
     * 查询所有商品订单列表
     * 
     * @param 
     * @return 商品订单集合
     */
	@Override 
	public List<GoodsOrder> selectGoodsOrderAll()
	{
		return goodsOrderMapper.selectGoodsOrderAll();
	}	
	
	/**
     * 查询所有商品订单列表 外键关联
     * 
     * @param 
     * @return 商品订单集合
     */
	@Override 
	public List<GoodsOrder> selectGoodsOrderAllAssoc()
	{
		return goodsOrderMapper.selectGoodsOrderAllAssoc();
	}		
	
	/**
     * 查询商品订单列表
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	@Override
	public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder)
	{
	    return goodsOrderMapper.selectGoodsOrderList(goodsOrder);
	}
	
	/**
     * 查询商品订单列表 外键关联
     * 
     * @param goodsOrder 商品订单信息
     * @return 商品订单集合
     */
	@Override
	public List<GoodsOrder> selectGoodsOrderListAssoc(GoodsOrder goodsOrder)
	{
	    return goodsOrderMapper.selectGoodsOrderListAssoc(goodsOrder);
	}	
	
    /**
     * 新增商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	@Override
	public int insertGoodsOrder(GoodsOrder goodsOrder)
	{
		String orderNum=getOrderCode(goodsOrder.getClientUserId());
		goodsOrder.setTitle(orderNum);
		goodsOrder.setCreateBy(goodsOrder.getClientUserId().toString());
		if(StringUtils.isNotBlank(goodsOrder.getAttributes())){
			String attributes=goodsOrder.getAttributes();
			String[] attrs=	attributes.split("/");
			Gt gt=new Gt();
			gt.setGoodsId(goodsOrder.getGoodsId());
			for (int i=0;i<attrs.length;i++){
				switch (i){
					case 0:
						gt.setAttributeId1(Long.parseLong(attrs[0]));
						break;
					case 1:
						gt.setAttributeId2(Long.parseLong(attrs[1]));
						break;
					case 2:
						gt.setAttributeId3(Long.parseLong(attrs[2]));
						break;
					case 3:
						gt.setAttributeId4(Long.parseLong(attrs[3]));
						break;
					case 4:
						gt.setAttributeId5(Long.parseLong(attrs[4]));
						break;
					case 5:
						gt.setAttributeId6(Long.parseLong(attrs[5]));
						break;
					case 6:
						gt.setAttributeId7(Long.parseLong(attrs[6]));
						break;
					case 7:
						gt.setAttributeId8(Long.parseLong(attrs[7]));
						break;
					case 8:
						gt.setAttributeId9(Long.parseLong(attrs[8]));
						break;
				}
			}
			List<Gt> gtList=gtMapper.selectGtByGoodsIdAndAttribute(gt);
			if(!gtList.isEmpty()){
				goodsOrder.setGtId(gtList.get(0).getId());
				gt.setId(gtList.get(0).getId());
				gt.setNum(goodsOrder.getNum());
				gtMapper.updateGtNums(gt);
			}
		}
		goodsOrder.setId(null);
		goodsOrder.setPlaceTime(new Date());
		goodsOrder.setCreateTime(new Date());
		goodsOrder.setGoodsOrderStatus(1);
	    return goodsOrderMapper.insertGoodsOrder(goodsOrder);
	}
	
	/**
     * 修改商品订单
     * 
     * @param goodsOrder 商品订单信息
     * @return 结果
     */
	@Override
	public int updateGoodsOrder(GoodsOrder goodsOrder)
	{
		/*if(StringUtils.isNotBlank(goodsOrder.getAttributes())){
			String attributes=goodsOrder.getAttributes();
			String[] attrs=	attributes.split("/");
			Gt gt=new Gt();
			gt.setGoodsId(goodsOrder.getGoodsId());
			for (int i=0;i<attrs.length - 1;i++){
				switch (i){
					case 0:
						gt.setAttributeId1(Long.parseLong(attrs[0]));
						break;
					case 1:
						gt.setAttributeId2(Long.parseLong(attrs[1]));
						break;
					case 2:
						gt.setAttributeId3(Long.parseLong(attrs[2]));
						break;
					case 3:
						gt.setAttributeId4(Long.parseLong(attrs[3]));
						break;
					case 4:
						gt.setAttributeId5(Long.parseLong(attrs[4]));
						break;
					case 5:
						gt.setAttributeId6(Long.parseLong(attrs[5]));
						break;
					case 6:
						gt.setAttributeId7(Long.parseLong(attrs[6]));
						break;
					case 7:
						gt.setAttributeId8(Long.parseLong(attrs[7]));
						break;
					case 8:
						gt.setAttributeId9(Long.parseLong(attrs[8]));
						break;
				}
			}
			List<Gt> gtList=gtMapper.selectGtByGoodsIdAndAttribute(gt);
			if(!gtList.isEmpty()){
				goodsOrder.setGtId(gtList.get(0).getId());
				gt.setId(gtList.get(0).getId());
				gt.setNum(goodsOrder.getNum());
				gtMapper.updateGtNum(gt);
			}
		}*/
		if(goodsOrder.getGoodsOrderStatus()==2){
			goodsOrder.setPayTime(new Date());
		}
		if(goodsOrder.getGoodsOrderStatus()==4){
			goodsOrder.setDeliveryTime(new Date());
		}
		if(goodsOrder.getGoodsOrderStatus()==5){
			goodsOrder.setGoodsTime(new Date());
		}
		if(goodsOrder.getGoodsOrderStatus()==7){
			goodsOrder.setCancelTime(new Date());
		}
		return goodsOrderMapper.updateGoodsOrder(goodsOrder);
	}

	/**
     * 删除商品订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsOrderByIds(String ids)
	{
		return goodsOrderMapper.deleteGoodsOrderByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 删除商品订单对象
     * 
     * @param id 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsOrderById(Long id)
	{
		return goodsOrderMapper.deleteGoodsOrderById(id);
	}

	@Override
	public List<GoodsOrder> selectGoodStatusByUserId(Long userId) {
		return goodsOrderMapper.selectGoodStatusByUserId(userId);
	}


	/**
	 * 生成时间戳
	 */
	private static String getDateTime(){
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}


	/**
	 * 生成固定长度随机码
	 * @param n 长度
	 */
	private static long getRandom(long n) {
		long min = 1,max = 9;
		for (int i = 1; i < n; i++) {
			min *= 10;
			max *= 10;
		}
		long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
		return rangeLong;
	}


	/**
	 * 生成不带类别标头的编码
	 * @param userId
	 */
	private static synchronized String getCode(Long userId){
		userId = userId == null ? 10000 : userId;
		return getDateTime() + toCode(userId);
	}


	private static String toCode(Long id) {
		String idStr = id.toString();
		StringBuilder idsbs = new StringBuilder();
/*		for (int i = idStr.length() - 1 ; i >= 0; i--) {
			idsbs.append(R[idStr.charAt(i)-'0']);
		}*/
		return idsbs.append(getRandom(7- idStr.length())).toString();
	}


	/**
	 * 生成订单单号编码
	 * @param userId
	 */
	public static String getOrderCode(Long userId){
		return getCode(userId);
	}

	@Override
	public List<GoodsOrder> selectGoodsOrdersByOrderId(Long orderId) {
		return goodsOrderMapper.selectGoodsOrdersByOrderId(orderId);
	}
}
