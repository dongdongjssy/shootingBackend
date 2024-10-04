package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 图片发布表 shooting_publish_with_image
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PublishWithImage extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /** 主键 */
   private Long id;
   /** 用户 */
   private Long clientUserId;
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
            .append( "image1", getImage1() ).append( "image2", getImage2() )
            .append( "image3", getImage3() ).append( "image4", getImage4() )
            .append( "image5", getImage5() ).append( "image6", getImage6() )
            .append( "image7", getImage7() ).append( "image8", getImage8() )
            .append( "image9", getImage9() ).append( "rankings", getRankings() )
            .append( "status", getStatus() ).toString();
   }
}
