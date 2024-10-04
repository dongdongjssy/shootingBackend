package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.shooting.service.IClientUserService;
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
import io.goose.shooting.domain.Honor;
import io.goose.shooting.service.IHonorService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 荣誉榜管理 信息操作处理
 * 
 * @author goose
 * @date 2021-02-01
 */
@Controller
@RequestMapping("/shooting/honor")
public class HonorController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(HonorController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/honor";
	
	@Autowired
	private IHonorService honorService;
	@Autowired
	private IClientUserService clientUserService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	
	
	@RequiresPermissions("shooting:honor:view")
	@GetMapping()
	public String honor(ModelMap mmap)
	{
		mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());

		return prefix + "/honor";
	}
	
	/**
	 * 查询荣誉榜管理列表
	 */
	@RequiresPermissions("shooting:honor:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Honor honor)
	{
		startPage();
        List<Honor> list = honorService.selectHonorListAssoc(honor);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出荣誉榜管理列表
	 */
	@RequiresPermissions("shooting:honor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Honor honor)
    {
    	List<Honor> list = honorService.selectHonorList(honor);
        ExcelUtil<Honor> util = new ExcelUtil<Honor>(Honor.class);
        return util.exportExcel(list, "honor");
    }
    
    /**
	 * 导入荣誉榜管理列表
	 */
	@RequiresPermissions("shooting:honor:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Honor> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Honor.class);
        	for(Honor obj : list) {
        		honorService.insertHonor(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增荣誉榜管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
		mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());

		return prefix + "/add";
	}
	
	/**
	 * 新增保存荣誉榜管理
	 */
	@RequiresPermissions("shooting:honor:add")
	@Log(title = "荣誉榜管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Honor honor 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.Honor.getValue() , true);
				honor.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		honor.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(honorService.insertHonor(honor));
	}

	/**
	 * 修改荣誉榜管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Honor honor = honorService.selectHonorByIdAssoc(id);
		mmap.put("honor", honor);
		mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());

		mmap.put("imageUrlPrefix", getImageUrlPrefix());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存荣誉榜管理
	 */
	@RequiresPermissions("shooting:honor:edit")
	@Log(title = "荣誉榜管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Honor honor 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.Honor.getValue() , true);
				honor.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		honor.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(honorService.updateHonor(honor));
	}
	
	/**
	 * 删除荣誉榜管理
	 */
	@RequiresPermissions("shooting:honor:remove")
	@Log(title = "荣誉榜管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(honorService.deleteHonorByIds(ids));
	}
	

	/**
	 * 查询荣誉榜管理分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Honor honor)
	{
		startPage(honor.getPd());
		
        List<Honor> list = honorService.selectHonorListAssoc(honor);
		return getDataTable(list);
	}




	
}
