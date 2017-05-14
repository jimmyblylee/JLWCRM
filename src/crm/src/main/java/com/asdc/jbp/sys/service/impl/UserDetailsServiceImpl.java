/**
 * Project Name : jbp-features-sys <br>
 * File Name : UserDetailsServiceImpl.java <br>
 * Package Name : com.asdc.jbp.sys.service.impl <br>
 * Create Time : Aug 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ClassName : UserDetailsServiceImpl <br>
 * Description : demo for UserDetailsService <br>
 * Create Time : Aug 10, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Service("jbpUserDetailsService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserDetailsServiceImpl implements UserDetailsService {

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("ShouldGetFromDB", "ShouldGetFromDB", Arrays.asList(new SimpleGrantedAuthority("ShouldGetFromDB")));
    }

}
