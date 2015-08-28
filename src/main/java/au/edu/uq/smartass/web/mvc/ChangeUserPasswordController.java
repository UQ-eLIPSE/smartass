/* This file is part of SmartAss and contains the ChangeUserPasswordController class that 
 * is used to change the user's password.   
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
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.ChangeUserPassword;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The ChangeUserPasswordController class that is used to change the user's password.
 */
public class ChangeUserPasswordController extends UserRequieredFormController {
	UsersDao dao;

	public ChangeUserPasswordController() {
		setCommandClass(ChangeUserPassword.class);
	}
	
	@Override
	protected ModelAndView doUpdate(Object command) throws Exception {
		return null;
	}
	
	@Override
	/**
	 * This function is called on data submit. It writes new user's password to the database.
	 */
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		UserItemModel user = (UserItemModel) request.getSession().getAttribute("user");
		if(checkUser(user)) {
			ChangeUserPassword cp = (ChangeUserPassword) command;
			if(Utility.md5(cp.getOldPassword()).equals(user.getPassword())) {
				if(cp.getNewPassword1().equals(cp.getNewPassword2())) {
					user.setPassword(Utility.md5(cp.getNewPassword1()));
					dao.updateUser(user);

					RedirectView exit = new RedirectView("index.htm"); 
					exit.setExposeModelAttributes(false);
					return new ModelAndView(exit);
				}
			}
		}

		return getErrorModel();
	}


	public void setDao(UsersDao dao) {
		this.dao = dao;
	}
}
