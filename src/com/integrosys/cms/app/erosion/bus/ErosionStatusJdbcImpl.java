package com.integrosys.cms.app.erosion.bus;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;

public class ErosionStatusJdbcImpl extends JdbcTemplateAdapter implements IErosionStatusJdbc{

	public String spCreateDataForFacilityReport;
	public String spCreateDataForSecurityReport;
	public String spCreateDataForFacilityWiseReport;
	public String spCreateDataForPartyWiseReport;
	public String spUpdateErosionForNpaFile;
	public String spDataBackupForErosion;
	
	public String getSpDataBackupForErosion() {
		return spDataBackupForErosion;
	}

	public void setSpDataBackupForErosion(String spDataBackupForErosion) {
		this.spDataBackupForErosion = spDataBackupForErosion;
	}

	public String getSpCreateDataForFacilityReport() {
		return spCreateDataForFacilityReport;
	}

	public void setSpCreateDataForFacilityReport(String spCreateDataForFacilityReport) {
		this.spCreateDataForFacilityReport = spCreateDataForFacilityReport;
	}

	public String getSpCreateDataForSecurityReport() {
		return spCreateDataForSecurityReport;
	}

	public void setSpCreateDataForSecurityReport(String spCreateDataForSecurityReport) {
		this.spCreateDataForSecurityReport = spCreateDataForSecurityReport;
	}

	public String getSpCreateDataForFacilityWiseReport() {
		return spCreateDataForFacilityWiseReport;
	}

	public void setSpCreateDataForFacilityWiseReport(String spCreateDataForFacilityWiseReport) {
		this.spCreateDataForFacilityWiseReport = spCreateDataForFacilityWiseReport;
	}

	public String getSpCreateDataForPartyWiseReport() {
		return spCreateDataForPartyWiseReport;
	}

	public void setSpCreateDataForPartyWiseReport(String spCreateDataForPartyWiseReport) {
		this.spCreateDataForPartyWiseReport = spCreateDataForPartyWiseReport;
	}

	public String getSpUpdateErosionForNpaFile() {
		return spUpdateErosionForNpaFile;
	}

	public void setSpUpdateErosionForNpaFile(String spUpdateErosionForNpaFile) {
		this.spUpdateErosionForNpaFile = spUpdateErosionForNpaFile;
	}

	@Override
	public void spCreateDataForFacilityReport(String applicationDate) throws ErosionStatusException {
		try {
            getJdbcTemplate().execute("{call " + getSpCreateDataForFacilityReport() + "('N', DATE '"+applicationDate+"')}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spCreateDataForFacilityReport().");
        }
		
	}

	@Override
	public void spCreateDataForSecurityReport() {
		try {
            getJdbcTemplate().execute("{call " + getSpCreateDataForSecurityReport() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spCreateDataForSecurityReport().");
        }
		
	}

	@Override
	public void spCreateDataForFacilityWiseReport(String applicationDate) {
		try {
            getJdbcTemplate().execute("{call " + getSpCreateDataForFacilityWiseReport() + "(DATE '"+applicationDate+"')}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spCreateDataForFacilityWiseReport().");
        }
		
	}

	@Override
	public void spCreateDataForPartyWiseReport() {
		try {
            getJdbcTemplate().execute("{call " + getSpCreateDataForPartyWiseReport() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spCreateDataForPartyWiseReport().");
        }
		
	}

	@Override
	public void spUpdateErosionForNpaFile() {
		try {
            getJdbcTemplate().execute("{call " + getSpUpdateErosionForNpaFile() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spUpdateErosionForNpaFile().");
        }
		
	}
	
	public List findErosionActivities(boolean isCreate) throws ErosionStatusException {
		List<IErosionStatus> erosionStatusList = new ArrayList();
		String sql = "SELECT ID,REPORTING_DATE,ACTIVITY,STATUS from CMS_EROSION_STATUS";
		ResultSet rs;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				IErosionStatus erosionStatus = new OBErosionStatus();
				erosionStatus.setId(rs.getLong("ID"));
				erosionStatus.setReportingDate(rs.getDate("REPORTING_DATE"));
				erosionStatus.setActivity(rs.getString("ACTIVITY"));
				erosionStatus.setStatus(rs.getString("STATUS"));
				erosionStatusList.add(erosionStatus);
			}
			rs.close();
			return erosionStatusList;
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in findErosionActivities()", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in findErosionActivities()", ex);
		} finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			} catch (SQLException ex) {
				throw new SearchDAOException("SQLException in findErosionActivities()", ex);
			}
		}
	}
	
	public void updateErosionActivity(IErosionStatus erosionStatus) throws ErosionStatusException {
		String queryStr="UPDATE CMS_EROSION_STATUS SET ID=?,REPORTING_DATE=?, ACTIVITY=?, STATUS=? WHERE ID=?";
		if (erosionStatus == null) {
			throw new ErosionStatusException("ErosionStatusDaoImpl.updateErosionActivity(IErosionStatus): IErosionStatus cannot be null.");
		}
		DefaultLogger.debug(this,"before updating status pending "+erosionStatus.getStatus());
		
		DBUtil curUtil = null;
		ResultSet rs = null;
		try {
			curUtil = new DBUtil();
			curUtil.setSQL(queryStr);
			curUtil.setLong(1, erosionStatus.getId());
			curUtil.setDate(2, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(erosionStatus.getReportingDate())));
			curUtil.setString(3, erosionStatus.getActivity());
			curUtil.setString(4, erosionStatus.getStatus());
			curUtil.setLong(5, erosionStatus.getId());
			rs = curUtil.executeQuery();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new SearchDAOException();
		}
		finally{
			try {
				if (curUtil != null) {
					curUtil.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DefaultLogger.debug(this,"after updating status pending "+erosionStatus.getStatus());
	}
	
	public OBGeneralParamEntry getAppDate(){
		OBGeneralParamEntry result = new OBGeneralParamEntry();
		
		String sql = "select PARAM_CODE,PARAM_VALUE from cms_general_param where param_code='APPLICATION_DATE'";
		ResultSet rs;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				result.setParamCode(rs.getString("PARAM_CODE"));
				result.setParamValue(rs.getString("PARAM_VALUE"));
			}
			rs.close();
			return result;
		} catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getAppDate()", ex);
		} catch (Exception ex) {
			throw new SearchDAOException("Exception in getAppDate()", ex);
		} finally {
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			} catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getAppDate()", ex);
			}
		}
	}

	@Override
	public void spErosionDataBackup() {
		try {
            getJdbcTemplate().execute("{call " + getSpDataBackupForErosion() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (ErosionStatusException ex) {
        	ex.printStackTrace();
            throw new ErosionStatusException("Error spErosionDataBackup().");
        }
	}

}
