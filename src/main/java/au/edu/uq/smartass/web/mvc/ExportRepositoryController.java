/* This file is part of SmartAss and contains the ExportRepositoryController class that 
 * extracts all templates with metadata and template-related files and packs them into
 * the zip file. Exported data then can be used to clone the repository content to 
 * some other site powered by SmartAss engine or to restore the same site content after serious 
 * crash. 
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.Zip;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The ExportRepositoryController class  
 * extracts all templates with metadata and template-related files and packs them into
 * the zip file. Exported data then can be used to clone the repository content to 
 * some other site powered by SmartAss engine or to restore the same site content after serious 
 * crash. 
 */
public class ExportRepositoryController extends AbstractController {
	private RepositoryStorage repositoryStorage;
	private TemplatesDao templatesDao;

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * Its only differences from default behavior is that it returns redirect view that sends client
	 * to the user list page.   
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_all = request.getParameter("all");
		boolean all = (s_all!=null && s_all.equals("1"));
		//get templates metadata from the repository
		List<TemplatesItemModel> templates;
		if(all) 
			 templates = templatesDao.select(); //get a list of all templates from the repository
		else {
			templates = new ArrayList<TemplatesItemModel>();
			String s_ids = request.getParameter("id");
			if(s_ids!=null) {
				String[] ids = s_ids.split(",");
				if(ids.length>0) 
					for(int i=0;i<ids.length;i++) 
						templates.add(templatesDao.getItem(Integer.parseInt(ids[i])));
			}
		}
		
		//export metadata and files for selected templates
		File temp = new File(System.getProperty("java.io.tmpdir"));
		File export_path = File.createTempFile("assexp", "", temp);
		export_path.delete();
		export_path.mkdir();
		File tex_path = new File(export_path, "tex");
		File file_path = new File(export_path, "files");
		File pdf_path = new File(export_path, "pdf");
		tex_path.mkdir();
		file_path.mkdir();
		pdf_path.mkdir();
		Set<FilesItemModel> files = new HashSet<FilesItemModel>();
		List<String> pdfs = new ArrayList<String>();
		for(TemplatesItemModel item : templates) {
			InputStream in = repositoryStorage.getFile(0, "", item.getName()+".tex");
			if(in!=null) {
				//write template file with metadata
				File tfile = new File(tex_path, item.getName()+".tex");
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tfile));
				out.write(item.metadataToString().getBytes());
				FileCopyUtils.copy(in, out);
				
				//add template-related files to be exported
				for(FilesItemModel f_item : item.getFiles()) 
					files.add(f_item);
				if(item.isHasQuestions())
					pdfs.add(item.getName()+"_QUESTIONS.pdf");
				if(item.isHasSolutions())
					pdfs.add(item.getName()+"_SOLUTIONS.pdf");
				if(item.isHasQuestions())
					pdfs.add(item.getName()+"_SHORTANSWERS.pdf");
			} //!!! Here should be reaction on file that is not found
		}
		for(FilesItemModel item: files) {
			File tfile = new File(file_path, item.getName());
			InputStream in = repositoryStorage.getFile(2, "", item.getName());
			if(in!=null) {
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tfile));
				FileCopyUtils.copy(in, out);
			}	// TODO: Here should be reaction on file that is not found
		}
		for(String item: pdfs) {
			File tfile = new File(pdf_path, item);
			InputStream in = repositoryStorage.getFile(1, "", item);
			if(in!=null) {
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tfile));
				FileCopyUtils.copy(in, out);
			} // TODO: Here should be reaction on file that is not found
		}
		
		File zipfile = new File(export_path, "export.zip");

		File[] tex_list, file_list, pdf_list;
		ZipOutputStream zip = Zip.createZip(zipfile);
		try {
			tex_list = tex_path.listFiles();
			for(int i=0; i<tex_list.length;i++)
				Zip.addFileToZip(zip, "tex/"+tex_list[i].getName(), tex_list[i]);
			file_list = file_path.listFiles();
			for(int i=0; i<file_list.length;i++)
				Zip.addFileToZip(zip, "files/"+file_list[i].getName(), file_list[i]);
			pdf_list = pdf_path.listFiles();
			for(int i=0; i<pdf_list.length;i++)
				Zip.addFileToZip(zip, "pdf/"+pdf_list[i].getName(), pdf_list[i]);
		} finally {
			zip.close();
		}
		for(int i=0; i<tex_list.length;i++)
			tex_list[i].delete();
		for(int i=0; i<file_list.length;i++)
			file_list[i].delete();
		for(int i=0; i<pdf_list.length;i++)
			pdf_list[i].delete();
		tex_path.delete();
		file_path.delete();
		pdf_path.delete();
		
		String content_type = getServletContext().getMimeType("export.zip");
		if(content_type==null)
			content_type = "application/zip";
		response.setContentType(content_type);
		response.setHeader("Content-Disposition", "attachment; filename=\"export.zip\"");
		
		InputStream in = new BufferedInputStream(new FileInputStream(zipfile));
		FileCopyUtils.copy(in, response.getOutputStream());
		
		return null;
	}
	
	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}

	public void setRepositoryStorage(RepositoryStorage repositoryStorage) {
		this.repositoryStorage = repositoryStorage;
	}
}
