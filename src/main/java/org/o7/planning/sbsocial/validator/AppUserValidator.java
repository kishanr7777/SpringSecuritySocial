package org.o7.planning.sbsocial.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.o7.planning.sbsocial.dao.AppUserDAO;
import org.o7.planning.sbsocial.entity.AppUser;
import org.o7.planning.sbsocial.form.AppUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AppUserValidator implements Validator {

	//common validator library
	private EmailValidator emailvalidator = EmailValidator.getInstance();
	
	@Autowired
	private AppUserDAO appUserDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == AppUserForm.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		AppUserForm form = (AppUserForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "UserName is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Firstname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Lastname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Password is required");
		
		if(errors.hasErrors()){
			return;
		}
		
		if(!emailvalidator.isValid(form.getEmail())){
			errors.rejectValue("email", "Email is not valid");
			return;
		}
		
		AppUser userAccount = appUserDAO.findAppUserByUserName(form.getUserName());
		
		if(userAccount != null) {
			if(form.getUserId() == null){
				errors.rejectValue("username", "Username is not available");
				return;
			}
			else if(!form.getUserId().equals(userAccount.getUserId())){
				errors.rejectValue("Username", "Username is not available");
				return;
			}
		}
		
		userAccount = appUserDAO.findByEmail(form.getEmail());
		
		 if (userAccount != null) {
	            if (form.getUserId() == null) {
	                errors.rejectValue("email", "", "Email is not available");
	                return;
	            } else if (!form.getUserId().equals(userAccount.getUserId() )) {
	                errors.rejectValue("email", "", "Email is not available");
	                return;
	            }
	        }
		
	}
	
	

}
