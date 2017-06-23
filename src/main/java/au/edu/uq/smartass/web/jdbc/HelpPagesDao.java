/* This file is part of SmartAss and contains the HelpPagesDao class - the object-relational mapping class 
 * that maps the HelpPageItem to the database table(s).
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
package au.edu.uq.smartass.web.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import au.edu.uq.smartass.web.HelpPageItem;

/**
 * The HelpPagesDao class is the object-relational mapping class 
 * that maps the HelpPageItem to the database table(s).
 */
@SuppressWarnings("unchecked")
public class HelpPagesDao extends JdbcDaoSupport {
	private ItemRowMapper rowMapper;
	
	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link HelpPageItem} object
	 */
	class ItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			HelpPageItem item = new HelpPageItem();
			item.setContext(rs.getString("context"));
			item.setText(rs.getString("text"));
			item.setTitle(rs.getString("title"));
			return item;
		}
	}
	
	/**
	 * Returns the FilesItemModel object by context where help was requested
	 * 
	 * @param context	the help context
	 * @return		the help page
	 */
	public HelpPageItem getItem(String context) {
		List<HelpPageItem> items = getJdbcTemplate().query("select * from web_help where context=?", new String[]{context}, getRowMapper() );
		if(items.size()==0) {
			HelpPageItem item = new HelpPageItem();
			item.setContext(context);
			item.setText("The help for this page can not be found!");
			item.setTitle("Unknown Page");
			return item;
		}
		return items.get(0); 
	}
	
	private ItemRowMapper getRowMapper() {
		if(rowMapper==null)
			rowMapper = new ItemRowMapper();
		return rowMapper;
	}
}
