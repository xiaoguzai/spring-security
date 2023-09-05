package com.example.service.impl;

import com.example.domain.LoginUser;
import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.service.LoginService;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
//Impl需要注入容器，因此加上@Service注解
public class LoginServiceImpl implements LoginService {

    //AuthenticationManager使用@Bean进行注解，因此这里我们需要定义AuthenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(@RequestBody User user) {
        //获取AuthenticationManager authentication方法进行用户认证
        //这里登录需要把用户名和密码封装成AuthenticationManager，
        //放入authenticate函数之中
        System.out.println("ResponseResult login user = "+user.toString());
        System.out.println("User = "+user.getUserName()+" "+user.getPassword());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        System.out.println("!!!after authenticationToken!!!");
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //第一次调用LoginUser getAuthorities
        System.out.println("!!!after authenticate");
        //让我们的ProviderManager帮助我们进行操作，这里ProviderManager最终会调用一个UserDetailsServiceImpl中的loadUserByUsername方法
        //根据ProviderManager查询数据库，因为UserDetailsServiceImpl implements UserDetailsService
        //而UserDetailsService是spring security自定义的接口

        System.out.println("UsernamePasswordAuthenticationToken = "+authenticationToken.toString());
        //第二次调用LoginUser getAuthorities
        //System.out.println("authentication = "+authentication.toString());

        //如果认证没通过，给出对应提示
        if(Objects.isNull(authentication))
        {
            throw new RuntimeException("登录失败");
        }
        System.out.println("放入redis中保存");
        //如果认证通过，使用userId生成一个jwt，jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        System.out.println("after getPrincipal");
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //System.out.println("loginUser = "+loginUser.toString());
        System.out.println("userid = "+userid);
        System.out.println("jwt = "+jwt);
        //把完整的用户信息存入redis之中，userId作为key，
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入redis，userId作为key，这里面的键值对键为token
        //值为jwt
        redisCache.setCacheObject("login:"+userid,loginUser);
        //这里必须打开redis，才能够保存得上
        System.out.println("finish");
        return new ResponseResult(200,"登录成功",map);
    }

    @Override
    public ResponseResult logout() {
        System.out.println("ResponseResult loginout");
        //获取SecurityContextHolder中的用户id
        //这里不需要删除SecurityContextHolder中的值，
        // 因为这里是一个单独的请求，其他请求访问过来又是一个新的请求，使用一个新的SecurityContextHolder
        // 其他请求在使用key获取的时候已经获取不到了，因此不会把token的值存进SecurityContextHolder

        //这里可以获取到SecurityContextHolder的值，因为会先调用JwtAuthenticationTokenFilter
        //而在JwtAuthenticationTokenFilter中会将LoginUser存入到SecurityContextHolder存入到
        //SecurityContextHolder之中，然后它会进行放行，才放行到注销登录的接口中
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //如果loginUser没有值的情况下会被前面拦截器拦截下来
        Long userid = loginUser.getUser().getId();

        //删除redis中的值
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"注销成功");
    }
}
