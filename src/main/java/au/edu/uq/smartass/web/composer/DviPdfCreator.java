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
import java.util.prefs.Preferences;
import java.util.zip.ZipOutputStream;

import au.edu.uq.smartass.web.Zip;

/**
 * The DviPdfCreator class that creates PDF or DVI
 * files by executing the assignment code
 *
 */
public class DviPdfCreator {
	File output_path;
	String tex_path;
	
	public DviPdfCreator() {
		Preferences prefs = Preferences.userRoot().node("au/edu/uq/smartass");
		output_path = new File(prefs.get("output_path", ""));
		tex_path = prefs.get("tex_path", ".");
	}
	
	/**
	 * Creates PDFs from the template
	 * 
	 * @param texPath		path to the temporary storage of newly created PDFs 
	 * @throws IOException
	 */
	public void makePdfs(String texPath)  throws IOException {
		File workPath = new File(output_path, texPath);
		runLatex("pdflatex", workPath, "questions.tex");
		runLatex("pdflatex", workPath, "questions.tex");
		runLatex("pdflatex", workPath, "answers.tex");
		runLatex("pdflatex", workPath, "answers.tex");
		runLatex("pdflatex", workPath, "solutions.tex");
		runLatex("pdflatex", workPath, "solutions.tex");
		
		zipFiles(workPath, "pdf");
	}
	
	/**
	 * Creates DVIs from the template
	 * 
	 * @param texPath		path to the temporary storage of newly created PDFs 
	 * @throws IOException
	 */
	public void makeDvis(String texPath)  throws IOException {
		File workPath = new File(output_path, texPath);
		runLatex("latex", workPath, "questions.tex");
		runLatex("latex", workPath, "answers.tex");
		runLatex("latex", workPath, "solutions.tex");
		
		zipFiles(workPath, "dvi");
	}
	
	/**
	 * Executes external  (not java) routines to create DVI/PDF from the LaTeX code
	 *   
	 * @param latex_exec		the name of the latex executable
	 * @param workDir			work directory
	 * @param tex_name			the name of LaTeX file to be processed by latex executable
	 * @throws IOException
	 */
	public void runLatex(String latex_exec, File workDir, String tex_name) throws IOException {
		Process p = Runtime.getRuntime().exec(latex_exec + " -interaction=batchmode \""
				+ tex_name + "\"", 
			new String[] {"TEXINPUTS=" + tex_path},
			workDir); 
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		try {
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
		} finally {
			input.close();
		}
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
