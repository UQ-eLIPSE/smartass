/* This file is part of SmartAss and contains the TemplateAwareController class.
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The TemplateAwareController class is an ancestor for all assignment-processing classes
 * and contains some functionality that is common for all them.
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

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.templates.TemplateReader;
import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.ParseException;
import au.edu.uq.smartass.templates.texparser.RComplexNode;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.web.RepositoryStorage;

/**
 * The ExecuteTemplateController class is a part of the server-side
 * backend for the interactive assignment editor applet and contains.
 * This class is an ancestor for all assignment-processing classes
 * and contains some functionality that is common for all them.
 *
 */
public abstract class TemplateAwareController extends AbstractController {
	protected RepositoryStorage repository;

	/**
	 * Returns {@link TemplateReader} with parsed template code ("template" is a common name for
	 * SmartAss files that is a combination of LaTeX code and specific control statements so an assignment
	 * is a template too in terms of SmartAss).
	 * @param name The name of the template
	 * @param session The http session
	 * @param refresh boolean if being refreshed
	 * @return a TexReader object
	 */
	protected TexReader getTemplate(String name, HttpSession session, boolean refresh) throws ParseException {
		TexReader tr = null;
		if(!refresh) {
			String tname = (String) session.getAttribute("TEMPLATE_NAME");
				tr = (TexReader) session.getAttribute("TEMPLATE_OBJECT");
		}
		if(tr==null) {
			InputStream tin = repository.getFile(0, "", name);
			if(tin!=null) {
				Engine engine = Engine.getInstance();
				tr = (TexReader) engine.getTemplateReader("tex");
				tr.loadTemplate(tin);
				engine.close();
				session.setAttribute("TEMPLATE_OBJECT", tr);
				session.setAttribute("TEMPLATE_NAME", name);
				session.setAttribute("TEMPLATE_EXECUTED", null);
			}
		}

		return tr;
	}

	/**
	 * Decodes nodePath that is an unique node id in {@link TexReader} execution result
	 * tree.
	 *
	 * @param root			the root node
	 * @param nodePath		the unique id of (or encoded path to) the node to be extracted from the tree
	 *
	 * @return				the {@link ResultNode} descendant defined by nodePath
	 */
	protected ResultNode getNode(ResultNode root, String nodePath) {
		if(nodePath.equals("0"))
			return root;

		String[] path = nodePath.split("_");
		ResultNode node = root;
		for(int i=1;i<path.length;i++) {
			if(!(node instanceof RComplexNode))
				return null;
			node = ((RComplexNode)node).getChild(Integer.parseInt(path[i])) ;
		}
		return node;
	}

	/**
	 * Sets the repository to use
	 * @param repository The RepositoryStorage object
	 */
	public void setRepository(RepositoryStorage repository) {
		this.repository = repository;
	}
}
