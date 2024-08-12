package com.integrosys.cms.app.common.bus;

import java.io.Serializable;

public final class PaginationBean implements Serializable {

	private static final long serialVersionUID = -8172267911109457753L;

	private long startIndex;

	private long endIndex;

	private long totalCount;

	public PaginationBean(long startIndex, long endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public PaginationBean(long startIndex, long endIndex, long totalCount) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.totalCount = totalCount;
	}

	/**
	 * @return Returns the endIndex.
	 */
	public long getEndIndex() {
		return endIndex;
	}

	/**
	 * @return Returns the startIndex.
	 */
	public long getStartIndex() {
		return startIndex;
	}

	/**
	 * @return Returns the totalCount.
	 */
	public long getTotalCount() {
		return totalCount;
	}

}
