package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 报名表 shooting_register
 *
 * @author goose
 * @date 2020-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Register extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;
    private String ids;
    /** 外键 */
    private Long fkId;
    /** 外键表 */
    private Integer fkTable;
    /** 报名者 */
    private Long clientUserId;
    private String source;
    private String schedules;
    /** 状态 */
    private Integer status;
    @Excel(name = "活动名称")
    private String actName;
    @Excel(name = "活动类别")
    private String typeName;
    @Excel(name = "报名者")
    private String clientUserName;
    @Excel(name = "状态")
    private String statusName;

    @ApiModelProperty(hidden = true)
    private ClientUser clientUser;

    private List<RegisterFormItem> registerFormItems;

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fkId", getFkId())
                .append("fkTable", getFkTable())
                .append("clientUserId", getClientUserId())
                .append("status", getStatus())
                .toString();
    }
}
