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

import io.goose.shooting.domain.GoodsInvoice;
import io.goose.shooting.service.IGoodsInvoiceService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 发票 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@RestController
@RequestMapping("/shooting/goodsInvoice")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "发票 ",description="发票")
public class GoodsInvoiceRestController extends BaseController
{

	@Autowired
	private IGoodsInvoiceService goodsInvoiceService;



	
	
	/**
	 * 查询发票列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询发票列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsInvoice", value = "发票", required = false, dataType = "GoodsInvoice")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsInvoice goodsInvoice)
	{
		startPage();
		
        List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceList(goodsInvoice);
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
	public GoodsInvoice getById( @PathVariable("id") Long id) {
		return goodsInvoiceService.selectGoodsInvoiceById(id);
	}
	
	/**
	 * 导出发票列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出发票列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsInvoice", value = "发票", required = false, dataType = "GoodsInvoice")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsInvoice goodsInvoice)
    {
    	List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceList(goodsInvoice);
        ExcelUtil<GoodsInvoice> util = new ExcelUtil<GoodsInvoice>(GoodsInvoice.class);
        return util.exportExcel(list, "goodsInvoice");
    } */
    
    /**
	 * 导入发票列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入发票列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "发票文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsInvoice> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsInvoice.class);
        	for(GoodsInvoice obj : list) {
        		goodsInvoiceService.insertGoodsInvoice(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存发票
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存发票")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsInvoice", value = "发票", required = true, dataType = "GoodsInvoice")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsInvoice goodsInvoice)
	{
		return toAjax(goodsInvoiceService.insertGoodsInvoice(goodsInvoice));
	}
	
	/**
	 * 修改保存发票
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存发票")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsInvoice", value = "发票", required = true, dataType = "GoodsInvoice")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsInvoice goodsInvoice)
	{

		return toAjax(goodsInvoiceService.updateGoodsInvoice(goodsInvoice));
	}
	
	/**
	 * 删除发票
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除发票")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsInvoiceService.deleteGoodsInvoiceByIds(ids));
	}
	
		/**
	 * 根据id删除发票
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除发票")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsInvoiceService.deleteGoodsInvoiceById(id));
	}
	
	
		/**
	 * 查询发票分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询发票分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsInvoice", value = "发票", required = true, dataType = "GoodsInvoice")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsInvoice goodsInvoice)
	{
		startPage(goodsInvoice.getPd());	
        List<GoodsInvoice> list = goodsInvoiceService.selectGoodsInvoiceList(goodsInvoice);
		return getDataTable(list);
	}
	



	
}
