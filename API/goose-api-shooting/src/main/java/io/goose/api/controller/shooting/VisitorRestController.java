package io.goose.api.controller.shooting;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.jmessage.api.common.model.RegisterInfo;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.goose.api.controller.service.JiguangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goose.common.base.AjaxResult;
import io.goose.common.page.PageDomain;
import io.goose.common.page.TableDataInfo;
import io.goose.common.utils.EmailUtil;
import io.goose.framework.web.base.BaseController;
import io.goose.shooting.domain.Visitor;
import io.goose.shooting.service.IVisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 访客 信息操作处理
 *
 * @author goose
 * @date 2020-12-24
 */
@RestController
@RequestMapping("/shooting/visitor")
//@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "访客 ", description = "访客")
public class VisitorRestController extends BaseController {

    @Autowired
    private IVisitorService visitorService;
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private JiguangService jiguangService;

    /**
     * 查询访客列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询访客列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "visitor", value = "访客", required = false, dataType = "Visitor")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo list(@RequestBody Visitor visitor) {
        startPage();
        List<Visitor> list = visitorService.selectVisitorList(visitor);
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
    public Visitor getById(@PathVariable("id") Long id) {
        return visitorService.selectVisitorById(id);
    }

    /**
     * 导出访客列表
     */
/*    @PostMapping("/export")
	@ApiOperation(value="导出访客列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "visitor", value = "访客", required = false, dataType = "Visitor")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Visitor visitor)
    {
    	List<Visitor> list = visitorService.selectVisitorList(visitor);
        ExcelUtil<Visitor> util = new ExcelUtil<Visitor>(Visitor.class);
        return util.exportExcel(list, "visitor");
    } */

    /**
     * 导入访客列表
     */
/*    @PostMapping("/import")
	@ApiOperation(value="导入访客列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "访客文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Visitor> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Visitor.class);
        	for(Visitor obj : list) {
        		visitorService.insertVisitor(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/


    /**
     * 新增保存访客
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增保存访客")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "visitor", value = "访客", required = true, dataType = "Visitor")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult add(@RequestBody Visitor visitor) {
        return toAjax(visitorService.insertVisitor(visitor));
    }

    /**
     * 修改保存访客
     */
    @PostMapping("/edit")
    @ApiOperation(value = "修改保存访客")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "visitor", value = "访客", required = true, dataType = "Visitor")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult edit(@RequestBody Visitor visitor) {
        return toAjax(visitorService.updateVisitor(visitor));
    }

    /**
     * 删除访客
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除访客")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult remove(String ids) {
        return toAjax(visitorService.deleteVisitorByIds(ids));
    }

    /**
     * 根据id删除访客
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除访客")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "${id}", value = "主键", required = true, dataType = "Long")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult removeById(@PathVariable("id") Long id) {
        return toAjax(visitorService.deleteVisitorById(id));
    }


    /**
     * 查询访客分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "查询访客分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "visitor", value = "访客", required = true, dataType = "Visitor")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TableDataInfo listPage(@RequestBody Visitor visitor) {
        startPage(visitor.getPd());
        List<Visitor> list = visitorService.selectVisitorList(visitor);
        return getDataTable(list);
    }

    /**
     * 访客登陆
     */
    @PostMapping("/login")
    @ApiOperation(value = "访客登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "visitor", value = "访客", required = true, dataType = "Visitor")
    })
    //@PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AjaxResult login(@RequestBody Visitor visitor) {
        // 校验验证码
        if (emailUtil.containCode(visitor.getEmail(), visitor.getCaptcha())) {
                visitor.setLoginTime(new Date());
                visitor.setStatus(0);
                return add(visitor);


            /*Visitor existingVisitor = visitorService.selectVisitorByEmail(visitor.getEmail());

            if (existingVisitor != null) {
                existingVisitor.setLoginTime(new Date());
                existingVisitor.setCaptcha(visitor.getCaptcha());
                visitorService.updateVisitor(existingVisitor);

                return AjaxResult.success();
            } else {
                // 添加用户到极光
                if (jiguangService.addJgUser(visitor.getEmail())) {
                    visitor.setLoginTime(new Date());
                    visitor.setStatus(0);
                    return add(visitor).put("jgUsername", visitor.getEmail());
                } else {
                    return AjaxResult.error("添加到极光聊天失败");
                }
            }*/
        } else {
            return error("登陆失败，验证码错误");
        }
    }
}
