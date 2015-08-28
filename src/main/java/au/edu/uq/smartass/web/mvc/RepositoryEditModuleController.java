/* This file is part of SmartAss and contains the RepositoryEditModuleController class that 
 * contains functionality to edit the ModulesItemModel object.   
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

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.web.ModulesItemModel;
import au.edu.uq.smartass.web.NamedItemModel;
import au.edu.uq.smartass.web.jdbc.ModulesDao;

/**
 * The RepositoryEditModuleController class contains functionality to edit the ModulesItemModel object.
 */
public class RepositoryEditModuleController extends RepositoryEditDictionaryController {
	ModulesDao dao;
	
	public RepositoryEditModuleController() {
		setCommandClass(ModulesItemModel.class);
	}

	@Override
	/**
	 * Returns ModulesItemModel for the given id
	 */
	protected Object getBackingObject(int id) {
		return dao.getItem(id);
	}
	
	@Override
	/**
	 * Writes changes in the object to the database 
	 */
	protected ModelAndView doUpdate(Object command) {
		dao.updateItem((ModulesItemModel) command);
		return getModelAndView("repository-modules.htm");
	}
	
	@Override
	/**
	 * Creates validator that checks for some modules-related data consistency
	 */
	protected DictionaryValidator createValidator() {
		return new DictionaryValidator() {
				@Override
				protected void validateName(NamedItemModel item, Errors errors) {
					super.validateName(item, errors);
					if(!item.getName().matches("\\w+"))
						errors.reject("malformed.name", "Incorrect module name \"" + item.getName() +"\"!");
					if(!((ModulesItemModel)item).getModulePackage().matches("((\\w)+\\.)*((\\w)+)"))
						errors.reject("malformed.name", "Incorrect module package \"" + ((ModulesItemModel)item).getModulePackage() +"\"!");
				}
			
				@Override
				protected void validateItem(NamedItemModel item,
						Errors errors) {
					ModulesItemModel module = (ModulesItemModel) item;
					ModulesItemModel found = 
						((ModulesDao)dao).getItem(module.getName(), module.getModulePackage());
					if(found!=null && found.getId()!=item.getId()) 
						errors.reject("exists.name", "Module \""+module.getFullName()+"\" already exists in the dictionary!");
				}
			};
	}
	
	public void setDao(ModulesDao dao) {
		this.dao = dao;
		setValidatorDao(dao);
	}
}
