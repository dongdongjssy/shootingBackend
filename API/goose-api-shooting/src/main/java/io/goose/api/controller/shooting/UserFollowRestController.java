package io.goose.api.controller.shooting;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.base.BaseEntity;
import io.goose.common.enums.ClientUserStatus;
import io.goose.common.enums.UserFollowTypes;
import io.goose.common.page.PageDomain;
import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//import org.springframewor.security.access.prepost.PreAuthorize;


/**
 * 用户关注 信息操作处理
 *
 * @author goose
 * @date 2020-05-22
 */
@RestController
@RequestMapping("/shooting/userFollow")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "用户关注 ", description = "用户关注")
public class UserFollowRestController extends BaseController {

    @Autowired
    private IUserFollowService userFollowService;

    @Autowired
    private IPostService postService;

    @Autowired
    private IClubPostService clubPostService;

    @Autowired
    private IClientUserService clientUserService;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private ICarouselService carouselService;


    /**
     * 查询用户关注列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询用户关注列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userFollow", value = "用户关注", required = false, dataType = "UserFollow")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody UserFollow userFollow) {
        startPage();

        List<UserFollow> list = userFollowService.selectUserFollowList(userFollow);
        return getDataTable(list);
    }

    /**
     * 根据ID查询
     */
    @PostMapping("/getById/{id}")
    @ApiOperation(value = " 根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Long")})
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserFollow getById(@PathVariable("id") Long id) {
        return userFollowService.selectUserFollowById(id);
    }

    /**
     * 导出用户关注列表
     */
/*    @PostMapping("/export")
	@ApiOperation(value="导出用户关注列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "userFollow", value = "用户关注", required = false, dataType = "UserFollow")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult export(UserFollow userFollow)
    {
    	List<UserFollow> list = userFollowService.selectUserFollowList(userFollow);
        ExcelUtil<UserFollow> util = new ExcelUtil<UserFollow>(UserFollow.class);
        return util.exportExcel(list, "userFollow");
    } */

    /**
     * 导入用户关注列表
     */
/*    @PostMapping("/import")
	@ApiOperation(value="导入用户关注列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "用户关注文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {
    	try {
        	List<UserFollow> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), UserFollow.class);
        	for(UserFollow obj : list) {
        		userFollowService.insertUserFollow(obj);
        	}
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("成功导入全部数据");

    }*/


    /**
     * 新增保存用户关注
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存用户关注")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userFollow", value = "用户关注", required = true, dataType = "UserFollow")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody UserFollow userFollow) {
        if (userFollowService.selectUserFollowList(userFollow).size() > 0) {
            return AjaxResult.error("已经关注");
        }
        return toAjax(userFollowService.insertUserFollow(userFollow));
    }

    /**
     * 修改保存用户关注
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存用户关注")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userFollow", value = "用户关注", required = true, dataType = "UserFollow")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody UserFollow userFollow) {
        return toAjax(userFollowService.updateUserFollow(userFollow));
    }

    /**
     * 删除用户关注
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除用户关注")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(userFollowService.deleteUserFollowByIds(ids));
    }

    /**
     * 根据id删除用户关注
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除用户关注")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(userFollowService.deleteUserFollowById(id));
    }


    /**
     * 查询用户关注分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询用户关注分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "userFollow", value = "用户关注", required = true, dataType = "UserFollow")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody UserFollow userFollow) {
        if (userFollow.getClientUserId() == null) {
            return null;
        }

        startPage(userFollow.getPd());

        List<BaseEntity> postList = new ArrayList<>();

        Post post = new Post();
        post.setStatus(1);

        List<Post> posts = postService.selectPostList(post);

        // set client user roles
        posts.stream().forEach(r -> {
            ClientUser postUser = r.getClientUser();

            if (!StringUtils.isBlank(postUser.getRoleIds())) {
                String[] roleIds = StringUtils.split(postUser.getRoleIds(), ",");
                for (String roleId : roleIds) {
                    try {
                        Role role = roleService.selectRoleById(Integer.parseInt(roleId));
                        if (role != null) {
                            if (postUser.getRoles() == null) {
                                List<Role> roles = new LinkedList<>();
                                roles.add(role);
                                postUser.setRoles(roles);
                            } else {
                                postUser.getRoles().add(role);
                            }
                        }
                    } catch (Exception e){
                        continue;
                    }
                }
            }
        });

        postList.addAll(posts);

        return getDataTable(postList);
    }

    /**
     * 获取我关注的用户或俱乐部的动态
     *
     * @param userFollow 用户关注
     * @return 动态列表
     */
    @PostMapping("/myFollowPosts")
    public TableDataInfo getMyFollowPosts(@RequestBody UserFollow userFollow) {
        if (userFollow.getClientUserId() == null) {
            return null;
        }
        List<Role> roleList = roleService.selectRoleAll();
        startPage(userFollow.getPd());
        Integer pageNUm=userFollow.getPd().getPageNum();
       // startPage(userFollow.getPd());
        //List<BaseEntity> postList = new ArrayList<>();

        Post post = new Post();
        post.setStatus(1);
        post.setUserId(userFollow.getClientUserId());
        List<Post> posts = postService.selectPostList(post);
        List list1=new ArrayList(16);
        posts.stream().forEach(p->{
        	if (!StringUtils.isBlank(p.getClientUser().getRoleIds())) {
                List<String> roleIds = Arrays.asList(StringUtils.split(p.getClientUser().getRoleIds(), ","));
                List<Role> useFollowRoleList= roleList.stream()
                		                              .filter(role->roleIds.contains(role.getId().toString()))
                		                              .distinct()
                		                              .collect(Collectors.toList());
                p.getClientUser().setRoles(useFollowRoleList);
        	}
        	list1.add(p);
        });


        Carousel carousel=new Carousel();
        carousel.setOnPage(10);
        carousel.setStatus(0);
        List<Carousel> carouselList = carouselService.selectCarouselList( carousel );
        if(!carouselList.isEmpty()) {
            if (carouselList.size() <= pageNUm) {
                pageNUm = pageNUm % carouselList.size();
            }
            list1.add(carouselList.get(pageNUm));
        }
        /*for(int i=0;i<posts.size();i++){
            if(!carouselList.isEmpty()){
                if((i+1)%3==0){
                    if(j<carouselList.size()){
                        list1.add(carouselList.get(j));
                    }else {
                        j=j-carouselList.size();
                        list1.add(carouselList.get(j));
                    }
                    j++;
                }
            }


        }*/
        
        // set client user roles
//        posts.stream().forEach(r -> {
//            ClientUser postUser = r.getClientUser();
//
//            if (!StringUtils.isBlank(postUser.getRoleIds())) {
//                String[] roleIds = StringUtils.split(postUser.getRoleIds(), ",");
//                for (String roleId : roleIds) {
//                    try {
//                        Role role = roleService.selectRoleById(Integer.parseInt(roleId));
//                        if (role != null) {
//                            if (postUser.getRoles() == null) {
//                                List<Role> roles = new LinkedList<>();
//                                roles.add(role);
//                                postUser.setRoles(roles);
//                            } else {
//                                postUser.getRoles().add(role);
//                            }
//                        }
//                    } catch (Exception e){
//                        continue;
//                    }
//                }
//            }
//        });
//
//        postList.addAll(posts);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list1);
        rspData.setTotal(new PageInfo(posts).getTotal());
        return rspData;
       // return getDataTable(list1);
    }

}
