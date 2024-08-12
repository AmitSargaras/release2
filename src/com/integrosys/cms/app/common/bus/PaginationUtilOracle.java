/*
 * Created on Nov 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.common.bus;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginationUtilOracle implements PaginationUtil {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.common.bus.PaginationUtil#formPagingQuery(java
	 * .lang.String, com.integrosys.cms.app.common.bus.PaginationBean)
	 */
	public String formPagingQuery(String queryOrg, PaginationBean pgBean) {
		// TODO Auto-generated method stub
		String result = "select * from ( select /*+ FIRST_ROWS(n) */ a.*, ROWNUM rnum from (" + queryOrg
				+ ") a where ROWNUM <= " + pgBean.getEndIndex() + ") where rnum  >= " + pgBean.getStartIndex();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.common.bus.PaginationUtil#formCountQuery(java.
	 * lang.String)
	 */
	public String formCountQuery(String queryOrg) {
		// TODO Auto-generated method stub
		return "select count(1) from (" + queryOrg + ")";
	}

}
