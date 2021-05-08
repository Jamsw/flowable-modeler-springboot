package com.spartices;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().authenticated()    //都需要身份认证
                .and().
                formLogin()     // 或者httpBasic()
                 // 指定自定义form表单请求的路径
                // 必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                // 这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                //默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
                .csrf().disable();
    }
}
