package com.integrosys.cms.app.erosion.bus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;

public class ErosionStatusJdbcTemplateImpl extends JdbcDaoSupport implements IErosionStatusJdbcTemplateImpl{
	
	
	public List findErosionActivities(boolean isCreate) throws ErosionStatusException {
		String sql = "SELECT ID,REPORTING_DATE,ACTIVITY,STATUS from CMS_EROSION_STATUS";
		
		if(isCreate)
			sql="SELECT ID,REPORTING_DATE,ACTIVITY,STATUS from CMS_EROSION_STATUS WHERE ACTIVITY LIKE 'Create Data%' OR ACTIVITY LIKE 'Update Erosion%'";
		else
			sql="SELECT ID,REPORTING_DATE,ACTIVITY,STATUS from CMS_EROSION_STATUS WHERE ACTIVITY LIKE 'Generate File%'";
			
		DefaultLogger.debug(this, "In the findErosionActivities(): " + sql);

		return (List<IErosionStatus>) getJdbcTemplate().query(sql, new ResultSetExtractor() {

			@Override
			public List<IErosionStatus> extractData(ResultSet rs) throws SQLException {
				List<IErosionStatus> returnData = new ArrayList<IErosionStatus>();
				while (rs.next()) {
					IErosionStatus erosionStatus = new OBErosionStatus();
					erosionStatus.setId(rs.getLong("ID"));
					erosionStatus.setReportingDate(rs.getDate("REPORTING_DATE"));
					erosionStatus.setActivity(rs.getString("ACTIVITY"));
					erosionStatus.setStatus(rs.getString("STATUS"));
					returnData.add(erosionStatus);
				}
				return returnData;
			}
		});

	}
	
	public void updateErosionActivity(final IErosionStatus erosionStatus) throws ErosionStatusException {
		String queryStr = "UPDATE CMS_EROSION_STATUS SET ID=?,REPORTING_DATE=?, ACTIVITY=?, STATUS=? WHERE ID=?";
		if (erosionStatus == null) {
			throw new ErosionStatusException(
					"ErosionStatusDaoImpl.updateErosionActivity(IErosionStatus): IErosionStatus cannot be null.");
		}
		DefaultLogger.debug(this, "before updating ErosionActivity : " + erosionStatus.getStatus());

		int executeUpdate = getJdbcTemplate().update(queryStr, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, erosionStatus.getId());
				ps.setTimestamp(2, new Timestamp(erosionStatus.getReportingDate().getTime()));
				ps.setString(3, erosionStatus.getActivity());
				ps.setString(4, erosionStatus.getStatus());
				ps.setLong(5, erosionStatus.getId());
			}
		});

		DefaultLogger.debug(this, "after updating ErosionActivity : " + erosionStatus.getStatus());
	}
	
	public OBGeneralParamEntry getAppDate() throws ErosionStatusException{

		String sqlDate = "select PARAM_CODE,PARAM_VALUE from cms_general_param where param_code='APPLICATION_DATE'";
		DefaultLogger.debug(this, "In the getAppDate(): " + sqlDate);

		return (OBGeneralParamEntry) getJdbcTemplate().query(sqlDate, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				OBGeneralParamEntry result = new OBGeneralParamEntry();
				if (rs.next()) {
					result.setParamCode(rs.getString("PARAM_CODE"));
					result.setParamValue(rs.getString("PARAM_VALUE"));
				}
				return result;
			}
		});

	}
	
	public OBGeneralParamEntry getErosionDate() throws ErosionStatusException{

		String sqlDate = "select PARAM_CODE,PARAM_VALUE from cms_general_param where param_code='EROSION_DATE'";
		DefaultLogger.debug(this, "In the getAppDate(): " + sqlDate);

		return (OBGeneralParamEntry) getJdbcTemplate().query(sqlDate, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				OBGeneralParamEntry result = new OBGeneralParamEntry();
				if (rs.next()) {
					result.setParamCode(rs.getString("PARAM_CODE"));
					result.setParamValue(rs.getString("PARAM_VALUE"));
				}
				return result;
			}
		});

	}
	
	public void updateGeneralParamErosionDate(final String newExecutionDate,final Date erosionDate) {

		DBUtil dbUtil = null;
		String sql = "update CMS_GENERAL_PARAM set param_value=?,LAST_UPDATE_DATE=? where param_code='EROSION_DATE'";

		DefaultLogger.debug(this, "before updating updateGeneralParamErosionDate() : " + sql);

		int executeUpdate = getJdbcTemplate().update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, newExecutionDate);
				ps.setDate(2, new java.sql.Date(erosionDate.getTime()));
			}
		});

		DefaultLogger.debug(this, "after updating updateGeneralParamErosionDate()");

	}


}
