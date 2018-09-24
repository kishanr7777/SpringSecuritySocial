package org.o7.planning.sbsocial.social;

import org.o7.planning.sbsocial.dao.AppUserDAO;
import org.o7.planning.sbsocial.entity.AppUser;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignUpImpl implements ConnectionSignUp {

	private AppUserDAO appUserDAO;

	public ConnectionSignUpImpl(AppUserDAO appUserDAO) {
		this.appUserDAO = appUserDAO;

	}

	 // After logging in social networking.
    // This method will be called to create a corresponding App_User record
    // if it does not already exist.
	@Override
	public String execute(Connection<?> connection) {
		// TODO Auto-generated method stub
		AppUser account = appUserDAO.createAppUser(connection);
		return account.getUserName();
	}

}
