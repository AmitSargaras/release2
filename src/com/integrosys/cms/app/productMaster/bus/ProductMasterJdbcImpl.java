package com.integrosys.cms.app.productMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;

public class ProductMasterJdbcImpl extends JdbcTemplateAdapter implements
IProductMasterJdbc{

	private static final String SELECT_PRODUCT_MASTER_TRX = "SELECT id,PRODUCT_CODE,PRODUCT_NAME,STATUS from CMS_PRODUCT_MASTER where deprecated='N'";

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
	 * @return List of all authorized ProductMaster
	 */
	public SearchResult getAllProductMaster() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_PRODUCT_MASTER_TRX,
					new ProductMasterRowMapper());

		} catch (Exception e) {
			throw new ProductMasterException("ERROR-- While retriving ProductMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all ProductMaster according to the query passed.
	 */

	public SearchResult getAllProductMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_PRODUCT_MASTER_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new ProductMasterRowMapper());
			}

		} catch (Exception e) {
			throw new ProductMasterException("ERROR-- While retriving ProductMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class ProductMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBProductMaster result = new OBProductMaster();
			
			result.setProductCode(rs.getString("PRODUCT_CODE"));
			result.setProductName(rs.getString("PRODUCT_NAME"));
			result.setStatus(rs.getString("STATUS"));
			
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	//added by santosh
	/**
	 * @return List of all authorized ProductMaster
	 */
	public SearchResult getAllFilteredProductMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = "";
		if(null!=searchBy && searchBy.equals("productCode"))
			searchByValue = "PRODUCT_CODE";
		else 
			searchByValue = "PRODUCT_NAME";
		
		String searchTextValue = searchText.toUpperCase();
		
		String GET_PRODUCT_MASTER_QUERY_STRING = "SELECT id,PRODUCT_CODE,PRODUCT_NAME,STATUS from CMS_PRODUCT_MASTER where deprecated='N'"
				+ "AND UPPER( " + searchByValue + ") LIKE '" + searchTextValue
				+ "%' ";
		//GET_PRODUCT_MASTER_QUERY_STRING+=" ORDER BY productCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_PRODUCT_MASTER_QUERY_STRING,
						new ProductMasterRowMapper());
			}
		} catch (Exception e) {
			throw new ProductMasterException("ERROR-- While retriving ProductMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
}
