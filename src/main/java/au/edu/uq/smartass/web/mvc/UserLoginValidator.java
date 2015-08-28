/* This file is part of SmartAss and contains the UserLoginValidator class that 
 * checks if the authentication data is correct and corresponding user is found in the database.   
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
import org.springframework.validation.Validator;

import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.UserLoginModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The UserLoginValidator class checks if the authentication data is correct 
 * and corresponding user is found in the database.
 */
public class UserLoginValidator implements Validator {
	UsersDao usersDao;

	public boolean supports(Class clazz) {
		return clazz==UserLoginModel.class;
	}

	/**
	 * Checks if the authentication data is correct and corresponding user is found in the database.
	 */
	public void validate(Object target, Errors errors)  {
		UserLoginModel auth = (UserLoginModel) target;
		try {
			UserItemModel user = usersDao.getItem(auth.getName(), Utility.md5(auth.getPassword()));
			if(user==null)
				errors.reject("incorrect.login", "Username or password is incorrect!");
			else
				auth.setUser(user);
		} catch (Exception e) {
			errors.reject("error.undefined", e.getMessage());
		}
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
