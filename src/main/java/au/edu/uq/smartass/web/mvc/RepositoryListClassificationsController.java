/* This file is part of SmartAss and contains the RepositoryListClassificationsController class that 
 * lists classifications.   
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

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;

/**
 * The RepositoryListClassificationsController class lists classifications.   
 */
public class RepositoryListClassificationsController extends AbstractController {
	ClassificationsDao dao;
	
	
	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It lists classifications depending on  filter string and parent classification 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String search = request.getParameter("search");
		model.put("lastsearch", search);
		int parent_id = 0;
		try {
			parent_id = Integer.parseInt(request.getParameter("parentid"));
		} catch (Exception e) {}
		model.put("parentid", parent_id);
		
		if(search==null || search.length()==0)
			search = "%";
		search =  "%" + search + "%";

		String delErr = (String) request.getSession().getAttribute("deleteErrors");
		if(delErr!=null) {
			model.put("errors", delErr);
			request.getSession().setAttribute("deleteErrors", null);
		}
		model.put("items", dao.getItems(parent_id, search));
		if(parent_id!=0) {
			ClassificationsItemModel parent = dao.getItem(parent_id);
			if(parent!=null)
				model.put("parent", parent);
		}
		return new ModelAndView("repositoryClasssList", model);
	}
	
	public void setDao(ClassificationsDao dao) {
		this.dao = dao;
	}
}
