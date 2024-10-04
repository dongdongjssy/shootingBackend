package io.goose.cloud.gateway.security.domain.auth;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author : goose
 * createAt: 2019/4/1
 */
@Data
@Builder
@Entity
@Table(name = "client_role")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Size(max = 50)
   private String name;

   private String description;

   @Tolerate
   public Role() {
   }


}
