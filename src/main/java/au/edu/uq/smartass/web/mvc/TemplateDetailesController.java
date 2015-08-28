/* This file is part of SmartAss and contains the TemplateDetailesController class that 
 * prepares template data to show them to the user.   
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
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The TemplateDetailesController class prepares template data to show them to the user.
 */
public class TemplateDetailesController extends AbstractController {
	private TemplatesDao templatesDao; 
	
	public TemplateDetailesController() {}

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It prepares template data and put them to the model.
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			TemplatesItemModel template = templatesDao.getItem(Integer.parseInt(request.getParameter("id")));
			if(template!=null)
				return new ModelAndView("template_details", "template",  template);
		} catch (Exception e) {	}
		return new ModelAndView("template_not_found");
	}

	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
}
