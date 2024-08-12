/*
 * Copyright Integro Technologies Pte Ltd
 * $header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;

/**
 * CompareCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CompareCreditRiskParamUnitTrustCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
																			// the
																			// comparision
																			// results
																			// list
																			// .
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE }, // Produce
																														// the
																														// trx
																														// value
																														// nevertheless
																														// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) map.get("creditRiskParamGroupTrxValue");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		ICreditRiskParamGroup actualFeedGroup = value.getCreditRiskParamGroup();
		ICreditRiskParamGroup stagingFeedGroup = value.getStagingCreditRiskParamGroup();

		if ((actualFeedGroup != null) && (actualFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "actual:");
			for (int i = 0; i < actualFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "Margin Of Advance " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getMarginOfAdvance()));
				DefaultLogger.debug(this, "Maximum Cap " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getMaximumCap()));
				DefaultLogger.debug(this, "Is Int Suspend " + i + " = "
						+ actualFeedGroup.getFeedEntries()[i].getIsIntSuspend());

				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getCreditRiskParamEntryRef()));
			}
		}
		if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "staging:");
			for (int i = 0; i < stagingFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "Margin Of Advance " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getMarginOfAdvance()));
				DefaultLogger.debug(this, "Maximum Cap " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getMaximumCap()));
				DefaultLogger.debug(this, "Is Int Suspend " + i + " = "
						+ stagingFeedGroup.getFeedEntries()[i].getIsIntSuspend());
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getCreditRiskParamEntryRef()));
			}
		}
		try {
			List compareResultsList = CompareOBUtil.compOBArray(stagingFeedGroup.getFeedEntries(), actualFeedGroup
					.getFeedEntries());

			offset = CreditRiskParamUnitTrustMapper.adjustOffset(offset, length, compareResultsList.size());
			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("creditRiskParamGroupTrxValue", value);
			resultMap.put("offset", new Integer(offset));

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
