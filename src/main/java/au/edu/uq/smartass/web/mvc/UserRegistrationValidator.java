/* This file is part of SmartAss and contains the UserRegistrationValidator class that 
 * validates the user registration data.   
 * 
 * Copyright (C) 2008 The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package au.edu.uq.smartass.web.mvc;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The UserRegistrationValidator class validates the user registration data.
 */
public class UserRegistrationValidator implements Validator {
	UsersDao usersDao;

	public boolean supports(Class clazz) {
		return clazz==UserRegistrationModel.class;
	}
	
	/**
	 * Validates the user registration data.
	 */
	public void validate(Object target, Errors errors) {
		UserRegistrationModel user = (UserRegistrationModel) target;
		ValidationUtils.rejectIfEmpty(errors, "name", "requiered.name", "Username can not be empty!");	
		if(!validateUserName(user))
			errors.rejectValue("name", "exists.user",  "User \"" + user.getName() + "\" already exists!");
		ValidationUtils.rejectIfEmpty(errors, "password1", "requiered.password", "Password can not be empty!");
		if(!user.getPassword1().equals(user.getPassword2()))
			errors.rejectValue("password1", "noequals.password",  "Password and password verification is different!");
		ValidationUtils.rejectIfEmpty(errors, "email", "requiered.email", "E-mail can not be empty!");	

	}
	
	/**
	 * Validates user name
	 */
	private boolean validateUserName(UserItemModel user) {
		UserItemModel foundUser = usersDao.getItem(user.getName());
		if(foundUser==null || user.getId()==foundUser.getId()) //no such user in database or the same user was found
			return true;
		return false;
	}
	
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

}
