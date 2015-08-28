/* This file is part of SmartAss and contains the ListBackupsController class that 
 * lists repository backups that is stored on the server. It's supposed that
 * there are a number of last backups available. 
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.web.BackupStorage;
import au.edu.uq.smartass.web.UserItemModel;

/**
 * The ListBackupsController class that  lists repository backups that is stored on the server. 
 * It's supposed that there are a number of last backups available.
 */
public class ListBackupsController extends UserRequieredController {
	BackupStorage backupStorage;
	
	/**
	 * Creates ListBackupsController and sets user rights mask to the administrative rights.
	 */
	public ListBackupsController() {
		rightsMask = UserItemModel.RIGHT_ADMIN;
	}
	
	@Override
	/**
	 * Put the list of available backups to the model
	 */
	protected ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", backupStorage.listBackups());
		return new ModelAndView("backups", map);
	}
	
	public void setBackupStorage(BackupStorage backupStorage) {
		this.backupStorage = backupStorage;
	}
}
