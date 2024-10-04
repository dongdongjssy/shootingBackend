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

import io.goose.shooting.domain.GoodsEvaluation;
import io.goose.shooting.service.IGoodsEvaluationService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商品评价 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/shooting/goodsEvaluation")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商品评价 ",description="商品评价")
public class GoodsEvaluationRestController extends BaseController
{

	@Autowired
	private IGoodsEvaluationService goodsEvaluationService;



	
	
	/**
	 * 查询商品评价列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商品评价列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsEvaluation", value = "商品评价", required = false, dataType = "GoodsEvaluation")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsEvaluation goodsEvaluation)
	{
		startPage();
		
        List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
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
	public GoodsEvaluation getById( @PathVariable("id") Long id) {
		return goodsEvaluationService.selectGoodsEvaluationById(id);
	}
	
	/**
	 * 导出商品评价列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商品评价列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsEvaluation", value = "商品评价", required = false, dataType = "GoodsEvaluation")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsEvaluation goodsEvaluation)
    {
    	List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
        ExcelUtil<GoodsEvaluation> util = new ExcelUtil<GoodsEvaluation>(GoodsEvaluation.class);
        return util.exportExcel(list, "goodsEvaluation");
    } */
    
    /**
	 * 导入商品评价列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商品评价列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商品评价文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsEvaluation> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsEvaluation.class);
        	for(GoodsEvaluation obj : list) {
        		goodsEvaluationService.insertGoodsEvaluation(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商品评价
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商品评价")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsEvaluation", value = "商品评价", required = true, dataType = "GoodsEvaluation")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsEvaluation goodsEvaluation)
	{
		return toAjax(goodsEvaluationService.insertGoodsEvaluation(goodsEvaluation));
	}
	
	/**
	 * 修改保存商品评价
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商品评价")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsEvaluation", value = "商品评价", required = true, dataType = "GoodsEvaluation")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsEvaluation goodsEvaluation)
	{
		return toAjax(goodsEvaluationService.updateGoodsEvaluation(goodsEvaluation));
	}
	
	/**
	 * 删除商品评价
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商品评价")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsEvaluationService.deleteGoodsEvaluationByIds(ids));
	}
	
		/**
	 * 根据id删除商品评价
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商品评价")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsEvaluationService.deleteGoodsEvaluationById(id));
	}
	
	
		/**
	 * 查询商品评价分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商品评价分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsEvaluation", value = "商品评价", required = true, dataType = "GoodsEvaluation")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsEvaluation goodsEvaluation)
	{
		startPage(goodsEvaluation.getPd());	
        List<GoodsEvaluation> list = goodsEvaluationService.selectGoodsEvaluationList(goodsEvaluation);
		return getDataTable(list);
	}
	



	
}
