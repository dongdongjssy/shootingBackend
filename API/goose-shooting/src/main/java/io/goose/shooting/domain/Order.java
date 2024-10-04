package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 订单表 shooting_order
 * 
 * @author goose
 * @date 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Order extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 订单编号 */
	private String title;
	/** 用户id */
	private Long clientUserId;
	/** 总价格 */
	private Integer price;
	/** 付款码 */
	private String pictureUrl;
	/** 付款码证明 */
	private String picture;
	/** 付款码说明 */
	private String comment;

	private String name;

	/** 支付状态 */
	private Integer goodsOrderStatus;
	/** 支付时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date payTime;
	/** 状态 */
	private Integer status;

	private List<GoodsOrder> goodsOrderList;

	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;

	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}    

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("title", getTitle())
			.append("clientUserId", getClientUserId())
			.append("price", getPrice())
			.append("pictureUrl", getPictureUrl())
			.append("goodsOrderStatus", getGoodsOrderStatus())
			.append("payTime", getPayTime())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("picture", getPictureUrl())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
