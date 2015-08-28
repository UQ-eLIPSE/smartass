/* This file is part of SmartAss and contains the RepositoryDeleteItemController class that 
 * is the ancestor for all classes which delete some object from the repository.   
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
import org.springframework.web.servlet.view.RedirectView;

/**
 * The RepositoryDeleteItemController class is the ancestor for all classes 
 * which delete some object from the repository.
 */
public abstract class RepositoryDeleteItemController extends UserRequieredController {

	@Override
	/**
	 * Executes class specific action e.g. deletes the object.
	 */
	protected ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) {
		if(confirm(request))
			try {
				delete(Integer.parseInt(request.getParameter("id")));
			} catch (Exception e) {
				request.getSession().setAttribute("deleteErrors", e.getMessage());
			} 
		RedirectView exit = new RedirectView(getRedirectViewName(request)); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}
	
	/**
	 * Checks if user is confirmed the object deletion 
	 */
	protected boolean confirm(HttpServletRequest request) {
		String confirmed = request.getParameter("confirmed"); 
		return (confirmed!=null && confirmed.length()!=0);
	}
	
	/**
	 * Deletes the object
	 */
	protected abstract void delete(int id) throws Exception;
	
	/**
	 * Returns the name of the redirect view e.g. the page where user will be sent after
	 * controller deletes the object
	 */
	protected abstract String getRedirectViewName(HttpServletRequest request);
}
