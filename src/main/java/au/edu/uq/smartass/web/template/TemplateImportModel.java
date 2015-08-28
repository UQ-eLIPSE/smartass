/* This file is part of SmartAss and contains the TemplateImportModel class that is
 * the extension of the TemplatesItemModel and add some import-related properties and functionality to it.
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;

/**
 * The TemplateImportModel class that is
 * the extension of the TemplatesItemModel and add some import-related properties and functionality to it.
 */
public class TemplateImportModel extends TemplatesItemModel {
	String data = "";
	Map<String, AuthorsItemModel> updAuthors = new HashMap<String, AuthorsItemModel>();
	List<AuthorsItemModel> updAuthorsList = new Vector<AuthorsItemModel>();

	boolean importAuthor;

	ListResolverModel filesResolver = new ListResolverModel(false);
	ListResolverModel modulesResolver = new ListResolverModel(false);
	ListResolverModel updatesResolver = new ListResolverModel(false);
	ListResolverModel updAuthorsResolver = new ListResolverModel(true);
	

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public Map<String, AuthorsItemModel> getUpdAuthors() {
		return updAuthors;
	}

	public List<AuthorsItemModel> getUpdAuthorsList() {
		return updAuthorsList;
	}

	public void setData(String templateBody) {
		this.data = templateBody;
	}
	
	public String getData() {
		return data;
	}

	public void setImportAuthor(boolean importAuthor) {
		this.importAuthor = importAuthor;
	}
	
	public boolean isImportAuthor() {
		return importAuthor;
	}

	public String getImportAuthorStr() {
		return "" + importAuthor;
	}
	
	public ListResolverModel getFilesResolver() {
		return filesResolver;
	}

	public void setFilesResolver(ListResolverModel filesResolver) {
		this.filesResolver = filesResolver;
	}

	public ListResolverModel getModulesResolver() {
		return modulesResolver;
	}

	public void setModulesResolver(ListResolverModel modulesResolver) {
		this.modulesResolver = modulesResolver;
	}

	public ListResolverModel getUpdatesResolver() {
		return updatesResolver;
	}

	public void setUpdatesResolver(ListResolverModel updatesResolver) {
		this.updatesResolver = updatesResolver;
	}

	public ListResolverModel getUpdAuthorsResolver() {
		return updAuthorsResolver;
	}

	public void setUpdAuthorsResolver(ListResolverModel updAuthorsResolver) {
		this.updAuthorsResolver = updAuthorsResolver;
	}

	
	public void addUpdateAuthor(AuthorsItemModel author) {
		if(!getUpdAuthors().containsValue(author)) {
			updAuthors.put(author.getName(), author);
			updAuthorsList.add(author);
		}
	}
	
	public void setUpdateAuthor(int i, AuthorsItemModel author) {
		String oldname = updAuthorsList.get(i).getName();
		updAuthorsList.set(i, author);
		updAuthors.put(oldname, author);
	}
	
	public String getDtcreatedStr() {
		if(getDtcreated()==null)
			return null;
		return dateFormat.format(getDtcreated());
	}
	
	public void setDtcreatedStr(String value) throws ParseException {
		try {
			setDtcreated(dateFormat.parse(value));
		} catch (Exception e) {
			setDtcreated(null);
		}
	}
	
	@Override
	public void clear() {
		super.clear();
		getUpdAuthors().clear();
		getUpdAuthorsList().clear();
		getFilesResolver().clear();
		getModulesResolver().clear();
		getUpdAuthorsResolver().clear();
		getUpdatesResolver().clear();
		setImportAuthor(false);
	}
}
