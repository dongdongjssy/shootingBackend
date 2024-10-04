package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.page.PageDomain;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.Recommend;
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

import io.goose.shooting.domain.RecommendCoach;
import io.goose.shooting.service.IRecommendCoachService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;

import static java.util.Comparator.comparing;


/**
 * 首页教官 信息操作处理
 * 
 * @author goose
 * @date 2020-06-23
 */
@RestController
@RequestMapping("/shooting/recommendCoach")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "首页教官 ",description="首页教官")
public class RecommendCoachRestController extends BaseController
{

	@Autowired
	private IRecommendCoachService recommendCoachService;



	
	
	/**
	 * 查询首页教官列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询首页教官列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendCoach", value = "首页教官", required = false, dataType = "RecommendCoach")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  RecommendCoach recommendCoach)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();

		startPage();
		
        List<RecommendCoach> list = recommendCoachService.selectRecommendCoachList(recommendCoach);
		if (pageDomain.getOrderByColumn()==null||pageDomain.getOrderByColumn().equals("")) {
			list.sort(comparing(RecommendCoach::getOnTop).reversed());
		}
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
	public RecommendCoach getById( @PathVariable("id") Long id) {
		return recommendCoachService.selectRecommendCoachById(id);
	}
	
	/**
	 * 导出首页教官列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出首页教官列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendCoach", value = "首页教官", required = false, dataType = "RecommendCoach")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(RecommendCoach recommendCoach)
    {
    	List<RecommendCoach> list = recommendCoachService.selectRecommendCoachList(recommendCoach);
        ExcelUtil<RecommendCoach> util = new ExcelUtil<RecommendCoach>(RecommendCoach.class);
        return util.exportExcel(list, "recommendCoach");
    } */
    
    /**
	 * 导入首页教官列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入首页教官列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "首页教官文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<RecommendCoach> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), RecommendCoach.class);
        	for(RecommendCoach obj : list) {
        		recommendCoachService.insertRecommendCoach(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存首页教官
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存首页教官")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendCoach", value = "首页教官", required = true, dataType = "RecommendCoach")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody RecommendCoach recommendCoach)
	{
		return toAjax(recommendCoachService.insertRecommendCoach(recommendCoach));
	}
	
	/**
	 * 修改保存首页教官
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存首页教官")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendCoach", value = "首页教官", required = true, dataType = "RecommendCoach")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody RecommendCoach recommendCoach)
	{
		return toAjax(recommendCoachService.updateRecommendCoach(recommendCoach));
	}
	
	/**
	 * 删除首页教官
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除首页教官")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(recommendCoachService.deleteRecommendCoachByIds(ids));
	}
	
		/**
	 * 根据id删除首页教官
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除首页教官")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(recommendCoachService.deleteRecommendCoachById(id));
	}
	
	
		/**
	 * 查询首页教官分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询首页教官分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendCoach", value = "首页教官", required = true, dataType = "RecommendCoach")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  RecommendCoach recommendCoach)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		startPage(recommendCoach.getPd());	
        List<RecommendCoach> list = recommendCoachService.selectRecommendCoachList(recommendCoach);
		if (pageDomain.getOrderByColumn()==null||pageDomain.getOrderByColumn().equals("")) {
			list.sort(comparing(RecommendCoach::getOnTop).reversed());
		}
        return getDataTable(list);
	}
	



	
}
