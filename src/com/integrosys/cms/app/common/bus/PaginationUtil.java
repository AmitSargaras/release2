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
public interface PaginationUtil {
	public String formCountQuery(String queryOrg);

	public String formPagingQuery(String queryOrg, PaginationBean pgBean);
}
