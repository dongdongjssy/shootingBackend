package io.goose.cloud.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Author: goose
 * createAt: 2019/4/1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private final AccessDeniedHandler accessDeniedHandler;

    private final UserDetailsService customUserDetailsService;

    private final JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler,
                             @Qualifier("RestAuthenticationAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler,
                             @Qualifier("CustomUserDetailsService") UserDetailsService customUserDetailsService,
                             JwtAuthenticationTokenFilter authenticationTokenFilter) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationTokenFilter = authenticationTokenFilter;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.customUserDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }


    /**
     * 装载BCrypt密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
                //.cors().and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // 对于获取token的rest api要允许匿名访问  "/remit/sms/sendSms4Register",
                .antMatchers(
                        "/monitor/bus-refresh",
                        "/monitor/refresh",
                        "/actuator/refresh",
                        "/actuator",
                        "/actuator/bus-refresh",
                        "/error/**",
                        "/wx/**",
                        "/shooting/auth/**",
                        "/shooting-service/getSignature",
                        "/shooting-service/shooting/recommend/getById/**",
                        "/shooting-service/shooting/startAdvertisement/startList",
                        "/shooting-service/shooting/email/sendEmail",
                        "/shooting-service/shooting/visitor/login",
                        "/shooting-service/shooting/carousel/list",
                        "/shooting-service/shooting/recommend/list",
                        "/map_src/**",
                        "/shooting/sms/**")
                .permitAll()
                //"/remit-service/remit/migration/*").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 添加JWT filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "swagger-ui.html",
                        "**/swagger-ui.html",
                        "/favicon.ico",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.gif",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/**/*.ttf"
                );
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html"
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
