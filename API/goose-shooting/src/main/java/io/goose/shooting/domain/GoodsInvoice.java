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
 * 发票表 shooting_goods_invoice
 * 
 * @author goose
 * @date 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GoodsInvoice extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 用户id */
	private Long clientUserId;
	/** 发票类型 */
	private Integer invoiceType;
	/** 发票抬头 */
	private String head;
	/** 发票税号 */
	private String code;
	/** 开户行 */
	private String bank;
	/** 银行账号 */
	private String bankAccount;
	/** 企业地址 */
	private String address;
	/** 企业电话 */
	private String phone;
	/** 是否默认发票 */
	private Integer defaultInvoice;
	/** 状态 */
	private Integer status;

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
			.append("invoiceType", getInvoiceType())
			.append("head", getHead())
			.append("code", getCode())
			.append("bank", getBank())
			.append("bankAccount", getBankAccount())
			.append("address", getAddress())
			.append("phone", getPhone())
			.append("defaultInvoice", getDefaultInvoice())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
