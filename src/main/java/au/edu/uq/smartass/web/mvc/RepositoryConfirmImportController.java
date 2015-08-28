/* This file is part of SmartAss and contains the RepositoryConfirmImportController class that 
 * makes some preparations to the import then asks user to confirm the import to the repository
 * then does actual changes to the repository depending on user decision.    
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hp.hpl.jena.util.FileUtils;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;
import au.edu.uq.smartass.web.jdbc.FilesDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;
import au.edu.uq.smartass.web.template.PreparsedMetadataModel;
import au.edu.uq.smartass.web.template.TemplateEditor;
import au.edu.uq.smartass.web.template.TemplateImportModel;

/**
 * The RepositoryConfirmImportController class  
 * makes some preparations to the import then asks user to confirm the import to the repository 
 * then does actual changes to the repository depending on user decision.
 * The import of incorrect data can badly damage the content of the repository so
 * this controller tries to evaluate the integrity of data to be imported and asks user for 
 * confirmation of import when some collisions or errors are found.
 */
public class RepositoryConfirmImportController extends UserRequieredFormController {
	TemplateEditor templateEditor;
	TemplatesDao templatesDao;
	RepositoryStorage storage;
	FilesDao filesDao;
	ClassificationsDao classificationsDao;
	
	/**
	 * The ErrorItem class contains the description of error occurred.
	 */
	public class ErrorItem {
		private String templateName;
		private List<String> files;
		private boolean noQuestions, noSolutions, noShortanswers;
		private boolean templateExists;
		private int errorLevel;

		public String getTemplateName() {
			return templateName;
		}
		
		public void setTemplateName(String templateName) {
			this.templateName = templateName;
		}
		
		public List<String> getFiles() {
			return files;
		}
		
		public void setFiles(List<String> files) {
			this.files = files;
		}
		
		public boolean isNoQuestions() {
			return noQuestions;
		}
		
		public void setNoQuestions(boolean noQuestions) {
			this.noQuestions = noQuestions;
		}
		
		public boolean isNoSolutions() {
			return noSolutions;
		}
		
		public void setNoSolutions(boolean noSolutions) {
			this.noSolutions = noSolutions;
		}
		
		public boolean isNoShortanswers() {
			return noShortanswers;
		}
		
		public void setNoShortanswers(boolean noShortanswers) {
			this.noShortanswers = noShortanswers;
		}
		
		public boolean isTemplateExists() {
			return templateExists;
		}
		
		public void setTemplateExists(boolean templateExists) {
			this.templateExists = templateExists;
		}
		
		public int getErrorLevel() {
			return errorLevel;
		}
		
		public void setErrorLevel(int errorLevel) {
			this.errorLevel = errorLevel;
		}
	}
	
	/**
	 * The Command class is used to send the data through Spring engine
	 * to the view and page in user's browser.
	 */
	public class Command {
		List<String> selectedIds = new ArrayList<String>();
		List<ErrorItem> errors = new ArrayList<ErrorItem>();
		
		public List<String> getSelectedIds() {
			return selectedIds;
		}
		
		public void setSelectedIds(List<String> selectedIds) {
			this.selectedIds = selectedIds;
		}
		
		public List<ErrorItem> getErrors() {
			return errors;
		}
	}

	
	/**
	 * Set the rights that required to do this action to the administrative rights
	 */
	public RepositoryConfirmImportController() {
		rightsMask = UserItemModel.RIGHT_ADMIN;
		setCommandName("item"); 
	}
	
	@Override
	/**
	 * Unpacks and processes data to be imported to the repository. 
	 * Evaluate the integrity of data to be imported and prepares the information about errors
	 * and collisions to ask user for confirmation.
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Command cmd = new Command();
		File import_path = (File) request.getSession().getAttribute("importPath");
		if(import_path==null)
			throw new Exception("Import path does not spesified. Can't import");
		
		//get the list of files to import
		File tex_path = new File(import_path, "tex");
		File pdf_path = new File(import_path, "pdf");
		File files_path = new File(import_path, "files");
		File[] tex_list, files_list, pdf_list;
		tex_list = tex_path.listFiles();
		List<File> tl = Arrays.asList(tex_list);
		Collections.sort(tl);
		tex_list = tl.toArray(tex_list);
		files_list = files_path.listFiles();
		pdf_list = pdf_path.listFiles();
		
		//Analyze each template's metadata
		String smeta = "Data imported: \n";
		for(int i=0;i<tex_list.length;i++) {
			
			TemplateImportModel template = readTemplate(tex_list[i]);
			TemplatesItemModel ex_template = templatesDao.getItem(template.getName());
			
			ErrorItem err = new ErrorItem();
			err.setTemplateName(template.getName());
			
			if(ex_template!=null) {
				err.setErrorLevel(1);
				err.setTemplateExists(true);
			}

			if(template.isHasQuestions() && !(new File(pdf_path, template.getName()+"_QUESTIONS.pdf")).exists()) {
				err.setErrorLevel(2);
				err.setNoQuestions(true);
			}
			if(template.isHasSolutions() && !(new File(pdf_path, template.getName()+"_SOLUTIONS.pdf")).exists()) {
				err.setErrorLevel(2);
				err.setNoSolutions(true);
			}
			if(template.isHasShortanswers() && !(new File(pdf_path, template.getName()+"_SHORTANSWERS.pdf")).exists()) {
				err.setErrorLevel(2);
				err.setNoShortanswers(true);
			}
				
			for(FilesItemModel f : template.getFiles())
				if(!(new File(files_path, f.getName())).exists()) {
					err.setErrorLevel(3);
					err.getFiles().add(f.getName());
				}
						
			if(err.getErrorLevel()>0)
				cmd.getErrors().add(err);
		}

		return cmd;
	}
	
	@Override
	/**
	 * Do actual changes to the repository depending on user decision.  
	 */
	protected ModelAndView doUpdate(Object command, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		File import_path = (File) request.getSession().getAttribute("importPath");
		//TODO: what if import_path doesn't exists?
		File tex_path = new File(import_path, "tex");
		File pdf_path = new File(import_path, "pdf");
		File files_path = new File(import_path, "files");
		
		Command cmd = (Command) command;
		List<File> tex_list = new ArrayList<File>();
		if(request.getParameter("selected")!=null) { //import selected only
			for(String s : cmd.getSelectedIds())
				tex_list.add(new File(tex_path, s+".tex"));
		} else if(request.getParameter("all")!=null) { //import all
			File[] files = tex_path.listFiles();
			for(int i=0; i<files.length;i++)
				tex_list.add(files[i]);
		} else { //anything else is treated as cancel
			RedirectView exit = new RedirectView("repository.htm"); 
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);
		}
		
