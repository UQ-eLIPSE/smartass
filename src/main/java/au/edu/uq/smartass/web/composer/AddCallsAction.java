/* This file is part of SmartAss and contains the AddCallsAction class 
 * that is the Spring bean that executes an action 
 * "Add %%CALL construction for each selected template to assignment"   
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
package au.edu.uq.smartass.web.composer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The AddCallsAction class is the Spring bean that executes an action 
 * "Add %%CALL construction for each selected template to assignment"
 *
 */
public class AddCallsAction {
	/** Data-aware object to access template properties in the repository */
	TemplatesDao templatesDao;
	
	/**
	 * Executes action. This method adds templates selected in the given {@link SelectTemplatesControl}
	 * object
	 *  
	 * @param calls		{@link SelectTemplatesControl} object that contains IDs of templates selected to be added to assignment
	 * @param assignment	assignment that is edited
	 */
	public void execute(SelectTemplatesControl calls, AssignmentConstruct assignment) {
		if(calls==null || calls.selectedIds==null)
			return;
		for(String s: calls.selectedIds) {
			TemplatesItemModel t = templatesDao.getItem(Integer.parseInt(s));
			String fullname = t.getName() + ".tex";
			if(t.getPath()!=null && t.getPath().length()>0)
				fullname = t.getPath() + File.separator + fullname;
			assignment.addRow(new CallConstruction(fullname)); //insert new construction after selected assignment row 
		}
			
	}

	/**
	 * The setter for the templatesDao field - a data-aware object to access template properties in the repository
	 */
	@Autowired
	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
}
