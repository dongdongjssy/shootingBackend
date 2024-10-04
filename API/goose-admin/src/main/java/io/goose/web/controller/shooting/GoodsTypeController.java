package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
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
import io.goose.shooting.domain.GoodsType;
import io.goose.shooting.service.IGoodsTypeService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 商品类型 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@Controller
@RequestMapping("/shooting/goodsType")
public class GoodsTypeController extends BaseController
{

    private String prefix = "shooting/goodsType";
	
	@Autowired
	private IGoodsTypeService goodsTypeService;
	@Autowired
	private IGoodsService goodsService;

	
	
	@RequiresPermissions("shooting:goodsType:view")
	@GetMapping()
	public String goodsType(ModelMap mmap)
	{
	    return prefix + "/goodsType";
	}
	
	/**
	 * 查询商品类型列表
	 */
	@RequiresPermissions("shooting:goodsType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsType goodsType)
	{
		startPage();
		goodsType.setCreateBy(ShiroUtils.getLoginName());

		List<GoodsType> list = goodsTypeService.selectGoodsTypeListAssoc(goodsType);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品类型列表
	 */
	@RequiresPermissions("shooting:goodsType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsType goodsType)
    {
    	List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        return util.exportExcel(list, "goodsType");
    }
    
    /**
	 * 导入商品类型列表
	 */
	@RequiresPermissions("shooting:goodsType:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsType> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsType.class);
        	for(GoodsType obj : list) {
        		goodsTypeService.insertGoodsType(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增商品类型
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品类型
	 */
	@RequiresPermissions("shooting:goodsType:add")
	@Log(title = "商品类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsType goodsType 
	)
	{		

		goodsType.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsTypeService.insertGoodsType(goodsType));
	}

	/**
	 * 修改商品类型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsType goodsType = goodsTypeService.selectGoodsTypeByIdAssoc(id);
		mmap.put("goodsType", goodsType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品类型
	 */
	@RequiresPermissions("shooting:goodsType:edit")
	@Log(title = "商品类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsType goodsType 
	)
	{		
		goodsType.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsTypeService.updateGoodsType(goodsType));
	}
	
	/**
	 * 删除商品类型
	 */
	@RequiresPermissions("shooting:goodsType:remove")
	@Log(title = "商品类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		int row=goodsTypeService.deleteGoodsTypeByIds(ids);
		if(row>0){
			goodsService.deleteGoodsByGoodsTypeIds(ids);
		}
		return toAjax(row);
	}
	

	/**
	 * 查询商品类型分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsType goodsType)
	{
		startPage(goodsType.getPd());
		
        List<GoodsType> list = goodsTypeService.selectGoodsTypeListAssoc(goodsType);
		return getDataTable(list);
	}




	
}
