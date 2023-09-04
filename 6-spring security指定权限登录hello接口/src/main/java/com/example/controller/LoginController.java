package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.domain.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//讲解教程https://blog.csdn.net/qq_22075913/article/details/125148535
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

    //在SpringSecurity中会使用默认的FilterSecurityInterceptor来
    //进行权限校验，在FilterSecurityInterceptor中会从SecurityContextHolder
    //获取其中的Authentication，然后获取其中的权限信息，当前用户是否
    //拥有当前资源所需的权限，所以我们在项目中只需要把当前登录用户的权限信息
    //也存入Authentication，然后设置我们资源需要的权限即可。
    //在UserDetails中查询对应的权限信息

    /***
     * 增加两点：1.loadUserByUsername中查询对应的权限信息，存入到LoginUser中来
     * 2.在认证过滤器中拿到权限信息也要传到authenticationToken中去
     * UsernamePasswordAuthenticationToken authenticationToken =
     * new UsernamePasswordAuthenticationToken(loginUser,null,null);
     * 权限设置可以通过api注解实现
     *
     * 登录的时候使用在LoginUser中,校验的时候在JwtAuthenticationTokenFilter之中
     *
     * 然后需要配置UserDetails的实现类LoginUser的里面的getAuthorities函数
     * 进行权限的配置，
     */
}
