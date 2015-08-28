/* This file is part of SmartAss and contains the DownloadBackupController class that 
 * is used to download the backup previously created by some other code.   
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

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.web.BackupStorage;
import au.edu.uq.smartass.web.UserItemModel;

/**
 * The DownloadBackupController class is used to download the backup previously created by some other code.
 */
public class DownloadBackupController extends UserRequieredController {
	BackupStorage backupStorage;

	public DownloadBackupController() {
		rightsMask = UserItemModel.RIGHT_ADMIN;
	}
	
	@Override
	/**
	 * Reads the backup and sends it to user's browser
	 */
	protected ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = (new File(request.getParameter("name"))).getName(); //to limit access to only backups directory
		String content_type = getServletContext().getMimeType(name);
		if(content_type==null)
			content_type = "text/plain";
		response.setContentType(content_type);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		
		InputStream in = backupStorage.getFile(name);
		FileCopyUtils.copy(in, response.getOutputStream());

		return null;
	}

	/**
	 * The setter for the backup storage - the object that serves backup access.
	 */
	public void setBackupStorage(BackupStorage storage) {
		this.backupStorage = storage;
	}
	
}
