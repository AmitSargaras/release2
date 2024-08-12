/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for checker to reject the transaction
 * Description: command that let the checker to reject the transaction that
 * being make by the maker
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CheckerRejectAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE},
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
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here rejection for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		String remarks = (String) map.get("remarks");
        if(remarks == null||remarks.trim().equals("")){
				exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
				resultMap.put("TrxId", limitProfileTrxVal.getTransactionID());
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else{
		
		
		
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			//DefaultLogger.debug(this, " >>>Debug:::CheckerRejectAADetailCommand Before reject TrxValue = " + AccessorUtil.printMethodValue(limitProfileTrxVal));

			ICMSTrxResult res = proxy.checkerRejectUpdateLimitProfile(trxContext, limitProfileTrxVal);
			resultMap.put("request.ITrxResult", res);

		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
			
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
			}
	}

}
