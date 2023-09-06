package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
//开启跨域请求，注意跨域请求不能用postman测试出来，因为postman不存在跨域问题，需要在浏览器中发起请求
    @Override
    public void addCorsMappings(CorsRegistry registry){
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名(允许任意的请求地址)
                .allowedOriginPatterns("*")
                //是否允许cookie(允许任意的cookie)
                .allowCredentials(true)
                //设置允许请求的方式
                .allowedMethods("GET","POST","DELETE","PUT")
                //设置允许的header属性
                .allowedHeaders("*")
                //跨域允许的时间
                .maxAge(3600);
    }
}
