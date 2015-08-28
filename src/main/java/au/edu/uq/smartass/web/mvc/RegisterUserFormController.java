/* This file is part of SmartAss and contains the RegisterUserFormController class that 
 * is used to register new SmartAss site user.   
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

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The RegisterUserFormController class that is used to register new SmartAss site user
 */
public class RegisterUserFormController extends SimpleFormController {
	UsersDao usersDao;
	
	public RegisterUserFormController() {
		setCommandClass(UserRegistrationModel.class);
		setCommandName("newUser");
	}
	
	@Override
	/**
	 * Executes on "create new user" form submit 
	 */
	protected ModelAndView onSubmit(Object command) throws Exception {
		UserRegistrationModel user = (UserRegistrationModel) command;
		user.setPassword(Utility.md5(user.getPassword1()));
		usersDao.addUser(user);

		RedirectView exit = new RedirectView("index.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}
	
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
}
