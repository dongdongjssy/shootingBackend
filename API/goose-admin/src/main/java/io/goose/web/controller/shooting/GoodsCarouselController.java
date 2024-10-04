package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.shooting.domain.Goods;
import io.goose.shooting.domain.GoodsType;
import io.goose.shooting.service.IGoodsService;
import io.goose.shooting.service.IGoodsTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.goose.shooting.domain.GoodsCarousel;
import io.goose.shooting.service.IGoodsCarouselService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;

import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 商城轮播图 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@Controller
@RequestMapping("/shooting/goodsCarousel")
public class GoodsCarouselController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(GoodsCarouselController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/goodsCarousel";
	
	@Autowired
	private IGoodsCarouselService goodsCarouselService;
	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IGoodsTypeService goodsTypeService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

	@RequiresPermissions("shooting:goodsCarousel:view")
	@GetMapping()
	public String goodsCarousel(ModelMap mmap)
	{
		mmap.put("goodsIdList", goodsService.selectGoodsAll());
		mmap.put("goodsTypeList", goodsTypeService.selectGoodsTypeAll());
	    return prefix + "/goodsCarousel";
	}
	
	/**
	 * 查询商城轮播图列表
	 */
	@RequiresPermissions("shooting:goodsCarousel:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsCarousel goodsCarousel)
	{
		startPage();
        List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselListAssoc(goodsCarousel);
        list.forEach(goodsCarousel1 -> {
        	Goods goods=goodsService.selectGoodsById(goodsCarousel1.getGoodsId());
        	if(!Objects.isNull(goods)){
				goodsCarousel1.setGoods(goods);
				goodsCarousel1.setGoodsName(goods.getName());
			}
		});
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商城轮播图列表
	 */
	@RequiresPermissions("shooting:goodsCarousel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsCarousel goodsCarousel)
    {
    	List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselList(goodsCarousel);
        ExcelUtil<GoodsCarousel> util = new ExcelUtil<GoodsCarousel>(GoodsCarousel.class);
        return util.exportExcel(list, "goodsCarousel");
    }
    
    /**
	 * 导入商城轮播图列表
	 */
	@RequiresPermissions("shooting:goodsCarousel:import")
    @PostMapping("/import")
    @ResponseBody
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
	        
    }
	
	/**
	 * 新增商城轮播图
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商城轮播图
	 */
	@RequiresPermissions("shooting:goodsCarousel:add")
	@Log(title = "商城轮播图", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsCarousel goodsCarousel 
				 , @RequestParam(value="mediaUrlFile", required=false) MultipartFile mediaUrlFile
	)
	{		
		try {
			if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(mediaUrlFile, null, UploadTypeEnums.GoodsCarousel.getValue(), true);
				goodsCarousel.setMediaUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		goodsCarousel.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsCarouselService.insertGoodsCarousel(goodsCarousel));
	}

	/**
	 * 修改商城轮播图
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsCarousel goodsCarousel = goodsCarouselService.selectGoodsCarouselByIdAssoc(id);
		mmap.put("goodsCarousel", goodsCarousel);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());
		mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商城轮播图
	 */
	@RequiresPermissions("shooting:goodsCarousel:edit")
	@Log(title = "商城轮播图", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsCarousel goodsCarousel 
				 , @RequestParam(value="mediaUrlFile", required=false) MultipartFile mediaUrlFile
	)
	{		
		try {
			if (mediaUrlFile != null && !mediaUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(mediaUrlFile, null, UploadTypeEnums.GoodsCarousel.getValue(), true);
				goodsCarousel.setMediaUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		goodsCarousel.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(goodsCarouselService.updateGoodsCarousel(goodsCarousel));
	}
	
	/**
	 * 删除商城轮播图
	 */
	@RequiresPermissions("shooting:goodsCarousel:remove")
	@Log(title = "商城轮播图", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsCarouselService.deleteGoodsCarouselByIds(ids));
	}
	

	/**
	 * 查询商城轮播图分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsCarousel goodsCarousel)
	{
		startPage(goodsCarousel.getPd());
		
        List<GoodsCarousel> list = goodsCarouselService.selectGoodsCarouselListAssoc(goodsCarousel);
		return getDataTable(list);
	}


	
}
