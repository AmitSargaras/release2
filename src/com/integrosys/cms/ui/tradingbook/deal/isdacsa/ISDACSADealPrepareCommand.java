/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.isdacsa;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the ISDA CSA Deal value to be view
 * or edit Description: command that get the value from database to let the user
 * to view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class ISDACSADealPrepareCommand extends AbstractCommand implements ICommonEventConstant {

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
				{ "InitialISDACSADealSummary", "com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary",
						FORM_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "agreementID", "java.lang.String", REQUEST_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																														// the
																														// length
																														// .
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "targetOffset", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "ISDACSADealSummaryTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBISDACSADealSummary",
						SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "InitialISDACSADealSummary", "java.util.list", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBISDACSADealSummary obISDACSADealSummary = (OBISDACSADealSummary) map.get("InitialISDACSADealSummary");

		String event = (String) map.get("event");
		String limitProfileID = (String) map.get("limitProfileID");
		String agreementID = (String) map.get("agreementID");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", agreementID=" + agreementID
				+ ", limitProfileID=" + limitProfileID);
		try {

			if (!(event.equals("maker_add_agreement_confirm_error") || event
					.equals("maker_update_agreement_confirm_error"))) {
				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();
				System.out.println("After proxy : " + proxy);

				IISDACSADealSummary isdaDealTrxVal = proxy.getISDACSADealSummaryByAgreement(Long
						.parseLong(limitProfileID), Long.parseLong(agreementID));
				IISDACSADealVal[] obISDACSADealVal = isdaDealTrxVal.getISDACSADealSummary();
				System.out.println("After isdaDealTrxVal : " + isdaDealTrxVal);

				resultMap.put("ISDACSADealSummaryTrxValue", isdaDealTrxVal);
				resultMap.put("InitialISDACSADealSummary", obISDACSADealVal);

				int length = 10;
				String target = (String) map.get("targetOffset");
				if (target != null) {
					int targetOffset = Integer.parseInt(target);
					targetOffset = ISDACSADealMapper.adjustOffset(targetOffset, length, obISDACSADealVal.length);
					resultMap.put("offset", new Integer(targetOffset));
				}
				else {
					resultMap.put("offset", new Integer(0));
				}

				resultMap.put("length", new Integer(10));
			}

			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		}
		catch (TradingBookException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
