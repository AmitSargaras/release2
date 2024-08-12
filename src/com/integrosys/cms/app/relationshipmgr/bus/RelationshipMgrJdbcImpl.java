package com.integrosys.cms.app.relationshipmgr.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class RelationshipMgrJdbcImpl extends JdbcDaoSupport implements
IRelationshipMgrJdbc {

	private static final String SELECT_RELATIONSHIP_MGR_TRX = "SELECT id,rm_mgr_code,rm_mgr_name,rm_mgr_mail,reporting_head,reporting_head_mail,region,last_update_date,last_update_by from CMS_RELATIONSHIP_MGR where deprecated='N' ";
	
	private static final String SELECT_INSERT_RELATIONSHIP_MGR_TRX = "SELECT id,rm_mgr_code,rm_mgr_name,rm_mgr_mail,reporting_head,reporting_head_mail,region,last_update_date,last_update_by from CMS_STAGE_RELATIONSHIP_MGR where deprecated='N' AND ID ";
	
	private static final String SELECT_REGION_TRX = "SELECT id,region_name from CMS_REGION where deprecated='N' AND id=";
	
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
	 * @return List of all RelationshipMgr according to the query passed.
	 */

	public SearchResult getAllRelationshipMgr(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_RELATIONSHIP_MGR_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new RelationshipMgrRowMapper());
			}

		} catch (Exception e) {
			throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized RelationshipMgr
	 */
		public SearchResult getAllRelationshipMgr() {
			List resultList = null;
			try {
	
				resultList = getJdbcTemplate().query(SELECT_RELATIONSHIP_MGR_TRX,
						new RelationshipMgrRowMapper());
	
			} catch (Exception e) {
				throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
			}
			SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
			return searchresult;
		}
	
		
		/**
		 * @return List of all authorized RelationshipMgr
		 */
			public IRegion getRegion(String regionId) {
				IRegion region = new OBRegion();
				try {
					String select_region = SELECT_REGION_TRX + regionId;
					List resultList = getJdbcTemplate().query(select_region,
							new RegionRowMapper());
					Iterator itr = resultList.iterator();
					while (itr.hasNext()) {
						OBRegion regionResult = (OBRegion) itr.next();
						region.setIdRegion(regionResult
								.getIdRegion());
						region.setRegionName(regionResult
								.getRegionName());
					}
		
				} catch (Exception e) {
					throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
				}
				return region;
			}	
	
	/**
	 * @return List of all RelationshipMgr according to the query passed.
	 */
	public List getAllRelationshipMgrSearch(String login) {
		String SELECT_RELATIONSHIP_MGR_BY_SEARCH = "SELECT id,description,startDate,endDate,last_update_date from CMS_RELATIONSHIP_MGR where deprecated='N' AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_RELATIONSHIP_MGR_TRX,
						new RelationshipMgrRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_RELATIONSHIP_MGR_BY_SEARCH,
						new RelationshipMgrRowMapper());
			}

		} catch (Exception e) {
			throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
		}
		return resultList;
	}

	public class RelationshipMgrRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBRelationshipMgr result = new OBRelationshipMgr();
			result.setRelationshipMgrCode(rs.getString("rm_mgr_code"));
			result.setRelationshipMgrName(rs.getString("rm_mgr_name"));
			result.setRelationshipMgrMailId(rs.getString("rm_mgr_mail"));
			result.setReportingHeadName(rs.getString("reporting_head"));
			result.setReportingHeadMailId(rs.getString("reporting_head_mail"));
			IRegion region = getRegion(rs.getString("region"));
			result.setRegion(region);
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setLastUpdateBy(rs.getString("last_update_by"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	
	
	public class RegionRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBRegion result = new OBRegion();
			result.setRegionName(rs.getString("region_name"));
			result.setIdRegion(rs.getLong("id"));
			return result;
		}
	}
	/**
	 * @return List of all RelationshipMgr according to the query passed.
	 */

	public IRelationshipMgr listRelationshipMgr(long holidayCode)
	throws SearchDAOException,RelationshipMgrException {
		String SELECT_RELATIONSHIP_MGR_ID = "SELECT id,description,startDate,endDate,last_update_date from description  where id="
			+ holidayCode;
		IRelationshipMgr holiday = new OBRelationshipMgr();
		try {
			holiday = (IRelationshipMgr) getJdbcTemplate().query(
					SELECT_RELATIONSHIP_MGR_ID,
					new RelationshipMgrRowMapper());
		} catch (Exception e) {
			throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
		}

		return holiday;

	}
//--------------------
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "SELECT TRANSACTION_TYPE FROM TRANSACTION WHERE TRANSACTION_TYPE='INSERT_RELATION_MGR'";
		List rowList = getJdbcTemplate().queryForList(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "SELECT TRANSACTION_TYPE FROM TRANSACTION WHERE TRANSACTION_TYPE='INSERT_RELATION_MGR' AND STATUS != 'ACTIVE'";
			List holidayList = getJdbcTemplate().queryForList(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return Pagination for uploaded files in RelationshipMgr.
	 */
	public List getAllStageRelationshipMgr(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		String SearchQuery ="SELECT SYS_ID from CMS_FILE_MAPPER where TRANS_ID='"+searchBy +"'";
		List listId = getJdbcTemplate().queryForList(SearchQuery);

		for (int i = 0; i < listId.size(); i++) {
			HashMap map = (HashMap) listId.get(i);
//				System.out.println("val = " + map.get("SYS_ID"));
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.get("SYS_ID").toString();
		}
		
		if(!strId.equals("")){
			String GET_BRANCH_QUERY_STRING = SELECT_INSERT_RELATIONSHIP_MGR_TRX
			+ "in ( " + strId +  " )";
			try {
				if (searchByValue.trim() != "") {
					resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
							new RelationshipMgrRowMapper());
				}
	
			} catch (Exception e) {
				throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
			}
		}
		return resultList;
	}
	
	/**
	 * @return list of files uploaded in staging table of RelationshipMgr.
	 */
	public List getFileMasterList(String searchBy) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		String SearchQuery ="SELECT SYS_ID from CMS_FILE_MAPPER where TRANS_ID='"+searchBy +"' ";
		List listId = getJdbcTemplate().queryForList(SearchQuery);
       return listId;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> fetchRMData(String rmEmpCode) {

		String sql = "select RM_MGR_NAME,(select reg.REGION_NAME from cms_region reg where reg.id= REGION) as region "
					+ " from CMS_RELATIONSHIP_MGR where EMPLOYEE_CODE = ? and STATUS = 'ACTIVE' and rownum=1";

		return (Map<String, String>) getJdbcTemplate().query(sql, new Object[] {rmEmpCode}, new ResultSetExtractor() {
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> map = new HashMap<String, String>();
				while(rs.next()) {
					map.put("relationshipMgr", rs.getString("RM_MGR_NAME"));
					map.put("rmRegion", rs.getString("REGION"));
				}
				return map;
			}
		});
				
	}
	
	public boolean isValidRMEmpCode(String rmEmpCode) {

		return (int) getJdbcTemplate().queryForObject(
				"select count(1) as count from CMS_RELATIONSHIP_MGR where EMPLOYEE_CODE = ? AND STATUS = 'ACTIVE'",
				new Object[] { rmEmpCode }, Integer.class) > 0 ? true : false;

	}
	
}
