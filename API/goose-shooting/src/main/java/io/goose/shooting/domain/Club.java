package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 俱乐部表 shooting_club
 *
 * @author goose
 * @date 2020-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Club extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private Long id;
    
    private String ids;
    /** 地区 */
    private Long areaId;
    @Excel(name = "地区")
    private String areaName;
    /** 名称 */
    @Excel(name = "名称")
    private String title;
    /** 简介 */
    private String profile;
    /** 用户头像 */
    private String avatar;
    /** 图片网址 */
    private String image;
    /** 教官数 */
    @Excel(name = "教官数")
    private Integer coachCount;
    /** 学员数 */
    @Excel(name = "学员数")
    private Integer studentCount;
    /** 粉丝数 */
    @Excel(name = "粉丝数")
    private Integer fansCount;
    /** 俱乐部类别 */
    @Excel(name = "俱乐部类别")
    private Integer category;
    /** 排名 */
    private Integer rankings;
    /** 状态 */

    private Integer status;
    @Excel(name = "状态")
    private String statusName;
    /** 极光用户名 */
    private String jgUsername;
    /** 极光群组名 */
    private String jgGroupId;

    private Boolean isUserFollow;

    private transient String jgGroupName;
    private transient Long jgGroupAdminId;

    @ApiModelProperty(hidden = true)
    private Area area;

    public void setArea(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("areaId", getAreaId())
                .append("areaName", getAreaName())
                .append("title", getTitle())
                .append("profile", getProfile())
                .append("avatar", getAvatar())
                .append("image", getImage())
                .append("coachCount", getCoachCount())
                .append("studentCount", getStudentCount())
                .append("fansCount", getFansCount())
                .append("category", getCategory())
                .append("rankings", getRankings())
                .append("status", getStatus())
                .append("statusName", getStatusName())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("jgUsername", getJgUsername())
                .append("jgGroupId", getJgGroupId())
                .toString();
    }
}
