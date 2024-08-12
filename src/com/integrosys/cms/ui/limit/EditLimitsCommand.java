/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/EditLimitsCommand.java,v 1.8 2005/11/07 12:27:07 whuang Exp $
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
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: whuang $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/11/07 12:27:07 $ Tag: $Name: $
 */
public class EditLimitsCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public EditLimitsCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "updatedLimitsMap", "java.util.HashMap", FORM_SCOPE },
				{ "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
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
				{ "request.ITrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", REQUEST_SCOPE } });
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
		HashMap updatedLimitsMap = (HashMap) map.get("updatedLimitsMap");
		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		ILimitTrxValue limitTrxProfile = (ILimitTrxValue) map.get("limitTrxProfile");
		ILimitTrxValue[] trxValues = limitTrxProfile.getLimitTrxValues();
		if ((trxValues != null) && (trxValues.length != 0)) {
			for (int i = 0; i < trxValues.length; i++) {
				ILimit limit = (ILimit) trxValues[i].getLimit();
				DefaultLogger.debug(this, "limitID: ---------------------- " + limit.getLimitID());
				if (!limit.getIsDAPError()) {
					DefaultLogger.debug(this, "Edit Limit limitID: ---------------------- " + limit.getLimitID());
					trxContext.setTrxCountryOrigin(limit.getBookingLocation().getCountryCode());
					trxContext.setTrxOrganisationOrigin(limit.getBookingLocation().getOrganisationCode());
					break;
				}
			}
		}
		ILimitProxy limitproxy = LimitProxyFactory.getProxy();
		try {
			ICMSTrxResult trxResult = limitproxy.makerUpdateLimit(trxContext, limitTrxProfile, updatedLimitsMap);
			ILimitTrxValue trxValue = (ILimitTrxValue) trxResult.getTrxValue();
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
