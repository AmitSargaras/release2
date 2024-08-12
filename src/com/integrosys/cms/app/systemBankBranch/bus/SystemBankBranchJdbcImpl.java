package com.integrosys.cms.app.systemBankBranch.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class SystemBankBranchJdbcImpl extends JdbcDaoSupport implements
ISystemBankBranchJdbc {

	private static final String SELECT_SYSTEM_BANK_BRANCH_TRX = "SELECT id,system_bank_branch_code,system_bank_branch_name,system_bank_code,city_town,address,state,country,region,contact_number,contact_mail,last_update_date,status from CMS_SYSTEM_BANK_BRANCH where deprecated='N' ";
	
	private static final String SELECT_SYSTEM_BANK_BRANCH_VALUE_FOR_HUB = "SELECT system_bank_branch_code from CMS_SYSTEM_BANK_BRANCH where is_hub='Y'AND STATUS!='INACTIVE' ";
	
	private static final String SELECT_SYSTEM_BANK_BRANCH_ID_FOR_HUB = "SELECT system_bank_branch_name from CMS_SYSTEM_BANK_BRANCH where is_hub='Y' AND STATUS!='INACTIVE' ";




	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;
	private static final String SELECT_SYSTEM_BANK_BRANCH_LIST_TRX = "SELECT id,system_bank_branch_code,system_bank_branch_name,system_bank_code,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from CMS_STAGE_SYSTEM_BANK_BRANCH where deprecated='N' AND ID ";

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
	 * @return List of all System Bank Branch according to the query passed.
	 */

	public SearchResult getAllSystemBankBranch(String searchBy, String searchText) {
		
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_SYSTEM_BANK_BRANCH_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			List resultList = null;
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new SystemBankBranchRowMapper());
			}
			SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
			return searchresult;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
	}

	/**
	 * @return List of all authorized System Bank Branch
	 */
	public SearchResult getAllSystemBankBranch() {
		
		try {
			List resultList = null;
			resultList = getJdbcTemplate().query(SELECT_SYSTEM_BANK_BRANCH_TRX,
					new SystemBankBranchRowMapper());
			SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
			return searchresult;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
		}
	
	
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	public List getAllHUBBranchId() {
		
		try {
			List resultList = null;
			resultList = getJdbcTemplate().queryForList(SELECT_SYSTEM_BANK_BRANCH_ID_FOR_HUB);
			return resultList;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
	}
	
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	public List getAllHUBBranchValue() {
		
		try {
			List resultList = null;
			resultList = getJdbcTemplate().queryForList(SELECT_SYSTEM_BANK_BRANCH_VALUE_FOR_HUB);
			return resultList;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
	}
	/**
	 * @return List of all System Bank Branch according to the query passed.
	 */
	public SearchResult getAllSystemBankBranchSearch(String login) {
		String SELECT_SYSTEM_BANK_BRANCH_BY_SEARCH = "SELECT id,system_bank_branch_code,system_bank_branch_name,system_bank_code,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from CMS_SYSTEM_BANK_BRANCH where deprecated='N' AND STATUS!='INACTIVE' AND system_bank_branch_name  LIKE '"
			+ login + "%' ";

		
		try {
			List resultList = null;
			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_SYSTEM_BANK_BRANCH_TRX,
						new SystemBankBranchRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_SYSTEM_BANK_BRANCH_BY_SEARCH,
						new SystemBankBranchRowMapper());
				
			}
			SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
			return searchresult;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
	}

	public class SystemBankBranchRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBSystemBankBranch result = new OBSystemBankBranch();
			ISystemBank systemBank = getSystemBank(rs
					.getString("system_bank_code"));
			result.setSystemBankBranchCode(rs
					.getString("system_bank_branch_code"));
			result.setSystemBankBranchName(rs
					.getString("system_bank_branch_name"));
			result.setSystemBankCode(systemBank);
			result.setAddress(rs.getString("address"));
//			result.setCityTown(rs.getString("city_town"));
//			result.setCountry(rs.getString("country"));
//			result.setRegion(rs.getString("region"));
//			result.setState(rs.getString("state"));
			result.setContactNumber(rs.getString("contact_number"));
			result.setContactMail(rs.getString("contact_mail"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setStatus(rs.getString("status"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	/**
	 * @return List  System Bank according to the query passed.
	 */
	public ISystemBank getSystemBank(String systemBankCode) {
		
		final String SELECT_SYSTEM_BANK_TRX = "SELECT id,system_bank_code,system_bank_name,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from CMS_SYSTEM_BANK "
			+"where system_bank_code= "+systemBankCode;
		SystemBankSearchQuery query = new SystemBankSearchQuery(
				getDataSource(), SELECT_SYSTEM_BANK_TRX);
		ISystemBank systemBank = new OBSystemBank();
		List searchParamTypeList = new ArrayList();
		List searchParamValueList = new ArrayList();

		searchParamTypeList.add(new SqlParameter(Types.VARCHAR));
		searchParamValueList.add(systemBankCode);

		List resultList = query.execute();
		Iterator itr = resultList.iterator();

		while (itr.hasNext()) {
			OBSystemBank systemBankSearchResult = (OBSystemBank) itr.next();
			systemBank.setSystemBankCode(systemBankSearchResult
					.getSystemBankCode());
			systemBank.setSystemBankName(systemBankSearchResult
					.getSystemBankName());
		}

		return systemBank;
	}

	class SystemBankSearchQuery extends MappingSqlQuery {
		/**
		 * 
		 * @param ds
		 * @param sql
		 */
		public SystemBankSearchQuery(DataSource ds, String sql) {
			super(ds, sql);
		}

		/**
		 * @param rs
		 * @param idx
		 * @return
		 */
		protected Object mapRow(ResultSet rs, int idx) throws SQLException {
			ISystemBank result = new OBSystemBank();

			result.setSystemBankCode(rs.getString("system_bank_code"));
			result.setSystemBankName(rs.getString("system_bank_name"));
			result.setAddress(rs.getString("address"));
			result.getCityTown().setIdCity(Long.parseLong(rs.getString("city_town")));
			result.getCountry().setIdCountry(Long.parseLong(rs.getString("country")));
			result.getRegion().setIdRegion(Long.parseLong(rs.getString("region")));
			result.getState().setIdState(Long.parseLong(rs.getString("state")));
			result.setContactNumber(rs.getLong("contact_number"));
			result.setContactMail(rs.getString("contact_mail"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setId(rs.getLong("id"));

			DefaultLogger.debug(this, "result---" + result.toString()
					+ result.getSystemBankName());
			return result;
		}

		public List execute(List typeList, List valueList) {

			// <let it be exception if TYPE list not exist
			for (int idx = 0; idx < typeList.size(); idx++) {
				// <declaring input parameter(s)
				declareParameter((SqlParameter) typeList.get(idx));
			}

			return super.execute(valueList.toArray());

		}
	}
	/**
	 * @return List of all System Bank Branch according to the query passed.
	 */

	public ISystemBankBranch listSystemBankBranch(long branchCode)
	throws SearchDAOException,SystemBankBranchException {
		String SELECT_SYSTEM_BANK_BRANCH_ID = "SELECT id,system_bank_branch_code, system_bank_branch_name,system_bank_code,system_bank_name,city_town,address,state,country,region,contact_number,contact_mail,last_update_date from cms_system_bank_branch  where id="
			+ branchCode;
		ISystemBankBranch systemBankBranch = new OBSystemBankBranch();
		try {
			systemBankBranch = (ISystemBankBranch) getJdbcTemplate().query(
					SELECT_SYSTEM_BANK_BRANCH_ID,
					new SystemBankBranchRowMapper());
			return systemBankBranch;
		} catch (Exception e) {
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}

		

	}	

}
