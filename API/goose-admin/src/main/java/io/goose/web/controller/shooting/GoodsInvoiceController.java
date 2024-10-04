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
import io.goose.shooting.domain.GoodsInvoice;
import io.goose.shooting.service.IGoodsInvoiceService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 发票 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/shooting/goodsInvoice")
public class GoodsInvoiceController extends BaseController
{

    private String prefix = "shooting/goodsInvoice";
	
	@Autowired
	private IGoodsInvoiceService goodsInvoiceService;


	
	
	@RequiresPermissions("shooting:goodsInvoice:view")
	@GetMapping()
	public String goodsInvoice(ModelMap mmap)
	{
	    return prefix + "/goodsInvoice";
	}
	
	/**
	 * 查询发票列表
	 */
	@RequiresPermissions("shooting:goodsInvoice:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsInvoice goodsInvoice)
	{
		startPage();
        List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceListAssoc(goodsInvoice);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出发票列表
	 */
	@RequiresPermissions("shooting:goodsInvoice:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsInvoice goodsInvoice)
    {
    	List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceList(goodsInvoice);
        ExcelUtil<GoodsInvoice> util = new ExcelUtil<GoodsInvoice>(GoodsInvoice.class);
        return util.exportExcel(list, "goodsInvoice");
    }
    
    /**
	 * 导入发票列表
	 */
	@RequiresPermissions("shooting:goodsInvoice:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsInvoice> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsInvoice.class);
        	for(GoodsInvoice obj : list) {
        		goodsInvoiceService.insertGoodsInvoice(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增发票
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存发票
	 */
	@RequiresPermissions("shooting:goodsInvoice:add")
	@Log(title = "发票", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsInvoice goodsInvoice 
	)
	{		

		goodsInvoice.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsInvoiceService.insertGoodsInvoice(goodsInvoice));
	}

	/**
	 * 修改发票
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsInvoice goodsInvoice = goodsInvoiceService.selectGoodsInvoiceByIdAssoc(id);
		mmap.put("goodsInvoice", goodsInvoice);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存发票
	 */
	@RequiresPermissions("shooting:goodsInvoice:edit")
	@Log(title = "发票", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsInvoice goodsInvoice 
	)
	{		
		goodsInvoice.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsInvoiceService.updateGoodsInvoice(goodsInvoice));
	}
	
	/**
	 * 删除发票
	 */
	@RequiresPermissions("shooting:goodsInvoice:remove")
	@Log(title = "发票", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsInvoiceService.deleteGoodsInvoiceByIds(ids));
	}
	

	/**
	 * 查询发票分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsInvoice goodsInvoice)
	{
		startPage(goodsInvoice.getPd());
		
        List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceListAssoc(goodsInvoice);
		return getDataTable(list);
	}




	
}
