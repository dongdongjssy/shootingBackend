package io.goose.web.controller.shooting;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import io.goose.shooting.domain.Visitor;
import io.goose.shooting.service.IVisitorService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 访客 信息操作处理
 * 
 * @author goose
 * @date 2020-12-24
 */
@Controller
@RequestMapping("/shooting/visitor")
public class VisitorController extends BaseController
{

    private String prefix = "shooting/visitor";
	
	@Autowired
	private IVisitorService visitorService;


	
	
	@RequiresPermissions("shooting:visitor:view")
	@GetMapping()
	public String visitor(ModelMap mmap)
	{
	    return prefix + "/visitor";
	}
	
	/**
	 * 查询访客列表
	 */
	@RequiresPermissions("shooting:visitor:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Visitor visitor)
	{
		startPage();
        List<Visitor> list = visitorService.selectVisitorListAssoc(visitor);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出访客列表
	 */
	@RequiresPermissions("shooting:visitor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Visitor visitor)
    {
    	List<Visitor> list = visitorService.selectVisitorList(visitor);
        ExcelUtil<Visitor> util = new ExcelUtil<Visitor>(Visitor.class);
        return util.exportExcel(list, "visitor");
    }
    
    /**
	 * 导入访客列表
	 */
	@RequiresPermissions("shooting:visitor:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Visitor> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Visitor.class);
        	for(Visitor obj : list) {
        		visitorService.insertVisitor(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增访客
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存访客
	 */
	@RequiresPermissions("shooting:visitor:add")
	@Log(title = "访客", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Visitor visitor 
	)
	{		

		visitor.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(visitorService.insertVisitor(visitor));
	}

	/**
	 * 修改访客
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Visitor visitor = visitorService.selectVisitorByIdAssoc(id);
		visitor.setTime(df.format(visitor.getLoginTime()));
		mmap.put("visitor", visitor);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存访客
	 */
	@RequiresPermissions("shooting:visitor:edit")
	@Log(title = "访客", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Visitor visitor 
	)
	{		
		visitor.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(visitorService.updateVisitor(visitor));
	}
	
	/**
	 * 删除访客
	 */
	@RequiresPermissions("shooting:visitor:remove")
	@Log(title = "访客", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(visitorService.deleteVisitorByIds(ids));
	}
	

	/**
	 * 查询访客分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Visitor visitor)
	{
		startPage(visitor.getPd());
		
        List<Visitor> list = visitorService.selectVisitorListAssoc(visitor);
		return getDataTable(list);
	}




	
}
