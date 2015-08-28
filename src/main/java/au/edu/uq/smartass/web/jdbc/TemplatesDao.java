/* This file is part of SmartAss and contains the TemplatesDao class - the object-relational mapping class 
 * that maps the TemplatesItemModel to the database table(s).
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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.ModulesItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UpdatesItemModel;

/**
 * The TemplatesDao class is the object-relational mapping class 
 * that maps the TemplatesItemModel to the database table(s).
 */
public class TemplatesDao extends JdbcDaoSupport {
	public static final String TEMPLATES_SELECT = "select * from templates order by name";
	private ItemRowMapper mapper;

	private AuthorsDao authorsDao;
	private TemplateClassificationsDao classificationsDao = new TemplateClassificationsDao();
	private TemplateFilesDao filesDao = new TemplateFilesDao();
	private TemplateModulesDao modulesDao = new TemplateModulesDao();
	private UpdatesDao updatesDao;
	
	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link TemplatesItemModel} object
	 */
	class ItemRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	TemplatesItemModel item = new TemplatesItemModel();
	    	item.setId(rs.getInt("id"));
	    	item.setName(rs.getString("name"));
	    	String t = rs.getString("path"); 
	    	item.setPath(t==null?"":t);
	    	t = rs.getString("description");
    		item.setDescription(t==null?"":t);
    		t = rs.getString("keywords");
	    	item.setKeywords(t==null?"":t);
	    	item.setDtuploaded(rs.getDate("dtuploaded"));
	    	try {
	    		item.setDtcreated(rs.getDate("dtcreated"));
	    	} catch(Exception e) {
	    		item.setDtcreated(null);
	    	}
	    	
	    	item.setHasQuestions(rs.getInt("hasQuestions")==1);
	    	item.setHasSolutions(rs.getInt("hasSolutions")==1);
	    	item.setHasShortanswers(rs.getInt("hasShortanswers")==1);
	    	
	    	item.setAuthor(authorsDao.getItem(rs.getInt("author_id")));
	    	item.setClassifications(classificationsDao.select(item.getId()));
	    	item.setFiles(filesDao.select(item.getId()));
	    	item.setModules(modulesDao.select(item.getId()));
	    	item.setUpdates(updatesDao.select(item.getId()));
	    	
	    	return item;
		}
	}
	
	/**
	 * The DAO class for the classifications of the given template
	 *
	 */
	class TemplateClassificationsDao extends ClassificationsDao {
		public List<ClassificationsItemModel> select(int template_id) {
			if(getDataSource()==null)
				setDataSource(getThis().getDataSource());
			return super.select("select c.* from templates_classifications t, classifications c " +
					"where t.class_id=c.id and t.template_id=?", new Integer[]{template_id});
		}
	}

	/**
	 * The DAO class for the files of the template
	 *
	 */
	class TemplateFilesDao extends FilesDao {
		@SuppressWarnings("unchecked")
		public List<FilesItemModel> select(int template_id) {
			if(getDataSource()==null)
				setDataSource(getThis().getDataSource());
			return (List<FilesItemModel>) getJdbcTemplate().query(
					"select f.* from templates_files t, files f where t.file_id=f.id and t.template_id=?", 
					new Integer[]{template_id},
					getRowMapper());
		}
	}

	/**
	 * The DAO class for the modules of the template
	 */
	class TemplateModulesDao extends ModulesDao {
		public List<ModulesItemModel> select(int template_id) {
			if(getDataSource()==null)
				setDataSource(getThis().getDataSource());
			return (List<ModulesItemModel>) getJdbcTemplate().query("select m.* from templates_modules t, modules m " +
					"where t.module_id=m.id and t.template_id=?", new Integer[]{template_id}, getRowMapper());
		}
	}


	private List<TemplatesItemModel> select(String sql, Object[] params) {
		if(mapper==null)
			mapper = new ItemRowMapper();
		return (List<TemplatesItemModel>) getJdbcTemplate().query(sql, params, (RowMapper) mapper);
	}

	/**
	 * Returns the list of all templates from the repository
	 */
	public List<TemplatesItemModel> select() {
		return select(TEMPLATES_SELECT, new Object[]{});
	}
	
	/**
	 * Returns the number of templates in the repository which conforms to the filter conditions
	 * passed to this function
	 */
	public int countRows(String templ_filter, String keyword_filter, int classification_id) {
		Vector<Object> params = new Vector<Object>();
		String sql = composeSql("select count(*) from templates t ", templ_filter, keyword_filter, classification_id, params);
		return getJdbcTemplate().queryForInt(sql, params.toArray());
	}
	
	/**
	 * Returns the list of templates from the repository which conforms to the filter conditions
	 * passed to this function
	 */
	public List<TemplatesItemModel> select(String templ_filter, String keyword_filter, int classification_id) {
		return select(templ_filter, keyword_filter, classification_id, -1, -1, null);
	}

	/**
	 * Returns the list of rows_num templates from the repository which conforms to the filter conditions
	 * passed to this function, skipping skip_num records
	 */
	public List<TemplatesItemModel> select(String templ_filter, String keyword_filter, int classification_id,
			int skip_num, int rows_num, String orderBy ) {
		Vector<Object> params = new Vector<Object>();
		String sql = composeSql("select * from templates t ", templ_filter, keyword_filter, classification_id, params);
		//set order of selected items
		sql = sql + "order by ";
		if(orderBy==null || orderBy.length()==0)
			sql = sql + " t.id desc ";
		else {
			String[] ords = orderBy.split(",");
			sql = sql + " t." + ords[0];
			for(int i=1;i<ords.length;i++)
				sql = sql + ", t." + ords;
		}
		//skip some items (this is used for splitting long lists to a number of pages) 
		if(skip_num>=0) {
			sql = sql + " LIMIT " + skip_num + ", " + rows_num; 
		}
		return select(sql, params.toArray());
	}
	
	private String composeSql(String select, String templ_filter, String keyword_filter, int classification_id,
			List<Object> params) {
		if(templ_filter==null) templ_filter = "";
		if(keyword_filter==null) keyword_filter = "";
		
		StringBuffer sql = new StringBuffer(select);

		templ_filter = templ_filter.trim();
		keyword_filter = keyword_filter.trim();
		if(!templ_filter.equals("") || !keyword_filter.equals("") || classification_id!=0) {
			if(classification_id!=0) {
				sql.append("inner join templates_classifications tc on t.id=tc.template_id " +
						"inner join classifications c on tc.class_id=c.id "); 
			}
			
			sql.append(" where ");

			if(classification_id!=0) {
				sql.append("(tc.class_id=? or c.parent_id=?) ");
				params.add(classification_id);
				params.add(classification_id);
			}
			
			if(!templ_filter.equals("")) {
				if(classification_id!=0)
					sql.append(" and ");
				sql.append(" t.name like ? ");
				params.add("%" + templ_filter + "%");
			}

			if(keyword_filter.length()!=0) {
				if(classification_id!=0 || templ_filter.length()!=0)
					sql.append(" and ");
				String[] keys = keyword_filter.split("[\\ \\,]");
				String clause = "(";
				boolean need_and = false;
				for(int i=0;i<keys.length;i++) { 
					if(keys[i].length()>0) {
						clause = clause + (need_and?" and ":"") + " keywords like ?";
						params.add("%" + keys[i] + "%");
						need_and = true;
					}
				}
				if(clause.length()>1)
					sql.append(clause + ")");
			}
		}
		return sql.toString();
	}

	/**
	 * Selects the given number of latest templates from the database 
	 */
	public List<TemplatesItemModel> selectLatest(int number) {
		if(number<=0)
			return select("select * from templates order by id desc", null);
		return select("select * from templates order by id desc limit " + number, null);
	}

	/**
	 * Get the TemplatesItemModel object by its id
	 * 
	 * @param id	the object's id
	 * @return		the object
	 */
	public TemplatesItemModel getItem(int id) {
		List<TemplatesItemModel> items = select("select * from templates where id=?", new Integer[]{id});
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	/**
	 * Get the TemplatesItemModel object by its name
	 * 
	 * @param name	the object's name
	 * @return		the object
	 */
	public TemplatesItemModel getItem(String name) {
		List<TemplatesItemModel> items = select("select * from templates where name=?", new String[]{name});
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	private TemplatesDao getThis() {
		return this;
	}
	
	/**
	 * Updates the data in the database by data from the given TemplatesItemModel object.
	 * If there is no record for this object in the database the new record will be created.
	 * 
	 * @param item	oject to be updated
	 */
	public void updateItem(TemplatesItemModel item) {
		//Logger log = Logger.getLogger(getClass());
		//first update template itself
		if(item.getId()==0) {
			try {
				//If the template is new we need to retrieve an autogenerated key and put it to the id field
				Connection conn = getJdbcTemplate().getDataSource().getConnection();
				PreparedStatement st = conn.prepareStatement(
						"insert into templates (name, keywords, description, author_id, hasQuestions, hasSolutions, hasShortanswers, dtcreated) values (?, ?, ?, ?, ?, ?, ?, ?)");
				st.setString(1, item.getName());
				st.setString(2, item.getKeywords());
				st.setString(3, item.getDescription());
				st.setInt(4, item.getAuthor().getId());
				st.setInt(5, item.isHasQuestions()?1:0);
				st.setInt(6, item.isHasSolutions()?1:0);
				st.setInt(7, item.isHasShortanswers()?1:0);
				if(item.getDtcreated()!=null)
					st.setDate(8, new Date(item.getDtcreated().getTime()));
				else
					st.setDate(8, null);
				st.execute();
				ResultSet ks = st.getGeneratedKeys();
				ks.next();
				item.setId(ks.getInt(1));
				ks.close();
				st.close();
				conn.close();
			} catch (SQLException e) {				
			}
		} else {
			Date date = null;
			if(item.getDtcreated()!=null)
				date = new Date(item.getDtcreated().getTime());
			getJdbcTemplate().update("update templates set name=?, keywords=?, description=?, author_id=?, hasQuestions=?, hasSolutions=?, hasShortanswers=?, dtcreated=? where id=?",
					new Object[]{item.getName(), item.getKeywords(), item.getDescription(), item.getAuthor().getId(), 
					item.isHasQuestions()?1:0, item.isHasSolutions()?1:0, item.isHasShortanswers()?1:0,
					date,
					item.getId()});
		}
		//then update links to various template child data 
		getJdbcTemplate().update("delete from templates_modules where template_id=?", new Object[]{item.getId()});
		for(ModulesItemModel o : item.getModules()) 
			getJdbcTemplate().update("insert into templates_modules (template_id, module_id) values (?, ?)", 
					new Object[]{item.getId(), o.getId()});
		getJdbcTemplate().update("delete from templates_files where template_id=?", new Object[]{item.getId()});
		for(FilesItemModel o : item.getFiles())
			getJdbcTemplate().update("insert into templates_files (template_id, file_id) values (?, ?)", 
					new Object[]{item.getId(), o.getId()});
		getJdbcTemplate().update("delete from templates_classifications where template_id=?", new Object[]{item.getId()});
		for(ClassificationsItemModel o : item.getClassifications())
			getJdbcTemplate().update("insert into templates_classifications (template_id, class_id) values (?, ?)", 
					new Object[]{item.getId(), o.getId()});
		getJdbcTemplate().update("delete from updates where template_id=?", new Object[]{item.getId()});
//		log.debug(item.getUpdates().size());
		for(UpdatesItemModel o : item.getUpdates()) {
/*			log.debug(o.getUpdateDate());*/
			//log.debug(o.getAuthor().getName() + o.getAuthor().getId());
			if(o.getAuthor().getId()==0)
				authorsDao.updateItem(o.getAuthor());
			//log.debug(o.getAuthor().getName() + o.getAuthor().getId());
			o.setTemplateId(item.getId());
			updatesDao.updateItem(o);
		}
	}

	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the object to be deleted
	 */
	public void  deleteItem(int id) {
		getJdbcTemplate().update("delete from templates_modules where template_id=?", new Object[]{id});
		getJdbcTemplate().update("delete from templates_files where template_id=?", new Object[]{id});
		getJdbcTemplate().update("delete from templates_classifications where template_id=?", new Object[]{id});
		getJdbcTemplate().update("delete from updates where template_id=?", new Object[]{id});
		getJdbcTemplate().update("delete from templates where id=?", new Object[]{id});
	}
	

	/**
	 * The setter for the authorsDao property
	 */
	public void setAuthorsDao(AuthorsDao authorsDao) {
		this.authorsDao = authorsDao;
	}

	/**
	 * The setter for the updatesDao property
	 */
	public void setUpdatesDao(UpdatesDao updatesDao) {
		this.updatesDao = updatesDao;
	}
}
