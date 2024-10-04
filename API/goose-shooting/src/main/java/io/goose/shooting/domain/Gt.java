package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;





/**
 * 商品属性关联表 shooting_gt
 * 
 * @author goose
 * @date 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Gt extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 属性1 */
	private Long attributeId1;
	/** 属性2 */
	private Long attributeId2;
	/** 属性3 */
	private Long attributeId3;
	/** 属性4 */
	private Long attributeId4;
	/** 属性5 */
	private Long attributeId5;
	/** 属性6 */
	private Long attributeId6;
	/** 属性7 */
	private Long attributeId7;
	/** 属性8 */
	private Long attributeId8;
	/** 属性9 */
	private Long attributeId9;

	private String attrbuites;
	/** 商品id */
	private Long goodsId;
	/** 商品库存 */
	private Integer num;

	private String name1;

	private String name2;

	private String name3;

	private String name4;

	private String name5;

	private String name6;

	private String name7;

	private String name8;

	private String name9;

	private String goodsName;

	@ApiModelProperty(hidden=true)
	private GoodsAttribute  goodsAttribute;

	public void setGoodsAttribute(GoodsAttribute goodsAttribute) 
	{
		this.goodsAttribute = goodsAttribute;
	}

	public GoodsAttribute getGoodsAttribute() 
	{
		return goodsAttribute;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("attributeId1", getAttributeId1())
			.append("attributeId2", getAttributeId2())
			.append("attributeId3", getAttributeId3())
			.append("attributeId4", getAttributeId4())
			.append("attributeId5", getAttributeId5())
			.append("attributeId6", getAttributeId6())
			.append("attributeId7", getAttributeId7())
			.append("attributeId8", getAttributeId8())
			.append("attributeId9", getAttributeId9())
			.append("goodsId", getGoodsId())
			.append("num", getNum())
			.toString();
    }
}
