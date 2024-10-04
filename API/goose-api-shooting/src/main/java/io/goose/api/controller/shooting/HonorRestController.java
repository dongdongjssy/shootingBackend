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

import io.goose.shooting.domain.Honor;
import io.goose.shooting.service.IHonorService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 荣誉榜管理 信息操作处理
 * 
 * @author goose
 * @date 2021-02-01
 */
@RestController
@RequestMapping("/shooting/honor")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "荣誉榜管理 ",description="荣誉榜管理")
public class HonorRestController extends BaseController
{

	@Autowired
	private IHonorService honorService;



	
	
	/**
	 * 查询荣誉榜管理列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询荣誉榜管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "honor", value = "荣誉榜管理", required = false, dataType = "Honor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  Honor honor)
	{
		startPage();
		
        List<Honor> list = honorService.selectHonorList(honor);
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
	public Honor getById( @PathVariable("id") Long id) {
		return honorService.selectHonorById(id);
	}
	
	/**
	 * 导出荣誉榜管理列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出荣誉榜管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "honor", value = "荣誉榜管理", required = false, dataType = "Honor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Honor honor)
    {
    	List<Honor> list = honorService.selectHonorList(honor);
        ExcelUtil<Honor> util = new ExcelUtil<Honor>(Honor.class);
        return util.exportExcel(list, "honor");
    } */
    
    /**
	 * 导入荣誉榜管理列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入荣誉榜管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "荣誉榜管理文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Honor> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Honor.class);
        	for(Honor obj : list) {
        		honorService.insertHonor(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存荣誉榜管理
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存荣誉榜管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "honor", value = "荣誉榜管理", required = true, dataType = "Honor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody Honor honor)
	{
		return toAjax(honorService.insertHonor(honor));
	}
	
	/**
	 * 修改保存荣誉榜管理
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存荣誉榜管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "honor", value = "荣誉榜管理", required = true, dataType = "Honor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Honor honor)
	{
		return toAjax(honorService.updateHonor(honor));
	}
	
	/**
	 * 删除荣誉榜管理
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除荣誉榜管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(honorService.deleteHonorByIds(ids));
	}
	
		/**
	 * 根据id删除荣誉榜管理
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除荣誉榜管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(honorService.deleteHonorById(id));
	}
	
	
		/**
	 * 查询荣誉榜管理分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询荣誉榜管理分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "honor", value = "荣誉榜管理", required = true, dataType = "Honor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Honor honor)
	{
		startPage(honor.getPd());	
        List<Honor> list = honorService.selectHonorList(honor);
		return getDataTable(list);
	}
	



	
}
