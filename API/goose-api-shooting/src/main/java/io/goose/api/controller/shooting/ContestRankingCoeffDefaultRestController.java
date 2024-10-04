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
import io.goose.shooting.domain.ContestRankingCoeffDefault;
import io.goose.shooting.service.IContestRankingCoeffDefaultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 默认名次系数 信息操作处理
 * 
 * @author goose
 * @date 2020-07-01
 */
@RestController
@RequestMapping("/shooting/contestRankingCoeffDefault")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "默认名次系数 ",description="默认名次系数")
public class ContestRankingCoeffDefaultRestController extends BaseController
{

	@Autowired
	private IContestRankingCoeffDefaultService contestRankingCoeffDefaultService;



	
	
	/**
	 * 查询默认名次系数列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询默认名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeffDefault", value = "默认名次系数", required = false, dataType = "ContestRankingCoeffDefault")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		startPage();
		
        List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
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
	public ContestRankingCoeffDefault getById( @PathVariable("id") Long id) {
		return contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultById(id);
	}
	
	/**
	 * 导出默认名次系数列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出默认名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeffDefault", value = "默认名次系数", required = false, dataType = "ContestRankingCoeffDefault")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestRankingCoeffDefault contestRankingCoeffDefault)
    {
    	List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
        ExcelUtil<ContestRankingCoeffDefault> util = new ExcelUtil<ContestRankingCoeffDefault>(ContestRankingCoeffDefault.class);
        return util.exportExcel(list, "contestRankingCoeffDefault");
    } */
    
    /**
	 * 导入默认名次系数列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入默认名次系数列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "默认名次系数文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
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
	        
    }*/    

	
	/**
	 * 新增保存默认名次系数
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存默认名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeffDefault", value = "默认名次系数", required = true, dataType = "ContestRankingCoeffDefault")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		return toAjax(contestRankingCoeffDefaultService.insertContestRankingCoeffDefault(contestRankingCoeffDefault));
	}
	
	/**
	 * 修改保存默认名次系数
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存默认名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeffDefault", value = "默认名次系数", required = true, dataType = "ContestRankingCoeffDefault")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		return toAjax(contestRankingCoeffDefaultService.updateContestRankingCoeffDefault(contestRankingCoeffDefault));
	}
	
	/**
	 * 删除默认名次系数
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除默认名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestRankingCoeffDefaultService.deleteContestRankingCoeffDefaultByIds(ids));
	}
	
		/**
	 * 根据id删除默认名次系数
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除默认名次系数")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestRankingCoeffDefaultService.deleteContestRankingCoeffDefaultById(id));
	}
	
	
		/**
	 * 查询默认名次系数分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询默认名次系数分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestRankingCoeffDefault", value = "默认名次系数", required = true, dataType = "ContestRankingCoeffDefault")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestRankingCoeffDefault contestRankingCoeffDefault)
	{
		startPage(contestRankingCoeffDefault.getPd());	
        List<ContestRankingCoeffDefault> list = contestRankingCoeffDefaultService.selectContestRankingCoeffDefaultList(contestRankingCoeffDefault);
		return getDataTable(list);
	}
	



	
}
