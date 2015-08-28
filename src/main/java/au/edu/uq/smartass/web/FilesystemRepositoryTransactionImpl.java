/* This file is a part of SmartAss and contains the FilesystemRepositoryTransactionImpl class.
 * Actually this class adds nothing to the RepositoryStorageTransactionImpl which it extends.
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
 * The FilesystemRepositoryTransactionImpl class adds nothing to the RepositoryStorageTransactionImpl 
 * which it extends.
 * This is the part of the repository storage transaction system that allows to make consistent
 * changes to the repository content, e.g. do a chain of changes that have to be done all together
 * or have not done at all.
 */
public class FilesystemRepositoryTransactionImpl extends
		RepositoryStorageTransactionImpl {

}
