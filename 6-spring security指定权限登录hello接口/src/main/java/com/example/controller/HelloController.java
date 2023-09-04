package com.example.controller;

import com.example.domain.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //加入spring-security包会进入一个
    //默认的拦截页面

    //前后端校验的关键：token，根据token来看
    //是否是需要的用户，登录后再访问其他请求
    //需要在请求头中携带token内容

    /***
     * SpringSecurity使用注解取访问对应的资源所需的权限常用注解
     * @PreAuthorize("hasAuthority('test')")，
     * 这里把需要的权限写死了，为test权限
     *
     * 但是使用SpringSecurity我们需要先开启相关配置
     * @EnableGlobalMethodSecurity(prePostEnabled = true)
     *
     * 这个配置加在SecurityConfig类之上
     * @EnableGlobalMethodSecurity(prePostEnabled = true)
     */
    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String hello(){
        System.out.println("!!!Hello!!!");
        return "hello";
    }
    /***
     * 上面这个只是赋予了test权限可以访问，
     * 但是没有赋予用户test的权限，因此目前
     * 还是无法访问hello接口
     *
     * 关键修改：SecurityConfig之中的
     */

}
