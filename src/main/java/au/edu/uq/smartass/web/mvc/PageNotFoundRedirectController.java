/* This file is part of SmartAss and contains the PageNotFoundRedirectController class that 
 * reacts on situation where user requested url to which the site has no corresponding page.   
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The PageNotFoundRedirectController class  
 * reacts on situation where user requested url to which the site has no corresponding page.
 */
public class PageNotFoundRedirectController extends AbstractController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * It analyze conditions those are caused "page not found" situation and try to 
	 * make a consistent reaction on it.   
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("message", "Page not found: " + request.getRequestURI());
		String base = "http://"+request.getServerName();
		if(request.getServerPort()!=80)
			base = base + ":" + request.getServerPort();
		
		String path = (String) request.getAttribute("javax.servlet.forward.servlet_path");
		
		String message = "";
		if(path==null || path.length()==0) {
			message = "Page or file not found!";
		} else if(path.endsWith("/")) {
			RedirectView exit = new RedirectView("/index.htm", true);
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);
		} else if(path!=null && path.length()>0) { 
			if(path.endsWith("composer"))
				message = "Assignment composer session has expired or has been closed by user!";
			else if(path.endsWith("template") || path.endsWith("tempalte-edit"))
				message = "Template editor session has expired or has been closed by user!";
			else {
				message = "Page or file <b>" + base + path + "</b> not found!";
/*				if(path.endsWith(".pdf") && path.contains("download/ass"))
					message = message + " that is possibly a PDF file that was deleted " +
						"due the timeout or when you leaved the execution results page.";*/
			}
		} /*else {
			message = 
				"Parameters<br>";
				Enumeration<String> params = request.getParameterNames();
				while(params.hasMoreElements()) {
					String s = params.nextElement();
					message = message + s + " : " + request.getParameter(s) + "<br>";
				}
				message = message + "\nAttributes<br>";
				params = request.getAttributeNames();
				while(params.hasMoreElements()) {
					String s = params.nextElement();
					message = message + s + " : " + request.getAttribute(s) + "<br>";
				}
				message = message + "\nHeaders<br>";
				params = request.getHeaderNames();
				while(params.hasMoreElements()) {
					String s = params.nextElement();
					message = message + s + " : " + request.getHeader(s) + "<br>";
				}*/
/*			RedirectView exit = new RedirectView("/index.htm", true);
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);

		}*/
		model.put("message", message);
		model.put("redirectUri", "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index.htm");
		
		return new ModelAndView("nopage", model);
	}

}
