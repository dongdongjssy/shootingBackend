package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
//import org.springframewor.security.access.prepost.PreAuthorize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.TrainingIntention;
import io.goose.shooting.service.ITrainingIntentionService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 培训意向管理 信息操作处理
 * 
 * @author goose
 * @date 2021-02-02
 */
@RestController
@RequestMapping("/shooting/trainingIntention")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "培训意向管理 ",description="培训意向管理")
public class TrainingIntentionRestController extends BaseController
{

	@Autowired
	private ITrainingIntentionService trainingIntentionService;



	
	
	/**
	 * 查询培训意向管理列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询培训意向管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "trainingIntention", value = "培训意向管理", required = false, dataType = "TrainingIntention")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  TrainingIntention trainingIntention)
	{
		startPage();
		
        List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionList(trainingIntention);
		return getDataTable(list);
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")		
	public TrainingIntention getById( @PathVariable("id") Long id) {
		return trainingIntentionService.selectTrainingIntentionById(id);
	}
	
	/**
	 * 导出培训意向管理列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出培训意向管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "trainingIntention", value = "培训意向管理", required = false, dataType = "TrainingIntention")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(TrainingIntention trainingIntention)
    {
    	List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionList(trainingIntention);
        ExcelUtil<TrainingIntention> util = new ExcelUtil<TrainingIntention>(TrainingIntention.class);
        return util.exportExcel(list, "trainingIntention");
    } */
    
    /**
	 * 导入培训意向管理列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入培训意向管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "培训意向管理文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
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
	        
    }*/    

	
	/**
	 * 新增保存培训意向管理
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存培训意向管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "trainingIntention", value = "培训意向管理", required = true, dataType = "TrainingIntention")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody TrainingIntention trainingIntention)
	{
		return toAjax(trainingIntentionService.insertTrainingIntention(trainingIntention));
	}
	
	/**
	 * 修改保存培训意向管理
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存培训意向管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "trainingIntention", value = "培训意向管理", required = true, dataType = "TrainingIntention")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody TrainingIntention trainingIntention)
	{
		return toAjax(trainingIntentionService.updateTrainingIntention(trainingIntention));
	}
	
	/**
	 * 删除培训意向管理
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除培训意向管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(trainingIntentionService.deleteTrainingIntentionByIds(ids));
	}
	
		/**
	 * 根据id删除培训意向管理
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除培训意向管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(trainingIntentionService.deleteTrainingIntentionById(id));
	}
	
	
		/**
	 * 查询培训意向管理分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询培训意向管理分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "trainingIntention", value = "培训意向管理", required = true, dataType = "TrainingIntention")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  TrainingIntention trainingIntention)
	{
		startPage(trainingIntention.getPd());	
        List<TrainingIntention> list = trainingIntentionService.selectTrainingIntentionList(trainingIntention);
		return getDataTable(list);
	}
	



	
}
