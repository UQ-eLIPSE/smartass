/* This file is part of SmartAss and contains the RepositoryConfirmDeleteFileController class that 
 * asks user to confirm the deletion of the file from the repository.   
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

import au.edu.uq.smartass.web.jdbc.FilesDao;

/**
 * The RepositoryConfirmDeleteFileController class  
 * asks user to confirm the deletion of the file from the repository.
 */
public class RepositoryConfirmDeleteFileController extends
		RepositoryConfirmDeleteController {
	FilesDao dao;
	
	@Override
	/**
	 * Returns FilesItemModel for given id.
	 */
	protected Object getItem(int id) {
		return dao.getItem(id);
	}

	@Override
	/**
	 * Returns the name of the view for the file deletion confirmation form.
	 */
	protected String getViewName() {
		return "repositoryFileConfirmDelete";
	}

	public void setDao(FilesDao dao) {
		this.dao = dao;
	}
}
