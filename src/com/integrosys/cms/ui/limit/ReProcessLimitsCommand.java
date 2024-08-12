/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ReProcessLimitsCommand.java,v 1.4 2005/09/30 07:44:02 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/30 07:44:02 $ Tag: $Name: $
 */
public class ReProcessLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", FORM_SCOPE },
				{ "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ArrayList referenceList = GenerateLimitHelper.getLimitsReference(map, theOBTrxContext);
		ILimitProfile limitProfile = (ILimitProfile) referenceList.get(0);
		HashMap bcaList = (HashMap) referenceList.get(1);
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			// get LimitTrxValue
			theOBTrxContext.setLimitProfile(limitProfile);
			ILimitTrxValue limitTrxProfile = limitProxy.getTrxLimitByLimitProfile(theOBTrxContext, limitProfile);
			limitTrxProfile.setBcaList(bcaList);
			result.put("limitTrxProfile", limitTrxProfile);
			map.put("event", map.get("event"));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}
