package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageHelper;
import io.goose.common.page.PageDomain;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.GoodsOrder;
import io.goose.shooting.domain.Message;
import io.goose.shooting.service.IGoodsOrderService;
import io.goose.shooting.service.IMessageService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.shooting.domain.Order;
import io.goose.shooting.service.IOrderService;
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.FileUploadUtils;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.config.Global;

/**
 * 订单 信息操作处理
 * 
 * @author goose
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/shooting/order")
public class OrderController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private Global global;

    private String prefix = "shooting/order";
	
	@Autowired
	private IOrderService orderService;

	@Autowired
	private IGoodsOrderService goodsOrderService;

	@Autowired
	private JpushService pushService;

	@Autowired
	private IMessageService messageService;
	@RequiresPermissions("shooting:order:view")
	@GetMapping()
	public String order(ModelMap mmap)
	{
	    return prefix + "/order";
	}
	
	/**
	 * 查询订单列表
	 */
	@RequiresPermissions("shooting:order:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Order order)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
			String orderBy = pageDomain.getOrderBy();
			if("".equals(orderBy)){
				orderBy="create_time desc";
			}
			PageHelper.startPage(pageNum, pageSize, orderBy);
		}
        List<Order> list = orderService.selectOrderListAssoc(order);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出订单列表
	 */
	@RequiresPermissions("shooting:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Order order)
    {
    	List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        return util.exportExcel(list, "order");
    }
    
    /**
	 * 导入订单列表
	 */
	@RequiresPermissions("shooting:order:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<Order> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), Order.class);
        	for(Order obj : list) {
        		orderService.insertOrder(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增订单
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存订单
	 */
	@RequiresPermissions("shooting:order:add")
	@Log(title = "订单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Order order 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = FileUploadUtils.upload(global.getMediaPathOrder(), pictureUrlFile);
				order.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	

		order.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(orderService.insertOrder(order));
	}

	/**
	 * 修改订单
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		Order order = orderService.selectOrderByIdAssoc(id);
		mmap.put("order", order);
	    mmap.put("imageUrlPrefix", getImageUrlPrefix());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存订单
	 */
	@RequiresPermissions("shooting:order:edit")
	@Log(title = "订单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Order order 
				 , @RequestParam(value="pictureUrlFile", required=false) MultipartFile pictureUrlFile
	)
	{		
		try {
			if (pictureUrlFile != null && !pictureUrlFile.isEmpty()) {
				String filePath = FileUploadUtils.upload(global.getMediaPathOrder(), pictureUrlFile);
				order.setPictureUrl(filePath);
			}
		} catch (Exception e) {
			log.error("图像上传失败！", e);
			return error(e.getMessage());
		}	
		order.setUpdateBy(ShiroUtils.getLoginName());
		int row= orderService.updateOrder(order);
		if(row>0){
			List<GoodsOrder> goodsOrderList=	goodsOrderService.selectGoodsOrdersByOrderId(order.getId());
			goodsOrderList.forEach(goodsOrder -> {
				goodsOrder.setGoodsOrderStatus(order.getGoodsOrderStatus());
				goodsOrderService.updateGoodsOrder(goodsOrder);
			});
			Order order1=orderService.selectOrderById(order.getId());
			Message message = new Message();
			message.setCreateBy(ShiroUtils.getLoginName());
			message.setTitle( "订单状态改变" );
			message.setContent( "您的订单状态已经改变" );
			message.setType(2);
			int success = messageService.insertMessage( message );
			if ( success > 0 ) {
				messageService.insertMessageUser( message.getId(),order1.getClientUserId(),
						message.getCreateBy() );
				// 推送
				pushService.jpush( message.getTitle(), message.getContent(), order1.getClientUserId().toString(),
						"9", "1", String.valueOf( order1.getId() ) );
			} else {
				throw new RuntimeException( "新增失败" );
			}
		}
		return toAjax(row);
	}
	
	/**
	 * 删除订单
	 */
	@RequiresPermissions("shooting:order:remove")
	@Log(title = "订单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(orderService.deleteOrderByIds(ids));
	}
	

	/**
	 * 查询订单分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody Order order)
	{
		startPage(order.getPd());
		
        List<Order> list = orderService.selectOrderListAssoc(order);
		return getDataTable(list);
	}




	
}
