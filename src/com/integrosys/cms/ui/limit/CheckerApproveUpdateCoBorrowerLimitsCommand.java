/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/CheckerApproveUpdateCoBorrowerLimitsCommand.java,v 1.4 2005/11/07 05:43:01 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/07 05:43:01 $ Tag: $Name: $
 */

public class CheckerApproveUpdateCoBorrowerLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "coBorrowerLimitTrxValue", "com.integrosys.cms.app.limit.trx.ICoBorrowerLimitTrxValue", SERVICE_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
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
		HashMap temp = new HashMap();
		ITrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String remarks = (String) map.get("remarks");
		ICoBorrowerLimitTrxValue coBorrowerLimitTrxValue = (ICoBorrowerLimitTrxValue) map
				.get("coBorrowerLimitTrxValue");
		coBorrowerLimitTrxValue.setRemarks(remarks);

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			ICMSTrxResult trxResult = limitProxy.checkerApproveUpdateCoBorrowerLimit(trxContext,
					coBorrowerLimitTrxValue);
			ICoBorrowerLimitTrxValue trxValue = (ICoBorrowerLimitTrxValue) trxResult.getTrxValue();
			result.put("request.ITrxResult", trxResult);
			result.put("request.ITrxValue", trxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
