package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    //自带的表并不满足需求，因为这里进行的是多表的联合查询，因此需要自定义方法
    //如果这里没有自定义方法，则使用的就是BaseMapper的默认方法
    List<String> selectPermsByUserId(Long userId);
}
//注意xml文件写好了之后需要配置，告诉xml文件在什么地方，在resources下面的application.yml配置
//如果放在mapper下面的MenuMapper文件中，则可以不用配置，因为MybatisPlusProperties默认就
//放在mapper下面的MenuMapper中的MenuMapper文件中
//写完MenuMapper之后最好在test文件夹下面测试一下
//查到之后只需要把UserDetailsServiceImpl.java中的
//List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
//修改成从数据库中查询的即可