package com.integrosys.cms.app.eod.refreshMv;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileUpload.bus.OBBaselUploadFile;
import com.integrosys.cms.app.fileUpload.bus.OBCommonFile;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/* @author uma.khot
 * 
 */

public class RefreshMvDao implements IRefreshMvDao {


    private JdbcTemplate jdbcTemplate;
    private String spUserTransaction;
    
    private String spRefreshEwsStockDeferral;
    
    private String spRefreshDPAuditTrailMV;
    
    
    private String spRefreshCustomerWiseSecurity;
    
    private String spRefreshCalculateRecDate;
    
    private String spRefreshStockStmntReportRM;
    
    private String spRefreshSecMapChargeIdMv;
    
    public String getSpRefreshSecMapChargeIdMv() {
 		return spRefreshSecMapChargeIdMv;
 	}

 	public void setSpRefreshSecMapChargeIdMv(String spRefreshSecMapChargeIdMv) {
 		this.spRefreshSecMapChargeIdMv = spRefreshSecMapChargeIdMv;
 	}
 	
    public String getSpRefreshStockStmntReportRM() {
		return spRefreshStockStmntReportRM;
	}

	public void setSpRefreshStockStmntReportRM(String spRefreshStockStmntReportRM) {
		this.spRefreshStockStmntReportRM = spRefreshStockStmntReportRM;
	}

	public String getSpRefreshCustomerWiseSecurity() {
			return spRefreshCustomerWiseSecurity;
	}

	public void setSpRefreshCustomerWiseSecurity(String spRefreshCustomerWiseSecurity) {
		this.spRefreshCustomerWiseSecurity = spRefreshCustomerWiseSecurity;
	}
	
	public String getSpRefreshCalculateRecDate() {
		return spRefreshCalculateRecDate;
	}

	public void setSpRefreshCalculateRecDate(String spRefreshCalculateRecDate) {
		this.spRefreshCalculateRecDate = spRefreshCalculateRecDate;
	}
  
    public String getSpRefreshDPAuditTrailMV() {
		return spRefreshDPAuditTrailMV;
	}

	public void setSpRefreshDPAuditTrailMV(String spRefreshDPAuditTrailMV) {
		this.spRefreshDPAuditTrailMV = spRefreshDPAuditTrailMV;
	}

	public String getSpRefreshEwsStockDeferral() {
		return spRefreshEwsStockDeferral;
	}

