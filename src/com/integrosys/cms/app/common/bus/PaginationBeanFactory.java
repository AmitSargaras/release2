package com.integrosys.cms.app.common.bus;

import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * <p>
 * A factory to build pagination bean based on the <tt>SearchCriteria</tt> and
 * number of records per page and also total pages on the screen.
 * 
 * <p>
 * Number of records to be fetched is based on the start index (offset) of the
 * current search. If current search offset is the starting of (records per page
 * * total pages), then number of records of (records per page * total pages)
 * will be fetched, otherwise number of records of (records per page) will be
 * fetched instead.
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * Records per page : 10
 * Total pages on the screen : 10
 * 
 * if current search start index (offset) : 0, then 
 * 	(10 * 10) + 1 = 101 records will be fetch.
 *  (that 101th records is to ensure next 10 pages can be shown on the screen.
 *  
 * if current search start index (offset) : 10, 
 * then records from 11 until 21 will be fetched.
 * 
 * So with above mechanism, we should store the total records for the 10 pages
 * in somewhere, such as session for the purpose of total number of records
 * for the 10 pages to be shown correctly.
 * </pre>
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class PaginationBeanFactory {
	/**
	 * To build a pagination bean based on <tt>SearchCriteria</tt>, number of
	 * records per page, and total pages on the screen.
	 * @param criteria search criteria instance
	 * @param recordsPerPage number of records per page
	 * @param totalPage total pages on the screen
	 * @return pagination bean to be used for pagination query
	 */
	public static PaginationBean buildPaginationBean(SearchCriteria criteria, int recordsPerPage, int totalPage) {
		return buildPaginationBean(criteria.getStartIndex(), recordsPerPage, totalPage);
	}

	/**
	 * To build a pagination bean based on the start index offset of the current
	 * search, number of records per page and total pages on the screen.
	 * @param offSet start index for the current search, start from 0
	 * @param recordsPerPage number of records per page
	 * @param totalPage total pages on the screen
	 * @return pagination bean to be used for the pagination query
	 */
	public static PaginationBean buildPaginationBean(int offSet, int recordsPerPage, int totalPage) {
		int totalAvalaibleRecordsForPages = recordsPerPage * totalPage;

		int pagingStartIndex = offSet + 1;
		int pagingEndIndex = offSet
				+ ((offSet % totalAvalaibleRecordsForPages) == 0 ? totalAvalaibleRecordsForPages : recordsPerPage) + 1;

		return new PaginationBean(pagingStartIndex, pagingEndIndex);
	}
}
