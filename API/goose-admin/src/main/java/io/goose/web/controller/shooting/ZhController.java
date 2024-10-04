package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;
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
import io.goose.shooting.domain.Zh;
import io.goose.shooting.service.IZhService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 总会内容 信息操作处理
 * 
 * @author goose
 * @date 2020-12-09
 */
@Controller
@RequestMapping("/shooting/zh")
public class ZhController extends BaseController
{

    private String prefix = "shooting/zh";
	
	@Autowired
	private IZhService zhService;


	
	
	@RequiresPermissions("shooting:zh:view")
	@GetMapping()
	public String zh(ModelMap mmap)
	{
	    return prefix + "/zh";
	}
	
	/**
	 * 查询总会内容列表
	 */
	@RequiresPermissions("shooting:zh:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Zh zh)
	{
		startPage();
        List<Zh> list = zhService.selectZhListAssoc(zh);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出总会内容列表
	 */
	@RequiresPermissions("shooting:zh:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Zh zh)
    {
    	List<Zh> list = zhService.selectZhList(zh);
        ExcelUtil<Zh> util = new ExcelUtil<Zh>(Zh.class);
        return util.exportExcel(list, "zh");
    }
    
    /**
	 * 导入总会内容列表
	 */
	@RequiresPermissions("shooting:zh:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Zh> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Zh.class);
        	for(Zh obj : list) {
        		zhService.insertZh(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增总会内容
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存总会内容
	 */
	@RequiresPermissions("shooting:zh:add")
	@Log(title = "总会内容", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Zh zh 
	)
	{		

		zh.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(zhService.insertZh(zh));
	}

	/**
	 * 修改总会内容
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Zh zh = zhService.selectZhByIdAssoc(id);
		mmap.put("zh", zh);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存总会内容
	 */
	@RequiresPermissions("shooting:zh:edit")
	@Log(title = "总会内容", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Zh zh 
	)
	{		
		zh.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(zhService.updateZh(zh));
	}
	
	/**
	 * 删除总会内容
	 */
	@RequiresPermissions("shooting:zh:remove")
	@Log(title = "总会内容", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(zhService.deleteZhByIds(ids));
	}
	

	/**
	 * 查询总会内容分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Zh zh)
	{
		startPage(zh.getPd());
		
        List<Zh> list = zhService.selectZhListAssoc(zh);
		return getDataTable(list);
	}




	
}
