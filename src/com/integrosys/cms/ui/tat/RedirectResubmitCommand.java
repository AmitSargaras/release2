/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/RedirectResubmitCommand.java,v 1.6 2006/04/19 09:42:58 jychong Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;

/**
 * Command class to reject a custodian doc state by checker..
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/04/19 09:42:58 $ Tag: $Name: $
 */
public class RedirectResubmitCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RedirectResubmitCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "resubmitToDo", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "entered RedirectResubmitCommand" + "");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String trxID = (String) map.get("trxID");
		String event = (String) map.get("event");
		// DefaultLogger.debug(this, "event= " + trxID);
		try {

			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue limitProfileTrxValue = new OBLimitProfileTrxValue();
			limitProfileTrxValue = limitproxy.getTrxLimitProfile(trxID);
			if (limitProfileTrxValue.getFromState().equals(ICMSConstant.STATE_PENDING_CREATE)) {
				resultMap.put("resubmitToDo", "Tat");
			}
			else {
				if ("LimitProfile".equals(limitProfileTrxValue.getTransactionSubType())) {
					resultMap.put("resubmitToDo", "LimitProfile");
				}
				else {
					resultMap.put("resubmitToDo", "Tat");
				}
			}
			/***
			 * CMSSP-697 else if
			 * ((limitProfileTrxValue.getFromState().equals(ICMSConstant
			 * .STATE_PENDING_UPDATE)) &&
			 * (limitProfileTrxValue.getTransactionSubType().equals("TAT")))
			 * resultMap.put("resubmitToDo", "Tat"); else if
			 * ((limitProfileTrxValue
			 * .getFromState().equals(ICMSConstant.STATE_PENDING_UPDATE)) &&
			 * (limitProfileTrxValue
			 * .getTransactionSubType().equals("LimitProfile")))
			 * resultMap.put("resubmitToDo", "LimitProfile");
			 ***/
			resultMap.put("event", event);
			resultMap.put("trxID", trxID);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}

}
