package com.example.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SGSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("认证成功");
    }
    //有一个default是默认方法，不重写也可以，另外一个是抽象方法
    /***
    * UsernamePasswordAuthenticationFilter.class查看调用
    * 源码的方法，进入UsernamePasswordAuthenticationFilter.class
    * 类之中，public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter
    * 进入AbstractAuthenticationProcessingFilter的父类中，查看成功时的调用
    * this.successHandler.onAuthenticationSuccess(request, response, authResult);
    * 进入到AuthenticationSuccessHandler接口之中
    */
}
