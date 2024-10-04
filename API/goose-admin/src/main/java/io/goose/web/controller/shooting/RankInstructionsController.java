package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.domain.RankInstructions;
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
import io.goose.shooting.service.IRankInstructionsService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 成绩说明 信息操作处理
 * 
 * @author goose
 * @date 2021-01-06
 */
@Controller
@RequestMapping("/shooting/rankInstructions")
public class RankInstructionsController extends BaseController
{

    private String prefix = "shooting/rankInstructions";
	
	@Autowired
	private IRankInstructionsService rankInstructionsService;


	
	
	@RequiresPermissions("shooting:rankInstructions:view")
	@GetMapping()
	public String rankInstructions(ModelMap mmap)
	{
	    return prefix + "/rankInstructions";
	}
	
	/**
	 * 查询成绩说明列表
	 */
	@RequiresPermissions("shooting:rankInstructions:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(RankInstructions rankInstructions)
	{
		startPage();
        List<RankInstructions> list = rankInstructionsService.selectRankInstructionsListAssoc(rankInstructions);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出成绩说明列表
	 */
	@RequiresPermissions("shooting:rankInstructions:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RankInstructions rankInstructions)
    {
    	List<RankInstructions> list = rankInstructionsService.selectRankInstructionsList(rankInstructions);
        ExcelUtil<RankInstructions> util = new ExcelUtil<RankInstructions>(RankInstructions.class);
        return util.exportExcel(list, "rankInstructions");
    }
    
    /**
	 * 导入成绩说明列表
	 */
	@RequiresPermissions("shooting:rankInstructions:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<RankInstructions> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), RankInstructions.class);
        	for(RankInstructions obj : list) {
        		rankInstructionsService.insertRankInstructions(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增成绩说明
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存成绩说明
	 */
	@RequiresPermissions("shooting:rankInstructions:add")
	@Log(title = "成绩说明", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(RankInstructions rankInstructions 
	)
	{		

		rankInstructions.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(rankInstructionsService.insertRankInstructions(rankInstructions));
	}

	/**
	 * 修改成绩说明
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		RankInstructions rankInstructions = rankInstructionsService.selectRankInstructionsByIdAssoc(id);
		mmap.put("rankInstructions", rankInstructions);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存成绩说明
	 */
	@RequiresPermissions("shooting:rankInstructions:edit")
	@Log(title = "成绩说明", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(RankInstructions rankInstructions 
	)
	{		
		rankInstructions.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(rankInstructionsService.updateRankInstructions(rankInstructions));
	}
	
	/**
	 * 删除成绩说明
	 */
	@RequiresPermissions("shooting:rankInstructions:remove")
	@Log(title = "成绩说明", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(rankInstructionsService.deleteRankInstructionsByIds(ids));
	}
	

	/**
	 * 查询成绩说明分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody RankInstructions rankInstructions)
	{
		startPage(rankInstructions.getPd());
		
        List<RankInstructions> list = rankInstructionsService.selectRankInstructionsListAssoc(rankInstructions);
		return getDataTable(list);
	}




	
}
