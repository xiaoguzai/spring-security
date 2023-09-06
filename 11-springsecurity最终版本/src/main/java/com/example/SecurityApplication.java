package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.mapper")
//配置mapper扫描
@EnableAutoConfiguration
//这里exclude={DataSourceAutoConfiguration.class}会报错
//Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required
//Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
//原因：spring默认需要加上数据，没有数据的情况下会报这个错
public class SecurityApplication {
    public static void main(String[] args) {
        System.out.println("!!!HELLO WORLD!!!");
        SpringApplication.run(SecurityApplication.class,args);
    }
}

/***
 * 报错：There is no PasswordEncoder mapped for the id "null"
 * 这里的PasswordEncoder用于密码校验，如果是明文存储的情况下，需要
 * 在password前面加上{noop}代表是明文存储
 *
 * 注意：登录的时候选择/hello这个接口，此时会自动跳转到登录接口
 *
 * 默认的password加密方式要求数据库中的密码格式为：{id}password，根据id
 * 去判断密码的加密方式，但是我们一般不会使用这种方式，因此需要替换PasswordEncoder，
 *
 * 我们一般使用SpringSecurity为我们提供的BCryptPasswordEncoder，只需要把
 * BCryptPasswordEncoder注入到Spring容器中，SpringSecurity就会使用该
 * PasswordEncoder来进行密码校验，我们可以定义一个SpringSecurity的配置类
 * SpringSecurity要求这个配置类型要继承WebSecurityAdapter
 */


/***
 * 这一版程序在数据库写入用户名xiaomazai，密码为1234
 * 使用BCryptPassword加密之后的结果$2a$10$VDFx9Khqpo4FAkx/NZLL3uZO0PcBZekL3AU5JtzJuuxbn2emZUCUK
 * 然后登录的时候输入用户名xiaomazai和密码1234之后可以登录成功
 */

/***
 * 目前这里没有配置redis已经不会直接报错，而是在日志中写上Bootstrapping Spring Data Redis repositories in DEFAULT mode
 * Finished Spring Data Redis repository scanning in 53 ms.Found 0 Redis repository interfaces.
 */

/***
 * 当SecurityConfig配置好了之后，hello这个接口就
 * 无法被调用了，因为没有权限，返回403
 */

/***
 * 之前没有使用UsernamePasswordAuthenticationFilter，而是自己定义了一个接口
 * 接口当中调用ProviderManager的认证方法，ProviderManager调用DaoAuthenticationProvider，
 * 然后DaoAuthenticationProvider调用InMemoryUserDetailsManager，这之后我们重写了
 * InMemoryUserDetailsManager去查询数据库，然后自己生成了token存入到redis之中
 *
 * 其他方案比如自定义UsernamePasswordAuthenticationFilter，或者UserDetails自己去实现
 * 或者使用登录成功处理器，如果有验证码就需要在UsernamePasswordAuthenticationFilter的
 * 过滤器前面去写
 */











