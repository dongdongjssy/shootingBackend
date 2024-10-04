package io.goose.web.controller.shooting;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.goose.framework.web.base.ClubBaseController;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.goose.system.domain.SysDictData;
import io.goose.system.service.ISysDictDataService;
import io.goose.web.controller.common.ExportExcel;
import io.goose.web.controller.service.JpushService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import io.goose.common.enums.BusinessType;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.service.impl.ext.RegisterServiceImplExt;

import javax.servlet.http.HttpServletResponse;


/**
 * 报名 信息操作处理
 *
 * @author goose
 * @date 2020-05-19
 */
@Controller
@RequestMapping("/shooting/register")
public class RegisterController extends ClubBaseController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private String prefix = "shooting/register";
    private static final String SUFFIX = ".xlsx";
    private static final String TITLE= "Title";
    private static final String CONTENT ="Content-disposition";
    private static final String CONCAT="attachment;filename=";
    private static final String EXCEL ="application/vnd.ms-excel";
    @Autowired
    private RegisterServiceImplExt registerService;

    @Autowired
    private IRegisterFormItemService registerFormItemService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private IContestService contestService;
    @Autowired
    private ITrainingService trainingService;
    @Autowired
    private IClubActivityService clubActivityService;
    
    @Autowired
    private JpushService pushService;
    
    @Autowired
    private IMessageService messageService;

    @RequiresPermissions("shooting:register:view")
    @GetMapping()
    public String register(ModelMap mmap) {
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        mmap.put("contestList", contestService.selectContestListAssoc(new Contest()));
        mmap.put("trainingList", trainingService.selectTrainingListAssoc(new Training()));
        mmap.put("clubActivityList", clubActivityService.selectClubActivityListAssoc(new ClubActivity()));

        return prefix + "/register";
    }


    @PostMapping("/getListById")
    @ResponseBody
    public Map<String,List> getListById() {
        Map<String, List> map=new HashMap<>(16);
        map.put("contestList", contestService.selectContestListAssoc(new Contest()));
        map.put("trainingList", trainingService.selectTrainingListAssoc(new Training()));
        map.put("clubActivityList", clubActivityService.selectClubActivityListAssoc(new ClubActivity()));
        return  map;
    }


    /**
     * 查询报名列表
     */
    @RequiresPermissions("shooting:register:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Register register) {
        startPage();
        register = setClubId(register);
        List<Register> list = registerService.selectRegisterListAssoc(register);

        Long clubId = getClubId();

        if (clubId != null || (clubId != null && clubId > 0)) {
            list = list.stream().filter(reg -> {
                if(reg.getFkTable() != 3){ return false;}

                ClubActivity clubActivity = clubActivityService.selectClubActivityById(reg.getFkId());
                if (clubActivity.getClubId() == clubId){ return true;}
                else {return false;}
            }).collect(Collectors.toList());
        }

        return getDataTable(list);
    }


    /**
     * 导出报名列表
     */
    @RequiresPermissions("shooting:register:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Register register) {
        List<Register> list = registerService.selectRegisterList(register);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFkTable() != null && list.get(i).getFkTable() == 1) {
                    Training training = trainingService.selectTrainingById(list.get(i).getFkId());
                    if (training != null && training.getTitle() != null) {
                        list.get(i).setActName(training.getTitle());
                    }
                }
                if (list.get(i).getFkTable() != null && list.get(i).getFkTable() == 2) {
                    Contest contest = contestService.selectContestById(list.get(i).getFkId());
                    if (contest != null && contest.getTitle() != null) {
                        list.get(i).setActName(contest.getTitle());
                    }
                }
                if (list.get(i).getFkTable() != null && list.get(i).getFkTable() == 3) {
                    ClubActivity clubActivity = clubActivityService.selectClubActivityById(list.get(i).getFkId());
                    if (clubActivity != null && clubActivity.getTitle() != null) {
                        list.get(i).setActName(clubActivity.getTitle());
                    }
                }
                List<SysDictData> status = dictDataService.selectDictDataByType("sys_common_status");
                List<ClientUser> clientUserList = clientUserService.selectClientUserAll();
                List<SysDictData> typeName = dictDataService.selectDictDataByType("shooting_register_fk_table");

                for (SysDictData d : status) {
                    Integer aS = list.get(i).getStatus();
                    if (aS != null) {
                        if (d.getDictValue().equals(aS.toString())) {
                            list.get(i).setStatusName(d.getDictLabel());
                        }
                    }
                }
                for (SysDictData d : typeName) {
                    Integer typeId = list.get(i).getFkTable();
                    if (typeId != null) {
                        if (d.getDictValue().equals(typeId.toString())) {
                            list.get(i).setTypeName(d.getDictLabel());
                        }
                    }
                }
                for (ClientUser clientUser : clientUserList) {
                    if (clientUser.getId().equals(list.get(i).getClientUser().getId())) {
                        list.get(i).setClientUserName(clientUser.getNickname());
                    }
                }
            }

        }
        ExcelUtil<Register> util = new ExcelUtil<Register>(Register.class);
        return util.exportExcel(list, "register");
    }


    /**
     * 导入报名列表
     */
    @RequiresPermissions("shooting:register:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult
    importExcel(@RequestParam(value = "file", required = false) MultipartFile excl) {
        try {
            List<Register> list =
                    ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Register.class);
            for (Register obj : list) {
                registerService.insertRegister(obj);
            }
        } catch (ExcelUtilException | IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }

    @RequiresPermissions("shooting:register:list")
    @GetMapping("exportRegister/{fkId}/{fkTable}")
    public void exportRegister(@PathVariable("fkId") Long fkId, @PathVariable("fkTable") Integer fkTable, HttpServletResponse response) {
        Register register=new Register();
        register.setFkTable(fkTable);
        register.setFkId(fkId);
        List<Register> registerList = registerService.selectRegisterList(register);
        List<Map<String, Object>> list = new ArrayList<>(16);
        List<Map<String, Object>> list1 = new ArrayList<>(16);
        if(registerList.isEmpty()){
            return;
        }
        registerList.forEach(register1 -> {
            if (register1.getFkTable() != null && register1.getFkTable() == 1) {
                Training training = trainingService.selectTrainingById(register1.getFkId());
                if (training != null && training.getTitle() != null) {
                    register1.setActName(training.getTitle());
                }
            }
            if (register1.getFkTable() != null && register1.getFkTable() == 2) {
                Contest contest = contestService.selectContestById(register1.getFkId());
                if (contest != null && contest.getTitle() != null) {
                    register1.setActName(contest.getTitle());
                }
            }
            if (register1.getFkTable() != null && register1.getFkTable() == 3) {
                ClubActivity clubActivity = clubActivityService.selectClubActivityById(register1.getFkId());
                if (clubActivity != null && clubActivity.getTitle() != null) {
                    register1.setActName(clubActivity.getTitle());
                }
            }
            List<SysDictData> releaseStatus = dictDataService.selectDictDataByType("release_status");
            List<SysDictData> clubReleaseStatus = dictDataService.selectDictDataByType("club_release_status");

            List<ClientUser> clientUserList = clientUserService.selectClientUserAll();
            List<SysDictData> typeName = dictDataService.selectDictDataByType("shooting_register_fk_table");

            if(register1.getStatus()!=null){
                if (register1.getFkTable() != null && register1.getFkTable() != 3) {
                    for (SysDictData d : releaseStatus) {
                        if (d.getDictValue().equals(register1.getStatus().toString())) {
                            register1.setStatusName(d.getDictLabel());
                        }
                    }
                }else {
                    for (SysDictData d : clubReleaseStatus) {
                        if (d.getDictValue().equals(register1.getStatus().toString())) {
                            register1.setStatusName(d.getDictLabel());
                        }
                    }
                }
            }




            for (SysDictData d : typeName) {
                Integer typeId = register1.getFkTable();
                if (typeId != null) {
                    if (d.getDictValue().equals(typeId.toString())) {
                        register1.setTypeName(d.getDictLabel());
                    }
                }
            }
            for (ClientUser clientUser : clientUserList) {
                if (clientUser.getId().equals(register1.getClientUser().getId())) {
                    register1.setClientUserName(clientUser.getNickname());
                }
            }
            RegisterFormItem registerFormItem=new RegisterFormItem();
            registerFormItem.setRegisterId(register1.getId());
            List<RegisterFormItem> registerFormItemList=registerFormItemService.selectRegisterFormItemList(registerFormItem);
            Map<String,Object> map=new HashMap<>();
            map.put("活动名称",register1.getActName());
            map.put("活动类型",register1.getTypeName());
            map.put("射手编号",register1.getClientUser().getMemberId());
            map.put("时间段",register1.getSchedules());
            map.put("报名者",register1.getClientUserName());
            map.put("状态",register1.getStatusName());
            Map<String,Object> map1=new HashMap<>();
            map1.put("actName",register1.getActName());
            map1.put("typeName",register1.getTypeName());
            map1.put("memberId",register1.getClientUser().getMemberId());
            map1.put("schedules",register1.getSchedules());
            map1.put("clientUserName",register1.getClientUserName());
            map1.put("statusName",register1.getStatusName());
            registerFormItemList.forEach(i -> {
                i.setCategory(i.getName());
                if(i.getName().equals("realName")){
                    i.setName("真实姓名");
                }
                if(i.getName().equals("sex")){
                    i.setName("性别");
                    if(i.getValue().equals("1")){
                        i.setValue("男");
                    }else {
                        i.setValue("女");
                    }
                }
                if(i.getName().equals("email")){
                    i.setName("电子邮箱");
                }
                if(i.getName().equals("phone")){
                    i.setName("联系电话");
                }
                if(i.getName().equals("city")){
                    i.setName("所在城市");
                }
                if(i.getName().equals("address")){
                    i.setName("地址");
                }
                if(i.getName().equals("idNumber")){
                    i.setName("身份证号");
                }
                if(i.getName().equals("englishName")){
                    i.setName("英文名");
                }
                if(i.getName().equals("memberId")){
                    i.setName("射手编号");
                }
                if(i.getName().equals("certExpireDate")){
                    i.setName("认证有效期");
                }
                if(i.getName().equals("graduateDate")){
                    i.setName("结业日期");
                }
                if(i.getRemark()!=null&&!i.getRemark().equals("")){
                    i.setValue(i.getValue()+"("+i.getRemark()+")");
                }
                map.put(i.getName(),i.getValue());
                map1.put(i.getCategory(),i.getValue());
            });
            list.add(map);
            list1.add(map);
        });

        export(response,list,list.get(0).keySet().toArray(new String[0]), list1.get(0).keySet().toArray(new String[0]));
    }

    /**
     * 新增报名
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        return prefix + "/add";
    }


    /**
     * 新增保存报名
     */
    @RequiresPermissions("shooting:register:add")
    @Log(title = "报名", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Register register) {

        register.setCreateBy(ShiroUtils.getLoginName());

        return toAjax(registerService.insertRegister(register));
    }


    /**
     * 修改报名
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Register register = registerService.selectRegisterByIdAssoc(id);
        if(!register.getRegisterFormItems().isEmpty()){
            register.getRegisterFormItems().forEach(i->{
                if(i.getName().equals("realName")){
                    i.setName("真实姓名");
                }
                if(i.getName().equals("sex")){
                    i.setName("性别");
                    if(i.getValue().equals("1")){
                        i.setValue("男");
                    }else {
                        i.setValue("女");
                    }
                }
                if(i.getName().equals("email")){
                    i.setName("电子邮箱");
                }
                if(i.getName().equals("phone")){
                    i.setName("联系电话");
                }
                if(i.getName().equals("city")){
                    i.setName("所在城市");
                }
                if(i.getName().equals("address")){
                    i.setName("地址");
                }
                if(i.getName().equals("idNumber")){
                    i.setName("身份证号");
                }
                if(i.getName().equals("englishName")){
                    i.setName("英文名");
                }
                if(i.getName().equals("memberId")){
                    i.setName("射手编号");
                }
                if(i.getName().equals("certExpireDate")){
                    i.setName("认证有效期");
                }
                if(i.getName().equals("graduateDate")){
                    i.setName("结业日期");
                }
            });

        }
        mmap.put("clientUserIdList", clientUserService.selectClientUserAllFullDisplay());
        mmap.put("register", register);
        return prefix + "/edit";
    }


    /**
     * 修改保存报名
     */
    @RequiresPermissions("shooting:register:edit")
    @Log(title = "报名", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Register register) {
        register.setUpdateBy(ShiroUtils.getLoginName());
        Register register1=registerService.selectRegisterById(register.getId());
        int success = registerService.updateRegister(register);
        if(success > 0) {
        	  Message message = new Message();
              message.setCreateBy( ShiroUtils.getLoginName() );
              message.setTitle( "报名状态更新" );
              message.setContent( "您的报名状态已更新，请到我的页面查看" );
              message.setType( 2 );
              success = messageService.insertMessage( message );
              if ( success > 0 ) {
                 ClientUser uu = clientUserService.selectClientUserById(register1.getClientUserId());
                    messageService.insertMessageUserInAsync( message.getId(), uu.getId(),
                          message.getCreateBy() );

                    if(register1.getFkTable()==1){
                        // 推送
                        pushService.jpush( message.getTitle(), message.getContent(), uu.getId().toString(),
                                "8", "1", null );
                    }else {
                        // 推送
                        pushService.jpush( message.getTitle(), message.getContent(), uu.getId().toString(),
                                "7", "1", null );
                    }


              } else {
                 throw new RuntimeException( "新增失败" );
              }
        }
        
        return toAjax(success);
    }


    /**
     * 删除报名
     */
    @RequiresPermissions("shooting:register:remove")
    @Log(title = "报名", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(registerService.deleteRegisterByIds(ids));
    }


    /**
     * 查询报名分页列表
     */
    @PostMapping("/list/page")
    public TableDataInfo listPage(@RequestBody Register register) {
        startPage(register.getPd());

        List<Register> list = registerService.selectRegisterListAssoc(register);
        return getDataTable(list);
    }


    void export(HttpServletResponse response, List<Map<String, Object>> list, String[] head, String[] content){
        ExportExcel exportExcel = new ExportExcel();
        StringBuilder filename = new StringBuilder();
        filename.append("导出报名表");
        filename.append(System.currentTimeMillis());
        filename.append(SUFFIX);
        try {
            response.setContentType(EXCEL);
            response.setHeader(CONTENT, CONCAT + new String(filename.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            OutputStream out= response.getOutputStream();
            exportExcel.exportXSExcelByColumn(
                    TITLE,
                    head,
                    content,
                    list,
                    out ,
                    null);
        } catch (IOException e) {
            log.error("导出失败");
        }
        ;
    }

}
