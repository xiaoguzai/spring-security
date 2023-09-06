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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//开启注解功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //因为AuthenticationEntryPoint被注入到容器之中了，这里我们可以直接使用
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

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
        /***
         * 关闭csrf是为了防范网站的csrf攻击，先在A网站产生了cookie
         * 然后在没有登出A网站的情况下访问危险网站B，此时B要求访问
         * 第三方站点A会发出一个request请求进行一些比如转账等敏感操作，
         * 根据B的request请求，由于之前A网站已经产生了cookie，因此此时会
         * 携带着之前的cookie进行操作
         *
         * 防范：后端会生成一个csrf_token，前端发起请求的时候需要携带csrf_token
         * 前端发起请求的时候需要携带这个csrf_token，后端会有过滤器进行校验，
         * 如果没有携带或者是伪造就不允许访问了
         * 前后端天然携带csrf，因此这里的csrf就可以关闭了
         */
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
                .antMatchers("/testCors").hasAuthority("system:test:list")
                .anyRequest().authenticated();
                //anyRequest()代表其他的任意请求
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);

        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                //配置响应失败处理器
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
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