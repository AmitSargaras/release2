/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/InterestRateMakerCancelEditCmd.java,v 1 2007/02/09 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy;
import com.integrosys.cms.app.interestrate.proxy.InterestRateProxyFactory;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to close the transaction Description:
 * command that let the maker to close the transaction that being make
 * previously
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class InterestRateMakerCancelEditCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InterestRateTrxValue", "com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue",
						SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here close transaction for Interest Rate is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IInterestRateTrxValue interestRateTrxVal = (IInterestRateTrxValue) map.get("InterestRateTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxContext);

		try {

			IInterestRateProxy proxy = InterestRateProxyFactory.getProxy();
			interestRateTrxVal = proxy.makerCancelUpdateInterestRate(trxContext, interestRateTrxVal);
			resultMap.put("request.ITrxValue", interestRateTrxVal);

		}
		catch (InterestRateException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
