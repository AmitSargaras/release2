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

/**
 *$Author: Abhijit R $
 *Command for checker to read CaseBranch Trx value
 */
public class CheckerReadCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseBranchProxyManager caseBranchProxy;

	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadCaseBranchCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
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
				{ "caseBranchObj", "com.integrosys.cms.app.caseBranch.bus.OBCaseBranch", FORM_SCOPE },
				{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,CaseBranchException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICaseBranch caseBranch;
			ICaseBranchTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get CaseBranch Trx value
			trxValue = (OBCaseBranchTrxValue) getCaseBranchProxy().getCaseBranchByTrxID(branchCode);
			
			caseBranch = (OBCaseBranch) trxValue.getStagingCaseBranch();
			
			resultMap.put("ICaseBranchTrxValue", trxValue);
			resultMap.put("caseBranchObj", caseBranch);
			resultMap.put("event", event);
		} catch (CaseBranchException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
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
