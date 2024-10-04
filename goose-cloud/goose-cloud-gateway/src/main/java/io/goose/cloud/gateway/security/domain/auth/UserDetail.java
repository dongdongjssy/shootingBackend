package io.goose.cloud.gateway.security.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author : goose
 * createAt: 2019/4/1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetail implements UserDetails {

   private User user;
   private boolean expired;

   public UserDetail(User user) {
      this.user = user;
   }

   public UserDetail(Long id, String username, Role role, String password) {
      user = new User();
      user.setId(id);
      user.setUserName(username);
      user.setPassword(password);

      if (role != null) {
         Set<Role> roles = new HashSet<Role>();
         roles.add(role);
         user.setRoles(roles);
      }
   }

   public UserDetail(String username, String password, Role role) {
      user = new User();
      user.setUserName(username);
      user.setPassword(password);
      Set<Role> roles = new HashSet<Role>();
      roles.add(role);
      user.setRoles(roles);
   }

   //返回分配给用户的角色列表
   @JsonIgnore
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      List<GrantedAuthority> authorities = new ArrayList<>();
      //      Iterator<Role> iter = user.getRoles().iterator();
      //      while (iter.hasNext()) {
      //         String roleName = iter.next().getName();
      //         authorities.add(new SimpleGrantedAuthority(roleName));
      //      }

      authorities.add(new SimpleGrantedAuthority(user.getUserName()));

      return authorities;
   }

   public long getId() {
      return user.getId();
   }

   @Override
   public String getPassword() {
      return user.getPassword();
   }

   @Override
   public String getUsername() {
      return user.getUserName();
   }

   /**
    * 账户是否未过期
    */
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   /**
    * 账户是否未锁定
    */
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   /**
    * 密码是否未过期
    */
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   /**
    * 账户是否激活
    */
   @Override
   public boolean isEnabled() {
      return true;
   }

   @JsonIgnore
   public Date getLastPasswordResetDate() {
      return user.getPasswordResetDate();
   }

   public void setLastPasswordResetDate(Date lastPasswordResetDate) {
      user.setPasswordResetDate(lastPasswordResetDate);
   }

   @JsonIgnore
   public Role getRole() {
      return user.getRoles().iterator().next();
   }

   public void setRole(Role role) {
      user.getRoles().clear();
      user.getRoles().add(role);
   }

   //    public void setId(Long id) {
   //        user.setId(id);
   //    }

   public void setUsername(String username) {
      user.setUserName(username);
   }

   public void setPassword(String password) {
      user.setPassword(password);
   }


   public User getUser() {
      return user;
   }

   public boolean isExpired() {
      return expired;
   }

   public void setExpired(boolean expired) {
      this.expired = expired;
   }


}
