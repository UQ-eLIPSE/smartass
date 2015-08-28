/* This file is part of SmartAss and contains the ClearResultsAction class - Spring bean that  
 * clears results of assignment execution when user leaves execution result state  
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

import java.io.File;
import java.util.prefs.Preferences;

/**
 * The ClearResultsAction class is the Spring bean that  clears results of assignment execution 
 * when user leaves execution result state
 *
 */
public class ClearResultsAction {
	/** The directory where execution result subdirectories is placed */ 
	File output_path;
	
	public ClearResultsAction() {
		Preferences prefs = Preferences.userRoot().node("au/edu/uq/smartass");
		output_path = new File(prefs.get("output_path", ""));
	}
	
	/**
	 * This method cleans assignment execution results 
	 * 
	 * @param basePath	the directory that containes execution results to be cleared 
	 * 
	 */
	public void clearTexs(String basePath) {
		File bp = new File(output_path, basePath);
		File[] files = bp.listFiles();
		if(files!=null)
			for(File f : files)
				f.delete();
		bp.delete();
	}

}
