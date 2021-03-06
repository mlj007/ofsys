package org.ferrari.utils.pagination.entry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ferrari.ConstantKeys;
import org.ferrari.utils.pagination.PaginationKeys;
import org.ferrari.utils.pagination.factory.IQuery;

/**
 * 分页控制
 * 
 * @author kevin
 */
public class HibernateQuery implements IQuery{
	private int pageSize = 20;
	private int page = 1;
	private int total = 0;
	private String searchKeys = null;
	private List<?> queryList = null;
	/**是否有跳转*/
	private boolean turnPage = false;
	private int displayPage = 10;

	/**
	 * @return the turnPage
	 */
	public boolean isTurnPage() {
		return turnPage;
	}

	/**
	 * @param turnPage the turnPage to set
	 */
	public void setTurnPage(boolean turnPage) {
		this.turnPage = turnPage;
	}

	public HibernateQuery() {
		
	}
	
	public HibernateQuery(Object scope) {
		if(scope == null) return;
		if(scope instanceof HttpServletRequest){
			HttpServletRequest request = (HttpServletRequest)scope;
			String currpage = request.getParameter(ConstantKeys.PAGINATION_CURR_PAGE);
			currpage = (currpage == null || "null".equals(currpage) || "".equals(currpage)) ? "1" : currpage;
			this.setCurrentPage(Integer.valueOf(currpage));
			this.setSearchKeys((String)request.getAttribute(PaginationKeys.PARAMETER_KEY));
			request.setAttribute(PaginationKeys.PAGINATION_KEY, this);
		}else if(scope instanceof HttpSession) {
			//do nothing......
		}
	}
	
	public HibernateQuery(String type) {
		
	}
	
	public HibernateQuery(Integer currPage, Integer pageSize){
		this.setCurrentPage(currPage);
		this.pageSize = pageSize;
	}
	
	/**
	 * 获取总页面数
	 * 
	 * @return
	 */
	public int getPageCount() {
		return Math.max(1, total / pageSize + ((total % pageSize == 0) ? 0 : 1));
	}

	/**
	 * 获取最大记录数
	 * 
	 * @return
	 */
	public int getMax() {
		return this.pageSize;
	}

	/**
	 * 获取最小记录数
	 * 
	 * @return
	 */
	public int getMin() {
		return pageSize * (page - 1);
	}
	
	public int getSqlMax() {
		return 0;
	}

	public int getSqlMin() {
		return 0;
	}
	
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * 获取每页显示的记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	public Integer getFirstResult(){
		return getCurrentPage() * getPageSize();
	}
	
	public Integer getMaxResult(){
		return this.pageSize;
	}
	/**
	 * 设置每页显示的记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前页数
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return page;
	}

	/**
	 * 设置当前页数
	 * 
	 * @param page
	 */
	public void setCurrentPage(int page) {
		this.page = page;
		if (this.page <= 0)
			this.page = 1;
	}

	/**
	 * 获取上一页页数
	 * 
	 * @return
	 */
	public int getPreviousPage() {
		return Math.max(1, this.page - 1);
	}

	/**
	 * 获取下一页页数
	 * 
	 * @return
	 */
	public int getNextPage() {
		return Math.min(Math.max(1, getPageCount()), this.page + 1);
	}

	/**
	 * 获取查询字符串
	 * 
	 * @return
	 */
	public String getSearchKeys() {
		return searchKeys;
	}

	/**
	 * 设置查询字符串
	 * 
	 * @param searchKeys
	 */
	public void setSearchKeys(String searchKeys) {
		this.searchKeys = searchKeys;
	}

	/**
	 * @return the queryList
	 */
	public List<?> getQueryList() {
		return queryList;
	}

	/**
	 * @param queryList the queryList to set
	 */
	public void setQueryList(List<?> queryList) {
		this.queryList = queryList;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("pageSize=").append(getPageSize()).append("\n");
		buffer.append("pageCount=").append(getPageCount()).append("\n");
		buffer.append("currentPage=").append(getCurrentPage()).append("\n");
		buffer.append("total=").append(getTotal()).append("\n");
		buffer.append("max=").append(getMax()).append("\n");
		buffer.append("min=").append(getMin()).append("\n");
		buffer.append("searchKeys=").append(getSearchKeys()).append("\n");
		buffer.append("isTurnPage=").append(isTurnPage()).append("\n");
		buffer.append("size=").append(queryList.size());
		return buffer.toString();
	}

	public int getDisplayPage() {
		return displayPage;
	}
 
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}


}
