package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.shooting.domain.GoodsType;
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
import io.goose.shooting.domain.Goods;
import io.goose.shooting.service.IGoodsService;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.IGoodsTypeService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 商品 信息操作处理
 * 
 * @author goose
 * @date 2021-03-03
 */
@Controller
@RequestMapping("/shooting/goods")
public class GoodsController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/goods";
	
	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IGoodsTypeService goodsTypeService;
	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;

	
	
	@RequiresPermissions("shooting:goods:view")
	@GetMapping()
	public String goods(ModelMap mmap)
	{
	    mmap.put("goodsTypeIdList", goodsTypeService.selectGoodsTypeAll());
	    return prefix + "/goods";
	}
	
	/**
	 * 查询商品列表
	 */
	@RequiresPermissions("shooting:goods:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Goods goods)
	{
		startPage();
		goods.setCreateBy(ShiroUtils.getLoginName());

		List<Goods> list = goodsService.selectGoodsListAssoc(goods);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品列表
	 */
	@RequiresPermissions("shooting:goods:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Goods goods)
    {
    	List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        return util.exportExcel(list, "goods");
    }
    
    /**
	 * 导入商品列表
	 */
	@RequiresPermissions("shooting:goods:import")
    @PostMapping("/import")
    @ResponseBody
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
	        
    }
	
	/**
	 * 新增商品
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		GoodsType goodsType=new GoodsType();
		goodsType.setStatus(0);
	    mmap.put("goodsTypeIdList", goodsTypeService.selectGoodsTypeList(goodsType));
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品
	 */
	@RequiresPermissions("shooting:goods:add")
	@Log(title = "商品", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Goods goods 
				 , @RequestParam(value="pictureUrl1File", required=false) MultipartFile pictureUrl1File
				 , @RequestParam(value="pictureUrl2File", required=false) MultipartFile pictureUrl2File
				 , @RequestParam(value="pictureUrl3File", required=false) MultipartFile pictureUrl3File
				 , @RequestParam(value="pictureUrl4File", required=false) MultipartFile pictureUrl4File
				 , @RequestParam(value="pictureUrl5File", required=false) MultipartFile pictureUrl5File
				 , @RequestParam(value="pictureUrl6File", required=false) MultipartFile pictureUrl6File
				 , @RequestParam(value="pictureUrl7File", required=false) MultipartFile pictureUrl7File
				 , @RequestParam(value="pictureUrl8File", required=false) MultipartFile pictureUrl8File
				 , @RequestParam(value="pictureUrl9File", required=false) MultipartFile pictureUrl9File
	)
	{		
		try {
			if (pictureUrl1File != null && !pictureUrl1File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl1File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl1(filePath);
			}
			if (pictureUrl2File != null && !pictureUrl2File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl2File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl2(filePath);
			}
			if (pictureUrl3File != null && !pictureUrl3File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl3File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl3(filePath);
			}
			if (pictureUrl4File != null && !pictureUrl4File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl4File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl4(filePath);
			}
			if (pictureUrl5File != null && !pictureUrl5File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl5File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl5(filePath);
			}
			if (pictureUrl6File != null && !pictureUrl6File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl6File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl6(filePath);
			}
			if (pictureUrl7File != null && !pictureUrl7File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl7File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl7(filePath);
			}
			if (pictureUrl8File != null && !pictureUrl8File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl8File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl8(filePath);
			}
			if (pictureUrl9File != null && !pictureUrl9File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl9File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl9(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		goods.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsService.insertGoods(goods));
	}

	/**
	 * 修改商品
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Goods goods = goodsService.selectGoodsByIdAssoc(id);
		mmap.put("goods", goods);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());
	    mmap.put("goodsTypeIdList", goodsTypeService.selectGoodsTypeAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品
	 */
	@RequiresPermissions("shooting:goods:edit")
	@Log(title = "商品", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Goods goods 
				 , @RequestParam(value="pictureUrl1File", required=false) MultipartFile pictureUrl1File
				 , @RequestParam(value="pictureUrl2File", required=false) MultipartFile pictureUrl2File
				 , @RequestParam(value="pictureUrl3File", required=false) MultipartFile pictureUrl3File
				 , @RequestParam(value="pictureUrl4File", required=false) MultipartFile pictureUrl4File
				 , @RequestParam(value="pictureUrl5File", required=false) MultipartFile pictureUrl5File
				 , @RequestParam(value="pictureUrl6File", required=false) MultipartFile pictureUrl6File
				 , @RequestParam(value="pictureUrl7File", required=false) MultipartFile pictureUrl7File
				 , @RequestParam(value="pictureUrl8File", required=false) MultipartFile pictureUrl8File
				 , @RequestParam(value="pictureUrl9File", required=false) MultipartFile pictureUrl9File
	)
	{		
		try {
			if(goods.getPictureUrl1()==null){
				goods.setPictureUrl1("");
			}
			if(goods.getPictureUrl2()==null){
				goods.setPictureUrl2("");
			}
			if(goods.getPictureUrl3()==null){
				goods.setPictureUrl3("");
			}
			if(goods.getPictureUrl4()==null){
				goods.setPictureUrl4("");
			}
			if(goods.getPictureUrl5()==null){
				goods.setPictureUrl5("");
			}
			if(goods.getPictureUrl6()==null){
				goods.setPictureUrl6("");
			}
			if(goods.getPictureUrl7()==null){
				goods.setPictureUrl7("");
			}
			if(goods.getPictureUrl8()==null){
				goods.setPictureUrl8("");
			}
			if(goods.getPictureUrl9()==null){
				goods.setPictureUrl9("");
			}
			if (pictureUrl1File != null && !pictureUrl1File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl1File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl1(filePath);
			}
			if (pictureUrl2File != null && !pictureUrl2File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl2File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl2(filePath);
			}
			if (pictureUrl3File != null && !pictureUrl3File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl3File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl3(filePath);
			}
			if (pictureUrl4File != null && !pictureUrl4File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl4File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl4(filePath);
			}
			if (pictureUrl5File != null && !pictureUrl5File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl5File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl5(filePath);
			}
			if (pictureUrl6File != null && !pictureUrl6File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl6File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl6(filePath);
			}
			if (pictureUrl7File != null && !pictureUrl7File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl7File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl7(filePath);
			}
			if (pictureUrl8File != null && !pictureUrl8File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl8File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl8(filePath);
			}
			if (pictureUrl9File != null && !pictureUrl9File.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrl9File, null, UploadTypeEnums.Goods.getValue(), true);
				goods.setPictureUrl9(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}
		goods.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(goodsService.updateGoods(goods));
	}



	/**
	 * 商品上下架
	 */
	@RequiresPermissions("shooting:goods:edit")
	@Log(title = "商品", businessType = BusinessType.UPDATE)
	@PostMapping("/updateGoodsStatus")
	@ResponseBody
	public AjaxResult updateGoodsStatus(Goods goods)
	{
		return toAjax(goodsService.updateGoodsStatus(goods));
	}
	
	/**
	 * 删除商品
	 */
	@RequiresPermissions("shooting:goods:remove")
	@Log(title = "商品", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsService.deleteGoodsByIds(ids));
	}
	

	/**
	 * 查询商品分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Goods goods)
	{
		startPage(goods.getPd());
		
        List<Goods> list = goodsService.selectGoodsListAssoc(goods);
		return getDataTable(list);
	}

	
}
