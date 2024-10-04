package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Goods;
import io.goose.shooting.domain.GoodsAttribute;
import io.goose.shooting.domain.Gt;
import io.goose.shooting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
//import org.springframewor.security.access.prepost.PreAuthorize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.GoodsOrder;
import io.goose.shooting.service.IGoodsService;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商品订单 信息操作处理
 * 
 * @author goose
 * @date 2021-03-19
 */
@RestController
@RequestMapping("/shooting/goodsOrder")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商品订单 ",description="商品订单")
public class GoodsOrderRestController extends BaseController
{

	@Autowired
	private IGoodsOrderService goodsOrderService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IGoodsAttributeService attributeService;

	@Autowired
	private IGoodsAddressService goodsAddressService;

	
	
	/**
	 * 查询商品订单列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商品订单列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsOrder", value = "商品订单", required = false, dataType = "GoodsOrder")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsOrder goodsOrder)
	{
		startPage();
		
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
		return getDataTable(list);
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")		
	public GoodsOrder getById( @PathVariable("id") Long id) {
	    GoodsOrder goodsOrder =	goodsOrderService.selectGoodsOrderById(id);
	    if(goodsOrder != null) {
	    	goodsOrder.setGoodsAddress(goodsAddressService.selectGoodsAddressById(goodsOrder.getGoodsAddressId()));
	    	goodsOrder.setGoods(goodsService.selectGoodsById(goodsOrder.getGoodsId()));
			Gt gt=goodsOrder.getGt();
			GoodsAttribute goodsAttribute=new GoodsAttribute();
			StringBuilder sb=new StringBuilder("");
			if(gt.getAttributeId1()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId1());
				sb.append(goodsAttribute.getName());
			}
			if(gt.getAttributeId2()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId2());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId3()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId3());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId4()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId4());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId5()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId5());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId6()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId6());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId7()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId7());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId8()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId8());
				sb.append("/"+goodsAttribute.getName());
			}
			if(gt.getAttributeId9()!=null){
				goodsAttribute=attributeService.selectGoodsAttributeById(gt.getAttributeId9());
				sb.append("/"+goodsAttribute.getName());
			}
			goodsOrder.setAttributes(sb.toString());
	    }
		return goodsOrder;
	}
	
	/**
	 * 导出商品订单列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商品订单列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsOrder", value = "商品订单", required = false, dataType = "GoodsOrder")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsOrder goodsOrder)
    {
    	List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        return util.exportExcel(list, "goodsOrder");
    } */
    
    /**
	 * 导入商品订单列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商品订单列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商品订单文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsOrder> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsOrder.class);
        	for(GoodsOrder obj : list) {
        		goodsOrderService.insertGoodsOrder(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商品订单
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商品订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsOrder", value = "商品订单", required = true, dataType = "GoodsOrder")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsOrder goodsOrder)
	{
		return toAjax(goodsOrderService.insertGoodsOrder(goodsOrder));
	}
	
	/**
	 * 修改保存商品订单
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商品订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsOrder", value = "商品订单", required = true, dataType = "GoodsOrder")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsOrder goodsOrder)
	{
		return toAjax(goodsOrderService.updateGoodsOrder(goodsOrder));
	}
	
	/**
	 * 删除商品订单
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商品订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsOrderService.deleteGoodsOrderByIds(ids));
	}
	
		/**
	 * 根据id删除商品订单
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商品订单")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsOrderService.deleteGoodsOrderById(id));
	}
	
	
		/**
	 * 查询商品订单分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商品订单分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsOrder", value = "商品订单", required = true, dataType = "GoodsOrder")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsOrder goodsOrder)
	{
		startPage(goodsOrder.getPd());	
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        list.forEach(goodsOrder1 -> {
			Goods goods=goodsService.selectGoodsById(goodsOrder1.getGoodsId());
			goodsOrder1.setGoods(goods);
		});
		return getDataTable(list);
	}



	/**
	 * 查询用户订单状态统计
	 */
	@PostMapping("/list/selectGoodStatusByUserId")
	public Map<String,Integer> selectGoodStatusByUserId(@RequestBody GoodsOrder goodsOrder)
	{
		List<GoodsOrder> list = goodsOrderService.selectGoodStatusByUserId(goodsOrder.getClientUserId());
		Map<String,Integer> map=new HashMap();
		list.forEach(goodsOrder1 -> {
			if(goodsOrder1.getGoodsOrderStatus()!=null){
				if(goodsOrder1.getGoodsOrderStatus()==1){
					map.put("待支付",goodsOrder1.getNum());
				}
				if(goodsOrder1.getGoodsOrderStatus()==3){
					map.put("待发货",goodsOrder1.getNum());
				}
				if(goodsOrder1.getGoodsOrderStatus()==4){
					map.put("待收货",goodsOrder1.getNum());
				}
				if(goodsOrder1.getGoodsOrderStatus()==5){
					map.put("待评价",goodsOrder1.getNum());
				}
			}
		});
		return map;
	}





}
