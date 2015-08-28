/* This file is part of SmartAss and contains the RepositoryDeleteFileController class that 
 * deletes the file from the repository.   
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

import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.jdbc.FilesDao;

/**
 * The RepositoryDeleteFileController class deletes the file from the repository.
 */
public class RepositoryDeleteFileController extends
		RepositoryDeleteItemController {
	FilesDao dao;
	RepositoryStorage storage;
	
	@Override
	protected void delete(int id) throws Exception {
		String filename = dao.getItem(id).getName();
		dao.deleteItem(id);
		
		storage.deleteFile(2, "", filename);
	}

	@Override
	protected String getRedirectViewName(HttpServletRequest request) {
		return "repository-files.htm";
	}
	
	public void setDao(FilesDao dao) {
		this.dao = dao;
	}

	public void setStorage(RepositoryStorage storage) {
		this.storage = storage;
	}
}
