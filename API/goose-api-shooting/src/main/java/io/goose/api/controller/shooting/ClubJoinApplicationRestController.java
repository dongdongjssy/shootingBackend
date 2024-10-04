package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.base.ClubRestBaseController;

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

import io.goose.shooting.domain.ClubJoinApplication;
import io.goose.shooting.service.IClubJoinApplicationService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;


/**
 * 入群申请 信息操作处理
 *
 * @author goose
 * @date 2020-05-23
 */
@RestController
@RequestMapping("/shooting/clubJoinApplication")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "入群申请 ",description="入群申请")
public class ClubJoinApplicationRestController extends ClubRestBaseController
{

	@Autowired
	private IClubJoinApplicationService clubJoinApplicationService;





	/**
	 * 查询入群申请列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询入群申请列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJoinApplication", value = "入群申请", required = false, dataType = "ClubJoinApplication")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  ClubJoinApplication clubJoinApplication)
	{
		startPage();
		clubJoinApplication = setClubId(clubJoinApplication);
        List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
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
	public ClubJoinApplication getById( @PathVariable("id") Long id) {
		return clubJoinApplicationService.selectClubJoinApplicationById(id);
	}

	/**
	 * 导出入群申请列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出入群申请列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJoinApplication", value = "入群申请", required = false, dataType = "ClubJoinApplication")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult export(ClubJoinApplication clubJoinApplication)
    {
    	List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
        ExcelUtil<ClubJoinApplication> util = new ExcelUtil<ClubJoinApplication>(ClubJoinApplication.class);
        return util.exportExcel(list, "clubJoinApplication");
    } */

    /**
	 * 导入入群申请列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入入群申请列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "入群申请文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {
    	try {
        	List<ClubJoinApplication> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubJoinApplication.class);
        	for(ClubJoinApplication obj : list) {
        		clubJoinApplicationService.insertClubJoinApplication(obj);
        	}
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }*/


	/**
	 * 新增保存入群申请
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存入群申请")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJoinApplication", value = "入群申请", required = true, dataType = "ClubJoinApplication")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult add( @RequestBody ClubJoinApplication clubJoinApplication)
	{
		clubJoinApplication = setClubId(clubJoinApplication);
		return toAjax(clubJoinApplicationService.insertClubJoinApplication(clubJoinApplication));
	}

	/**
	 * 修改保存入群申请
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存入群申请")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJoinApplication", value = "入群申请", required = true, dataType = "ClubJoinApplication")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody ClubJoinApplication clubJoinApplication)
	{
		return toAjax(clubJoinApplicationService.updateClubJoinApplication(clubJoinApplication));
	}

	/**
	 * 删除入群申请
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除入群申请")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult remove( String ids)
	{
		return toAjax(clubJoinApplicationService.deleteClubJoinApplicationByIds(ids));
	}

		/**
	 * 根据id删除入群申请
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除入群申请")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult removeById( @PathVariable("id") Long id)
	{
		return toAjax(clubJoinApplicationService.deleteClubJoinApplicationById(id));
	}


		/**
	 * 查询入群申请分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询入群申请分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "clubJoinApplication", value = "入群申请", required = true, dataType = "ClubJoinApplication")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo listPage( @RequestBody  ClubJoinApplication clubJoinApplication)
	{
		startPage(clubJoinApplication.getPd());
		clubJoinApplication = setClubId(clubJoinApplication);
        List<ClubJoinApplication> list = clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication);
		return getDataTable(list);
	}





}
