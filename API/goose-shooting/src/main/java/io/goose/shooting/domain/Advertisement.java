package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 广告表 shooting_advertisement
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Advertisement extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	private String ids;
	/** 标题 */
	private String title;
	/** 详情 */
	private String detail;
	/** 媒体网址 */
	private String mediaUrl;
	/** 媒体类别 */
	private Integer mediaType;
	/** 排名 */
	private Integer rankings;
	/** 状态 */
	private Integer status;
	
	private String url;



	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("title", getTitle())
			.append("detail", getDetail())
			.append("mediaUrl", getMediaUrl())
			.append("mediaType", getMediaType())
			.append("rankings", getRankings())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
