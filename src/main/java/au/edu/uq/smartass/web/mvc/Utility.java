/* This file is part of SmartAss and contains the Utility class that 
 * contains some utility functions.   
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
package au.edu.uq.smartass.web.mvc;

import java.security.MessageDigest;

/**
 * The Utility class contains some utility functions.   
 */
public class Utility {

	/**
	 * Counts md5 sum for given {@link String}
	 */
	public static String md5(String str) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");  
		md.update(str.getBytes());  
		byte[] hash = md.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {  
			if ((0xff & hash[i]) < 0x10) {  
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));  
			} else {  
				hexString.append(Integer.toHexString(0xFF & hash[i]));  
			}   
		}  
		return hexString.toString();
	}
}
