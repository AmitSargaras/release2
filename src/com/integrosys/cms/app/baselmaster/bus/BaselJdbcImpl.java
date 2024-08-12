package com.integrosys.cms.app.baselmaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.bus.ComponentJdbcImpl.ComponentRowMapper;

public class BaselJdbcImpl extends JdbcDaoSupport implements IBaselJdbc{
	
	private static final String SELECT_BASEL_TRX = "SELECT id,status,system,system_value,basel_validation,exposure_source,REPORTED_HANDOFF from CMS_BASEL_MASTER where deprecated='N'";
	
	private static final String SELECT_BASEL_COUNT = "SELECT count(1) from CMS_BASEL_MASTER where deprecated='N' AND system =";
	
	private static final String SELECT_BASEL_ENTRY="SELECT entry_Code FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE='BASEL_MASTER' AND ACTIVE_STATUS='1'";
	

	public SearchResult getAllActualBasel() throws BaselMasterException {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_BASEL_TRX,
					new BaselRowMapper());

		} catch (Exception e) {
			throw new BaselMasterException("ERROR-- While retriving Actual Basel in JDBC");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllActualCommon() throws BaselMasterException {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_BASEL_ENTRY,new CommonRowMapper());

		} catch (Exception e) {
			throw new BaselMasterException("ERROR-- While retriving Actual Common Code in JDBC");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		
	}
	
	public class BaselRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBBaselMaster result = new OBBaselMaster();
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			result.setSystem(rs.getString("system"));
			result.setSystemValue(rs.getString("system_value"));
			result.setBaselValidation(rs.getString("basel_validation"));
			result.setExposureSource(rs.getString("exposure_source"));
			result.setReportHandOff(rs.getString("REPORTED_HANDOFF"));
			
			return result;
		}
	}
	
	public class CommonRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCommonCodeEntry result = new OBCommonCodeEntry();
			//result.setId(rs.getLong("id"));
			result.setEntryCode(rs.getString("entry_Code"));
			/*result.setSystem(rs.getString("system"));
			result.setSystemValue(rs.getString("system_value"));
			result.setBaselValidation(rs.getString("basel_validation"));
			result.setExposureSource(rs.getString("exposure_source"));
			result.setReportHandOff(rs.getString("REPORTED_HANDOFF"));*/
			
			return result;
		}
	}
	
	
}
