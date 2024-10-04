package io.goose.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.ClubJoinApplication;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IClubJoinApplicationService;
import io.goose.web.controller.service.JMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.goose.common.annotation.Log;
import io.goose.common.base.AjaxResult;
import io.goose.common.enums.BusinessType;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.shiro.service.SysPasswordService;
import io.goose.framework.util.ShiroUtils;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Club;
import io.goose.shooting.service.IClubService;
import io.goose.system.domain.SysUser;
import io.goose.system.service.ISysPostService;
import io.goose.system.service.ISysRoleService;
import io.goose.system.service.ISysUserService;


/**
 * 用户信息
 *
 * @author goose
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;
    @Autowired
    private IClubService clubService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private JMessageService jMsgService;

    @Autowired
    private IClubJoinApplicationService clubJoinApplicationService;

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
        if (ShiroUtils.getClubId() != null && ShiroUtils.getClubId() != 0) {
            user.setClubId(ShiroUtils.getClubId());
        }
        List<SysUser> list = userService.selectUserList(user);
        List<Club> clubList = clubService.selectClubAll();
        for (SysUser u : list) {
            if (u.getClubId() == null || u.getClubId() == 0) {
                u.setOrgName(null);
            } else {
                for (Club o : clubList) {
                    if (o.getId() == u.getClubId()) {
                        u.setOrgName(o.getTitle());
                    }
                }
            }
        }
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user) {
        user.setOrgId(ShiroUtils.getOrgId());
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "user");
    }


    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user(ModelMap mmap) {
        mmap.put("orgIdList", getOrgList(true));
        return prefix + "/user";
    }

    private List<Club> getOrgList(boolean allOrgOption) {
        Long orgId = ShiroUtils.getClubId();
        List<Club> orgList = new ArrayList<>();
        if (allOrgOption) {
            if (orgId == null || orgId == 0) {
                Club allOrg = new Club();
                allOrg.setTitle("全部俱乐部");
                allOrg.setId(0l);
                orgList.add(allOrg);
            }
        }

        Club orgInfo = new Club();
        if (orgId != null && orgId != 0) orgInfo.setId(orgId);
        orgList.addAll(clubService.selectClubList(orgInfo));
        return orgList;
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("orgIdList", getOrgList(true));
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }


    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }
        if (user.getPhonenumber().length() < 4) {
            return error("手机号格式不对");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(),
                user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        addUserToClientUser(user);
        return toAjax(userService.insertUser(user));
    }


    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        boolean allOrgOption = false;//禁止提升某商会用户为超级管理员
        SysUser user = userService.selectUserById(userId);
        if (user != null && (user.getClubId() == null || user.getClubId() == 0)) {
            allOrgOption = true;
        }
        mmap.put("orgIdList", getOrgList(allOrgOption));
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));

        return prefix + "/edit";
    }


    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && SysUser.isAdmin(user.getUserId())) {
            return error("不允许修改超级管理员用户");
        }

        if (user.getPhonenumber().length() < 4) {
            return error("手机号格式不对");
        }

        user.setUpdateBy(ShiroUtils.getLoginName());
        addUserToClientUser(user);

        return toAjax(userService.updateUser(user));
    }

    private int addUserToClientUser(SysUser sysUser) {
        int result = -1;

        ClientUser existingClientUser = clientUserService.selectClientUserByPhone(sysUser.getPhonenumber());
        Club club=new Club();
        if(sysUser.getClubId()!=null && sysUser.getClubId()!=0){
            club = clubService.selectClubById(sysUser.getClubId());
        }


        // update
        if (existingClientUser != null) {
            if (club != null) {
                if (existingClientUser.getJgUsername() != null) {
                    if (!existingClientUser.getJgUsername().equals(existingClientUser.getPhone())) {
                        if (jMsgService.addUserToJMessage(existingClientUser)) {
                            if (!jMsgService.addUserToGroup(club, existingClientUser))
                                return -1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (!jMsgService.addUserToGroup(club, existingClientUser))
                            return -1;
                    }
                } else {
                    if (jMsgService.addUserToJMessage(existingClientUser)) {
                        if (!jMsgService.addUserToGroup(club, existingClientUser))
                            return -1;
                    } else {
                        return -1;
                    }
                }
            }
            //            existingClientUser.setUserName(sysUser.getLoginName() != null ? sysUser.getUserName() : sysUser.getPhonenumber());
            existingClientUser.setPhone(sysUser.getPhonenumber());
            //            existingClientUser.setNickname(sysUser.getUserName() != null ? sysUser.getUserName() : sysUser.getPhonenumber());
            existingClientUser.setStatus(2);
            existingClientUser.setJgUsername(sysUser.getPhonenumber());

            result = clientUserService.updateClientUser(existingClientUser);

            if (Objects.isNull(club)) {
                ClubJoinApplication clubJoinApplication = new ClubJoinApplication();
                clubJoinApplication.setClientUser(existingClientUser);
                clubJoinApplication.setClientUserId(existingClientUser.getId());
                clubJoinApplication.setClub(club);
                clubJoinApplication.setClubId(club.getId());
                clubJoinApplication.setStatus(1);
                clubJoinApplicationService.insertClubJoinApplication(clubJoinApplication);
            }
        }

        // insert
        else {
            ClientUser clientUser = new ClientUser();
            clientUser.setUserName(sysUser.getLoginName() != null ? sysUser.getUserName() : sysUser.getPhonenumber());
            clientUser.setNickname(sysUser.getUserName() != null ? sysUser.getUserName() : sysUser.getPhonenumber());
            clientUser.setPhone(sysUser.getPhonenumber());
            clientUser.setJgUsername(sysUser.getPhonenumber());
            clientUser.setStatus(2);

            if (Objects.isNull(club)) {
                if (jMsgService.addUserToJMessage(clientUser)) {
                    if (!jMsgService.addUserToGroup(club, clientUser))
                        return -1;
                } else {
                    return -1;
                }
            }

            result = clientUserService.insertClientUser(clientUser);

            if (Objects.isNull(club)) {
                ClubJoinApplication clubJoinApplication = new ClubJoinApplication();
//                clubJoinApplication.setClientUser(clientUser);
                clubJoinApplication.setClientUserId(clientUser.getId());
//                clubJoinApplication.setClub(club);
                clubJoinApplication.setClubId(club.getId());
                clubJoinApplication.setStatus(1);
                if (clubJoinApplicationService.selectClubJoinApplicationList(clubJoinApplication).size() == 0)
                    clubJoinApplicationService.insertClubJoinApplication(clubJoinApplication);
            }
        }

        return result;
    }


    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }


    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(),
                user.getSalt()));
        return toAjax(userService.resetUserPwd(user));
    }


    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }


    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user) {
        return userService.checkPhoneUnique(user);
    }


    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user) {
        return userService.checkEmailUnique(user);
    }

}
