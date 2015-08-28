/* This file is part of SmartAss and contains the TemplatesItemModel class - the model class for smartass template.
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
package au.edu.uq.smartass.web;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;



/**
 * The model class for smartass template.
 */
public class TemplatesItemModel extends IntIdItemModel implements Serializable{
	//Stored data
	/** The template name */
	private String name = "";
	/** Template related keywords */
	private String keywords = "";
	/** The template description */
	private String description = "";
	/** The path to template in the repository */
	private String path = "";
	/** Has template questions example PDF in the repository? */
	private boolean hasQuestions;
	public boolean isHasQuestions() {
		return hasQuestions;
	}

	/** Has template solutions example PDF in the repository? */
	private boolean hasSolutions;
	/** Has template shortanswers example PDF in the repository? */
	private boolean hasShortanswers;
	/** The date when the template was created */
	private Date dtcreated;
	/** The date when the template was uploaded to repository */
	private Date dtuploaded;

	//Data from dictionaries
	/** The author of this template */
	private AuthorsItemModel author;
	
	
	//Detail tables
	/** Template classifications */
	List<ClassificationsItemModel> classifications = new Vector<ClassificationsItemModel>();
	/** Template related files */
	List<FilesItemModel> files = new Vector<FilesItemModel>();
	/** Modules used by this template */
	List<ModulesItemModel> modules = new Vector<ModulesItemModel>();
	/** The list of template updates */
	List<UpdatesItemModel> updates = new Vector<UpdatesItemModel>();

	//Repository manipulation transaction
	RepositoryStorageTransaction transaction;
	
	//Auxiliary members
//	RepositoryStorage storage;
	File tex_root; 
	String old_name = null, old_path = null;

	/**
	 * The getter for the template description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The setter for the template description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The getter for template related keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * The setter for template related keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * The getter for the template name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter for the template name
	 */
	public void setName(String name) {
		if(old_name==null)
			old_name = this.name;
		this.name = name;
	}

	/**
	 * The getter for the questions example PDF name
	 */
	public String getQuestions() {
		if(hasQuestions)
			return name + "_QUESTIONS.pdf";
		else
			return "";
	}

	/**
	 * The getter for the shortanswers example PDF name
	 */
	public String getShortanswers() {
		if(hasShortanswers)
			return name + "_SHORTANSWERS.pdf";
		else
			return "";
	}

	/**
	 * The getter for the solutions example PDF name
	 */
	public String getSolutions() {
		if(hasSolutions)
			return name + "_SOLUTIONS.pdf";
		else
			return "";
	}

	/**
	 * The getter for the path to template in the repository
	 */
	public String getPath() {
		return path;
	}

	/**
	 * The setter for the path to template in the repository
	 */
	public void setPath(String path) {
		old_path = this.path;
		this.path = path;
	}

	/**
	 * The getter for the template author
	 */
	public AuthorsItemModel getAuthor() {
		return author;
	}

	/**
	 * The setter for the template author
	 */
	public void setAuthor(AuthorsItemModel author) {
		this.author = author;
	}

	/**
	 * The getter for template classifications
	 */
	public List<ClassificationsItemModel> getClassifications() {
		return classifications;
	}

	/**
	 * The setter for template classifications
	 */
	public void setClassifications(List<ClassificationsItemModel> classifications) {
		this.classifications = classifications;
	}

	/**
	 * The getter for template related files
	 */
	public List<FilesItemModel> getFiles() {
		return files;
	}

	/**
	 * The setter for template related files
	 */
	public void setFiles(List<FilesItemModel> files) {
		this.files = files;
	}

	/**
	 * The getter for template related modules
	 */
	public List<ModulesItemModel> getModules() {
		return modules;
	}

	/**
	 * The setter for template related modules
	 */
	public void setModules(List<ModulesItemModel> modules) {
		this.modules = modules;
	}

	/**
	 * The getter for the template updates list
	 */
	public List<UpdatesItemModel> getUpdates() {
		return updates;
	}

