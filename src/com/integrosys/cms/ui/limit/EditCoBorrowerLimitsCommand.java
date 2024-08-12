/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/EditCoBorrowerLimitsCommand.java,v 1.4 2005/11/07 12:27:07 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/07 12:27:07 $ Tag: $Name: $
 */

public class EditCoBorrowerLimitsCommand extends AbstractCommand {

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "updatedCoBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue",
						FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		ICoBorrowerLimitTrxValue updatedTrxValue = (ICoBorrowerLimitTrxValue) map.get("updatedCoBorrowerLimitTrxValue");
		ICoBorrowerLimitTrxValue[] trxValues = updatedTrxValue.getCoBorrowerLimitTrxValues();
		if ((trxValues != null) && (trxValues.length != 0)) {
			AccessorUtil.copyValue(trxValues[0], updatedTrxValue, new String[] { "getCoBorrowerLimitTrxValues" });
			for (int i = 0; i < trxValues.length; i++) {
				ICoBorrowerLimit limit = (ICoBorrowerLimit) trxValues[i].getLimit();
				if (!limit.getIsDAPError()) {
					trxContext.setTrxCountryOrigin(limit.getBookingLocation().getCountryCode());
					trxContext.setTrxOrganisationOrigin(limit.getBookingLocation().getOrganisationCode());
				}
			}
		}
		ILimitProxy limitproxy = LimitProxyFactory.getProxy();
		try {
			ICMSTrxResult trxResult = limitproxy.makerUpdateCoBorrowerLimit(trxContext, updatedTrxValue);
			ICoBorrowerLimitTrxValue trxValue = (ICoBorrowerLimitTrxValue) trxResult.getTrxValue();
			result.put("request.ITrxResult", trxResult);
			result.put("request.ITrxValue", trxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}
}
