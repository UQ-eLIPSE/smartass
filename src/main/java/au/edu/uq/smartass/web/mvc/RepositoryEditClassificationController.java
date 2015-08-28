/* This file is part of SmartAss and contains the RepositoryEditClassificationController class that 
 * contains functionality to edit the ClassificationItemModel object.      
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.NamedItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;

/**
 * The RepositoryEditClassificationController class 
 * contains functionality to edit the ClassificationItemModel object.
 */
public class RepositoryEditClassificationController extends
		RepositoryEditDictionaryController {
	ClassificationsDao dao;
	
	public RepositoryEditClassificationController() {
		setCommandClass(ClassificationsItemModel.class);
	}

	@Override
	/**
	 * Returns ClassificationsItemModel for the given id
	 */
	protected Object getBackingObject(int id) {
		return dao.getItem(id);
	}
	
	@Override
	/**
	 * Creates new ClassificationsItemModel object
	 */
	protected Object newBackingObject(HttpServletRequest request) throws Exception {
		ClassificationsItemModel item = (ClassificationsItemModel) super.newBackingObject(request);
		
		int parent_id = 0;
		try {
			parent_id = Integer.parseInt(request.getParameter("parentid"));
		} catch (Exception e) {}
		item.setParentModel(dao.getItem(parent_id));
		
		return item;
	}
	
	@Override
	/**
	 * Writes changes in the object to the database 
	 */
	protected ModelAndView doUpdate(Object command) {
		dao.updateItem((ClassificationsItemModel) command);
		return getModelAndView("repository-classs.htm", command);
	}
	
	@Override
	/**
	 * Returns redirect URL depending on edited object's parent id
	 */
	protected RedirectView getRedirectView(String url, Object command) {
		RedirectView rw = new RedirectView(url);
		ClassificationsItemModel parent = ((ClassificationsItemModel) command).getParentModel();
		if(parent!=null)
			rw.addStaticAttribute("parentid", parent.getId());
		return rw;
	}
	
	@Override
	/**
	 * Validates the ClassificationsItemModel
	 */
	protected DictionaryValidator createValidator() {
		return new DictionaryValidator() {
				@Override
				protected void validateItem(NamedItemModel item,
						Errors errors) {
					ClassificationsItemModel clitem = (ClassificationsItemModel) item;
					int parentid = clitem.getParentModel()==null?0:clitem.getParentModel().getId();
					List<ClassificationsItemModel> found = 
						((ClassificationsDao)dao).getItems(parentid, item.getName());
					if(found!=null && found.size()>0 && found.get(0).getId()!=item.getId()) {
						if(parentid==0)
							errors.reject("exists.name", "Classification \""+item.getName()+"\" already exists in the dictionary!");
						else
							errors.reject("exists.name", "Classification \""+clitem.getParentModel().getName()+"/"+item.getName()+"\" already exists in the dictionary!");
					}

				}
			};
	}

	public void setDao(ClassificationsDao dao) {
		this.dao = dao;
		setValidatorDao(dao);
	}
	
}
