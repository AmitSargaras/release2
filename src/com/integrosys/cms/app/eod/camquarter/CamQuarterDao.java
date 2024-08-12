package com.integrosys.cms.app.eod.camquarter;

/**
 * @author uma.khot
 */

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class CamQuarterDao implements ICamQuarterDao{
	
	private String sp_cam_detail_report;
	private String sp_cam_quarter_activity;
	private JdbcTemplate jdbcTemplate;
	
	public String getSp_cam_detail_report() {
		return sp_cam_detail_report;
	}
	public void setSp_cam_detail_report(String spCamDetailReport) {
		sp_cam_detail_report = spCamDetailReport;
	}
	public String getSp_cam_quarter_activity() {
		return sp_cam_quarter_activity;
	}
	public void setSp_cam_quarter_activity(String spCamQuarterActivity) {
		sp_cam_quarter_activity = spCamQuarterActivity;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public String generateCamDetailExtract() throws Exception{
		String flag="fail";
		try{
			
			getJdbcTemplate().execute("{call "+getSp_cam_detail_report()+"()}", new CallableStatementCallback() {
				
				@Override
				public Object doInCallableStatement(CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.executeUpdate();
					return null;
				}
			});
			 flag="success";
		}catch(Exception e){
			
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new Exception("Exception in generateCamDetailExtract.");
		}
		return flag;
	}
	@Override
	public String generateCamQuarterExtract() throws Exception {

		String flag="fail";
		try{
			
			getJdbcTemplate().execute("{call "+getSp_cam_quarter_activity()+"()}", new CallableStatementCallback() {
				
				@Override
				public Object doInCallableStatement(CallableStatement cs)
						throws SQLException, DataAccessException {
					cs.executeUpdate();
					return null;
				}
			});
			 flag="success";
		}catch(Exception e){
		
			e.printStackTrace();
			DefaultLogger.debug(this, e.getMessage());
			throw new Exception("Exception in generateCamQuarterExtract.");
		}
		return flag;
	
	}
	
}