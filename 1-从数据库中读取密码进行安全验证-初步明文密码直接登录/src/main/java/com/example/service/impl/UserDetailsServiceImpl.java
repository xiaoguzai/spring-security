package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这里的UserDetails是一个接口，因此需要写一个对应的实现类

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户，就抛出异常
        if(Objects.isNull(user))
        {
            throw new RuntimeException("用户名或者密码错误!");
        }
        //因为在UsernamePasswordAuthenticationFilter之后
        //会调用一个ExceptionTranslationFilter，所以只要出现
        //异常都会被捕获到

        //TODO 查询对应的权限信息

        //封装成UserDetails返回，标注类AllArgsConstructor，因此可以直接传入
        return new LoginUser(user);
    }
    //这里实际上是由AbstreactUserDetailsAuthenticationProvider
    //中的DaoAuthenticationProvider去调用loadUserByUsername方法的
    //
    //这里我们需要做的事情跟InMemoryUserDetailsManager是一样的，查询用户
    //然后查询权限信息，然后封装成为UserDetails对象返回
}
