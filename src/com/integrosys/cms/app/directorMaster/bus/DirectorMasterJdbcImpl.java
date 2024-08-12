package com.integrosys.cms.app.directorMaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */

public class DirectorMasterJdbcImpl extends JdbcDaoSupport implements
IDirectorMasterJdbc {

	private static final String SELECT_DIRECTOR_MASTER_TRX = "SELECT id,version_time,create_by,creation_date,last_update_by,last_update_date,deprecated,status,din_no,name from CMS_DIRECTOR_MASTER";
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
	 * @return List of all Director Master according to the query passed.
	 */

	public List getAllDirectorMaster(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();


String	GET_BRANCH_QUERY_STRING = SELECT_DIRECTOR_MASTER_TRX
		+ " WHERE ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
				
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new DirectorMasterRowMapper());
			}

		} catch (Exception e) {
			throw new DirectorMasterException("ERROR-- While retriving Director Master");
		}
		return resultList;
	}

	/**
	 * @return List of all authorized Director Master
	 */
	public List getAllDirectorMaster() {
		List resultList = null;
//		System.out.println("In JDBC DirectorMaster getAllDirectorMaster ");
			try {
			
			resultList = getJdbcTemplate().query(SELECT_DIRECTOR_MASTER_TRX,
					new DirectorMasterRowMapper());

		} catch (Exception e) {
			throw new DirectorMasterException("ERROR-- While retriving Director Master");
		}
		return resultList;
		
	}
	/**
	 * @return List of all Director Master according to the query passed.
	 */
	public List getAllDirectorMasterSearch(String login) {
		String SELECT_DIRECTOR_MASTER_BY_SEARCH = "SELECT id,version_time,create_by,creation_date,last_update_by,last_update_date,deprecated,status,din_no,name,director_code,action,director_id,director_ref from CMS_DIRECTOR_MASTER where  name  LIKE '"
			+ login + "%' ";
		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_DIRECTOR_MASTER_TRX,
						new DirectorMasterRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_DIRECTOR_MASTER_BY_SEARCH,
						new DirectorMasterRowMapper());
			}

		} catch (Exception e) {
			throw new DirectorMasterException("ERROR-- While retriving Director Master");
		}
		return resultList;
	}

	public class DirectorMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBDirectorMaster result = new OBDirectorMaster();
			result.setId(rs.getLong("id"));
			result.setVersionTime((rs.getLong("version_time")));
			result.setCreateBy(rs.getString("create_by"));
			result.setCreationDate(rs.getDate("creation_date"));
			result.setLastUpdateBy(rs.getString("last_update_by"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setDeprecated(rs.getString("deprecated"));
			result.setStatus(rs.getString("status"));
			result.setDinNo((rs.getString("din_no")));
			result.setName(rs.getString("name"));
			result.setDirectorCode(rs.getString("director_code"));
			//result.setRelationship(rs.getString("relationship"));
			
			return result;
		}
	}
	/**
	 * @return List  Director Master according to the query passed.
	 */
	public IDirectorMaster getdirectorMaster(String din_no) {
//		System.out.println("IN search for director master");
		return null;
	}

	class DirectorMasterSearchQuery extends MappingSqlQuery {
		/**
		 * 
		 * @param ds
		 * @param sql
		 */
		public DirectorMasterSearchQuery(DataSource ds, String sql) {
			super(ds, sql);
		}

		/**
		 * @param rs
		 * @param idx
		 * @return
		 */
		protected Object mapRow(ResultSet rs, int idx) throws SQLException {
			IDirectorMaster result = new OBDirectorMaster();
			result.setId(rs.getLong("id"));
			result.setVersionTime((rs.getLong("version_time")));
			result.setCreateBy(rs.getString("create_by"));
			result.setCreationDate(rs.getDate("creation_date"));
			result.setLastUpdateBy(rs.getString("last_update_by"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setDeprecated(rs.getString("deprecated"));
			result.setStatus(rs.getString("status"));
			result.setDinNo((rs.getString("din_no")));
			result.setName(rs.getString("name"));
			result.setDirectorCode(rs.getString("directorCode"));
			result.setAction(rs.getString("action"));
				
			return result;
		}

		public List execute(List typeList, List valueList) {

		
			for (int idx = 0; idx < typeList.size(); idx++) {
			
				declareParameter((SqlParameter) typeList.get(idx));
			}

			return super.execute(valueList.toArray());

		}
	}
	/**
	 * @return List of all director Master according to the query passed.
	 */

	

	
	public IDirectorMaster listDirectorMaster(long branchCode)
			throws DirectorMasterException {
		// TODO Auto-generated method stub
		return null;
	}


}
