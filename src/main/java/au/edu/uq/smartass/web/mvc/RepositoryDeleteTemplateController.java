/* This file is part of SmartAss and contains the RepositoryDeleteTemplateController class that 
 * deletes the template from the repository.   
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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The RepositoryDeleteTemplateController class deletes the template from the repository.  
 */
public class RepositoryDeleteTemplateController extends
		RepositoryDeleteItemController {
	TemplatesDao dao;
	RepositoryStorage storage;

	@Override
	protected void delete(int id) throws IOException {
		TemplatesItemModel item = dao.getItem(id);
		dao.deleteItem(id);

		storage.deleteFile(0, "", item.getName()+".tex");
		if(item.isHasQuestions())
			storage.deleteFile(1, "", item.getQuestions());
		if(item.isHasShortanswers())
			storage.deleteFile(1, "", item.getShortanswers());
		if(item.isHasSolutions())
			storage.deleteFile(1, "", item.getSolutions());
	}

	@Override
	protected String getRedirectViewName(HttpServletRequest request) {
		String classid = request.getParameter("classid");
		if(classid==null)
			return "repository.htm";
		else
			return "repository.htm?classid="+classid;
	}

	public void setDao(TemplatesDao dao) {
		this.dao = dao;
	}
	
	public void setStorage(RepositoryStorage storage) {
		this.storage = storage;
	}
}
