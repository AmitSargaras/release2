/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/InterestRateListCommand.java,v 1 2007/02/08 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.bus.OBInterestRate;
import com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy;
import com.integrosys.cms.app.interestrate.proxy.InterestRateProxyFactory;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for maker to read the value Description:
 * command that let the maker to read the value that want to be modify
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class InterestRateListCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialInterestRate", "com.integrosys.cms.app.interestrate.bus.OBInterestRate", FORM_SCOPE },
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
				{ "InterestRateTrxValue", "com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue",
						SERVICE_SCOPE }, { "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialInterestRate", "java.util.list", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		// System.out.println("after trxContext: "+trxContext);
		OBInterestRate obInterestRate = (OBInterestRate) map.get("InitialInterestRate");
		// System.out.println("after obInterestRate: "+obInterestRate);

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", interestRateID="
				+ obInterestRate.getIntRateID());
		try {

			if (!(event.equals("maker_edit_interesetrate_confirm_error") || event.equals("interestrate_list_error"))) {
				IInterestRateProxy proxy = InterestRateProxyFactory.getProxy();

				IInterestRateTrxValue interestRateTrxVal = proxy.getInterestRateTrxValue(trxContext, obInterestRate
						.getIntRateType(), obInterestRate.getIntRateDate());
				//System.out.println("interestRateTrxVal: "+interestRateTrxVal);

				if (MaintainInterestRateAction.EVENT_VIEW.equals(event) || "checker_view".equals(event)) {
					resultMap.put("InterestRateTrxValue", interestRateTrxVal);
					resultMap.put("InitialInterestRate", interestRateTrxVal.getInterestRates());

				}
				else {
					// if current status is other than ACTIVE & REJECTED, then
					// show workInProgress.
					// i.e. allow edit only if status is either ACTIVE or
					// REJECTED
					if (!((interestRateTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (interestRateTrxVal
							.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
						//System.out.println("************** wip *************")
						// ;
						resultMap.put("wip", "wip");
						resultMap.put("InitialInterestRate", interestRateTrxVal.getStagingInterestRates());

					}
					else {
						resultMap.put("InterestRateTrxValue", interestRateTrxVal);
					}

					resultMap.put("InitialInterestRate", interestRateTrxVal.getInterestRates());
				}
			}

			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

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
