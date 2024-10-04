package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
//import org.springframewor.security.access.prepost.PreAuthorize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.goose.shooting.domain.AgeGroup;
import io.goose.shooting.domain.ContestGroup;
import io.goose.shooting.service.IContestGroupService;


import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.common.base.AjaxResult;


/**
 * 组别 信息操作处理
 * 
 * @author goose
 * @date 2020-05-27
 */
@RestController
@RequestMapping("/shooting/contestGroup")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "组别 ",description="组别")
public class ContestGroupRestController extends BaseController
{

	@Autowired
	private IContestGroupService contestGroupService;



	
	
	/**
	 * 查询组别列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询组别列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestGroup", value = "组别", required = false, dataType = "ContestGroup")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestGroup contestGroup)
	{
		startPage();
		
        List<ContestGroup> list = contestGroupService.selectContestGroupList(contestGroup);
		return getDataTable(list);
	}
	
	/**
	 *  查组别
	 */
	@GetMapping("/getGroup")
	@ApiOperation(value="  查组别")
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<AgeGroup> getGroup()
	{
		List<AgeGroup> newList = new ArrayList<>();
		AgeGroup ageGroup = new AgeGroup();
		ageGroup.setId(0L);
		ageGroup.setName("全部");
        newList.add(ageGroup);
        
        List<ContestGroup> list = contestGroupService.selectContestGroupList(new ContestGroup());
        for(ContestGroup cg: list) {
    		AgeGroup ageGroup1 = new AgeGroup();
    		ageGroup1.setId(cg.getId());
    		ageGroup1.setName(cg.getName());
        	newList.add(ageGroup1);
        }
        
		return newList;
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")		
	public ContestGroup getById( @PathVariable("id") Long id) {
		return contestGroupService.selectContestGroupById(id);
	}
	
	/**
	 * 导出组别列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出组别列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestGroup", value = "组别", required = false, dataType = "ContestGroup")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestGroup contestGroup)
    {
    	List<ContestGroup> list = contestGroupService.selectContestGroupList(contestGroup);
        ExcelUtil<ContestGroup> util = new ExcelUtil<ContestGroup>(ContestGroup.class);
        return util.exportExcel(list, "contestGroup");
    } */
    
    /**
	 * 导入组别列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入组别列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "组别文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestGroup> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestGroup.class);
        	for(ContestGroup obj : list) {
        		contestGroupService.insertContestGroup(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存组别
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存组别")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestGroup", value = "组别", required = true, dataType = "ContestGroup")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestGroup contestGroup)
	{
		return toAjax(contestGroupService.insertContestGroup(contestGroup));
	}
	
	/**
	 * 修改保存组别
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存组别")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestGroup", value = "组别", required = true, dataType = "ContestGroup")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestGroup contestGroup)
	{
		return toAjax(contestGroupService.updateContestGroup(contestGroup));
	}
	
	/**
	 * 删除组别
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除组别")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestGroupService.deleteContestGroupByIds(ids));
	}
	
		/**
	 * 根据id删除组别
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除组别")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestGroupService.deleteContestGroupById(id));
	}
	
	
		/**
	 * 查询组别分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询组别分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestGroup", value = "组别", required = true, dataType = "ContestGroup")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestGroup contestGroup)
	{
		startPage(contestGroup.getPd());	
        List<ContestGroup> list = contestGroupService.selectContestGroupList(contestGroup);
		return getDataTable(list);
	}
	



	
}
