package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Club;
import io.goose.shooting.domain.ClubCategory;
import io.goose.shooting.service.IClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 * 俱乐部 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/shooting/club")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "俱乐部 ", description = "俱乐部")
public class ClubRestController extends BaseController {

    @Autowired
    private IClubService clubService;


    /**
     * 查询俱乐部列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询俱乐部列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "club", value = "俱乐部",
            required = false, dataType = "Club")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody Club club) {
        startPage();
        List<Club> list = new LinkedList<>();
        Optional<Long> userId = getUserIdFromHeader();
        if (userId.isPresent()) {
            list = clubService.selectClubListWithUserId(userId.get());
        } else {
            list = clubService.selectClubList(club);
        }
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
    public Club getById(@PathVariable("id") Long id) {
        return clubService.selectClubByIdAssoc(id);
    }

    /**
     * 导出俱乐部列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出俱乐部列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "club", value = "俱乐部", required = false, dataType =
     * "Club") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Club club) {
     * List<Club> list = clubService.selectClubList(club); ExcelUtil<Club> util = new
     * ExcelUtil<Club>(Club.class); return util.exportExcel(list, "club"); }
     */

    /**
     * 导入俱乐部列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入俱乐部列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "俱乐部文件", required = false,
     * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try { List<Club>
     * list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Club.class); for(Club obj : list)
     * { clubService.insertClub(obj); } } catch(ExcelUtilException | IOException e) { return
     * AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存俱乐部
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存俱乐部")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "club", value = "俱乐部",
            required = true, dataType = "Club")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Club club) {
        return toAjax(clubService.insertClub(club));
    }


    /**
     * 修改保存俱乐部
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存俱乐部")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "club", value = "俱乐部",
            required = true, dataType = "Club")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Club club) {
        return toAjax(clubService.updateClub(club));
    }


    /**
     * 删除俱乐部
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除俱乐部")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(clubService.deleteClubByIds(ids));
    }


    /**
     * 根据id删除俱乐部
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除俱乐部")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(clubService.deleteClubById(id));
    }


    /**
     * 查询俱乐部分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询俱乐部分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "club", value = "俱乐部",
            required = true, dataType = "Club")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Club club) {
        startPage(club.getPd());
        List<Club> list = clubService.selectClubList(club);
        return getDataTable(list);
    }

    /** 获取俱乐部类别 */
    @PostMapping("/clubCategoryList")
    public AjaxResult getClubCategories(@RequestBody ClubCategory category) {
        if (category.getType() != null) {
            List<ClubCategory> categories = clubService.getClubCategories(category.getType());
            return AjaxResult.success("获取俱乐部类型列表成功").put("categories", categories);
        } else {
            return AjaxResult.error();
        }
    }



    /**
     * 查询热推俱乐部列表
     */
    @PostMapping("/hotList")
    @ApiOperation(value = "查询热推俱乐部列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "club", value = "俱乐部",
            required = true, dataType = "Club")})
    public TableDataInfo hotList(@RequestBody Club club) {
        List<Club> list = clubService.selectClubListByAreaOrFans(club);
        if(list.isEmpty()){
            List<Club> clubList = clubService.selectClubListByAreaOrFans(new Club());
            return getDataTable(clubList);
        }
        return getDataTable(list);
    }

}
