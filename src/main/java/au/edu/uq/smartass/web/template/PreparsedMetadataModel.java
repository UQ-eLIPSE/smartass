/* This file is part of SmartAss and contains the PreparsedMetadataModel class that
 * stores pre-parsed metadata from the template. "Pre-parsed" means that at this point data is red 
 * from the template, recognized as files, modules etc but stored as plain strings
 * without SmartAss objects models creation (that will be done late together with resolution - the 
 * attempt to map preparsed data to existing database objects).
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The PreparsedMetadataModel class
 * stores pre-parsed metadata from the template. "Pre-parsed" means that at this point data is red 
 * from the template, recognized as files, modules etc but stored as plain strings
 * without SmartAss objects models creation (that will be done late together with resolution - the 
 * attempt to map preparsed data to existing database objects).
 */
public class PreparsedMetadataModel implements Serializable {

	/** The template name */
	private String name = "";
	/** Template related keywords */
	private String keywords = "";
	/** The template description */
	private String description = "";
	/** The date when the template was created */
	private String dtcreated;

	private String[] author = {"", ""};
	private List<String[]> files = new ArrayList<String[]>();
	private List<String[]> modules = new ArrayList<String[]>();
	private List<String[]> updates = new ArrayList<String[]>();
	private List<String[]> updAuthors = new ArrayList<String[]>(); 

    public List<String[]> getUpdAuthors() { return updAuthors; }

	public String getName() { return name; }
	
	public void setName(String name) {
		name = name.trim();
		if(name.lastIndexOf(".tex")==name.length()-4)
			this.name = name.substring(0, name.lastIndexOf(".tex"));
		else
			this.name = name;
	}
	
	public String getKeywords() { return keywords; }
	
	public void setKeywords(String keywords) { this.keywords = keywords; }
	
	public String getDescription() { return description; }
	
	public void setDescription(String description) { this.description = description; }
	
	public String getDtcreated() { return dtcreated; }
	
	public void setDtcreated(String dtcreated) { this.dtcreated = dtcreated; }
	
	public String[] getAuthor() { return author; }
	
	public void setAuthor(String[] author) { this.author = author; }
	
	public List<String[]> getFiles() { return files; }

	public List<String[]> getModules() { return modules; } 

    public List<String[]> getUpdates() { return updates; }

	boolean isEmpty() {
		return
				name.isEmpty() &&
                keywords.isEmpty() &&
                description.isEmpty() &&
                dtcreated == null &&
                author[0].isEmpty() && author[1].isEmpty() &&
                files.isEmpty() &&
                modules.isEmpty() &&
                updates.isEmpty() &&
                updAuthors.isEmpty();
	}
}

