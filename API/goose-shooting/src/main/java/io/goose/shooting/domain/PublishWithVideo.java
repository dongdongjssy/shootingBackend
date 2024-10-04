package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 视频发布表 shooting_publish_with_video
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PublishWithVideo extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /** 主键 */
   private Long id;
   /** 用户 */
   private Long clientUserId;
   /** 内容 */
   private String content;
   /** 视频 */
   private String video;
   /** 排名 */
   private Integer rankings;
   /** 状态 */
   private Integer status;

   @ApiModelProperty( hidden = true )
   private ClientUser clientUser;


   public void setClientUser( ClientUser clientUser ) {
      this.clientUser = clientUser;
   }


   public ClientUser getClientUser() {
      return clientUser;
   }


   public String toString() {
      return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE ).append( "id", getId() )
            .append( "clientUserId", getClientUserId() ).append( "content", getContent() )
            .append( "video", getVideo() ).append( "rankings", getRankings() )
            .append( "status", getStatus() ).toString();
   }
}
