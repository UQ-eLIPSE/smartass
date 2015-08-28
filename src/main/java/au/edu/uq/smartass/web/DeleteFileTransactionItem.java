/* @(#)DeleteFileTransactionItem.java
 *
 * This file is part of SpringAss and describes the DeleteFileTransactionItem class.  
 * This class extends AbstractRepositoryStorageTransactionItem and implements RepositoryStorageTransactionItem interface 
 * and contains activity related to repository contained file delete.  
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

import java.io.IOException;

/**
 * The DeleteFileTransactionItem class extends AbstractRepositoryStorageTransactionItem 
 * and implements RepositoryStorageTransactionItem interface. 
 * It contains activity related to repository contained file delete.
 */
public class DeleteFileTransactionItem extends
		AbstractRepositoryStorageTransactionItem {
	/**
	 * Initialize new DeleteFileTransactionItem instance
	 * 
	 * @param scope		Scope in the repository. 0 - TeX templates, 1 - examples pdf files or 2 - template related files
	 * @param path		Path to the file
	 * @param fileName	The name of the file
	 */
	public DeleteFileTransactionItem(int scope, String path, String fileName) {
		this.scope = scope;
		this.path = path;
		this.fileName = fileName; 
	}
	
	/**
	 * Actually deletes file from the repository on the transaction commit
	 * 
	 *  @param storage	the instance of the repository storage
	 */
	@Override
	public void commit(RepositoryStorage storage) throws IOException {
		storage.deleteFile(scope, path, fileName);
	}

}
