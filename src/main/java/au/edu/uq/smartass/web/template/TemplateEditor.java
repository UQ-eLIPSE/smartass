/* This file is part of SmartAss and contains the TemplateEditor class that 
 * contains a set of functions that is used to import and edit a template. 
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.IntIdItemModel;
import au.edu.uq.smartass.web.ModulesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UpdatesItemModel;
import au.edu.uq.smartass.web.jdbc.AuthorsDao;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;
import au.edu.uq.smartass.web.jdbc.FilesDao;
import au.edu.uq.smartass.web.jdbc.ModulesDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The TemplateEditor class contains a set of functions that is used to import and edit a template.
 */
public class TemplateEditor {
	AuthorsDao authorsDao;
	FilesDao filesDao;
	ModulesDao modulesDao;
	TemplatesDao templatesDao;
	RepositoryStorage storage;
	ClassificationsDao classificationsDao;
	
	/**
	 * Prepares metadata to analyze and import
	 * 
	 * @param template		template model to be filled with imported data
	 * @param file			raw template text that contains metadata and template's body
	 * 
	 * @return				pre-parsed metadata
	 * @throws IOException
	 */
	public PreparsedMetadataModel prepareImport(TemplateImportModel template, String file) throws IOException {
		Vector<String> meta = new Vector<String>();
		String s = splitMetadata(file, meta);
		template.setData(s);
		return preparseMetadata(template, meta);
	}
	
	/**
	 * Extracts metadata block from the template text 
	 * 
	 * @param src		raw template text
	 * @param meta		placeholder for metadata strings
	 * @return			template text stripped from metadata
	 * @throws IOException
	 */
	public String splitMetadata(String src, Vector<String> meta) throws IOException {
		BufferedReader in = new BufferedReader(new StringReader(src));
		StringWriter tmp = new StringWriter();
		
	    String s;
	    while((s=in.readLine())!=null && !(s.contains("%%META"))) 
	    	tmp.write(s + "\n");
	    while((s=in.readLine())!=null && !(s.contains("%%META END"))) {
	    	meta.add(s);
	    }
	    while((s=in.readLine())!=null)
	    	tmp.write(s + "\n");
	    
	    return tmp.toString();
	}
	
