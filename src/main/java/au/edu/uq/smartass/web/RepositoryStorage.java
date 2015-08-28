/* This file is part of SmartAss and contains the RepositoryStorage interface that contains a set of
 * repository manipulation methods.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The interface for the smartass repository storage. This interface contains a set of
 * repository manipulation methods.
 */
public interface RepositoryStorage {
	/**
	 * This method returns the {@link InputStream} for the repository stored object requested by parameters
	 * 
	 *  @param scope	requested object type: 0 - template, 1 - example pdf, 2 - template related file
	 *  @param path		path to the object inside the given scope
	 *  @param name		the object name
	 */
	InputStream getFile(int scope, String path, String name);
	

	/**
	 * This method returns the size of the repository stored object requested by parameters
	 * 
	 *  @param scope	requested object type: 0 - template, 1 - example pdf, 2 - template related file
	 *  @param path		path to the object inside the given scope
	 *  @param name		the object name
	 */
	long getFileSize(int scope, String path, String name);
	
	/**
	 * This method writes the content of the {@link InputStream} as the repository stored object under given name and given path
	 * into given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param stream	{@link InputStream} whith object content data
	 */
	void setFile(int scope, String path, String name, InputStream stream)  throws IOException;
	
	void setFile(int scope, String path, String name, InputStream stream, RepositoryStorageTransaction transaction)  throws IOException;
	
	/**
	 * This method writes the content of the data parameter as the repository stored object under given name and given path
	 * into given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param data      object content data
	 */
	void setFile(int scope, String path, String name, byte[] data) throws IOException;
	
	void setFile(int scope, String path, String name, byte[] data, RepositoryStorageTransaction transaction) throws IOException;
	
	/**
	 * This method deletes the repository stored object under given name and given path
	 * in the given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 */
	void deleteFile(int scope, String path, String name) throws IOException;

	void deleteFile(int scope, String path, String name, RepositoryStorageTransaction transaction) throws IOException;

	/**
	 * This method renames the repository stored object with given name and given path
	 * in the given repository scope to new name and path
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param newPath		new path to the object inside the given scope
	 * @param newName		new object name
	 * @throws Exception 
	 */
	void renameFile(int scope, String path, String name, String newPath, String newName) throws Exception;
	
	void renameFile(int scope, String path, String name, String newPath, String newName, RepositoryStorageTransaction transaction) throws Exception;
	
	RepositoryStorageTransaction getTransaction();
}
