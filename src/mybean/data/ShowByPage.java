package mybean.data;

import com.sun.rowset.*;

/**
 * @author 小狮
 *
 */
// 描述记录的分页信息
public class ShowByPage {
	CachedRowSetImpl rowSet = null;// 存储表中全部记录的行集对象
	int pageSize = 10;// 每页显示的记录数
	int pageAllCount = 0;// 分页后的总页数
	int showPage = 1;// 当前显示页
	StringBuffer presentPageResult;// 显示当前页内容

	public CachedRowSetImpl getRowSet() {
		return rowSet;
	}

	public void setRowSet(CachedRowSetImpl rowSet) {
		this.rowSet = rowSet;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageAllCount() {
		return pageAllCount;
	}

	public void setPageAllCount(int pageAllCount) {
		this.pageAllCount = pageAllCount;
	}

	public int getShowPage() {
		return showPage;
	}

	public void setShowPage(int showPage) {
		this.showPage = showPage;
	}

	public StringBuffer getPresentPageResult() {
		return presentPageResult;
	}

	public void setPresentPageResult(StringBuffer presentPageResult) {
		this.presentPageResult = presentPageResult;
	}

}
