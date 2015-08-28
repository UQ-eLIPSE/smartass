/* This file is part of SmartAss and contains the UserLoginController class that 
 * serves the user login to the SmartAss web site. 
 *
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

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.UserLoginModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The UserLoginController class serves the user login to the SmartAss web site.
 */
public class UserLoginController extends SimpleFormController {
	UsersDao usersDao;
	
	public UserLoginController() {
		setCommandClass(UserLoginModel.class);
		setCommandName("userLogin");
	}
	
	@Override
	/**
	 * Sets the "user" attribute of the session ant redirects to the homepage.
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserLoginModel auth = (UserLoginModel) command;
		if(auth.getUser()!=null)
			request.getSession().setAttribute("user", auth.getUser());
		
		String redirectUrl = request.getParameter("url");
		if(redirectUrl==null || redirectUrl.length()==0)
			redirectUrl = "index.htm";
		RedirectView exit = new RedirectView(redirectUrl); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

}
