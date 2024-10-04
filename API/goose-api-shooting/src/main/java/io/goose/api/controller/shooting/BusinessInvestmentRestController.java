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

import io.goose.shooting.domain.BusinessInvestment;
import io.goose.shooting.service.IBusinessInvestmentService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 招商 信息操作处理
 *
 * @author goose
 * @date 2020-06-18
 */
@RestController
@RequestMapping("/shooting/businessInvestment")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "招商 ", description = "招商")
public class BusinessInvestmentRestController extends BaseController {

    @Autowired
    private IBusinessInvestmentService businessInvestmentService;


    /**
     * 查询招商列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询招商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "businessInvestment", value = "招商", required = false, dataType = "BusinessInvestment")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody BusinessInvestment businessInvestment) {
        startPage();

        List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentList(businessInvestment);
        return getDataTable(list);
    }

    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Long")})
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public BusinessInvestment getById(@PathVariable("id") Long id) {
        return businessInvestmentService.selectBusinessInvestmentById(id);
    }

    /**
     * 导出招商列表
     */
/*    @PostMapping("/export")
	@ApiOperation(value="导出招商列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "businessInvestment", value = "招商", required = false, dataType = "BusinessInvestment")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(BusinessInvestment businessInvestment)
    {
    	List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentList(businessInvestment);
        ExcelUtil<BusinessInvestment> util = new ExcelUtil<BusinessInvestment>(BusinessInvestment.class);
        return util.exportExcel(list, "businessInvestment");
    } */

    /**
     * 导入招商列表
     */
/*    @PostMapping("/import")
	@ApiOperation(value="导入招商列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "招商文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<BusinessInvestment> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), BusinessInvestment.class);
        	for(BusinessInvestment obj : list) {
        		businessInvestmentService.insertBusinessInvestment(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/


    /**
     * 新增保存招商
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存招商")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "businessInvestment", value = "招商", required = true, dataType = "BusinessInvestment")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody BusinessInvestment businessInvestment) {
        return toAjax(businessInvestmentService.insertBusinessInvestment(businessInvestment));
    }

    /**
     * 修改保存招商
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存招商")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "businessInvestment", value = "招商", required = true, dataType = "BusinessInvestment")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody BusinessInvestment businessInvestment) {
        return toAjax(businessInvestmentService.updateBusinessInvestment(businessInvestment));
    }

    /**
     * 删除招商
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除招商")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(businessInvestmentService.deleteBusinessInvestmentByIds(ids));
    }

    /**
     * 根据id删除招商
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除招商")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(businessInvestmentService.deleteBusinessInvestmentById(id));
    }


    /**
     * 查询招商分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询招商分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "businessInvestment", value = "招商", required = true, dataType = "BusinessInvestment")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody BusinessInvestment businessInvestment) {
        startPage(businessInvestment.getPd());
        List<BusinessInvestment> list = businessInvestmentService.selectBusinessInvestmentList(businessInvestment);
        return getDataTable(list);
    }


}
