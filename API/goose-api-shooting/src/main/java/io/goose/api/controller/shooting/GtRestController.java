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

import io.goose.shooting.domain.Gt;
import io.goose.shooting.service.IGtService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商品属性关联 信息操作处理
 * 
 * @author goose
 * @date 2021-03-18
 */
@RestController
@RequestMapping("/shooting/gt")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商品属性关联 ",description="商品属性关联")
public class GtRestController extends BaseController
{

	@Autowired
	private IGtService gtService;



	
	
	/**
	 * 查询商品属性关联列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商品属性关联列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = false, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  Gt gt)
	{
		startPage();
		
        List<Gt> list = gtService.selectGtList(gt);
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
	public Gt getById( @PathVariable("id") Long id) {
		return gtService.selectGtById(id);
	}
	
	/**
	 * 导出商品属性关联列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商品属性关联列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = false, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Gt gt)
    {
    	List<Gt> list = gtService.selectGtList(gt);
        ExcelUtil<Gt> util = new ExcelUtil<Gt>(Gt.class);
        return util.exportExcel(list, "gt");
    } */
    
    /**
	 * 导入商品属性关联列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商品属性关联列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商品属性关联文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Gt> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Gt.class);
        	for(Gt obj : list) {
        		gtService.insertGt(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商品属性关联
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商品属性关联")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = true, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody Gt gt)
	{
		return toAjax(gtService.insertGt(gt));
	}
	
	/**
	 * 修改保存商品属性关联
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商品属性关联")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = true, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Gt gt)
	{
		return toAjax(gtService.updateGt(gt));
	}
	
	/**
	 * 删除商品属性关联
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商品属性关联")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(gtService.deleteGtByIds(ids));
	}
	
		/**
	 * 根据id删除商品属性关联
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商品属性关联")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(gtService.deleteGtById(id));
	}
	
	
		/**
	 * 查询商品属性关联分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商品属性关联分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = true, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Gt gt)
	{
		startPage(gt.getPd());	
        List<Gt> list = gtService.selectGtList(gt);
		return getDataTable(list);
	}

	/**
	 * 查询商品属性关联分页列表
	 */
	@PostMapping("/getNums")
	@ApiOperation(value="查询商品属性关联分页列表")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="body", name = "gt", value = "商品属性关联", required = true, dataType = "Gt")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Integer getNums( @RequestBody  Gt gt)
	{
		List<Gt> list = gtService.selectGtByGoodsIdAndAttribute(gt);
		if(list.isEmpty()){
			return 0;
		}
		return list.get(0).getNum();
	}




}
