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

import io.goose.shooting.domain.PaymentCode;
import io.goose.shooting.service.IPaymentCodeService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 付款码管理 信息操作处理
 * 
 * @author goose
 * @date 2021-01-21
 */
@RestController
@RequestMapping("/shooting/paymentCode")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "付款码管理 ",description="付款码管理")
public class PaymentCodeRestController extends BaseController
{

	@Autowired
	private IPaymentCodeService paymentCodeService;



	
	
	/**
	 * 查询付款码管理列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询付款码管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "paymentCode", value = "付款码管理", required = false, dataType = "PaymentCode")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  PaymentCode paymentCode)
	{
		startPage();
		
        List<PaymentCode> list = paymentCodeService.selectPaymentCodeList(paymentCode);
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
	public PaymentCode getById( @PathVariable("id") Long id) {
		return paymentCodeService.selectPaymentCodeById(id);
	}
	
	/**
	 * 导出付款码管理列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出付款码管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "paymentCode", value = "付款码管理", required = false, dataType = "PaymentCode")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(PaymentCode paymentCode)
    {
    	List<PaymentCode> list = paymentCodeService.selectPaymentCodeList(paymentCode);
        ExcelUtil<PaymentCode> util = new ExcelUtil<PaymentCode>(PaymentCode.class);
        return util.exportExcel(list, "paymentCode");
    } */
    
    /**
	 * 导入付款码管理列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入付款码管理列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "付款码管理文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
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
	        
    }*/    

	
	/**
	 * 新增保存付款码管理
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存付款码管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "paymentCode", value = "付款码管理", required = true, dataType = "PaymentCode")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody PaymentCode paymentCode)
	{
		return toAjax(paymentCodeService.insertPaymentCode(paymentCode));
	}
	
	/**
	 * 修改保存付款码管理
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存付款码管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "paymentCode", value = "付款码管理", required = true, dataType = "PaymentCode")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody PaymentCode paymentCode)
	{
		return toAjax(paymentCodeService.updatePaymentCode(paymentCode));
	}
	
	/**
	 * 删除付款码管理
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除付款码管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(paymentCodeService.deletePaymentCodeByIds(ids));
	}
	
		/**
	 * 根据id删除付款码管理
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除付款码管理")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(paymentCodeService.deletePaymentCodeById(id));
	}
	
	
		/**
	 * 查询付款码管理分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询付款码管理分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "paymentCode", value = "付款码管理", required = true, dataType = "PaymentCode")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  PaymentCode paymentCode)
	{
		startPage(paymentCode.getPd());	
        List<PaymentCode> list = paymentCodeService.selectPaymentCodeList(paymentCode);
		return getDataTable(list);
	}
	



	
}
