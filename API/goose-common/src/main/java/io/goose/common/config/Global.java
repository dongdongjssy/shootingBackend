package io.goose.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Global {

   public Global() {
   }


   @Value( "${goose.name}" )
   private String name;

   @Value( "${goose.version}" )
   private String version;

   @Value( "${goose.copyrightYear}" )
   private String copyrightYear;

   @Value( "${goose.addressEnabled}" )
   private Boolean addressEnabled;

   @Value( "${goose.profile}" )
   private String profile;

   @Value( "${goose.shooting}" )
   private String shooting;

   @Value( "${goose.clientUser}" )
   private String subpathClientUser;

   @Value( "${goose.carousel}" )
   private String subpathCarousel;

   @Value( "${goose.recommend}" )
   private String subpathRecommend;

   @Value( "${goose.post}" )
   private String subpathPost;

   @Value( "${goose.training}" )
   private String subpathTraining;

   @Value( "${goose.contest}" )
   private String subpathContest;

   @Value( "${goose.advertisement}" )
   private String subpathAdvertisement;

   @Value( "${goose.startAdvertisement}" )
   private String subpathStartAdvertisement;

   @Value( "${goose.paymentCode}" )
   private String subpathPaymentCode;

   @Value( "${goose.club}" )
   private String subpathClub;

   @Value( "${goose.clubPost}" )
   private String subpathClubPost;

   @Value( "${goose.clubActivity}" )
   private String subpathClubActivity;

   @Value( "${goose.publish}" )
   private String subpathPublish;

   @Value( "${goose.coach}" )
   private String subpathCoach;

   @Value( "${goose.judge}" )
   private String subpathJudge;

   @Value( "${goose.import}" )
   private String pathImport;

   @Value( "${goose.message}" )
   private String subpathMessage;

   @Value( "${goose.goods}" )
   private String subpathGoods;

   @Value( "${goose.order}" )
   private String subpathOrder;

   @Value( "${goose.goodsCarousel}" )
   private String subpathGoodsCarousel;
   
   
   @Value("${goose.basePic}")
	private String basePic;
   
   public String getBasePicLocation() {
   	return basePic;
   }
   
   /**
    * 获取项目名称
    */
   public String getName() {
      return name;
   }


   /**
    * 获取项目版本
    */
   public String getVersion() {
      return version;
   }


   /**
    * 获取版权年份
    */
   public String getCopyrightYear() {
      return copyrightYear;
   }


   /**
    * 获取文件上传路径
    */
   public String getProfile() {
      return profile;
   }


   /**
    * 获取头像上传路径
    */
   public String getAvatarPath() {
      return getProfile() + "avatar/";
   }


   /**
    * 获取下载上传路径
    */
   public static String getDownloadPath() {
      // TODO
      return "C:/" + "download/";
   }


   /**
    * 获取ip地址开关
    */
   public static Boolean isAddressEnabled() {
      // TODO
      return true;
   }


   public String getShooting() {
      return shooting;
   }


   public String getMediaPathClientUser() {
      return shooting + subpathClientUser;
   }


   public String getMediaPathCarousel() {
      return shooting + subpathCarousel;
   }


   public String getMediaPathRecommend() {
      return shooting + subpathRecommend;
   }


   public String getMediaPathPost() {
      return shooting + subpathPost;
   }


   public String getMediaPathTraining() {
      return shooting + subpathTraining;
   }


   public String getMediaPathContest() {
      return shooting + subpathContest;
   }

   public String getMediaPathMessage() {
	      return shooting + subpathMessage;
	   }

   public String getMediaPathAdvertisement() {
      return shooting + subpathAdvertisement;
   }

   public String getMediaPathStartAdvertisement() {
      return shooting +subpathStartAdvertisement;
   }

   public String getMediaPathPaymentCode() {
      return shooting +subpathPaymentCode;
   }

   public String getMediaPathClub() {
      return shooting + subpathClub;
   }


   public String getMediaPathClubPost() {
      return shooting + subpathClubPost;
   }


   public String getMediaPathClubActivity() {
      return shooting + subpathClubActivity;
   }


   public String getMediaPathPublish() {
      return shooting + subpathPublish;
   }


   public String getMediaPathCoach() {
      return shooting + subpathCoach;
   }


   public String getMediaPathJudge() {
      return shooting + subpathJudge;
   }


   public String getMediaPathImport() {
      return pathImport;
   }

   public String getMediaPathGoods() {
      return shooting + subpathGoods;
   }

   public String getMediaPathOrder() {
      return shooting + subpathOrder;
   }

   public String getMediaPathGoodsCarousel() {
      return shooting + subpathGoodsCarousel;
   }

}