	/**
	 * Pre-parses metadata. "Pre-pase" means that the data is red 
	 * from the template, recognized as files, modules etc but stored as plain strings
	 * without SmartAss objects creation.
	 * 
	 * @param template	template model to be filled with imported data
	 * @param meta		metadata strings
	 * @return
	 */
	protected PreparsedMetadataModel preparseMetadata(TemplateImportModel template, Vector<String> meta) {
		PreparsedMetadataModel premeta = new PreparsedMetadataModel(); 

		if(meta==null || meta.size()==0)
			return premeta;
		
		int state=0;
		String[] sfile = {};
		String[] supdate = {};
		String[] smodule = {};
		String[] supdauthor = {};
		
		for(String s : meta) {
			if(s.indexOf("%%")==0) {
				if(state==5) {
					if(s.contains("%%PARAMETERS")) {
						state = 6;
					} else {
						premeta.getModules().add(smodule);
						state = 0;
					}
				} else {
					switch(state) {
					case 2:
						premeta.getFiles().add(sfile);
						break;
					case 3:
						premeta.getUpdates().add(supdate);
						break;
					case 5:
						break;
					case 6:
						premeta.getModules().add(smodule);
						break;
					case 7:
						premeta.getUpdAuthors().add(supdauthor);
					}
					state = 0;
				}
			}

			switch(state) {
			case 0:
				if(s.contains("%%AUTHOR")) {
					premeta.setAuthor(new String[]{s.replace("%%AUTHOR", "").trim(), ""});
					state = 4;
				} else if(s.contains("%%KEYWORDS")) {
					premeta.setKeywords(s.replaceFirst("%%KEYWORDS", "").trim());
				} else if(s.contains("%%CREATED")) {
					premeta.setDtcreated(s.replaceFirst("%%CREATED", "").trim());
				} else if(s.contains("%%DESCRIPTION")) {
					premeta.setDescription(s.replaceFirst("%%DESCRIPTION", "").trim());
					state = 1;
				} else if(s.contains("%%FILE")) {
					sfile = new String[]{s.replaceFirst("%%FILE", "").trim(), ""};
					state = 2;
				} else if(s.contains("%%MODULE")) {
					smodule = new String[]{s.replaceFirst("%%MODULE", "").trim(), "", "", ""};
					state = 5;
				} else if(s.contains("%%UPDATE AUTHOR")) {
					supdauthor = new String[]{s.replaceFirst("%%UPDATE AUTHOR", "").trim(), ""};
					state = 7;
				} else if(s.contains("%%UPDATE")) {
					String[] spl = s.replaceFirst("%%UPDATE", "").split(",", 2);
					if(spl.length==1)
						supdate = new String[]{spl[0], "", ""};
					else
						supdate = new String[]{spl[0].trim(), spl[1].trim(), ""};
					state = 3;
				}  
				break;
			case 1:
				premeta.setDescription(premeta.getDescription() + "\n" + s.replaceFirst("%", ""));
				break;
			case 2:
				sfile[1] = sfile[1] + "\n" + s.replaceFirst("%", "");
				break;
			case 3:
				supdate[2] = supdate[2] + "\n" + s.replaceFirst("%", "");
				break;
			case 4:
				premeta.getAuthor()[1] = premeta.getAuthor()[1] + "\n" + s.replaceFirst("%", "");
				break;
			case 5:
				smodule[2] = smodule[2] + "\n" + s.replaceFirst("%", "");
				break;
			case 6:
				smodule[3] = smodule[3] + "\n" + s.replaceFirst("%%PARAMETERS", "").replaceFirst("%", "").trim();
				break;
			case 7:
				supdauthor[1] = supdauthor[1] + "\n" + s.replaceFirst("%", "");
				break;
			}
		}

		if(state==5) {
			premeta.getModules().add(smodule);
			state = 0;
		} else {
			switch(state) {
			case 2:
				premeta.getFiles().add(sfile);
				break;
			case 3:
				premeta.getUpdates().add(supdate);
				break;
			case 5:
				break;
			case 6:
				premeta.getModules().add(smodule);
				break;
			case 7:
				premeta.getUpdAuthors().add(supdauthor);
			}
			state = 0;
		}
		return premeta;
	}
	

	protected AuthorsItemModel parseAuthor(String name, String description) {
		AuthorsItemModel author;
		author = authorsDao.getItem(name);
		if(author==null) {
			author = new AuthorsItemModel();
			author.setName(name);
			author.setDescription(description);
		}
		return author;
	}
	
	/**
	 * Parses pre-parsed metadata. After this function call template properties are set
	 * to the SmartAss objects created basing on the content of the pre-parsed metadata  
	 * 
	 * @param template	template model to be filled with imported data			
	 * @param premeta	pre-parsed metadata
	 * @param classId	id of the classification to which template will belong 
	 */
	public void parseMetadata(TemplateImportModel template, PreparsedMetadataModel premeta, 
			int classId) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		AuthorsItemModel author;
		
		template.clear();
		template.setName(premeta.getName());
		template.setKeywords(premeta.getKeywords());
		template.setDescription(premeta.getDescription());
		try { 
			template.setDtcreated(dateformat.parse(premeta.getDtcreated()));
		} catch (Exception e) {}
		if(premeta.getAuthor()[0].length()>0) { 
			template.setAuthor(parseAuthor(premeta.getAuthor()[0], premeta.getAuthor()[1]));
			if(template.getAuthor().getId()!=0)
				template.setImportAuthor(true);
		}
		
		ClassificationsItemModel clss = classificationsDao.getItem(classId);
		if(clss!=null)
			template.getClassifications().add(clss);

