/* This file is part of SmartAss and contains the UserDetailsController class.   
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
 * The UserDetailsController class is used to show user details in the userDetailes view.
 * This view shows only restricted subset of the user properties not including such sensitive
 * data as the user password 
 */
public class UserDetailsController extends AbstractController {
	UsersDao dao;

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It prepares UserItemModel an puts it to the view model. 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("item", dao.getItem(Integer.parseInt(request.getParameter("id"))));
		} catch (Exception e) {}
		return new ModelAndView("userDetails", model);
	}
	
	public void setDao(UsersDao dao) {
		this.dao = dao;
	}

}
