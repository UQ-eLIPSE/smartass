/* This file is part of SmartAss and contains the UploadAssignmentModel class - 
 * the model (as in Model-View-Controller) class for the assignment upload to the 
 * SmartAss web site assignment editor.
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

import java.io.IOException;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * The UploadAssignmentModel class is the model (as in Model-View-Controller) class 
 * for the assignment upload to the SmartAss web site assignment editor.
 *
 */
public class UploadAssignmentModel implements Serializable {
	String file;

	/**
	 * Returns uploaded file content as the {@link String}
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Stores the uploaded file content as the {@link MultipartFile}
	 * @throws IOException
	 */
	public void setFileMultipart(MultipartFile file) throws IOException {
		file.toString();
		this.file = new String(file.getBytes());
	}
	
}
