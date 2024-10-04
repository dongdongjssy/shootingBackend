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
import io.goose.shooting.domain.GoodsShops;
import io.goose.shooting.service.IGoodsShopsService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 商铺 信息操作处理
 * 
 * @author goose
 * @date 2021-03-19
 */
@Controller
@RequestMapping("/shooting/goodsShops")
public class GoodsShopsController extends BaseController
{

    private String prefix = "shooting/goodsShops";
	
	@Autowired
	private IGoodsShopsService goodsShopsService;


	
	
	@RequiresPermissions("shooting:goodsShops:view")
	@GetMapping()
	public String goodsShops(ModelMap mmap)
	{
	    return prefix + "/goodsShops";
	}
	
	/**
	 * 查询商铺列表
	 */
	@RequiresPermissions("shooting:goodsShops:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsShops goodsShops)
	{
		startPage();
        List<GoodsShops> list = goodsShopsService.selectGoodsShopsListAssoc(goodsShops);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商铺列表
	 */
	@RequiresPermissions("shooting:goodsShops:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsShops goodsShops)
    {
    	List<GoodsShops> list = goodsShopsService.selectGoodsShopsList(goodsShops);
        ExcelUtil<GoodsShops> util = new ExcelUtil<GoodsShops>(GoodsShops.class);
        return util.exportExcel(list, "goodsShops");
    }
    
    /**
	 * 导入商铺列表
	 */
	@RequiresPermissions("shooting:goodsShops:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsShops> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsShops.class);
        	for(GoodsShops obj : list) {
        		goodsShopsService.insertGoodsShops(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增商铺
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商铺
	 */
	@RequiresPermissions("shooting:goodsShops:add")
	@Log(title = "商铺", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsShops goodsShops 
	)
	{		

		goodsShops.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsShopsService.insertGoodsShops(goodsShops));
	}

	/**
	 * 修改商铺
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsShops goodsShops = goodsShopsService.selectGoodsShopsByIdAssoc(id);
		mmap.put("goodsShops", goodsShops);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商铺
	 */
	@RequiresPermissions("shooting:goodsShops:edit")
	@Log(title = "商铺", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsShops goodsShops 
	)
	{		
		goodsShops.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsShopsService.updateGoodsShops(goodsShops));
	}
	
	/**
	 * 删除商铺
	 */
	@RequiresPermissions("shooting:goodsShops:remove")
	@Log(title = "商铺", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsShopsService.deleteGoodsShopsByIds(ids));
	}
	

	/**
	 * 查询商铺分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsShops goodsShops)
	{
		startPage(goodsShops.getPd());
		
        List<GoodsShops> list = goodsShopsService.selectGoodsShopsListAssoc(goodsShops);
		return getDataTable(list);
	}




	
}
