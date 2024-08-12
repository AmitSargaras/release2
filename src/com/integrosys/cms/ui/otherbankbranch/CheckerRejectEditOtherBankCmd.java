/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 * @author $Author: Dattatray Thorat $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectEditOtherBankCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IOtherBankProxyManager otherBankProxyManager;

	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditOtherBankCmd() {
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
				{ "IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 { "TrxId", "java.lang.String", REQUEST_SCOPE }
		
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE },
				{ "IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }
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
	        HashMap exceptionMap = new HashMap();
	        try {
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            IOtherBankTrxValue trxValueIn = (OBOtherBankTrxValue) map.get("IOtherBankTrxValue");
	            String trxId=(String) (map.get("TrxId"));
	            String remarks = (String) map.get("remarks");
	            if(remarks == null || remarks.equals("")){
	            	exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
	            	IOtherBankTrxValue trxValue = null;
	            	resultMap.put("TrxId", trxId);
	            	resultMap.put("IOtherBankTrxValue", trxValueIn);
	            	resultMap.put("request.ITrxValue", trxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	            }else{
	            	ctx.setRemarks(remarks);
	            	IOtherBankTrxValue trxValueOut = getOtherBankProxyManager().checkerRejectOtherBank(ctx, trxValueIn);
	            	resultMap.put("request.ITrxValue", trxValueOut);
	            }	
	        }catch (OtherBankException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
