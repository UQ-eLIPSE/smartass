/* This file is part of SmartAss and contains the RepositoryEditAuthorController class that 
 * contains functionality to edit the AuthorItemModel object.   
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


import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.jdbc.AuthorsDao;

/**
 * The RepositoryEditAuthorController class contains functionality to edit the AuthorItemModel object
 */
public class RepositoryEditAuthorController extends RepositoryEditDictionaryController {
	AuthorsDao dao;
	
	public RepositoryEditAuthorController() {
		setCommandClass(AuthorsItemModel.class);
	}

	@Override
	/**
	 * Returns AuthorItemModel for the given id
	 */
	protected Object getBackingObject(int id) {
		return dao.getItem(id);
	}

	@Override
	/**
	 * Writes changes in the object to the database 
	 */
	protected ModelAndView doUpdate(Object command) {
		dao.updateItem((AuthorsItemModel) command);
		return getModelAndView("repository-authors.htm");
	}
	
	public void setDao(AuthorsDao dao) {
		this.dao = dao;
		setValidatorDao(dao);
	}
}
