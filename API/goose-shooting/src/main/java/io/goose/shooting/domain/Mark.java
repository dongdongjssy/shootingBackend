package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 成绩表 shooting_mark
 * 
 * @author goose
 * @date 2020-04-28
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class Mark extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /** 主键 */
   private Long id;
   /** 用户 */
   private Long clientUserId;
   /** 成绩 */
   private Integer mark;

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
            .append( "clientUserId", getClientUserId() ).append( "mark", getMark() ).toString();
   }
}
