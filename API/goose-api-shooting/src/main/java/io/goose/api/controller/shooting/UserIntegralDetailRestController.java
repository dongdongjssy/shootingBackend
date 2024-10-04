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

import io.goose.shooting.domain.UserIntegralDetail;
import io.goose.shooting.service.IUserIntegralDetailService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 用户电子积分卡明细 信息操作处理
 * 
 * @author goose
 * @date 2021-03-31
 */
@RestController
@RequestMapping("/shooting/userIntegralDetail")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户电子积分卡明细 ",description="用户电子积分卡明细")
public class UserIntegralDetailRestController extends BaseController
{

	@Autowired
	private IUserIntegralDetailService userIntegralDetailService;



	
	
	/**
	 * 查询用户电子积分卡明细列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询用户电子积分卡明细列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegralDetail", value = "用户电子积分卡明细", required = false, dataType = "UserIntegralDetail")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  UserIntegralDetail userIntegralDetail)
	{
		startPage();
		
        List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailList(userIntegralDetail);
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
	public UserIntegralDetail getById( @PathVariable("id") Long id) {
		return userIntegralDetailService.selectUserIntegralDetailById(id);
	}
	
	/**
	 * 导出用户电子积分卡明细列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出用户电子积分卡明细列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegralDetail", value = "用户电子积分卡明细", required = false, dataType = "UserIntegralDetail")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(UserIntegralDetail userIntegralDetail)
    {
    	List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailList(userIntegralDetail);
        ExcelUtil<UserIntegralDetail> util = new ExcelUtil<UserIntegralDetail>(UserIntegralDetail.class);
        return util.exportExcel(list, "userIntegralDetail");
    } */
    
    /**
	 * 导入用户电子积分卡明细列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入用户电子积分卡明细列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "用户电子积分卡明细文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<UserIntegralDetail> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserIntegralDetail.class);
        	for(UserIntegralDetail obj : list) {
        		userIntegralDetailService.insertUserIntegralDetail(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存用户电子积分卡明细
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存用户电子积分卡明细")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegralDetail", value = "用户电子积分卡明细", required = true, dataType = "UserIntegralDetail")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody UserIntegralDetail userIntegralDetail)
	{
		return toAjax(userIntegralDetailService.insertUserIntegralDetail(userIntegralDetail));
	}
	
	/**
	 * 修改保存用户电子积分卡明细
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存用户电子积分卡明细")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegralDetail", value = "用户电子积分卡明细", required = true, dataType = "UserIntegralDetail")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody UserIntegralDetail userIntegralDetail)
	{
		return toAjax(userIntegralDetailService.updateUserIntegralDetail(userIntegralDetail));
	}
	
	/**
	 * 删除用户电子积分卡明细
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除用户电子积分卡明细")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(userIntegralDetailService.deleteUserIntegralDetailByIds(ids));
	}
	
		/**
	 * 根据id删除用户电子积分卡明细
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除用户电子积分卡明细")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(userIntegralDetailService.deleteUserIntegralDetailById(id));
	}
	
	
		/**
	 * 查询用户电子积分卡明细分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询用户电子积分卡明细分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegralDetail", value = "用户电子积分卡明细", required = true, dataType = "UserIntegralDetail")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  UserIntegralDetail userIntegralDetail)
	{
		startPage(userIntegralDetail.getPd());	
        List<UserIntegralDetail> list = userIntegralDetailService.selectUserIntegralDetailList(userIntegralDetail);
		return getDataTable(list);
	}
	



	
}
