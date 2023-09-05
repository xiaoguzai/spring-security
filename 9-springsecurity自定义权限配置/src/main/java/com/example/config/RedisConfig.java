package com.example.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.example.utils.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //在之前的FastJson版本中，存在着反序列化漏洞，升级之后解决了反序列化漏洞，但是需要手动开启autoType，autoType
    //默认是关闭的，否则反序列化失败
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }


    //系统中对RedisTemplate默认采用的是JDK序列化机制
    //加入我们不希望采用默认的JDK方式进行序列化，可以
    //对RedisTemplate进行定制，指定自己的序列化方式
    //这里我们采用高级定制的方式，通过FastJsonRedisSerializer
    //对自己对方法进行定义
    @Bean
    @SuppressWarnings(value = {"unchecked","rawtypes"})
    //@SuppressWarnings注解是jse提供的注解。作用是屏蔽一些无关紧要的警告。使开发者能看到一些他们真正关心的警告。从而提高开发者的效率
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        //这里采用自己定义的serializer即FastJsonRedisSerializer进行序列化
        //属于高级定义的方式

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        //高级定制的情况必须修改RedisATemplate对象的默认配置
        template.afterPropertiesSet();
        return template;
    }
}
