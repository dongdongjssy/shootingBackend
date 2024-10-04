package io.goose.api.controller.shooting;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Role;
import io.goose.shooting.domain.UserRole;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IRoleService;
import io.goose.shooting.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


/**
 * 客户端用户角色关联 信息操作处理
 *
 * @author goose
 * @date 2020-04-28
 */
@RestController
@RequestMapping("/client/userRole")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "客户端用户角色关联 ", description = "客户端用户角色关联")
public class UserRoleRestController extends BaseController {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private IRoleService roleService;

    /**
     * 查询客户端用户角色关联列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询客户端用户角色关联列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "userRole",
            value = "客户端用户角色关联", required = false, dataType = "UserRole")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestHeader(name = "userId") Long userId,
                              @RequestBody UserRole userRole) {

        startPage();

        if (userId == null)
            return null;

        ClientUser user = clientUserService.selectClientUserById(userId);
        String[] roleIds = StringUtils.split(user.getRoleIds(), ",");
        List<Role> roles = new LinkedList<>();

        if (roleIds != null) {
            for (String roleId : roleIds) {
                if (StringUtils.isNotBlank(roleId))
                    try {
                        roles.add(roleService.selectRoleById(Integer.parseInt(roleId)));
                    } catch (NumberFormatException e){
                        continue;
                    }
            }
        }
        Comparator<Role> ageComparator = (o1, o2)->o1.getSort().compareTo(o2.getSort());
        roles.sort(ageComparator);
        //      List<UserRole> list = userRoleService.selectUserRoleListAssoc(userRole);
        return getDataTable(roles);
    }


    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserRole getById(@RequestHeader(name = "userId") Long userId,
                            @PathVariable("id") Long id) {

        return userRoleService.selectUserRoleById(id.intValue());
    }

    /**
     * 导出客户端用户角色关联列表
     */
    /*
     * @PostMapping("/export")
     *
     * @ApiOperation(value="导出客户端用户角色关联列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="body", name = "userRole", value = "客户端用户角色关联", required = false,
     * dataType = "UserRole") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * export(UserRole userRole) { List<UserRole> list =
     * userRoleService.selectUserRoleList(userRole); ExcelUtil<UserRole> util = new
     * ExcelUtil<UserRole>(UserRole.class); return util.exportExcel(list, "userRole"); }
     */

    /**
     * 导入客户端用户角色关联列表
     */
    /*
     * @PostMapping("/import")
     *
     * @ApiOperation(value="导入客户端用户角色关联列表")
     *
     * @ApiImplicitParams({
     *
     * @ApiImplicitParam(paramType="query", name = "file", value = "客户端用户角色关联文件", required = false,
     * dataType = "MultipartFile") }) //@PreAuthorize("hasAnyRole('USER','ADMIN')") public AjaxResult
     * importExcel(@RequestParam(value="file", required=false) MultipartFile excl) { try {
     * List<UserRole> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserRole.class);
     * for(UserRole obj : list) { userRoleService.insertUserRole(obj); } } catch(ExcelUtilException |
     * IOException e) { return AjaxResult.error(e.getMessage()); }
     *
     * return AjaxResult.success("成功导入全部数据");
     *
     * }
     */


    /**
     * 新增保存客户端用户角色关联
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存客户端用户角色关联")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "userRole",
            value = "客户端用户角色关联", required = true, dataType = "UserRole")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestHeader(name = "userId") Long userId,
                          @RequestBody UserRole userRole) {

        return toAjax(userRoleService.insertUserRole(userRole));
    }


    /**
     * 修改保存客户端用户角色关联
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存客户端用户角色关联")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "userRole",
            value = "客户端用户角色关联", required = true, dataType = "UserRole")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestHeader(name = "userId") Long userId,
                           @RequestBody UserRole userRole) {

        return toAjax(userRoleService.updateUserRole(userRole));
    }


    /**
     * 删除客户端用户角色关联
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除客户端用户角色关联")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表",
            required = true, dataType = "String")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(@RequestHeader(name = "userId") Long userId, String ids) {

        return toAjax(userRoleService.deleteUserRoleByIds(ids));
    }


    /**
     * 根据id删除客户端用户角色关联
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除客户端用户角色关联")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "${id}", value = "主键",
            required = true, dataType = "Long")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@RequestHeader(name = "userId") Long userId,
                                 @PathVariable("id") Long id) {

        return toAjax(userRoleService.deleteUserRoleById(id.intValue()));
    }


    /**
     * 查询客户端用户角色关联分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询客户端用户角色关联分页列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "body", name = "userRole",
            value = "客户端用户角色关联", required = true, dataType = "UserRole")})
    // @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestHeader(name = "userId") Long userId,
                                  @RequestBody UserRole userRole) {

        startPage(userRole.getPd());
        List<UserRole> list = userRoleService.selectUserRoleList(userRole);
        return getDataTable(list);
    }

}
