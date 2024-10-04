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

import io.goose.shooting.domain.UserIntegral;
import io.goose.shooting.service.IUserIntegralService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 用户电子积分卡 信息操作处理
 * 
 * @author goose
 * @date 2021-03-31
 */
@RestController
@RequestMapping("/shooting/userIntegral")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户电子积分卡 ",description="用户电子积分卡")
public class UserIntegralRestController extends BaseController
{

	@Autowired
	private IUserIntegralService userIntegralService;



	
	
	/**
	 * 查询用户电子积分卡列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询用户电子积分卡列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegral", value = "用户电子积分卡", required = false, dataType = "UserIntegral")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  UserIntegral userIntegral)
	{
		startPage();
		
        List<UserIntegral> list = userIntegralService.selectUserIntegralList(userIntegral);
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
	public UserIntegral getById( @PathVariable("id") Long id) {
		return userIntegralService.selectUserIntegralById(id);
	}
	
	/**
	 * 导出用户电子积分卡列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出用户电子积分卡列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegral", value = "用户电子积分卡", required = false, dataType = "UserIntegral")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(UserIntegral userIntegral)
    {
    	List<UserIntegral> list = userIntegralService.selectUserIntegralList(userIntegral);
        ExcelUtil<UserIntegral> util = new ExcelUtil<UserIntegral>(UserIntegral.class);
        return util.exportExcel(list, "userIntegral");
    } */
    
    /**
	 * 导入用户电子积分卡列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入用户电子积分卡列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "用户电子积分卡文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<UserIntegral> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserIntegral.class);
        	for(UserIntegral obj : list) {
        		userIntegralService.insertUserIntegral(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存用户电子积分卡
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存用户电子积分卡")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegral", value = "用户电子积分卡", required = true, dataType = "UserIntegral")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody UserIntegral userIntegral)
	{
		return toAjax(userIntegralService.insertUserIntegral(userIntegral));
	}
	
	/**
	 * 修改保存用户电子积分卡
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存用户电子积分卡")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegral", value = "用户电子积分卡", required = true, dataType = "UserIntegral")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody UserIntegral userIntegral)
	{
		return toAjax(userIntegralService.updateUserIntegral(userIntegral));
	}
	
	/**
	 * 删除用户电子积分卡
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除用户电子积分卡")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(userIntegralService.deleteUserIntegralByIds(ids));
	}
	
		/**
	 * 根据id删除用户电子积分卡
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除用户电子积分卡")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(userIntegralService.deleteUserIntegralById(id));
	}
	
	
		/**
	 * 查询用户电子积分卡分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询用户电子积分卡分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userIntegral", value = "用户电子积分卡", required = true, dataType = "UserIntegral")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  UserIntegral userIntegral)
	{
		startPage(userIntegral.getPd());	
        List<UserIntegral> list = userIntegralService.selectUserIntegralList(userIntegral);
		return getDataTable(list);
	}
	



	
}
