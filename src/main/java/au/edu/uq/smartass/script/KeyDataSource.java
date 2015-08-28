/* This file is part of SmartAss and contains the KeyDataSource class that is the 
 * datasource for data access in scripts that returns the data record by the key field.   
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
 * The KeyDataSource class is the datasource for data access in scripts 
 * that returns data record by key field.
 */
public class KeyDataSource extends DataSource {
	Vector<DataArray> data = new Vector<DataArray>(); 

	/**
	 * Creates the datasource and reads the data record that has <code>key</code> string in the field number <code>keyno</code>.
	 * If there is more then one record with the same key field than all these records are red.  
	 * 
	 * @param reader	the data reader from where data is red
	 * @param keyno		the key field number
	 * @param key		the key value
	 */
	public KeyDataSource(DataReader reader, int keyno, String key) {
		super(reader);
		
		DataArray d;
		while((d = reader.readData())!=null)
			if(d.getField(keyno).equals(key))
				data.add(d);
		
		reader.close();
	}
	
	@Override
	/**
	 * Returns randomly one of data records red on datasource creation. 
	 * Usually there is an only one such record for KeyDataSource that matches to the key given to
	 * this object on its creation.
	 */
	public DataArray getData() {
		if(data.size()==0)
			return new DataArray();
		int pos = RandomChoice.randInt(0, data.size()-1);
		return data.get(pos);
	}

}
