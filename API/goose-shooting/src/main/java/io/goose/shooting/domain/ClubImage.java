package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 俱乐部图片表 shooting_club_image
 * 
 * @author goose
 * @date 2020-05-01
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class ClubImage extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /** 主键 */
   private Long id;
   /** 俱乐部 */
   private Long clubId;
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

   @ApiModelProperty( hidden = true )
   private Club club;


   public void setClub( Club club ) {
      this.club = club;
   }


   public Club getClub() {
      return club;
   }


   public String toString() {
      return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE ).append( "id", getId() )
            .append( "clubId", getClubId() ).append( "image1", getImage1() )
            .append( "image2", getImage2() ).append( "image3", getImage3() )
            .append( "image4", getImage4() ).append( "image5", getImage5() )
            .append( "image6", getImage6() ).append( "image7", getImage7() )
            .append( "image8", getImage8() ).append( "image9", getImage9() ).toString();
   }
}