	public void setSpRefreshEwsStockDeferral(String spRefreshEwsStockDeferral) {
		this.spRefreshEwsStockDeferral = spRefreshEwsStockDeferral;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public String getSpUserTransaction() {
		return spUserTransaction;
	}

	public void setSpUserTransaction(String spUserTransaction) {
		this.spUserTransaction = spUserTransaction;
	}

	@Override
	public void refreshMvForUserAdminReport() throws Exception {
	       try {
	            getJdbcTemplate().execute("{call " + getSpUserTransaction() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For UserAdminReport Completed.");
	        }
	        catch (Exception ex) {
	        	ex.printStackTrace();
	            throw new Exception("Error while refreshing materialized view For UserAdminReport");
	        }
		
	}
	
	//Added for EWSStockDeferral
	@Override
	public void refreshMvForEwsStockDeferral() throws Exception {
	       try {
	            getJdbcTemplate().execute("{call " + getSpRefreshEwsStockDeferral() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For EWSStockDeferral Completed.");
	            System.out.println ("Refreshing materialized view For EWSStockDeferral Completed");
	        }
	        catch (Exception ex) {
	            System.out.println("Error while refreshing materialized view For EWSStockDeferral"+ex.getMessage());
	        	ex.printStackTrace();
	            throw new Exception("Error while refreshing materialized view For EWSStockDeferral");
	        }
		
	}
	@Override
	public void refreshMvForAuditTrailReportDB() throws Exception {
	       try {
	            getJdbcTemplate().execute("{call " + getSpRefreshDPAuditTrailMV() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For DPRegulAuditTrailMV Completed.");
	            System.out.println ("Refreshing materialized view For DPRegulAuditTrailMV Completed");
	        }
	        catch (Exception ex) {
	            System.out.println("Error while refreshing materialized view For DPRegulAuditTrailMV"+ex.getMessage());
	        	ex.printStackTrace();
	            throw new Exception("Error while refreshing materialized view For DPRegulAuditTrailMV");
	        }
	         	
	}
	
	
	@Override
	public void refreshMvForCustomerWiseSecurityReport() throws Exception {
	       try {
	    	   System.out.println("Inside refreshMvForCustomerWiseSecurityReport()");
	            getJdbcTemplate().execute("{call " + getSpRefreshCustomerWiseSecurity() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For CustomerWiseSecurity Completed.");
	        }
	        catch (Exception ex) {
	        	ex.printStackTrace();
	        	System.out.println("Exception while refreshing materialized view For CustomerWiseSecurity..ex=>"+ex);
	            throw new Exception("Error while refreshing materialized view For CustomerWiseSecurity");
	        }
		
	}
	

	@Override
	public void refreshMvForCustomerWiseStockDetailsReport() throws Exception {
	       try {
	    	   System.out.println("Inside refreshMvForCustomerWiseStockDetailsReport() =>getSpRefreshCalculateRecDate()=>CALCULATE_REC_DATE_MV..");
	            getJdbcTemplate().execute("{call " + getSpRefreshCalculateRecDate() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For CustomerWiseStockDetails getSpRefreshCalculateRecDate()=>CALCULATE_REC_DATE_MV Completed.");
	        }
	        catch (Exception ex) {
	        	ex.printStackTrace();
	        	System.out.println("Exception while refreshing materialized view For CustomerWiseStockDetails..getSpRefreshCalculateRecDate()=>CALCULATE_REC_DATE_MV.. =>ex=>"+ex);
	            throw new Exception("Error while refreshing materialized view For CustomerWiseStockDetails getSpRefreshCalculateRecDate()=>CALCULATE_REC_DATE_MV..");
	        }
		
	}

	@Override
	public void refreshMvForStockStatementReportForRM() throws Exception {
		try {
			getJdbcTemplate().execute("{call " + getSpRefreshStockStmntReportRM() + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
            DefaultLogger.debug(this, "Refreshing materialized view For StockStatementReportRMMV Completed.");
            System.out.println ("Refreshing materialized view For StockStatementReportRMMV Completed");
        }
        catch (Exception ex) {
            System.out.println("Error while refreshing materialized view For StockStatementReportRMMV"+ex.getMessage());
        	ex.printStackTrace();}
			}
	

	@Override
	public void refreshMvForSPRfreshSecMapChargeIdMV() throws Exception {
	       try {
	    	   System.out.println("Inside refreshMvForSPRfreshSecMapChargeIdMV() =>getSpRefreshSecMapChargeIdMv()=>SP_RFRESH_SECMAPCHARGEID_MV..");
	            getJdbcTemplate().execute("{call " + getSpRefreshSecMapChargeIdMv() + "()}",  new CallableStatementCallback() {
	                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
	                	cs.executeUpdate();
	                    return null;
	                }
	            });
	            DefaultLogger.debug(this, "Refreshing materialized view For  getSpRefreshSecMapChargeIdMv()=>SP_RFRESH_SECMAPCHARGEID_MV Completed.");
	        }
	        catch (Exception ex) {
	        	ex.printStackTrace();
	        	System.out.println("Exception while refreshing materialized view For ..getSpRefreshSecMapChargeIdMv()=>SP_RFRESH_SECMAPCHARGEID_MV.. =>ex=>"+ex);
	            throw new Exception("Error while refreshing materialized view For  getSpRefreshSecMapChargeIdMv()=>SP_RFRESH_SECMAPCHARGEID_MV..");
	        }
		
	}
	
}