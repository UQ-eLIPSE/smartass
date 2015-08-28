/* This file is part of SmartAss and contains the Zip class that is
 * the utility class for add/extract files to/from the zip archive. 
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/** This file is part of SmartAss and contains the Zip class that is
 * the utility class for add/extract files to/from the zip archive.
 */ 
public class Zip {
	/**
	 * Creates new {@link ZipOutputStream} for given zipfile .
	 */
	public static ZipOutputStream createZip(File zipfile) throws FileNotFoundException{
		return new ZipOutputStream(
	              new BufferedOutputStream(
		                  new FileOutputStream(zipfile)));
	}
	
	/**
	 * Creates a new {@link ZipOutputStream} for given zipfile and sets it to append mode.
	 * 
	 * @param zipfile	zip {@link File} to be appended
	 * @return	{@link ZipOutputStream} for given zip {@link File}
	 * @throws FileNotFoundException
	 */
	public static ZipOutputStream appendZip(File zipfile) throws FileNotFoundException{
		return new ZipOutputStream(
	              new BufferedOutputStream(
		                  new FileOutputStream(zipfile, true)));
	}
	
	/**
	 * Writes {@link String} data to the given {@link ZipOutputStream} as the entry with given name
	 * 
	 * @param zip			{@link ZipOutputStream} to which data will be added
	 * @param entryname		the name for the entry stored to {@link ZipOutputStream}
	 * @param data			{@link String} with data to be written to the {@link ZipOutputStream}
	 * @throws IOException
	 */
	public static void addToZip(ZipOutputStream zip, String entryname, String data) throws IOException {
        ZipEntry newEntry = new ZipEntry(entryname);
        newEntry.setSize(data.length());
        newEntry.setTime(System.currentTimeMillis());
        zip.putNextEntry(newEntry);
        byte[] buffer = data.getBytes();
        zip.write(buffer, 0, buffer.length);
        zip.closeEntry();
    }
	
	/**
	 * Writes data from the {@link File} to the given {@link ZipOutputStream} as the entry with given name
	 * 
	 * @param zip			{@link ZipOutputStream} to which data will be added
	 * @param entryname		the name for the entry stored to {@link ZipOutputStream}
	 * @param file			{@link File} with data to be written to the {@link ZipOutputStream}
	 * @throws IOException
	 */
	public static void addFileToZip(ZipOutputStream zip, String entryname, File file) throws IOException {
		ZipEntry newEntry = new ZipEntry(entryname);
		zip.putNextEntry(newEntry);
		//int entrySize = 0;
		
		InputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int bytesRead;
		try {
			while ((bytesRead = in.read(buffer)) >= 0)  
				zip.write(buffer, 0, bytesRead);
			zip.closeEntry();
		} finally {
			in.close();
		}
	}

}
