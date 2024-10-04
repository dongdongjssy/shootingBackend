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

import io.goose.shooting.domain.StartAdvertisement;
import io.goose.shooting.service.IStartAdvertisementService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 启动页广告 信息操作处理
 * 
 * @author goose
 * @date 2021-01-06
 */
@RestController
@RequestMapping("/shooting/startAdvertisement")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "启动页广告 ",description="启动页广告")
public class StartAdvertisementRestController extends BaseController
{

	@Autowired
	private IStartAdvertisementService startAdvertisementService;



	
	
	/**
	 * 查询启动页广告列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询启动页广告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "startAdvertisement", value = "启动页广告", required = false, dataType = "StartAdvertisement")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  StartAdvertisement startAdvertisement)
	{
		startPage();
		
        List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementList(startAdvertisement);
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
	public StartAdvertisement getById( @PathVariable("id") Long id) {
		return startAdvertisementService.selectStartAdvertisementById(id);
	}






	/**
	 * 查询启动页广告生效
	 */
	@PostMapping("/startList")
	@ApiOperation(value="查询启动页广告生效列表")
	public StartAdvertisement getStartAdvertisement()
	{
		List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementByTime();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 导出启动页广告列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出启动页广告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "startAdvertisement", value = "启动页广告", required = false, dataType = "StartAdvertisement")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(StartAdvertisement startAdvertisement)
    {
    	List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementList(startAdvertisement);
        ExcelUtil<StartAdvertisement> util = new ExcelUtil<StartAdvertisement>(StartAdvertisement.class);
        return util.exportExcel(list, "startAdvertisement");
    } */
    
    /**
	 * 导入启动页广告列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入启动页广告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "启动页广告文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<StartAdvertisement> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), StartAdvertisement.class);
        	for(StartAdvertisement obj : list) {
        		startAdvertisementService.insertStartAdvertisement(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存启动页广告
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存启动页广告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "startAdvertisement", value = "启动页广告", required = true, dataType = "StartAdvertisement")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody StartAdvertisement startAdvertisement)
	{
		return toAjax(startAdvertisementService.insertStartAdvertisement(startAdvertisement));
	}
	
	/**
	 * 修改保存启动页广告
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存启动页广告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "startAdvertisement", value = "启动页广告", required = true, dataType = "StartAdvertisement")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody StartAdvertisement startAdvertisement)
	{
		return toAjax(startAdvertisementService.updateStartAdvertisement(startAdvertisement));
	}
	
	/**
	 * 删除启动页广告
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除启动页广告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(startAdvertisementService.deleteStartAdvertisementByIds(ids));
	}
	
		/**
	 * 根据id删除启动页广告
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除启动页广告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(startAdvertisementService.deleteStartAdvertisementById(id));
	}
	
	
		/**
	 * 查询启动页广告分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询启动页广告分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "startAdvertisement", value = "启动页广告", required = true, dataType = "StartAdvertisement")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  StartAdvertisement startAdvertisement)
	{
		startPage(startAdvertisement.getPd());	
        List<StartAdvertisement> list = startAdvertisementService.selectStartAdvertisementList(startAdvertisement);
		return getDataTable(list);
	}
	



	
}
