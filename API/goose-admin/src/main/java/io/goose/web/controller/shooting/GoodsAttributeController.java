package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.shooting.domain.Goods;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.shooting.domain.GoodsAttribute;
import io.goose.shooting.service.IGoodsAttributeService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IGoodsService;
import io.goose.shooting.service.IGoodsTypeService;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 属性 信息操作处理
 * 
 * @author goose
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/shooting/goodsAttribute")
public class GoodsAttributeController extends BaseController
{

    private String prefix = "shooting/goodsAttribute";
	
	@Autowired
	private IGoodsAttributeService goodsAttributeService;

	@Autowired
	private IGoodsService goodsService;



	
	
	@RequiresPermissions("shooting:goodsAttribute:view")
	@GetMapping()
	public String goodsAttribute(ModelMap mmap)
	{
	    mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    return prefix + "/goodsAttribute";
	}
	
	/**
	 * 查询属性列表
	 */
	@RequiresPermissions("shooting:goodsAttribute:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsAttribute goodsAttribute)
	{
		startPage();
		goodsAttribute.setCreateBy(ShiroUtils.getLoginName());
        List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeListAssoc(goodsAttribute);
		return getDataTable(list);
	}


	/**
	 * 查询一种商品所有属性
	 */
	@PostMapping("/selectGoodsAttributeByGoodsId")
	@ResponseBody
	public List<Map<String,Object>> getAttributeByGoodsId(GoodsAttribute goodsAttribute)
	{
		List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeByGoodsId(goodsAttribute.getGoodsId());
		Map<String,List<GoodsAttribute>> ss=list.stream().collect(Collectors.groupingBy(GoodsAttribute::getLabel));
		List<Map<String,Object>> mapList= new ArrayList<>();
		Set set = ss.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String name=it.next().toString();
			Map<String,Object> map=new HashMap<>();
			map.put("name",name);
			map.put("item",ss.get(name));
			mapList.add(map);
		}
		return  mapList;
	}


	@PostMapping("/selectAllGoodsAttributeByGoodsId")
	@ResponseBody
	public List<Map<String,Object>> selectAllGoodsAttributeByGoodsId(GoodsAttribute goodsAttribute)
	{
		List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeByGoodsId(goodsAttribute.getGoodsId());
		Map<String,List<GoodsAttribute>> ss=list.stream().collect(Collectors.groupingBy(GoodsAttribute::getLabel));
		List<Map<String,Object>> mapList= new ArrayList<>();
		Set set = ss.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String name=it.next().toString();
			Map<String,Object> map=new HashMap<>();
			map.put("name",name);
			map.put("item",ss.get(name));
			mapList.add(map);
		}
		return  mapList;
	}

	
	
	/**
	 * 导出属性列表
	 */
	@RequiresPermissions("shooting:goodsAttribute:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsAttribute goodsAttribute)
    {
    	List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeList(goodsAttribute);
        ExcelUtil<GoodsAttribute> util = new ExcelUtil<GoodsAttribute>(GoodsAttribute.class);
        return util.exportExcel(list, "goodsAttribute");
    }
    
    /**
	 * 导入属性列表
	 */
	@RequiresPermissions("shooting:goodsAttribute:import")
    @PostMapping("/import")
    @ResponseBody
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
	        
    }
	
	/**
	 * 新增属性
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		Goods goods=new Goods();
		goods.setCreateBy(ShiroUtils.getLoginName());
	    mmap.put("goodsIdList", goodsService.selectGoodsList(goods));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存属性
	 */
	@RequiresPermissions("shooting:goodsAttribute:add")
	@Log(title = "属性", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsAttribute goodsAttribute 
	)
	{		

		goodsAttribute.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsAttributeService.insertGoodsAttribute(goodsAttribute));
	}

	/**
	 * 修改属性
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Goods goods=new Goods();
		goods.setCreateBy(ShiroUtils.getLoginName());
		mmap.put("goodsAttribute", goodsAttributeService.selectGoodsAttributeById(id));
	    mmap.put("goodsIdList", goodsService.selectGoodsList(goods));
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存属性
	 */
	@RequiresPermissions("shooting:goodsAttribute:edit")
	@Log(title = "属性", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsAttribute goodsAttribute 
	)
	{		
		goodsAttribute.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsAttributeService.updateGoodsAttribute(goodsAttribute));
	}
	
	/**
	 * 删除属性
	 */
	@RequiresPermissions("shooting:goodsAttribute:remove")
	@Log(title = "属性", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsAttributeService.deleteGoodsAttributeByIds(ids));
	}
	

	/**
	 * 查询属性分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsAttribute goodsAttribute)
	{
		startPage(goodsAttribute.getPd());
		
        List<GoodsAttribute> list = goodsAttributeService.selectGoodsAttributeListAssoc(goodsAttribute);
		return getDataTable(list);
	}




	
}
