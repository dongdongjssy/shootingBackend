package io.goose.common.enums;

public enum UploadTypeEnums {
	
	   Recommend(1, "shooting/recommend"),
	   Advertisement(2, "shooting/advertisement"),
	   Carousel(3, "shooting/carousel"),
	   Club(4, "shooting/club"),
	   ClubActivity(5, "shooting/clubActivity"),
	   ClubPost(6, "shooting/clubPost"),
	   Contest(7, "shooting/contest"),
	   Message(8, "shooting/message"),
	   Post(9, "shooting/post"),
	   Publish(10, "shooting/publish"),
	   Training(11, "shooting/training"),
	   ClientUser(12,"shooting/clientUser"),
	   Zh(13,"shooting/zh"),
	   Coach(14,"shooting/coach"),
	   Judge(15,"shooting/judge"),
	   StartAdvertisement(16, "shooting/startAdvertisement"),
	   PaymentCode(17, "shooting/paymentCode"),
	   Honor(18, "shooting/honor"),
	   Role(19, "shooting/role"),
	   Goods(20,"shooting/goods"),

	   GoodsCarousel(21,"shooting/goodsCarousel"),
	Order(22,"shooting/order");


	   private final Integer code;
	   private final String value;

	   UploadTypeEnums(Integer code, String value) {
	      this.code = code;
	      this.value = value;
	   }

	   public Integer getCode() {
	      return code;
	   }

	   public String getValue() {
	      return value;
	   }
}
