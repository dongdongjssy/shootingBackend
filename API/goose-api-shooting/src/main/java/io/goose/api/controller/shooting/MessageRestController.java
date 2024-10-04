package io.goose.api.controller.shooting;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
//import org.springframewor.security.access.prepost.PreAuthorize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import io.goose.shooting.domain.Message;
import io.goose.shooting.service.IMessageService;


import io.goose.common.page.TableDataInfo;
import io.goose.framework.web.base.BaseController;
import io.goose.common.base.AjaxResult;


/**
 * 通告 信息操作处理
 * 
 * @author goose
 * @date 2020-05-20
 */
@RestController
@RequestMapping("/shooting/message")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "通告 ",description="通告")
public class MessageRestController extends BaseController
{

	@Autowired
	private IMessageService messageService;



	/**
	 * 聊天列表查看系统消息是否有未读数据
	 */
	@PostMapping("/isView")
	@ApiOperation(value="聊天列表查看系统消息是否有未读数据")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = false, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public int isView( @RequestBody  Message message)
	{
		return messageService.selectMessageIsViewByUser(message);
	}
	
	
	/**
	 * 查询客户可见的通告且所有变为已读
	 */
	@PostMapping("/listByUser")
	@ApiOperation(value="查询客户可见的通告且所有变为已读")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = false, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo listByUser( @RequestBody  Message message)
	{
        messageService.updateMessageUser(message.getClientUserId());
		startPage();
        List<Message> list = messageService.selectMessageListByUser(message);
        for(Message m : list) {
        	if(m.getTitle().equals("新赛事发布")) {
        		m.setType(2);
        		m.setSubType(1);
        	}else if(m.getTitle().equals("新培训发布")) {
        		m.setType(1);
        		m.setSubType(1);
        	}else if(m.getTitle().equals("赛事报名成功")){
        		m.setType(2);
        		m.setSubType(2);
        	}else if(m.getTitle().equals("培训报名成功")) {
        		m.setType(1);
        		m.setSubType(2);
        	}else if(m.getContent().contains("祝您取得好成绩")){
        		m.setType(2);
        		m.setSubType(2);
        	}else if(m.getTitle().equals("身份认证更新")){
        		m.setType(5);
        		m.setSubType(0);
        	}else{
        		m.setType(3);
        		m.setSubType(1);
        		m.setOrderId(m.getId());
        	}
        }
		return getDataTable(list);
	}
	
	
	/**
	 * 查询通告列表
	 */
	@PostMapping("/list")
	@ApiOperation(value="查询通告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = false, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public TableDataInfo list( @RequestBody  Message message)
	{
		startPage();
        List<Message> list = messageService.selectMessageList(message);
		return getDataTable(list);
	}
	
	/**
	 * 根据ID查询
	 * */
	@PostMapping("/getById/{id}")
	@ApiOperation(value=" 根据ID查询")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "id", value = "主键", required = true, dataType = "Long")})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")		
	public Message getById( @PathVariable("id") Long id) {
		return messageService.selectMessageById(id);
	}
	
	/**
	 * 导出通告列表
	 */
/*    @PostMapping("/export")
	@ApiOperation(value="导出通告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = false, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult export(Message message)
    {
    	List<Message> list = messageService.selectMessageList(message);
        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
        return util.exportExcel(list, "message");
    } */
    
    /**
	 * 导入通告列表
	 */
/*    @PostMapping("/import")
	@ApiOperation(value="导入通告列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query", name = "file", value = "通告文件", required = false, dataType = "MultipartFile")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Message> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Message.class);
        	for(Message obj : list) {
        		messageService.insertMessage(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }*/    

	
	/**
	 * 新增保存通告
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增保存通告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = true, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult add( @RequestBody Message message)
	{
		return toAjax(messageService.insertMessage(message));
	}
	
	/**
	 * 修改保存通告
	 */
	@PostMapping("/edit")
	@ApiOperation(value="修改保存通告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = true, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public AjaxResult edit( @RequestBody Message message)
	{
		return toAjax(messageService.updateMessage(message));
	}
	
	/**
	 * 删除通告
	 */
	@PostMapping( "/remove")
	@ApiOperation(value="删除通告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "${ids}", value = "主键列表", required = true, dataType = "String")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")			
	public AjaxResult remove( String ids)
	{
		return toAjax(messageService.deleteMessageByIds(ids));
	}
	
		/**
	 * 根据id删除通告
	 */
    @PostMapping( "/remove/{id}")
	@ApiOperation(value="根据id删除通告")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="path", name = "${id}", value = "主键", required = true, dataType = "Long")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public AjaxResult removeById( @PathVariable("id") Long id)
	{		
		return toAjax(messageService.deleteMessageById(id));
	}
	
	
		/**
	 * 查询通告分页列表
	 */
	@PostMapping("/list/page")
	@ApiOperation(value="查询通告分页列表")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="body", name = "message", value = "通告", required = true, dataType = "Message")
	})
	//@PreAuthorize("hasAnyRole('USER','ADMIN')")	
	public TableDataInfo listPage( @RequestBody  Message message)
	{
		startPage(message.getPd());	
        List<Message> list = messageService.selectMessageList(message);
		return getDataTable(list);
	}
	



	
}
