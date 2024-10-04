package io.goose.shooting.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.goose.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 客户端角色表 client_role
 * 
 * @author goose
 * @date 2020-05-09
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class Role extends BaseEntity {

   private static final long serialVersionUID = 1L;

   /**  */
   private Integer id;
   
   private String ids;
   /** 角色 */
   private String name;
   /** 描述 */
   private String description;
   /** 图片 */
   private String pictureUrl;
   /** 排序 */
   private Integer sort;


   @Override
   public String toString() {
      return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE ).append( "id", getId() )
            .append( "name", getName() )
           .append("pictureUrl", getPictureUrl())
           .append("sort", getSort())
           .append( "description", getDescription() ).toString();
   }
}
