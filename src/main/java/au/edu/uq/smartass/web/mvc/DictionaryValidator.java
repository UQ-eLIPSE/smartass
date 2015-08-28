/* This file is part of SmartAss and contains the DictionaryValidator class that 
 * is the ancestor for all validator classes for the dictionary objects.   
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
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import au.edu.uq.smartass.web.NamedItemModel;
import au.edu.uq.smartass.web.jdbc.DictionaryDao;

/**
 * The DictionaryValidator class that is the ancestor for all validator classes for the dictionary objects.
 */
public class DictionaryValidator implements Validator {
	DictionaryDao dao;
	
	public DictionaryValidator() {
	}
	
	/**
	 * Creates the validator with the given DAO (database access object)
	 * @param dao
	 */
	public DictionaryValidator(DictionaryDao dao) {
		this.dao = dao;
	}

	/**
	 * Checks if this validator can validate the object of given class
	 */
	public boolean supports(Class clazz) {
		return NamedItemModel.class.isAssignableFrom(clazz);
	}

	/**
	 * Validates the object. This function do validation that is common for any dictionary class.
	 * The ancestor should override it to add any ancestor specific validation.
	 */
	public void validate(Object target, Errors errors) {
		NamedItemModel item = (NamedItemModel) target;
		validateName(item, errors);
		if(dao!=null) {
			validateItem(item, errors);
		}
	}
	
	protected void validateName(NamedItemModel item, Errors errors) {
		if(item.getName().length()==0)
			ValidationUtils.rejectIfEmpty(errors, "name", "requiered.name", "Field \"name\" can not be empty!");
	}

	protected void validateItem(NamedItemModel item, Errors errors) {
		NamedItemModel found = dao.getItem(item.getName());
		if(found!=null && found.getId()!=item.getId()) {
			errors.reject("exists.name", "Record with \""+item.getName()+"\" name already exists in the dictionary!");
		}
	}

	/**
	 * The setter for the dao (database access object) property
	 */
	public void setDao(DictionaryDao dao) {
		this.dao = dao;
	}
}
