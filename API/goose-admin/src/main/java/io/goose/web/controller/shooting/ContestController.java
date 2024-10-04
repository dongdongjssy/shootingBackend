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
import org.springframework.scheduling.annotation.Async;
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
import io.goose.framework.web.base.BaseController;
import io.goose.web.controller.service.AsyncMessageService;
import io.goose.web.controller.service.JpushService;


/**
 * 赛事 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@Controller
@RequestMapping("/shooting/contest")
public class ContestController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ContestController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/contest";

    @Autowired
    private IContestService contestService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private JpushService pushService;

    @Autowired
    private IContestLevelCoeffService contestLevelCoeffService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private IMessageService messageService;

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
    
    @Autowired
    private AsyncMessageService asyncMessageService;

    @RequiresPermissions("shooting:contest:view")
    @GetMapping()
    public String contest(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("levelIdList", contestLevelCoeffService.selectContestLevelCoeffAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(2);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/contest";
    }


    /**
     * 查询赛事列表
     */
    @RequiresPermissions("shooting:contest:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Contest contest) {
        startPage();
        List<Contest> list = contestService.selectContestListAssoc(contest);
        return getDataTable(list);
    }


    /**
     * 导出赛事列表
     */
    @RequiresPermissions("shooting:contest:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Contest contest) {
        List<Contest> list = contestService.selectContestList(contest);
        if (list.size() > 0) {
            List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
            List<ContestLevelCoeff> contestLevelCoeffList = contestLevelCoeffService.selectContestLevelCoeffAll();
            List<Type> typeList = typeService.selectTypeAll();
            for (int i = 0; i < list.size(); i++) {
                for (SysDictData d : status) {
                    Integer aS = list.get(i).getStatus();
                    if (aS != null) {
                        if (d.getDictValue().equals(aS.toString())) {
                            list.get(i).setStatusName(d.getDictLabel());
                        }
                    }
                }
                for (ContestLevelCoeff contestLevelCoeff : contestLevelCoeffList) {
                    if (list.get(i).getLevelId() != null) {
                        if (contestLevelCoeff.getId() == list.get(i).getLevelId()) {
                            list.get(i).setLevelName(contestLevelCoeff.getLevelName());
                        }
                    }
                }
                for (Type type : typeList) {
                    if (list.get(i).getTypeId() != null) {
                        if (type.getId() == list.get(i).getTypeId()) {
                            list.get(i).setTypeName(type.getName());
                        }
                    }
                }
            }
        }
        ExcelUtil<Contest> util = new ExcelUtil<Contest>(Contest.class);
        return util.exportExcel(list, "contest");
    }


    /**
     * 导入赛事列表
     */
    @RequiresPermissions("shooting:contest:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Contest> list =
                    ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Contest.class);
            for (Contest obj : list) {
                contestService.insertContest(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }


    /**
     * 新增赛事
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("levelIdList", contestLevelCoeffService.selectContestLevelCoeffAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(2);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/add";
    }


    /**
     * 新增保存赛事
     * @throws InterruptedException 
     */
    @RequiresPermissions("shooting:contest:add")
    @Log(title = "赛事", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Contest contest,
                              @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) throws InterruptedException {

        if (StringUtils.isBlank(contest.getTitle())) {
            return error("赛事名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null, UploadTypeEnums.Contest.getValue(), true);
                contest.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        contest.setCreateBy(ShiroUtils.getLoginName());

        int success = contestService.insertContest(contest);
        if (success > 0) {
            List<Schedule> schedules = contest.getSchedules();

            for (int i = 0; i < schedules.size(); i++) {
                Schedule s = schedules.get(i);
                s.setFkId(contest.getId());
                s.setType("contest");

                try {
                    scheduleService.insertSchedule(s);
                } catch (Exception e) {
                    log.error("添加起始日期失败");
                    return error(e.getMessage());
                }
            }

            RegisterConfig registerConfig = contest.getRegisterConfig();

            if (registerConfig != null) {
                registerConfig.setFkId(contest.getId());
                registerConfig.setRegisterType("contest");
                registerConfigService.insertRegisterConfig(registerConfig);
                if(registerConfig.getPersonalInfos()!=null) {
                    registerConfig.getPersonalInfos().stream().forEach(pi -> {
                        pi.setRegisterConfigId(registerConfig.getId());
                        registerConfigPersonalInfoService.insertRegisterConfigPersonalInfo(pi);
                    });
                }
                if(registerConfig.getCustomizedInfos()!=null) {
                    registerConfig.getCustomizedInfos().stream().forEach(ci -> {
                        ci.setRegisterConfigId(registerConfig.getId());
                        registerConfigCustomizedInfoService.insertRegisterConfigCustomizedInfo(ci);
                    });
                }
                if(registerConfig.getRoleInfos()!=null) {
                    registerConfig.getRoleInfos().stream().forEach(ri -> {
                        ri.setRegisterConfigId(registerConfig.getId());
                        registerConfigRoleInfoService.insertRegisterConfigRoleInfo(ri);
                    });
                }
            }
            
            asyncMessageService.task(contest);
        }
        return toAjax(success);
    }
    

    /**
     * 修改赛事
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Contest contest = contestService.selectContestByIdAssoc(id);

        List<Schedule> schedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "contest"));
        Collections.sort(schedules, Comparator.comparing(Schedule::getStartDate));

        contest.setSchedules(schedules);

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "contest"));

        if (registerConfig != null) {
            registerConfig.setCustomizedInfos(registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()));
            List<RegisterConfigPersonalInfo> pInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            registerConfig.setPersonalInfos(pInfos);
            List<RegisterConfigRoleInfo> rInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            registerConfig.setRoleInfos(rInfos);

            contest.setRegisterConfig(registerConfig);

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
            contest.setRegisterConfig(new RegisterConfig());
        }

        mmap.put("contest", contest);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("levelIdList", contestLevelCoeffService.selectContestLevelCoeffAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(2);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/edit";
    }


    /**
     * 修改保存赛事
     */
    @RequiresPermissions("shooting:contest:edit")
    @Log(title = "赛事", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @Transactional
    public AjaxResult editSave(Contest contest,
                               @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) {

        if (StringUtils.isBlank(contest.getTitle())) {
            return error("赛事名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null, UploadTypeEnums.Contest.getValue(), true);
                contest.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        contest.setUpdateBy(ShiroUtils.getLoginName());

        int result = contestService.updateContest(contest);

        List<Schedule> currentSchedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(contest.getId(), "contest"));
        List<Schedule> newSchedules = contest.getSchedules();

        currentSchedules.forEach(s -> {
            scheduleService.deleteScheduleById(s.getId());
        });

        newSchedules.forEach(s -> {
            s.setFkId(contest.getId());
            s.setType("contest");
            scheduleService.insertSchedule(s);
        });

        // update register config
        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(contest.getId(), "contest"));

        if (registerConfig != null || (registerConfig == null && contest.getRegisterConfig() != null)) {
            if (registerConfig == null) {
                registerConfig = new RegisterConfig(contest.getId(), "contest");
                registerConfig.setIsUnauthAllowed(contest.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.insertRegisterConfig(registerConfig);
            }

            registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId()).forEach(i -> registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoById(i.getId()));
            registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoById(i.getId()));
            registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigRoleInfoService.deleteRegisterConfigRoleInfoById(i.getId()));

            final RegisterConfig finalRegisterConfig = registerConfig;
            if(contest.getRegisterConfig()!=null&&contest.getRegisterConfig().getPersonalInfos()!=null) {
                contest.getRegisterConfig().getPersonalInfos().stream().forEach(pi -> {
                    pi.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigPersonalInfoService.insertRegisterConfigPersonalInfo(pi);
                });
            }
            if(contest.getRegisterConfig()!=null&&contest.getRegisterConfig().getCustomizedInfos()!=null) {
                contest.getRegisterConfig().getCustomizedInfos().stream().forEach(ci -> {
                    ci.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigCustomizedInfoService.insertRegisterConfigCustomizedInfo(ci);
                });
            }
            if(contest.getRegisterConfig()!=null&&contest.getRegisterConfig().getRoleInfos()!=null) {
                contest.getRegisterConfig().getRoleInfos().stream().forEach(ri -> {
                    ri.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigRoleInfoService.insertRegisterConfigRoleInfo(ri);
                });
            }
            if (registerConfig.getIsUnauthAllowed() != contest.getRegisterConfig().getIsUnauthAllowed()) {
                registerConfig.setIsUnauthAllowed(contest.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.updateRegisterConfig(registerConfig);
            }
        }

        return toAjax(result);
    }


    /**
     * 删除赛事
     */
    @RequiresPermissions("shooting:contest:remove")
    @Log(title = "赛事", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    @Transactional
    public AjaxResult remove(String ids) {

        for (String id : ids.split(",")) {
            Iterator<Schedule> iterator = scheduleService.selectScheduleByFkIdAndType(new Schedule(Long.parseLong(id), "contest")).iterator();
            while (iterator.hasNext()) {
                scheduleService.deleteScheduleById(iterator.next().getId());
            }

            Contest contest = contestService.selectContestById(Long.parseLong(id));
            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(contest.getId(), "contest"));

            if (registerConfig != null) {
                Long configId = registerConfig.getId();

                registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoByConfigId(configId);
                registerConfigRoleInfoService.deleteRegisterConfigRoleInfoByConfigId(configId);
                registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoByConfigId(configId);
                registerConfigService.deleteRegisterConfigById(configId);
            }
        }

        int result = contestService.deleteContestByIds(ids);
        if (result > 0) {
            return toAjax(result);
        } else
            return error("删除失败，请重试或联系管理员!");
    }


    /**
     * 查询赛事分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Contest contest) {
        startPage(contest.getPd());

        List<Contest> list = contestService.selectContestListAssoc(contest);
        return getDataTable(list);
    }

}
