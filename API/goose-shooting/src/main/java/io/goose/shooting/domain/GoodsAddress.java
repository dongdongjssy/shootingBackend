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
 * 收货地址表 shooting_goods_address
 * 
 * @author goose
 * @date 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsAddress extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户id */
	private Long clientUserId;
	/** 收货人 */
	private String name;
	/** 手机号 */
	private String phone;
	/** 街道 */
	private String street;
	/** 省份 */
	private String province;
	/** 市 */
	private String city;
	/** 区 */
	private String area;
	/** 区号 */
	private String areaNumber;
	/** 收货地址 */
	private String address;
	/** 状态 */
	private Integer status;

	/** 默认地址 */
	private Integer defaultAddress;

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
			.append("clientUserId", getClientUserId())
			.append("name", getName())
			.append("phone", getPhone())
			.append("street", getStreet())
			.append("province", getProvince())
			.append("city", getCity())
			.append("area", getArea())
			.append("areaNumber", getAreaNumber())
			.append("address", getAddress())
			.append("status", getStatus())
				.append("defaultAddress", getDefaultAddress())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
