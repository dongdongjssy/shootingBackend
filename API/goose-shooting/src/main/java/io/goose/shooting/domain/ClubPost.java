package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 俱乐部动态表 shooting_club_post
 *
 * @author goose
 * @date 2020-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClubPost extends BaseEntity {
   private static final long serialVersionUID = 1L;


   /** 主键 */
   private Long id;
   
   private String ids;
   /** 所属俱乐部 */
   private Long clubId;
   @Excel(name = "所属俱乐部")
   private String clubName;
   /** 发布者 */
   private Long clientUserId;
   /** 标题 */
   private String title;
   /** 内容 */
   private String content;
   /** 图集链接1 */
   private String image1;
   /** 图集链接2 */
   private String image2;
   /** 图集链接3 */
   private String image3;
   /** 图集链接4 */
   private String image4;
   /** 图集链接5 */
   private String image5;
   /** 图集链接6 */
   private String image6;
   /** 图集链接7 */
   private String image7;
   /** 图集链接8 */
   private String image8;
   /** 图集链接9 */
   private String image9;
   /** 视频 */
   private String video;
   /** 点赞数 */
   @Excel(name = "点赞数")
   private Integer likeCount;
   /** 评论数 */
   @Excel(name = "评论数")
   private Integer commentCount;
   /** 浏览数 */
   @Excel(name = "浏览数")
   private Integer readCount;
   /** 置顶 */
   private Integer onTop;
   /** 排名 */
   private Integer rankings;
   /** 状态 */
   private Integer status;
   /**是否为草稿*/
   private Integer draft;

   @Excel(name = "状态")
   private String statusName;

   @ApiModelProperty(hidden = true)
   private Club club;

   public void setClub(Club club) {
      this.club = club;
   }

   public Club getClub() {
      return club;
   }

   // not stored in db, only for app client use
   private Boolean isUserLike;
   private Boolean isUserCollect;
   private Boolean isUserRead;
   private Long userId;

   private String contentShort;

   private String videoCover;

   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clubId", getClubId())
            .append("clientUserId", getClientUserId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("image1", getImage1())
            .append("image2", getImage2())
            .append("image3", getImage3())
            .append("image4", getImage4())
            .append("image5", getImage5())
            .append("image6", getImage6())
            .append("image7", getImage7())
            .append("image8", getImage8())
            .append("image9", getImage9())
            .append("video", getVideo())
            .append("likeCount", getLikeCount())
            .append("commentCount", getCommentCount())
            .append("readCount", getReadCount())
            .append("onTop", getOnTop())
            .append("rankings", getRankings())
              .append("draft", getDraft())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
   }
}
