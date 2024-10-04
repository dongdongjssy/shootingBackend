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

import io.goose.shooting.domain.Goods;
import io.goose.shooting.service.IGoodsService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商品 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/shooting/goods")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商品 ",description="商品")
public class GoodsRestController extends BaseController
{

	@Autowired
	private IGoodsService goodsService;



	
	
	/**
	 * 查询商品列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商品列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goods", value = "商品", required = false, dataType = "Goods")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  Goods goods)
	{
		startPage();
		
        List<Goods> list = goodsService.selectGoodsList(goods);
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
	public Goods getById( @PathVariable("id") Long id) {
		return goodsService.selectGoodsById(id);
	}
	
	/**
	 * 导出商品列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商品列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goods", value = "商品", required = false, dataType = "Goods")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Goods goods)
    {
    	List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        return util.exportExcel(list, "goods");
    } */
    
    /**
	 * 导入商品列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商品列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商品文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Goods> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Goods.class);
        	for(Goods obj : list) {
        		goodsService.insertGoods(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商品
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商品")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goods", value = "商品", required = true, dataType = "Goods")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody Goods goods)
	{
		return toAjax(goodsService.insertGoods(goods));
	}
	
	/**
	 * 修改保存商品
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商品")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goods", value = "商品", required = true, dataType = "Goods")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Goods goods)
	{
		return toAjax(goodsService.updateGoods(goods));
	}
	
	/**
	 * 删除商品
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商品")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsService.deleteGoodsByIds(ids));
	}
	
		/**
	 * 根据id删除商品
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商品")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsService.deleteGoodsById(id));
	}
	
	
		/**
	 * 查询商品分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商品分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goods", value = "商品", required = true, dataType = "Goods")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Goods goods)
	{
		startPage(goods.getPd());	
        List<Goods> list = goodsService.selectGoodsList(goods);
		return getDataTable(list);
	}
	



	
}
