/* This file is part of SmartAss and contains the AdministrateUserController class that 
 * is used to allow the site administrator to edit the user's properties.   
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
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The AdministrateUserController class is used to allow the site administrator to edit the user's properties
 */
public class AdministrateUserController extends SimpleFormController {
	UsersDao usersDao;

	public AdministrateUserController() {
		setCommandClass(UserItemModel.class);
		setCommandName("editUser");
	}
	
	@Override
	/**
	 * Returns the model (as im Model-View-Controller) class for the SmartAss web site user
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		try {
			int user_id = Integer.parseInt((String)request.getParameter("userid"));
			Object user = usersDao.getItem(user_id);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	/**
	 * This function is called on data submit. It updates the user's data in the database.
	 */
	protected ModelAndView onSubmit(Object command) throws Exception {
		UserItemModel user = (UserItemModel) command;
		usersDao.updateUser(user);
		
		RedirectView exit = new RedirectView("users.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}
	
	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * Its only differences from default behavior is that it returns redirect view that sends client
	 * to the user list page.   
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			return super.handleRequestInternal(request, response);
		} catch (Exception e) {
			RedirectView exit = new RedirectView("users.htm"); 
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);
		}
	}

	/**
	 * The setter for the usersDao property
	 */
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
