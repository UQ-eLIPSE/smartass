/* This file is part of SmartAss and contains the AbstractResolveAuthorController class that 
 * is used to "resolve" author - e.g. connect author name from the netadata of template importing 
 * to the corresponding author record in the smartass database or create a new one if such record 
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

import java.util.List;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;


//import org.apache.log4j.Logger;

import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.jdbc.AuthorsDao;

/**
 * The AbstractResolveAuthorController class 
 * is used to "resolve" author - e.g. connect author name from the netadata of template importing 
 * to the corresponding author record in the smartass database or create a new one if such record 
 * does not exists.
 * 
 * There is different template properties that need the author resolution, this class is the
 * ancestor for all specific author resolution classes.
 */
public abstract class AbstractResolveAuthorController extends AbstractResolveController{
	AuthorsDao dao;

	public AuthorsDao getDao() {
		return dao;
	}

	public void setDao(AuthorsDao authorsDao) {
		this.dao = authorsDao;
	}
	
	/**
	 * Analyzes the template author's name and returns the list of suggestions about what
	 * records in the repository can correspond to this name 
	 * 
	 * @param authorName	the author's name
	 * @return				the list of possible authors from the local repository
	 */
	public List<AuthorsItemModel> getSuggestions(String authorName) {
		List<AuthorsItemModel> suggs =  dao.getAuthors("%"+authorName+"%"); 
		List<AuthorsItemModel> tmp;
		String[] parts = authorName.trim().split(" ");
		for(int i=0;i<parts.length;i++) 
			if(parts[i]!=null) {
				parts[i] = parts[i].trim();
				if(parts[i].length()>2) {//skip non-names... make this more sophisticated in future
					tmp = dao.getAuthors("%"+parts[i]+"%");
					for(AuthorsItemModel t : tmp) 
						if(!suggs.contains(t))
							suggs.add(t);
				}
					
			}
		return suggs;
	}
	
	/**
	 * Searches through the authors in the repository by the substring from the author's name, 
	 * returns the list of found authors.
	 */
	public List<AuthorsItemModel> search(String filter) {
		return dao.getAuthors("%" + filter + "%");
	}
	
	/**
	 * Searches through the authors in the repository by the substring from the authors name, 
	 * returns the list of found authors. This function takes care about some additional information
	 * that web site engine needs to break list of the records to a set of pages.
	 */
        @SuppressWarnings("unchecked")
	public List<AuthorsItemModel> search(ResolveAuthorModel ra) {
		ra.setRowsNum(dao.countRows("%" + ra.getSearch() + "%"));
		return dao.getItems("%" + ra.getSearch() + "%", ra.getPageNo()*ra.getRowsPerPage(), ra.getRowsPerPage());
	}
	
	/**
	 * Evaluates the solution that user has selected, checks for its consistency
	 * and sets the "author" property of the template 
	 *   
	 * @param template		the template to be imported
	 * @param ra			the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */
	public boolean resolve(TemplateImportModel template, ResolveAuthorModel ra, MessageContext  mcontext) {
		if(!validate(template, ra, mcontext))
			return false;
		AuthorsItemModel author;
		if(ra.getSolution()==0) {
			if(ra.getAuthor().getName().length()==0) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Author's name is requiered!").build());
				return false;
			}
			if(dao.getItem(ra.getAuthor().getName())!=null) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
				"Author "+ra.getAuthor().getName()+" already exists in the database!").build());
			return false;
			}
			
			setAuthor(template, ra.getAuthor());
		} else {
			author = dao.getItem(ra.getSolution());
			if(author!=null) //there is a VERY little probability that author was 
							 //deleted while request was processed
							 //otherwise it HAS to be in the database
				setAuthor(template, author);
			else {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Selected author does not found in the database!").build());
				return false;
				}
		}
		return true;
	}
	
	/**
	 * Prepares {@link ResolveAuthorModel} 
	 * 
	 * @param template		the template to be imported model
	 * @return				{@link ResolveAuthorModel} filled with appropriate data 
	 */
	public ResolveAuthorModel prepareModel(TemplateImportModel template) {
		AuthorsItemModel author = new AuthorsItemModel();
		if(needImport(template)) {
			AuthorsItemModel imp = initAuthor(template);
			author.setName(imp.getName());
			author.setDescription(imp.getDescription());
		}
		
		return new ResolveAuthorModel(author); 
	}
	
	protected abstract AuthorsItemModel initAuthor(TemplateImportModel template);
	
	protected abstract boolean needImport(TemplateImportModel template);

	protected abstract void setAuthor(TemplateImportModel template, AuthorsItemModel author);
}
