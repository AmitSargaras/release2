package com.integrosys.cms.app.leiDateValidation.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;


public class LeiDateValidationJdbcImpl extends JdbcTemplateAdapter implements
ILeiDateValidationJdbc{

	private static final String SELECT_LEI_DATE_VALIDATION_TRX = "SELECT id,PARTY_ID,PARTY_NAME,LEI_DATE_VALIDATION_PERIOD,STATUS from CMS_LEI_DATE_VALIDATION";

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
	 * @return List of all authorized LeiDateValidation
	 */
	public SearchResult getAllLeiDateValidation() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_LEI_DATE_VALIDATION_TRX,
					new LeiDateValidationRowMapper());

		} catch (Exception e) {
			throw new LeiDateValidationException("ERROR-- While retriving LeiDateValidation");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all LeiDateValidation according to the query passed.
	 */

	public SearchResult getAllLeiDateValidation(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_LEI_DATE_VALIDATION_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new LeiDateValidationRowMapper());
			}

		} catch (Exception e) {
			throw new LeiDateValidationException("ERROR-- While retriving LeiDateValidation");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class LeiDateValidationRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBLeiDateValidation result = new OBLeiDateValidation();
			
			result.setPartyID(rs.getString("PARTY_ID"));
			result.setPartyName(rs.getString("PARTY_NAME"));
			result.setLeiDateValidationPeriod(rs.getString("LEI_DATE_VALIDATION_PERIOD"));
			result.setStatus(rs.getString("STATUS"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	//added by santosh
	/**
	 * @return List of all authorized LeiDateValidation
	 */
	public SearchResult getAllFilteredLeiDateValidation(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = "";
		if(null!=searchBy && searchBy.equals("partyID"))
			searchByValue = "PARTY_ID";
		else 
			searchByValue = "PARTY_NAME";
		
		String searchTextValue = searchText.toUpperCase();
		
		String GET_PRODUCT_MASTER_QUERY_STRING = "SELECT id,PARTY_ID,PARTY_NAME,LEI_DATE_VALIDATION_PERIOD,STATUS from CMS_LEI_DATE_VALIDATION"
				+ " where UPPER(" + searchByValue + ") LIKE '%" + searchTextValue
				+ "%' ";
		//GET_PRODUCT_MASTER_QUERY_STRING+=" ORDER BY productCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_PRODUCT_MASTER_QUERY_STRING,
						new LeiDateValidationRowMapper());
			}
		} catch (Exception e) {
			throw new LeiDateValidationException("ERROR-- While retriving LeiDateValidation");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
}

