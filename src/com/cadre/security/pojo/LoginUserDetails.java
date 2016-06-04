/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.security.pojo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.cadre.model.dictionary.Dictionary;
import com.cadre.pojo.DictionaryDb;
import com.cadre.pojo.SysResource;
import com.cadre.pojo.SysRole;


/**
 * @Type: com.cadre.security.pojo.LoginUserDetails.java
 * @ClassName: LoginUserDetails
 * @Description: <br/>
 */
public class LoginUserDetails extends User {
	private static final long serialVersionUID = 1L;

	public LoginUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
//		System.out.println("username:"+username+"!!!psw:"+password);
	}
	
	public LoginUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username,password,authorities);
    }
	
	@Override
    public boolean equals(Object o) {
        if (o instanceof LoginUserDetails) {
            return getUsername().equals(((LoginUserDetails) o).getUsername());
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }
    
	/**
	 * 系统用户
	 */
	private com.cadre.pojo.User user;
	
	/**
	 * 用户角色列表
	 */
	private List<SysRole> roles;
	/**
	 * 用户资源列表
	 */
	private List<SysResource> resources;
	/**
	 * 用户选择资源
	 */
	private List<DictionaryDb> dictionarys;

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<SysResource> getResources() {
		return resources;
	}

	public void setResources(List<SysResource> resources) {
		this.resources = resources;
	}

	public com.cadre.pojo.User getUser() {
		return user;
	}

	public void setUser(com.cadre.pojo.User user) {
		this.user = user;
	}

	public List<DictionaryDb> getDictionarys() {
		return dictionarys;
	}

	public void setDictionarys(List<DictionaryDb> dictionarys) {
		this.dictionarys = dictionarys;
	}
	
}
