/* This file is part of SmartAss and contains the EngineSettings class - the class-holder of
 * different Engine settings.
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

package au.edu.uq.smartass.web;

import java.util.prefs.Preferences;

import au.edu.uq.smartass.web.composer.AssignmentConstruct;
import au.edu.uq.smartass.web.composer.DviPdfCreator;

/**
 * The class-holder of a set of Engine settings. This settings is used by Smartass Engine and template parser.
 * Both Engine and template parser does not get settings directly from this class. They retrieve 
 * settigs from java {@link Preferences} at "au/edu/uq/smartass" node. This class is used as the interface between
 * Spring xml-stored settings and Smartass {@link Preferences} stored ones.
 * 
 * @author nik
 *
 */
public class EngineSettings {

        /** */
	private Preferences preferences = Preferences.userRoot().node("au/edu/uq/smartass");
	
	/**
	 * The setter for the list of modules root directories. Use semicolon as the list item delimiter.   
	 */
	public void setModulesRoot(String value) { preferences.put("modules_root", value); }
	
	/**
	 * The setter for the list of templates root directories. Use semicolon as the list item delimiter.   
	 */
	public void setTemplatesRoot(String value) { preferences.put("templates_root", value); }
	
	/**
	 * The setter for the path where generated TeX and PDF/DVI files will be placed. 
	 * Actually this is not Engine setting, it is used by {@link DviPdfCreator} but placed in 
	 * {@link EngineSettings} to group all template-processing related settings in single place.  
	 */
	public void setOutputPath(String value) { preferences.put("output_path", value); }

        public void setLatexCommand(String value) { preferences.put( "latex_command", value ); }
        public void setPdfLatexCommand(String value) { preferences.put( "pdflatex_command", value ); }

	/**
	 * The setter for the template header text (e.g. part of template from top to "\\begin{document}". 
	 * Actually this is not Engine setting, it is used by {@link AssignmentConstruct}s getCode() method but placed in 
	 * {@link EngineSettings} to group all template-processing related settings in single place.  
	 */
	public void setTemplateHeader(String value) { preferences.put("templateHeader", value); }
	
	/**
	 * The setter for the list of directories where latex and pdflatex will search files it needs for DVI/PDF creation. 
	 * The delimiter of this list items is system-dependent, use ":" on linux/unix.   
	 */
	public void setTexSearchPath(String value) { preferences.put("tex_path", value); }
	
	/**
	 * The setter for the path to directories where backups of repository and assignments are stored. 
	 */
	public void setBackupsPath(String value) { preferences.put("backups_path", value); }
	
}
