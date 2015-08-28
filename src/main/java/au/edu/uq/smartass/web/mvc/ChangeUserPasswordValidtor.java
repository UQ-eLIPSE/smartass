/* This file is part of SmartAss and contains the ChangeUserPasswordValidtor class that 
 * is used to validate new password value before save it to the database.   
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

import au.edu.uq.smartass.web.ChangeUserPassword;

/**
 * The ChangeUserPasswordValidtor class validates new password value before 
 * save it to the database.
 */
public class ChangeUserPasswordValidtor implements Validator {

	/**
	 * Checks if this validator can work with the object passed to it 
	 */
	public boolean supports(Class clazz) {
		return ChangeUserPassword.class.equals(clazz);
	}

	/**
	 * Validates new password
	 */
	public void validate(Object target, Errors errors) {
		ChangeUserPassword cp = (ChangeUserPassword) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "required", "Old password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword1", "required", "New password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword2", "required", "New password confirmation is required");
		if(!cp.getNewPassword1().equals(cp.getNewPassword2()))
			errors.reject("not-equal", "New password and its confirmation should be equal");
	}

}
