package org.o7.planning.sbsocial.utils;

import java.util.List;

import org.o7.planning.sbsocial.entity.AppUser;
import org.o7.planning.sbsocial.social.SocialUserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

public class SecurityUtils {

	//auto login

	public static void loginUser(AppUser appUser, List<String> roleNames) {

		SocialUserDetails userDetails = new SocialUserDetailsImpl(appUser, roleNames);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
