package io.goose.shooting.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.goose.common.annotation.Excel;
import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户端用户表 client_user
 *
 * @author goose
 * @date 2020-05-31
 */
@Data
@EqualsAndHashCode( callSuper = false )
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientUser extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /** 用户id */
   private Long id;
   
   private String ids;
   
   /** 用户昵称 */
   @Excel(name = "用户昵称")
   private String nickname;
   /** 用户名 */
   @Excel(name = "用户名")
   private String userName;
   /** 邮箱 */
   @Excel(name = "邮箱")
   private String email;
   /** 手机号 */
   @Excel(name = "手机号")
   private String phone;
   /** 密码 */
   private String password;
   /** 密码 */
   private String newPassword;
   /** 用户类型 */
   private String userType;
   /** 用户微信 */
   private String openId;
   /** 用户头像 */
   private String avatar;
   /** 城市 */
   @Excel(name = "城市")
   private String city;
   /** 地址 */
   private String address;
   /** 密码重置时间 */
   @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
   private Date passwordResetDate;
   /** 认证状态 */
   private Integer status;
   
   @Excel(name = "认证状态")
   private String statusName;
   /** 身份证正面 */
   private String idFront;
   /** 身份证背面 */
   private String idBack;
   /** 身份证号 */
   @Excel(name = "身份证号")
   private String idNumber;
   /** 真实姓名 */
   @Excel(name = "真实姓名")
   private String realName;
   /** 极光用户名 */
   private String jgUsername;
   /** 会员号 */
   @Excel(name = "会员号")
   private String memberId;
   /** 英文名 */
   private String englishName;
   /** 认证有效期 */
   @Excel(name = "认证有效期")
   @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
   private Date certExpireDate;
   /** 证件照片 */
   private String selfieImage;
   /** 结业日期 */
   @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
   private Date graduateDate;
   /** 性别 */
   private Integer sex;
   /** 护照号码 */
   private String passportNo;
   /** 多角色 */
   private String roleIds;
   
   @Excel(name = "角色")
   private String roleNames;
   
   @Excel(name = "所属俱乐部")
   private String myClubNames;
   
   private String myClubIds;

   private List<Role> roles;
   @Excel(name = "用户身份")
   private String userCardStatus;


   /** 年龄 */
   private Integer age;
   
   //血型
   private Integer bloodType; 

   private String introduction;
   
   @Override
   public String toString() {
      return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE ).append( "id", getId() )
            .append( "nickname", getNickname() ).append( "userName", getUserName() )
            .append( "email", getEmail() ).append( "phone", getPhone() )
            .append( "password", getPassword() ).append( "userType", getUserType() )
            .append( "openId", getOpenId() ).append( "avatar", getAvatar() )
            .append( "city", getCity() ).append( "address", getAddress() )
            .append( "passwordResetDate", getPasswordResetDate() ).append( "status", getStatus() )
            .append( "idFront", getIdFront() ).append( "idBack", getIdBack() )
            .append( "idNumber", getIdNumber() ).append( "realName", getRealName() )
            .append( "jgUsername", getJgUsername() ).append( "memberId", getMemberId() )
            .append( "englishName", getEnglishName() )
            .append( "certExpireDate", getCertExpireDate() )
            .append( "selfieImage", getSelfieImage() ).append( "graduateDate", getGraduateDate() )
            .append( "sex", getSex() ).append( "passportNo", getPassportNo() )
            .append( "roleIds", getRoleIds() ) .append( "newPassword", getNewPassword() )
              .append( "age", getAge() ).append( "userCardStatus", getUserCardStatus() ).toString();
   }
}
