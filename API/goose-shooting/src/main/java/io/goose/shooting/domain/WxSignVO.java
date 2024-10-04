package io.goose.shooting.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论视图层对象
 * @author yanglei.xyz
 * @Date 2020-02-17 15:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxSignVO {

	private String appId;

	private String timestamp;

	private String nonceStr;

	private String signature;

}