		List<String> imported_files = new ArrayList<String>();
		for(File tf : tex_list) {
			TemplateImportModel template = readTemplate(tf);
			TemplatesItemModel ex_template = templatesDao.getItem(template.getName());
			if(ex_template!=null) {
				templatesDao.deleteItem(ex_template.getId()); 
				//delete files from the repository
				storage.deleteFile(0, "", ex_template.getName()+".tex");
				storage.deleteFile(1, "", ex_template.getName()+"_QUESTIONS.pdf");
				storage.deleteFile(1, "", ex_template.getName()+"_SOLUTIONS.pdf");
				storage.deleteFile(1, "", ex_template.getName()+"_SHORTANSWERS.pdf");
			}

//			templatesDao.updateItem(template);

			try { //ignore all file-related errors
				File t = new File(pdf_path, template.getName()+"_QUESTIONS.pdf");
				if(t.exists()) {
					template.setHasQuestions(true);
					storage.setFile(1, "", template.getName()+"_QUESTIONS.pdf", new FileInputStream(t));
				}
				t = new File(pdf_path, template.getName()+"_SOLUTIONS.pdf");
				if(t.exists()) {
					template.setHasSolutions(true);
					storage.setFile(1, "", template.getName()+"_SOLUTIONS.pdf", new FileInputStream(t));
				}
				t = new File(pdf_path, template.getName()+"_SHORTANSWERS.pdf");
				if(t.exists()) {
					template.setHasShortanswers(true);
					storage.setFile(1, "", template.getName()+"_SHORTANSWERS.pdf", new FileInputStream(t));
				}
				
				for(FilesItemModel fi: template.getFiles()) {
					if(!imported_files.contains(fi.getName())) {
						FileInputStream fis = new FileInputStream(new File(files_path, fi.getName()));
						storage.setFile(2, "", fi.getName(), fis);
						imported_files.add(fi.getName());
					}
				}
			} catch (Exception e) {	}

			for(ClassificationsItemModel c: template.getClassifications()) {
				if(c.getId()==0)
					classificationsDao.updateItem(c);
			}

			templateEditor.save(template);
		}


		RedirectView exit = new RedirectView("repository.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}
	
	private TemplateImportModel readTemplate(File file) throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file)); 
		ByteArrayOutputStream tmp_templ = new ByteArrayOutputStream((int)file.length()); //guess that we will never get template lager than 2^32 bytes in size to import
		FileCopyUtils.copy(fin, tmp_templ);
		TemplateImportModel template = new TemplateImportModel();
		templateEditor.startRepositoryTransaction(template);
		PreparsedMetadataModel pre_meta = templateEditor.prepareImport(template, tmp_templ.toString());
		templateEditor.parseMetadata(template, pre_meta, 0);
		template.setName(file.getName().split("\\.")[0]);

		BufferedReader in = new BufferedReader(new StringReader(tmp_templ.toString()));
		StringWriter tmp = new StringWriter();
		
	    String s;
	    while((s=in.readLine())!=null && !(s.contains("%%META END")))
	    	if(s.indexOf("%%CATEGORY")==0) {
	    		String[] ct = s.split("\\s", 2);
	    		if(ct.length>1) {
	    			ct = ct[1].split("/");
	    			ct[0] = ct[0].trim();
	    			ClassificationsItemModel first = (ClassificationsItemModel) classificationsDao.getItem(ct[0]);
	    			if(first==null) {
	    				first = new ClassificationsItemModel();
	    				first.setName(ct[0]);
	    			}
	    			if(ct.length==1)
	    				template.getClassifications().add(first);
	    			else {
	    				ct[1] = ct[1].trim();
	    				List<ClassificationsItemModel> classs = classificationsDao.getItems(first.getId(), ct[1]);
	    				ClassificationsItemModel second;
	    				if(classs.size()==0) {
	    					second = new ClassificationsItemModel();
	    					second.setName(ct[1]);
	    					second.setParentModel(first);
	    				} else
	    					second = classs.get(0);
	    				template.getClassifications().add(second);
	    			}
	    		}
	    	}
		
		return template;
	}
	
    public void setTemplateEditor(TemplateEditor templateEditor) {
		this.templateEditor = templateEditor;
	}
    
    public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
    
    public void setStorage(RepositoryStorage storage) {
		this.storage = storage;
	}
    
    public void setFilesDao(FilesDao filesDao) {
		this.filesDao = filesDao;
	}
    
    public void setClassificationsDao(ClassificationsDao classificationsDao) {
		this.classificationsDao = classificationsDao;
	}
}
