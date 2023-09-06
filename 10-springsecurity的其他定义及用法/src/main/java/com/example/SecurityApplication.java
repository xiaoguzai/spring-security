package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
//SGSuccessHandler为认证成功的处理器，注意认证成功的处理器必须在configure中配置
//http.formLogin().successHandler(successHandler);
//之前写的没有配置successHandler，因此认证成功处理器无法使用

/***
 * 失败接口的调用：先查看WebSecurityConfigurerAdapter.class
 * 这里包含了一个configure函数
 * protected void configure(HttpSecurity http) throws Exception {
 *     this.logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
 *     http.authorizeRequests((requests) -> {
 *         ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
 *     });
 *     http.formLogin();
 *     http.httpBasic();
 * }
 */

/***
 * 找到成功接口调用的部分：查看UsernamePasswordAuthenticationFilter.class之中
 * 找到父类AbstractAuthenticationProcessingFilter，查看doFilter方法
 * private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
 *     if (!this.requiresAuthentication(request, response)) {
 *         chain.doFilter(request, response);
 *     } else {
 *         try {
 *             Authentication authenticationResult = this.attemptAuthentication(request, response);
 *             if (authenticationResult == null) {
 *                 return;
 *             }
 *
 *             this.sessionStrategy.onAuthentication(authenticationResult, request, response);
 *             if (this.continueChainBeforeSuccessfulAuthentication) {
 *                 chain.doFilter(request, response);
 *             }
 *
 *             this.successfulAuthentication(request, response, chain, authenticationResult);
 *        } catch (InternalAuthenticationServiceException var5) {
 *             this.logger.error("An internal error occurred while trying to authenticate the user.", var5);
 *             this.unsuccessfulAuthentication(request, response, var5);
 *        } catch (AuthenticationException var6) {
 *             this.unsuccessfulAuthentication(request, response, var6);
 *        }
 *    }
 * }
 * 这里调用成功的时候this.successfulAuthentication(request, response, chain, authenticationResult);
 * 调用失败的时候this.unsuccessfulAuthentication(request, response, var5);
 * 之前经过我们配置之后已经没有UsernamePasswordAuthenticationFilter了
 */