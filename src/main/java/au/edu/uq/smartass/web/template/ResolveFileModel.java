/* This file is part of SmartAss and contains the ResolveFileModel class that 
 * serves as a model to "resolve" file - e.g. to connect file name from the metadata 
 * of template that is being imported  to the corresponding record in the smartass database 
 * or to create a new one if such record does not exists.

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

import org.springframework.web.multipart.MultipartFile;

import au.edu.uq.smartass.web.FilesItemModel;

/**
 * The ResolveFileModel class 
 * serves as a model to "resolve" file - e.g. to connect file name from the metadata 
 * of template that is being imported  to the corresponding record in the smartass database 
 * or to create a new one if such record does 
 */
public class ResolveFileModel extends ResolveItemModel {
	byte[] filedata;
	private FilesItemModel file;
	
	public ResolveFileModel(FilesItemModel file) {
		this.file = file;
	}
	
	public byte[] getFileData() {
		return filedata;
	}
	
	public void setFileData(byte[] filedata) {
		this.filedata = filedata;
	}
	
	public void setFileMultipart(MultipartFile file) throws IOException {
		if(file!=null)
			this.filedata = file.getBytes();
		else
			this.filedata = null;
	}

	public FilesItemModel getFile() {
		return file;
	}
}
