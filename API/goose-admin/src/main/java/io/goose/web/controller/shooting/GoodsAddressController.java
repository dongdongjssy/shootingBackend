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
import io.goose.shooting.domain.GoodsAddress;
import io.goose.shooting.service.IGoodsAddressService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 收货地址 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/shooting/goodsAddress")
public class GoodsAddressController extends BaseController
{

    private String prefix = "shooting/goodsAddress";
	
	@Autowired
	private IGoodsAddressService goodsAddressService;


	
	
	@RequiresPermissions("shooting:goodsAddress:view")
	@GetMapping()
	public String goodsAddress(ModelMap mmap)
	{
	    return prefix + "/goodsAddress";
	}
	
	/**
	 * 查询收货地址列表
	 */
	@RequiresPermissions("shooting:goodsAddress:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsAddress goodsAddress)
	{
		startPage();
        List<GoodsAddress> list = goodsAddressService.selectGoodsAddressListAssoc(goodsAddress);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出收货地址列表
	 */
	@RequiresPermissions("shooting:goodsAddress:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsAddress goodsAddress)
    {
    	List<GoodsAddress> list = goodsAddressService.selectGoodsAddressList(goodsAddress);
        ExcelUtil<GoodsAddress> util = new ExcelUtil<GoodsAddress>(GoodsAddress.class);
        return util.exportExcel(list, "goodsAddress");
    }
    
    /**
	 * 导入收货地址列表
	 */
	@RequiresPermissions("shooting:goodsAddress:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsAddress> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsAddress.class);
        	for(GoodsAddress obj : list) {
        		goodsAddressService.insertGoodsAddress(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增收货地址
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存收货地址
	 */
	@RequiresPermissions("shooting:goodsAddress:add")
	@Log(title = "收货地址", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsAddress goodsAddress 
	)
	{		

		goodsAddress.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsAddressService.insertGoodsAddress(goodsAddress));
	}

	/**
	 * 修改收货地址
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsAddress goodsAddress = goodsAddressService.selectGoodsAddressByIdAssoc(id);
		mmap.put("goodsAddress", goodsAddress);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存收货地址
	 */
	@RequiresPermissions("shooting:goodsAddress:edit")
	@Log(title = "收货地址", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsAddress goodsAddress 
	)
	{		
		goodsAddress.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsAddressService.updateGoodsAddress(goodsAddress));
	}
	
	/**
	 * 删除收货地址
	 */
	@RequiresPermissions("shooting:goodsAddress:remove")
	@Log(title = "收货地址", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsAddressService.deleteGoodsAddressByIds(ids));
	}
	

	/**
	 * 查询收货地址分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsAddress goodsAddress)
	{
		startPage(goodsAddress.getPd());
		
        List<GoodsAddress> list = goodsAddressService.selectGoodsAddressListAssoc(goodsAddress);
		return getDataTable(list);
	}




	
}
