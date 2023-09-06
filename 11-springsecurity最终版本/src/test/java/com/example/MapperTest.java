package com.example;

import com.example.domain.User;
import com.example.mapper.MenuMapper;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/***
 * 要写测试类需要引入相关依赖
 * <dependency>
 *     <groupId>org.springframework.boot</groupId>
 *     <artifactId>spring-boot-starter-test</artifactId>
 * </dependency>
 *
 * 注意这个文件需要放在com.example的下面
 */
@SpringBootTest
public class MapperTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void TestBCryptPasswordEncoder(){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //这里没有必要采用new的方式，直接采用注入容器的方式即可

        //System.out.println(passwordEncoder.
        //        matches("12345",
        //                ""));

        String encode = passwordEncoder.encode("12345");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode);

        System.out.println(passwordEncoder.
                matches("12345",
                "$2a$10$06YRJgS2xdBlBHLC9XOyEOsClYBL8ULd57OaBcbc4wuu9cLXjxtzS"));
    }

    @Test
    public void TestMenuMapper(){
        List<String> results = menuMapper.selectPermsByUserId(2L);
        for(int i=0;i<results.size();i++)
        {
            System.out.println(results.get(i));
        }
    }
}
