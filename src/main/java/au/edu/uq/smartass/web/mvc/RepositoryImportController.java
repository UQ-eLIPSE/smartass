/* This file is part of SmartAss and contains the RepositoryImportController class  
 * that reads data to import sent by user and prepares them to analysis and import that
 * RepositoryConfirmImportController performs.   
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

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.RepositoryImportFileModel;
import au.edu.uq.smartass.web.UserItemModel;

/**
 * the RepositoryImportController class reads data to import sent by user 
 * and prepares them to analysis and import that RepositoryConfirmImportController performs.
 */
public class RepositoryImportController extends UserRequieredFormController {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( RepositoryImportController.class );
	
	public RepositoryImportController() {
		rightsMask = UserItemModel.RIGHT_ADMIN;
		setCommandName("item"); 
	}
	
	@Override
	/**
	 * Reads data to import sent by user and prepares them to analysis and import 
	 * that RepositoryConfirmImportController performs.
	 */
	protected ModelAndView doUpdate(
			Object command, 
			HttpServletRequest request, 
			HttpServletResponse response
	) throws Exception {

		LOG.debug("doUpdate()[ command=>{}, request=>{}, response=>{} ]", "-", "-", "-");

		RepositoryImportFileModel item = (RepositoryImportFileModel) command;
		
		//create temporary directory
		File temp = new File(System.getProperty("java.io.tmpdir"));
		File import_path = File.createTempFile("assimp", "", temp);
		import_path.delete();
		import_path.mkdir();
		File tex_path = new File(import_path, "tex");
		tex_path.mkdir();
		File pdf_path = new File(import_path, "pdf");
		pdf_path.mkdir();
		File files_path = new File(import_path, "files");
		files_path.mkdir();


		LOG.debug("doUpdate(): temp Import Path => {}", import_path.getAbsolutePath());
		LOG.debug("doUpdate(): temp Template Path => {}", tex_path.getAbsolutePath());
		LOG.debug("doUpdate(): temp Samples Path => {}", pdf_path.getAbsolutePath());
		LOG.debug("doUpdate(): temp Associated Path => {}", files_path.getAbsolutePath());

		LOG.debug("doUpdate(): Extract ZIP archive => {}", "--");
		
		//unzip uploaded import package into the temporary directory
		ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(item.getFile()));
		ZipEntry entry;
		byte[] data = new byte[2048];
		int count;
		try {
			while((entry=zip.getNextEntry())!=null) {

				LOG.debug("doUpdate(): Extract FILE => {}", entry.getName());

				try {
					OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(import_path, entry.getName())), 2048);
					try {
						while ((count = zip.read(data, 0, 2048)) != -1) {
							   out.write(data, 0, count);
							}
					} catch (Exception e) {	
						LOG.error("doUpdate(): Cannot READ zip archive file => {}", e.getMessage());
					} finally {
						out.flush();
						out.close();
					}
				} catch (FileNotFoundException e) {	
					LOG.error("doUpdate(): Extract FileNotFoundException  => {}", e.getMessage());
				}
			}
		} finally {
			zip.close();
		}
		
		request.getSession().setAttribute("importPath", import_path);
		
		RedirectView exit = new RedirectView("repository-import-confirm.htm"); 
		exit.setExposeModelAttributes(false);
		return new ModelAndView(exit);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return new RepositoryImportFileModel();
	}

	/**
	 * Registers the custom editor that understands multipart in the post parameters
	 * sent by browser  
	 */
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    throws ServletException {
	    // to actually be able to convert Multipart instance to byte[]
	    // we have to register a custom editor
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	    // now Spring knows how to handle multipart object and convert them
    }
}
