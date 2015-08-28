/* This file is part of SmartAss and contains the RepositoryEditFileController class that 
 * contains functionality to edit the FilesItemModel object.   
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.jdbc.FilesDao;

/**
 * The RepositoryEditFileController class contains functionality to edit the FilesItemModel object
 */
public class RepositoryEditFileController extends
		RepositoryEditDictionaryController {
	FilesDao dao;
	RepositoryStorage repository;

	public RepositoryEditFileController() {
		setCommandClass(UploadFileModel.class);
	}
	
	@Override
	/**
	 * Returns FilesItemModel for the given id
	 */
	protected Object getBackingObject(int id) {
		FilesItemModel item = dao.getItem(id);
		if(item==null)
			return null;
		return new UploadFileModel(item);
	}

	@Override
	/**
	 * Writes changes in the object to the database 
	 */
	protected ModelAndView doUpdate(Object command) throws Exception {
		UploadFileModel file = (UploadFileModel) command; 
		if(file.getId()==0 || file.getReplaceFile()) {
			if(file.getId()!=0) {
				FilesItemModel oldfile = dao.getItem(file.getId());
				repository.deleteFile(2, "", oldfile.getName());
			}
			repository.setFile(2, "", file.getName(), file.getFile());
		} else {
			FilesItemModel oldfile = dao.getItem(file.getId());
			if(!file.getName().equals(oldfile.getName()))
				repository.renameFile(2, "", oldfile.getName(), "", file.getName());
		}
		dao.updateItem(file);
		return getModelAndView("repository-files.htm");
	}
	
	public void setDao(FilesDao dao) {
		this.dao = dao;
		super.setValidatorDao(dao);
	}
	
	public void setRepository(RepositoryStorage repository) {
		this.repository = repository;
	}

	/**
	 * Registers the custom editor that understands multipart in the post parameters
	 * sent by browser  
	 */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws ServletException {
	    // to actually be able to convert Multipart instance to byte[]
	    // we have to register a custom editor
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	    // now Spring knows how to handle multipart object and convert them
    }
}
