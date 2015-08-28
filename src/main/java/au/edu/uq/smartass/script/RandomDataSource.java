/* This file is part of SmartAss and contains the RandomDataSource class that is a 
 * datasource that returns a random record from the data set. 
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
package au.edu.uq.smartass.script;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
 * RandomDataSource class is a datasource that returns a random record from th
 */
public class RandomDataSource extends DataSource {
	Vector<DataArray> data = new Vector<DataArray>(); 
	boolean unique = false;
	
	/**
	 * Creates the {@link RandomDataSource} object and initializes its data from the data reader 
	 * 
	 * @param reader	the data reader
	 */
	public RandomDataSource(DataReader reader) {
		super(reader);
		initData();
	}
	
	/**
	 * Creates the {@link RandomDataSource} object and initializes its data from the data reader.
	 * The data records will be treated as unique if isUnique is true e.g. each record will be returned 
	 * to datasource caller only once. 
	 * 
	 * @param reader	the data reader
	 * @param isUnique	
	 */
	public RandomDataSource(DataReader reader, boolean isUnique) {
		super(reader);
		initData();
		unique = isUnique;
	}
	
	/**
	 * Initializes the data stream
	 */
	protected void initData() {
		DataArray d;
		reader.rewindDataStream();
		while((d = reader.readData())!=null)
			data.add(d);
	}
	
	@Override
	/**
	 * Returns next random data record
	 */
	public DataArray getData() {
		if(unique && data.size()==0)
			initData();
		int pos = RandomChoice.randInt(0, data.size()-1);
		DataArray d = data.get(pos);
		if(unique)
			data.remove(pos);
		return d;
	}

}
