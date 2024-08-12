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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealDetail;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IISDACSADealValTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the ISDA CSA Deal Valuation value to
 * be view or edit Description: command that get the value from database to let
 * the user to view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class ISDACSADealPrepareValuationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialISDACSADeal", "com.integrosys.cms.app.tradingbook.bus.OBISDACSADealDetail", FORM_SCOPE },
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

		return (new String[][] {
				{ "ISDACSADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBISDACSADealValTrxValue",
						SERVICE_SCOPE }, { "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "InitialISDACSADeal", "java.util.Object", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBISDACSADealDetail obISDACSADealDetail = (OBISDACSADealDetail) map.get("InitialISDACSADeal");

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", agreementID="
				+ obISDACSADealDetail.getAgreementID() + ", LEID=" + obISDACSADealDetail.getLEID());
		try {

			if (!(event.equals("maker_add_agreement_confirm_error") || event
					.equals("maker_update_agreement_confirm_error"))) {
				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

				IISDACSADealValTrxValue isdaDealTrxVal = proxy.getISDACSADealValuationTrxValue(trxContext,
						obISDACSADealDetail.getAgreementID());

				if (!((isdaDealTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (isdaDealTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))
						&& !event.equals("view")) {
					// System.out.println("************** wip *************");
					resultMap.put("wip", "wip");
					resultMap.put("InitialISDACSADeal", isdaDealTrxVal.getStagingISDACSADealValuation());

				}
				else {
					resultMap.put("ISDACSADealTrxValue", isdaDealTrxVal);
				}

				resultMap.put("InitialISDACSADeal", isdaDealTrxVal.getISDACSADealValuation());

			}

			resultMap.put("offset", new Integer(0));
			resultMap.put("length", new Integer(10));
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
