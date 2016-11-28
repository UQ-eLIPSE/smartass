/* This file is part of SmartAss and contains the HomePageController class that 
 * prepares data for main SmartAss website page.   
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.AssignmentsDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;
import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The HomePageController class prepares data for main SmartAss website page.
 */
public class HomePageController extends AbstractController {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( HomePageController.class );

	protected TemplatesDao templatesDao; 
	protected AssignmentsDao assignmentsDao;
	protected UsersDao usersDao;

	// Render all assignments on the server side, handle the pagination with javascript
	public static int ROWS_PER_PAGE = Integer.MAX_VALUE;

	public HomePageController() {}
	
	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It prepares data for the main SmartAss website page depending on different 
	 * condition and user activity. 
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LOG.info(
			"HomePageController::handleRequestInternal\n([-----\n* * *request=>* * *\n{}\n\n* * *response=>* * *\n{}\n-----])", 
			request.toString(), 
			response.toString());
	
		Map<String, Object> model = new HashMap<String, Object>();
		String mode = request.getParameter("mode");

		LOG.info("HomePageController::handleRequestInternal([ mode=>{} ])", mode);

		if(mode==null || mode.length()==0) {
			model.put("templates", templatesDao.selectLatest(10));
			model.put("assignments", assignmentsDao.selectLatest(10, 0));
		} else {
			int rowsPerPage = ROWS_PER_PAGE;
			UserItemModel user = (UserItemModel) request.getSession().getAttribute("user");
			if(user!=null && user.getRowsPerPage()>0) 
				rowsPerPage = user.getRowsPerPage();
			
			int page_no = 1;
			String spage_no = request.getParameter("page");
			if(spage_no!=null && spage_no.length()>0) 
				try {
					page_no = Integer.parseInt(spage_no);
				} catch (NumberFormatException e) {}
			model.put("page_no", page_no);
			
			if(mode.equals("recent")) {
				model.put("assignments", assignmentsDao.selectLatest(rowsPerPage, (page_no-1)*rowsPerPage));
				model.put("page_num", (assignmentsDao.countRows())/rowsPerPage+1);
			} else if(mode.equals("user")) {
				int userid = Integer.parseInt(request.getParameter("id"));
				if(userid!=0) {
					model.put("assignments", assignmentsDao.selectByUser(userid, (page_no-1)*rowsPerPage, rowsPerPage));
					model.put("auser", usersDao.getItem(userid));
					model.put("page_num", (assignmentsDao.countRows(userid))/rowsPerPage+1);
				}
			}
			else if(mode.equals("my")) {
				if(user!=null) {
					model.put("assignments", assignmentsDao.selectByUser(user.getId(), (page_no-1)*rowsPerPage, rowsPerPage));
					model.put("auser", user);
					model.put("page_num", (assignmentsDao.countRows(user.getId()))/rowsPerPage+1);
				}
			} else if(mode.equals("browse")) {
				model.put("assignments", assignmentsDao.select(
						request.getParameter("byname"),
						request.getParameter("byuser"),
						request.getParameter("bytags"), (page_no-1)*rowsPerPage, rowsPerPage));
				model.put("assignments_byname", request.getParameter("byname"));
				model.put("assignments_byuser", request.getParameter("byuser"));
				model.put("assignments_bytag", request.getParameter("bytag"));
			}
			model.put("page_num", (assignmentsDao.countRows(
					request.getParameter("byname"),
					request.getParameter("byuser"),
					request.getParameter("bytags")))/rowsPerPage+1);
		}
		model.put("mode", mode);
		return new ModelAndView("home", model);
	}
	
	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
	
	public void setAssignmentsDao(AssignmentsDao assignmentsDao) {
		this.assignmentsDao = assignmentsDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
