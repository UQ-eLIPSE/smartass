/* This file is part of SmartAss and contains the UpdateTemplateNodeController class. 
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The ExecuteTemplateController class updates updates given assignment tree node with 
 * the changed by user code received from the interactive editor applet.
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
import au.edu.uq.smartass.templates.texparser.ASTAnyText;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The UpdateTemplateNodeController class is a part of the server-side
 * backend for the interactive assignment editor applet and contains 
 * a code that updates given assignment tree node with 
 * the changed by user code received from the interactive editor applet. 
 * 
 */
public class UpdateTemplateNodeController extends TemplateAwareController {
	
	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. 
	 * It updates given assignment tree node with the changed by user code 
	 * received from the interactive editor applet.
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		Logger log = Logger.getLogger(getClass());
//		log.debug("code:" + request.getParameter("code"));
		
		String nodePathS = request.getParameter("node");
//		log.debug("node:'" + nodePathS+"'");
		
		if(nodePathS!=null && nodePathS.length()>0) {
			String name = request.getParameter("name");
			if(name!=null) {
				TexReader tr = getTemplate(name, request.getSession(), false);
				SimpleNode node = tr.getRootNode();
	
				if(!nodePathS.equals("0")) {
					String[] nodePath = nodePathS.split("_");
					node = findNode(node, nodePath, 1);
					//log.debug("node found:" + node);
					ASTAnyText newnode = new ASTAnyText(0);
					newnode.setText(request.getParameter("code"));
					node.replaceSelf(newnode);
				
					//repository.setFile(0, "", name, tr.getRootNode().getCode().getBytes());
					//though new node has ASTAnyText type it can contain code 
					//that can be  parsed in any complex nodes tree
					//so re-parse template code   
					tr.loadTemplate(tr.getRootNode().getCode());
				} else
					tr.loadTemplate(request.getParameter("code"));
				request.getSession().setAttribute("TEMPLATE_EXECUTED", null);
			}
		}
		
		return null;
	}

	private SimpleNode findNode(SimpleNode node, String[] nodePath, int pos) {
		int nodePos = Integer.parseInt(nodePath[pos]);
		if(pos==nodePath.length-1)
			return (SimpleNode) node.jjtGetChild(nodePos);
		return findNode((SimpleNode) node.jjtGetChild(nodePos), nodePath, pos+1);
	}
}
