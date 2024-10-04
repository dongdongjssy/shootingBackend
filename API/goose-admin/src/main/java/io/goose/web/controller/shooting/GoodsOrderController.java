package io.goose.web.controller.shooting;

import java.io.IOException;
import java.util.*;

import com.github.pagehelper.PageHelper;
import io.goose.common.page.PageDomain;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.ExcelUtil;
import io.goose.common.utils.StringUtils;
import io.goose.framework.web.page.TableSupport;
import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.goose.system.domain.SysUser;
import io.goose.system.service.ISysDictDataService;
import io.goose.system.service.ISysUserService;
import io.goose.web.controller.service.JpushService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.goose.framework.web.base.BaseController;
import io.goose.framework.util.ShiroUtils;
import io.goose.common.page.TableDataInfo;
import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;

/**
 * 商品订单 信息操作处理
 * 
 * @author goose
 * @date 2021-03-19
 */
@Controller
@RequestMapping("/shooting/goodsOrder")
public class GoodsOrderController extends BaseController
{

    private String prefix = "shooting/goodsOrder";
	
	@Autowired
	private IGoodsOrderService goodsOrderService;

	@Autowired
	private IGoodsAttributeService attributeService;

	@Autowired
	private	IOrderService orderService;

	@Autowired
	private IGoodsInvoiceService goodsInvoiceService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IGoodsAddressService goodsAddressService;

	@Autowired
	private ISysDictDataService dictDataService;

	@Autowired
	private JpushService pushService;

	@Autowired
	private IMessageService messageService;
	@Autowired
	private ISysUserService userService;

	@Autowired
	private  IClientUserService clientUserService;
	
	@RequiresPermissions("shooting:goodsOrder:view")
	@GetMapping()
	public String goodsOrder(ModelMap mmap)
	{

		mmap.put("merchantsList", userService.selectMerchantsList(new SysUser()));
	    mmap.put("invoiceIdList", goodsInvoiceService.selectGoodsInvoiceAll());
	    mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    mmap.put("goodsAddressIdList", goodsAddressService.selectGoodsAddressAll());
	    return prefix + "/goodsOrder";
	}
	
