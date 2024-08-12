/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealValTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the GMRA Deal Valaution value to be
 * view or edit Description: command that get the value from database to let the
 * user to view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class GMRADealPrepareValuationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialGMRADealVal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "agreementID", "java.lang.String", REQUEST_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
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
				{ "GMRADealValTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealValTrxValue", SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.lang.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ "InitialGMRADealVal", "java.util.Object", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBGMRADealDetail obGMRADealDetail = (OBGMRADealDetail) map.get("InitialGMRADealVal");

		String event = (String) map.get("event");
		String agreementID = (String) map.get("agreementID");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", agreementID=" + agreementID + ", LEID="
				+ obGMRADealDetail.getLEID());
		try {

			if (!(event.equals("maker_update_valuation_confirm_error") || event
					.equals("maker_edit_reject_confirm_error"))) {
				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

				IGMRADealValTrxValue gmraDealValTrxVal = proxy.getGMRADealValuationTrxValue(trxContext, Long
						.parseLong(agreementID));

				if (!((gmraDealValTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (gmraDealValTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))
						&& !event.equals("view")) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialGMRADealVal", gmraDealValTrxVal.getStagingGMRADealValuation());

				}
				else {
					resultMap.put("GMRADealValTrxValue", gmraDealValTrxVal);
				}

				resultMap.put("InitialGMRADealVal", gmraDealValTrxVal.getGMRADealValuation());
				System.out.println("GMRADealPrepareValuationCmd gmraDealValTrxVal.getGMRADealValuation() : "
						+ gmraDealValTrxVal.getGMRADealValuation());
				if (event.equals("view")) {
					int offset = 0;
					System.out.println("(Integer) map.get(offset) : " + (Integer) map.get("offset"));
					if ((Integer) map.get("offset") != null) {
						offset = ((Integer) map.get("offset")).intValue();
					}
					int length = 10;
					int targetOffset = 0;
					if (map.get("targetOffset") != null) {
						targetOffset = Integer.parseInt((String) map.get("targetOffset"));
					}
					IGMRADealVal[] valuationArr = gmraDealValTrxVal.getGMRADealValuation();

					targetOffset = GMRADealValMapper.adjustOffset(targetOffset, length, valuationArr.length);
					resultMap.put("offset", new Integer(targetOffset));
					resultMap.put("length", new Integer(10));

				}
				else {
					resultMap.put("offset", new Integer(0));
					resultMap.put("length", new Integer(10));
				}
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
