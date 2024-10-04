package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 商品评价表 shooting_goods_evaluation
 * 
 * @author goose
 * @date 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsEvaluation extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 订单id */
	private Long goodsOrderId;
	/** 商品id */
	private Long goodsId;
	/** 用户id */
	private Long clientUserId;
	/** 评价 */
	private String comments;
	/** 星级 */
	private Integer star;
	/** 状态 */
	private Integer status;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品订单号
	 */
	private String goodsOrderName;
	/**
	 * 商户
	 */
	private String userName;

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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("goodsOrderId", getGoodsOrderId())
			.append("goodsId", getGoodsId())
			.append("clientUserId", getClientUserId())
			.append("comments", getComments())
			.append("star", getStar())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
