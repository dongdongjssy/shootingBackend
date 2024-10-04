package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.domain.Goods;
import io.goose.shooting.domain.GoodsAttribute;
import io.goose.shooting.service.IGoodsService;
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
import io.goose.shooting.domain.Gt;
import io.goose.shooting.service.IGtService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IGoodsAttributeService;

import io.goose.framework.util.ShiroUtils;

import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 商品属性关联 信息操作处理
 * 
 * @author goose
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/shooting/gt")
public class GtController extends BaseController
{

    private String prefix = "shooting/gt";
	
	@Autowired
	private IGtService gtService;

	@Autowired
	private IGoodsAttributeService goodsAttributeService;

	@Autowired
	private IGoodsService goodsService;


	
	
	@RequiresPermissions("shooting:gt:view")
	@GetMapping()
	public String gt(ModelMap mmap)
	{
		mmap.put("goodsIdList", goodsService.selectGoodsAll());

		mmap.put("attributeId1List", goodsAttributeService.selectGoodsAttributeAll());

	    return prefix + "/gt";
	}
	
	/**
	 * 查询商品属性关联列表
	 */
	@RequiresPermissions("shooting:gt:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Gt gt)
	{
		startPage();
		gt.setCreateBy(ShiroUtils.getLoginName());
        List<Gt> list = gtService.selectGtListAssoc(gt);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品属性关联列表
	 */
	@RequiresPermissions("shooting:gt:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gt gt)
    {
    	List<Gt> list = gtService.selectGtList(gt);
        ExcelUtil<Gt> util = new ExcelUtil<Gt>(Gt.class);
        return util.exportExcel(list, "gt");
    }
    
    /**
	 * 导入商品属性关联列表
	 */
	@RequiresPermissions("shooting:gt:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Gt> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Gt.class);
        	for(Gt obj : list) {
        		gtService.insertGt(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增商品属性关联
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		Goods goods=new Goods();
		goods.setCreateBy(ShiroUtils.getLoginName());
		mmap.put("goodsIdList", goodsService.selectGoodsList(goods));
		GoodsAttribute attribute=new GoodsAttribute();
		attribute.setCreateBy(ShiroUtils.getLoginName());
		mmap.put("attributeId1List", goodsAttributeService.selectGoodsAttributeList(attribute));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品属性关联
	 */
	@RequiresPermissions("shooting:gt:add")
	@Log(title = "商品属性关联", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Gt gt 
	)
	{		

		gt.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(gtService.insertGt(gt));
	}

	/**
	 * 修改商品属性关联
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		mmap.put("goodsIdList", goodsService.selectGoodsAll());

		Gt gt = gtService.selectGtByIdAssoc(id);
		mmap.put("gt", gt);
	    mmap.put("attributeId1List", goodsAttributeService.selectGoodsAttributeAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品属性关联
	 */
	@RequiresPermissions("shooting:gt:edit")
	@Log(title = "商品属性关联", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Gt gt 
	)
	{		
		gt.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(gtService.updateGt(gt));
	}
	
	/**
	 * 删除商品属性关联
	 */
	@RequiresPermissions("shooting:gt:remove")
	@Log(title = "商品属性关联", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(gtService.deleteGtByIds(ids));
	}
	

	/**
	 * 查询商品属性关联分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Gt gt)
	{
		startPage(gt.getPd());
		
        List<Gt> list = gtService.selectGtListAssoc(gt);
		return getDataTable(list);
	}




	
}
