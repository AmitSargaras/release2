/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.tat;

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
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class to reject a custodian doc state by checker..
 * @author $Author: pooja $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/09/03 07:17:00 $ Tag: $Name: $
 */
public class AppChkTatCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AppChkTatCommand() {
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
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
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
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE } });
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
		ILimitProfileTrxValue iLimitprofileTrxValue = (ILimitProfileTrxValue) map.get("trxValue");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event= " + event);
		try {
			theOBTrxContext.setTrxCountryOrigin(iLimitprofileTrxValue.getLimitProfile().getOriginatingLocation()
					.getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(iLimitprofileTrxValue.getLimitProfile().getOriginatingLocation()
					.getOrganisationCode());
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			if (iLimitprofileTrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)) {
				ICMSTrxResult resultOb = limitproxy.checkerApproveCreateTAT(theOBTrxContext, iLimitprofileTrxValue);
				resultMap.put("request.ITrxResult", resultOb);
			}
			else {
				iLimitprofileTrxValue.setTransactionSubType("TAT");
				ICMSTrxResult resultOb = limitproxy.checkerApproveUpdateLimitProfile(theOBTrxContext,
						iLimitprofileTrxValue);
				resultMap.put("request.ITrxResult", resultOb);
			}

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
		
	}
}
