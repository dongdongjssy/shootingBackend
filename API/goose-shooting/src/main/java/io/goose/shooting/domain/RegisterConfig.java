package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 报名配置表 shooting_register_config
 *
 * @author goose
 * @date 2021-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public RegisterConfig() {
    }

    public RegisterConfig(Long fkId, String type) {
        this.fkId = fkId;
        this.registerType = type;
    }

    /**
     * 主键
     */
    private Long id;
    /**
     * 关联项目主键
     */
    private Long fkId;
    /**
     * 报名类型
     */
    private String registerType;
    /**
     * 是否允许未认证用户报名
     */
    private Integer isUnauthAllowed;
    /**
     * 配置的名称
     */
    private String title;

    private List<RegisterConfigPersonalInfo> personalInfos;
    private List<RegisterConfigCustomizedInfo> customizedInfos;
    private List<RegisterConfigRoleInfo> roleInfos;

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fkId", getFkId())
                .append("registerType", getRegisterType())
                .append("isUnauthAllowed", getIsUnauthAllowed())
                .append("title", getTitle())
                .toString();
    }
}
