package io.goose.common.enums;

/**
 * 客户端用户状态
 */
public enum ClientUserStatus {
   IN_ACTIVE(-1, "停用"),
   NOT_VERIFIED(0, "未认证"),
   IN_REVIEW(1, "审核中"),
   VERIFIED(2, "已认证");

   private final Integer code;
   private final String value;

   ClientUserStatus(Integer code, String value) {
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
