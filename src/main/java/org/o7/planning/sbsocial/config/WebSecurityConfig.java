package org.o7.planning.sbsocial.config;

import org.o7.planning.sbsocial.entity.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		//pages that do not require login
		http.authorizeRequests().antMatchers("/", "/signup", "login", "logout").permitAll();
		
		http.authorizeRequests().antMatchers("/userInfo").access("hasRole('"+AppRole.ROLE_USER+"')");
		
		//for admin only
		http.authorizeRequests().antMatchers("/admin").access("hasRole('"+AppRole.ROLE_ADMIN+"')");
		
		//when user has role xx
		//but access role that require role yy
		//AccessDeniedException will be thrown
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		//form login config
		http.authorizeRequests().and().formLogin()//
			//submit url of login page
			.loginProcessingUrl("/j_spring_security_check")//submit url
			.loginPage("/login")//
			.defaultSuccessUrl("/userInfo")//
			.failureUrl("/login?error=true")//
			.usernameParameter("username")//
			.passwordParameter("password");
		
		//logout config
		http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
		
		//spring social config
		http.apply(new SpringSocialConfigurer()).signupUrl("/signup");
		
	}
	
	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

}
