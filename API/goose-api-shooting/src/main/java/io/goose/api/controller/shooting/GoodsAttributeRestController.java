package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

import io.goose.shooting.domain.GoodsAttribute;
import io.goose.shooting.service.IGoodsAttributeService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 属性 信息操作处理
 * 
 * @author goose
 * @date 2021-03-18
 */
@RestController
@RequestMapping("/shooting/goodsAttribute")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "属性 ",description="属性")
public class GoodsAttributeRestController extends BaseController
{

	@Autowired
	private IGoodsAttributeService goodsAttributeService;



	
	
	/**
	 * 查询属性列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询属性列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = false, dataType = "GoodsAttribute")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  GoodsAttribute goodsAttribute)
	{
		startPage();
		
        List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeList(goodsAttribute);
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
	public GoodsAttribute getById( @PathVariable("id") Long id) {
		return goodsAttributeService.selectGoodsAttributeById(id);
	}
	
	/**
	 * 导出属性列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出属性列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = false, dataType = "GoodsAttribute")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(GoodsAttribute goodsAttribute)
    {
    	List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeList(goodsAttribute);
        ExcelUtil<GoodsAttribute> util = new ExcelUtil<GoodsAttribute>(GoodsAttribute.class);
        return util.exportExcel(list, "goodsAttribute");
    } */
    
    /**
	 * 导入属性列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入属性列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "属性文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsAttribute> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsAttribute.class);
        	for(GoodsAttribute obj : list) {
        		goodsAttributeService.insertGoodsAttribute(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存属性
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存属性")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = true, dataType = "GoodsAttribute")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody GoodsAttribute goodsAttribute)
	{
		return toAjax(goodsAttributeService.insertGoodsAttribute(goodsAttribute));
	}
	
	/**
	 * 修改保存属性
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存属性")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = true, dataType = "GoodsAttribute")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody GoodsAttribute goodsAttribute)
	{
		return toAjax(goodsAttributeService.updateGoodsAttribute(goodsAttribute));
	}
	
	/**
	 * 删除属性
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除属性")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(goodsAttributeService.deleteGoodsAttributeByIds(ids));
	}
	
		/**
	 * 根据id删除属性
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除属性")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(goodsAttributeService.deleteGoodsAttributeById(id));
	}
	
	
		/**
	 * 查询属性分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询属性分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = true, dataType = "GoodsAttribute")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  GoodsAttribute goodsAttribute)
	{
		startPage(goodsAttribute.getPd());	
        List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeList(goodsAttribute);
		return getDataTable(list);
	}



	/**
	 * 查询一种商品所有属性
	 */
	@PostMapping("/selectGoodsAttributeByGoodsId")
	@ApiOperation(value="查询一种商品所有属性")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="body", name = "goodsAttribute", value = "属性", required = true, dataType = "GoodsAttribute")
	})
	public List<Map<String,Object>> getAttributeByGoodsId(@RequestBody  GoodsAttribute goodsAttribute)
	{
		List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeByGoodsId(goodsAttribute.getGoodsId());
		Map<String,List<GoodsAttribute>> ss=list.stream().collect(Collectors.groupingBy(GoodsAttribute::getLabel));
		List<Map<String,Object>> mapList= new ArrayList<>();
		Set set = ss.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String name=it.next().toString();
			Map<String,Object> map=new HashMap<>(16);
			map.put("name",name);
			map.put("item",ss.get(name));
			mapList.add(map);
		}
		return  mapList;
	}





}
