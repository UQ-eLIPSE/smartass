package au.edu.uq.smartass.web.composer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;
import java.lang.IllegalArgumentException;
import java.lang.Integer;

import org.springframework.beans.factory.annotation.Autowired;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

public class SelectTemplatesControl implements Serializable {

	String nameFilter = "";
	String keywordsFilter = "";

        /**
         * Possible orderings
         */
        HashMap<String, Comparator> orderings;
        String currentOrdering;

	List<TemplatesItemModel> templates;

	private List<String> selectedIds = new ArrayList<String>();

	int classid;
	int topclassid;
	int pageNum;
	int pageNo;
        
        // @REVIEW Doing this will keep all the rows in a single page
        // We will do the pages in the client side javascript
        // There might be a better way to do this
        int rowsPerPage = Integer.MAX_VALUE;
	//int rowsPerPage = 20;           //TODO: move this to app settings
        
	int rowsNum;

        
        /**
         * Create a serializable comparator
         */
        class SerializableComparator<T> implements Serializable, Comparator {
            public int compare(Object o1, Object o2) {
                return 0;
            }
        };


        public SelectTemplatesControl() {

            super();

            // Using hashset instead of enum for cleaner UI
            orderings = new HashMap<String, Comparator>();
            
            SerializableComparator<TemplatesItemModel> nameAsc = new SerializableComparator<TemplatesItemModel>() {

                @Override
                public int compare(Object o1, Object o2) {
                    TemplatesItemModel t1 = (TemplatesItemModel) o1;
                    TemplatesItemModel t2 = (TemplatesItemModel) o2;

                    return t1.getName().compareTo(t2.getName());
                }
            };

            SerializableComparator<TemplatesItemModel> nameDes = new SerializableComparator<TemplatesItemModel>() {

                @Override
                public int compare(Object o1, Object o2) {
                    TemplatesItemModel t1 = (TemplatesItemModel) o1;
                    TemplatesItemModel t2 = (TemplatesItemModel) o2;

                    return t2.getName().compareTo(t1.getName());
                }
            };

            SerializableComparator<TemplatesItemModel> authAsc = new SerializableComparator<TemplatesItemModel>() {

                @Override
                public int compare(Object o1, Object o2) {
                    TemplatesItemModel t1 = (TemplatesItemModel) o1;
                    TemplatesItemModel t2 = (TemplatesItemModel) o2;

                    if (t1.getAuthor() == null || t2.getAuthor() == null) {
                        return 0;
                    }

                    return t1.getAuthor().getName().compareTo(t2.getAuthor().getName());
                }
            };

            SerializableComparator<TemplatesItemModel> authDes = new SerializableComparator<TemplatesItemModel>() {

                @Override
                public int compare(Object o1, Object o2) {
                    TemplatesItemModel t1 = (TemplatesItemModel) o1;
                    TemplatesItemModel t2 = (TemplatesItemModel) o2;

                    if (t1.getAuthor() == null || t2.getAuthor() == null) {
                        return 0;
                    }

                    return t2.getAuthor().getName().compareTo(t1.getAuthor().getName());
                }
            };

            // All the possible orderings
            orderings.put("Name - Ascending", nameAsc);
            orderings.put("Name - Descending", nameDes);
            orderings.put("Author - Ascending", authAsc);
            orderings.put("Author - Descending", authDes);
            // ROY TODO: Do the date ones
            orderings.put("Date - Ascending", nameAsc);
            orderings.put("Date - Descending", nameDes);

            currentOrdering = "Name - Descending";

        }

        /**
         * Returns the current ordering, as a string
         * @returns Returns the current ordering
         */
        public String getOrdering() {
            return currentOrdering;

        }

        /**
         * Sets the ordering of the templates
         * @param newOrder  The new ordering of the template, must be one of the keys
         *                  in the 'ordering' hashmap
         */
        public void setOrdering(String newOrder) {
            if (orderings.containsKey(newOrder)) {
                currentOrdering = newOrder;
            } else {
                throw new IllegalArgumentException("'" + newOrder + "' is not a valid order");
            }
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

        // TODO: This method doesn't seem to be used.
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
                // Do ordering in client side javascript instead
                //Collections.sort(templates, orderings.get(currentOrdering));

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
