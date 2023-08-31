package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //加入spring-security包会进入一个
    //默认的拦截页面

    //前后端校验的关键：token，根据token来看
    //是否是需要的用户，登录后再访问其他请求
    //需要在请求头中携带token内容
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
