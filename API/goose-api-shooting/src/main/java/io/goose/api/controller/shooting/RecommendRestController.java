package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Advertisement;
import io.goose.shooting.domain.Recommend;
import io.goose.shooting.domain.details.DynamicSimple;
import io.goose.shooting.domain.details.RecommendDetails;
import io.goose.shooting.service.impl.AdvertisementServiceImpl;
import io.goose.shooting.service.impl.ext.RecommendServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * 推荐 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/shooting/recommend")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "推荐 ", description = "推荐")
public class RecommendRestController extends BaseController {

    @Autowired
    private RecommendServiceImplExt recommendService;

    @Autowired
    private AdvertisementServiceImpl advertisementService;

    /**
     * 查询推荐列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询推荐列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "recommend", value = "推荐",
            required = false, dataType = "Recommend")})
    public TableDataInfo list(@RequestBody Recommend recommend) {
        startPage(recommend.getPd());

        Optional<Long> userId = getUserIdFromHeader();
        if (userId.isPresent()) {
            List<Recommend> recommendList = recommendService.selectRecommendSummaryList(userId.get());
            List<Advertisement> advertisementList = advertisementService.selectAdvertisementAll();

            if (advertisementList.size() > 0) {
                Recommend recommendItem = null;
                if (recommendList.size() > 3) recommendItem = recommendList.get(2);
                else recommendItem = recommendList.get(recommendList.size() - 1);

                Advertisement ad = advertisementList.get(0);
                recommendItem.setAdTitle(ad.getTitle());
                recommendItem.setAdContent(ad.getDetail());
                recommendItem.setAdMediaUrl(ad.getMediaUrl());
            }

            return getDataTable(recommendList);
        } else {
            return getDataTable(recommendService.selectRecommendList(recommend));
        }
    }


    /**
     * 查询推荐列表
     */
    @PostMapping("/simpleList")
    @ApiOperation(value = "查询推荐列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "recommend", value = "推荐",
            required = false, dataType = "Recommend")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo simpleList(@RequestBody Recommend recommend) {
        startPage(recommend.getPd());

        List<DynamicSimple> list = recommendService.selectDynamicSimpleList(recommend);

        return getDataTable(list);
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Recommend getById(@PathVariable("id") Long id) {
        return recommendService.selectRecommendById(id);
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getDetailsById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    public RecommendDetails getDetailsById(@PathVariable("id") Long id) {
        return recommendService.selectRecommendDetailsById(id);
    }

    /**
     * 导出推荐列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出推荐列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "recommend", value = "推荐", required = false,
     * dataType = "Recommend") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(Recommend recommend) { List<Recommend> list =
     * recommendService.selectRecommendList(recommend); ExcelUtil<Recommend> util = new
     * ExcelUtil<Recommend>(Recommend.class); return util.exportExcel(list, "recommend"); }
     */

    /**
     * 导入推荐列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入推荐列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "推荐文件", required = false, dataType
     * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<Recommend> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Recommend.class);
     * for(Recommend obj : list) { recommendService.insertRecommend(obj); } }
     * catch(ExcelUtilException | IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存推荐
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存推荐")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "recommend", value = "推荐",
            required = true, dataType = "Recommend")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Recommend recommend) {
        return toAjax(recommendService.insertRecommend(recommend));
    }


    /**
     * 修改保存推荐
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存推荐")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "recommend", value = "推荐",
            required = true, dataType = "Recommend")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Recommend recommend) {
        return toAjax(recommendService.updateRecommend(recommend));
    }


    /**
     * 删除推荐
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除推荐")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(recommendService.deleteRecommendByIds(ids));
    }


    /**
     * 根据id删除推荐
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除推荐")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(recommendService.deleteRecommendById(id));
    }


    /**
     * 查询推荐分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询推荐分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "recommend", value = "推荐",
            required = true, dataType = "Recommend")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Recommend recommend) {
        startPage(recommend.getPd());
        List<Recommend> list = recommendService.selectRecommendList(recommend);
        return getDataTable(list);
    }

}
