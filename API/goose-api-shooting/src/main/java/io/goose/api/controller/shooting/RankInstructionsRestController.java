package io.goose.api.controller.shooting;

import java.util.List;

import io.goose.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.CrossOrigin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.RankInstructions;
import io.goose.shooting.service.IRankInstructionsService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 成绩说明 信息操作处理
 * 
 * @author goose
 * @date 2021-01-06
 */
@RestController
@RequestMapping("/shooting/rankInstructions")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "成绩说明 ",description="成绩说明")
public class RankInstructionsRestController extends BaseController
{

	@Autowired
	private IRankInstructionsService rankInstructionsService;



	
	
	/**
	 * 查询成绩说明列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询成绩说明列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "rankInstructions", value = "成绩说明", required = false, dataType = "RankInstructions")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  RankInstructions rankInstructions)
	{
		startPage();
		
        List<RankInstructions> list = rankInstructionsService.selectRankInstructionsList(rankInstructions);
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
	public RankInstructions getById( @PathVariable("id") Long id) {
		return rankInstructionsService.selectRankInstructionsById(id);
	}
	
	/**
	 * 导出成绩说明列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出成绩说明列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "rankInstructions", value = "成绩说明", required = false, dataType = "RankInstructions")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(RankInstructions rankInstructions)
    {
    	List<RankInstructions> list = rankInstructionsService.selectRankInstructionsList(rankInstructions);
        ExcelUtil<RankInstructions> util = new ExcelUtil<RankInstructions>(RankInstructions.class);
        return util.exportExcel(list, "rankInstructions");
    } */
    
    /**
	 * 导入成绩说明列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入成绩说明列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "成绩说明文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
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
	        
    }*/    

	
	/**
	 * 新增保存成绩说明
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存成绩说明")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "rankInstructions", value = "成绩说明", required = true, dataType = "RankInstructions")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody RankInstructions rankInstructions)
	{
		return toAjax(rankInstructionsService.insertRankInstructions(rankInstructions));
	}
	
	/**
	 * 修改保存成绩说明
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存成绩说明")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "rankInstructions", value = "成绩说明", required = true, dataType = "RankInstructions")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody RankInstructions rankInstructions)
	{
		return toAjax(rankInstructionsService.updateRankInstructions(rankInstructions));
	}
	
	/**
	 * 删除成绩说明
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除成绩说明")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(rankInstructionsService.deleteRankInstructionsByIds(ids));
	}
	
		/**
	 * 根据id删除成绩说明
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除成绩说明")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(rankInstructionsService.deleteRankInstructionsById(id));
	}
	
	
		/**
	 * 查询成绩说明分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询成绩说明分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "rankInstructions", value = "成绩说明", required = true, dataType = "RankInstructions")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  RankInstructions rankInstructions)
	{
		startPage(rankInstructions.getPd());	
        List<RankInstructions> list = rankInstructionsService.selectRankInstructionsList(rankInstructions);
		return getDataTable(list);
	}
	



	
}
