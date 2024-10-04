package io.goose.api.controller.shooting;

import java.util.List;
import java.util.stream.Collectors;

import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.domain.Judge;
import io.goose.shooting.service.impl.ext.CoachServiceImplExt;
import io.goose.shooting.service.impl.ext.JudgeServiceImplExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.ClubJudge;
import io.goose.shooting.service.IClubJudgeService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 俱乐部裁判 信息操作处理
 * 
 * @author goose
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/shooting/clubJudge")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "俱乐部裁判 ",description="俱乐部裁判")
public class ClubJudgeRestController extends ClubRestBaseController
{

	@Autowired
	private IClubJudgeService clubJudgeService;

	@Autowired
	JudgeServiceImplExt judgeServiceImplExt;

	
	
	/**
	 * 查询俱乐部裁判列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询俱乐部裁判列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJudge", value = "俱乐部裁判", required = false, dataType = "ClubJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ClubJudge clubJudge)
	{
		startPage();
		
        List<ClubJudge> list = clubJudgeService.selectClubJudgeList(clubJudge);
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
	public ClubJudge getById( @PathVariable("id") Long id) {
		return clubJudgeService.selectClubJudgeById(id);
	}



	/**
	 * 根据Club ID查询
	 */
	@PostMapping( "/getByClubId/{clubId}" )
	@ApiOperation( value = " 根据外键ID查询" )
	@ApiImplicitParams( { @ApiImplicitParam( paramType = "query", name = "clubId", value = "外键",
			required = true, dataType = "Long" ) } )
	public List<Judge> getByClubId(@PathVariable( "clubId" ) Long clubId ) {
		List<ClubJudge> clubCoachList = clubJudgeService.selectClubJudgeAllAssoc();
		List<Judge> coachList =
				clubCoachList.stream().filter( clubCoach -> clubCoach.getClubId() == clubId )
						.map( clubCoach -> clubCoach.getJudge() ).collect( Collectors.toList() );

		coachList.forEach( coach -> judgeServiceImplExt.setCategoryNameToRemark( coach ) );

		return coachList;
	}





	/**
	 * 导出俱乐部裁判列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出俱乐部裁判列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJudge", value = "俱乐部裁判", required = false, dataType = "ClubJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(ClubJudge clubJudge)
    {
    	List<ClubJudge> list = clubJudgeService.selectClubJudgeList(clubJudge);
        ExcelUtil<ClubJudge> util = new ExcelUtil<ClubJudge>(ClubJudge.class);
        return util.exportExcel(list, "clubJudge");
    } */
    
    /**
	 * 导入俱乐部裁判列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入俱乐部裁判列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "俱乐部裁判文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<ClubJudge> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubJudge.class);
        	for(ClubJudge obj : list) {
        		clubJudgeService.insertClubJudge(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存俱乐部裁判
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存俱乐部裁判")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJudge", value = "俱乐部裁判", required = true, dataType = "ClubJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody ClubJudge clubJudge)
	{
		return toAjax(clubJudgeService.insertClubJudge(clubJudge));
	}
	
	/**
	 * 修改保存俱乐部裁判
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存俱乐部裁判")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJudge", value = "俱乐部裁判", required = true, dataType = "ClubJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ClubJudge clubJudge)
	{
		return toAjax(clubJudgeService.updateClubJudge(clubJudge));
	}
	
	/**
	 * 删除俱乐部裁判
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除俱乐部裁判")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(clubJudgeService.deleteClubJudgeByIds(ids));
	}
	
		/**
	 * 根据id删除俱乐部裁判
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除俱乐部裁判")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(clubJudgeService.deleteClubJudgeById(id));
	}
	
	
		/**
	 * 查询俱乐部裁判分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询俱乐部裁判分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJudge", value = "俱乐部裁判", required = true, dataType = "ClubJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  ClubJudge clubJudge)
	{
		startPage(clubJudge.getPd());	
        List<ClubJudge> list = clubJudgeService.selectClubJudgeList(clubJudge);
		return getDataTable(list);
	}
	



	
}
