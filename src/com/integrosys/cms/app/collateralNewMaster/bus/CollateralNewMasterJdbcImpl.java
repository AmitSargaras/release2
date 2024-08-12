package com.integrosys.cms.app.collateralNewMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.bus.PaginationUtil;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class CollateralNewMasterJdbcImpl extends JdbcTemplateAdapter implements
ICollateralNewMasterJdbc {

	private static final String SELECT_COLLATERAL_NEW_MASTER_TRX = "SELECT id,NEW_COLLATERAL_CODE,NEW_COLLATERAL_DESCRIPTION,NEW_COLLATERAL_MAIN_TYPE,NEW_COLLATERAL_SUB_TYPE,INSURANCE,STATUS,REVALUATION_FREQUENCY_COUNT,REVALUATION_FREQUENCY_DAYS from CMS_COLLATERAL_NEW_MASTER WHERE DEPRECATED='N' ORDER BY NEW_COLLATERAL_CODE asc  ";
	private static final String SELECT_COLLATERAL_NEW_MASTER_BY_SECSUBTYPE = "SELECT id,NEW_COLLATERAL_CODE,NEW_COLLATERAL_DESCRIPTION,NEW_COLLATERAL_MAIN_TYPE,NEW_COLLATERAL_SUB_TYPE,INSURANCE,STATUS,REVALUATION_FREQUENCY_COUNT,REVALUATION_FREQUENCY_DAYS from CMS_COLLATERAL_NEW_MASTER WHERE NEW_COLLATERAL_SUB_TYPE=? ";
	

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
	 * @return List of all CollateralNewMaster according to the query passed.
	 */

	public SearchResult getAllCollateralNewMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_COLLATERAL_NEW_MASTER_TRX
		+ "AND (Upper( " + searchByValue + ") LIKE '%" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new CollateralNewMasterRowMapper());
			}

		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(), resultList.size(), resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized System Bank Branch
	 */
	public SearchResult getAllCollateralNewMaster() {
		List resultList = null;
		try {
			
//			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			resultList =   getJdbcTemplate().query(SELECT_COLLATERAL_NEW_MASTER_TRX,
					new CollateralNewMasterRowMapper());

		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/*
	 * getFilteredCollateral(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * generates query for collateral master based on input search criteria .
	 */
	public SearchResult getFilteredCollateral(String collateralCode,String collateralDescription,String collateralMainType,String collateralSubType) {
		List resultList = null;
		String order=" ORDER BY NEW_COLLATERAL_CODE asc ";
		String query="SELECT id,NEW_COLLATERAL_CODE,NEW_COLLATERAL_DESCRIPTION,NEW_COLLATERAL_MAIN_TYPE,NEW_COLLATERAL_SUB_TYPE,INSURANCE,STATUS,REVALUATION_FREQUENCY_COUNT,REVALUATION_FREQUENCY_DAYS from CMS_COLLATERAL_NEW_MASTER WHERE DEPRECATED='N' ";
		try {
			if("".equals(collateralCode))
				collateralCode=null;
			if("".equals(collateralDescription))
				collateralDescription=null;
			if("".equals(collateralMainType))
				collateralMainType=null;
			if("".equals(collateralSubType))
				collateralSubType=null;
			
//			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			if(null!=collateralCode)
				query=query+" and lower(NEW_COLLATERAL_CODE) = '"+collateralCode.toLowerCase()+"'";
			if(null!=collateralDescription)
				query=query+" and lower(NEW_COLLATERAL_DESCRIPTION) like '%"+collateralDescription.toLowerCase()+"%'";
			if(null!=collateralMainType)
				query=query+" and NEW_COLLATERAL_MAIN_TYPE = '"+collateralMainType+"'";
			if(null!=collateralSubType)
				query=query+" and NEW_COLLATERAL_SUB_TYPE = '"+collateralSubType+"'";
			
			query=query+order;
			resultList =   getJdbcTemplate().query(query,
					new CollateralNewMasterRowMapper());

		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	public SearchResult getAllCollateralNewMasterBySecSubType(String secSubType) {
		List resultList = null;
		try {
			
//			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			resultList =   getJdbcTemplate().query(SELECT_COLLATERAL_NEW_MASTER_BY_SECSUBTYPE,new Object[] { secSubType},	new CollateralNewMasterRowMapper());

		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	
	/**
	 * @return List of all CollateralNewMaster according to the query passed.
	 */
	public List getAllCollateralNewMasterSearch(String login) {
		String SELECT_COLLATERAL_NEW_MASTER_BY_SEARCH = "SELECT id,NEW_COLLATERAL_CODE,NEW_COLLATERAL_DESCRIPTION,NEW_COLLATERAL_MAIN_TYPE,NEW_COLLATERAL_SUB_TYPE,INSURANCE,STATUS,REVALUATION_FREQUENCY_COUNT,REVALUATION_FREQUENCY_DAYS from CMS_COLLATERAL_NEW_MASTER  AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_COLLATERAL_NEW_MASTER_TRX,
						new CollateralNewMasterRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_COLLATERAL_NEW_MASTER_BY_SEARCH,
						new CollateralNewMasterRowMapper());
			}

		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}
		return resultList;
	}

	public class CollateralNewMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCollateralNewMaster result = new OBCollateralNewMaster();
			
			result.setNewCollateralCode(rs.getString("NEW_COLLATERAL_CODE"));
			result.setNewCollateralDescription(rs.getString("NEW_COLLATERAL_DESCRIPTION"));
			result.setNewCollateralMainType(rs.getString("NEW_COLLATERAL_MAIN_TYPE"));
			result.setNewCollateralSubType(rs.getString("NEW_COLLATERAL_SUB_TYPE"));
			result.setStatus(rs.getString("STATUS"));
			result.setInsurance(rs.getString("INSURANCE"));
			result.setRevaluationFrequencyCount(rs.getInt("REVALUATION_FREQUENCY_COUNT"));
			result.setRevaluationFrequencyDays(rs.getString("REVALUATION_FREQUENCY_DAYS"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}
	/**
	 * @return List of all CollateralNewMaster according to the query passed.
	 */

	public ICollateralNewMaster listCollateralNewMaster(long collateralNewMasterCode)
	throws SearchDAOException,CollateralNewMasterException {
		String SELECT_COLLATERAL_NEW_MASTER_ID = "SELECT id,NEW_COLLATERAL_CODE,NEW_COLLATERAL_DESCRIPTION,NEW_COLLATERAL_MAIN_TYPE,NEW_COLLATERAL_SUB_TYPE,INSURANCE,STATUS,REVALUATION_FREQUENCY_COUNT,REVALUATION_FREQUENCY_DAYS from description  where id="
			+ collateralNewMasterCode;
		ICollateralNewMaster collateralNewMaster = new OBCollateralNewMaster();
		try {
			collateralNewMaster = (ICollateralNewMaster) getJdbcTemplate().query(
					SELECT_COLLATERAL_NEW_MASTER_ID,
					new CollateralNewMasterRowMapper());
		} catch (Exception e) {
			throw new CollateralNewMasterException("ERROR-- While retriving CollateralNewMaster");
		}

		return collateralNewMaster;

	}


	

	

}
