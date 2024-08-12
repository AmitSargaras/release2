/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/InterestRateMakerReadRejectedCmd.java,v 1 2007/02/09 Jerlin Exp $
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
import com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy;
import com.integrosys.cms.app.interestrate.proxy.InterestRateProxyFactory;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to reedit the interest rate value
 * after rejected by checker Description: command that get the value that being
 * rejected by checker from database to let the user to reedit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class InterestRateMakerReadRejectedCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] {
				{ "InterestRateTrxValue", "com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue",
						SERVICE_SCOPE }, { "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "InitialInterestRate", "java.util.list", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value where checker rejected
	 * from database for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			IInterestRateProxy proxy = InterestRateProxyFactory.getProxy();
			// Get Trx By TrxID
			IInterestRateTrxValue interestRateTrxVal = proxy.getInterestRateTrxValueByTrxID(trxContext, trxId);

			// if current status is other than ACTIVE & REJECTED, then show
			// workInProgress.
			// i.e. allow edit only if status is either ACTIVE or REJECTED
			if ((!interestRateTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
					&& (!interestRateTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED))

			) {
				resultMap.put("wip", "wip");
				resultMap.put("InitialInterestRate", interestRateTrxVal.getStagingInterestRates());
			}
			else {
				resultMap.put("InterestRateTrxValue", interestRateTrxVal);
			}

			resultMap.put("timefrequency.labels", CommonDataSingleton
					.getCodeCategoryLabels(MaintainInterestRateHelper.TIME_FREQUENCY_CODE));
			resultMap.put("timefrequency.values", CommonDataSingleton
					.getCodeCategoryValues(MaintainInterestRateHelper.TIME_FREQUENCY_CODE));
			resultMap.put("InitialInterestRate", interestRateTrxVal.getStagingInterestRates());

			// set countrie map
			resultMap.put("countries.map", MaintainInterestRateHelper.buildCountryeMap());

			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", MaintainInterestRateHelper.buildTimeFrequencyMap());

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
