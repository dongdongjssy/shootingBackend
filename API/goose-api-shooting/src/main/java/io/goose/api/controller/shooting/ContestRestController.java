package io.goose.api.controller.shooting;

import java.lang.reflect.Array;
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
import io.goose.common.page.PageDomain;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 赛事 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@RestController
@RequestMapping("/shooting/contest")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "赛事 ", description = "赛事")
public class ContestRestController extends BaseController {

    @Autowired
    private IContestService contestService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRegisterConfigService registerConfigService;

    @Autowired
    private IRegisterService registerService;


    @Autowired
    private IRegisterConfigCustomizedInfoService registerConfigCustomizedInfoService;

    @Autowired
    private IRegisterConfigPersonalInfoService registerConfigPersonalInfoService;

    @Autowired
    private IRegisterConfigRoleInfoService registerConfigRoleInfoService;

    /**
     * 查询赛事列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询赛事列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = false, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody Contest contest) {
        startPage();

        List<Contest> list = contestService.selectContestListAssoc(contest);
        TableDataInfo ret = getDataTable(list);
        PageDomain pd = contest.getPd();
        if (ret.getTotal() <= (pd.getPageNum() - 1) * pd.getPageSize()) {
            return getDataTable(new ArrayList<Contest>());
        }
        return ret;
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Contest getById(@PathVariable("id") Long id) {
        Contest contest = contestService.selectContestByIdAssoc(id);
        if (contest.getContent() != null) {
            contest.setShortContent(StringUtils.Html2Text(contest.getContent()));
        }

        List<Schedule> contestScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "contest"));
        if (contestScheduels != null) {
            contest.setSchedules(contestScheduels);
        }

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "contest"));

        if(registerConfig != null) {
            List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

            List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

            List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
            if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

            contest.setRegisterConfig(registerConfig);
        }

        return contest;
    }


    /**
     * 导出赛事列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出赛事列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "contest", value = "赛事", required = false, dataType
     * = "Contest") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult export(Contest
     * contest) { List<Contest> list = contestService.selectContestList(contest); ExcelUtil<Contest>
     * util = new ExcelUtil<Contest>(Contest.class); return util.exportExcel(list, "contest"); }
     */

    /**
     * 导入赛事列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入赛事列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "赛事文件", required = false, dataType
     * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<Contest> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Contest.class);
     * for(Contest obj : list) { contestService.insertContest(obj); } } catch(ExcelUtilException |
     * IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存赛事
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存赛事")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = true, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Contest contest) {
        return toAjax(contestService.insertContest(contest));
    }


    /**
     * 修改保存赛事
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存赛事")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = true, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Contest contest) {
        return toAjax(contestService.updateContest(contest));
    }


    /**
     * 删除赛事
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除赛事")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(contestService.deleteContestByIds(ids));
    }


    /**
     * 根据id删除赛事
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除赛事")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(contestService.deleteContestById(id));
    }


    /**
     * 查询赛事分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询赛事分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = true, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Contest contest) {
        startPage(contest.getPd());
        List<Contest> list = contestService.selectContestList(contest);
        Register register=new Register();
        register.setClientUserId(contest.getClientUserId());
        register.setFkTable(2);
        List<Register> registerList=registerService.selectRegisterList(register);
        String[] colorArr=new String[]{"#DE4E47","#FFCC50","#2A9E60","#4B8DF2","#9F60FC","#E735B6","#4E5F98","#6D90B4","#E1AD8E","#DF8696"};
/*        for(int i=0;i<list.size();i++){
            int a=i%10;
            list.get(i).setColor(colorArr[a]);
            *//*if(i<10){
                list.get(i).setColor(colorArr[i]);
            }else {
                i=i-10;
                list.get(i).setColor(colorArr[i]);
            }*//*
        }*/
        int i=0;
        for (Contest cont : list) {
            registerList.forEach(r->{
                if(cont.getId()==r.getFkId()){
                    cont.setReleaseStatus(r.getStatus());
                }
            });


            cont.setColor(colorArr[i]);
            i++;
            if (cont.getContent() != null) {
                cont.setShortContent(StringUtils.Html2Text(cont.getContent()));
            }

            List<Schedule> contestScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(cont.getId(), "contest"));
            if (contestScheduels != null) {
                cont.setSchedules(contestScheduels);
            }

            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(cont.getId(), "contest"));

            if(registerConfig != null) {
                List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
                if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

                List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
                if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

                List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
                if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

                cont.setRegisterConfig(registerConfig);
            }
        }

        TableDataInfo ret = getDataTable(list);
        PageDomain pd = contest.getPd();
        if (ret.getTotal() <= (pd.getPageNum() - 1) * pd.getPageSize()) {
            return getDataTable(new ArrayList<Contest>());
        }
        return ret;
    }




   /* *//**
     * 查询赛事列表
     *//*
    @PostMapping("/list/calendar")
    @ApiOperation(value = "查询赛事日历")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "contest", value = "赛事",
            required = false, dataType = "Contest")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Map<String, List<Contest>>> getContestCalendar(@RequestBody Contest contest) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(contest.getParams().get("beginTime").toString());
        Date end = sdf.parse(contest.getParams().get("endTime").toString());
        List<Contest> list = contestService.selectContestList(contest);
        List<String> dateList = getBetweenDates(start, end);
        List<Map<String, List<Contest>>> monthList = new ArrayList<>(16);
        String[] colorArr=new String[]{"#DE4E47","#FFCC50","#2A9E60","#4B8DF2","#9F60FC","#E735B6","#4E5F98","#6D90B4","#E1AD8E","#DF8696"};
        for(int i=0;i<list.size();i++){
            if(i<10){
                list.get(i).setColor(colorArr[i]);
            }else {
                i=i-10;
                list.get(i).setColor(colorArr[i]);
            }
        }
        dateList.forEach(s -> {
            Map<String, List<Contest>> contestMap = new HashMap<>(16);
            List<Contest> contestList = new ArrayList<>(16);
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
    }*/



    /**
     * 查询赛事列表
     */
    @PostMapping("/roleList/{id}")
    @ApiOperation(value = "查询赛事报名配置角色列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<RegisterConfigRoleInfo> getRoleList(@PathVariable("id") Long id) {
        RegisterConfigRoleInfo registerConfigRoleInfo=new RegisterConfigRoleInfo();
        registerConfigRoleInfo.setRegisterConfigId(id);
        return registerConfigRoleInfoService.selectRegisterConfigRoleInfoList(registerConfigRoleInfo);
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
