/* This file is part of SmartAss and contains the RepositoryConfirmDeleteController class that 
 * is the ancestor for all controller classes for delete some object from the repository.   
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

import au.edu.uq.smartass.web.AuthorsItemModel;

/**
 * The RepositoryConfirmDeleteController class that is the ancestor for all controller classes 
 * which delete some object from the repository.
 */
public abstract class RepositoryConfirmDeleteController extends UserRequieredController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * It prepares object data to show them to the user who wants to delete the object.
	 */
	protected ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("item", getItem(Integer.parseInt(request.getParameter("id"))));
		return new ModelAndView(getViewName(), model);
	}

	/**
	 * The descendant should override this function to return actual object 
	 *  
	 * @param id	the object id
	 * @return		the object
	 */
	protected abstract Object getItem(int id);
	
	/**
	 * The descendant should override this function to returns actual view name for
	 * its object class
	 */
	protected abstract String getViewName();
	
	
}