		for(String[] sm : premeta.getModules()) {
			String mods[] = sm[0].split("\\.");
			String modpackage = "";
			if(mods.length>1)
				modpackage = mods[0];
			for(int i=1;i<mods.length-1;i++)
				modpackage = modpackage + "." + mods[i];
			String module = mods[mods.length-1];
			ModulesItemModel m = modulesDao.getItem(module, modpackage);
			if(m==null) {
				m = new ModulesItemModel();
				m.setName(module);
				m.setModulePackage(modpackage);
				m.setDescription(sm[2]);
				m.setParameters(sm[3]);
			}
			if(!template.getModules().contains(m)) {
				template.getModules().add(m);
				if(m.getId()!=0)
					template.getModulesResolver().getFounds().add(true);
				else
					template.getModulesResolver().getFounds().add(false);
			}
		}
		
		for(String[] sf : premeta.getFiles()) {
			FilesItemModel f = filesDao.getItem(sf[0]);
			if(f==null) {
				f = new FilesItemModel();
				f.setName(sf[0]);
				f.setDescription(sf[1]);
			}
			if(!template.getFiles().contains(f)) {
				template.getFiles().add(f);
				if(f.getId()!=0)
					template.getFilesResolver().getFounds().add(true);
				else
					template.getFilesResolver().getFounds().add(false);
			}
		}
		
		for(String[] sa : premeta.getUpdAuthors()) {
			if(sa[0].length()>0) {
				author = template.getUpdAuthors().get(sa[0]);
				if(author==null) {
					author = parseAuthor(sa[0], sa[1]); //find in the DB or create new
					template.addUpdateAuthor(author);
					if(author.getId()!=0)
						template.getUpdAuthorsResolver().getFounds().add(true);
					else
						template.getUpdAuthorsResolver().getFounds().add(false);
				}
			}
		}
		
		for(String[] su : premeta.getUpdates()) {
			if(su[1].length()>0) {
				author = template.getUpdAuthors().get(su[1]);
				if(author==null) {
					author =  parseAuthor(su[1], ""); //find in the DB or create new
					template.addUpdateAuthor(author);
					if(author.getId()!=0)
						template.getUpdAuthorsResolver().getFounds().add(true);
					else
						template.getUpdAuthorsResolver().getFounds().add(false);
				} 
				UpdatesItemModel u = new UpdatesItemModel();
				try {
					u.setUpdateDate(dateformat.parse(su[0]));
				} catch (ParseException e) {}
				u.setAuthor(author);
				u.setComment(su[2]);
				template.getUpdates().add(u);
				template.getUpdatesResolver().getFounds().add(false);
				template.getUpdatesResolver().getImports().add(true);
			}
		}
		
