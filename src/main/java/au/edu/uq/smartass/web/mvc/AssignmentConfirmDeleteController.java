/* This file is part of SmartAss and contains the AssignmentConfirmDeleteController class that 
 * is used to ask user to confirm that he/she really wants to delete the assignment.   
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

import au.edu.uq.smartass.web.AssignmentsItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.AssignmentsDao;

/**
 * The AssignmentConfirmDeleteController class is used to ask user to confirm that 
 * he/she really wants to delete the assignment
 */
public class AssignmentConfirmDeleteController extends AbstractController {
	AssignmentsDao dao;
	
	/**
	 * Checks if the user has rights to delete the assignment
	 * 
	 * @param user				user who try to delete the assignment
	 * @param assignmentUser	user who is the author of the assignment
	 * @return
	 */
	protected boolean checkUser(UserItemModel user, UserItemModel assignmentUser) {
		if(user==null || user.getId()==0) 
			return false;
		if((user.getRights() & 1)!=1 || user.getId()!=assignmentUser.getId())
			return false;
		return true;
	}
	
	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * It checks if the user has an appropriate rights and asks if he really wants to delete the assignment.
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			AssignmentsItemModel it = dao.getItem(id);
			if(!checkUser((UserItemModel) request.getSession().getAttribute("user"), it.getUser())) {
				model.put("description", "to delete this assignment");
				return new ModelAndView("errorNoRights", model);
			} else 
				model.put("item", it);
		} catch (Exception e) {}
		return new ModelAndView("assignmentConfirmDelete", model);
	}
	
	/**
	 * The setter for the assignments DAO field
	 */
	public void setDao(AssignmentsDao dao) {
		this.dao = dao;
	}
}
