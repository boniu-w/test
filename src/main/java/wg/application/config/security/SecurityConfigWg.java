// package wg.application.config.security;
//
// import org.springframework.context.annotation.Configuration;
//
// /************************************************************************
//  * @author: gpt作品
//  * @description:
//  * @params:
//  * @return:
//  * @createTime: 10:38  2023/4/20
//  * @updateTime: 10:38  2023/4/20
//  ************************************************************************/
// @Configuration
// @EnableWebSecurity
// public class SecurityConfigWg extends WebSecurityConfigurerAdapter {
//
//   // 定义身份验证规则
//   @Override
//   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//     auth.inMemoryAuthentication()
//       .withUser("user1").password("{noop}password1").roles("USER")
//         .authorities(new SimpleGrantedAuthority("USER"))
//       .and()
//       .withUser("user2").password("{noop}password2").roles("USER", "ADMIN")
//         .authorities(
//           new SimpleGrantedAuthority("USER"),
//           new SimpleGrantedAuthority("ADMIN")
//         )
//       .and().withUser("admin").password("{noop}admin123").roles("ADMIN");
//   }
//
//   // 定义授权规则
//   // 假设只有 ADMIN 角色可以访问 /admin/**
//   // USER 和 ADMIN 角色都可以访问 /public/**
//   // 未经身份验证的用户可以访问 /public/**，但不能访问其他页面
//   // 其他所有页面都需要身份验证
//   // 登录页是 /login
//   // 登出页是 /logout
//   // 登录成功后重定向到 /home
//   // 登出成功后重定向到 /public
//   @Override
//   protected void configure(HttpSecurity http) throws Exception {
//     http.authorizeRequests()
//       .antMatchers("/admin/**").hasRole("ADMIN")
//       .antMatchers("/public/**").hasAnyRole("ADMIN", "USER")
//       .antMatchers("/public/**").permitAll()
//       .anyRequest().authenticated()
//       .and()
//       .formLogin().loginPage("/login").defaultSuccessURL("/home").permitAll()
//       .and()
//       .logout().logoutUrl("/logout").logoutSuccessUrl("/public").permitAll();
//   }
// }