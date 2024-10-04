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
import io.goose.shooting.domain.ContestRankingCoeff;
import io.goose.shooting.service.IContestRankingCoeffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 名次系数 信息操作处理
 * 
 * @author goose
 * @date 2020-07-01
 */
@RestController
@RequestMapping("/shooting/contestRankingCoeff")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "名次系数 ",description="名次系数")
public class ContestRankingCoeffRestController extends BaseController
{

	@Autowired
	private IContestRankingCoeffService contestRankingCoeffService;



	
	
	/**
	 * 查询名次系数列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeff", value = "名次系数", required = false, dataType = "ContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestRankingCoeff contestRankingCoeff)
	{
		startPage();
		
        List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffList(contestRankingCoeff);
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
	public ContestRankingCoeff getById( @PathVariable("id") Long id) {
		return contestRankingCoeffService.selectContestRankingCoeffById(id);
	}
	
	/**
	 * 导出名次系数列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeff", value = "名次系数", required = false, dataType = "ContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestRankingCoeff contestRankingCoeff)
    {
    	List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffList(contestRankingCoeff);
        ExcelUtil<ContestRankingCoeff> util = new ExcelUtil<ContestRankingCoeff>(ContestRankingCoeff.class);
        return util.exportExcel(list, "contestRankingCoeff");
    } */
    
    /**
	 * 导入名次系数列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "名次系数文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
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
	        
    }*/    

	
	/**
	 * 新增保存名次系数
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeff", value = "名次系数", required = true, dataType = "ContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestRankingCoeff contestRankingCoeff)
	{
		return toAjax(contestRankingCoeffService.insertContestRankingCoeff(contestRankingCoeff));
	}
	
	/**
	 * 修改保存名次系数
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeff", value = "名次系数", required = true, dataType = "ContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestRankingCoeff contestRankingCoeff)
	{
		return toAjax(contestRankingCoeffService.updateContestRankingCoeff(contestRankingCoeff));
	}
	
	/**
	 * 删除名次系数
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestRankingCoeffService.deleteContestRankingCoeffByIds(ids));
	}
	
		/**
	 * 根据id删除名次系数
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestRankingCoeffService.deleteContestRankingCoeffById(id));
	}
	
	
		/**
	 * 查询名次系数分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询名次系数分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeff", value = "名次系数", required = true, dataType = "ContestRankingCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestRankingCoeff contestRankingCoeff)
	{
		startPage(contestRankingCoeff.getPd());	
        List<ContestRankingCoeff> list = contestRankingCoeffService.selectContestRankingCoeffList(contestRankingCoeff);
		return getDataTable(list);
	}
	



	
}
