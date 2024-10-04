package io.goose.api.controller.shooting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ContestContestRankingCoeff;
import io.goose.shooting.service.IContestContestRankingCoeffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 赛事具体成绩 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/shooting/contestContestRankingCoeff")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "赛事具体成绩 ",description="赛事具体成绩")
public class ContestContestRankingCoeffRestController extends BaseController
{

	@Autowired
	private IContestContestRankingCoeffService contestContestRankingCoeffService;



	
	
	/**
	 * 查询赛事具体成绩列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询赛事具体成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRankingCoeff", value = "赛事具体成绩", required = false, dataType = "ContestContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestContestRankingCoeff contestContestRankingCoeff)
	{
		startPage();
		
        List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffList(contestContestRankingCoeff);
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
	public ContestContestRankingCoeff getById( @PathVariable("id") Long id) {
		return contestContestRankingCoeffService.selectContestContestRankingCoeffById(id);
	}
	
	/**
	 * 导出赛事具体成绩列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出赛事具体成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRankingCoeff", value = "赛事具体成绩", required = false, dataType = "ContestContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestContestRankingCoeff contestContestRankingCoeff)
    {
    	List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffList(contestContestRankingCoeff);
        ExcelUtil<ContestContestRankingCoeff> util = new ExcelUtil<ContestContestRankingCoeff>(ContestContestRankingCoeff.class);
        return util.exportExcel(list, "contestContestRankingCoeff");
    } */
    
    /**
	 * 导入赛事具体成绩列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入赛事具体成绩列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "赛事具体成绩文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestContestRankingCoeff> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestContestRankingCoeff.class);
        	for(ContestContestRankingCoeff obj : list) {
        		contestContestRankingCoeffService.insertContestContestRankingCoeff(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存赛事具体成绩
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存赛事具体成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRankingCoeff", value = "赛事具体成绩", required = true, dataType = "ContestContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestContestRankingCoeff contestContestRankingCoeff)
	{
		return toAjax(contestContestRankingCoeffService.insertContestContestRankingCoeff(contestContestRankingCoeff));
	}
	
	/**
	 * 修改保存赛事具体成绩
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存赛事具体成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRankingCoeff", value = "赛事具体成绩", required = true, dataType = "ContestContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestContestRankingCoeff contestContestRankingCoeff)
	{
		return toAjax(contestContestRankingCoeffService.updateContestContestRankingCoeff(contestContestRankingCoeff));
	}
	
	/**
	 * 删除赛事具体成绩
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除赛事具体成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestContestRankingCoeffService.deleteContestContestRankingCoeffByIds(ids));
	}
	
		/**
	 * 根据id删除赛事具体成绩
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除赛事具体成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestContestRankingCoeffService.deleteContestContestRankingCoeffById(id));
	}
	
	
		/**
	 * 查询赛事具体成绩分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询赛事具体成绩分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestContestRankingCoeff", value = "赛事具体成绩", required = true, dataType = "ContestContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestContestRankingCoeff contestContestRankingCoeff)
	{
		startPage(contestContestRankingCoeff.getPd());	
        List<ContestContestRankingCoeff> list = contestContestRankingCoeffService.selectContestContestRankingCoeffList(contestContestRankingCoeff);
		return getDataTable(list);
	}
	



	
}
