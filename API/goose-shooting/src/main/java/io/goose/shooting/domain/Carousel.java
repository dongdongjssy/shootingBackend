package io.goose.shooting.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;





/**
 * 轮播媒体表 shooting_carousel
 * 
 * @author goose
 * @date 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Carousel extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	
	/** 主键 */
	private Long id;
	/** 所在页面 */
	private Integer onPage;
	/** 标题 */
	private String title;
	/** 描述 */
	private String subTitle;
	/** 媒体网址 */
	private String mediaUrl;
	/** 轮播图类别 */
	private Integer mediaType;
	/** 排名 */
	private Integer rankings;
	/** 状态 */
	private Integer status;
	/** 所属俱乐部 */
	private Long clubId;
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
	/**是否为草稿*/
	private Integer draft;

	@ApiModelProperty(hidden=true)
	private Club  club;
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

	public void setClub(Club club) 
	{
		this.club = club;
	}

	public Club getClub() 
	{
		return club;
	}    
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
			.append("onPage", getOnPage())
			.append("title", getTitle())
			.append("subTitle", getSubTitle())
			.append("mediaUrl", getMediaUrl())
			.append("mediaType", getMediaType())
			.append("rankings", getRankings())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.append("clubId", getClubId())
			.append("contestId", getContestId())
			.append("trainingId", getTrainingId())
			.append("coachId", getCoachId())
			.append("judgeId", getJudgeId())
			.append("recommendId", getRecommendId())
			.append("clubPostId", getClubPostId())
			.append("draft", getDraft())
			.append("clubActivityId", getClubActivityId())
			.toString();
    }
}
