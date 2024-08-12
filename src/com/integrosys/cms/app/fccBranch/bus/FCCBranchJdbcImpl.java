package com.integrosys.cms.app.fccBranch.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class FCCBranchJdbcImpl extends JdbcDaoSupport implements
IFCCBranchJdbc {

	private static final String SELECT_FCCBRANCH_TRX = "SELECT id,branchCode,aliasBranchCode, branchName,deprecated,status from CMS_FCCBRANCH_MASTER where deprecated='N' ORDER BY branchCode asc ";
	private static final String SELECT_RECFCCBRANCH_TRX = "SELECT id, version_time, branchCode,aliasBranchCode, branchName,deprecated, status from CMS_FCCBRANCH_MASTER where deprecated='N'  ORDER BY branchCode asc ";
	
	private static final String SELECT_INSERT_FCCBRANCH_TRX = "SELECT id, version_time, branchCode,aliasBranchCode, branchName, deprecated, status  from CMS_STAGE_FCCBRANCH_MASTER where deprecated='N' AND ID ";
	
	
	

	public SearchResult getAllFCCBranch(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = "SELECT id,branchCode,aliasBranchCode, branchName,deprecated,status from CMS_FCCBRANCH_MASTER where deprecated='N'"
		+ "AND UPPER(" + searchByValue + ") LIKE '" + searchTextValue
		+ "%' ";
		
		GET_BRANCH_QUERY_STRING+=" ORDER BY branchCode asc ";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new FCCBranchRowMapper());
			}

		} catch (Exception e) {
			throw new FCCBranchException("ERROR-- While retriving FCCBranch");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(), resultList.size(), resultList);
		return searchresult;
	}

	
	public SearchResult getAllFCCBranch() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_FCCBRANCH_TRX,
					new FCCBranchRowMapper());

		} catch (Exception e) {
			throw new FCCBranchException("ERROR-- While retriving FCCBranch");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
		}
	
	public IFCCBranch findBranchByBranchCode(String branchCode) throws Exception {
		String sql = "select id, branchcode, branchname, aliasbranchcode, status "
				+ "from CMS_FCCBRANCH_MASTER where status ='ACTIVE' and DEPRECATED = 'N' and branchcode = ?";
		return (IFCCBranch) getJdbcTemplate().queryForObject(sql, new Object[] {branchCode}, new FCCBranchRowMapper());
	}
	
	

	public class FCCBranchRowMapper implements RowMapper {
	
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBFCCBranch result = new OBFCCBranch();
			result.setBranchCode(rs.getString("branchCode"));
			result.setBranchName(rs.getString("branchName"));
			result.setAliasBranchCode(rs.getString("aliasBranchCode"));
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			return result;
		}
	}
	
	


	
	
}
