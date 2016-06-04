/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cadre.security.pojo.LoginUserDetails;
import com.cadre.pojo.User;
import com.cadre.service.sys.UserService;

/**
 * @Type: com.cadre.security.service.LoginUserDetailServiceImpl.java
 * @ClassName: LoginUserDetailServiceImpl
 * @Description: 用于spring security 用户登录<br/>
 * 
 */
@Component("loginUserDetailServiceImpl")
public class LoginUserDetailServiceImpl implements UserDetailsService{

	@Autowired
	UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		//用户登陆
		User user = userLogin(username);
		if(user != null) {
			LoginUserDetails userDetails = new LoginUserDetails(username,user.getPassWord(), generateSimpleAuth());
			userDetails.setUser(user);
			return userDetails;
		}
		
		//如果程序运行到此，说明登录失败了，只能返回null;
		throw new UsernameNotFoundException("用户不存在");
	}

	/**
	 * 用户登陆
	 * @param username
	 * @return
	 */
	private User userLogin(String username) {
		if(StringUtils.isBlank(username)) {
			return null;
		}
		return userService.findByUsername(username);
		
	}
	
	/**
	 * 产生简单的spring security验证需要的角色。
	 * 可以传递一个user的属性作为该方法的参数，然后根据参数值不同赋予不同角色。
	 * @return
	 */
	private Collection<SimpleGrantedAuthority> generateSimpleAuth(){
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		authorities.add(authority);
		return authorities;
	}
}
