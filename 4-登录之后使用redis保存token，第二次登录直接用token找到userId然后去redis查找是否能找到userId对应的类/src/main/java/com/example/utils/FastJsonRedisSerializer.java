package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.util.Assert;
import java.nio.charset.Charset;

/**
 * Redis使用FastJson序列化
 *
 * @author sg
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T>
{

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    static
    {
        //静态变量、静态方法、静态常量统称为类的静态成员，归整个类所有
        //不属于某个单一的对象
        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //全局变量设置为这个
    }

    public FastJsonRedisSerializer(Class<T> clazz)
    {
        super();
        this.clazz = clazz;
    }

    //重新写序列化方法，改写成JSON格式，方便通信与读写
    @Override
    public byte[] serialize(T t) throws SerializationException
    {
        if (t == null)
        {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
        /***
         toJSONString(t,SerializerFeature.WriteClassName)中
         WriteClassName:序列化写入类型信息，默认为false
         普通的JSON文本不知道它是什么类型，比如{"id":12,"name":魏嘉留}
         传入的JSON文本{"@type":"com.alibaba.demo.Employee","id":12,"name":魏嘉留}
         ***/
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException
    {
        if (bytes == null || bytes.length <= 0)
        {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        //DEFAULT_CHARSET为上面定义的UTF-8
        //System.out.println("clazz = "+clazz);
        return JSON.parseObject(str, clazz);
    }

    /***
     protected JavaType getJavaType(Class<?> clazz)
     {
     System.out.println("getJavaType");
     System.out.println("type is"+clazz.toString()+" *** ");
     return TypeFactory.defaultInstance().constructType(clazz);
     }
     ***/
}

