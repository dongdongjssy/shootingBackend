package io.goose.shooting.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 购物车表 shooting_goods_shopping_cart
 * 
 * @author goose
 * @date 2021-03-16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsShoppingCart extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户id */
	private Long clientUserId;
	/** 商品id */
	private Long goodsId;
	/** 数量 */
	private Integer nums;
	/** 库存 */
	private Integer inventory;
	/** 状态 */
	private Integer status;
	/**选择的属性*/
	private String attributes;
	private String attributesIds;

	@ApiModelProperty(hidden=true)
	private ClientUser  clientUser;

	private String ids;



	public void setClientUser(ClientUser clientUser) 
	{
		this.clientUser = clientUser;
	}

	public ClientUser getClientUser() 
	{
		return clientUser;
	}

	@ApiModelProperty(hidden=true)
	private Goods  goods;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("clientUserId", getClientUserId())
			.append("goodsId", getGoodsId())
			.append("nums", getNums())
			.append("inventory", getInventory())
			.append("attributes", getAttributes())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
