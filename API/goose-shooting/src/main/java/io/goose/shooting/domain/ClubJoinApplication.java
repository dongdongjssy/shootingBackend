package io.goose.shooting.domain;

import io.goose.common.annotation.Excel;
import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 入群申请表 shooting_club_join_application
 *
 * @author goose
 * @date 2020-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClubJoinApplication extends BaseEntity {
   private static final long serialVersionUID = 1L;


   /** 主键 */
   private Long id;
   private String ids;
   /** 申请者 */
   private Long clientUserId;
   @Excel(name = "申请者")
   private String clientUserName;
   /** 申请加入的俱乐部 */
   private Long clubId;
   @Excel(name = "申请加入的俱乐部")
   private String clubName;

   /** 状态 */
   private Integer status;
   @Excel(name = "状态")
   private String statusName;


   @ApiModelProperty(hidden = true)
   private ClientUser clientUser;

   public void setClientUser(ClientUser user) {
      this.clientUser = user;
   }

   public ClientUser getClientUser() {
      return clientUser;
   }

   @ApiModelProperty(hidden = true)
   private Club club;

   public void setClub(Club club) {
      this.club = club;
   }

   public Club getClub() {
      return club;
   }

   public String toString() {
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clientUserId", getClientUserId())
            .append("clubId", getClubId())
            .append("status", getStatus())
            .toString();
   }
}
