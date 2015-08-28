/* @(#)AbstractRepositoryStorageTransactionItem.java
 *
 * This file is part of SpringAss and describes AbstractRepositoryStorageTransactionItem class.  
 * This class implements RepositoryStorageTransactionItem interface that is a part of SpringAss file storage infrastructure
 * and contains some features that is common for any RepositoryStorageTransactionItem implementation.  
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

import java.io.Serializable;

/**
 * The AbstractRepositoryStorageTransactionItem class implements RepositoryStorageTransactionItem interface 
 * that is a part of SpringAss file storage infrastructure and represents a single operation in the transaction.  
 * This class contains some features common for any RepositoryStorageTransactionItem implementation.  
 *
 */
public class AbstractRepositoryStorageTransactionItem implements RepositoryStorageTransactionItem, Serializable {
	protected RepositoryStorageTransaction transaction;
	//Scope in the repository. 0 - TeX templates, 1 - examples pdf files or 2 - template related files
	protected int scope;
	protected String fileName;
	protected String path;

	/**
	 * Do actual changes in the repository (put file to the repository, delete file etc) on transaction commit.
	 * 
	 * @param storage	repository storage
	 */
	public void commit(RepositoryStorage storage) throws Exception {};
	
	/**
	 * Undo (if any) all changes made in the repository and free allocated resources on transaction rollback.
	 * 
	 * @param storage	repository storage
	 */
	public void rollback(RepositoryStorage storage) {};
	
	/**
	 * Assign a transaction to this transaction item.
	 * 
	 * @param transaction	an RepositoryStorageTransaction item
	 */
	public void setTransaction(RepositoryStorageTransaction transaction) {
		if(this.transaction==null) //can't change transaction once initialized 
			this.transaction = transaction;
	}
	
	/**
	 * Get the transaction to which this RepositoryStorageTransactionItem belongs
	 */
	public RepositoryStorageTransaction getTransaction() {
		return transaction;
	}
}
