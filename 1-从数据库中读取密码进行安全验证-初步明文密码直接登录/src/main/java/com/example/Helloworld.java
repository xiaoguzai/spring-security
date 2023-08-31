package com.example;

import com.example.utils.FastJsonRedisSerializer;

public class Helloworld {
    public static void main(String[] args) {
        FastJsonRedisSerializer a = new FastJsonRedisSerializer(Integer.class);
        System.out.println(a.serialize(new Integer(5)));
        System.out.println(a.deserialize(a.serialize(new Integer(5))));
    }
}
