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
import io.goose.shooting.domain.ContestLevelCoeff;
import io.goose.shooting.service.IContestLevelCoeffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 级别系数 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/shooting/contestLevelCoeff")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "级别系数 ",description="级别系数")
public class ContestLevelCoeffRestController extends BaseController
{

	@Autowired
	private IContestLevelCoeffService contestLevelCoeffService;



	
	
	/**
	 * 查询级别系数列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询级别系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestLevelCoeff", value = "级别系数", required = false, dataType = "ContestLevelCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestLevelCoeff contestLevelCoeff)
	{
		startPage();
		
        List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffList(contestLevelCoeff);
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
	public ContestLevelCoeff getById( @PathVariable("id") Long id) {
		return contestLevelCoeffService.selectContestLevelCoeffById(id);
	}
	
	/**
	 * 导出级别系数列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出级别系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestLevelCoeff", value = "级别系数", required = false, dataType = "ContestLevelCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestLevelCoeff contestLevelCoeff)
    {
    	List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffList(contestLevelCoeff);
        ExcelUtil<ContestLevelCoeff> util = new ExcelUtil<ContestLevelCoeff>(ContestLevelCoeff.class);
        return util.exportExcel(list, "contestLevelCoeff");
    } */
    
    /**
	 * 导入级别系数列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入级别系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "级别系数文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestLevelCoeff> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestLevelCoeff.class);
        	for(ContestLevelCoeff obj : list) {
        		contestLevelCoeffService.insertContestLevelCoeff(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存级别系数
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存级别系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestLevelCoeff", value = "级别系数", required = true, dataType = "ContestLevelCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestLevelCoeff contestLevelCoeff)
	{
		return toAjax(contestLevelCoeffService.insertContestLevelCoeff(contestLevelCoeff));
	}
	
	/**
	 * 修改保存级别系数
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存级别系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestLevelCoeff", value = "级别系数", required = true, dataType = "ContestLevelCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestLevelCoeff contestLevelCoeff)
	{
		return toAjax(contestLevelCoeffService.updateContestLevelCoeff(contestLevelCoeff));
	}
	
	/**
	 * 删除级别系数
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除级别系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestLevelCoeffService.deleteContestLevelCoeffByIds(ids));
	}
	
		/**
	 * 根据id删除级别系数
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除级别系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestLevelCoeffService.deleteContestLevelCoeffById(id));
	}
	
	
		/**
	 * 查询级别系数分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询级别系数分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestLevelCoeff", value = "级别系数", required = true, dataType = "ContestLevelCoeff")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestLevelCoeff contestLevelCoeff)
	{
		startPage(contestLevelCoeff.getPd());	
        List<ContestLevelCoeff> list = contestLevelCoeffService.selectContestLevelCoeffList(contestLevelCoeff);
		return getDataTable(list);
	}
	



	
}
