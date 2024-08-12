/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
*@author $Author: Abhijit R$
*Command to read CaseBranch
 */
public class MakerReadCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseBranchProxyManager caseBranchProxy;

	
	
	
	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(
			ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadCaseBranchCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				 {"branchCode", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "caseBranchObj", "com.integrosys.cms.app.caseBranch.bus.OBCaseBranch", SERVICE_SCOPE },
				{ "caseBranchObj", "com.integrosys.cms.app.caseBranch.bus.OBCaseBranch", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue", SERVICE_SCOPE}
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICaseBranch caseBranch;
			ICaseBranchTrxValue trxValue=null;
			String caseBranchCode=(String) (map.get("branchCode"));
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			String event = (String) map.get("event");
			trxValue = (OBCaseBranchTrxValue) getCaseBranchProxy().getCaseBranchTrxValue(Long.parseLong(caseBranchCode));
			caseBranch = (OBCaseBranch) trxValue.getCaseBranch();
			long id = caseBranch.getId();
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_HOLIDAY",id,"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE")))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);	
			resultMap.put("ICaseBranchTrxValue", trxValue);
			resultMap.put("caseBranchObj", caseBranch);
		}catch (CaseBranchException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	

}
