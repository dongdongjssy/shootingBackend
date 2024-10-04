package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
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
import io.goose.shooting.domain.PaymentCode;
import io.goose.shooting.service.IPaymentCodeService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 付款码管理 信息操作处理
 * 
 * @author goose
 * @date 2021-01-21
 */
@Controller
@RequestMapping("/shooting/paymentCode")
public class PaymentCodeController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(PaymentCodeController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/paymentCode";
	
	@Autowired
	private IPaymentCodeService paymentCodeService;

	@Autowired
	private FileUploadDownloadOSSService fileUploadDownloadService;
	
	
	@RequiresPermissions("shooting:paymentCode:view")
	@GetMapping()
	public String paymentCode(ModelMap mmap)
	{
	    return prefix + "/paymentCode";
	}
	
	/**
	 * 查询付款码管理列表
	 */
	@RequiresPermissions("shooting:paymentCode:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(PaymentCode paymentCode)
	{
		startPage();
        List<PaymentCode> list = paymentCodeService.selectPaymentCodeListAssoc(paymentCode);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出付款码管理列表
	 */
	@RequiresPermissions("shooting:paymentCode:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PaymentCode paymentCode)
    {
    	List<PaymentCode> list = paymentCodeService.selectPaymentCodeList(paymentCode);
        ExcelUtil<PaymentCode> util = new ExcelUtil<PaymentCode>(PaymentCode.class);
        return util.exportExcel(list, "paymentCode");
    }
    
    /**
	 * 导入付款码管理列表
	 */
	@RequiresPermissions("shooting:paymentCode:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<PaymentCode> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), PaymentCode.class);
        	for(PaymentCode obj : list) {
        		paymentCodeService.insertPaymentCode(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增付款码管理
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存付款码管理
	 */
	@RequiresPermissions("shooting:paymentCode:add")
	@Log(title = "付款码管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PaymentCode paymentCode 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.PaymentCode.getValue() , true);
				paymentCode.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		paymentCode.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(paymentCodeService.insertPaymentCode(paymentCode));
	}

	/**
	 * 修改付款码管理
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		PaymentCode paymentCode = paymentCodeService.selectPaymentCodeByIdAssoc(id);
		mmap.put("paymentCode", paymentCode);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存付款码管理
	 */
	@RequiresPermissions("shooting:paymentCode:edit")
	@Log(title = "付款码管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PaymentCode paymentCode 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = fileUploadDownloadService.upload(pictureUrlFile, null, UploadTypeEnums.PaymentCode.getValue() , true);
				paymentCode.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		paymentCode.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(paymentCodeService.updatePaymentCode(paymentCode));
	}
	
	/**
	 * 删除付款码管理
	 */
	@RequiresPermissions("shooting:paymentCode:remove")
	@Log(title = "付款码管理", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(paymentCodeService.deletePaymentCodeByIds(ids));
	}
	

	/**
	 * 查询付款码管理分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody PaymentCode paymentCode)
	{
		startPage(paymentCode.getPd());
		
        List<PaymentCode> list = paymentCodeService.selectPaymentCodeListAssoc(paymentCode);
		return getDataTable(list);
	}



	
}
