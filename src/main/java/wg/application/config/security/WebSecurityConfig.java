// package wg.application.config.security;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.security.web.authentication.AuthenticationFailureHandler;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.security.web.authentication.logout.LogoutHandler;
// import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
// import org.springframework.security.web.session.InvalidSessionStrategy;
// import wg.application.enumeration.CodeEnum;
// import wg.application.ioc.dao.UserDao;
// import wg.application.service.UserService;
// import wg.application.vo.ResponseMsg;
// import wg.application.vo.Result;
//
// import javax.annotation.Resource;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.HashMap;
// import java.util.Map;
//
// /************************************************************************
//  * @author: wg
//  * @description:
//  * @params:
//  * @return:
//  * @createTime: 15:14  2022/3/30
//  * @updateTime: 15:14  2022/3/30
//  ************************************************************************/
// @Configuration
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//     @Autowired
//     private UserService userService;
//
//     @Bean
//     public PasswordEncoder getPasswordEncoder() {
//         //前后端分离要注入一个PasswordEncoder来给successHandler使用
//         return new BCryptPasswordEncoder();
//     }
//
//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         super.configure(auth);
//         auth.userDetailsService(userService);
//     }
//
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests()
//                 .antMatchers("/admin/**")
//                 .hasRole("ADMIN")
//                 .antMatchers("/user/**")
//                 .access("hasAnyRole('ADMIN','USER')")
//                 .antMatchers("/db/**")
//                 .hasRole("DBA")
//                 .antMatchers("/register")
//                 .permitAll()
//                 .anyRequest()//其他请求登录后可访问
//                 .authenticated()
//                 .and()
//                 .formLogin()
//                 //.loginPage("/login") 前后端分离，前端不能收到302
//                 .loginProcessingUrl("/user/login")
//                 .usernameParameter("username")
//                 .passwordParameter("password")
//                 .successHandler(new AuthenticationSuccessHandler() {
//                     @Override
//                     public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                         httpServletResponse.setContentType("application/json;charset=utf-8");
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         Map map = new HashMap();
//                         map.put("authenties", authentication.getAuthorities());
//                         map.put("name", authentication.getName());
//                         ResponseMsg rsp = new ResponseMsg(200, "ok", map);
//                         PrintWriter out = httpServletResponse.getWriter();
//                         out.write(objectMapper.writeValueAsString(rsp));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .failureHandler(new AuthenticationFailureHandler() {
//                     @Override
//                     public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                         httpServletResponse.setContentType("application/json;charset=utf-8");
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         ResponseMsg rsp = new ResponseMsg(500, "fail", e.getMessage());
//                         PrintWriter out = httpServletResponse.getWriter();
//                         out.write(objectMapper.writeValueAsString(rsp));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .permitAll()
//                 .and()
//                 .sessionManagement()
//                 //.invalidSessionUrl("/session/invalid")//session过期后跳转的url
//                 .invalidSessionStrategy(new InvalidSessionStrategy() {
//                     @Override
//                     public void onInvalidSessionDetected(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         Result result = new Result(CodeEnum.SUCCESS.getCode(), "invalid -wg");
//                         PrintWriter out = httpServletResponse.getWriter();
//                         out.write(objectMapper.writeValueAsString(result));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .and()
//                 .logout()
//                 .logoutUrl("/logout")
//                 .clearAuthentication(true)
//                 .invalidateHttpSession(true)
//                 .addLogoutHandler(new LogoutHandler() {
//                     @Override
//                     public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
//                         //此处可做一些清除工作
//                     }
//                 })
//                 .logoutSuccessHandler(new LogoutSuccessHandler() {
//                     @Override
//                     public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                         ResponseMsg rsp = new ResponseMsg(200, "ok", "登出成功");
//                         PrintWriter out = httpServletResponse.getWriter();
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         out.write(objectMapper.writeValueAsString(rsp));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .permitAll()
//                 .and()
//                 .exceptionHandling()
//                 .accessDeniedHandler(new AccessDeniedHandler() {
//                     @Override
//                     public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//                         PrintWriter out = httpServletResponse.getWriter();
//                         ResponseMsg rsp = new ResponseMsg(401, "fail", "权限不足");
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         out.write(objectMapper.writeValueAsString(rsp));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                     //前后端分离前端不会302重定向，所以要重写AuthenticationEntryPoint
//
//                     @Override
//                     public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                         ObjectMapper objectMapper = new ObjectMapper();
//                         Result result = new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getDescription());
//                         PrintWriter out = httpServletResponse.getWriter();
//                         out.write(objectMapper.writeValueAsString(result));
//                         out.flush();
//                         out.close();
//                     }
//                 })
//                 .and()
//                 .csrf()
//                 .disable()
//                 .cors();
//     }
// }