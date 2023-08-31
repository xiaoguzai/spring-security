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
        SpringApplication.run(SecurityApplication.class,args);
    }
}

/***
 * 报错：There is no PasswordEncoder mapped for the id "null"
 * 这里的PasswordEncoder用于密码校验，如果是明文存储的情况下，需要
 * 在password前面加上{noop}代表是明文存储
 *
 * 注意：登录的时候选择/hello这个接口，此时会自动跳转到登录接口
 */
