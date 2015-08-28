package au.edu.uq.smartass.web.composer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

public class SelectTemplatesControl implements Serializable {
	String nameFilter = "";
	String keywordsFilter = "";
	List<TemplatesItemModel> templates;
	List<String> selectedIds;
	int classid;
	int topclassid;
	int pageNum;
	int pageNo;
	int rowsPerPage;
	int rowsNum;
	
	
	public SelectTemplatesControl() {
		selectedIds = new ArrayList<String>();
		
		rowsPerPage = 20; //TODO: move this to app settings
/*		UserItemModel user = (UserItemModel) request.getSession().getAttribute("user");
		if(user!=null && user.getRowsPerPage()>0) 
			rowsPerPage = user.getRowsPerPage();*/
		
	}
	
	public String getKeywordsFilter() {
		return keywordsFilter;
	}

	public void setKeywordsFilter(String keywordsFilter) {
		this.keywordsFilter = keywordsFilter;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public List<TemplatesItemModel> getTempaltes() {
		return templates;
	}
	
	public List<String> getSelectedIds() {
		return selectedIds;
	}
	
	public void setSelectedIds(List<String> selectedIds) {
		this.selectedIds = selectedIds;
	}
	
	public void setTemplates(List<TemplatesItemModel> templates) {
		this.templates = templates;
	}
	
	public List<TemplatesItemModel> getTemplates() {
		return templates;
	}
	
	public int getClassid() {
		return classid;
	}
	
	public void setClassid(int classid) {
		this.classid = classid;
	}
	
	public void setTopclassid(int topclassid) {
		this.topclassid = topclassid;
	}
	
	public int getTopclassid() {
		return topclassid;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	
	public void setRowsNum(int rows_num) {
		rowsNum = rows_num;
		pageNum = (rows_num-1) / rowsPerPage;
	}
	
	public int getRowsNum() {
		return rowsNum;
	}
}
