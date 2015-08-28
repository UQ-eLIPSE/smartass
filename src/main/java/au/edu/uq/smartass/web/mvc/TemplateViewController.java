/* This file is part of SmartAss and contains the TemplateViewController class that 
 * shows template text on the web page.
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
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

/**
 * The TemplateViewController class shows template text on the web page.
 */
public class TemplateViewController extends AbstractController {
	private TemplatesDao templatesDao; 
	private RepositoryStorage repositoryStorage;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> model = new HashMap<String, String>();
		try {
			TemplatesItemModel template = templatesDao.getItem(Integer.parseInt(request.getParameter("id")));
			
			model.put("tempalte_name", template.getName());
			
			InputStream in = repositoryStorage.getFile(0, "", template.getName()+".tex");
			String str = FileCopyUtils.copyToString(new InputStreamReader(in));
			model.put("content", str);
		} catch (Exception e) {}
		
		return new ModelAndView("template_view", model);
	}

	public void setTemplatesDao(TemplatesDao templatesDao) {
		this.templatesDao = templatesDao;
	}
	
	public void setRepositoryStorage(RepositoryStorage repositoryStorage) {
		this.repositoryStorage = repositoryStorage;
	}
}
