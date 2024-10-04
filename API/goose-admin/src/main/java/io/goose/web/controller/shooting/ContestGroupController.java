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
import io.goose.shooting.domain.ContestGroup;
import io.goose.shooting.service.IContestGroupService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 组别 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@Controller
@RequestMapping("/shooting/contestGroup")
public class ContestGroupController extends BaseController
{

    private String prefix = "shooting/contestGroup";
	
	@Autowired
	private IContestGroupService contestGroupService;


	
	
	@RequiresPermissions("shooting:contestGroup:view")
	@GetMapping()
	public String contestGroup(ModelMap mmap)
	{
	    return prefix + "/contestGroup";
	}
	
	/**
	 * 查询组别列表
	 */
	@RequiresPermissions("shooting:contestGroup:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestGroup contestGroup)
	{
		startPage();
        List<ContestGroup> list = contestGroupService.selectContestGroupListAssoc(contestGroup);
		return getDataTable(list);
	}
	
	
//	/**
//	 * 导出组别列表
//	 */
//	@RequiresPermissions("shooting:contestGroup:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(ContestGroup contestGroup)
//    {
//    	List<ContestGroup> list = contestGroupService.selectContestGroupList(contestGroup);
//        ExcelUtil<ContestGroup> util = new ExcelUtil<ContestGroup>(ContestGroup.class);
//        return util.exportExcel(list, "contestGroup");
//    }
//    
//    /**
//	 * 导入组别列表
//	 */
//	@RequiresPermissions("shooting:contestGroup:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {        
//    	try {
//        	List<ContestGroup> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestGroup.class);
//        	for(ContestGroup obj : list) {
//        		contestGroupService.insertContestGroup(obj);
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
	 * 新增组别
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存组别
	 */
	@RequiresPermissions("shooting:contestGroup:add")
	@Log(title = "组别", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestGroup contestGroup 
	)
	{		

		contestGroup.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestGroupService.insertContestGroup(contestGroup));
	}

	/**
	 * 修改组别
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestGroup contestGroup = contestGroupService.selectContestGroupByIdAssoc(id);
		mmap.put("contestGroup", contestGroup);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存组别
	 */
	@RequiresPermissions("shooting:contestGroup:edit")
	@Log(title = "组别", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestGroup contestGroup 
	)
	{		
		contestGroup.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestGroupService.updateContestGroup(contestGroup));
	}
	
	/**
	 * 删除组别
	 */
	@RequiresPermissions("shooting:contestGroup:remove")
	@Log(title = "组别", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestGroupService.deleteContestGroupByIds(ids));
	}
	

	/**
	 * 查询组别分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestGroup contestGroup)
	{
		startPage(contestGroup.getPd());
		
        List<ContestGroup> list = contestGroupService.selectContestGroupListAssoc(contestGroup);
		return getDataTable(list);
	}




	
}
