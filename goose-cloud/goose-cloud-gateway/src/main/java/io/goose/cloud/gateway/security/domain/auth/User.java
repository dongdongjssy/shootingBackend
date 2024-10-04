package io.goose.cloud.gateway.security.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "client_user")
public class User {

   /** 用户id */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   /** 用户昵称 */
   private String nickname;
   /** 用户名 */
   @Column(unique = true)
   private String userName;
   /** 邮箱 */
   @Column(unique = true)
   private String email;
   /** 手机号 */
   @Column(unique = true)
   private String phone;
   /** 密码 */
   private String password;
   /** 用户类型 */
   private String userType;
   /** 用户微信 */
   private String openId;
   /** 用户头像 */
   private String avatar;
   /** 城市 */
   private String city;
   /** 地址 */
   private String address;
   /** 密码重置时间 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date passwordResetDate;
   /** 认证状态 */
   private Integer status;
   /** 身份证正面 */
   private String idFront;
   /** 身份证背面 */
   private String idBack;
   /** 身份证号 */
   @Column(unique = true)
   private String idNumber;
   /** 真实姓名 */
   private String realName;
   /** 极光用户名 */
   @Column(unique = true)
   private String jgUsername;
   /** 会员号 */
   private String memberId;
   /** 英文名 */
   private String englishName;
   /** 认证有效期 */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date certExpireDate;

   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date graduateDate;

   private Integer sex;

   private String roleIds;

   @Transient
   private String smsCode;

   @Transient
   private Set<Role> roles;

   public String toString() {
      return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
              .append("id", getId())
              .append("nickname", getNickname())
              .append("userName", getUserName())
              .append("email", getEmail())
              .append("phone", getPhone())
              .append("password", getPassword())
              .append("userType", getUserType())
              .append("openId", getOpenId())
              .append("avatar", getAvatar())
              .append("city", getCity())
              .append("address", getAddress())
              .append("passwordResetDate", getPasswordResetDate())
              .append("status", getStatus())
              .append("idFront", getIdFront())
              .append("idBack", getIdBack())
              .append("idNumber", getIdNumber())
              .append("realName", getRealName())
              .append("jgUsername", getJgUsername())
              .append("memberId", getMemberId())
              .append("englishName", getEnglishName())
              .append("certExpireDate", getCertExpireDate())
              .toString();
   }
}
