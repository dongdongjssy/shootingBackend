package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;
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

import io.goose.shooting.domain.Zh;
import io.goose.shooting.service.IZhService;


import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.common.base.AjaxResult;


/**
 * 总会内容 信息操作处理
 * 
 * @author goose
 * @date 2020-12-09
 */
@RestController
@RequestMapping("/shooting/zh")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "总会内容 ",description="总会内容")
public class ZhRestController extends BaseController
{

	@Autowired
	private IZhService zhService;



	
	
	/**
	 * 查询总会内容列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询总会内容列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "zh", value = "总会内容", required = false, dataType = "Zh")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  Zh zh)
	{
		startPage();
		
        List<Zh> list = zhService.selectZhList(zh);
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
	public Zh getById( @PathVariable("id") Long id) {
		return zhService.selectZhById(id);
	}
	
	/**
	 * 导出总会内容列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出总会内容列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "zh", value = "总会内容", required = false, dataType = "Zh")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Zh zh)
    {
    	List<Zh> list = zhService.selectZhList(zh);
        ExcelUtil<Zh> util = new ExcelUtil<Zh>(Zh.class);
        return util.exportExcel(list, "zh");
    } */
    
    /**
	 * 导入总会内容列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入总会内容列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "总会内容文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Zh> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Zh.class);
        	for(Zh obj : list) {
        		zhService.insertZh(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存总会内容
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存总会内容")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "zh", value = "总会内容", required = true, dataType = "Zh")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody Zh zh)
	{
		return toAjax(zhService.insertZh(zh));
	}
	
	/**
	 * 修改保存总会内容
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存总会内容")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "zh", value = "总会内容", required = true, dataType = "Zh")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Zh zh)
	{
		return toAjax(zhService.updateZh(zh));
	}
	
	/**
	 * 删除总会内容
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除总会内容")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(zhService.deleteZhByIds(ids));
	}
	
		/**
	 * 根据id删除总会内容
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除总会内容")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(zhService.deleteZhById(id));
	}
	
	
		/**
	 * 查询总会内容分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询总会内容分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "zh", value = "总会内容", required = true, dataType = "Zh")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Zh zh)
	{
		startPage(zh.getPd());	
        List<Zh> list = zhService.selectZhList(zh);
		return getDataTable(list);
	}
	



	
}
