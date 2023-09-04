package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl loadUserByUsername");
        //这里的UserDetails是一个接口，因此需要写一个对应的实现类

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户，就抛出异常
        System.out.println("user = "+user.toString());
        if(Objects.isNull(user))
        {
            throw new RuntimeException("用户名或者密码错误!");
        }
        //因为在UsernamePasswordAuthenticationFilter之后
        //会调用一个ExceptionTranslationFilter，所以只要出现
        //异常都会被捕获到

        //TODO 查询对应的权限信息
        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));


        //封装成UserDetails返回，标注类AllArgsConstructor，因此可以直接传入
        return new LoginUser(user,list);
    }
    //这里实际上是由AbstreactUserDetailsAuthenticationProvider
    //中的DaoAuthenticationProvider去调用loadUserByUsername方法的
    //
    //这里我们需要做的事情跟InMemoryUserDetailsManager是一样的，查询用户
    //然后查询权限信息，然后封装成为UserDetails对象返回

    /***
     * Spring栈溢出报错：https://blog.csdn.net/qq_35425070/article/details/108915454
     * 下面一个讲解较好：https://www.cnblogs.com/tyleaf/p/16919093.html
     */
}
