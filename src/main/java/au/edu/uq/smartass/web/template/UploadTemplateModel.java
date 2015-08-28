/* This file is part of SmartAss and contains the UploadTemplateModel class that is
 * used as the model to upload tempalte to the site. 
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
package au.edu.uq.smartass.web.template;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * The UploadTemplateModel class that is
 * used as the model to upload tempalte to the site
 */
public class UploadTemplateModel implements Serializable {
	String file;
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFile() {
		return file;
	}

	public void setFileMultipart(CommonsMultipartFile file) throws IOException {
		this.file = new String(file.getBytes());
	}
	
}
