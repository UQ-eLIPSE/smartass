/* @(#)SetFileTransactionItem.java 
 * 
 * This file is part of SmartAss and contains the SetFileTransactionItem class 
 * that is a part of SpringAss file storage infrastructure and represents 
 * the "store file to the repository" operation in the transaction.
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

import java.io.File;
import java.io.FileInputStream;

/**
 * The SetFileTransactionItem class is a part of SpringAss file storage infrastructure and represents 
 * the "store file to the repository" operation in the transaction.
 *
 * The repository storage transaction system allows to make consistent
 * changes to the repository content, e.g. do a chain of changes that have to be done all together
 * or have not to be done at all.
 */
public class SetFileTransactionItem extends AbstractRepositoryStorageTransactionItem {
	private File tmpfile;
	
	/**
	 * Creates new  {@link SetFileTransactionItem} for given scope, path and filename. 
	 * File content will be taken from temporary file where is was put by some other part of SmartAss
	 * 
	 * @param scope		Scope in the repository. 0 - TeX templates, 1 - examples pdf files or 2 - template related files
	 * @param path		Path to the file
	 * @param fileName	The name of the file
	 * @param tmpfile	temporary file with data tu be written to the repository
	 */
	public SetFileTransactionItem(int scope, String path, String fileName, File tmpfile) {
		this.scope = scope;
		this.path = path;
		this.fileName = fileName;
		this.tmpfile = tmpfile;
	}
	
	/**
	 * Do actual changes in the repository (put file to the repository, delete file etc) on transaction commit.
	 * 
	 * @param storage	repository storage
	 */
	@Override
	public void commit(RepositoryStorage storage) throws Exception {
		storage.setFile(scope, path, fileName, new FileInputStream(tmpfile));
		tmpfile.delete();
	}
	
	/**
	 * Undo (if any) all changes made in the repository and free allocated resources on transaction rollback.
	 * 
	 * @param storage	repository storage
	 */
	@Override
	public void rollback(RepositoryStorage storage) {
		tmpfile.delete();
	}
}
