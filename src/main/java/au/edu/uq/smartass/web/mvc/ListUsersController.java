/* This file is part of SmartAss and contains the ListBackupsController class that 
 * lists users that exist on the site. 
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The ListBackupsController class that lists users that exist on the site.
 */
public class ListUsersController extends AbstractController {
	UsersDao usersDao;

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It creates the view and fill the model with the users list. 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users", usersDao.getItems("%"));
		return new ModelAndView("usersList", model);
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
