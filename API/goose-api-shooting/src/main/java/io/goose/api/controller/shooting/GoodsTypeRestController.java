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

import io.goose.shooting.domain.GoodsType;
import io.goose.shooting.service.IGoodsTypeService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商品类型 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/shooting/goodsType")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商品类型 ",description="商品类型")
public class GoodsTypeRestController extends BaseController
{

	@Autowired
	private IGoodsTypeService goodsTypeService;



	
	
	/**
	 * 查询商品类型列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商品类型列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsType", value = "商品类型", required = false, dataType = "GoodsType")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsType goodsType)
	{
		startPage();
		
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
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
	public GoodsType getById( @PathVariable("id") Long id) {
		return goodsTypeService.selectGoodsTypeById(id);
	}
	
	/**
	 * 导出商品类型列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商品类型列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsType", value = "商品类型", required = false, dataType = "GoodsType")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsType goodsType)
    {
    	List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
        ExcelUtil<GoodsType> util = new ExcelUtil<GoodsType>(GoodsType.class);
        return util.exportExcel(list, "goodsType");
    } */
    
    /**
	 * 导入商品类型列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商品类型列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商品类型文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsType> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsType.class);
        	for(GoodsType obj : list) {
        		goodsTypeService.insertGoodsType(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商品类型
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商品类型")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsType", value = "商品类型", required = true, dataType = "GoodsType")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsType goodsType)
	{
		return toAjax(goodsTypeService.insertGoodsType(goodsType));
	}
	
	/**
	 * 修改保存商品类型
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商品类型")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsType", value = "商品类型", required = true, dataType = "GoodsType")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsType goodsType)
	{
		return toAjax(goodsTypeService.updateGoodsType(goodsType));
	}
	
	/**
	 * 删除商品类型
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商品类型")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsTypeService.deleteGoodsTypeByIds(ids));
	}
	
		/**
	 * 根据id删除商品类型
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商品类型")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsTypeService.deleteGoodsTypeById(id));
	}
	
	
		/**
	 * 查询商品类型分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商品类型分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsType", value = "商品类型", required = true, dataType = "GoodsType")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsType goodsType)
	{
		startPage(goodsType.getPd());	
        List<GoodsType> list = goodsTypeService.selectGoodsTypeList(goodsType);
		return getDataTable(list);
	}
	



	
}
