package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * SubmitCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class SubmitCreditRiskParamUnitTrustCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ CreditRiskParamUnitTrustForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
																						// the
																						// current
																						// feed
																						// entries
																						// to
																						// be
																						// saved
																						// as
																						// a
																						// whole
																						// .
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } };

	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", REQUEST_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(CreditRiskParamUnitTrustForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(1));
			// Element at index 2 is the target offset which is not required
			// here.
			ICreditRiskParamGroup inputGroup = (ICreditRiskParamGroup) inputList.get(1);
			OBCreditRiskParam[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			// trxContext.setCustomer(null);
			// trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) map
					.get("creditRiskParamGroupTrxValue");
			ICreditRiskParamGroup group = value.getStagingCreditRiskParamGroup();
			OBCreditRiskParam[] entriesArr = group.getFeedEntries();

			// Update using the input fields.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					entriesArr[offset + i].setMarginOfAdvance(inputEntriesArr[i].getMarginOfAdvance());

					entriesArr[offset + i].setMaximumCap(inputEntriesArr[i].getMaximumCap());

					entriesArr[offset + i].setIsIntSuspend(inputEntriesArr[i].getIsIntSuspend());

					entriesArr[offset + i].setIsAcceptable(inputEntriesArr[i].getIsAcceptable());
				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingCreditRiskParamGroup(group);

			ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();

			ICreditRiskParamGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				String remarks = (String) map.get("remarks");
				value.setRemarks(remarks);

				resultValue = (ICreditRiskParamGroupTrxValue) proxy.makerUpdateRejectedCreditRiskParam(trxContext,
						value, value.getStagingCreditRiskParamGroup(), CreditRiskParamType.UNIT_TRUST);
			}
			else {
				resultValue = (ICreditRiskParamGroupTrxValue) proxy.makerUpdateCreditRiskParam(trxContext, value, value
						.getStagingCreditRiskParamGroup(), CreditRiskParamType.UNIT_TRUST);
			}

			resultMap.put("request.ITrxValue", resultValue);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
