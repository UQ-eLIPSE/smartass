/* This file is part of SmartAss and contains the NodePdfCreatorController class 
 * that is a part of the server-side backend for the interactive assignment editor applet. 
 * It creates PDF representation for LaTeX code from given assignment execution result tree node.
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.RTemplate;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.web.composer.DviPdfCreator;

/**
 * The NodePdfCreatorController class is a part of the server-side
 * backend for the interactive assignment editor applet. 
 * It creates PDF representation for LaTeX code from given assignment execution result tree node.
 * 
 */
public class NodePdfCreatorController extends TemplateAwareController {
	DviPdfCreator creator;
	
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. 
	 * It creates PDF representation for LaTeX code from given assignment execution result tree node.  
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Logger log = Logger.getLogger(getClass());

		String node_name = request.getParameter("node");
		String section = request.getParameter("section");
		TexReader tr = getTemplate(request.getParameter("file"), request.getSession(), false);
		//log.debug("tr:" + tr);
		if(tr==null)
			return null;

		if(!tr.isExecuted())
			tr.execute();
		ResultNode node = getNode(tr.getResultNode(), node_name);
		String node_str;
		if(tr.getResultNode() instanceof RTemplate)
			node_str = ((RTemplate)tr.getResultNode()).wrapWithHeaders(node.getSection(section));
		else
			node_str = node.getSection(section);
		File file = File.createTempFile("ass-", ".tex");
		//log.debug("tex name:" + file.getPath());
		try {
			FileWriter file_writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(file_writer);
			if(tr.getResultNode()==node)
				out.write(node.getSection(section));
			else
				out.write(((RTemplate)tr.getResultNode()).wrapWithHeaders(node.getSection(section)));
			out.close();
		} catch (Exception e) {
			//log.debug("error:" + e.getMessage());
			return null;
		}
		
		creator.runLatex("pdflatex", file.getParentFile(), file.getName());
		creator.runLatex("pdflatex", file.getParentFile(), file.getName());
		//log.debug("tex deleted: " + file.delete());
		file = new File(file.getParentFile(),
				file.getName().substring(0, file.getName().length()-3) + "pdf");
		
		
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"test.pdf\"");
		response.setContentLength((int) file.length());
		
		//log.debug("file name:" + file.getPath());
		
		InputStream in = new FileInputStream(file);
		FileCopyUtils.copy(in, response.getOutputStream());
		//log.debug("pdf deleted: " + file.delete());
		
		return null;
	}

	/**
	 * The setter for the {@link DviPdfCreator} object that creates a PDF from LaTeX code.
	 */
	public void setCreator(DviPdfCreator creator) {
		this.creator = creator;
	}
}
