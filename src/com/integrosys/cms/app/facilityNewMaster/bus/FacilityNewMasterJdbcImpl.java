package com.integrosys.cms.app.facilityNewMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.bus.PaginationUtil;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class FacilityNewMasterJdbcImpl extends JdbcTemplateAdapter implements
IFacilityNewMasterJdbc {

	private static final String SELECT_FACILITY_NEW_MASTER_TRX = "SELECT id,NEW_FACILITY_CODE,NEW_FACILITY_NAME,NEW_FACILITY_TYPE,NEW_FACILITY_SYSTEM,NEW_FACILITY_CATEGORY,LINE_NUMBER,STATUS,AVAIL_AND_OPTION_APPLICABLE from CMS_FACILITY_NEW_MASTER where deprecated='N' ORDER BY NEW_FACILITY_CODE asc  ";
	

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
	 * @return List of all FacilityNewMaster according to the query passed.
	 */

	public SearchResult getAllFacilityNewMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_FACILITY_NEW_MASTER_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new FacilityNewMasterRowMapper());
			}

		} catch (Exception e) {
			throw new FacilityNewMasterException("ERROR-- While retriving FacilityNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized System Bank Branch
	 */
	public SearchResult getAllFacilityNewMaster() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_FACILITY_NEW_MASTER_TRX,
					new FacilityNewMasterRowMapper());

		} catch (Exception e) {
			throw new FacilityNewMasterException("ERROR-- While retriving FacilityNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	/*
	 * @see com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc#getFilteredActualFacilityNewMaster(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 get query for facility new master based on search criteria
	 *
	 */
	public SearchResult getFilteredActualFacilityNewMaster(String code, String name,
			String category, String type, String system, String line) {
		List resultList = null;
		try {
			if(null==code)
				code="";
			if(null==name)
				name="";
			if(null==category)
				category="";
			if(null==type)
				type="";
			if(null==system)
				system="";
			if(null==line)
				line="";
			if(code.equalsIgnoreCase("null"))
	 			code="";
	 		if(name.equalsIgnoreCase("null"))
	 			name="";
	 		if(category.equalsIgnoreCase("null"))
	 			category="";
	 		if(type.equalsIgnoreCase("null"))
	 			type="";
	 		if(system.equalsIgnoreCase("null"))
	 			system="";
	 		if(line.equalsIgnoreCase("null"))
	 			line="";
        String sql="SELECT id,NEW_FACILITY_CODE,NEW_FACILITY_NAME,NEW_FACILITY_TYPE,NEW_FACILITY_SYSTEM,NEW_FACILITY_CATEGORY,LINE_NUMBER,STATUS,AVAIL_AND_OPTION_APPLICABLE from CMS_FACILITY_NEW_MASTER where deprecated='N' and status = 'ACTIVE' ";
        if(!"".equalsIgnoreCase(code))
        	sql+=" and lower(NEW_FACILITY_CODE) = '"+code.toLowerCase()+"'";
        if(!"".equalsIgnoreCase(name))
        	sql+=" and lower(NEW_FACILITY_NAME) like '%"+name.toLowerCase()+"%'";
        if(!"".equalsIgnoreCase(category))
        	sql+=" and NEW_FACILITY_CATEGORY = '"+category+"'";
        if(!"".equalsIgnoreCase(type))
        	sql+=" and NEW_FACILITY_TYPE = '"+type+"'";
        if(!"".equalsIgnoreCase(system))
        	sql+=" and NEW_FACILITY_SYSTEM = '"+system+"'";
        if(!"".equalsIgnoreCase(line))
        	sql+=" and lower(LINE_NUMBER) like '%"+line.toLowerCase()+"%'";
        sql+=" ORDER BY NEW_FACILITY_CODE asc";
        resultList = getJdbcTemplate().query(sql,
					new FacilityNewMasterRowMapper());

		} catch (Exception e) {
			throw new FacilityNewMasterException("ERROR-- While retriving FacilityNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}

private void finalize(DBUtil dbUtil, ResultSet rs) {
try {
	if (null != rs) {
		rs.close();
	}
	if (dbUtil != null) {
		dbUtil.close();
	}
}
catch (Exception e) {
	e.printStackTrace();
}
}
	
	/**
	 * @return List of all FacilityNewMaster according to the query passed.
	 */
	public List getAllFacilityNewMasterSearch(String login) {
		String SELECT_FACILITY_NEW_MASTER_BY_SEARCH = "SELECT id,NEW_FACILITY_CODE,NEW_FACILITY_NAME,NEW_FACILITY_TYPE,NEW_FACILITY_SYSTEM,LINE_NUMBER,STATUS from CMS_FACILITY_NEW_MASTER where deprecated='N' AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_FACILITY_NEW_MASTER_TRX,
						new FacilityNewMasterRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_FACILITY_NEW_MASTER_BY_SEARCH,
						new FacilityNewMasterRowMapper());
			}

		} catch (Exception e) {
			throw new FacilityNewMasterException("ERROR-- While retriving FacilityNewMaster");
		}
		return resultList;
	}

	public class FacilityNewMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBFacilityNewMaster result = new OBFacilityNewMaster();
			result.setNewFacilityCode(rs.getString("NEW_FACILITY_CODE"));
			result.setNewFacilityName(rs.getString("NEW_FACILITY_NAME"));
			result.setNewFacilityType(rs.getString("NEW_FACILITY_TYPE"));
			result.setNewFacilitySystem(rs.getString("NEW_FACILITY_SYSTEM"));
			result.setStatus(rs.getString("STATUS"));
			result.setLineNumber(rs.getString("LINE_NUMBER"));
			result.setNewFacilityCategory(rs.getString("NEW_FACILITY_CATEGORY"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	/**
	 * @return List of all FacilityNewMaster according to the query passed.
	 */

	public IFacilityNewMaster listFacilityNewMaster(long facilityNewMasterCode)
	throws SearchDAOException,FacilityNewMasterException {
		String SELECT_FACILITY_NEW_MASTER_ID = "SELECT id,NEW_FACILITY_CODE,NEW_FACILITY_NAME,NEW_FACILITY_TYPE,NEW_FACILITY_SYSTEM,LINE_NUMBER,STATUS from description  where id="
			+ facilityNewMasterCode;
		IFacilityNewMaster facilityNewMaster = new OBFacilityNewMaster();
		try {
			facilityNewMaster = (IFacilityNewMaster) getJdbcTemplate().query(
					SELECT_FACILITY_NEW_MASTER_ID,
					new FacilityNewMasterRowMapper());
		} catch (Exception e) {
			throw new FacilityNewMasterException("ERROR-- While retriving FacilityNewMaster");
		}

		return facilityNewMaster;

	}

	@Override
	public IFacilityNewMaster getFacilityMasterByFacCode(String facilityCode) throws FacilityNewMasterException {
		String SELECT_FACILITY_NEW_MASTER = "SELECT id,NEW_FACILITY_CODE,NEW_FACILITY_NAME,NEW_FACILITY_TYPE,NEW_FACILITY_SYSTEM,LINE_NUMBER,STATUS,AVAIL_AND_OPTION_APPLICABLE FROM CMS_FACILITY_NEW_MASTER WHERE deprecated ='N' AND status = 'ACTIVE' and NEW_FACILITY_CODE = ?";
		
		IFacilityNewMaster facilityNewMaster = null;
		
		List facilityNewMasterList = getJdbcTemplate().query(SELECT_FACILITY_NEW_MASTER, new Object[] { facilityCode }, new RowMapper() {
			@Override
			public IFacilityNewMaster mapRow(ResultSet rs, int rowNum) throws SQLException {
				IFacilityNewMaster obFacilityNewMaster = new OBFacilityNewMaster();
				obFacilityNewMaster.setId(rs.getLong("id"));
				String availAndOptionApplicable = rs.getString("AVAIL_AND_OPTION_APPLICABLE");
				if (availAndOptionApplicable != null)
					obFacilityNewMaster.setAvailAndOptionApplicable(availAndOptionApplicable.trim());
				return obFacilityNewMaster;
			}
		});
		
		if(!facilityNewMasterList.isEmpty()){
			facilityNewMaster =  (IFacilityNewMaster) facilityNewMasterList.get(0);
		}

		return facilityNewMaster;
	}

public String getLineExcludeFromLoaMasterByFacilityCode(String facilityCode) {
		if(StringUtils.isNotBlank(facilityCode)) {
			String query = "select LINE_EXCLUDE_FROM_LOA from CMS_FACILITY_NEW_MASTER where NEW_FACILITY_CODE = ?";
			return (String) getJdbcTemplate().queryForObject(query, new Object[] {facilityCode},String.class );
		}
		return null;
	}
	
public List<String> getAllActiveLineNumbers(){
		
		StringBuffer sql = new StringBuffer("select distinct line_number from cms_facility_new_master")
				.append(" where status ='ACTIVE' AND deprecated = 'N' and line_number is not null")
				.append(" order by line_number asc");
		
		List<String> result = null;
		try {
			result = getJdbcTemplate().queryForList(sql.toString(), String.class);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	

	

}
