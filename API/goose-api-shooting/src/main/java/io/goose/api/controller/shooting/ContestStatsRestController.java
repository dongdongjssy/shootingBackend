package io.goose.api.controller.shooting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.AgeGroup;
import io.goose.shooting.domain.ContestStats;
import io.goose.shooting.domain.Stats;
import io.goose.shooting.service.IContestStatsService;
import io.goose.system.domain.SysDictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 选手排名 信息操作处理
 * 
 * @author goose
 * @date 2020-05-28
 */
@RestController
@RequestMapping("/shooting/contestStats")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "选手排名 ",description="选手排名")
public class ContestStatsRestController extends BaseController
{

	@Autowired
	private IContestStatsService contestStatsService;


	/**
	 * 查年份 
	 */
	@PostMapping("/getYear")
	@ApiOperation(value="首页选手排名列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = false, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<String> getYear(@RequestBody  ContestStats contestStats)
	{
//        List<Integer> list = contestStatsService.selectYear(contestStats);
        List<String> returnList = new ArrayList<String>();
//        for(Integer i : list) {
        	String total = contestStats.getYear() + "年总成绩";
        	String best =  contestStats.getYear() + "年3场最好";
        	returnList.add(total);
        	returnList.add(best);
//        }
		return returnList;
	}
	
	
	/**
	 *  查年龄组别
	 */
	@GetMapping("/getAgeGroup")
	@ApiOperation(value="  查年龄组别")
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<AgeGroup> getAgeGroup()
	{
		List<AgeGroup> newList = new ArrayList<>();
		AgeGroup ageGroup = new AgeGroup();
		ageGroup.setId(0L);
		ageGroup.setName("全部");
        List<AgeGroup> list = contestStatsService.getAgeGroup();
        newList.add(ageGroup);
        for(AgeGroup ag : list) {
        	newList.add(ag);
        }
		return newList;
	}
	
	
	/**
	 * 我的成绩
	 */
	@PostMapping("/findMyMark")
	@ApiOperation(value="我的成绩")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "我的成绩", required = false, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Map<String,Object> findMyMark(@RequestBody  ContestStats contestStats)
	{
        Map<String,Object> map = contestStatsService.findMyMark(contestStats);
		return map;
	}
	

	
	/**
	 * 首页选手排名列表
	 */
	@PostMapping("/getStats")
	@ApiOperation(value="首页选手排名列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = false, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo getStats(@RequestBody  ContestStats contestStats)
	{
//		startPage();
        List<Stats> list = contestStatsService.getStats(contestStats);
		return getDataTable(list);
	}
	
	
	
	
	/**
	 * 查询选手排名列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询选手排名列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = false, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ContestStats contestStats)
	{
		startPage();
		
        List<ContestStats> list = contestStatsService.selectContestStatsList(contestStats);
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
	public ContestStats getById( @PathVariable("id") Long id) {
		return contestStatsService.selectContestStatsById(id);
	}
	
	/**
	 * 导出选手排名列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出选手排名列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = false, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ContestStats contestStats)
    {
    	List<ContestStats> list = contestStatsService.selectContestStatsList(contestStats);
        ExcelUtil<ContestStats> util = new ExcelUtil<ContestStats>(ContestStats.class);
        return util.exportExcel(list, "contestStats");
    } */
    
    /**
	 * 导入选手排名列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入选手排名列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "选手排名文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ContestStats> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ContestStats.class);
        	for(ContestStats obj : list) {
        		contestStatsService.insertContestStats(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存选手排名
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存选手排名")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = true, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ContestStats contestStats)
	{
		return toAjax(contestStatsService.insertContestStats(contestStats));
	}
	
	/**
	 * 修改保存选手排名
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存选手排名")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = true, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ContestStats contestStats)
	{
		return toAjax(contestStatsService.updateContestStats(contestStats));
	}
	
	/**
	 * 删除选手排名
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除选手排名")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(contestStatsService.deleteContestStatsByIds(ids));
	}
	
		/**
	 * 根据id删除选手排名
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除选手排名")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(contestStatsService.deleteContestStatsById(id));
	}
	
	
		/**
	 * 查询选手排名分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询选手排名分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "contestStats", value = "选手排名", required = true, dataType = "ContestStats")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ContestStats contestStats)
	{
		startPage(contestStats.getPd());	
        List<ContestStats> list = contestStatsService.selectContestStatsList(contestStats);
		return getDataTable(list);
	}
	



	
}
