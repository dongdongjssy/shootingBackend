package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;

import io.goose.common.page.PageDomain;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.RecommendCoach;
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

import io.goose.shooting.domain.RecommendJudge;
import io.goose.shooting.service.IRecommendJudgeService;


import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;

import static java.util.Comparator.comparing;


/**
 * 首页裁判 信息操作处理
 *
 * @author goose
 * @date 2020-06-23
 */
@RestController
@RequestMapping("/shooting/recommendJudge")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "首页裁判 ", description = "首页裁判")
public class RecommendJudgeRestController extends BaseController {

    @Autowired
    private IRecommendJudgeService recommendJudgeService;


    /**
     * 查询首页裁判列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询首页裁判列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "recommendJudge", value = "首页裁判", required = false, dataType = "RecommendJudge")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody RecommendJudge recommendJudge) {
        PageDomain pageDomain = TableSupport.buildPageRequest();

        startPage();

        List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeList(recommendJudge);
        if (pageDomain.getOrderByColumn()==null||pageDomain.getOrderByColumn().equals("")) {
            list.sort(comparing(RecommendJudge::getOnTop).reversed());
        }
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
    public RecommendJudge getById(@PathVariable("id") Long id) {
        return recommendJudgeService.selectRecommendJudgeById(id);
    }

    /**
     * 导出首页裁判列表
     */
/*    @PostMapping("/export")
	@ApiOperation(value="导出首页裁判列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "recommendJudge", value = "首页裁判", required = false, dataType = "RecommendJudge")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(RecommendJudge recommendJudge)
    {
    	List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeList(recommendJudge);
        ExcelUtil<RecommendJudge> util = new ExcelUtil<RecommendJudge>(RecommendJudge.class);
        return util.exportExcel(list, "recommendJudge");
    } */

    /**
     * 导入首页裁判列表
     */
/*    @PostMapping("/import")
	@ApiOperation(value="导入首页裁判列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "首页裁判文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<RecommendJudge> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), RecommendJudge.class);
        	for(RecommendJudge obj : list) {
        		recommendJudgeService.insertRecommendJudge(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/


    /**
     * 新增保存首页裁判
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存首页裁判")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "recommendJudge", value = "首页裁判", required = true, dataType = "RecommendJudge")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody RecommendJudge recommendJudge) {
        return toAjax(recommendJudgeService.insertRecommendJudge(recommendJudge));
    }

    /**
     * 修改保存首页裁判
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存首页裁判")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "recommendJudge", value = "首页裁判", required = true, dataType = "RecommendJudge")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody RecommendJudge recommendJudge) {
        return toAjax(recommendJudgeService.updateRecommendJudge(recommendJudge));
    }

    /**
     * 删除首页裁判
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除首页裁判")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(recommendJudgeService.deleteRecommendJudgeByIds(ids));
    }

    /**
     * 根据id删除首页裁判
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除首页裁判")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(recommendJudgeService.deleteRecommendJudgeById(id));
    }


    /**
     * 查询首页裁判分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询首页裁判分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "recommendJudge", value = "首页裁判", required = true, dataType = "RecommendJudge")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody RecommendJudge recommendJudge) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        startPage(recommendJudge.getPd());
        List<RecommendJudge> list = recommendJudgeService.selectRecommendJudgeList(recommendJudge);
        if (pageDomain.getOrderByColumn()==null||pageDomain.getOrderByColumn().equals("")) {
            list.sort(comparing(RecommendJudge::getOnTop).reversed());
        }
        return getDataTable(list);
    }


}
