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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PageNotFoundRedirectController class  
 * reacts on situation where user requested url to which the site has no corresponding page.
 */
public class PageNotFoundRedirectController extends AbstractController {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( PageNotFoundRedirectController.class );

	/**
	 * This function is called by Spring framework on HTTP request from the browser. 
	 * It analyze conditions those are caused "page not found" situation and try to 
	 * make a consistent reaction on it.   
	 */
	@Override protected ModelAndView handleRequestInternal(
                        HttpServletRequest request,
			HttpServletResponse response
                ) throws Exception {

                LOG.info( 
                                "::handleRequestInternal()[\n\nrequest=>\n{}\n\nresponse=>\n{}\n\n]", 
                                request.toString(), response.toString()
                        );
		

		String base = 
                                "http://" + request.getServerName() +
			        ":" + request.getServerPort()
                        ;
		String path = (String) request.getAttribute("javax.servlet.forward.servlet_path");
		
		String message;
		if (path == null || path.length() == 0) {
			message = "Page or file not found!";

		} else if (path.endsWith("/")) {
			RedirectView exit = new RedirectView("/index.htm", true);
			exit.setExposeModelAttributes(false);
			return new ModelAndView(exit);

		} else if (path.endsWith("composer")) {
                        message = "Assignment composer session has expired or has been closed by user!";

	        } else if ( path.endsWith("template") || path.endsWith("tempalte-edit") ) {
                        message = "Template editor session has expired or has been closed by user!";

                } else {
                        message = "Page or file <b>" + base + path + "</b> not found!";
                }

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", message);
		model.put("redirectUri", base + request.getContextPath() + "/index.htm");

                LOG.info( "::handleRequestInternal()[ model=>{} ]", model.toString());
		
		return new ModelAndView("nopage", model);
	}

}
