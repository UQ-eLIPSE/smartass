/* This file is part of SmartAss and contains the HelpPageController class that 
 * prepares data for the help page.   
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

import au.edu.uq.smartass.web.HelpPageItem;
import au.edu.uq.smartass.web.jdbc.HelpPagesDao;

/**
 * The HelpPageController class prepares data for the help page.
 */
public class HelpPageController extends AbstractController {
	private HelpPagesDao dao;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse responce) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		HelpPageItem item = dao.getItem(request.getParameter("context")); 
		model.put("text", item.getText());
		model.put("title", item.getTitle());
		return new ModelAndView("help", model);
	}
	
	public void setDao(HelpPagesDao dao) {
		this.dao = dao;
	}

}
