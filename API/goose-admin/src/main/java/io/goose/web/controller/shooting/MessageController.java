package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Message;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IMessageService;
import io.goose.web.controller.service.JpushService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 通告 信息操作处理
 * 
 * @author goose
 * @date 2020-05-20
 */
@Controller
@RequestMapping("/shooting/message")
public class MessageController extends BaseController
{

	   
    private String prefix = "shooting/message";
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private IClientUserService clientUserService;

	@Autowired
	private JpushService pushService;

	
	@RequiresPermissions("shooting:message:view")
	@GetMapping()
	public String message(ModelMap mmap)
	{
	    return prefix + "/message";
	}
	
	/**
	 * 查询通告列表
	 */
	@RequiresPermissions("shooting:message:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Message message)
	{
		startPage();
        List<Message> list = messageService.selectMessageListAssoc(message);
		return getDataTable(list);
	}
	
	
//	/**
//	 * 导出通告列表
//	 */
//	@RequiresPermissions("shooting:message:export")
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(Message message)
//    {
//    	List<Message> list = messageService.selectMessageList(message);
//        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
//        return util.exportExcel(list, "message");
//    }
//    
//    /**
//	 * 导入通告列表
//	 */
//	@RequiresPermissions("shooting:message:import")
//    @PostMapping("/import")
//    @ResponseBody
//    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
//    {        
//    	try {
//        	List<Message> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Message.class);
//        	for(Message obj : list) {
//        		messageService.insertMessage(obj);
//        	} 
//        }
//        catch(ExcelUtilException | IOException e) {
//        	return AjaxResult.error(e.getMessage());
//        }
//        
//        return AjaxResult.success("成功导入全部数据");      
//	        
//    }
	
	/**
	 * 新增通告
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存通告
	 */
	@RequiresPermissions("shooting:message:add")
	@Log(title = "通告", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Message message)
	{		
		message.setCreateBy(ShiroUtils.getLoginName());
		int success = messageService.insertMessage(message);
		if(success > 0) {
			if(message.getType() == 1) {
				List<ClientUser> list = clientUserService.selectClientUserAll();
				for(ClientUser uu : list) {
					messageService.insertMessageUserInAsync(message.getId(),uu.getId(),message.getCreateBy());
					//推送
					pushService.jpushMessage("CPSA通知",message.getTitle(),uu.getId().toString(),"3","2",message.getId().toString(),message.getIsImportant());
				}
			}else {
				String[] ulist = message.getUserList();
				for(int i =0 ; i<ulist.length; i++) {
					messageService.insertMessageUser(message.getId(),Long.valueOf(ulist[i]),message.getCreateBy());
					//推送
					pushService.jpushMessage("CPSA通知",message.getTitle(),ulist[i].toString(),"3","2",message.getId().toString(),message.getIsImportant());
				}
			}
		}else {
			throw new RuntimeException("新增失败");
		}
		return toAjax(success);
	}
	
	/**
	 * 详情
	 */
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, ModelMap mmap)
	{
		Message message = messageService.selectMessageByIdAssoc(id);
		mmap.put("message", message);
	    return prefix + "/detail";
	}

	/**
	 * 修改通告
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Message message = messageService.selectMessageByIdAssoc(id);
		mmap.put("message", message);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存通告
	 */
	@RequiresPermissions("shooting:message:edit")
	@Log(title = "通告", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Message message 
	)
	{		
		message.setUpdateBy(ShiroUtils.getLoginName());		
		return toAjax(messageService.updateMessage(message));
	}
	
	/**
	 * 删除通告
	 */
	@RequiresPermissions("shooting:message:remove")
	@Log(title = "通告", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(messageService.deleteMessageByIds(ids));
	}
	

	/**
	 * 查询通告分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Message message)
	{
		startPage(message.getPd());
		
        List<Message> list = messageService.selectMessageListAssoc(message);
		return getDataTable(list);
	}




	
}
