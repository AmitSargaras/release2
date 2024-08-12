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
import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the GMRA Deal value to be view or
 * edit Description: command that get the value from database to let the user to
 * view or edit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class GMRADealPrepareReadCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialGMRADeal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADealDetail", FORM_SCOPE },
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
				{ "CPAgreementDetail", "com.integrosys.cms.app.tradingbook.bus.OBCPAgreementDetail", SERVICE_SCOPE },
				{ "dealBranchListID", "java.util.Collection", REQUEST_SCOPE },
				{ "dealBranchListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialGMRADeal", "java.util.list", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBGMRADealDetail obGMRADealDetail = (OBGMRADealDetail) map.get("InitialGMRADeal");

		String event = (String) map.get("event");
		try {

			if (!(event.equals("maker_add_agreement_confirm_error") || event
					.equals("maker_update_agreement_confirm_error"))) {
				ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();

				ICPAgreementDetail cpAgreementDetail = proxy.getCounterPartyAgreementDetail(obGMRADealDetail
						.getAgreementID());

				resultMap.put("CPAgreementDetail", cpAgreementDetail);
				resultMap.put("InitialGMRADealSummary", cpAgreementDetail);

				if ("refresh_maker_add_deal".equals(event) || "maker_add_deal_confirm_error".equals(event)) {

					CommonCodeList commonCode = CommonCodeList.getInstance(obGMRADealDetail.getGMRADealDetail()
							.getDealCountry(), ICMSConstant.CATEGORY_CODE_BKGLOC, true);
					resultMap.put("dealBranchListID", commonCode.getCommonCodeValues());
					resultMap.put("dealBranchListValue", commonCode.getCommonCodeLabels());

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
