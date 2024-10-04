package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Gt;
import io.goose.shooting.service.IGtService;
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
import io.goose.shooting.domain.GoodsAttribute;
import io.goose.shooting.domain.GoodsShoppingCart;
import io.goose.shooting.service.IGoodsAttributeService;
import io.goose.shooting.service.IGoodsService;
import io.goose.shooting.service.IGoodsShoppingCartService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.StringUtils;
import io.goose.common.base.AjaxResult;


/**
 * 购物车 信息操作处理
 * 
 * @author goose
 * @date 2021-03-16
 */
@RestController
@RequestMapping("/shooting/goodsShoppingCart")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "购物车 ",description="购物车")
public class GoodsShoppingCartRestController extends BaseController
{

	@Autowired
	private IGoodsShoppingCartService goodsShoppingCartService;

	@Autowired
	private IGtService gtService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IGoodsAttributeService goodsAttributeService;

	
	
	/**
	 * 查询购物车列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询购物车列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShoppingCart", value = "购物车", required = false, dataType = "GoodsShoppingCart")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsShoppingCart goodsShoppingCart)
	{
		startPage();
		
        List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartList(goodsShoppingCart);
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
	public GoodsShoppingCart getById( @PathVariable("id") Long id) {
		return goodsShoppingCartService.selectGoodsShoppingCartById(id);
	}
	
	/**
	 * 导出购物车列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出购物车列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShoppingCart", value = "购物车", required = false, dataType = "GoodsShoppingCart")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsShoppingCart goodsShoppingCart)
    {
    	List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartList(goodsShoppingCart);
        ExcelUtil<GoodsShoppingCart> util = new ExcelUtil<GoodsShoppingCart>(GoodsShoppingCart.class);
        return util.exportExcel(list, "goodsShoppingCart");
    } */
    
    /**
	 * 导入购物车列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入购物车列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "购物车文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsShoppingCart> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsShoppingCart.class);
        	for(GoodsShoppingCart obj : list) {
        		goodsShoppingCartService.insertGoodsShoppingCart(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存购物车
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存购物车")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShoppingCart", value = "购物车", required = true, dataType = "GoodsShoppingCart")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsShoppingCart goodsShoppingCart)
	{
		return toAjax(goodsShoppingCartService.insertGoodsShoppingCart(goodsShoppingCart));
	}
	
	/**
	 * 修改保存购物车
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存购物车")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShoppingCart", value = "购物车", required = true, dataType = "GoodsShoppingCart")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsShoppingCart goodsShoppingCart)
	{
		return toAjax(goodsShoppingCartService.updateGoodsShoppingCart(goodsShoppingCart));
	}
	
	/**
	 * 删除购物车
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除购物车")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove(@RequestBody GoodsShoppingCart goodsShoppingCart)
	{
		String ids=goodsShoppingCart.getIds();
		return toAjax(goodsShoppingCartService.deleteGoodsShoppingCartByIds(ids));
	}
	
		/**
	 * 根据id删除购物车
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除购物车")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsShoppingCartService.deleteGoodsShoppingCartById(id));
	}
	
	
		/**
	 * 查询购物车分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询购物车分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShoppingCart", value = "购物车", required = true, dataType = "GoodsShoppingCart")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsShoppingCart goodsShoppingCart)
	{
		startPage(goodsShoppingCart.getPd());	
        List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartList(goodsShoppingCart);
		for(GoodsShoppingCart car : list) {
			car.setGoods(goodsService.selectGoodsById(car.getGoodsId()));
			if(StringUtils.isNotEmpty(car.getAttributes())) {
				String[] attributesArr = car.getAttributes().split("/");
				Gt gt=new Gt();
				gt.setGoodsId(car.getGoodsId());
				String newAttributeArr = null;
				String newAttributeArrIds = null;
				for(int i=0; i<attributesArr.length; i++) {
					switch (i){
						case 0:
							gt.setAttributeId1(Long.parseLong(attributesArr[0]));
							break;
						case 1:
							gt.setAttributeId2(Long.parseLong(attributesArr[1]));
							break;
						case 2:
							gt.setAttributeId3(Long.parseLong(attributesArr[2]));
							break;
						case 3:
							gt.setAttributeId4(Long.parseLong(attributesArr[3]));
							break;
						case 4:
							gt.setAttributeId5(Long.parseLong(attributesArr[4]));
							break;
						case 5:
							gt.setAttributeId6(Long.parseLong(attributesArr[5]));
							break;
						case 6:
							gt.setAttributeId7(Long.parseLong(attributesArr[6]));
							break;
						case 7:
							gt.setAttributeId8(Long.parseLong(attributesArr[7]));
							break;
						case 8:
							gt.setAttributeId9(Long.parseLong(attributesArr[8]));
							break;
					}
					GoodsAttribute ga = goodsAttributeService.selectGoodsAttributeById(Long.valueOf(attributesArr[i]));
					if(i == 0) {
						newAttributeArr = ga.getName();
						newAttributeArrIds = ga.getId().toString();
					}else {
						newAttributeArr = newAttributeArr + "/" + ga.getName();
						newAttributeArrIds = newAttributeArrIds + "/" + ga.getId().toString();
					}
				}
				List<Gt> gtList=gtService.selectGtByGoodsIdAndAttribute(gt);
				if(!gtList.isEmpty()){
					car.setInventory(gtList.get(0).getNum());
				}
				car.setAttributes(newAttributeArr);
				car.setAttributesIds(newAttributeArrIds);
			}
		}
        return getDataTable(list);
	}
	



	
}
