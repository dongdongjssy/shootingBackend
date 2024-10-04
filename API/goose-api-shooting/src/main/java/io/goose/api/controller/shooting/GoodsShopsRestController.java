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

import io.goose.shooting.domain.GoodsShops;
import io.goose.shooting.service.IGoodsShopsService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商铺 信息操作处理
 * 
 * @author goose
 * @date 2021-03-19
 */
@RestController
@RequestMapping("/shooting/goodsShops")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商铺 ",description="商铺")
public class GoodsShopsRestController extends BaseController
{

	@Autowired
	private IGoodsShopsService goodsShopsService;



	
	
	/**
	 * 查询商铺列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商铺列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShops", value = "商铺", required = false, dataType = "GoodsShops")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsShops goodsShops)
	{
		startPage();
		
        List<GoodsShops> list = goodsShopsService.selectGoodsShopsList(goodsShops);
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
	public GoodsShops getById( @PathVariable("id") Long id) {
		return goodsShopsService.selectGoodsShopsById(id);
	}
	
	/**
	 * 导出商铺列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商铺列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShops", value = "商铺", required = false, dataType = "GoodsShops")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsShops goodsShops)
    {
    	List<GoodsShops> list = goodsShopsService.selectGoodsShopsList(goodsShops);
        ExcelUtil<GoodsShops> util = new ExcelUtil<GoodsShops>(GoodsShops.class);
        return util.exportExcel(list, "goodsShops");
    } */
    
    /**
	 * 导入商铺列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商铺列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商铺文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsShops> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsShops.class);
        	for(GoodsShops obj : list) {
        		goodsShopsService.insertGoodsShops(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商铺
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商铺")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShops", value = "商铺", required = true, dataType = "GoodsShops")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsShops goodsShops)
	{
		return toAjax(goodsShopsService.insertGoodsShops(goodsShops));
	}
	
	/**
	 * 修改保存商铺
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商铺")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShops", value = "商铺", required = true, dataType = "GoodsShops")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsShops goodsShops)
	{
		return toAjax(goodsShopsService.updateGoodsShops(goodsShops));
	}
	
	/**
	 * 删除商铺
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商铺")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsShopsService.deleteGoodsShopsByIds(ids));
	}
	
		/**
	 * 根据id删除商铺
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商铺")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsShopsService.deleteGoodsShopsById(id));
	}
	
	
		/**
	 * 查询商铺分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商铺分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsShops", value = "商铺", required = true, dataType = "GoodsShops")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsShops goodsShops)
	{
		startPage(goodsShops.getPd());	
        List<GoodsShops> list = goodsShopsService.selectGoodsShopsList(goodsShops);
		return getDataTable(list);
	}
	



	
}
