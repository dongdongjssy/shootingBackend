package io.goose.api.controller.shooting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.ClubRestBaseController;
import io.goose.shooting.service.impl.ext.ClubActivityServiceImplExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 俱乐部活动 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@RestController
@RequestMapping("/shooting/clubActivity")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "俱乐部活动 ", description = "俱乐部活动")
public class ClubActivityRestController extends ClubRestBaseController {

    @Autowired
    private ClubActivityServiceImplExt clubActivityService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRegisterConfigService registerConfigService;

    @Autowired
    private IRegisterConfigCustomizedInfoService registerConfigCustomizedInfoService;

    @Autowired
    private IRegisterConfigPersonalInfoService registerConfigPersonalInfoService;

    @Autowired
    private IRegisterConfigRoleInfoService registerConfigRoleInfoService;

    /**
     * 查询俱乐部活动列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询俱乐部活动列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubActivity",
            value = "俱乐部活动", required = false, dataType = "ClubActivity")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody ClubActivity clubActivity) {
        startPage();
        clubActivity = setClubId(clubActivity);
        List<ClubActivity> list = clubActivityService.selectClubActivityListAssoc(clubActivity);
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
    public ClubActivity getById(@PathVariable("id") Long id) {
        ClubActivity clubActivity = clubActivityService.selectClubActivityById(id);

        List<Schedule> contestScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "clubActivity"));
        if (contestScheduels != null) {
            clubActivity.setSchedules(contestScheduels);
        }

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "clubActivity"));

        if(registerConfig != null) {
            List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

            List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

            List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
            if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

            clubActivity.setRegisterConfig(registerConfig);
        }

        return clubActivity;
    }


    /**
     * 根据CLUB ID查询
     */
    @PostMapping("/getByClubId/{clubId}")
    @ApiOperation(value = " 根据CLUB ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "clubId", value = "主键",
            required = true, dataType = "Long")})
    public TableDataInfo getByClubId(@PathVariable("clubId") Long clubId) {
        List<ClubActivity> list = clubActivityService.selectClubActivityListByClubId(clubId);
        return getDataTable(list);
    }

    /**
     * 导出俱乐部活动列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出俱乐部活动列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "clubActivity", value = "俱乐部活动", required = false,
     * dataType = "ClubActivity") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(ClubActivity clubActivity) { List<ClubActivity> list =
     * clubActivityService.selectClubActivityList(clubActivity); ExcelUtil<ClubActivity> util = new
     * ExcelUtil<ClubActivity>(ClubActivity.class); return util.exportExcel(list, "clubActivity"); }
     */

    /**
     * 导入俱乐部活动列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入俱乐部活动列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "俱乐部活动文件", required = false,
     * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<ClubActivity> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(),
     * ClubActivity.class); for(ClubActivity obj : list) {
     * clubActivityService.insertClubActivity(obj); } } catch(ExcelUtilException | IOException e) {
     * return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存俱乐部活动
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存俱乐部活动")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubActivity",
            value = "俱乐部活动", required = true, dataType = "ClubActivity")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody ClubActivity clubActivity) {
        clubActivity = setClubId(clubActivity);
        return toAjax(clubActivityService.insertClubActivity(clubActivity));
    }


    /**
     * 修改保存俱乐部活动
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存俱乐部活动")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubActivity",
            value = "俱乐部活动", required = true, dataType = "ClubActivity")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody ClubActivity clubActivity) {
        return toAjax(clubActivityService.updateClubActivity(clubActivity));
    }


    /**
     * 删除俱乐部活动
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除俱乐部活动")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(clubActivityService.deleteClubActivityByIds(ids));
    }


    /**
     * 根据id删除俱乐部活动
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除俱乐部活动")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(clubActivityService.deleteClubActivityById(id));
    }


    /**
     * 查询俱乐部活动分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询俱乐部活动分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "clubActivity",
            value = "俱乐部活动", required = true, dataType = "ClubActivity")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody ClubActivity clubActivity) {
        startPage(clubActivity.getPd());
        clubActivity = setClubId(clubActivity);
        List<ClubActivity> list = clubActivityService.selectClubActivityList(clubActivity);
        for (ClubActivity c : list) {
            List<Schedule> contestScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(c.getId(), "clubActivity"));
            if (contestScheduels != null) {
                c.setSchedules(contestScheduels);
            }

            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(c.getId(), "clubActivity"));

            if(registerConfig != null) {
                List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
                if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

                List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
                if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

                List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
                if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

                c.setRegisterConfig(registerConfig);
            }
        }
        return getDataTable(list);
    }


    /**
     * 查询赛事列表
     */
    @PostMapping("/list/calendar")
    @ApiOperation(value = "查询赛事日历")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = false, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Map<String, List<ClubActivity>>> getContestCalendar(@RequestBody ClubActivity clubActivity) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(clubActivity.getParams().get("beginTime").toString());
        Date end = sdf.parse(clubActivity.getParams().get("endTime").toString());
        List<ClubActivity> list = clubActivityService.selectClubActivityList(clubActivity);
        List<String> dateList = getBetweenDates(start, end);
        List<Map<String, List<ClubActivity>>> monthList = new ArrayList<>(16);
        dateList.forEach(s -> {
            Map<String, List<ClubActivity>> contestMap = new HashMap<>(16);
            List<ClubActivity> contestList = new ArrayList<>(16);
            list.forEach(c -> {
                try {
                    List<String> dates = getBetweenDates(c.getStartDate(), c.getEndDate());
                    if (dates.contains(s)) {
                        contestList.add(c);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            contestMap.put(s, contestList);
            monthList.add(contestMap);
        });
        return monthList;
    }


    private List<String> getBetweenDates(Date start, Date end) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.compareTo(tempEnd) <= 0) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

}
