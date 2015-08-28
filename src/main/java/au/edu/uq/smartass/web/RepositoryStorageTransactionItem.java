/* @(#)RepositoryStorageTransactionItem.java 
 * 
 * This file is part of SmartAss and contains the RepositoryStorageTransactionItem interface 
 * that is a part of SpringAss file storage infrastructure 
 * and represents a single operation in the transaction.
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

/**
 * The RepositoryStorageTransactionItem interface is a part of SpringAss file storage infrastructure 
 * and represents a single operation in the transaction.  
 * The repository storage transaction system allows to make consistent
 * changes to the repository content, e.g. do a chain of changes that have to be done all together
 * or have not to be done at all.
 *
 */
public interface RepositoryStorageTransactionItem {
	public void commit(RepositoryStorage storage) throws Exception;
	public void rollback(RepositoryStorage storage);
	public RepositoryStorageTransaction getTransaction();
	public void setTransaction(RepositoryStorageTransaction transaction);
}
