package com.example.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils{

    /***
     * 将字符串渲染到客户端，说白了就是前端接收数据
     * 需要把待接收的数据放到HttpServletResponse这个类之中
     * @param response  渲染对象
     * @param string    待渲染的字符串
     * @return
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
