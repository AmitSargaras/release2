/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class to reject a custodian doc state by checker..
 * @author $Author: pooja $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/09/12 10:53:14 $ Tag: $Name: $
 */
public class RejectChkCoBorrowerCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RejectChkCoBorrowerCommand() {
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
				{ "coBorrowertrxValue", "com.integrosys.cms.app.limit.trx.OBCoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICoBorrowerLimitTrxValue iCoBorrowerLimitTrxValue = (ICoBorrowerLimitTrxValue) map.get("coBorrowertrxValue");
		try {

			ILimitProxy coborrowerproxy = LimitProxyFactory.getProxy();
			DefaultLogger.debug(this, "remarks is " + theOBTrxContext.getRemarks());
			long outerLimitID = iCoBorrowerLimitTrxValue.getLimit().getOuterLimitID();
			ILimit outerLimit = coborrowerproxy.getLimit(outerLimitID);
			theOBTrxContext.setTrxCountryOrigin(outerLimit.getBookingLocation().getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(outerLimit.getBookingLocation().getOrganisationCode());
			ICMSTrxResult resultOb = coborrowerproxy.checkerRejectUpdateCoBorrowerLimit(theOBTrxContext,
					iCoBorrowerLimitTrxValue);
			resultMap.put("request.ITrxResult", resultOb);
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
