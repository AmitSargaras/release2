/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/InterestRateMakerEditCmd.java,v 1 2007/02/09 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.bus.OBInterestRate;
import com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy;
import com.integrosys.cms.app.interestrate.proxy.InterestRateProxyFactory;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to edit the interest rate percentage
 * Description: command that let the maker to save the interest rate percentage
 * that being edited to the database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class InterestRateMakerEditCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InterestRates", "java.util.Collection", FORM_SCOPE }, // Collection
																			// of
																			// com
																			// .
																			// integrosys
																			// .
																			// cms
																			// .
																			// app
																			// .
																			// interestrate
																			// .
																			// bus
																			// .
																			// OBInterestRate
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		Collection paramsCol = (Collection) map.get("InterestRates");
		OBInterestRate[] obInterestRates = null;
		obInterestRates = new OBInterestRate[paramsCol.size()];
		obInterestRates = (OBInterestRate[]) paramsCol.toArray(obInterestRates);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IInterestRateTrxValue interestRateTrxVal = (IInterestRateTrxValue) map.get("InterestRateTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obInterestRates' = "
				+ obInterestRates);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {
			IInterestRateProxy proxy = InterestRateProxyFactory.getProxy();

			for (int i = 0; i < obInterestRates.length; i++) {
				OBInterestRate obInterestRate = obInterestRates[i];
				DefaultLogger.debug(this, ">>>Debug:::1014 InterestRateTrxVal = "
						+ AccessorUtil.printMethodValue(obInterestRate));
			}

			IInterestRateTrxValue trxValue = proxy.makerUpdateInterestRate(trxContext, interestRateTrxVal,
					obInterestRates);
			resultMap.put("request.ITrxValue", trxValue);
			resultMap.put("InterestRateTrxValue", trxValue);

		}
		catch (InterestRateException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
