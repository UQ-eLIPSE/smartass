/* This file is part of SmartAss and contains the DownloadController class that 
 * prepares data for "Download generated assignments in LaTeX and PDF" page.   
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

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;

import au.edu.uq.smartass.web.jdbc.FilesDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DownloadController class prepares data for "Download generated assignments in LaTeX and PDF" page.
 */
public class DownloadController extends AbstractController {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( DownloadController.class );

	private RepositoryStorage repositoryStorage;
	private TemplatesDao templatesDao;
	private FilesDao filesDao;
	
	/**
	 * This function is called by Spring framework on HTTP request from the browser.
	 * It prepares data for "Download generated assignments in LaTeX and PDF" page. 
	 */
	@Override protected ModelAndView handleRequestInternal(
                        HttpServletRequest request,
			HttpServletResponse response
                ) throws Exception {

                LOG.info( 
                                "::handleRequestInternal()[\n\nrequest=>\n{}\n\nresponse=>\n{}\n\n]", 
                                request.toString(), response.toString() 
                        );

		try {
			int id = Integer.parseInt(request.getParameter("id"));
			int scope = Integer.parseInt(request.getParameter("scope"));

			String name = "";
			
			switch(scope) {
			case 0: 
			case 1: 
				TemplatesItemModel t = templatesDao.getItem(id);
				String path = t.getPath();
				if(scope==0) {
					name = t.getName() + ".tex";
					response.getOutputStream().write(t.metadataToString().getBytes());
				} else {
					int kind = Integer.parseInt(request.getParameter("kind"));
					switch(kind) {
					case 0:
						name = t.getName() + "_QUESTIONS.pdf";
						break;
					case 1:
						name = t.getName() + "_SOLUTIONS.pdf";
						break;
					case 2:
						name = t.getName() + "_SHORTANSWERS.pdf";
					}
				}
				break;
			case 2: 
				FilesItemModel f = filesDao.getItem(id);
				name = f.getName();
			}
	
                        //      . Set content type .
			String content_type = getServletContext().getMimeType(name);
			response.setContentType( (content_type != null) ? content_type : "text/plain" );

			response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
			
			InputStream in = repositoryStorage.getFile(scope, "", name);
			FileCopyUtils.copy(in, response.getOutputStream());
	
			return null;
		} catch (Exception ex) {
                        LOG.info( "::handleRequestInternal()[ exception caught ]\n{}\n\n", ex.getMessage() );
			RedirectView exit = new RedirectView("no-page-redirect.htm"); 
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);
		}
	}

	/**
	 * The setter for the repository storage property.
	 */
	public void setRepositoryStorage(RepositoryStorage repositoryStorage) {
		this.repositoryStorage = repositoryStorage;
	}

	/**
	 * The setter for the templatesDao property.
	 */
	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}

	/**
	 * The setter for the filesDao property.
	 */
	public void setFilesDao(FilesDao filesDao) {
		this.filesDao = filesDao;
	}

}
