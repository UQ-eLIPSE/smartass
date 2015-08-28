/* This file is part of SmartAss and contains the UserRequieredController class that 
 * contains functionality that is common for all controllers that depends on 
 * user that is logged in and user rights.   
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

import au.edu.uq.smartass.web.UserItemModel;

/**
 * The UserRequieredController class contains functionality that is common 
 * for all controllers that depends on user that is logged in and user rights.
 */
public abstract class UserRequieredController extends AbstractController {
	protected int rightsMask;

	/**
	 * Checks if user logged in and if user has enough rights to use the controller functionality.
	 */
	protected boolean checkUser(UserItemModel user) {
		if(user==null || user.getId()==0) 
			return false;
		if((user.getRights() & rightsMask)!=rightsMask)
			return false;
		return true;
	}
	
	/**
	 * This function is called by handleRequestInternal() if the user rights is OK
	 */
	protected abstract ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * Returns controller operation description. Descendants can override this function to
	 * give more detailed description.
	 */
	protected String getOperationDescription() {
		return "perform this operation";
	}

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It checks user rights and if this check is successful it calls execute() that performs
	 * controller specific actions 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(checkUser((UserItemModel) request.getSession().getAttribute("user")))
			return execute(request, response);
		else {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("description", getOperationDescription());
			return new ModelAndView("errorNoRights", model);
		}
	}

}
