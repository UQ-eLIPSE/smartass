/* This file is part of SmartAss and contains the RepositoryConfirmDeleteModuleController class that 
 * asks user to confirm the deletion of the module from the repository   
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

import au.edu.uq.smartass.web.jdbc.ModulesDao;

/**
 * The RepositoryConfirmDeleteModuleController class that 
 * asks user to confirm the deletion of the module from the repository
 */
public class RepositoryConfirmDeleteModuleController extends
		RepositoryConfirmDeleteController {
	ModulesDao dao;

	@Override
	/**
	 * Returns ModulesItemModel for given id.
	 */
	protected Object getItem(int id) {
		return dao.getItem(id);
	}
	
	@Override
	/**
	 * Returns the name of the view for the module deletion confirmation form.
	 */
	protected String getViewName() {
		return "repositoryModuleConfirmDelete";
	}

	public void setDao(ModulesDao dao) {
		this.dao = dao;
	}
}
