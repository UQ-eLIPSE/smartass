/* This file is part of SmartAss and contains the ParseTemplateTreeController class. 
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The ParseTemplateTreeController class parses assignment
 * code (that is a combination of LaTeX code and specific SmartAss control 
 * statements) and creates its JSON representation for interactive editor applet
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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.ASTAnyText;
import au.edu.uq.smartass.templates.texparser.ASTCall;
import au.edu.uq.smartass.templates.texparser.ASTDocument;
import au.edu.uq.smartass.templates.texparser.ASTMulti;
import au.edu.uq.smartass.templates.texparser.ASTMultiChoice;
import au.edu.uq.smartass.templates.texparser.ASTRepeat;
import au.edu.uq.smartass.templates.texparser.ASTScript;
import au.edu.uq.smartass.templates.texparser.ASTSection;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The ParseTemplateTreeController class is a part of the server-side
 * backend for the interactive assignment editor applet. It parses assignment
 * code (that is a combination of LaTeX code and specific SmartAss control 
 * statements) and creates its JSON representation for interactive editor applet.
 * 
 */
public class ParseTemplateTreeController extends TemplateAwareController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. It parses assignment
	 * code (that is a combination of LaTeX code and specific SmartAss control 
	 * statements) and creates its JSON representation for interactive editor applet.  
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json");
		response.addHeader("Expires",  "Thu, 01 Jan 1970 01:00:00 GM");
		
/*		String stamp = (String) request.getSession().getAttribute("STAMP");
		if(stamp==null)
			stamp = "0";
		else
			stamp = Integer.toString(Integer.parseInt(stamp)+1);
			request.getSession().setAttribute("STAMP", stamp);*/

		String name = request.getParameter("name");
		if(name!=null) {
			TexReader tr = getTemplate(name, request.getSession(), false);
			SimpleNode node = tr.getRootNode();
			StringBuffer json = new StringBuffer();
			Date date = new Date(System.currentTimeMillis());
			json.append("{\n" + 
					"date: '" + date + "',\n" + 
					"session: '" + request.getSession().getId() + "',\n" +  
					"label: 'name',\n" +
					"identifier: 'id',\n" +
					"items: [\n");
			codeToTree(node, json, "0");
			json.append("]\n"/* + 
					",stamp: "+ stamp + "\n"*/ +
			"}"); 
			response.getOutputStream().print(json.toString());
		} else {
			response.getOutputStream().print(
					"{\n" + 
					"label: 'name',\n" +
					"identifier: 'name',\n" +
			"items: []\n}" );
		}
		
		return null;
	}
	
	
	/**
	 * Converts parsed assignment code to its JSON representation for interactive editor applet
	 *   
	 * @param node				one of {@link ResultNode} descendants
	 * @param json				{@link StringBuffer} that is container for JSON code
	 * @param parentId			an unique id for the {@link ResultNode}
	 */
	protected void codeToTree(SimpleNode node, StringBuffer json, String parentId) {
		for(int i=0;i<node.jjtGetNumChildren();i++) {
			SimpleNode child = (SimpleNode) node.jjtGetChild(i); 
			if(child!=null)
			{
				String[] code = {"", ""};
				if(child instanceof ASTAnyText || child instanceof ASTCall  || child instanceof ASTScript) 
					code[0] = child.getCode();
				else if(child instanceof ASTDocument) {
					code[0] = "\\begin{document}\n";
					code[1] = "\\end{document}\n";
				} else if(child instanceof ASTMulti) {
					code[0] = "%%MULTI " + ((ASTMulti)child).getChoicesCount() + "\n";
					code[1] = "%%ENDMULTI\n";
				} else if(child instanceof ASTMultiChoice) 
					code[0] = "%%CHOICE\n";
				else if(child instanceof ASTRepeat) {
					code[0] = "%%REPEAT " + ((ASTRepeat)child).getRepeatsNum() + "\n";
					code[1] = "%%ENDREPEAT\n";
				} else if(child instanceof ASTSection) {
					code[0] = "%%BEGIN " + ((ASTSection)child).getName() + "\n";
					code[1] = "%%END "+ ((ASTSection)child).getName() + "\n";
				}

				json.append("{ name: '");
				if(child instanceof ASTAnyText)
					json.append("Text");
				else if(child instanceof ASTCall)
					json.append("Call " + ((ASTCall)child).getFilename());
				else if(child instanceof ASTSection) 
					json.append("Section " + ((ASTSection)child).getName());
				else if(child instanceof ASTDocument)
					json.append("Document body");
				else
					json.append(child.toString());
				json.append("', id: '");
				json.append(parentId + "_" + i);
				json.append("', type:'category'");
				json.append(",\n codehead: \"");
				json.append(code[0]
						/*.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")*/
						.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace((char) 10, (char)13).replace("\r", ""));
				json.append("\"");
				json.append(",\n codetail: \"");
				json.append(code[1]
									/*.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")*/
				                 .replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace((char) 13, ' ').replace((char) 10, ' '));
							json.append("\"");
						
				if(node.jjtGetChild(i).jjtGetNumChildren()>0) {
					json.append(",\n items: [\n");
					codeToTree((SimpleNode) node.jjtGetChild(i), json, parentId + "_" + i);
					json.append("]\n");
				}
				if(i<node.jjtGetNumChildren()-1)
					json.append("},\n");
				else
					json.append("}\n");
			} else node.removeNode(i--);
		}
	}
	
}
