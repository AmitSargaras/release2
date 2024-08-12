package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.productMaster.bus.*;

public class NpaTraqCodeMasterJdbcImpl extends JdbcTemplateAdapter implements
INpaTraqCodeMasterJdbc{

	private static final String SELECT_NPA_TRAQ_CODE_MASTER_TRX = "SELECT ID,NPA_TRAQ_CODE,SECURITY_TYPE,SECURITY_SUB_TYPE,PROPERTY_TYPE_CODE_DESC FROM CMS_NPA_TRAQ_CODE_MASTER WHERE STATUS='ACTIVE'";

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
	 * @return List of all authorized NpaTraqCodeMaster
	 */
	public SearchResult getAllNpaTraqCodeMaster() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_NPA_TRAQ_CODE_MASTER_TRX,
					new NpaTraqCodeMasterRowMapper());

		} catch (Exception e) {
			throw new NpaTraqCodeMasterException("ERROR-- While retriving NpaTraqCodeMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all NpaTraqCodeMaster according to the query passed.
	 */

	public SearchResult getAllNpaTraqCodeMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_QUERY_STRING = SELECT_NPA_TRAQ_CODE_MASTER_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_QUERY_STRING,
						new NpaTraqCodeMasterRowMapper());
			}

		} catch (Exception e) {
			throw new NpaTraqCodeMasterException("ERROR-- While retriving NpaTraqCodeMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}
	
	public class NpaTraqCodeMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBNpaTraqCodeMaster result = new OBNpaTraqCodeMaster();
			
			result.setId(rs.getLong("ID"));
			result.setNpaTraqCode(rs.getString("NPA_TRAQ_CODE"));
			result.setSecurityType(rs.getString("SECURITY_TYPE"));
			result.setSecuritySubType(rs.getString("SECURITY_SUB_TYPE"));
			result.setPropertyTypeCodeDesc(rs.getString("PROPERTY_TYPE_CODE_DESC"));
			
			return result;
		}
	}
	
	public boolean isNpaTraqCodeUniqueJdbc(String securityType, String securitySubType, String propertyTypeDesc) {

		String query = null;

		if ("PT".equals(securityType)) {
			query = "SELECT count(*) FROM CMS_NPA_TRAQ_CODE_MASTER WHERE STATUS='ACTIVE' AND PROPERTY_TYPE_CODE_DESC LIKE '"
					+ propertyTypeDesc + "' ";
		} else {
			query = "SELECT count(*) FROM CMS_NPA_TRAQ_CODE_MASTER WHERE STATUS='ACTIVE' AND SECURITY_TYPE LIKE '"
					+ securityType + "' AND SECURITY_SUB_TYPE LIKE '" + securitySubType + "' ";
		}

		int count = 0;
		DBUtil dbUtil = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
			if (null != rs) {
				while (rs.next()) {
					count = rs.getInt(1);
					if (count > 0) {
						flag = true;
					}
				}
			}
			finalize(dbUtil, rs);
		} catch (Exception e) {
			DefaultLogger.debug(this, " exception in getReleaselinedetailsLiabBranchCount: " + e.getMessage());
			e.printStackTrace();
		}
		return flag;

	}

	private static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
}
