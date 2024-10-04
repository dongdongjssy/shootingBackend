package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.shooting.domain.GoodsShoppingCart;
import io.goose.shooting.service.IGoodsShoppingCartService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 购物车 信息操作处理
 * 
 * @author goose
 * @date 2021-03-16
 */
@Controller
@RequestMapping("/shooting/goodsShoppingCart")
public class GoodsShoppingCartController extends BaseController
{

    private String prefix = "shooting/goodsShoppingCart";
	
	@Autowired
	private IGoodsShoppingCartService goodsShoppingCartService;


	
	
	@RequiresPermissions("shooting:goodsShoppingCart:view")
	@GetMapping()
	public String goodsShoppingCart(ModelMap mmap)
	{
	    return prefix + "/goodsShoppingCart";
	}
	
	/**
	 * 查询购物车列表
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsShoppingCart goodsShoppingCart)
	{
		startPage();
        List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartListAssoc(goodsShoppingCart);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出购物车列表
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsShoppingCart goodsShoppingCart)
    {
    	List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartList(goodsShoppingCart);
        ExcelUtil<GoodsShoppingCart> util = new ExcelUtil<GoodsShoppingCart>(GoodsShoppingCart.class);
        return util.exportExcel(list, "goodsShoppingCart");
    }
    
    /**
	 * 导入购物车列表
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:import")
    @PostMapping("/import")
    @ResponseBody
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
	        
    }
	
	/**
	 * 新增购物车
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存购物车
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:add")
	@Log(title = "购物车", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsShoppingCart goodsShoppingCart 
	)
	{		

		goodsShoppingCart.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsShoppingCartService.insertGoodsShoppingCart(goodsShoppingCart));
	}

	/**
	 * 修改购物车
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsShoppingCart goodsShoppingCart = goodsShoppingCartService.selectGoodsShoppingCartByIdAssoc(id);
		mmap.put("goodsShoppingCart", goodsShoppingCart);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存购物车
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:edit")
	@Log(title = "购物车", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsShoppingCart goodsShoppingCart 
	)
	{		
		goodsShoppingCart.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsShoppingCartService.updateGoodsShoppingCart(goodsShoppingCart));
	}
	
	/**
	 * 删除购物车
	 */
	@RequiresPermissions("shooting:goodsShoppingCart:remove")
	@Log(title = "购物车", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsShoppingCartService.deleteGoodsShoppingCartByIds(ids));
	}
	

	/**
	 * 查询购物车分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsShoppingCart goodsShoppingCart)
	{
		startPage(goodsShoppingCart.getPd());
		
        List<GoodsShoppingCart> list = goodsShoppingCartService.selectGoodsShoppingCartListAssoc(goodsShoppingCart);
		return getDataTable(list);
	}




	
}
