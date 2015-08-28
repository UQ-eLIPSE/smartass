/* This file is part of SmartAss and contains the UserSettingsController class that 
 * is used to edit user properties.   
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

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The UserSettingsController class is used to edit user properties.
 */
public class UserSettingsController extends SimpleFormController {
	UsersDao usersDao;

	public UserSettingsController() {
		setCommandClass(UserItemModel.class);
		setCommandName("editUser");
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Object user = request.getSession().getAttribute("user");
		if(user==null)
			user = new UserItemModel();
		return user;
	}
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		UserItemModel user = (UserItemModel) command;
		usersDao.updateUser(user);
		
		RedirectView exit = new RedirectView("index.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}
	
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

}
