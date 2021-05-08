package com.spartices;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * 点击登录时会调用该函数、并传入登录名  根据用户名查询数据库获取用户信息
     * @param username：登录用户名
     * @return: 返回用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new org.springframework.security.core.userdetails.User("admin",
                "test",
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
