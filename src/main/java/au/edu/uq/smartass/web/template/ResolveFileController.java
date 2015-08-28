/* This file is part of SmartAss and contains the ResolveFileController class that  
 * is used to "resolve" file - e.g. to connect file name from the metadata of template that is being imported 
 * to the corresponding record in the smartass database or to create a new one if such record 
 * does not exists.
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
package au.edu.uq.smartass.web.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.FilesDao;

/**
 * The ResolveFileController class  
 * is used to "resolve" file - e.g. to connect file name from the metadata of template that is being imported 
 * to the corresponding record in the smartass database or create a new one if such record 
 * does not exists.
 */
public class ResolveFileController extends AbstractResolveController {
	FilesDao dao;
	RepositoryStorage storage;
	
	/**
	 * Analyzes the file name from the template and returns the list of suggestions about what
	 * records in the repository can correspond to this name 
	 * 
	 * @param filename	the file name
	 * @return				the list of possible files from the local repository
	 */
	public List<FilesItemModel> getSuggestions(String filename) {
		List<FilesItemModel> sugg = new ArrayList<FilesItemModel>();
		String parts[] = filename.split("\\.");
		String search;
		if(parts[parts.length-1].length()>4)
			search = filename;
		else {
			search = parts[0];
			for(int i=1; i<parts.length-1;i++)
				search = search + "." + parts[i];
		}
		return dao.getItems("%"+search+"%");
	}
	
	/**
	 * Searches through the files in the repository by the substring from the file name. 
	 * Returns the list of found files.
	 */
	public List<FilesItemModel> search(String filter) {
		return dao.getItems("%" + filter + "%");
	}

	/**
	 * Searches through the files in the repository by the substring from the file name. 
	 * Returns the list of found files. This function takes care about some additional information
	 * that web site engine needs to break list of the records to a set of pages.
	 */
	public List<FilesItemModel> search(ResolveFileModel rm) {
		rm.setRowsNum(dao.countRows("%" + rm.getSearch() + "%"));
		return dao.getItems("%" + rm.getSearch() + "%", rm.getPageNo()*rm.getRowsPerPage(), rm.getRowsPerPage());
	}

	/**
	 * Evaluates the solution that user has selected, checks for its consistency
	 * and adds the file to the files property of the {@link TemplatesItemModel}
	 *   
	 * @param template		the template to be imported
	 * @param r				the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */
	public boolean resolve(TemplatesItemModel template, ResolveFileModel r, int itemNo, MessageContext mcontext) throws IOException {
		if(!validate(template, r, mcontext))
			return false;

		if(itemNo==-1) {
			itemNo = template.getFiles().size();
			template.getFiles().add(null);
		}
		
		if(r.getSolution()==0) {
			if(r.getFile().getName().length()==0) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
						"\"File name\" field is requiered!").build());
				return false;
			}
			if(dao.getItem(r.getFile().getName())!=null) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
						"File \""+r.getFile().getName()+"\" already exists in the database!").build());
				return false;
			}

			storage.setFile(2, "", r.getFile().getName(), r.getFileData(), template.getTransaction());
			template.getFiles().set(itemNo, r.getFile());
		} else {
			FilesItemModel file = dao.getItem(r.getSolution());
			if(file!=null) //there is a VERY little probability that the file was 
				 //deleted while request was processed
				 //otherwise it HAS to be in the database
				template.getFiles().set(itemNo, 
						file);
			else {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Selected file does not found in the database!").build());
				return false;
				}
		}
		return true;
	}
	
	/**
	 * Evaluates the solution that user has selected for the current resolving file from the {@link TemplateImportModel},
	 * checks for its consistency and adds the file to the files property
	 *   
	 * @param template		the template to be imported
	 * @param r				the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */
	public boolean resolve(TemplateImportModel template, ResolveFileModel r, MessageContext mcontext) throws IOException {
		return resolve(template, r, template.getFilesResolver().getCurrentItemNo(), mcontext);
	}
	
	/**
	 * Creates new {@link ResolveFileModel} for the current importing item from the 
	 * {@link TemplateImportModel}
	 */
	public ResolveFileModel prepareModel(TemplateImportModel template) {
		FilesItemModel file = new FilesItemModel();
		if(template.getFilesResolver().getNeedImport()) {
			FilesItemModel imp = template.getFiles().get(template.getFilesResolver().getCurrentItemNo()); 
			file.setName(imp.getName());
			file.setDescription(imp.getDescription());
		}
		return new ResolveFileModel(file);
	}
	
	/**
	 * Creates new {@link ResolveFileModel} 
	 */
	public ResolveFileModel newModel() {
		return new ResolveFileModel(new FilesItemModel());
	}

	public FilesDao getDao() {
		return dao;
	}

	public void setDao(FilesDao dao) {
		this.dao = dao;
	}
	
	public void setStorage(RepositoryStorage storage) {
		this.storage = storage;
	}


}
