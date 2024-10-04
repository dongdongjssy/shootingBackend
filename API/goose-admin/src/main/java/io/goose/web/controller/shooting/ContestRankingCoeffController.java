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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ContestRankingCoeff;
import io.goose.shooting.service.IContestGroupService;
import io.goose.shooting.service.IContestRankingCoeffService;
import io.goose.shooting.service.ICourseService;
import io.goose.shooting.service.ITypeService;

/**
 * 名次系数 信息操作处理
 * 
 * @author goose
 * @date 2020-07-01
 */
@Controller
@RequestMapping("/shooting/contestRankingCoeff")
public class ContestRankingCoeffController extends BaseController
{

    private String prefix = "shooting/contestRankingCoeff";
	
	@Autowired
	private IContestRankingCoeffService contestRankingCoeffService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ITypeService typeService;

	@Autowired
	private IContestGroupService contestGroupService;


	
	
	@RequiresPermissions("shooting:contestRankingCoeff:view")
	@GetMapping()
	public String contestRankingCoeff(ModelMap mmap)
	{
	    mmap.put("courseIdList", courseService.selectCourseAll());
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/contestRankingCoeff";
	}
	
	/**
	 * 查询名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestRankingCoeff contestRankingCoeff)
	{
		startPage();
        List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffListAssoc(contestRankingCoeff);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ContestRankingCoeff contestRankingCoeff)
    {
    	List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffList(contestRankingCoeff);
        ExcelUtil<ContestRankingCoeff> util = new ExcelUtil<ContestRankingCoeff>(ContestRankingCoeff.class);
        return util.exportExcel(list, "contestRankingCoeff");
    }
    
    /**
	 * 导入名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestRankingCoeff> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestRankingCoeff.class);
        	for(ContestRankingCoeff obj : list) {
        		contestRankingCoeffService.insertContestRankingCoeff(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增名次系数
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("courseIdList", courseService.selectCourseAll());
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:add")
	@Log(title = "名次系数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestRankingCoeff contestRankingCoeff 
	)
	{		

		contestRankingCoeff.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestRankingCoeffService.insertContestRankingCoeff(contestRankingCoeff));
	}

	/**
	 * 修改名次系数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestRankingCoeff contestRankingCoeff = contestRankingCoeffService.selectContestRankingCoeffByIdAssoc(id);
		mmap.put("contestRankingCoeff", contestRankingCoeff);
	    mmap.put("courseIdList", courseService.selectCourseAll());
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:edit")
	@Log(title = "名次系数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestRankingCoeff contestRankingCoeff 
	)
	{		
		contestRankingCoeff.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestRankingCoeffService.updateContestRankingCoeff(contestRankingCoeff));
	}
	
	/**
	 * 删除名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeff:remove")
	@Log(title = "名次系数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestRankingCoeffService.deleteContestRankingCoeffByIds(ids));
	}
	

	/**
	 * 查询名次系数分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestRankingCoeff contestRankingCoeff)
	{
		startPage(contestRankingCoeff.getPd());
		
        List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffListAssoc(contestRankingCoeff);
		return getDataTable(list);
	}




	
}
