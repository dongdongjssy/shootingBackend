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
 * 商品表 shooting_goods
 * 
 * @author goose
 * @date 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 商品名称 */
	private String name;
	/** 商品描述 */
	private String description;
	/** 商品图片1 */
	private String pictureUrl1;
	/** 商品图片2 */
	private String pictureUrl2;
	/** 商品图片3 */
	private String pictureUrl3;
	/** 商品图片4 */
	private String pictureUrl4;
	/** 商品图片5 */
	private String pictureUrl5;
	/** 商品图片6 */
	private String pictureUrl6;
	/** 商品图片7 */
	private String pictureUrl7;
	/** 商品图片8 */
	private String pictureUrl8;
	/** 商品图片9 */
	private String pictureUrl9;
	/** 价格 */
	private String price;
	/** 会员价格 */
	private String memberPrice;
	/** 商品类型 */
	private Long goodsTypeId;
	/** 邮寄方式 */
	private Integer mailMethod;
	/** 邮费 */
	private String mailPrice;
	/** 是否提供发票 */
	private Integer invoice;
	/** 详情描述 */
	private String comments;
	/** 状态 */
	private Integer status;

	@ApiModelProperty(hidden=true)
	private GoodsType  goodsType;

	public void setGoodsType(GoodsType goodsType) 
	{
		this.goodsType = goodsType;
	}

	public GoodsType getGoodsType() 
	{
		return goodsType;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("name", getName())
			.append("description", getDescription())
			.append("pictureUrl1", getPictureUrl1())
			.append("pictureUrl2", getPictureUrl2())
			.append("pictureUrl3", getPictureUrl3())
			.append("pictureUrl4", getPictureUrl4())
			.append("pictureUrl5", getPictureUrl5())
			.append("pictureUrl6", getPictureUrl6())
			.append("pictureUrl7", getPictureUrl7())
			.append("pictureUrl8", getPictureUrl8())
			.append("pictureUrl9", getPictureUrl9())
			.append("price", getPrice())
			.append("memberPrice", getMemberPrice())
			.append("goodsTypeId", getGoodsTypeId())
			.append("mailMethod", getMailMethod())
			.append("mailPrice", getMailPrice())
			.append("invoice", getInvoice())
			.append("comments", getComments())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
