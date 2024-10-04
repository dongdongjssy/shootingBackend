package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

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
import io.goose.shooting.domain.ContestContestRankingCoeff;
import io.goose.shooting.service.IContestContestRankingCoeffService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 赛事具体成绩 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@Controller
@RequestMapping("/shooting/contestContestRankingCoeff")
public class ContestContestRankingCoeffController extends BaseController
{

    private String prefix = "shooting/contestContestRankingCoeff";
	
	@Autowired
	private IContestContestRankingCoeffService contestContestRankingCoeffService;


	
	
	@RequiresPermissions("shooting:contestContestRankingCoeff:view")
	@GetMapping()
	public String contestContestRankingCoeff(ModelMap mmap)
	{
	    return prefix + "/contestContestRankingCoeff";
	}
	
	/**
	 * 查询赛事具体成绩列表
	 */
	@RequiresPermissions("shooting:contestContestRankingCoeff:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ContestContestRankingCoeff contestContestRankingCoeff)
	{
		startPage();
        List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffListAssoc(contestContestRankingCoeff);
		return getDataTable(list);
	}
	
	
/*	*//**
	 * 导出赛事具体成绩列表
	 *//*
	@RequiresPermissions("shooting:contestContestRankingCoeff:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ContestContestRankingCoeff  )
    {
    	List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffList(contestContestRankingCoeff);
        ExcelUtil<ContestContestRankingCoeff> util = new ExcelUtil<ContestContestRankingCoeff>(ContestContestRankingCoeff.class);
        return util.exportExcel(list, "contestContestRankingCoeff");
    }*/

//    /**
//	 * 导入赛事具体成绩列表
//	 */
//	@RequiresPermissions("shooting:contestContestRankingCoeff:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {        
//    	try {
//        	List<ContestContestRankingCoeff> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestContestRankingCoeff.class);
//        	for(ContestContestRankingCoeff obj : list) {
//        		contestContestRankingCoeffService.insertContestContestRankingCoeff(obj);
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
	 * 新增赛事具体成绩
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存赛事具体成绩
	 */
	@RequiresPermissions("shooting:contestContestRankingCoeff:add")
	@Log(title = "赛事具体成绩", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ContestContestRankingCoeff contestContestRankingCoeff 
	)
	{		

		contestContestRankingCoeff.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(contestContestRankingCoeffService.insertContestContestRankingCoeff(contestContestRankingCoeff));
	}

	/**
	 * 修改赛事具体成绩
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		ContestContestRankingCoeff contestContestRankingCoeff = contestContestRankingCoeffService.selectContestContestRankingCoeffByIdAssoc(id);
		mmap.put("contestContestRankingCoeff", contestContestRankingCoeff);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存赛事具体成绩
	 */
	@RequiresPermissions("shooting:contestContestRankingCoeff:edit")
	@Log(title = "赛事具体成绩", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ContestContestRankingCoeff contestContestRankingCoeff 
	)
	{		
		contestContestRankingCoeff.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(contestContestRankingCoeffService.updateContestContestRankingCoeff(contestContestRankingCoeff));
	}
	
	/**
	 * 删除赛事具体成绩
	 */
	@RequiresPermissions("shooting:contestContestRankingCoeff:remove")
	@Log(title = "赛事具体成绩", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(contestContestRankingCoeffService.deleteContestContestRankingCoeffByIds(ids));
	}
	

	/**
	 * 查询赛事具体成绩分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody ContestContestRankingCoeff contestContestRankingCoeff)
	{
		startPage(contestContestRankingCoeff.getPd());
		
        List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffListAssoc(contestContestRankingCoeff);
		return getDataTable(list);
	}




	
}
