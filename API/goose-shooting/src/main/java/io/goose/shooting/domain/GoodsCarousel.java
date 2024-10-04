package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 商城轮播图表 shooting_goods_carousel
 * 
 * @author goose
 * @date 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsCarousel extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 是否关联商品 */
	private Integer connectGoods;
	/** 关联的商品 */
	private Long goodsId;
	/** 标题 */
	private String title;
	/** 描述 */
	private String subTitle;
	/** 上传图片 */
	private String mediaUrl;
	/** 状态 */
	private Integer status;

	private Goods goods;

	private String goodsName;

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("connectGoods", getConnectGoods())
			.append("goodsId", getGoodsId())
			.append("title", getTitle())
			.append("subTitle", getSubTitle())
			.append("mediaUrl", getMediaUrl())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
