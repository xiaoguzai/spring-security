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
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //让我们的ProviderManager帮助我们进行操作，这里ProviderManager最终会调用一个UserDetailsServiceImpl中的loadUserByUsername方法
        //根据ProviderManager查询数据库，因为UserDetailsServiceImpl implements UserDetailsService
        //而UserDetailsService是spring security自定义的接口

        System.out.println("authentication = "+authentication.toString());

        //如果认证没通过，给出对应提示
        if(Objects.isNull(authentication))
        {
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，使用userId生成一个jwt，jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //把完整的用户信息存入redis之中，userId作为key，
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入redis，userId作为key，这里面的键值对键为token
        //值为jwt
        redisCache.setCacheObject("login:"+userid,loginUser);
        return new ResponseResult(200,"登录成功",map);
    }
}
