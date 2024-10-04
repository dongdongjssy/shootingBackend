package io.goose.common.enums;

/**
 * 关注类型
 */
public enum UserFollowTypes {
   USER(0, "user"),
   CLUB(1, "club");

   private final Integer code;
   private final String value;

   UserFollowTypes(Integer code, String value) {
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
