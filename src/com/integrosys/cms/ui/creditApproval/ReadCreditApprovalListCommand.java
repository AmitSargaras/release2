package com.integrosys.cms.ui.creditApproval;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;

/**
 * This class implements command
 */
public class ReadCreditApprovalListCommand extends CreditApprovalCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
	             { "approvalName", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }		
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
																																																	// offset																										// .
		{ "oBCreditApproval", "java.util.ArrayList",REQUEST_SCOPE},
		{ "event", "java.lang.String", REQUEST_SCOPE },
		{ "startIndex", "java.lang.String", REQUEST_SCOPE },
		 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
         { "approvalName", "java.lang.String", REQUEST_SCOPE},
		 {"searchResultCreditApproval", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
		
																	 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			String approvalCode="";
			String approvalName="";
			approvalCode=(String) map.get("approvalCode");
			approvalName=(String) map.get("approvalName");
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIndex = (String) map.get("startIndex");
			String event = (String) map.get("event");
			SearchResult searchResultCreditApproval = null;
			boolean update =getUpdateTable();
			if(update){
				updateCreditApprovalError();
			}
			if(startIndex==null){
			startIndex = "0";
			}
			List creditApprovalList = getCreditApprovalProxy().getCreditApprovalList();
			
			 searchResultCreditApproval = new SearchResult(Integer.parseInt(startIndex), 10, creditApprovalList.size(), creditApprovalList);
			
			DefaultLogger.debug(this, "after getting CreditApproval feed group from proxy.");
			resultMap.put("oBCreditApproval", creditApprovalList);
			returnMap.put("event", event);
			
			resultMap.put("searchResultCreditApproval", searchResultCreditApproval);
			resultMap.put("startIndex", startIndex);
			resultMap.put("approvalCode", approvalCode);            
            resultMap.put("approvalName", approvalName);
		}
		catch (CreditApprovalException ex) {
	       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
	         ex.printStackTrace();
	         throw (new CommandProcessingException(ex.getMessage()));
		}
	    catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));

		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
private void updateCreditApprovalError(){
		
		DBUtil dbUtil = null;
		
		String sql = "update CMS_CREDIT_APPROVAL set APPROVAL_CODE='APP0000572' ,APPROVAL_NAME='MAHIMA DIXIT' ," +
				"MAXIMUM_LIMIT=500000,MINIMUM_LIMIT=0,SEGMENT=null,EMAIL='mahima.dixit@hdfcbank.com',SENIOR='N',RISK='N'," +
				"DEFERRAL_POWERS='N',WAIVING_POWERS='N',REGION_ID=0,VERSION_TIME=0,CREATE_BY='SYSTEM',CREATION_DATE='25-APR-12 10.03.17.242708000 PM'," +
				"LAST_UPDATE_BY='SYSTEM',LAST_UPDATE_DATE='25-APR-12 10.03.17.242708000 PM',DEPRECATED='N',STATUS='ACTIVE' where id=557 ";
		


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
		
			int  rs = dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in ReadCreditApprovalListCommand", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in ReadCreditApprovalListCommand", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in ReadCreditApprovalListCommand", ex);
			}
		}
		
	}
	
	private boolean getUpdateTable(){
		
		DBUtil dbUtil = null;
		
		String sql = "select * from  CMS_CREDIT_APPROVAL where  APPROVAL_CODE='APP0000572' ";
		
		DefaultLogger.debug(this, "--------1---------"+sql);
		String data  = new String();
		ResultSet rs=null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
		
			  rs = dbUtil.executeQuery();
			  if(rs.next()){
				  return false;
			  }else{
				  return true;
			  }
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in ReadCreditApprovalListCommand", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in ReadCreditApprovalListCommand", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in ReadCreditApprovalListCommand", ex);
			}
		}
		
	}
	
}
