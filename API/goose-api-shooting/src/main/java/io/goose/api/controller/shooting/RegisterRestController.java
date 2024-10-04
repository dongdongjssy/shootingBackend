package io.goose.api.controller.shooting;

import java.util.List;
import java.util.Objects;

import io.goose.shooting.service.IRegisterFormItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Register;
import io.goose.shooting.service.IRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 报名 信息操作处理
 *
 * @author goose
 * @date 2020-05-19
 */
@RestController
@RequestMapping("/shooting/register")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "报名 ", description = "报名")
public class RegisterRestController extends BaseController {

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private IRegisterFormItemService registerFormItemService;

    /**
     * 查询报名列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询报名列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "register", value = "报名",
            required = false, dataType = "Register")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody Register register) {
        startPage();

        List<Register> list = registerService.selectRegisterList(register);
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
    public Register getById(@PathVariable("id") Long id) {
        return registerService.selectRegisterById(id);
    }

    /**
     * 导出报名列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出报名列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "register", value = "报名", required = false,
     * dataType = "Register") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(Register register) { List<Register> list =
     * registerService.selectRegisterList(register); ExcelUtil<Register> util = new
     * ExcelUtil<Register>(Register.class); return util.exportExcel(list, "register"); }
     */

    /**
     * 导入报名列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入报名列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "报名文件", required = false, dataType
     * = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<Register> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Register.class);
     * for(Register obj : list) { registerService.insertRegister(obj); } } catch(ExcelUtilException |
     * IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存报名
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存报名")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "register", value = "报名",
            required = true, dataType = "Register")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    //@Transactional
    public AjaxResult add(@RequestBody Register register) {
        int result = registerService.insertRegister(register);

        if(result > 0&& !Objects.isNull(register.getRegisterFormItems())) {
            register.getRegisterFormItems().forEach(item -> {
                item.setRegisterId(register.getId());
            });

          result=  registerFormItemService.insertRegisterFormItems(register.getRegisterFormItems());
        }

        return toAjax(result);
    }


    /**
     * 修改保存报名
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存报名")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "register", value = "报名",
            required = true, dataType = "Register")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Register register) {
        return toAjax(registerService.updateRegister(register));
    }


    /**
     * 删除报名
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除报名")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(registerService.deleteRegisterByIds(ids));
    }


    /**
     * 根据id删除报名
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除报名")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(registerService.deleteRegisterById(id));
    }


    /**
     * 查询报名分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询报名分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "register", value = "报名",
            required = true, dataType = "Register")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Register register) {
        startPage(register.getPd());
        List<Register> list = registerService.selectRegisterList(register);
        return getDataTable(list);
    }

}
