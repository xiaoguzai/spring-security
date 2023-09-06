package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /***
         * super.configure(http);
         * 默认进入configure函数之中，可以看到有
         * http.formLogin();帮助我们生成登录表单
         * 并且也会把UsernamePasswordFilter添加
         * 到配置中，什么都没有配置的时候会有Username
         * PasswordFilter
         */
        http.formLogin()
                //配置认证成功处理器
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        http.logout()
                //配置注销成功处理器
                .logoutSuccessHandler(logoutSuccessHandler);

        //这里hello接口需要被保护到
        http.authorizeRequests().anyRequest().authenticated();
    }
}
