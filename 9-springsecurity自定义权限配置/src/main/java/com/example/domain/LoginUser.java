package com.example.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> permissions;

    public LoginUser(User user,List<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    //redis默认不会将List<SimpleGrantedAuthority> authorities序列化，
    //因为会报异常，所以我们不需要把authorities这个集合存入到redis之中
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("LoginUser getAuthorities");
        //获取权限部分，暂时不管
        //查看GrantedAuthority这个类型，发现这是一个接口，去找寻它的实现类
        //查看实现类的快捷键：ctrl+alt+按住鼠标左键
        //如果是mac电脑则为：win+alt+按住鼠标左键

        //源码部分：调用权限集合，查看是否有我们接口需要的权限
        //有返回true，没有则返回false

        /***
         * 这里查看到了接口的实现
         * public SimpleGrantedAuthority(String role) {
         *     Assert.hasText(role, "A granted authority textual representation is required");
         *     this.role = role;
         * }
         * 说明List<String>可以封装成SimpleGrantedAuthority的类型
         */
        /***
        List<GrantedAuthority> newList = new ArrayList<>();
        for(String permission: permissions){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            newList.add(authority);
        }
         ***/
        if(authorities != null)
        {
            return authorities;
        }
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        //SimpleGrantedAuthority::new使用流的构造器，最后Collectors.toList()将结果转为list类型
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        //是否没过期，返回true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //是否没有超时
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
