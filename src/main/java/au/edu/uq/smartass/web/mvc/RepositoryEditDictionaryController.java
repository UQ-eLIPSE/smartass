/* This file is part of SmartAss and contains the RepositoryEditDictionaryController class that 
 * is the ancestor for all dictionary edit controllers and contains some basic functionality
 * that is common for all dictionaries.   
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
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.jdbc.DictionaryDao;

/**
 * The RepositoryEditDictionaryController class  
 * is the ancestor for all dictionary edit controllers and contains some basic functionality
 * that is common for all dictionaries.   
 */
public abstract class RepositoryEditDictionaryController extends UserRequieredFormController {
	DictionaryValidator validator;
	
	/**
	 * Creates the controller, sets an appropriate validator, user rights and command name.
	 */
	public RepositoryEditDictionaryController() {
		setValidator(validator = createValidator());
		rightsMask = 4;
		setCommandName("item"); 
	}

	/**
	 * The ancestor have to override this function if it requires som extra validation activity
	 * than the basic one provided by the DictionaryValidator class.  
	 */
	protected DictionaryValidator createValidator() {
		return new DictionaryValidator();
	}
	
	@Override
	/**
	 * Creates form baking object. This function relies to abstract getBackingObject() function
	 * that should be defined in each specific edit dictionary controller class 
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		String sid = request.getParameter("id");
		if(sid==null || sid.length()==0)
			return newBackingObject(request);
		int id = 0;
		try {
			id = Integer.parseInt(sid);
		} catch (Exception e) {
			return newBackingObject(request);
		}
		Object item = getBackingObject(id);
		if(item==null)
			return newBackingObject(request);
		return item; 
	}
	
	/**
	 * Creates the redirect view for given url. 
	 * 
	 * @param url		url where to redirect
	 * @param command	object that have being edited 
	 * @return
	 */
	protected RedirectView getRedirectView(String url, Object command) {
		RedirectView exit = new RedirectView(url); 
		exit.setExposeModelAttributes(false);
		return exit;
	}
	
	protected ModelAndView getModelAndView(String url, Object command) {
		return new ModelAndView(getRedirectView(url, command));
	}
	
	protected ModelAndView getModelAndView(String url) {
		return getModelAndView(url, null);
	}

	/**
	 * Creates an empty object to be edited, e.g. new exemplar of dictionary model class
	 */
	protected Object newBackingObject(HttpServletRequest request) throws Exception {
		return getCommandClass().newInstance();
	}

	/**
	 * Sets DAO (database access object) for the validator
	 */
	protected void setValidatorDao(DictionaryDao newDao) {
		validator.setDao(newDao);
	}
	
	/**
	 * This function have to be overrided in the descendant to return
	 * the model object for the given id
	 */
	protected abstract Object getBackingObject(int id);
	
}
