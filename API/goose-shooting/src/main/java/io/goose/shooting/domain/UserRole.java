package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户端用户角色关联表 client_user_role
 * 
 * @author goose
 * @date 2020-06-20
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class UserRole extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /**  */
   private Integer id;
   /** 用户 */
   private Long userId;
   /** 角色 */
   private Integer roleId;
   /** 多角色 */
   private String roleIds;

   @ApiModelProperty( hidden = true )
   private ClientUser clientUser;
   @ApiModelProperty( hidden = true )
   private Role role;


   public void setClientUser( ClientUser clientUser ) {
      this.clientUser = clientUser;
   }


   public ClientUser getClientUser() {
      return clientUser;
   }


   public void setRole( Role role ) {
      this.role = role;
   }


   public Role getRole() {
      return role;
   }


   @Override
   public String toString() {
      return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE ).append( "id", getId() )
            .append( "userId", getUserId() ).append( "roleId", getRoleId() )
            .append( "roleIds", getRoleIds() ).toString();
   }
}
