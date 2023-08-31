package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //登录，这里登录的时候需要传入用户名和密码
        //!!!注意!!!这里必须传入User参数变量之后才能调用这个函数
        //如果post中没有User这个变量的时候是无法进入到这个函数之中的
        //System.out.println("begin login");
        return loginService.login(user);
        //调用LoginServiceImpl中的login方法
    }
    /***
     * 1.登录的时候先调用JwtAuthenticationTokenFilter，此时第一次登录直接放行
     * 然后调用LoginServiceImpl中的login方法，这里在login中直接返回结果200了
     * 因此不需要后面的认证
     * 2.接下来测试使用token直接登录的方式，
     * (1)先头中不加token，此时
     * http://localhost:8080/hello会返回403，因为SecurityContextHolder
     * 中获取不到认证的状态
     * (2)然后在请求头中加上之前登录获取的token，此时
     */

    //login接口一般会携带用户名和密码，并且一般采用post的形式

    //退出登录：1..删除redis中的数据
    //获取SecurityContextHolder中的认证信息，删除redis中的对应用户信息
    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
    //先调用正常登录接口，然后调用注销接口
    //这里需要重点体会的是：1.为什么不删除SecurityContextHolder中的用户信息?
    //2.为什么SecurityContextHolder可以取得信息
    /***
     * 解释：同一个接口共用一个SecurityContextHolder，而不同的接口
     * 使用不同的SecurityContextHolder，因此在/user/logout中传入了
     * token，所以这里的SecurityContextHolder可以拿到token的信息
     *
     * 但是由于不同的接口不共用一个SecurityContextHolder，因此
     * SecurityContextHolder不需要删除之前的信息，只需要之前的
     * redis删除掉信息即可
     */
}
