package io.goose.api.controller.shooting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import org.springframework.web.multipart.MultipartFile;


/**
 * 订单 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/shooting/order")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "订单 ",description="订单")
public class OrderRestController extends BaseController
{

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IGoodsOrderService goodsOrderService;

	@Autowired
	private IPaymentCodeService paymentCodeService;
	@Autowired
	private IGtService gtService;
	@Autowired
	private IGoodsAddressService goodsAddressService;
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	private static final int[] R = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};

	private static final Logger log = LoggerFactory.getLogger(PostRestController.class);

	/**
	 * 查询订单列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询订单列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "order", value = "订单", required = false, dataType = "Order")
	})
	public TableDataInfo list( @RequestBody  Order order)
	{
		startPage();
		
        List<Order> list = orderService.selectOrderList(order);
		return getDataTable(list);
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	public Order getById( @PathVariable("id") Long id) {
		Order order= orderService.selectOrderById(id);
		GoodsOrder goodsOrder=new GoodsOrder();
		goodsOrder.setOrder(order);
		List<GoodsOrder> goodsOrderList=goodsOrderService.selectGoodsOrderList(goodsOrder);
		GoodsAddress goodsAddress=goodsAddressService.selectGoodsAddressById(goodsOrderList.get(0).getGoodsAddressId());
		order.setName(goodsAddress.getName());
		order.setGoodsOrderList(goodsOrderList);
		return order;
	}
	

	
	/**
	 * 新增保存订单
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "order", value = "订单", required = true, dataType = "Order")
	})
	@Transactional(rollbackFor = Exception.class)
	public AjaxResult add( @RequestBody Order order)
	{
		StringBuilder ss= new StringBuilder();
		String orderNum=getOrderCode(order.getClientUserId());
		order.setTitle(orderNum);
		order.setCreateBy(order.getClientUserId().toString());
		PaymentCode paymentCode=new PaymentCode();
		paymentCode.setType(6);
		List<PaymentCode> paymentCodes=paymentCodeService.selectPaymentCodeList(paymentCode);
		if(!paymentCodes.isEmpty()){
			order.setPictureUrl(paymentCodes.get(0).getPictureUrl());
			order.setComment(paymentCodes.get(0).getDetail());
		}
		order.setGoodsOrderStatus(1);
		int success=orderService.insertOrder(order);
		Long orderId = order.getId();
		order.getGoodsOrderList().stream().forEach(goodsOrder -> {
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
				List<Gt> gtList=gtService.selectGtByGoodsIdAndAttribute(gt);
				if(!gtList.isEmpty()){
					if((gtList.get(0).getNum()-goodsOrder.getNum())<0){
						ss.append("订单号为"+orderNum+"的商品剩余库存小鱼购买数量");
					}
				}
			}
			goodsOrder.setOrderId((long) orderId);
			int s=goodsOrderService.insertGoodsOrder(goodsOrder);
			if(s==0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			if(StringUtils.isNotBlank(ss)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}

		});
		if(success > 0) {
			AjaxResult res = new AjaxResult();
			res.put("code", 0);
			res.put("orderId", orderId);
			res.put("warn",ss);
			return res;
		}else {
			return AjaxResult.error();
		}
	}
	
	/**
	 * 修改保存订单
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "order", value = "订单", required = true, dataType = "Order")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Order order)
	{

		order.setGoodsOrderStatus(2);
		int success=orderService.updateOrder(order);
		GoodsOrder goodsOrder=new GoodsOrder();
		goodsOrder.setOrderId(order.getId());
		List<GoodsOrder> goodsOrderList=goodsOrderService.selectGoodsOrderList(goodsOrder);
		if(!goodsOrderList.isEmpty()){
			goodsOrderList.stream().forEach(go -> {
				go.setGoodsOrderStatus(2);
				int ss=goodsOrderService.updateGoodsOrder(go);
				if(ss==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			});
		}

		return toAjax(success);
	}
	
	/**
	 * 删除订单
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(orderService.deleteOrderByIds(ids));
	}
	
		/**
	 * 根据id删除订单
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(orderService.deleteOrderById(id));
	}
	
	
		/**
	 * 查询订单分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询订单分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "order", value = "订单", required = true, dataType = "Order")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Order order)
	{
		startPage(order.getPd());	
        List<Order> list = orderService.selectOrderList(order);
		return getDataTable(list);
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
		/* for (int i = idStr.length() - 1 ; i >= 0; i--) {
			 idsbs.append(R[idStr.charAt(i)-'0']);
		 }*/
		return idsbs.append(getRandom(7 - idStr.length())).toString();
 	}


	/**
	  * 生成订单单号编码
	  * @param userId
	  */
	public static String getOrderCode(Long userId){
		return getCode(userId);
	}



	/**
	 * 取消订单
	 */
	@PostMapping("/cancle")
	@ApiOperation(value="修改保存订单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="body", name = "order", value = "订单", required = true, dataType = "Order")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult cancle( @RequestBody Order order1)
	{
		order1.setGoodsOrderStatus(7);
		int s= orderService.updateOrder(order1);
		GoodsOrder goodsOrder=new GoodsOrder();
		goodsOrder.setOrderId(order1.getId());
		List<GoodsOrder> goodsOrderList=goodsOrderService.selectGoodsOrderList(goodsOrder);
		goodsOrderList.forEach(goodsOrder1 -> {
			goodsOrder1.setGoodsOrderStatus(7);
		int i=	goodsOrderService.updateGoodsOrder(goodsOrder1);
		});
		return toAjax(s);
	}


	/**
	 * 查询动态分页列表
	 */
	@PostMapping("/uploadPic")
	@ApiOperation(value = "图片上传")
	@ResponseBody
	// @PreAuthorize("hasAnyRole('USER','ADMIN')")
	public  AjaxResult uploadPic(@RequestParam(value = "image1File", required = false) MultipartFile image1File, Order order){
		try {
			if (image1File != null && !image1File.isEmpty()) {
				String filePath = fileUploadDownloadService.uploadAliOss(image1File, null, UploadTypeEnums.Order.getValue(),true);
				order.setPicture(filePath);
			}

		} catch (Exception e) {
			log.error("媒体上传失败！", e);
		}
		return toAjax(	orderService.updateOrder(order));

	}

	
}
