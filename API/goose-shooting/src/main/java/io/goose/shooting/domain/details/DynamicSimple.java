package io.goose.shooting.domain.details;

import java.util.Date;


public class DynamicSimple {

   private Long id;
   private String dynamicType;
   private String category;
   private String title;
   private String content;

   private String image1;
   private String image2;
   private String image3;
   private String image4;
   private String image5;
   private String image6;
   private String image7;
   private String image8;
   private String image9;
   private String video;

   private Integer likeCount;
   private Integer commentCount;
   private Integer readCount;
   private Integer onTop;
   private Integer rankings;

   private Date createDateTime;
   // this is string format for createDateTime, for UI display only
   private String createTime;

   private ClientUser clientUser;


   public ClientUser getClientUser() {
      return clientUser;
   }


   public void setClientUser( ClientUser clientUser ) {
      this.clientUser = clientUser;
   }


   /** 广告 */
   private String adTitle;
   private String adContent;
   private String adMediaUrl;


   public String getDynamicType() {
      return dynamicType;
   }


   public void setDynamicType( String dynamicType ) {
      this.dynamicType = dynamicType;
   }


   public Long getId() {
      return id;
   }


   public void setId( Long id ) {
      this.id = id;
   }


   public String getCategory() {
      return category;
   }


   public void setCategory( String category ) {
      this.category = category;
   }


   public String getTitle() {
      return title;
   }


   public void setTitle( String title ) {
      this.title = title;
   }


   public String getContent() {
      return content;
   }


   public void setContent( String content ) {
      this.content = content;
   }


   public String getImage1() {
      return image1;
   }


   public void setImage1( String image1 ) {
      this.image1 = image1;
   }


   public String getImage2() {
      return image2;
   }


   public void setImage2( String image2 ) {
      this.image2 = image2;
   }


   public String getImage3() {
      return image3;
   }


   public void setImage3( String image3 ) {
      this.image3 = image3;
   }


   public String getImage4() {
      return image4;
   }


   public void setImage4( String image4 ) {
      this.image4 = image4;
   }


   public String getImage5() {
      return image5;
   }


   public void setImage5( String image5 ) {
      this.image5 = image5;
   }


   public String getImage6() {
      return image6;
   }


   public void setImage6( String image6 ) {
      this.image6 = image6;
   }


   public String getImage7() {
      return image7;
   }


   public void setImage7( String image7 ) {
      this.image7 = image7;
   }


   public String getImage8() {
      return image8;
   }


   public void setImage8( String image8 ) {
      this.image8 = image8;
   }


   public String getImage9() {
      return image9;
   }


   public void setImage9( String image9 ) {
      this.image9 = image9;
   }


   public String getVideo() {
      return video;
   }


   public void setVideo( String video ) {
      this.video = video;
   }


   public Integer getLikeCount() {
      return likeCount;
   }


   public void setLikeCount( Integer likeCount ) {
      this.likeCount = likeCount;
   }


   public Integer getCommentCount() {
      return commentCount;
   }


   public void setCommentCount( Integer commentCount ) {
      this.commentCount = commentCount;
   }


   public Integer getReadCount() {
      return readCount;
   }


   public void setReadCount( Integer readCount ) {
      this.readCount = readCount;
   }


   public Integer getOnTop() {
      return onTop;
   }


   public void setOnTop( Integer onTop ) {
      this.onTop = onTop;
   }


   public Integer getRankings() {
      return rankings;
   }


   public void setRankings( Integer rankings ) {
      this.rankings = rankings;
   }


   public Date getCreateDateTime() {
      return createDateTime;
   }


   public void setCreateDateTime( Date createDateTime ) {
      this.createDateTime = createDateTime;
   }


   public String getCreateTime() {
      return createTime;
   }


   public void setCreateTime( String createTime ) {
      this.createTime = createTime;
   }


   public String getAdTitle() {
      return adTitle;
   }


   public void setAdTitle( String adTitle ) {
      this.adTitle = adTitle;
   }


   public String getAdContent() {
      return adContent;
   }


   public void setAdContent( String adContent ) {
      this.adContent = adContent;
   }


   public String getAdMediaUrl() {
      return adMediaUrl;
   }


   public void setAdMediaUrl( String adMediaUrl ) {
      this.adMediaUrl = adMediaUrl;
   }


   public class ClientUser {

      private Long id;
      private String nickname;
      private String avatar;
      private String roleName;
      private String roleDesciption;


      public Long getId() {
         return id;
      }


      public void setId( Long id ) {
         this.id = id;
      }


      public String getNickname() {
         return nickname;
      }


      public void setNickname( String nickname ) {
         this.nickname = nickname;
      }


      public String getAvatar() {
         return avatar;
      }


      public void setAvatar( String avatar ) {
         this.avatar = avatar;
      }


      public String getRoleName() {
         return roleName;
      }


      public void setRoleName( String roleName ) {
         this.roleName = roleName;
      }


      public String getRoleDesciption() {
         return roleDesciption;
      }


      public void setRoleDesciption( String roleDesciption ) {
         this.roleDesciption = roleDesciption;
      }

   }

}
