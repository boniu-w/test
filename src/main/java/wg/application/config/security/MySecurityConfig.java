package wg.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/*************************************************************
 * @Package wg.application.config
 * @author wg
 * @date 2020/8/4 11:39
 * @version
 * @Copyright
 *************************************************************/
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @return 密码加密工具
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // 去掉 CSRF
//        http.csrf().disable()
//          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
//          .and()
//
//          .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
//
//          .and()
//          .authorizeRequests()//定义哪些URL需要被保护、哪些不需要被保护
//
//          .antMatchers("/doc.html").permitAll()
//          .antMatchers("/swagger-ui.html").permitAll()
//          .antMatchers("/swagger**/**").permitAll()
//          .antMatchers("/webjars/**").permitAll()
//          .antMatchers("/v2/**").permitAll()
//          .antMatchers("/druid/**.**").permitAll()
//          .antMatchers("/druid/index.html").permitAll()
//          .antMatchers("/**/excelImport").permitAll()
//          //开发模拟登陆接口（默认为admin）
//          .antMatchers("/test/GetToken").permitAll()
// //                .antMatchers("/sys/my/**").permitAll()
//
//          .anyRequest()//任何请求,登录后可以访问
// //                .access("@rbacauthorityservice.hasPermission(request,authentication)") // RBAC 动态 url 认证
//          .authenticated()  // 除上面外的所有请求全部需要鉴权认证
//
//          .and()
//          .formLogin()  //开启登录, 定义当需要用户登录时候，转到的登录页面
// //                .loginPage("/test/login.html")
// //                .loginProcessingUrl("/login")
//          .successHandler(authenticationSuccessHandler) // 登录成功
//          .failureHandler(authenticationFailureHandler) // 登录失败
//          .permitAll()
//
//          .and()
//          .logout()//默认注销行为为logout
//          .logoutUrl("/logout")
//          .logoutSuccessHandler(logoutSuccessHandler)
//          .permitAll();
//
//        // 记住我
//        http.rememberMe().rememberMeParameter("remember-me")
//          .userDetailsService(userDetailsService).tokenValiditySeconds(1000);
//
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
//        //UsernamePasswordAuthenticationFilter.class的obtainUsername用于接收前端参数
//        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter
//
//    }

}
