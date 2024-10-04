package io.goose.api.controller.shooting;

import java.util.ArrayList;
import java.util.List;

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
 * 培训 信息操作处理
 *
 * @author goose
 * @date 2020-05-03
 */
@RestController
@RequestMapping("/shooting/training")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "培训 ", description = "培训")
public class TrainingRestController extends BaseController {

    @Autowired
    private ITrainingService trainingService;


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
     * 查询培训列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询培训列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "training", value = "培训",
            required = false, dataType = "Training")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody Training training) {
        startPage();

        List<Training> list = trainingService.selectTrainingList(training);

        list.forEach(t -> {
            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(t.getId(), "training"));
            registerConfig.setRoleInfos(registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId()));
            registerConfig.setPersonalInfos(registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId()));
            registerConfig.setCustomizedInfos(registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId()));

            t.setRegisterConfig(registerConfig);
        });

        TableDataInfo ret = getDataTable(list);
        PageDomain pd = training.getPd();
        if (ret.getTotal() <= (pd.getPageNum() - 1) * pd.getPageSize())
            return getDataTable(new ArrayList<Training>());
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
    public Training getById(@PathVariable("id") Long id) {

        Training t = trainingService.selectTrainingById(id);
        if (t.getContent() != null) {
            t.setShortContent(StringUtils.Html2Text(t.getContent()));
        }

        List<Schedule> trainingScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(id, "training"));
        if (trainingScheduels != null) {
            t.setSchedules(trainingScheduels);
        }

        RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(id, "training"));

        if(registerConfig != null) {
            List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
            if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

            List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
            if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

            List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
            if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

            t.setRegisterConfig(registerConfig);
        }

        return t;
    }

    /**
     * 导出培训列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出培训列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "training", value = "培训", required = false,
     * dataType = "Training") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(Training training) { List<Training> list =
     * trainingService.selectTrainingList(training); ExcelUtil<Training> util = new
     * ExcelUtil<Training>(Training.class); return util.exportExcel(list, "training"); }
     */

    /**
     * 导入培训列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入培训列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "培训文件", required = false, dataType
     * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<Training> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Training.class);
     * for(Training obj : list) { trainingService.insertTraining(obj); } } catch(ExcelUtilException |
     * IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存培训
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存培训")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "training", value = "培训",
            required = true, dataType = "Training")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Training training) {
        return toAjax(trainingService.insertTraining(training));
    }


    /**
     * 修改保存培训
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存培训")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "training", value = "培训",
            required = true, dataType = "Training")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Training training) {
        return toAjax(trainingService.updateTraining(training));
    }


    /**
     * 删除培训
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除培训")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(trainingService.deleteTrainingByIds(ids));
    }


    /**
     * 根据id删除培训
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除培训")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(trainingService.deleteTrainingById(id));
    }


    /**
     * 查询培训分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询培训分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "training", value = "培训",
            required = true, dataType = "Training")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Training training) {
        startPage(training.getPd());
        List<Training> list = trainingService.selectTrainingList(training);
        Register register=new Register();
        register.setClientUserId(training.getClientUserId());
        register.setFkTable(1);
        List<Register> registerList=registerService.selectRegisterList(register);
        for (Training t : list) {
            registerList.forEach(r->{
                if(t.getId()==r.getFkId()){
                    t.setReleaseStatus(r.getStatus());
                }
            });
            if (t.getContent() != null) {
                t.setShortContent(StringUtils.Html2Text(t.getContent()));
            }

            List<Schedule> trainingScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(t.getId(), "training"));
            if (trainingScheduels != null) {
                t.setSchedules(trainingScheduels);
            }

            RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(t.getId(), "training"));

            if(registerConfig != null) {
                List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
                if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

                List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
                if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

                List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
                if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

                t.setRegisterConfig(registerConfig);
            }
        }
        TableDataInfo ret = getDataTable(list);
        PageDomain pd = training.getPd();
        if (ret.getTotal() <= (pd.getPageNum() - 1) * pd.getPageSize())
            return getDataTable(new ArrayList<Training>());
        return ret;
    }

}
