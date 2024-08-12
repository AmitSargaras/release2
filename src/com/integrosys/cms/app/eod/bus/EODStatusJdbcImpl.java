package com.integrosys.cms.app.eod.bus;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.batch.eod.EODConstants;

public class EODStatusJdbcImpl  extends JdbcTemplateAdapter implements IEODStatusJdbc {

	
	
	public void recpColLimitLink() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_CMS_Recp_Col_Limit_Link + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}

	
	public void recpColProductLink() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_CMS_RECP_COL_PRODUCT_LINK + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}

	
	public void recpCollateral() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_cms_Recp_Collateral + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}

	
	public void recpCustLegal() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_CMS_RECP_CUST_Legal + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}

	
	public void recpCustomer() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_cms_Recp_Customer + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}

	
	public void recpParty() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_cms_Recp_PARTY + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}
	
	public void recpRbiAdfAllProcedure() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.PROC_SP_CMS_RECP_ALL_PROCEDURE + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error executeReceivedStatementProc.");
        }
		
	}


	
	public String[] getStatusEODActivities() throws EODStatusException {
		String[] status= new String[6];
 		String sql = "select status from CMS_ADF_RBI_STATUS";
		ResultSet rs;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			// skip initial rows as specified by the startIndex.
			int index=0;
			while ( rs.next()) {
				status[index]=rs.getString("status");
				index++;
			}
			rs.close();
			return status;
	}catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getStatusEODActivities", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getStatusEODActivities", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getStatusEODActivities", ex);
		}
	}
	
		
	
}
	
	public void recpFinconParty() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Party + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Party.");
        }
		
	}

	public void recpFinconColLimitLink() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Col_Limit_link + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Col_Limit_link.");
        }
		
	}


	public void recpFinconColProductLink() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Col_Prod_Link + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Col_Prod_Link.");
        }
		
	}


	public void recpFinconCollateral() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Collateral + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Collateral.");
        }
		
	}


	public void recpFinconCustLegal() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Cust_Legal + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Cust_Legal.");
        }
		
	}


	public void recpFinconCustomer() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Recp_Customer + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Recp_Customer.");
        }
		
	}
	
	// Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Starts
	public void recpFinconCustWiseSec() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Cust_Wise_Sec + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Cust_Wise_Sec.");
        }
		
	}
	
	public void recpFinconMasterIndustry() throws EODStatusException {
		try {
            getJdbcTemplate().execute("{call " + EODConstants.Proc_SP_Fincon_Master_Industry + "()}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
        }
        catch (EODStatusException ex) {
        	ex.printStackTrace();
            throw new EODStatusException("Error Proc_SP_Fincon_Master_Industry.");
        }
		
	}
	
	//Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Ends
	
}
