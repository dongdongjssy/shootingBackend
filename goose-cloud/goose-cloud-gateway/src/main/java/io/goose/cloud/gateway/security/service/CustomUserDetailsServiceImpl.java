package io.goose.cloud.gateway.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.goose.cloud.gateway.security.domain.auth.User;
import io.goose.cloud.gateway.security.domain.auth.UserDetail;
import io.goose.cloud.gateway.security.repo.UserRepository;

/**
 * 登陆身份认证
 * @author: goose
 * createAt: 2019/4/1
 */
@Component(value="CustomUserDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UserRepository userRepo;
   
    @Override
    public UserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User does not exist： '%s'.", name));
        }
        
        return new UserDetail(user);
    }
}
