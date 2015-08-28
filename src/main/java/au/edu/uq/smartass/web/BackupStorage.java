/* @(#)BackupStorage.java
 *
 * This file is part of SpringAss and describes class BackupStorage that is responsible for
 * access to repository backups 
 * 
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package au.edu.uq.smartass.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * The BackupStorage class is responsible for access to repository backups. 
 * The backups are supposed to be created, updated and/or removed by some external process. 
 * 
 */
public class BackupStorage {
	String backupPath;

	/**
	 * The setter for the backup path
	 */
	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	
	/**
	 * Lists backup files 
	 */
	public String[] listBackups() {
		if(backupPath!=null) {
			File bkdir = new File(backupPath);
			if(bkdir.exists() && bkdir.isDirectory()) { 
				String[] flist = bkdir.list();
				java.util.Arrays.sort(flist);
				String[] flistm = new String[flist.length];
				for(int i=0;i<flist.length;i++)
					flistm[i] = flist[flist.length-1-i];
				return flistm;
			}
		}
		return null;
	}

	/**
	 * Returns {@link InputStream} for given file with backup
	 * 
	 * @param name	file name
	 * 
	 * @return	{@link InputStream} for backup file
	 */
	public InputStream getFile(String name) {
		try {
			return new FileInputStream(new File(new File(backupPath), name));
		} catch (FileNotFoundException e) {}
		return null;
	}
}
