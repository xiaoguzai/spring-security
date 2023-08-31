package com.example.filter;

import com.example.domain.LoginUser;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    //redis的内容直接从容器中获取即可
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal");
        //1.获取token
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token))
        {
            //放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            //这里可以放行的原因在于后面还有FilterSecurityInterceptor等其他过滤器，
            //如果没有认证后面还会被拦截下来
            return;
            //这里如果没有return，响应回来之后还会调用后面的代码
        }

        String userId;
        //2.解析token(响应不为空)
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
            //这里parseJWT就是一个解析过程，不需要过多深究
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //3.从redis中获取用户信息
        String rediskey = "login:"+userId;
        System.out.println("---rediskey = "+rediskey);
        LoginUser loginUser = redisCache.getCacheObject(rediskey);
        System.out.println("---loginUser = "+loginUser.toString());
        if(Objects.isNull(loginUser))
        {
            throw new RuntimeException("用户未登录");
        }
        //前面存入的是loginUser类型，因此这里不需要强转

        //4.存入SecurityContextHolder,这里setAuthentication需要传入
        //TODO 获取权限信息封装到Authentication之中
        //Authentication类，因此loginUser不能直接放入进去，需要转换
        //成Authentication类
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        //!!!这里必须调用带有三个参数的UsernamePasswordAuthenticationToken,因为带有三个参数的UsernamePasswordAuthenticationToken
        //是已经认证过的UsernamePasswordAuthenticationToken
        //第三个属于权限信息
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        //放行之后到下一个过滤器

        //filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
    /***
     * 定义jwt认证过滤器：
     * 获取token、解析token获取其中的userid
     * 从redis中获取用户信息、存入SecurityContextHolder
     * 之前继承filter，可以实现filter接口 implements Filter
     * 默认的implements Filter可能会存在一定的问题，导致一个请求过来会被调用多次
     *
     * 这个过滤器肯定要在FilterSecurityInterceptor前面，否则直接发现未认证之后
     * 就会抛出异常，因此选择放在UsernamePasswordAuthenticationFilter前面的
     * 位置，配置放在继承WebSecurityConfigurerAdapter的SecurityConfig之中
     */
}