		template.getFilesResolver().initImports();
		template.getModulesResolver().initImports();
		//template.getUpdatesResolver().initImports();
		template.getUpdAuthorsResolver().initImports();
	}
	
	public boolean afterSelectMetadata(TemplateImportModel template, MessageContext mcontext) {
		if(template.getName().length()==0) {
			mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
				"\"Template's name\" field is required!").build());
			return false;
		}
		if(!checkTemplateName(template.getName(), 0, mcontext))
			return false;
		for(int i=0; i<template.getUpdates().size();i++)
			if(template.getUpdatesResolver().getImports().get(i)) {
				for(int j=0;j<template.getUpdAuthorsList().size();j++) {
					AuthorsItemModel author = template.getUpdAuthorsList().get(j);
					if(author.getId()!=0 && template.getUpdates().get(i).getAuthor().getId()==author.getId()
							|| author.getId()==0 && template.getUpdates().get(i).getAuthor().getName().equals(author.getName()))
						template.getUpdAuthorsResolver().getImports().set(j, true);
				}
			}
		return true;
	}
	
	/**
	 * Stores examples PDFs to the repository storage
	 * 
	 * @param template	template model to be filled with imported data
	 * @param ex		examples PDFs that is uploaded by user
	 * @throws IOException
	 */
	public void uploadExamples(TemplatesItemModel template, UploadExamplesModel ex) throws IOException {
		//Logger l = Logger.getLogger(getClass());
		template.getName();
		if(ex.getQuestions().length!=0) {
			template.setHasQuestions(true);
			//l.debug(template.getQuestions());
			storage.setFile(1, "", template.getQuestions(), ex.getQuestions(), template.getTransaction());
		} 
		if(ex.getSolutions().length!=0) {
			template.setHasSolutions(true);
			//l.debug(template.getSolutions());
			storage.setFile(1, "", template.getSolutions(), ex.getSolutions(), template.getTransaction());
		}
		if(ex.getShortanswers().length!=0) {
			template.setHasShortanswers(true);
			//l.debug(template.getShortanswers());
			storage.setFile(1, "", template.getShortanswers(), ex.getShortanswers(), template.getTransaction());
		}
	}

	/**
	 * Some actions that is executed before save newly imported template to the repository
	 * 
	 * @param template	template model to import
	 */
	public void prepareSave(TemplateImportModel template) {
		//modules
		int delta = 0;
		for(int i=0;i<template.getModulesResolver().getImports().size();i++) 
			if(!template.getModulesResolver().getImports().get(i)) {
				template.getModules().remove(i-delta++);
			}
		//files
		delta = 0;
		for(int i=0;i<template.getFilesResolver().getImports().size();i++) 
			if(!template.getFilesResolver().getImports().get(i)) {
				//template.getFilesResolver().getImports().remove(i-delta);
				template.getFiles().remove(i-delta++);
			}
		//updates
		delta = 0;
		for(int i=0;i<template.getUpdatesResolver().getImports().size();i++) 
			if(!template.getUpdatesResolver().getImports().get(i)) {
				//template.getUpdatesResolver().getImports().remove(i-delta);
				template.getUpdates().remove(i-delta++);
			}
		//update authors
		delta = 0;
		for(int i=0;i<template.getUpdAuthorsResolver().getImports().size();i++) 
			if(!template.getUpdAuthorsResolver().getImports().get(i)) {
				//template.getUpdAuthorsResolver().getImports().remove(i-delta);
				template.getUpdAuthors().remove(i-delta++);
			}
	}
	
	/**
	 * Saves newly imported template to the repository
	 * 
	 * @param template	template model to import
	 * @throws Exception
	 */
	public void save(TemplatesItemModel template) throws Exception {
		if(template.getAuthor().getId()==0)
			authorsDao.updateItem(template.getAuthor());
		for(ModulesItemModel o: template.getModules())
			if(o.getId()<=0)
				modulesDao.updateItem(o);
		for(FilesItemModel o: template.getFiles())
			if(o.getId()<=0)
				filesDao.updateItem(o);
		
		if(template instanceof TemplateImportModel) {
			TemplateImportModel imp = (TemplateImportModel) template;
			for(AuthorsItemModel o: imp.getUpdAuthorsList()) {
				if(o.getId()<=0)
					authorsDao.updateItem(o);
				for(UpdatesItemModel u: imp.getUpdates()) 
					if(u.getAuthor().getId()==0 && imp.getUpdAuthors().get(u.getAuthor().getName())==o)
						u.setAuthor(o);
			}
		}
		
		template.getTransaction().commit(storage);

		if(template instanceof TemplateImportModel) 
			storage.setFile(0, "", template.getName()+".tex", ((TemplateImportModel) template).getData().getBytes());
		else {
			TemplatesItemModel old = templatesDao.getItem(template.getId());
			if(!old.getName().equals(template.getName())) {
				storage.renameFile(0, "", old.getName()+".tex", "", template.getName()+".tex");
				if(old.isHasQuestions())
					if(!template.isHasQuestions()) 
						storage.deleteFile(1, "", old.getQuestions());
					else if(!old.getQuestions().equals(template.getQuestions()))
						storage.renameFile(1, "", old.getQuestions(), "", template.getQuestions());
				if(old.isHasSolutions())
					if(!template.isHasSolutions()) 
						storage.deleteFile(1, "", old.getSolutions());
					else if(!old.getSolutions().equals(template.getSolutions()))
						storage.renameFile(1, "", old.getSolutions(), "", template.getSolutions());
				if(old.isHasShortanswers())
					if(!template.isHasShortanswers()) 
						storage.deleteFile(1, "", old.getShortanswers());
					else if(!old.getShortanswers().equals(template.getShortanswers()))
						storage.renameFile(1, "", old.getShortanswers(), "", template.getShortanswers());
			}
		}

		templatesDao.updateItem(template);
	}
	
	/**
	 * Removes metadata item from the list
	 * 
	 * @param list	list of metadata items
	 * @param i		the position of item to remove
	 */
	public void removeFromList(List<IntIdItemModel> list, int i) {		
		list.remove(i);
	}
	
	/**
	 * Removes metadata item from the list only if there are more then one 
	 * items left
	 * 
	 * @param list	list of metadata items
	 * @param i		the position of item to remove
	 */
	public void removeFromListNotLast(List<IntIdItemModel> list, int i) {
		if(list.size()>1)	
			list.remove(i);
	}
			
	/**
	 * Deletes the example PDF
	 * 
	 * @param template		template that is edited
	 * @param kind			1 - questions, 2 - solutions, 3 - short answers
	 * @throws IOException
	 */
	public void deleteExample(TemplatesItemModel template, int kind) throws IOException {
		switch (kind) {
		case 0:
			storage.deleteFile(1, "", template.getQuestions(), template.getTransaction());
			template.setHasQuestions(false);
			break;
		case 1:
			storage.deleteFile(1, "", template.getSolutions(), template.getTransaction());
			template.setHasSolutions(false);
			break;
		case 2:
			storage.deleteFile(1, "", template.getShortanswers(), template.getTransaction());
			template.setHasShortanswers(false);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Checks template name for correctness and consistency
	 * 
	 * @param name		template name
	 * @param id		template id
	 * @param mcontext	context from Spring Webflow
	 * @return			true if tempalte name is OK
	 */
	public boolean checkTemplateName(String name, int id, MessageContext mcontext) {
		//Logger log = Logger.getLogger(getClass());
		//log.debug("Template name: " + name);
		TemplatesItemModel t = templatesDao.getItem(name);
		if(t==null || t.getId()==id)
			return true;
		mcontext.addMessage(new MessageBuilder().error().source("tempaltename").defaultText(
				"Template with the name \"" + name + "\" already exists in the repository!").build());
		//log.debug("Error: " + mcontext.getAllMessages()[0].toString());
		return false;
	}
	
	/**
	 * Starts the repository storage transaction for given template. All changes to the storage
	 * that is made during the transaction active will be done all together on transaction commit or
	 * undone on transaction rallback.
	 *  
	 * @param template		template that is edited or imported
	 */
	public void startRepositoryTransaction(TemplatesItemModel template) {
		template.setTransaction(storage.getTransaction());
	}
	
	/**
	 * Commits the repository storage transaction for given template. All changes to the storage
	 * that is made during the transaction active will be done all together on transaction commit or
	 * undone on transaction rallback.
	 *  
	 * @param template		template that is edited or imported
	 */
	public void commitRepositoryTransaction(TemplatesItemModel template) throws Exception {
		template.getTransaction().commit(storage);
	}

	/**
	 * Rollbacks the repository storage transaction for given template. All changes to the storage
	 * that is made during the transaction active will be done all together on transaction commit or
	 * undone on transaction rallback.
	 *  
	 * @param template		template that is edited or imported
	 */
	public void rollbackRepositoryTransaction(TemplatesItemModel template) throws Exception {
		template.getTransaction().rollback(storage);
	}

	public void setAuthorsDao(AuthorsDao authorsDao) {
		this.authorsDao = authorsDao;
	}
	
	public void setFilesDao(FilesDao filesDao) {
		this.filesDao = filesDao;
	}
	
	public void setModulesDao(ModulesDao modulesDao) {
		this.modulesDao = modulesDao;
	}
	
/*	public void setUpdatesDao(UpdatesDao updatesDao) {
		this.updatesDao = updatesDao;
	}
	*/
	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
	
	public void setStorage(RepositoryStorage storage) {
		this.storage = storage;
	}
	
	public void setClassificationsDao(ClassificationsDao classificationsDao) {
		this.classificationsDao = classificationsDao;
	}
}
