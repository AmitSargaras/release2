package com.integrosys.cms.app.udf.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;

public class UdfJdbcImpl extends JdbcTemplateAdapter implements
IUdfJdbc{

	private static final String SELECT_UDF_TRX = "SELECT id,MODULEID,MODULENAME,FIELDNAME,FIELDTYPEID,FIELDTYPE,OPTIONS,SEQUENCE,MANDATORY,NUMERIC_LENGTH,STATUS,DEPRECATED from CMS_UDF ";

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
	 * @return List of all authorized Udf
	 */
	public SearchResult getAllUdf() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_UDF_TRX,
					new UdfRowMapper());

		} catch (Exception e) {
			throw new UdfException("ERROR-- While retriving Udf");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all Udf according to the query passed.
	 */

	public SearchResult getAllUdf(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_UDF_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new UdfRowMapper());
			}

		} catch (Exception e) {
			throw new UdfException("ERROR-- While retriving Udf");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class UdfRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBUdf result = new OBUdf();
			
			result.setModuleId(rs.getLong("MODULEID"));
			result.setModuleName(rs.getString("MODULENAME"));
			result.setFieldName(rs.getString("FIELDNAME"));
			result.setFieldTypeId(rs.getLong("FIELDTYPEID"));
			result.setFieldType(rs.getString("FIELDTYPE"));
			result.setOptions(rs.getString("OPTIONS"));
			result.setSequence(rs.getInt("SEQUENCE"));
			result.setMandatory(rs.getString("MANDATORY"));
			result.setNumericLength(rs.getString("NUMERIC_LENGTH"));
			result.setStatus(rs.getString("STATUS"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	//added by santosh
	/**
	 * @return List of all authorized Udf
	 */
	public SearchResult getAllFilteredUdf(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = "";
		if(null!=searchBy && searchBy.equals("productCode"))
			searchByValue = "PRODUCT_CODE";
		else 
			searchByValue = "PRODUCT_NAME";
		
		String searchTextValue = searchText.toUpperCase();
		
		String GET_UDF_QUERY_STRING = "SELECT id,PRODUCT_CODE,PRODUCT_NAME,STATUS from CMS_UDF where deprecated='N'"
				+ "AND UPPER( " + searchByValue + ") LIKE '" + searchTextValue
				+ "%' ";
		//GET_UDF_QUERY_STRING+=" ORDER BY productCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_UDF_QUERY_STRING,
						new UdfRowMapper());
			}
		} catch (Exception e) {
			throw new UdfException("ERROR-- While retriving Udf");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
}
