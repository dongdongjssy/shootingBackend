package io.goose.shooting.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 属性表 shooting_goods_attribute
 * 
 * @author goose
 * @date 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsAttribute extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 属性名称 */
	private String name;

	/** 属性名称 */
	private String label;
	/** 上级 */
	private Long parentId;
	/** 商品 */
	private Long goodsId;


	@ApiModelProperty(hidden=true)
	private Goods  goods;


	public void setGoods(Goods goods) 
	{
		this.goods = goods;
	}

	public Goods getGoods() 
	{
		return goods;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
			.append("parentId", getParentId())
			.append("goodsId", getGoodsId())
				.append("createBy",getCreateBy())
			.toString();
    }
}
