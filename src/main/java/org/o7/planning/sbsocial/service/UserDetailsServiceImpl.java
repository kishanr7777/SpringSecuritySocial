package org.o7.planning.sbsocial.service;

import java.util.ArrayList;
import java.util.List;

import org.o7.planning.sbsocial.dao.AppRoleDAO;
import org.o7.planning.sbsocial.dao.AppUserDAO;
import org.o7.planning.sbsocial.entity.AppUser;
import org.o7.planning.sbsocial.social.SocialUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AppUserDAO appUserDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		System.out.println("UserDetailsServiceImpl.loadByUsername = "+username);
		AppUser appUser = this.appUserDAO.findAppUserByUserName(username);
		
		if(appUser == null){
			System.out.println("Username not found "+ username);
			throw new UsernameNotFoundException("User "+username+" not found in the database");
		}
		System.out.println("Found User: "+ appUser);
		
		//	Role_User, Role_Admin
		List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if(roleNames != null){
			for(String role : roleNames){
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
				grantList.add(grantedAuthority);
			}
		}
		
		SocialUserDetailsImpl userDetails = new SocialUserDetailsImpl(appUser, roleNames);
		return userDetails;
	}

}
