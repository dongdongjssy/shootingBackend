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
import io.goose.shooting.domain.ContestLevelCoeff;
import io.goose.shooting.service.IContestLevelCoeffService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 级别系数 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@Controller
@RequestMapping("/shooting/contestLevelCoeff")
public class ContestLevelCoeffController extends BaseController
{

    private String prefix = "shooting/contestLevelCoeff";
	
	@Autowired
	private IContestLevelCoeffService contestLevelCoeffService;


	
	
	@RequiresPermissions("shooting:contestLevelCoeff:view")
	@GetMapping()
	public String contestLevelCoeff(ModelMap mmap)
	{
	    return prefix + "/contestLevelCoeff";
	}
	
	/**
	 * 查询级别系数列表
	 */
	@RequiresPermissions("shooting:contestLevelCoeff:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestLevelCoeff contestLevelCoeff)
	{
		startPage();
        List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffListAssoc(contestLevelCoeff);
		return getDataTable(list);
	}
	
	
//	/**
//	 * 导出级别系数列表
//	 */
//	@RequiresPermissions("shooting:contestLevelCoeff:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(ContestLevelCoeff contestLevelCoeff)
//    {
//    	List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffList(contestLevelCoeff);
//        ExcelUtil<ContestLevelCoeff> util = new ExcelUtil<ContestLevelCoeff>(ContestLevelCoeff.class);
//        return util.exportExcel(list, "contestLevelCoeff");
//    }
//    
//    /**
//	 * 导入级别系数列表
//	 */
//	@RequiresPermissions("shooting:contestLevelCoeff:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {        
//    	try {
//        	List<ContestLevelCoeff> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestLevelCoeff.class);
//        	for(ContestLevelCoeff obj : list) {
//        		contestLevelCoeffService.insertContestLevelCoeff(obj);
//        	} 
//        }
//        catch(ExcelUtilException | IOException e) {
//        	return AjaxResult.error(e.getMessage());
//        }
//        
//        return AjaxResult.success("成功导入全部数据");      
//	        
//    }
	
	/**
	 * 新增级别系数
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存级别系数
	 */
	@RequiresPermissions("shooting:contestLevelCoeff:add")
	@Log(title = "级别系数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestLevelCoeff contestLevelCoeff 
	)
	{		

		contestLevelCoeff.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestLevelCoeffService.insertContestLevelCoeff(contestLevelCoeff));
	}

	/**
	 * 修改级别系数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestLevelCoeff contestLevelCoeff = contestLevelCoeffService.selectContestLevelCoeffByIdAssoc(id);
		mmap.put("contestLevelCoeff", contestLevelCoeff);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存级别系数
	 */
	@RequiresPermissions("shooting:contestLevelCoeff:edit")
	@Log(title = "级别系数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestLevelCoeff contestLevelCoeff 
	)
	{		
		contestLevelCoeff.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestLevelCoeffService.updateContestLevelCoeff(contestLevelCoeff));
	}
	
	/**
	 * 删除级别系数
	 */
	@RequiresPermissions("shooting:contestLevelCoeff:remove")
	@Log(title = "级别系数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestLevelCoeffService.deleteContestLevelCoeffByIds(ids));
	}
	

	/**
	 * 查询级别系数分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestLevelCoeff contestLevelCoeff)
	{
		startPage(contestLevelCoeff.getPd());
		
        List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffListAssoc(contestLevelCoeff);
		return getDataTable(list);
	}




	
}
