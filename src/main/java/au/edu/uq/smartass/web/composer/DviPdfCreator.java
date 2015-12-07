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
import java.io.FileFilter;
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

	private static final String[] texDocs = { "questions.tex", "answers.tex", "solutions.tex" };

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

                latex_cmd = preferences.get("latex_command", "latex");
                pdflatex_cmd = preferences.get("pdflatex_command", "pdflatex");

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
		createDocs(pdflatex_cmd, texPath, "pdf");
	}
	
	/**
	 * Creates DVIs from the template
	 * 
	 * @param texPath		path to the temporary storage of newly created PDFs 
	 * @throws IOException
	 */
	public void makeDvis(String texPath)  throws IOException {
		createDocs(latex_cmd, texPath, "dvi");
	}

	private void createDocs(final String command, final String texPath, final String ext) throws IOException {
                File workPath = new File(out_path, texPath);
		for (String texDoc : texDocs) runLatex(command, workPath, texDoc);
		archiveFiles(workPath, ext);
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
//		for (Map.Entry<String,String> me : environment.entrySet()){
//			LOG.debug( "::runLatex()[ environment: {} => {} ]", me.getKey(), me.getValue() );
//		}

        builder.directory(workDir);
        Process process = builder.start();

        BufferedReader processOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ( (line = processOut.readLine()) != null)
        LOG.debug("::runLatex [output]=> {}", line);

		try {
			process.waitFor();

		} catch (InterruptedException ex) {
			LOG.warn( "::runLatex()[ {} ]", ex.getMessage() );

		} finally {
			if (0 != process.exitValue()) 
					LOG.warn(
							"::runLatex()[ Problem running '{}' command on '{}' file! ]", 
							latex_exec, tex_name 
						);
		}
	}
	
	/**
	 * Zips assignments to allow user download them all together  
	 * 
	 * @param path
	 * @param ext
	 * @throws IOException
	 */
	private void archiveFiles(final File path, final String ext) throws IOException {
		ZipOutputStream archiveFile = Zip.createZip( new File(path, "assignments.zip") );
		try {
			for (
				File file : 
				path.listFiles( new FileFilter() { public boolean accept(File pathname) {
					return pathname.getName().toLowerCase().matches("^.*(?:tex|" + ext + ")$");
				}} )
			) {
				LOG.info( "::zipFiles()[ Adding '{}' to archive '{}'! ]", file, "assignments.zip" );
				Zip.addFileToZip(archiveFile, file.getName(), file);
			}

		} finally {
			archiveFile.close();
		}
	}
}
