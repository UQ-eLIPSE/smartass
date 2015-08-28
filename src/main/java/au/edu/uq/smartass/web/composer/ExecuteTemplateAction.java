/* This file is part of SmartAss and contains the ExecuteTemplateAction class that  
 * executes assignment creating questions, solutions and short answers .tex files
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
package au.edu.uq.smartass.web.composer;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;
import java.util.zip.ZipOutputStream;

import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.web.Zip;

/**
 * The ExecuteTemplateAction class executes assignment.
 * This is the core functionality of the SmartAss - to process "template" files with control statements and
 * some LaTeX decorations, including assignments that is templates
 * too in the sense this word is used in the "template execution" context, and produce LaTeX files
 * with questions, solutions and short answers that can be immediately used to create PDF or DVI documents.
 *
 */
public class ExecuteTemplateAction  {
	
	/**
	 * This function is called by Spring framework. It calls doExecute to process assignment code to create
	 * question, solution and short answers files.
	 * 
	 * @param context		Spring webflow request context
	 * @return				{@link Event} to Spring webflow transition to the new state
	 * @throws Exception
	 */
	public Event execute(RequestContext context) throws Exception {
		return doExecute(null, null, context);
	}
	
	/**
	 * Processes assignment code to create question, solution and short answers files.
	 * 
	 * @param prepared_code		prepared code with decoration added as String
	 * @param code				code without decorations as String
	 * @param context			Spring webflow request context
	 * @return				{@link Event} to Spring webflow transition to the new state
	 * @throws Exception
	 */
	public Event doExecute(String prepared_code, String code, RequestContext context) throws Exception {
		//Logger log = Logger.getLogger(getClass());
		//File questions, answers, solutions, zipfile;

		String tex = prepared_code;
		//log.debug(code);
		if(tex==null || tex.length()==0) {
			tex = code;
			if(tex==null)
				tex = ((AssignmentConstruct) context.getFlowScope().get("template")).getCode();
		}

		//context.getRequestParameters().get("code");
		
		Engine engine = new Engine();
		TexReader tr = (TexReader) engine.getTemplateReader("tex");
		tr.loadTemplate(new ByteArrayInputStream(tex.getBytes("UTF-8")));
		tr.setPredefinedNames(new String[]{"DEF", "QUESTION", "SHORTANSWER", "SOLUTION"});
		tr.execute();
		String output_path = engine.getPreference("output_path");
		engine.close();
		output_path = saveExecutionResults(tr, output_path);
		engine = null;
		context.getFlowScope().put("resultPath", output_path);
		return new Event(this, "ok");
	}
	
	/**
	 * Saves assignment execution results as files and pack them to the zip file
	 * 
	 * @param tr			{@link TexReader} that contains execution results
	 * @param outputRoot	the path where result files will be saved 
	 * @return				the output directory name
	 * @throws IOException
	 */
	protected String saveExecutionResults(TexReader tr, String outputRoot) throws IOException {
		ResultNode result = tr.getResultNode();
		
		File output_root = new File(outputRoot);
		File output_path = File.createTempFile("ass", "", output_root);
		output_path.delete();
		output_path.mkdir();
		output_path.mkdir();
		
		File questions = new File(output_path, "questions.tex");
		File answers = new File(output_path, "answers.tex");
		File solutions = new File(output_path, "solutions.tex");
		File zipfile = new File(output_path, "assignments.zip");
		
		writeFile(questions, result.getSection("QUESTION"));
		writeFile(answers, result.getSection("SHORTANSWER"));
		writeFile(solutions, result.getSection("SOLUTION"));
		
		ZipOutputStream zip = Zip.createZip(zipfile);
		try {
			Zip.addToZip(zip, "questions.tex", result.getSection("QUESTION"));
			Zip.addToZip(zip, "answers.tex", result.getSection("SHORTANSWER"));
			Zip.addToZip(zip, "solutions.tex", result.getSection("SOLUTION"));
		} finally {
			zip.close();
		}
		return output_path.getName();
	}

	/**
	 * Assists the InteractiveApp - the java applet that can be embedded into the web page
	 * to provide highly interactive assignments execution and edit.
	 * Processes assignment code to create question, solution and short answers files.
	 * 
	 * @param prepared_code		prepared code with decoration added as String
	 * @param code				code without decorations as String
	 * @param context			Spring webflow request context
	 * @throws Exception
	 */
	public void prepareInteractive(String prepared_code, String code, RequestContext context) throws Exception {
		String tex = prepared_code;
		if(tex==null || tex.length()==0) {
			tex = code;
			if(tex==null)
				tex = ((AssignmentConstruct) context.getFlowScope().get("template")).getCode();
		}

		context.getExternalContext().getSessionMap().put("TEMPLATE_NAME", "COMPOSER");
		context.getExternalContext().getSessionMap().put("TEMPLATE_EXECUTED", null);
		Engine engine = new Engine();
		TexReader tr =  (TexReader) engine.getTemplateReader("tex");
		tr.loadTemplate(tex);
		//l.debug("pp-tr: " + tr);
		engine.close();
		context.getExternalContext().getSessionMap().put("TEMPLATE_OBJECT", tr);
		//context.getExternalContext().getSessionMap().put("TEMPLATE_CODE", tex);
	}
	
	/**
	 * Assists the InteractiveApp - the java applet that can be embedded into the web page
	 * to provide highly interactive assignments execution and edit.
	 * Returns assignment execution results
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void extractInteractive(RequestContext context) throws Exception {
		TexReader tr =  (TexReader)context.getExternalContext().getSessionMap().get("TEMPLATE_OBJECT");
		if(tr!=null) {
			((AssignmentConstruct)context.getFlowScope().get("template")).setCode(tr.getRootNode().getCode());
			Preferences prefs = Preferences.userRoot().node("au/edu/uq/smartass");
			String output_path = prefs.get("output_path", "aaa");
			if(output_path!=null) {
				context.getFlowScope().put("resultPath", saveExecutionResults(tr, output_path));
			}
		}
	}

	/**
	 * Assists the InteractiveApp - the java applet that can be embedded into the web page
	 * to provide highly interactive assignments execution and edit.
	 * Returns the assignment code
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void extractInteractiveCode(RequestContext context) throws Exception {
		TexReader tr =  (TexReader)context.getExternalContext().getSessionMap().get("TEMPLATE_OBJECT");
		if(tr!=null) 
			((AssignmentConstruct)context.getFlowScope().get("template")).setCode(tr.getRootNode().getCode());
	}
	
	/**
	 * Utility function to write string to the given {@link File}
	 */
	private static void  writeFile(File file, String data) throws IOException {
			FileWriter file_writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(file_writer);
			if(data==null)
				out.write("");
			else
				out.write(data);
			out.close();
	}
}
