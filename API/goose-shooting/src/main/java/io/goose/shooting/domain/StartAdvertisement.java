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
 * 启动页广告表 shooting_start_advertisement
 * 
 * @author goose
 * @date 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class StartAdvertisement extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 描述 */
	private String describes;
	/** 广告类型 */
	private Integer advertisementType;
	/** 上传时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date uploadTime;
	/** 生效时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date effectTime;
	/** 失效时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date failureTime;
	/** 状态 */
	private Integer advertisementStatus;
	/** 是否跳转 */
	private Integer jump;
	/** 跳转类型 */
	private Integer jumpType;
	/** 内部关联 */
	private Integer connectType;
	/** 关联的赛事 */
	private Long contestId;
	/** 关联的培训 */
	private Long trainingId;
	/** 关联的教官 */
	private Long coachId;
	/** 关联的裁判 */
	private Long judgeId;
	/** 关联的总会推荐 */
	private Long recommendId;
	/** 关联的俱乐部动态 */
	private Long clubPostId;
	/** 关联的俱乐部赛事 */
	private Long clubActivityId;
	/** 外部链接 */
	private String mediaUrl;
	/** 广告详情 */
	private String detail;
	/** 图片网址 */
	private String pictureUrl;
	/** 状态 */
	private Integer status;

	@ApiModelProperty(hidden=true)
	private Contest  contest;
	@ApiModelProperty(hidden=true)
	private Training  training;
	@ApiModelProperty(hidden=true)
	private RecommendCoach  recommendCoach;
	@ApiModelProperty(hidden=true)
	private RecommendJudge  recommendJudge;
	@ApiModelProperty(hidden=true)
	private Recommend  recommend;
	@ApiModelProperty(hidden=true)
	private ClubPost  clubPost;
	@ApiModelProperty(hidden=true)
	private ClubActivity  clubActivity;

	public void setContest(Contest contest) 
	{
		this.contest = contest;
	}

	public Contest getContest() 
	{
		return contest;
	}    
	public void setTraining(Training training) 
	{
		this.training = training;
	}

	public Training getTraining() 
	{
		return training;
	}    
	public void setRecommendCoach(RecommendCoach recommendCoach) 
	{
		this.recommendCoach = recommendCoach;
	}

	public RecommendCoach getRecommendCoach() 
	{
		return recommendCoach;
	}    
	public void setRecommendJudge(RecommendJudge recommendJudge) 
	{
		this.recommendJudge = recommendJudge;
	}

	public RecommendJudge getRecommendJudge() 
	{
		return recommendJudge;
	}    
	public void setRecommend(Recommend recommend) 
	{
		this.recommend = recommend;
	}

	public Recommend getRecommend() 
	{
		return recommend;
	}    
	public void setClubPost(ClubPost clubPost) 
	{
		this.clubPost = clubPost;
	}

	public ClubPost getClubPost() 
	{
		return clubPost;
	}    
	public void setClubActivity(ClubActivity clubActivity) 
	{
		this.clubActivity = clubActivity;
	}

	public ClubActivity getClubActivity() 
	{
		return clubActivity;
	}    

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("describes", getDescribes())
			.append("advertisementType", getAdvertisementType())
			.append("uploadTime", getUploadTime())
			.append("effectTime", getEffectTime())
			.append("failureTime", getFailureTime())
			.append("advertisementStatus", getAdvertisementStatus())
			.append("jump", getJump())
			.append("jumpType", getJumpType())
			.append("connectType", getConnectType())
			.append("contestId", getContestId())
			.append("trainingId", getTrainingId())
			.append("coachId", getCoachId())
			.append("judgeId", getJudgeId())
			.append("recommendId", getRecommendId())
			.append("clubPostId", getClubPostId())
			.append("clubActivityId", getClubActivityId())
			.append("mediaUrl", getMediaUrl())
			.append("detail", getDetail())
			.append("pictureUrl", getPictureUrl())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
    }
}
