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
import io.goose.shooting.domain.ContestRankingCoeffDefault;
import io.goose.shooting.service.IContestGroupService;
import io.goose.shooting.service.IContestRankingCoeffDefaultService;
import io.goose.shooting.service.ICourseService;
import io.goose.shooting.service.ITypeService;

/**
 * 默认名次系数 信息操作处理
 * 
 * @author goose
 * @date 2020-07-01
 */
@Controller
@RequestMapping("/shooting/contestRankingCoeffDefault")
public class ContestRankingCoeffDefaultController extends BaseController
{

    private String prefix = "shooting/contestRankingCoeffDefault";
	
	@Autowired
	private IContestRankingCoeffDefaultService contestRankingCoeffDefaultService;

	@Autowired
	private ICourseService courseService;

	@Autowired
	private ITypeService typeService;

	@Autowired
	private IContestGroupService contestGroupService;


	
	
	@RequiresPermissions("shooting:contestRankingCoeffDefault:view")
	@GetMapping()
	public String contestRankingCoeffDefault(ModelMap mmap)
	{
	    mmap.put("courseIdList", courseService.selectCourseAll());
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/contestRankingCoeffDefault";
	}
	
	/**
	 * 查询默认名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		startPage();
        List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultListAssoc(contestRankingCoeffDefault);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出默认名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ContestRankingCoeffDefault contestRankingCoeffDefault)
    {
    	List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
        ExcelUtil<ContestRankingCoeffDefault> util = new ExcelUtil<ContestRankingCoeffDefault>(ContestRankingCoeffDefault.class);
        return util.exportExcel(list, "contestRankingCoeffDefault");
    }
    
    /**
	 * 导入默认名次系数列表
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestRankingCoeffDefault> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestRankingCoeffDefault.class);
        	for(ContestRankingCoeffDefault obj : list) {
        		contestRankingCoeffDefaultService.insertContestRankingCoeffDefault(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增默认名次系数
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
	 * 新增保存默认名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:add")
	@Log(title = "默认名次系数", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestRankingCoeffDefault contestRankingCoeffDefault 
	)
	{		

		contestRankingCoeffDefault.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestRankingCoeffDefaultService.insertContestRankingCoeffDefault(contestRankingCoeffDefault));
	}

	/**
	 * 修改默认名次系数
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestRankingCoeffDefault contestRankingCoeffDefault = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultByIdAssoc(id);
		mmap.put("contestRankingCoeffDefault", contestRankingCoeffDefault);
	    mmap.put("courseIdList", courseService.selectCourseAll());
	    mmap.put("typeIdList", typeService.selectTypeAll());
	    mmap.put("groupIdList", contestGroupService.selectContestGroupAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存默认名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:edit")
	@Log(title = "默认名次系数", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestRankingCoeffDefault contestRankingCoeffDefault 
	)
	{		
		contestRankingCoeffDefault.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestRankingCoeffDefaultService.updateContestRankingCoeffDefault(contestRankingCoeffDefault));
	}
	
	/**
	 * 删除默认名次系数
	 */
	@RequiresPermissions("shooting:contestRankingCoeffDefault:remove")
	@Log(title = "默认名次系数", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestRankingCoeffDefaultService.deleteContestRankingCoeffDefaultByIds(ids));
	}
	

	/**
	 * 查询默认名次系数分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		startPage(contestRankingCoeffDefault.getPd());
		
        List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultListAssoc(contestRankingCoeffDefault);
		return getDataTable(list);
	}




	
}
