/*
 * Created on Nov 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.common.bus;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginationResult implements Serializable {
	private long count;

	private List resultList;

	/**
	 * @return Returns the count.
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count The count to set.
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return Returns the resultList.
	 */
	public List getResultList() {
		return resultList;
	}

	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
}
