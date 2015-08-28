/* This file is part of SmartAss and contains the ConfirmDeleteUserController class that 
 * is used to confirm that given user really have to be deleted from the site database.   
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

import au.edu.uq.smartass.web.jdbc.UsersDao;

/**
 * The ConfirmDeleteUserController class that 
 * is used to confirm that given user really have to be deleted from the site database.
 * 
 */
public class ConfirmDeleteUserController extends
		RepositoryConfirmDeleteController {
	UsersDao dao;

	@Override
	/**
	 * Returns user object that is asked to be deleted
	 */
	protected Object getItem(int id) {
		return dao.getItem(id);
	}

	@Override
	protected String getViewName() {
		return "userConfirmDelete";
	}

	public void setDao(UsersDao dao) {
		this.dao = dao;
	}
}
