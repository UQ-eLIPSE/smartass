/* This file is part of SmartAss and contains the DviPdfCreator class that creates PDF or DVI
 * files by executing the assignment code
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.prefs.Preferences;
import java.util.zip.ZipOutputStream;

import au.edu.uq.smartass.web.Zip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The DviPdfCreator class that creates PDF or DVI
 * files by executing the assignment code
 *
 */
public class DviPdfCreator {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( DviPdfCreator.class );

        /** */
        private File out_path;

        /** */
        private String tex_path;

        /** */
        private String latex_cmd;

        /** */
        private String pdflatex_cmd;

        /** */
        {
                Preferences preferences = Preferences.userRoot().node("au/edu/uq/smartass");
                
                out_path = new File( preferences.get("output_path", "") );
                tex_path = preferences.get("tex_path", ".");
                latex_cmd = preferences.get("latex_command", "/usr/bin/latex");
                pdflatex_cmd = preferences.get("pdflatex_command", "/usr/bin/pdflatex");

                // @TODO: check for existance of Process Commands on system.
                // @TODO: *     'latex'
                // @TODO: *     'pdflatex'
        }

	
	/**
	 * Creates PDFs from the template
	 * 
	 * @param texPath		path to the temporary storage of newly created PDFs 
	 * @throws IOException
	 */
	public void makePdfs(String texPath)  throws IOException {
		File workPath = new File(out_path, texPath);
		runLatex(pdflatex_cmd, workPath, "questions.tex");
		runLatex(pdflatex_cmd, workPath, "questions.tex");
		runLatex(pdflatex_cmd, workPath, "answers.tex");
		runLatex(pdflatex_cmd, workPath, "answers.tex");
		runLatex(pdflatex_cmd, workPath, "solutions.tex");
		runLatex(pdflatex_cmd, workPath, "solutions.tex");
		
		zipFiles(workPath, "pdf");
	}
	
	/**
	 * Creates DVIs from the template
	 * 
	 * @param texPath		path to the temporary storage of newly created PDFs 
	 * @throws IOException
	 */
	public void makeDvis(String texPath)  throws IOException {
                File workPath = new File(out_path, texPath);
		runLatex(latex_cmd, workPath, "questions.tex");
		runLatex(latex_cmd, workPath, "answers.tex");
		runLatex(latex_cmd, workPath, "solutions.tex");
		
		zipFiles(workPath, "dvi");
	}
	
	/**
	 * Executes external (not java) routines to create DVI/PDF from the LaTeX code
	 *   
	 * @param latex_exec    the name of the latex executable
	 * @param workDir	work directory
	 * @param tex_name	the name of LaTeX file to be processed by latex executable
	 * @throws IOException
	 */
	public void runLatex(String latex_exec, File workDir, String tex_name) throws IOException {
                LOG.info("::runLatex [ latex_exec=>{}, working_directory=>{}, tex_name=>{} ]", latex_exec, workDir, tex_name);
                ProcessBuilder builder = new ProcessBuilder(
                                latex_exec,
                                "-interaction=batchmode",
                                "\"" + tex_name + "\""
                        );
                Map<String,String> environment = builder.environment();
                environment.put("TEXINPUTS", tex_path);
                builder.directory(workDir);

                Process process = builder.start();

                BufferedReader processOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ( (line = processOut.readLine()) != null)
                                LOG.info("::runLatex [output]=> {}", line);
	}
	
	/**
	 * Zips assignments to allow user download them all together  
	 * 
	 * @param outputPath
	 * @param ext
	 * @throws IOException
	 */
	private void zipFiles(File outputPath, String ext) throws IOException {
		File zipfile = new File(outputPath, "assignments.zip");

		ZipOutputStream zip = Zip.createZip(zipfile);
		try {
			Zip.addFileToZip(zip, "questions.tex", new File(outputPath, "questions.tex"));
			Zip.addFileToZip(zip, "answers.tex", new File(outputPath, "answers.tex"));
			Zip.addFileToZip(zip, "solutions.tex", new File(outputPath, "solutions.tex"));
			Zip.addFileToZip(zip, "questions."+ext, new File(outputPath, "questions."+ext));
			Zip.addFileToZip(zip, "answers."+ext, new File(outputPath, "answers."+ext));
			Zip.addFileToZip(zip, "solutions."+ext, new File(outputPath, "solutions."+ext));
		} finally {
			zip.close();
		}

	}
}
