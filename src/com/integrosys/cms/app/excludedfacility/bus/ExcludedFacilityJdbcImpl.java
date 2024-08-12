package com.integrosys.cms.app.excludedfacility.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;

public class ExcludedFacilityJdbcImpl  extends JdbcTemplateAdapter implements
IExcludedFacilityJdbc{

	private static final String SELECT_EXCLUDED_FACILITY_TRX = "SELECT id,EXCLUDED_FACILITY_CATEGORY,STATUS from CMS_EXCLUDED_FACILITY where deprecated='N' ORDER BY EXCLUDED_FACILITY_CATEGORY asc  ";

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}
	/**
	 * @return List of all authorized Excluded Facility
	 */
	public SearchResult getAllExcludedFacility() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_EXCLUDED_FACILITY_TRX,
					new ExcludedFacilityRowMapper());

		} catch (Exception e) {
			throw new ExcludedFacilityException("ERROR-- While retriving ExcludedFacility");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	/**
	 * @return List of all ExcludedFacility according to the query passed.
	 */

	public SearchResult getAllExcludedFacility(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_EXCLUDED_FACILITY_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new ExcludedFacilityRowMapper());
			}

		} catch (Exception e) {
			throw new ExcludedFacilityException("ERROR-- While retriving ExcludedFacility");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	public class ExcludedFacilityRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBExcludedFacility result = new OBExcludedFacility();
			
			/*result.setExcludedFacilityCategory(rs.getString("EXCLUDED_FACILITY_CATEGORY"));*/
			result.setExcludedFacilityDescription(rs.getString("EXCLUDED_FACILITY_CATEGORY"));
			
			result.setStatus(rs.getString("STATUS"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	
}
