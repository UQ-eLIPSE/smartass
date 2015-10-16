/* This file is part of SmartAss and contains the SelectAuthorController class that is
 * used to select author for newly added or edited template.
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

import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.AuthorsDao;

/**
 * The SelectAuthorController class that is
 * used to select author for newly added or edited template.
 */
public class SelectAuthorController extends AbstractResolveController {
	AuthorsDao dao;

	public void setDao(AuthorsDao authorsDao) {
		this.dao = authorsDao;
	}
	
	/**
	 * Searches through authors in the repository by the substring from the author name. 
	 * Returns the list of authors found.
	 */
	public List<AuthorsItemModel> search(String filter) {
		return dao.getAuthors("%" + filter + "%");
	}
	
	/**
	 * Searches through authors in the repository by the substring from the author name. 
	 * Returns the list  This function takes care about some additional information
	 * that web site engine needs to break list of the records to a set of pages.
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorsItemModel> search(ResolveAuthorModel ra) {
		ra.setRowsNum(dao.countRows("%" + ra.getSearch() + "%"));
		return dao.getItems("%" + ra.getSearch() + "%", ra.getPageNo()*ra.getRowsPerPage(), ra.getRowsPerPage());
	}
	
	
	/**
	 * Evaluates the solution that user has selected, checks for its consistency
	 * and sets the author property of the {@link TemplatesItemModel}
	 *   
	 * @param template		the template that is edited
	 * @param ra			the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */

	public boolean resolve(TemplatesItemModel template, ResolveAuthorModel ra, MessageContext mcontext) {
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
			
			template.setAuthor(ra.getAuthor());
		} else {
			author = dao.getItem(ra.getSolution());
			if(author!=null) //there is a VERY little probability that author was 
							 //deleted while request was processed
							 //otherwise it HAS to be in the database
				template.setAuthor(author);
			else {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Selected author does not found in the database!").build());
				return false;
				}
		}
		return true;
	}
	
	public ResolveAuthorModel newModel() {
		return new ResolveAuthorModel(new AuthorsItemModel());
	}}
