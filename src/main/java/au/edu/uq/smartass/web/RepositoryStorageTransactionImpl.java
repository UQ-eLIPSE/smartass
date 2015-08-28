/* This file is a part of SmartAss and contains the RepositoryStorageTransactionImpl class.
 * This class implements RepositoryStorageTransaction interface. 
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The RepositoryStorageTransactionImpl class implements RepositoryStorageTransaction interface.
 * This is the part of the repository storage transaction system that allows to make consistent
 * changes to the repository content, e.g. do a chain of changes that have to be done all together
 * or have not to be done at all.
 */
public class RepositoryStorageTransactionImpl implements
		RepositoryStorageTransaction, Serializable {
	protected List<RepositoryStorageTransactionItem> items;
	protected boolean active;

	/**
	 * This function adds the {@link RepositoryStorageTransactionItem} to the list of items that will be 
	 * executed on transaction commit
	 */
	public void add(RepositoryStorageTransactionItem item) {
		if(item.getTransaction()!=null)
			return;
		start();
		items.add(item);
		item.setTransaction(this);
	}

	/**
	 * Commit all changes to the given {@link RepositoryStorage}
	 */
	public void commit(RepositoryStorage storage) throws Exception {
		if(active) {
			for(RepositoryStorageTransactionItem it: items)
				it.commit(storage);
			items.clear();
			active = false;
		}
	}

	/**
	 * Rollback all changes to the given {@link RepositoryStorage}
	 */
	public void rollback(RepositoryStorage storage) {
		if(active) {
			for(RepositoryStorageTransactionItem it: items)
				it.rollback(storage);
			items.clear();
			active = false;
		}
	}

	/**
	 * Start transaction. This method do nothing if transaction is already active.
	 */
	public void start() {
		if(!active) {
			if(items==null)
				items = new ArrayList<RepositoryStorageTransactionItem>();
			active = true;
		}
	}
	
	public boolean isActive() {
		return active;
	}

}
