/*
Copyright Integro Technologies Pte Ltd
$Header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * PaginateCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class PaginateCreditRiskParamUnitTrustCommand extends AbstractCommand {

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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ CreditRiskParamUnitTrustForm.MAPPER,
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue",
						REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(CreditRiskParamUnitTrustForm.MAPPER);

			// The below offset is the first record of the current range to be
			// saved.

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
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

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setMarginOfAdvance(inputEntriesArr[i].getMarginOfAdvance());

				entriesArr[offset + i].setMaximumCap(inputEntriesArr[i].getMaximumCap());

				entriesArr[offset + i].setIsIntSuspend(inputEntriesArr[i].getIsIntSuspend());

				entriesArr[offset + i].setIsAcceptable(inputEntriesArr[i].getIsAcceptable());
			}

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						OBCreditRiskParam entry1 = (OBCreditRiskParam) a;
						OBCreditRiskParam entry2 = (OBCreditRiskParam) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			}

			group.setFeedEntries(entriesArr);
			value.setStagingCreditRiskParamGroup(group);

			targetOffset = CreditRiskParamUnitTrustMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("request.ITrxValue", value);
			resultMap.put("creditRiskParamGroupTrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(CreditRiskParamUnitTrustForm.MAPPER, value);

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
