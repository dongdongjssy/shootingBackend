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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.GoodsCarousel;
import io.goose.shooting.service.IGoodsCarouselService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 商城轮播图 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@RestController
@RequestMapping("/shooting/goodsCarousel")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "商城轮播图 ",description="商城轮播图")
public class GoodsCarouselRestController extends BaseController
{

	@Autowired
	private IGoodsCarouselService goodsCarouselService;



	
	
	/**
	 * 查询商城轮播图列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询商城轮播图列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsCarousel", value = "商城轮播图", required = false, dataType = "GoodsCarousel")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsCarousel goodsCarousel)
	{
		startPage();
		
        List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselList(goodsCarousel);
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
	public GoodsCarousel getById( @PathVariable("id") Long id) {
		return goodsCarouselService.selectGoodsCarouselById(id);
	}
	
	/**
	 * 导出商城轮播图列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出商城轮播图列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsCarousel", value = "商城轮播图", required = false, dataType = "GoodsCarousel")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsCarousel goodsCarousel)
    {
    	List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselList(goodsCarousel);
        ExcelUtil<GoodsCarousel> util = new ExcelUtil<GoodsCarousel>(GoodsCarousel.class);
        return util.exportExcel(list, "goodsCarousel");
    } */
    
    /**
	 * 导入商城轮播图列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入商城轮播图列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "商城轮播图文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsCarousel> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsCarousel.class);
        	for(GoodsCarousel obj : list) {
        		goodsCarouselService.insertGoodsCarousel(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存商城轮播图
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存商城轮播图")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsCarousel", value = "商城轮播图", required = true, dataType = "GoodsCarousel")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsCarousel goodsCarousel)
	{
		return toAjax(goodsCarouselService.insertGoodsCarousel(goodsCarousel));
	}
	
	/**
	 * 修改保存商城轮播图
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存商城轮播图")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsCarousel", value = "商城轮播图", required = true, dataType = "GoodsCarousel")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsCarousel goodsCarousel)
	{
		return toAjax(goodsCarouselService.updateGoodsCarousel(goodsCarousel));
	}
	
	/**
	 * 删除商城轮播图
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除商城轮播图")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsCarouselService.deleteGoodsCarouselByIds(ids));
	}
	
		/**
	 * 根据id删除商城轮播图
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除商城轮播图")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsCarouselService.deleteGoodsCarouselById(id));
	}
	
	
		/**
	 * 查询商城轮播图分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询商城轮播图分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsCarousel", value = "商城轮播图", required = true, dataType = "GoodsCarousel")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsCarousel goodsCarousel)
	{
		startPage(goodsCarousel.getPd());	
        List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselList(goodsCarousel);
		return getDataTable(list);
	}
	



	
}
