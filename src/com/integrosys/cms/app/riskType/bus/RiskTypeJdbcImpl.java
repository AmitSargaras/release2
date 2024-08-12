package com.integrosys.cms.app.riskType.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.ui.common.UIUtil;

public class RiskTypeJdbcImpl extends JdbcTemplateAdapter implements
IRiskTypeJdbc{

	private static final String SELECT_RISK_TYPE_TRX = "SELECT id,RISK_TYPE_NAME,RISK_TYPE_CODE,STATUS,DEPRECATED from CMS_RISK_TYPE WHERE STATUS = 'ACTIVE' ";

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
	 * @return List of all authorized RiskType
	 */
	public SearchResult getAllRiskType() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_RISK_TYPE_TRX,
					new RiskTypeRowMapper());

		} catch (Exception e) {
			throw new RiskTypeException("ERROR-- While retriving RiskType");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all RiskType according to the query passed.
	 */

	public SearchResult getAllRiskType(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_RISK_TYPE_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new RiskTypeRowMapper());
			}

		} catch (Exception e) {
			throw new RiskTypeException("ERROR-- While retriving RiskType");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class RiskTypeRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBRiskType result = new OBRiskType();
			
			result.setRiskTypeName(rs.getString("RISK_TYPE_NAME"));
			result.setRiskTypeCode(rs.getString("RISK_TYPE_CODE"));
		
			result.setStatus(rs.getString("STATUS"));
			result.setId(rs.getLong("id"));
			result.setDeprecated(rs.getString("DEPRECATED"));
			
			
			return result;
		}
	}
	
	//added by santosh
	/**
	 * @return List of all authorized RiskType
	 */
	public SearchResult getAllFilteredRiskType(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = "";
		if(null!=searchBy && searchBy.equals("productCode"))
			searchByValue = "PRODUCT_CODE";
		else 
			searchByValue = "PRODUCT_NAME";
		
		String searchTextValue = searchText.toUpperCase();
		
		String GET__QUERY_STRING = "SELECT id,CRITERIA,VALUATION_AMOUNT,RAM_RATING,EXCLUDE_PARTY_ID,STATUS from CMS_VALUATION_AMT_RATING "
				+ "AND UPPER( " + searchByValue + ") LIKE '" + searchTextValue
				+ "%' ";
		//GET__QUERY_STRING+=" ORDER BY productCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET__QUERY_STRING,
						new RiskTypeRowMapper());
			}
		} catch (Exception e) {
			throw new RiskTypeException("ERROR-- While retriving RiskType");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/*	public List<OBRiskType> getValuationByRamRatingOfCAM(long collateralId) {
			StringBuffer sql = new StringBuffer("select * from cms_risk_type  where status='ACTIVE' and deprecated = 'N' and ") 
					.append("ram_rating in ( SELECT prf.CMS_APPR_OFFICER_GRADE FROM CMS_LIMIT_SECURITY_MAP sec_map ")
					.append("inner join SCI_LSP_LMT_PROFILE prf on prf.CMS_LSP_LMT_PROFILE_ID = sec_map.CMS_LSP_LMT_PROFILE_ID ") 
					.append("where sec_map.cms_collateral_id = ?)");
			
			return getJdbcTemplate().query(sql.toString(), new Object[] {collateralId}, new RowMapper() {
				
				public OBRiskType mapRow(ResultSet rs, int idx) throws SQLException {
					OBRiskType ob =  new OBRiskType();
					ob.setCriteria(rs.getString("criteria"));
					ob.setExcludePartyId(rs.getString("exclude_party_id"));
					ob.setValuationAmount(rs.getString("valuation_amount"));
					return ob;
				}
			} );
		}*/
}
