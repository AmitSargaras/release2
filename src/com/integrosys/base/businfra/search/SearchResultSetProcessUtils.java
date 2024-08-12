package com.integrosys.base.businfra.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Helper class to prepare <tt>SearchResult</tt> based on the
 * <tt>SearchCriteria</tt> and <tt>ResultSet</tt>.
 * <p>
 * At one time, {@link #RECORDS_PER_PAGE} of records will be fetched, at will
 * read until {@link SearchCriteria#getNItems()} times {@link #TOTAL_PAGES} (eg.
 * 10 * 10 = 100), or the <tt>ResultSet</tt> giving no more result.
 * <p>
 * JDBC DAO using this helper class, when using
 * {@link #processResultSet(SearchCriteria, ResultSet, ResultSetProcessAction)},
 * need to provide implementation of <tt>ResultSetProcessAction</tt> on how to
 * read each {@link ResultSet#next()} record, ie map it to a domain object.
 * @author Chong Jun Yong
 * 
 */
public abstract class SearchResultSetProcessUtils {

	private static final int TOTAL_PAGES = PropertyManager.getInt("pagination.total.page", 10);

	private static final int RECORDS_PER_PAGE = PropertyManager.getInt("pagination.records.per.page", 10);

	/**
	 * To process <tt>ResultSet</tt> and prepare the <tt>SearchResult</tt> based
	 * on <tt>SearchCriteria</tt> provided.
	 * 
	 * @param criteria a search criteria object, mainly for the start index and
	 *        number of items required
	 * @param rs the resultset after queried using jdbc
	 * @param action result set callback to handle a single record in ResultSet
	 * @return Prepared search result, which ready to be paginated, or
	 *         <tt>null</tt> if there is no record in the result set supplied.
	 */
	public static SearchResult processResultSet(SearchCriteria criteria, ResultSet rs, ResultSetProcessAction action) {
		Collection resultList = new ArrayList();

		int startIndex = criteria.getStartIndex();
		int maxItems = criteria.getNItems() * TOTAL_PAGES;

		if (startIndex >= maxItems) {
			maxItems = ((int) java.lang.Math.ceil(startIndex + 1 / maxItems)) * maxItems;
		}

		int endIndex = startIndex + RECORDS_PER_PAGE;
		int count = 0;
		try {
			while (rs.next() && (count <= maxItems)) {
				if (startIndex <= count && count < endIndex) {
					Object result = action.doInResultSet(rs);
					resultList.add(result);
				}
				count++;
			}
		}
		catch (SQLException ex) {
			throw new SearchDAOException("failed to process result set", ex);
		}

		return (count == 0) ? null : new SearchResult(criteria.getStartIndex(), resultList.size(), count, resultList);
	}

	/**
	 * A callback to act on the ResultSet for a single record rather than using
	 * {@link ResultSet#next()} again.
	 * 
	 */
	public interface ResultSetProcessAction {
		public Object doInResultSet(ResultSet rs) throws SQLException;
	}

}
