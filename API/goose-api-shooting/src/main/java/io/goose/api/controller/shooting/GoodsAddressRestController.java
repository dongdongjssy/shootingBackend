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

import io.goose.shooting.domain.GoodsAddress;
import io.goose.shooting.service.IGoodsAddressService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 收货地址 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/shooting/goodsAddress")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "收货地址 ",description="收货地址")
public class GoodsAddressRestController extends BaseController
{

	@Autowired
	private IGoodsAddressService goodsAddressService;



	
	
	/**
	 * 查询收货地址列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询收货地址列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAddress", value = "收货地址", required = false, dataType = "GoodsAddress")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsAddress goodsAddress)
	{
		startPage();
		
        List<GoodsAddress> list = goodsAddressService.selectGoodsAddressList(goodsAddress);
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
	public GoodsAddress getById( @PathVariable("id") Long id) {
		return goodsAddressService.selectGoodsAddressById(id);
	}
	
	/**
	 * 导出收货地址列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出收货地址列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAddress", value = "收货地址", required = false, dataType = "GoodsAddress")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsAddress goodsAddress)
    {
    	List<GoodsAddress> list = goodsAddressService.selectGoodsAddressList(goodsAddress);
        ExcelUtil<GoodsAddress> util = new ExcelUtil<GoodsAddress>(GoodsAddress.class);
        return util.exportExcel(list, "goodsAddress");
    } */
    
    /**
	 * 导入收货地址列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入收货地址列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "收货地址文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsAddress> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsAddress.class);
        	for(GoodsAddress obj : list) {
        		goodsAddressService.insertGoodsAddress(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存收货地址
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存收货地址")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAddress", value = "收货地址", required = true, dataType = "GoodsAddress")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsAddress goodsAddress)
	{
		return toAjax(goodsAddressService.insertGoodsAddress(goodsAddress));
	}
	
	/**
	 * 修改保存收货地址
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存收货地址")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAddress", value = "收货地址", required = true, dataType = "GoodsAddress")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsAddress goodsAddress)
	{
		return toAjax(goodsAddressService.updateGoodsAddress(goodsAddress));
	}
	
	/**
	 * 删除收货地址
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除收货地址")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsAddressService.deleteGoodsAddressByIds(ids));
	}
	
		/**
	 * 根据id删除收货地址
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除收货地址")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsAddressService.deleteGoodsAddressById(id));
	}
	
	
		/**
	 * 查询收货地址分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询收货地址分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAddress", value = "收货地址", required = true, dataType = "GoodsAddress")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsAddress goodsAddress)
	{
		startPage(goodsAddress.getPd());	
        List<GoodsAddress> list = goodsAddressService.selectGoodsAddressList(goodsAddress);
		return getDataTable(list);
	}
	



	
}
