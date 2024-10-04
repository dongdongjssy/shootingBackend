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
import io.goose.shooting.domain.ContestStats;
import io.goose.shooting.service.IContestStatsService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.ITypeService;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IContestGroupService;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 选手排名 信息操作处理
 * 
 * @author goose
 * @date 2020-05-28
 */
@Controller
@RequestMapping("/shooting/contestStats")
public class ContestStatsController extends BaseController
{

    private String prefix = "shooting/contestStats";
	
	@Autowired
	private IContestStatsService contestStatsService;

	@Autowired
	private ITypeService typeService;

	@Autowired
	private IContestGroupService contestGroupService;

	@Autowired
	private IClientUserService clientUserService;

	
	
	@RequiresPermissions("shooting:contestStats:view")
	@GetMapping()
	public String contestStats(ModelMap mmap)
	{
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("contestGroupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/contestStats";
	}
	
	/**
	 * 查询选手排名列表
	 */
	@RequiresPermissions("shooting:contestStats:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestStats contestStats)
	{
		startPage();
        List<ContestStats> list = contestStatsService.selectContestStatsListAssoc(contestStats);
		return getDataTable(list);
	}
	
	
//	/**
//	 * 导出选手排名列表
//	 */
//	@RequiresPermissions("shooting:contestStats:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(ContestStats contestStats)
//    {
//    	List<ContestStats> list = contestStatsService.selectContestStatsList(contestStats);
//        ExcelUtil<ContestStats> util = new ExcelUtil<ContestStats>(ContestStats.class);
//        return util.exportExcel(list, "contestStats");
//    }
//    
//    /**
//	 * 导入选手排名列表
//	 */
//	@RequiresPermissions("shooting:contestStats:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {        
//    	try {
//        	List<ContestStats> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestStats.class);
//        	for(ContestStats obj : list) {
//        		contestStatsService.insertContestStats(obj);
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
	 * 新增选手排名
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("contestGroupIdList", contestGroupService.selectContestGroupAll());
	    mmap.put("clientUserList", clientUserService.selectClientUserAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存选手排名
	 */
	@RequiresPermissions("shooting:contestStats:add")
	@Log(title = "选手排名", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestStats contestStats 
	)
	{		

		contestStats.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestStatsService.insertContestStats(contestStats));
	}

	/**
	 * 修改选手排名
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestStats contestStats = contestStatsService.selectContestStatsByIdAssoc(id);
		mmap.put("contestStats", contestStats);
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("contestGroupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存选手排名
	 */
	@RequiresPermissions("shooting:contestStats:edit")
	@Log(title = "选手排名", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestStats contestStats 
	)
	{		
		contestStats.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestStatsService.updateContestStats(contestStats));
	}
	
	/**
	 * 删除选手排名
	 */
	@RequiresPermissions("shooting:contestStats:remove")
	@Log(title = "选手排名", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestStatsService.deleteContestStatsByIds(ids));
	}
	

	/**
	 * 查询选手排名分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestStats contestStats)
	{
		startPage(contestStats.getPd());
		
        List<ContestStats> list = contestStatsService.selectContestStatsListAssoc(contestStats);
		return getDataTable(list);
	}




	
}
