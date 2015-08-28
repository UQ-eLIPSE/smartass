/* This file is part of SmartAss and contains the SectionListController class. 
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The SectionListController class returns the list of assignment sections.
 *   
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
package au.edu.uq.smartass.web.iapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.templates.TexReader;

/**
 * The SectionListController class is a part of the server-side
 * backend for the interactive assignment editor applet. 
 * It returns the list of assignment section names.
 * The actual sections set depends on the assignment content but usually it is
 * "QUESTIONS", "SOLUTIONS" and "SHORTANSWERS"
 */
public class SectionListController extends TemplateAwareController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. 
	 * It returns the list of assignment section names such as "QUESTIONS", "SOLUTIONS" and so on.  
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json");
		String name = request.getParameter("name");
		if(name==null) {
			response.getOutputStream().print("{sections: []}");
			return null;
		}
		TexReader tr = getTemplate(name, request.getSession(), false);
		if(tr!=null) { 
			if(request.getSession().getAttribute("TEMPLATE_EXECUTED")==null)
				tr.execute();
			String json = "{sections: [";
			for(int i=0;i<tr.getSectionNames().size();i++) {
				json = json + "\"" + tr.getSectionNames().get(i) + "\"";
				if(i<tr.getSectionNames().size()-1)
					json = json +",";
			}
			json = json + "]}";
			response.getOutputStream().print(json);
		}
		return null;
	}

}