	/**
	 * The setter for the template updates list
	 */
	public void setUpdates(List<UpdatesItemModel> updates) {
		this.updates = updates;
	}

	public Date getDtcreated() {
		return dtcreated;
	}

	public void setDtcreated(Date dtcreated) {
		this.dtcreated = dtcreated;
	}
	
	public Date getDtuploaded() {
		return dtuploaded;
	}

	public void setDtuploaded(Date dtuploaded) {
		this.dtuploaded = dtuploaded;
	}

	public void setHasQuestions(boolean hasQuestions) {
		this.hasQuestions = hasQuestions;
	}

	public boolean isHasSolutions() {
		return hasSolutions;
	}

	public void setHasSolutions(boolean hasSolutions) {
		this.hasSolutions = hasSolutions;
	}

	public boolean isHasShortanswers() {
		return hasShortanswers;
	}

	public void setHasShortanswers(boolean hasShortanswers) {
		this.hasShortanswers = hasShortanswers;
	}

	public String metadataToString() {
		StringBuffer m = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		m.append("%%META\n");
		if(author!=null) {
			m.append("%%AUTHOR ").append(author.getName()).append("\n");
			if(author.getDescription()!=null && author.getDescription().length()>0)
				m.append("%").append(author.getDescription().replaceAll("\\n", "\n%")).append("\n");
		}
		m.append("%%KEYWORDS ").append(keywords).append("\n");
		m.append("%%DESCRIPTION ").append(description.replaceAll("\\n", "\n%")).append("\n");
		if(getDtcreated()!=null)
			m.append("%%CREATED ").append(df.format(getDtcreated())).append("\n");
		
		for(FilesItemModel o: files) {
			m.append("%%FILE ").append(o.getName()).append("\n");
			if(o.getDescription()!=null && o.getDescription().length()>0)
				m.append("%").append(o.getDescription().replaceAll("\\n", "\n%")).append("\n");
		}
		
		for(ModulesItemModel o: modules) {
			String mpackg = o.getModulePackage();
			if(mpackg!=null && mpackg.length()>0)
				m.append("%%MODULE ").append(mpackg+".").append(o.getName()).append("\n");
			else
				m.append("%%MODULE ").append(o.getName()).append("\n");
			if(o.getDescription()!=null && o.getDescription().length()>0)
				m.append("%").append(o.getDescription().replaceAll("\\n", "\n%")).append("\n");
			if(o.getParameters()!=null && o.getParameters().length()>0)
				m.append("%%PARAMETERS ").append(o.getParameters().replaceAll("\\n", "\n%")).append("\n");
		}

		List<AuthorsItemModel> updAuthors = new ArrayList<AuthorsItemModel>();
		for(UpdatesItemModel o: updates) {
			m.append("%%UPDATE ").append(df.format(o.getUpdateDate())).append(o.getAuthor().getName()).append("\n");
			if(o.getComment()!=null && o.getComment().length()>0)
				m.append("%").append(o.getComment().replaceAll("\\n", "\n%")).append("\n");
			if(!o.getAuthor().equals(author) && !updAuthors.contains(o.getAuthor()))
				updAuthors.add(o.getAuthor());
		}
		for(AuthorsItemModel o: updAuthors) {
			m.append("%%UPDATE AUTHOR ").append(o.getName()).append("\n");
			if(o.getDescription()!=null && o.getDescription().length()>0)
				m.append("%").append(o.getDescription().replaceAll("\\n", "\n%")).append("\n");
		}
		
		for(ClassificationsItemModel c: classifications) 
			m.append("%%CATEGORY " + c.getFullName() + "\n");
			
		m.append("%%META END\n");
		return m.toString();
	}
	
	public void clear() {
		setName("");
		setKeywords("");
		setDescription("");
		getClassifications().clear();
		getModules().clear();
		getFiles().clear();
		getUpdates().clear();
	}
	
	public void setTransaction(RepositoryStorageTransaction transaction) {
		this.transaction = transaction;
	}
	
	public RepositoryStorageTransaction getTransaction() {
		return transaction;
	}
}
