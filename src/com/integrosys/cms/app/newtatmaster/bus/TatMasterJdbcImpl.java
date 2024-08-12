package com.integrosys.cms.app.newtatmaster.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.component.bus.ComponentException;



public class TatMasterJdbcImpl extends JdbcDaoSupport implements ITatMasterJdbc{
	
	private static final String SELECT_TAT_EVENT = "SELECT id,status,Events,Start_Timing,End_Timing,Timing_hours,Timing_Min,EVENT_CODE from CMS_TAT_MASTER where deprecated='N'";
	
	
	
	public SearchResult getAllTatEvents() throws TatMasterException {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_TAT_EVENT,
					new TatMasterRowMapper());

		} catch (Exception e) {
			throw new TatMasterException("ERROR-- While retriving TAT Events");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public class TatMasterRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBNewTatMaster result = new OBNewTatMaster();
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			result.setTimingHours(rs.getString("Timing_hours"));
			result.setTimingMin(rs.getString("Timing_Min"));
			result.setStartTime(rs.getString("Start_Timing"));
			result.setEndTime(rs.getString("End_Timing"));
			result.setUserEvent(rs.getString("Events"));
			result.setEventCode(rs.getString("EVENT_CODE"));
			
			
			return result;
		}

}
	public INewTatMaster getTatEvent(String event) throws TatMasterException {
		List resultList = null;
		try {
			String sql=SELECT_TAT_EVENT;
			
			sql=sql+" and EVENT_CODE='"+event+"' ";
			resultList = getJdbcTemplate().query(sql,new TatMasterRowMapper());

		} catch (Exception e) {
			throw new TatMasterException("ERROR-- While retriving TAT Events");
		}
		if(resultList!=null && resultList.size()>0){
			return (INewTatMaster)resultList.get(0);
		}else{
			return null;	
		}
		
	}
	
	public HashMap getTatTimings(String event) throws TatMasterException {
		List resultList = null;
		HashMap resultMap = new HashMap();
		try {
			String sql=SELECT_TAT_EVENT;
			
			sql=sql+" and EVENT_CODE='"+event+"' ";
			resultList = getJdbcTemplate().query(sql,new TatMasterRowMapper());

		} catch (Exception e) {
			throw new TatMasterException("ERROR-- While retriving TAT Events");
		}
		if(resultList!=null && resultList.size()>0){
			INewTatMaster tatMaster= (INewTatMaster)resultList.get(0);
			String startTime=tatMaster.getStartTime();
			String endTime=tatMaster.getEndTime();
			resultMap.put("in", startTime);
			resultMap.put("out", endTime);
		}
		
		return resultMap;
	}
}