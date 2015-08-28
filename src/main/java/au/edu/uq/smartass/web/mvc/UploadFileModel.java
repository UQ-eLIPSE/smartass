/* This file is part of SmartAss and contains the UploadFileModel class that 
 * is used as the container for the data of the file uploaded by user.   
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
package au.edu.uq.smartass.web.mvc;


import au.edu.uq.smartass.web.FilesItemModel;

/**
 * The UploadFileModel class is used as the container for the data of the file uploaded by user.
 */
public class UploadFileModel extends FilesItemModel {
	private byte[] file;
	private boolean replaceFile;

	public UploadFileModel(FilesItemModel fileModel) {
		setId(fileModel.getId());
		setName(fileModel.getName());
		setDescription(fileModel.getDescription());
	}
	
	public UploadFileModel() {}
	
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	public byte[] getFile() {
		return file;
	}
	
	public void setReplaceFile(boolean replaceFile) {
		this.replaceFile = replaceFile;
	}
	
	public boolean getReplaceFile() {
		return replaceFile;
	}
}
