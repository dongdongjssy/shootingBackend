package io.goose.shooting.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.goose.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 赛事表 shooting_contest
 *
 * @author goose
 * @date 2020-06-03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class Contest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    private String ids;
    /**
     * 地区
     */
    private Long areaId;
    @Excel(name = "地区")
    private String areaName;
    /**
     * 科目
     */
    @Excel(name = "科目")
    private Long courseId;

    /**
     * 赛事级别ID
     */
    private Long levelId;
    @Excel(name = "赛事级别ID")
    private String levelName;
    /**
     * 射击类别
     */
    private Long typeId;
    @Excel(name = "射击类别")
    private String typeName;

    /**
     * 赛事名称
     */
    @Excel(name = "赛事名称")
    private String title;
    /**
     * 图片网址
     */
    private String imageUrl;
    /**
     * 开始日期
     */
    @Excel(name = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    private String startTime;
    /**
     * 结束日期
     */
    @Excel(name = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 价格
     */
    @Excel(name = "价格")
    private String price;
    /**
     * 地址
     */
    private String address;
    /**
     * 活动内容
     */
    private String content;

    private String shortContent;
    /**
     * 排名
     */
    private Integer rankings;
    /**
     * 状态
     */
    private Integer status;
    @Excel(name = "状态")
    private String statusName;
    /** 发布状态 */
    private Integer releaseStatus;

    private String color;

    /**是否推荐*/
    private Integer recommend;
    /**是否为草稿*/
    private Integer draft;
    /** 付款码 */
    private Long paymentCodeId;

    /**用户id*/
    private Long clientUserId;



    @ApiModelProperty(hidden = true)
    private Area area;
    @ApiModelProperty(hidden = true)
    private Course course;
    @ApiModelProperty(hidden = true)
    private Type type;
    @ApiModelProperty(hidden = true)
    private ContestLevelCoeff contestLevelCoeff;
    @ApiModelProperty(hidden=true)
    private PaymentCode  paymentCode;
    /**
     * 报名截止日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollDeadline;

    private List<Schedule> schedules;

    private RegisterConfig registerConfig;

    public void setArea(Area area) {
        this.area = area;
    }


    public Area getArea() {
        return area;
    }


    public void setCourse(Course course) {
        this.course = course;
    }


    public Course getCourse() {
        return course;
    }


    public void setType(Type type) {
        this.type = type;
    }


    public Type getType() {
        return type;
    }


    public void setContestLevelCoeff(ContestLevelCoeff contestLevelCoeff) {
        this.contestLevelCoeff = contestLevelCoeff;
    }


    public ContestLevelCoeff getContestLevelCoeff() {
        return contestLevelCoeff;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
                .append("areaId", getAreaId()).append("courseId", getCourseId())
                .append("typeId", getTypeId()).append("title", getTitle())
                .append("imageUrl", getImageUrl()).append("startDate", getStartDate())
                .append("endDate", getEndDate()).append("price", getPrice())
                .append("address", getAddress()).append("content", getContent())
                .append("rankings", getRankings()).append("status", getStatus())
                .append("createBy", getCreateBy()).append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime())
                .append("levelId", getLevelId()).append("releaseStatus", getReleaseStatus())
                .append("color", getColor()).append("draft", getDraft())
                .append("paymentCodeId", getPaymentCode()).append("recommend", getRecommend())
                .toString();
    }
}
