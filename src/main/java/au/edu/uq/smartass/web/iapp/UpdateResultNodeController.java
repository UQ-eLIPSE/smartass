/* This file is part of SmartAss and contains the UpdateResultNodeController class. 
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The UpdateResultNodeController class updates given result tree node with 
 * the changed by user text received from the interactive editor applet.
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

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.app.RSectionsTextNode;
import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.RComplexNode;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The UpdateResultNodeController class is a part of the server-side
 * backend for the interactive assignment editor applet that
 * updates given result tree node with the changed by user text received from the
 * interactive editor applet. 
 */
public class UpdateResultNodeController extends TemplateAwareController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. 
	 * It updates given result tree node with the changed by user text received from the
	 * interactive editor applet.  
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse responce) throws Exception {
		//Logger l = Logger.getLogger(getClass());
		
		String nodePathS = request.getParameter("node");
		String name = request.getParameter("name");
		
		//l.debug(nodePathS);
		//l.debug(name);
		
		if(name!=null && nodePathS!=null && nodePathS.length()>0) {
			TexReader tr = getTemplate(name, request.getSession(), false);
			
			//l.debug("tex reader:"+tr);
			
			if(tr==null)
				return null;
			ResultNode node = getNode(tr.getResultNode(), nodePathS);
			
			//l.debug("node to replace: "+node);
			
			if(node==null)
				return null;
			
			RSectionsTextNode newnode = new RSectionsTextNode();
			for(String it : tr.getSectionNames()) {
				String text = request.getParameter(it);
				//l.debug("section:"+ it + " text: " + text);
				if(text==null)
					text = node.getSection(it);
				newnode.setSection(it, text);
			}
			
			//l.debug(newnode);
			
			if(node==tr.getResultNode())
				tr.setResultNode(newnode);
			else
				node.replaceSelf(newnode);
			
			//l.debug("after replace node");
		}
		
		return null;
	}
}
