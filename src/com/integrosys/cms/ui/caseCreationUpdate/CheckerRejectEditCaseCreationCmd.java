/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseCreationUpdate;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.OBCaseCreationTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 * @author $Author: Abhijit R $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectEditCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseCreationProxyManager caseCreationProxy;

	
	
	public ICaseCreationProxyManager getCaseCreationProxy() {
		return caseCreationProxy;
	}

	public void setCaseCreationProxy(
			ICaseCreationProxyManager caseCreationProxy) {
		this.caseCreationProxy = caseCreationProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditCaseCreationCmd() {
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
				{ "ICaseCreationTrxValue", "com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				
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
	       
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            ICaseCreationTrxValue trxValueIn = (OBCaseCreationTrxValue) map.get("ICaseCreationTrxValue");
	            HashMap exceptionMap = new HashMap();
	            String event = (String) map.get("event");
	            String remarks = (String) map.get("remarks");
	            if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("caseCreationUpdateRemarksError", new ActionMessage("error.reject.remark"));
					ISystemBankBranchTrxValue trxValueInValidate = null;
					
					resultMap.put("trxId", trxValueIn.getTransactionID());
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}else{
					 try {
	            ctx.setRemarks(remarks);
	            ICaseCreationTrxValue trxValueOut = getCaseCreationProxy().checkerRejectCaseCreation(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            
	        }catch (CaseCreationException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
				}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
