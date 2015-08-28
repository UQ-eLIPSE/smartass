/* This file is part of SmartAss and contains the NewUpdateController class that is
 * used to add new update (e.g. the record about changes in the template)
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
import au.edu.uq.smartass.web.UpdatesItemModel;
import au.edu.uq.smartass.web.jdbc.AuthorsDao;

/**
 * The NewUpdateController class is
 * used to add new update (e.g. the record about changes in the template)
 */
public class NewUpdateController {
	AuthorsDao dao;
	
	public void setDao(AuthorsDao authorsDao) {
		this.dao = authorsDao;
	}
	
	/**
	 * Searches authors for the update in the repository 
	 * 
	 * @param filter	search string
	 * @return			the list of the {@link AuthorsItemModel} found
	 */
	public List<AuthorsItemModel> search(String filter) {
		return dao.getAuthors("%" + filter + "%");
	}
	
	/**
	 * Searches an author for the update in the repository using information from
	 * the {@link ResolveUpdateModel} 
	 * 
	 * @param rm		{@link ResolveUpdateModel} with the information for authors search
	 * @return			the list of the {@link AuthorsItemModel} found
	 */
	public List<AuthorsItemModel> search(ResolveUpdateModel rm) {
		rm.setRowsNum(dao.countRows("%" + rm.getSearch() + "%"));
		return dao.getItems("%" + rm.getSearch() + "%", rm.getPageNo()*rm.getRowsPerPage(), rm.getRowsPerPage());
	}
	
	/**
	 * Creates new empty {@link ResolveUpdateModel}
	 */
	public ResolveUpdateModel newModel() {
		UpdatesItemModel update = new UpdatesItemModel();
		update.setAuthor(new AuthorsItemModel());
		return new ResolveUpdateModel(update);
	}
	
	/**
	 * Evaluates the solution that user has selected, checks for its consistency
	 * sets the {@link UpdatesItemModel} properties and puts resolved update to the template 
	 *   
	 * @param template		the template to be imported
	 * @param rm			the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 */
	public boolean resolve(TemplatesItemModel template, ResolveUpdateModel rm, MessageContext mcontext) {
		if(rm.getUpdate().getUpdateDate()==null) {
			mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
				"Update date is requiered!").build());
			return false;
		}

		if(rm.getSolution()<0)  {
			mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
				"Your have to select author from the database or enter new one!").build());
			return false;
		} 
		if(rm.getSolution()!=0)  {
			AuthorsItemModel author = dao.getItem(rm.getSolution());
			if(author!=null) //there is a VERY little probability that author was 
				 //deleted while request was processed
				 //otherwise it HAS to be in the database
				rm.getUpdate().setAuthor(author);
			else
				; //TODO: throw some error...
		} else {
			if(rm.getUpdate().getAuthor()==null || rm.getUpdate().getAuthor().getName()==null ||
					rm.getUpdate().getAuthor().getName().length()==0) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Author's name is requiered!").build());
				return false;
			}
			
		}
		template.getUpdates().add(rm.getUpdate());
		return true;	
	}
}
