/* This file is part of SmartAss and contains the RepositoryListItemsController class that 
 * is the ancestor for all controllers that lists some objects from the repository.   
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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.DictionaryDao;

/**
 * The RepositoryListItemsController class  
 * is the ancestor for all controllers that lists some objects from the repository.
 */
public abstract class RepositoryListItemsController extends AbstractController {
	public static int ROWS_PER_PAGE=20 ; //temporary! move this to application settings!
	DictionaryDao dao;

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * It checks the user rights and prepares data to be listed in the site web page.
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.addHeader("Expires",  "Thu, 01 Jan 1981 01:00:00 GM");
		int page_no = 1;
		String spage_no = request.getParameter("page");
		if(spage_no!=null && spage_no.length()>0) 
			try {
				page_no = Integer.parseInt(spage_no);
			} catch (NumberFormatException e) {}

		int rowsPerPage = ROWS_PER_PAGE;
		UserItemModel user = (UserItemModel) request.getSession().getAttribute("user");
		if(user!=null && user.getRowsPerPage()>0) 
			rowsPerPage = user.getRowsPerPage();
		
		Map<String, Object> model = new HashMap<String, Object>();
		String delErr = (String) request.getSession().getAttribute("deleteErrors");
		if(delErr!=null) {
			model.put("errors", delErr);
			request.getSession().setAttribute("deleteErrors", null);
		}
		String search = request.getParameter("search");
		model.put("lastsearch", search);
		model.put("page_num", dao.countRows(prepareFilterParam(search))/rowsPerPage+1);
		model.put("page_no", page_no);
		
		return populateData(model, prepareFilterParam(search),(page_no-1)*rowsPerPage, rowsPerPage);
	}
	
	/**
	 * Prepares filter string for the SQL statement
	 * 
	 * @param search 	string to search
	 * 
	 * @return			prepared string
	 */
	protected String prepareFilterParam(String search) {
		if(search==null || search.length()==0)
			return "%";
		return "%" + search + "%";
	}
	
	/**
	 * Populates model with objects retrieved from the repository
	 * 
	 * @param model		model 9as im Model-View-Controller) to be populated by objects
	 * @param filter	filter string
	 * @param skip_num	rows number to skip
	 * @param rows_num	rows number to show
	 * @return
	 */
	protected ModelAndView populateData(Map<String, Object> model, String filter, int skip_num, int rows_num) {
		model.put("items", dao.getItems(filter, skip_num, rows_num));
		return new ModelAndView(getViewName(), model);
	}
	
	/**
	 * This function should be overrided by descendant to return appropriate view name
	 */
	protected abstract String getViewName();
	
	/**
	 * Setter for the dao field that should be one of the {@link DictionaryDao} descendants.  
	 */
	public void setDao(DictionaryDao dao) {
		this.dao = dao;
	}
}
