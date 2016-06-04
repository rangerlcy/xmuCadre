/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.security.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cadre.security.pojo.LoginUserDetails;
import com.cadre.model.utils.DigestUtil;


/**
 * @Type: com.cadre.security.service.LoginAuthenticationProvider.java
 * @ClassName: LoginAuthenticationProvider
 * 
 */
@Component("loginAuthenticationProvider")
public class LoginAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	Logger LOG = LogManager.getLogger(LoginAuthenticationProvider.class);
	@Autowired
	private UserDetailsService loginUserDetailServiceImpl;

	@Autowired
	UserInfoLoadFacade userInfoLoadFacade;
	/**
	 * 验证用户密码
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		LOG.debug("LoginAuthenticationProvider#additionalAuthenticationChecks:验证用户登陆密码....");

		if (authentication.getCredentials() == null ||
				StringUtils.isBlank(authentication.getCredentials().toString())) {
			logger.debug("LoginAuthenticationProvider#additionalAuthenticationChecks:提供验证的密码为空，验证失败...");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","密码为空，验证失败"),
					userDetails);
		}

		String presentedPassword = authentication.getCredentials().toString();
		System.out.println("!!!!!1111"+presentedPassword);				//这个是前端获取到的密码
		System.out.println("!!!!!2222"+userDetails.getPassword());		//这个是数据库中存的密码
		if(!StringUtils.equals(userDetails.getPassword(), DigestUtil.encryptPWD(presentedPassword))) {
			logger.debug("LoginAuthenticationProvider#additionalAuthenticationChecks:密码不匹配，验证失败");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials","密码不匹配，验证失败"), 
					userDetails);
		}
		
		LOG.debug("到此，用户权限认证成功，开始加载用户了详细信息");
		userInfoLoadFacade.loadUserAllInfo((LoginUserDetails)userDetails);
	}

	/**
	 * 获取登录用户信息
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		LOG.debug("LoginAuthenticationProvider#retrieveUser:用户登录....");
		UserDetails loadedUser;
		try {
			loadedUser = loginUserDetailServiceImpl.loadUserByUsername(username);
		} catch (UsernameNotFoundException notFound) {
			LOG.error("LoginAuthenticationProvider#retrieveUser:根据用户名找不到登录的用户....");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.notFound",notFound.getMessage()), 
					null);
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.notFound","用户不存在"), 
					loadedUser);
		}
		return loadedUser;
	}

}
