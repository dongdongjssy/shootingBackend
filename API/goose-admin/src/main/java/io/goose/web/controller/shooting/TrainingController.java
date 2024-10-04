package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
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
import io.goose.framework.web.base.BaseController;
import io.goose.web.controller.service.JpushService;

/**
 * 培训 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@Controller
@RequestMapping("/shooting/training")
public class TrainingController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);

    @Autowired
    private Global global;

    private String prefix = "shooting/training";

    @Autowired
    private ITrainingService trainingService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private JpushService pushService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private FileUploadDownloadOSSService fileUploadDownloadService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPaymentCodeService paymentCodeService;

    @Autowired
    private IRegisterConfigService registerConfigService;

    @Autowired
    private IRegisterConfigCustomizedInfoService registerConfigCustomizedInfoService;

    @Autowired
    private IRegisterConfigPersonalInfoService registerConfigPersonalInfoService;

    @Autowired
    private IRegisterConfigRoleInfoService registerConfigRoleInfoService;

    @RequiresPermissions("shooting:training:view")
    @GetMapping()
    public String training(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(1);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/training";
    }

    /**
     * 查询培训列表
     */
    @RequiresPermissions("shooting:training:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Training training) {
        startPage();
        if(training.getDraft()==null){
            training.setDraft(0);
        }
        List<Training> list = trainingService.selectTrainingListAssoc(training);
        return getDataTable(list);
    }

    /**
     * 导出培训列表
     */
    @RequiresPermissions("shooting:training:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Training training) {
        List<Training> list = trainingService.selectTrainingList(training);
        ExcelUtil<Training> util = new ExcelUtil<Training>(Training.class);
        return util.exportExcel(list, "training");
    }

    /**
     * 导入培训列表
     */
    @RequiresPermissions("shooting:training:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Training> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Training.class);
            for (Training obj : list) {
                trainingService.insertTraining(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }

    /**
     * 新增培训
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(1);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));
        return prefix + "/add";
    }

    /**
     * 新增保存培训
     */
    @RequiresPermissions("shooting:training:add")
    @Log(title = "培训", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Training training,
                              @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) {

        if (StringUtils.isBlank(training.getTitle())) {
            return error("培训名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null,
                        UploadTypeEnums.Training.getValue(), true);
                training.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }

        training.setCreateBy(ShiroUtils.getLoginName());

        int success = trainingService.insertTraining(training);
        if (success > 0) {
            List<Schedule> schedules = training.getSchedules();

            for (int i = 0; i < schedules.size(); i++) {
                Schedule s = schedules.get(i);
                s.setFkId(training.getId());
                s.setType("training");

                try {
                    scheduleService.insertSchedule(s);
                } catch (Exception e) {
                    log.error("添加起始日期失败");
                    return error(e.getMessage());
                }
            }

            RegisterConfig registerConfig = training.getRegisterConfig();

            if (registerConfig != null) {
                registerConfig.setFkId(training.getId());
                registerConfig.setRegisterType("training");
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


            Message message = new Message();
            message.setCreateBy(ShiroUtils.getLoginName());
            message.setTitle("新培训发布");
            message.setContent("有新的培训发布,快去报名参加吧");
            message.setType(1);
            success = messageService.insertMessage(message);
            if (success > 0) {
                List<ClientUser> list = clientUserService.selectClientUserAll();
                for (ClientUser uu : list) {
                    messageService.insertMessageUserInAsync(message.getId(), uu.getId(),
                            message.getCreateBy());
                    // 推送
                    pushService.jpush(message.getTitle(), message.getContent(), uu.getId().toString(),
                            "1", "1", String.valueOf(training.getId()));
                }
            } else {
                throw new RuntimeException("新增失败");
            }
        }
        return toAjax(success);
    }

    /**
     * 修改培训
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Training training = trainingService.selectTrainingByIdAssoc(id);
        List<Schedule> schedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "training"));
        Collections.sort(schedules, Comparator.comparing(Schedule::getStartDate));

        training.setSchedules(schedules);

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "training"));

        if (registerConfig != null) {
            registerConfig.setCustomizedInfos(registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()));
            List<RegisterConfigPersonalInfo> pInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            registerConfig.setPersonalInfos(pInfos);
            List<RegisterConfigRoleInfo> rInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            registerConfig.setRoleInfos(rInfos);

            training.setRegisterConfig(registerConfig);

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

            training.setRegisterConfig(new RegisterConfig());
        }

        mmap.put("training", training);
        mmap.put("imageUrlPrefix", getImageUrlPrefix());
        mmap.put("areaIdList", areaService.selectAreaAll());
        mmap.put("courseIdList", courseService.selectCourseAll());
        mmap.put("typeIdList", typeService.selectTypeAll());
        mmap.put("roleList", roleService.selectRoleAll());
        PaymentCode paymentCode=new PaymentCode();
        paymentCode.setType(1);
        mmap.put("paymentList", paymentCodeService.selectPaymentCodeList(paymentCode));

        return prefix + "/edit";
    }

    /**
     * 修改保存培训
     */
    @RequiresPermissions("shooting:training:edit")
    @Log(title = "培训", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @Transactional
    public AjaxResult editSave(Training training,
                               @RequestParam(value = "imageUrlFile", required = false) MultipartFile imageUrlFile) {

        if (StringUtils.isBlank(training.getTitle())) {
            return error("培训名称不能为空。");
        }

        try {
            if (imageUrlFile != null && !imageUrlFile.isEmpty()) {
                String filePath = fileUploadDownloadService.upload(imageUrlFile, null,
                        UploadTypeEnums.Training.getValue(), true);
                training.setImageUrl(filePath);
            }
        } catch (Exception e) {
            log.error("图像上传失败！", e);
            return error(e.getMessage());
        }
        training.setUpdateBy(ShiroUtils.getLoginName());

        int result = trainingService.updateTraining(training);

        // update schedules
        List<Schedule> currentSchedules = scheduleService.selectScheduleByFkIdAndType(new Schedule(training.getId(), "training"));
        List<Schedule> newSchedules = training.getSchedules();

        currentSchedules.forEach(s -> {
            scheduleService.deleteScheduleById(s.getId());
        });

        newSchedules.forEach(s -> {
            s.setFkId(training.getId());
            s.setType("training");
            scheduleService.insertSchedule(s);
        });

        // update register config
        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(training.getId(), "training"));

        if (registerConfig != null || (registerConfig == null && training.getRegisterConfig() != null)) {
            if(registerConfig == null) {
                registerConfig = new RegisterConfig(training.getId(), "training");
                registerConfig.setIsUnauthAllowed(training.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.insertRegisterConfig(registerConfig);
            }

            registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId()).forEach(i -> registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoById(i.getId()));
            registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoById(i.getId()));
            registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId()).forEach(i -> registerConfigRoleInfoService.deleteRegisterConfigRoleInfoById(i.getId()));

            final RegisterConfig finalRegisterConfig = registerConfig;
            if(training.getRegisterConfig()!=null&&training.getRegisterConfig().getPersonalInfos()!=null) {
                training.getRegisterConfig().getPersonalInfos().stream().forEach(pi -> {
                    pi.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigPersonalInfoService.insertRegisterConfigPersonalInfo(pi);
                });
            }
            if(training.getRegisterConfig()!=null&&training.getRegisterConfig().getCustomizedInfos()!=null) {
                training.getRegisterConfig().getCustomizedInfos().stream().forEach(ci -> {
                    ci.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigCustomizedInfoService.insertRegisterConfigCustomizedInfo(ci);
                });
            }
            if(training.getRegisterConfig()!=null&&training.getRegisterConfig().getRoleInfos()!=null) {
                training.getRegisterConfig().getRoleInfos().stream().forEach(ri -> {
                    ri.setRegisterConfigId(finalRegisterConfig.getId());
                    registerConfigRoleInfoService.insertRegisterConfigRoleInfo(ri);
                });
            }
            if (registerConfig.getIsUnauthAllowed() != training.getRegisterConfig().getIsUnauthAllowed()) {
                registerConfig.setIsUnauthAllowed(training.getRegisterConfig().getIsUnauthAllowed());
                registerConfigService.updateRegisterConfig(registerConfig);
            }
        }


        return toAjax(result);
    }

    /**
     * 删除培训
     */
    @RequiresPermissions("shooting:training:remove")
    @Log(title = "培训", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    @Transactional
    public AjaxResult remove(String ids) {

        for (String id : ids.split(",")) {
            Iterator<Schedule> iterator = scheduleService.selectScheduleByFkIdAndType(new Schedule(Long.parseLong(id), "training")).iterator();
            while (iterator.hasNext()) {
                scheduleService.deleteScheduleById(iterator.next().getId());
            }

            Training training = trainingService.selectTrainingById(Long.parseLong(id));
            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(training.getId(), "training"));

            if (registerConfig != null) {
                Long configId = registerConfig.getId();

                registerConfigPersonalInfoService.deleteRegisterConfigPersonalInfoByConfigId(configId);
                registerConfigRoleInfoService.deleteRegisterConfigRoleInfoByConfigId(configId);
                registerConfigCustomizedInfoService.deleteRegisterConfigCustomizedInfoByConfigId(configId);
                registerConfigService.deleteRegisterConfigById(configId);
            }
        }

        int result = trainingService.deleteTrainingByIds(ids);

        if (result > 0) {
            return toAjax(result);
        } else
            return error("删除失败，请重试或联系管理员!");
    }

    /**
     * 查询培训分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Training training) {
        startPage(training.getPd());

        List<Training> list = trainingService.selectTrainingListAssoc(training);
        return getDataTable(list);
    }

}
