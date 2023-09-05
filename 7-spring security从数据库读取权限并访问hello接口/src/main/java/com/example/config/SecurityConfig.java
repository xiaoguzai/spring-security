package com.example.config;

import com.example.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//开启注解功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        //只需要把BCryptPasswordEncoder对象注入Spring容器中
        //SpringSecurity就会使用PasswordEncoder来进行校验
        System.out.println("SecurityConfig passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    //自定义AuthenticationManager
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("###begin configure");
        http
                //关闭csrf
                .csrf().disable()
                //一个.相当于csrf已经配完了
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //sessionManagement()会返回一个SessionManagementConfigurer方法，然后这里可以接着.进行调用
                //使用STATELESS不会取创建HttpSession，当我们使用前后端分离来进行开发后，session已经没用来
                //调用完之后是SessionManagementConfigurer类
                .and()
                //and完成之后是HttpSecurity类
                .authorizeRequests()
                //对于登录接口允许匿名访问
                .antMatchers("/user/login").anonymous()
                //除/user/login接口外的所有请求需要鉴权认证，匿名的意思是未登录可以访问
                //已登录无法访问，请求头携带token不能返回，不携带token可以返回
                //.permitAll()代表无论登录与否都能访问
                .anyRequest().authenticated();
                //anyRequest()代表其他的任意请求
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
    }

    //调用
    //暴露自定义的AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        //System.out.println("###begin authenticationManagerBean");
        System.out.println("SecurityConfig authenticationManagerBean");
        return super.authenticationManagerBean();
    }
}