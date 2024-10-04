package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.service.IClientUserService;
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
import io.goose.shooting.domain.TrainingIntention;
import io.goose.shooting.service.ITrainingIntentionService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 培训意向管理 信息操作处理
 * 
 * @author goose
 * @date 2021-02-02
 */
@Controller
@RequestMapping("/shooting/trainingIntention")
public class TrainingIntentionController extends BaseController
{

    private String prefix = "shooting/trainingIntention";
	
	@Autowired
	private ITrainingIntentionService trainingIntentionService;

	@Autowired
	private IClientUserService clientUserService;
	
	
	@RequiresPermissions("shooting:trainingIntention:view")
	@GetMapping()
	public String trainingIntention(ModelMap mmap)
	{
	    return prefix + "/trainingIntention";
	}
	
	/**
	 * 查询培训意向管理列表
	 */
	@RequiresPermissions("shooting:trainingIntention:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TrainingIntention trainingIntention)
	{
		startPage();
        List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionListAssoc(trainingIntention);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出培训意向管理列表
	 */
	@RequiresPermissions("shooting:trainingIntention:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TrainingIntention trainingIntention)
    {
    	List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionList(trainingIntention);
        ExcelUtil<TrainingIntention> util = new ExcelUtil<TrainingIntention>(TrainingIntention.class);
        return util.exportExcel(list, "trainingIntention");
    }
    
    /**
	 * 导入培训意向管理列表
	 */
	@RequiresPermissions("shooting:trainingIntention:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<TrainingIntention> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), TrainingIntention.class);
        	for(TrainingIntention obj : list) {
        		trainingIntentionService.insertTrainingIntention(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增培训意向管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存培训意向管理
	 */
	@RequiresPermissions("shooting:trainingIntention:add")
	@Log(title = "培训意向管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TrainingIntention trainingIntention 
	)
	{		

		trainingIntention.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(trainingIntentionService.insertTrainingIntention(trainingIntention));
	}

	/**
	 * 修改培训意向管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		TrainingIntention trainingIntention = trainingIntentionService.selectTrainingIntentionByIdAssoc(id);
		mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());

		mmap.put("trainingIntention", trainingIntention);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存培训意向管理
	 */
	@RequiresPermissions("shooting:trainingIntention:edit")
	@Log(title = "培训意向管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TrainingIntention trainingIntention 
	)
	{		
		trainingIntention.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(trainingIntentionService.updateTrainingIntention(trainingIntention));
	}
	
	/**
	 * 删除培训意向管理
	 */
	@RequiresPermissions("shooting:trainingIntention:remove")
	@Log(title = "培训意向管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(trainingIntentionService.deleteTrainingIntentionByIds(ids));
	}
	

	/**
	 * 查询培训意向管理分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody TrainingIntention trainingIntention)
	{
		startPage(trainingIntention.getPd());
		
        List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionListAssoc(trainingIntention);
		return getDataTable(list);
	}




	
}
