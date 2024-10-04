package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.goose.system.domain.SysDictData;
import io.goose.system.service.ISysDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.config.Global;
import io.goose.common.enums.BusinessType;
import io.goose.common.enums.UploadTypeEnums;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.rest.service.FileUploadDownloadOSSService;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.ClubBaseController;


/**
 * 俱乐部活动 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@Controller
@RequestMapping("/shooting/clubActivity")
public class ClubActivityController extends ClubBaseController {

    private static final Logger log = LoggerFactory.getLogger(ClubActivityController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/clubActivity";

    @Autowired
    private IClubActivityService clubActivityService;

    @Autowired
    private IClubService clubService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private FileUploadDownloadOSSService fileUploadDownloadService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRegisterConfigService registerConfigService;

    @Autowired
    private IRegisterConfigCustomizedInfoService registerConfigCustomizedInfoService;

    @Autowired
    private IRegisterConfigPersonalInfoService registerConfigPersonalInfoService;

    @Autowired
    private IRegisterConfigRoleInfoService registerConfigRoleInfoService;
    @Autowired
    private IPaymentCodeService paymentCodeService;

    @RequiresPermissions("shooting:clubActivity:view")
    @GetMapping()
    public String clubActivity(ModelMap mmap) {
        Long clubId = getClubId();
        mmap.put("clubIdList",
                clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(3);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/clubActivity";
    }


    /**
     * 查询俱乐部活动列表
     */
    @RequiresPermissions("shooting:clubActivity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClubActivity clubActivity) {
        startPage();
        clubActivity = setClubId(clubActivity);
        List<ClubActivity> list = clubActivityService.selectClubActivityListAssoc(clubActivity);
        return getDataTable(list);
    }


    /**
     * 导出俱乐部活动列表
     */
    @RequiresPermissions("shooting:clubActivity:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ClubActivity clubActivity) {
        clubActivity = setClubId(clubActivity);
        Object bt = clubActivity.getParams().get("beginTime");
        Object et = clubActivity.getParams().get("endTime");

        if (bt == null && et == null) {
            clubActivity.setParams(null);
        }
//      System.out.println(clubActivity.getParams().get("beginTime").toString());
//      System.out.println(clubActivity.getParams().get("endTime").toString());
//      if(clubActivity.getParams().get("beginTime").toString().equals("")&&clubActivity.getParams().get("endTime").toString().equals("")){
//         clubActivity.setParams(null);
//      }
        List<ClubActivity> list = clubActivityService.selectClubActivityList(clubActivity);
        if (list.size() > 0) {
            List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
            List<Area> areaList = areaService.selectAreaAll();
            List<Club> clubList = clubService.selectClubAll();
            List<Type> typeList = typeService.selectTypeAll();
            List<Course> courseList = courseService.selectCourseAll();
            for (int i = 0; i < list.size(); i++) {
                for (SysDictData d : status) {
                    Integer aS = list.get(i).getStatus();
                    if (aS != null) {
                        if (d.getDictValue().equals(aS.toString())) {
                            list.get(i).setStatusName(d.getDictLabel());
                        }
                    }
                }
                for (Club club : clubList) {
                    if (club.getId() == list.get(i).getClubId()) {
                        list.get(i).setClubName(club.getTitle());
                    }
                }
                for (Area area : areaList) {
                    if (area.getId() == list.get(i).getAreaId()) {
                        list.get(i).setAreaName(area.getName());
                    }
                }
                for (Type type : typeList) {
                    if (type.getId() == list.get(i).getTypeId()) {
                        list.get(i).setTypeName(type.getName());
                    }
                }
                for (Course course : courseList) {
                    if (course.getId() == list.get(i).getCourseId()) {
                        list.get(i).setCourseName(course.getName());
                    }
                }
            }

        }
        ExcelUtil<ClubActivity> util = new ExcelUtil<ClubActivity>(ClubActivity.class);
        return util.exportExcel(list, "clubActivity");
    }


    /**
     * 导入俱乐部活动列表
     */
    @RequiresPermissions("shooting:clubActivity:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<ClubActivity> list =
                    ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), ClubActivity.class);
            for (ClubActivity clubActivity : list) {
                clubActivity = setClubId(clubActivity);
                clubActivityService.insertClubActivity(clubActivity);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增俱乐部活动
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Long clubId = getClubId();
        mmap.put("clubIdList",
                clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(3);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/add";
    }


    /**
     * 新增保存俱乐部活动
     */
    @RequiresPermissions("shooting:clubActivity:add")
    @Log(title = "俱乐部活动", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @Transactional
    public AjaxResult addSave(ClubActivity clubActivity,
                              @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) {

        if (StringUtils.isBlank(clubActivity.getTitle())) {
            return error("活动名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null, UploadTypeEnums.ClubActivity.getValue(), true);
                clubActivity.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        clubActivity.setCreateBy(ShiroUtils.getLoginName());
        clubActivity = setClubId(clubActivity);

        int result = clubActivityService.insertClubActivity(clubActivity);
        if (result > 0) {
            List<Schedule> schedules = clubActivity.getSchedules();

            for (int i = 0; i < schedules.size(); i++) {
                Schedule s = schedules.get(i);
                s.setFkId(clubActivity.getId());
                s.setType("clubActivity");

                try {
                    scheduleService.insertSchedule(s);
                } catch (Exception e) {
                    log.error("添加起始日期失败");
                    return error(e.getMessage());
                }
            }

            RegisterConfig registerConfig = clubActivity.getRegisterConfig();
            if (registerConfig != null) {
                registerConfig.setFkId(clubActivity.getId());
                registerConfig.setRegisterType("clubActivity");
                registerConfigService.insertRegisterConfig(registerConfig);
                if(registerConfig.getPersonalInfos()!=null){
                    registerConfig.getPersonalInfos().stream().forEach(pi -> {
                        pi.setRegisterConfigId(registerConfig.getId());
                        registerConfigPersonalInfoService.insertRegisterConfigPersonalInfo(pi);
                    });

                }
                if(registerConfig.getCustomizedInfos()!=null){
                    registerConfig.getCustomizedInfos().stream().forEach(ci -> {
                        ci.setRegisterConfigId(registerConfig.getId());
                        registerConfigCustomizedInfoService.insertRegisterConfigCustomizedInfo(ci);
                    });
                }

                if(registerConfig.getRoleInfos()!=null){
                    registerConfig.getRoleInfos().stream().forEach(ri -> {
                        ri.setRegisterConfigId(registerConfig.getId());
                        registerConfigRoleInfoService.insertRegisterConfigRoleInfo(ri);
                    });
                }
            }
        }

        return toAjax(result);
    }


    /**
     * 修改俱乐部活动
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ClubActivity clubActivity = clubActivityService.selectClubActivityByIdAssoc(id);

        List<Schedule> schedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "clubActivity"));
        Collections.sort(schedules, Comparator.comparing(Schedule::getStartDate));

        clubActivity.setSchedules(schedules);

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "clubActivity"));

        if (registerConfig != null) {
            registerConfig.setCustomizedInfos(registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()));
            List<RegisterConfigPersonalInfo> pInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            registerConfig.setPersonalInfos(pInfos);
            List<RegisterConfigRoleInfo> rInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            registerConfig.setRoleInfos(rInfos);

            clubActivity.setRegisterConfig(registerConfig);

            List<String> pInfoValues = pInfos.stream().map(info -> info.getInfoName()).collect(Collectors.toCollection(ArrayList::new));
            List<String> pInfoRequired = pInfos.stream().map(info -> info.getIsRequired() == 1 ? info.getInfoName() + "1" : info.getInfoName() + "0").collect(Collectors.toCollection(ArrayList::new));
            List<String> rInfoValues = rInfos.stream().map(info -> info.getRoleName()).collect(Collectors.toCollection(ArrayList::new));

            mmap.put("pInfoValues", pInfoValues);
            mmap.put("pInfoRequired", pInfoRequired);
            mmap.put("rInfoValues", rInfoValues);
        } else {
            mmap.put("pInfoValues", new ArrayList<>());
            mmap.put("pInfoRequired", new ArrayList<>());
            mmap.put("rInfoValues", new ArrayList<>());

            clubActivity.setRegisterConfig(new RegisterConfig());
        }

        mmap.put("clubActivity", clubActivity);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        Long clubId = getClubId();
        mmap.put("clubIdList",
                clubId != null ? clubService.selectClubById(clubId) : clubService.selectClubAll());
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(3);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/edit";
    }


    /**
     * 修改保存俱乐部活动
     */
    @RequiresPermissions("shooting:clubActivity:edit")
    @Log(title = "俱乐部活动", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @Transactional
    public AjaxResult editSave(ClubActivity clubActivity,
                               @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) {

        if (StringUtils.isBlank(clubActivity.getTitle())) {
            return error("活动名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null, UploadTypeEnums.ClubActivity.getValue(), true);
                clubActivity.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        clubActivity.setUpdateBy(ShiroUtils.getLoginName());
        clubActivity = setClubId(clubActivity);

        int result = clubActivityService.updateClubActivity(clubActivity);

        List<Schedule> currentSchedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(clubActivity.getId(), "clubActivity"));
        List<Schedule> newSchedules = clubActivity.getSchedules();

        currentSchedules.forEach(s -> {
            scheduleService.deleteScheduleById(s.getId());
        });
        if(!Objects.isNull(newSchedules)){
            for (Schedule s : newSchedules) {
                s.setFkId(clubActivity.getId());
                s.setType("clubActivity");
                scheduleService.insertSchedule(s);
            }
        }


        // update register config
        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(clubActivity.getId(), "clubActivity"));

        if (registerConfig != null || (registerConfig == null && clubActivity.getRegisterConfig() != null)) {
            if (registerConfig == null) {
                registerConfig = new RegisterConfig(clubActivity.getId(), "clubActivity");
                registerConfig.setIsUnauthAllowed(clubActivity.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.insertRegisterConfig(registerConfig);
            }

            registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId()).forEach(i -> registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoById(i.getId()));
            registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoById(i.getId()));
            registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigRoleInfoService.deleteRegisterConfigRoleInfoById(i.getId()));

            final RegisterConfig finalRegisterConfig = registerConfig;
            if( clubActivity.getRegisterConfig()!=null&&clubActivity.getRegisterConfig().getPersonalInfos()!=null){
                clubActivity.getRegisterConfig().getPersonalInfos().stream().forEach(pi -> {
                    pi.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigPersonalInfoService.insertRegisterConfigPersonalInfo(pi);
                });
            }
            if( clubActivity.getRegisterConfig()!=null&&clubActivity.getRegisterConfig().getCustomizedInfos()!=null){
                clubActivity.getRegisterConfig().getCustomizedInfos().stream().forEach(ci -> {
                    ci.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigCustomizedInfoService.insertRegisterConfigCustomizedInfo(ci);
                });
            }
            if( clubActivity.getRegisterConfig()!=null&&clubActivity.getRegisterConfig().getRoleInfos()!=null){
                clubActivity.getRegisterConfig().getRoleInfos().stream().forEach(ri -> {
                    ri.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigRoleInfoService.insertRegisterConfigRoleInfo(ri);
                });

            }
            if (registerConfig.getIsUnauthAllowed() != clubActivity.getRegisterConfig().getIsUnauthAllowed()) {
                registerConfig.setIsUnauthAllowed(clubActivity.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.updateRegisterConfig(registerConfig);
            }
        }

        return toAjax(result);
    }


    /**
     * 删除俱乐部活动
     */
    @RequiresPermissions("shooting:clubActivity:remove")
    @Log(title = "俱乐部活动", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    @Transactional
    public AjaxResult remove(String ids) {

        for (String id : ids.split(",")) {
            Iterator<Schedule> iterator = scheduleService.selectScheduleByFkIdAndType(new Schedule(Long.parseLong(id), "clubActivity")).iterator();
            while (iterator.hasNext()) {
                scheduleService.deleteScheduleById(iterator.next().getId());
            }

            ClubActivity clubActivity = clubActivityService.selectClubActivityById(Long.parseLong(id));
            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(clubActivity.getId(), "clubActivity"));

            if (registerConfig != null) {
                Long configId = registerConfig.getId();

                registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoByConfigId(configId);
                registerConfigRoleInfoService.deleteRegisterConfigRoleInfoByConfigId(configId);
                registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoByConfigId(configId);
                registerConfigService.deleteRegisterConfigById(configId);
            }
        }

        return toAjax(clubActivityService.deleteClubActivityByIds(ids));
    }


    /**
     * 查询俱乐部活动分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody ClubActivity clubActivity) {
        startPage(clubActivity.getPd());
        clubActivity = setClubId(clubActivity);
        List<ClubActivity> list = clubActivityService.selectClubActivityListAssoc(clubActivity);
        return getDataTable(list);
    }

}
