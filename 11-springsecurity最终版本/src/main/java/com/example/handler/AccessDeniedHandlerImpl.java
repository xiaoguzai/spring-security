package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.domain.ResponseResult;
import com.example.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//授权失败的实现类
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //这里需要做的事情与刚才类似，因此可以直接复制
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "您的权限不足");
        String json = JSON.toJSONString(result);
        //处理异常
        WebUtils.renderString(httpServletResponse,json);
    }
}

/***
 * UsernamePasswordAuthenticationFilter.class查看调用
 * 源码的方法，进入UsernamePasswordAuthenticationFilter.class
 * 类之中，public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter
 * 进入AbstractAuthenticationProcessingFilter的父类中，查看成功时的调用
 * this.successHandler.onAuthenticationSuccess(request, response, authResult);
 * 进入到AuthenticationSuccessHandler接口之中
 */