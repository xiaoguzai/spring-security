package com.example.controller;

import com.example.domain.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 用户表user
 * id   user_name
 *  1      sg
 *  2      sg2
 *
 * 权限表menu
 * id  menu_name       perm_key
 * 1    删除图书        sys:book:delete
 * 2   查看图书列表      sys:book:list
 *
 * 角色表role
 * id     name        role_key
 * 1    图书管理员       admin
 * 2      借阅人        reader
 *
 * 角色表跟权限表是多对多的关系，角色表可以有
 * 多个权限，权限表也可以对应多个角色因此这里我们拉出一个
 * 角色权限关联表role_menu
 * role_id  menu_id
 *    1        1
 *    1        2
 *    2        2
 *
 * 一个角色可以对应多个用户，比如一个角色图书管理员可以
 * 由多人(多个用户)承担，同时一个用户可以是多个角色，
 * 一个用户也可以对应多个角色，比如一个角色既可以是图书管理员，
 * 又可以是借阅人，也是多对多的关系，因此这里我们再次引入一个
 * 关联表
 * 用户角色关联表
 * user_id  role_id
 *    1        1
 *    1        2
 *    2        2
 *
 *
 *
 * 完整的数据库表结构
 *     用户表 user                                                        权限表 menu
 *   id    user_name                                            id   menu_name    perm_key
 *    1       sg                                                1     删除图书    sys:book:delete
 *    2       sg2                                               2   查看图书列表   sys:book:list
 *
 *
 *  用户角色关联表 user_role            角色表 role                   角色权限关联表 role_menu
 *  user_id role_id            id      name      role_key         role_id     menu_id
 *    1        1               1     图书管理员    admin               1           1
 *    1        2               2       借阅人     reader              1           2
 *    2        2                                                     2           2
 */

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
    //@PreAuthorize("hasAuthority('system:test:list')")
    @PreAuthorize("@ex.hasAuthority('system:test:list')")
    //!!!调用容器中的名字带ex的类

    //这里授权失败可以改为@PreAuthorize("hasAuthority('system:test:list111')")
    //多个权限：@PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')")
    //@hasRole这个字段会把我们传入的参数拼接上ROLE_之后再去比较，因此要求用户对应的权限也要有
    //ROLE_这个前缀才可以，@hasAnyRole同理
    //@PreAuthorize("hasAnyRole('admin','system:dept:list')")
    //此时重新登录，在请求头加上token之后

    //这里@PreAuthorize后面的hasAuthority可以看出来是一个方法的调用
    //'system:test:list'是方法的参数，调用的是SecurityExpressionRoot.java
    //中的hasAuthority函数
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