	/**
	 * 查询商品订单列表
	 */
	@RequiresPermissions("shooting:goodsOrder:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(GoodsOrder goodsOrder)
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
		goodsOrder.setCreateBy(ShiroUtils.getLoginName());
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderListAssoc(goodsOrder);
        list.forEach(goodsOrder1 -> {
        	if(StringUtils.isNotBlank(goodsOrder1.getAttributes())){
				String attributes=goodsOrder1.getAttributes();
				String[] attrs=	attributes.split("/");
				StringBuilder sb=new StringBuilder();
				for (int i=0;i<attrs.length;i++){
					GoodsAttribute attribute=attributeService.selectGoodsAttributeById(Long.parseLong(attrs[i]));
					if(!Objects.isNull(attribute)){
						sb.append(attribute.getName()+"/");
					}
				}
				if(StringUtils.isNotBlank(sb)){
					goodsOrder1.setAttributes(sb.substring(0,sb.length() - 1));
				}
			}
		});
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品订单列表
	 */
	@RequiresPermissions("shooting:goodsOrder:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GoodsOrder goodsOrder)
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
		goodsOrder.setCreateBy(ShiroUtils.getLoginName());
    	List<GoodsOrder> list = goodsOrderService.selectGoodsOrderListAssoc(goodsOrder);
    	list.forEach(goodsOrder1 -> {
			if(goodsOrder1.getOrderId()!=null){
				Order order=orderService.selectOrderById(goodsOrder1.getOrderId());
				goodsOrder1.setOrderTitle(order.getTitle());
			}
			if(goodsOrder1.getClientUserId()!=null){
				ClientUser clientUser=clientUserService.selectClientUserById(goodsOrder1.getClientUserId());
				if(!Objects.isNull(clientUser)){
					goodsOrder1.setClientUserName(clientUser.getNickname());
				}
			}
			if(goodsOrder1.getGoodsInvoice()!=null&&goodsOrder1.getGoodsInvoice().getHead()!=null){
				goodsOrder1.setInvoiceHead(goodsOrder1.getGoodsInvoice().getHead());
				goodsOrder1.setInvoiceCode(goodsOrder1.getGoodsInvoice().getCode());
			}
			if(goodsOrder1.getGoods()!=null&&goodsOrder1.getGoods().getCreateBy()!=null){
				goodsOrder1.setUserName(goodsOrder1.getGoods().getCreateBy());
			}
			if(goodsOrder1.getGoodsAddressId()!=null){
				GoodsAddress address=goodsAddressService.selectGoodsAddressById(goodsOrder1.getGoodsAddressId());
				if(Objects.isNull(address)){
					goodsOrder1.setAddress("");
					goodsOrder1.setAddressName("");
					goodsOrder1.setAddressPhone("");
				}else {
					goodsOrder1.setAddress(Optional.ofNullable(address.getProvince()).orElse("") + Optional.ofNullable(address.getCity()).orElse("") + Optional.ofNullable(address.getArea()).orElse("") + Optional.ofNullable(address.getAddress()).orElse(""));
					goodsOrder1.setAddressName(address.getName());
					goodsOrder1.setAddressPhone(address.getPhone());
				}


			}
			if(goodsOrder1.getGoodsOrderStatus()!=null){
				goodsOrder1.setGoodsOrderStatusName(dictDataService.selectDictLabel("goods_order_status", goodsOrder1.getGoodsOrderStatus().toString()));
			}
			if(StringUtils.isNotBlank(goodsOrder1.getAttributes())){
				String attributes=goodsOrder1.getAttributes();
				String[] attrs=	attributes.split("/");
				StringBuilder sb=new StringBuilder();
				for (int i=0;i<attrs.length;i++){
					GoodsAttribute attribute=attributeService.selectGoodsAttributeById(Long.parseLong(attrs[i]));
					if(!Objects.isNull(attribute)){
						sb.append(attribute.getName()+"/");

					}
				}
				if(StringUtils.isNotBlank(sb)){
					goodsOrder1.setAttributes(sb.substring(0,sb.length() - 1));
				}
				//goodsOrder1.setAttributes(sb.substring(0,sb.length() - 1));
			}
		});



        ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        return util.exportExcel(list, "goodsOrder");
    }
    
    /**
	 * 导入商品订单列表
	 */
	@RequiresPermissions("shooting:goodsOrder:import")
    @PostMapping("/import")
    @ResponseBody
    public AjaxResult importExcel(@RequestParam(value="file", required=false) MultipartFile excl)
    {        
    	try {
        	List<GoodsOrder> list = ExcelImpExpUtil.parseExcelFile(excl.getInputStream(), GoodsOrder.class);
        	for(GoodsOrder obj : list) {
        		goodsOrderService.insertGoodsOrder(obj);
        	} 
        }
        catch(ExcelUtilException | IOException e) {
        	return AjaxResult.error(e.getMessage());
        }
        
        return AjaxResult.success("成功导入全部数据");      
	        
    }
	
	/**
	 * 新增商品订单
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap)
	{
	    mmap.put("invoiceIdList", goodsInvoiceService.selectGoodsInvoiceAll());
	    mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    mmap.put("goodsAddressIdList", goodsAddressService.selectGoodsAddressAll());
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品订单
	 */
	@RequiresPermissions("shooting:goodsOrder:add")
	@Log(title = "商品订单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(GoodsOrder goodsOrder 
	)
	{		

		goodsOrder.setCreateBy(ShiroUtils.getLoginName());
		
		return toAjax(goodsOrderService.insertGoodsOrder(goodsOrder));
	}

	/**
	 * 修改商品订单
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		GoodsOrder goodsOrder = goodsOrderService.selectGoodsOrderByIdAssoc(id);
		mmap.put("goodsOrder", goodsOrder);
	    mmap.put("invoiceIdList", goodsInvoiceService.selectGoodsInvoiceAll());
	    mmap.put("goodsIdList", goodsService.selectGoodsAll());
	    mmap.put("goodsAddressIdList", goodsAddressService.selectGoodsAddressAll());
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品订单
	 */
	@RequiresPermissions("shooting:goodsOrder:edit")
	@Log(title = "商品订单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(GoodsOrder goodsOrder 
	)
	{		
		goodsOrder.setUpdateBy(ShiroUtils.getLoginName());

		int row=goodsOrderService.updateGoodsOrder(goodsOrder);
		Message message = new Message();
		message.setCreateBy(ShiroUtils.getLoginName());
		message.setTitle( "订单状态改变" );
		message.setContent( "您的订单状态已经改变" );
		message.setType(2);
		int success = messageService.insertMessage( message );
		if ( success > 0 ) {
			GoodsOrder goodsOrder1=goodsOrderService.selectGoodsOrderById(goodsOrder.getId());
			messageService.insertMessageUser( message.getId(),goodsOrder1.getClientUserId(),
					message.getCreateBy() );
			// 推送
			pushService.jpush( message.getTitle(), message.getContent(), goodsOrder1.getClientUserId().toString(),
					"9", "1", String.valueOf( goodsOrder.getId() ) );
		} else {
			throw new RuntimeException( "新增失败" );
		}
		return toAjax(row);
	}
	
	/**
	 * 删除商品订单
	 */
	@RequiresPermissions("shooting:goodsOrder:remove")
	@Log(title = "商品订单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(goodsOrderService.deleteGoodsOrderByIds(ids));
	}
	

	/**
	 * 查询商品订单分页列表
	 */
	@PostMapping("/list/page")
	public TableDataInfo listPage(@RequestBody GoodsOrder goodsOrder)
	{
		startPage(goodsOrder.getPd());
		
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderListAssoc(goodsOrder);
		return getDataTable(list);
	}







	
}
