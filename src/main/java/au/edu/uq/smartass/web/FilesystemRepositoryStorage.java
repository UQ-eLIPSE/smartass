/* This file is part of SmartAss and contains the FilesystemRepositoryStorage class - 
 * the RepositoryStorage implementation that stores data as files somewhere in the filesystem.
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.FileCopyUtils;

/**
 * The FilesystemRepositoryStorage class is the RepositoryStorage implementation 
 * that stores data as files somewhere in the filesystem.
 *
 */
public class FilesystemRepositoryStorage implements RepositoryStorage {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( FilesystemRepositoryStorage.class );
	
	/**
	 * The list of paths to containers of 
	 * [0] - templates
	 * [1] - example pdfs
	 * [2] - files  
	 */
	List<String> paths;
	
	/**
	 * This method returns the {@link InputStream} for repository stored object requested by parameters.
	 * The caller is responsible to close the stream returned by this method.
	 * 
	 *  @param scope	requested object type: 0 - template, 1 - example pdf, 2 - template related file
	 *  @param path		path to the object inside the given scope
	 *  @param name		object name
	 */
	public InputStream getFile(int scope, String path, String name) {
		try {
			return new FileInputStream(new File(new File(paths.get(scope), path), name));
		} catch (FileNotFoundException e) {}
		return null;
	}
	
	/**
	 * The setter for paths
	 *  
	 * @param paths		new paths value 
	 */
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
	
	/**
	 * This method writes the content of the {@link InputStream} as the repository stored object under given name and path
	 * into given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param stream	{@link InputStream} with the object content data
	 */
	public void setFile(int scope, String path, String name, InputStream stream) throws IOException {

		LOG.debug("setFile()[ scope=>{}, path=>{}, name=>{}, stream=>{} ]", scope, path, name, "-");

		if (path == null) path = "";

		File target_dir = new File(new File(paths.get(scope)), path);
		if (!target_dir.exists()) target_dir.mkdirs();

		File target = new File(target_dir, name);
		LOG.debug("setFile() : target => {}", target.getAbsolutePath());

		if (target.exists()) target.delete();
		
		if ( stream == null ) return;
		
		FileCopyUtils.copy(stream, new FileOutputStream(target));
	}

	/**
	 * This method behaves differently depending on what it receive as the <b>transaction</b> parameter.
	 * If <b>transaction</b> is null then it immediately writes the content of the {@link InputStream} 
	 * as the repository stored object under given name and path into given repository scope, 
	 * e.g. behaves identically to setFile method without <b>transaction</b> parameter.
	 * 
	 * If the <b>transaction</b> is not null it reads <b>stream</b> and stores it in the temporary file
	 * and put it to the repository on the transaction commit
	 * 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param stream	{@link InputStream} with the object content data
	 * @param transaction	{@link RepositoryStorageTransaction} instance
	 */
	public void setFile(int scope, String path, String name,
			InputStream stream, RepositoryStorageTransaction transaction)
			throws IOException {
		if(transaction==null)
			setFile(scope, path, name, stream);
		else {
			File tmpfile = File.createTempFile("ass", name);
			FileCopyUtils.copy(stream, new FileOutputStream(tmpfile));
			transaction.add(new SetFileTransactionItem(scope, path, name, tmpfile));
		}
	}
	
	/**
	 * This method writes the content of the byte array as the repository stored object under given name and path
	 * into given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param data		array with the object content data
	 */
	public void setFile(int scope, String path, String name, byte[] data) throws IOException {
		if(path==null)
			path = "";
		File target_dir = new File(new File(paths.get(scope)), path);
		if(!target_dir.exists())
			target_dir.mkdirs();
		File target = new File(target_dir, name);
		if(target.exists())
			target.delete();
		
		if(data==null)
			return;
		
		FileOutputStream ostream = new FileOutputStream(target);
		try {
			ostream.write(data);
		} finally {
			ostream.close();
		}
	}
	
	/**
	 * This method behaves differently depending on what it receive as the <b>transaction</b> parameter.
	 * If <b>transaction</b> is null then it immediately writes the content of the <b>data</b> 
	 * as the repository stored object under given name and path into given repository scope, 
	 * e.g. behaves identically to setFile method without <b>transaction</b> parameter.
	 * <br>
	 * <br>If the <b>transaction</b> is not null then this function stores data in the temporary file
	 * and put it to the repository only on the transaction commit
	 * 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param data		array with the object content data
	 * @param transaction	{@link RepositoryStorageTransaction} instance
	 */
	public void setFile(int scope, String path, String name, byte[] data,
			RepositoryStorageTransaction transaction) throws IOException {
		if(transaction==null)
			setFile(scope, path, name, data);
		else {
			File tmpfile = File.createTempFile("ass", name);
			FileOutputStream ostream = new FileOutputStream(tmpfile);
			try {
				ostream.write(data);
			} finally {
				ostream.close();
			}
			transaction.add(new SetFileTransactionItem(scope, path, name, tmpfile));
		}
	}
	
	/**
	 * This method deletes the file with given name and path from given repository scope 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 */
	public void deleteFile(int scope, String path, String name) throws IOException {

		LOG.debug("deleteFile()[ scope=>{}, path=>{}, name=>{} ]", scope, path, name);

		File file = new File(new File(new File(paths.get(scope)), path), name);

		LOG.debug("deleteFile() : FILE => {}", file.getAbsolutePath());

		if(file.exists()) file.delete();	
	}
	
	/**
	 * This method behaves differently depending on what it receive as the <b>transaction</b> parameter.
	 * If <b>transaction</b> is null then it immediately deletes the file with given name and path from 
	 * the given repository scope, e.g. behaves identically to the deleteFile method without <b>transaction</b> parameter.
	 * <br>
	 * <br>If the <b>transaction</b> is not null then this method shedules file deletion on
	 * the transaction commit
	 * 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to the object inside the given scope
	 * @param name		object name
	 * @param transaction	{@link RepositoryStorageTransaction} instance
	 */
	public void deleteFile(int scope, String path, String name,
			RepositoryStorageTransaction transaction) throws IOException {
		if(transaction==null)
			deleteFile(scope, path, name);
		else
			transaction.add(new DeleteFileTransactionItem(scope, path, name));
	}
	
	/**
	 * This method moves the file with given name and path from given repository scope
	 * to the new path/name 
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to existing object inside the given scope
	 * @param name		name of the existing object
	 * @param newPath	new path to the object 
	 * @param newName	new name for the object
	 */
	public void renameFile(int scope, String path, String name, String newPath, String newName) throws Exception {
	    File file = new File(new File(new File(paths.get(scope)), path), name);
	    File file2 = new File(new File(new File(paths.get(scope)),newPath), newName);
	    if (!file.renameTo(file2)) {
	    	throw new Exception("error renaming file " + file.getPath() + " to " +file2.getPath());
	        // File was not successfully renamed
	    	//Throw error or something
	    }
	}
	
	public void renameFile(int scope, String path, String name, String newPath,
			String newName, RepositoryStorageTransaction transaction)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Returns the size of the object stored in the repository
	 * 
	 * @param scope		object type: 0 - template, 1 - example pdf, 2 - template related file
	 * @param path		path to existing object inside the given scope
	 * @param name		name of the existing object
	 */
	public long getFileSize(int scope, String path, String name) {
		File f = new File(new File(paths.get(scope), path), name);
		return f.length();
	}
	
	/**
	 * Creates new transaction instance
	 *   
	 */
	public RepositoryStorageTransaction getTransaction() {
		return new RepositoryStorageTransactionImpl();
	}
	
}
