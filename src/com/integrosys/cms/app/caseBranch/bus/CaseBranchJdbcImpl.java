package com.integrosys.cms.app.caseBranch.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.bus.PaginationUtil;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class CaseBranchJdbcImpl extends JdbcDaoSupport implements
ICaseBranchJdbc {

	private static final String SELECT_CASEBRANCH_TRX = "SELECT id,  branchCode, coordinator1, coordinator1Email, coordinator2, coordinator2Email, deprecated, status    from CMS_CASEBRANCH where deprecated='N' ORDER BY branchCode asc ";
	private static final String SELECT_RECCASEBRANCH_TRX = "SELECT id, version_time, branchCode, coordinator1, coordinator1Email, coordinator2, coordinator2Email, deprecated, status from CMS_CASEBRANCH where deprecated='N'  ORDER BY branchCode asc ";
	
	private static final String SELECT_INSERT_CASEBRANCH_TRX = "SELECT id, version_time, branchCode, coordinator1, coordinator1Email, coordinator2, coordinator2Email, deprecated, status  from CMS_STAGE_CASEBRANCH where deprecated='N' AND ID ";
	
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
	 * @return List of all CaseBranch according to the query passed.
	 */

	public SearchResult getAllCaseBranch(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_CASEBRANCH_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new CaseBranchRowMapper());
			}

		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(), resultList.size(), resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized CaseBranch
	 */
	public SearchResult getAllCaseBranch() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_CASEBRANCH_TRX,
					new CaseBranchRowMapper());

		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all authorized CaseBranch
	 */
	public SearchResult getAllFilteredCaseBranch(String code,String name){
		List resultList = null;
		String query="SELECT id,  branchCode, coordinator1, coordinator1Email, coordinator2, coordinator2Email," +
				" deprecated, status" +
				"    from CMS_CASEBRANCH where deprecated='N'";
		//  ORDER BY branchCode asc 
		if(!"".equals(code)){
			query+=" and branchCode = '"+code.trim()+"'";
		}
		if(!"".equals(name)){
			query+=" and branchcode in ("+
					"SELECT system_bank_branch_code"+
					" FROM CMS_SYSTEM_BANK_BRANCH"+
					" WHERE lower(system_bank_branch_name) LIKE '%"+name.toLowerCase().trim()+"%')";
		}
		query+=" ORDER BY branchCode asc ";
		try {
         
			resultList = getJdbcTemplate().query(query,
					new CaseBranchRowMapper());

		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
	}
	/**
	 * @return List of all CaseBranch according to the query passed.
	 */
	public List getAllCaseBranchSearch(String login) {
		String SELECT_CASEBRANCH_BY_SEARCH = "SELECT id, version_time, branchCode, coordinator1, coordinator1Email, coordinator2, coordinator2Email, deprecated, status   from CMS_CASEBRANCH where deprecated='N' AND branchCode  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_CASEBRANCH_TRX,
						new CaseBranchRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_CASEBRANCH_BY_SEARCH,
						new CaseBranchRowMapper());
			}

		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		return resultList;
	}

	public class CaseBranchRowMapper implements RowMapper {
	
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCaseBranch result = new OBCaseBranch();
			result.setBranchCode(rs.getString("branchCode"));
			result.setCoordinator1(rs.getString("coordinator1"));
			result.setCoordinator2(rs.getString("coordinator2"));
			result.setCoordinator1Email(rs.getString("coordinator1Email"));
			result.setCoordinator2Email(rs.getString("coordinator2Email"));
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			return result;
		}
	}
	/**
	 * @return List of all CaseBranch according to the query passed.
	 */

	public ICaseBranch listCaseBranch(long caseBranchCode)
	throws SearchDAOException,CaseBranchException {
		String SELECT_CASEBRANCH_ID = "SELECT id,description,startDate,endDate,last_update_date from description  where id="
			+ caseBranchCode;
		ICaseBranch caseBranch = new OBCaseBranch();
		try {
			caseBranch = (ICaseBranch) getJdbcTemplate().query(
					SELECT_CASEBRANCH_ID,
					new CaseBranchRowMapper());
		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}

		return caseBranch;

	}
	
	public List getCaseBranchListForYear(long year) {
		List resultList = null;
		OBCaseBranch caseBranch;
		try {
			resultList = getJdbcTemplate().query(SELECT_CASEBRANCH_TRX, new CaseBranchRowMapper());
		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				caseBranch = (OBCaseBranch)iter.next();
				//startDate.setTime(caseBranch.getStartDate());
				//endDate.setTime(caseBranch.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}

	public List getRecurrentCaseBranchListForYear(long year) {
		List resultList = null;
		OBCaseBranch caseBranch;
		try {
			resultList = getJdbcTemplate().query(SELECT_RECCASEBRANCH_TRX,	new CaseBranchRowMapper());
		} catch (Exception e) {
			throw new CaseBranchException("ERROR-- While retriving CaseBranch");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				caseBranch = (OBCaseBranch)iter.next();
				//startDate.setTime(caseBranch.getStartDate());
				//endDate.setTime(caseBranch.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}
	
}
