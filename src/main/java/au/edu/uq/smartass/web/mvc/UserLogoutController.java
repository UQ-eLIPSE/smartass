/* This file is part of SmartAss and contains the UserLogoutController class that 
 * finishes the user session. After logout the user is anonymous, has minimal rights and can't save edited assignments.   
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
 * The UserLogoutController class finishes the user session. 
 * After logout the user is anonymous, has minimal rights and can't save edited assignments.
 */
public class UserLogoutController extends HomePageController {

	@Override
	/**
	 * Finishes the user session.
	 * After logout the user is anonymous, has minimal rights and can't save edited assignments.   
	 * This function is called by Spring framework on HTTP request from the browser. 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("user", null);
		
		RedirectView exit = new RedirectView("index.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}

}
