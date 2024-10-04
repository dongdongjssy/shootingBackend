package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.math.BigDecimal;



/**
 * 商品订单表 shooting_goods_order
 * 
 * @author goose
 * @date 2021-03-19
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;

	@Excel(name = "购买订单编号")
	private String orderTitle;
	/** 订单编号 */
	@Excel(name = "商品订单编号")
	private String title;

	/** 用户 */
	private Long clientUserId;
	@Excel(name = "下单人")
	private String clientUserName;
	/** 总订单id*/
	private Long orderId;
	/** 发票 */
	private Long invoiceId;
	@Excel(name = "发票抬头")
	private String invoiceHead;
	@Excel(name = "发票税号")
	private String invoiceCode;
	@Excel(name = "商户")
	private String userName;
	/** 商品 */
	private Long goodsId;
	/** 属性库存 */
	private Long gtId;

	/** 商品名称 */
	@Excel(name = "商品名称")
	private String name;
	/** 属性 */
	@Excel(name = "属性")
	private String attributes;
	/** 收货地址 */
	private Long goodsAddressId;
	@Excel(name = "收货地址")
	private String address;
	@Excel(name = "收货人")
	private String addressName;
	
	@Excel(name = "收货人手机号")
	private String addressPhone;

	
	
	/** 商品数量 */
	@Excel(name = "购买数量")
	private Integer num;
	/** 价格 */
	@Excel(name = "价格")
	private BigDecimal price;
	/** 邮费 */
	@Excel(name = "邮费")
	private BigDecimal postage;
	@Excel(name = "备注")
	private String remark;

	/** 备注 */
	private String description;
	/** 快递公司 */
	@Excel(name = "快递公司")
	private String courierCompany;
	/** 快递单号 */
	@Excel(name = "快递单号")
	private String courierNumber;
	/** 下单时间 */
	@Excel(name = "下单时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date placeTime;
	/** 取消时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date cancelTime;
	/** 付款时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;
	/** 发货时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryTime;
	/** 收货时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date goodsTime;
	/** 退货时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date ungoodsTime;
	/** 退款时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date unpayTime;
	/** 订单状态 */
	private Integer goodsOrderStatus;
	@Excel(name = "订单状态")
	private String goodsOrderStatusName;

	/** 状态 */
	private Integer status;

	private Order order;

	private String merchants;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;




	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;
	
	@ApiModelProperty(hidden=true)
	private GoodsInvoice  goodsInvoice;
	@ApiModelProperty(hidden=true)
	private Goods  goods;
	@ApiModelProperty(hidden=true)
	private GoodsAddress  goodsAddress;

	public Gt getGt() {
		return gt;
	}

	public void setGt(Gt gt) {
		this.gt = gt;
	}

	@ApiModelProperty(hidden=true)
	private Gt  gt;

	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}    
	public void setGoodsInvoice(GoodsInvoice goodsInvoice) 
	{
		this.goodsInvoice = goodsInvoice;
	}

	public GoodsInvoice getGoodsInvoice() 
	{
		return goodsInvoice;
	}    
	public void setGoods(Goods goods) 
	{
		this.goods = goods;
	}

	public Goods getGoods() 
	{
		return goods;
	}    
	public void setGoodsAddress(GoodsAddress goodsAddress) 
	{
		this.goodsAddress = goodsAddress;
	}

	public GoodsAddress getGoodsAddress() 
	{
		return goodsAddress;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("title", getTitle())
			.append("clientUserId", getClientUserId())
			.append("invoiceId", getInvoiceId())
			.append("goodsId", getGoodsId())
			.append("goodsAddressId", getGoodsAddressId())
			.append("name", getName())
			.append("num", getNum())
			.append("price", getPrice())
			.append("postage", getPostage())
			.append("description", getDescription())
			.append("courierCompany", getCourierCompany())
			.append("courierNumber", getCourierNumber())
			.append("placeTime", getPlaceTime())
			.append("cancelTime", getCancelTime())
			.append("payTime", getPayTime())
			.append("deliveryTime", getDeliveryTime())
			.append("goodsTime", getGoodsTime())
			.append("ungoodsTime", getUngoodsTime())
			.append("unpayTime", getUnpayTime())
			.append("goodsOrderStatus", getGoodsOrderStatus())
				.append("gt", getGt())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
