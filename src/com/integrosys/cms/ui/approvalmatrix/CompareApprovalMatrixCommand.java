/*
 * Copyright Integro Technologies Pte Ltd
 * $header$
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/28 08:43:23 $ Tag: $Name: $
 */
public class CompareApprovalMatrixCommand extends AbstractCommand {

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

		IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		IApprovalMatrixGroup actualFeedGroup = value.getApprovalMatrixGroup();
		IApprovalMatrixGroup stagingFeedGroup = value.getStagingApprovalMatrixGroup();

		if ((actualFeedGroup != null) && (actualFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "actual:");
			for (int i = 0; i < actualFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getRiskGrade()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getApprovalMatrixEntryRef()));
			}
		}
		if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "staging:");
			for (int i = 0; i < stagingFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getRiskGrade()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getApprovalMatrixEntryRef()));
			}
		}

		try {
			List compareResultsList = CompareOBUtil.compOBArray(stagingFeedGroup.getFeedEntries(), actualFeedGroup
					.getFeedEntries());

			offset = ApprovalMatrixMapper.adjustOffset(offset, length, compareResultsList.size());
			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("approvalMatrixTrxValue", value);
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

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
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
		return new String[][] {// Produce the comparision results list.
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
																			// the
																			// trx
																			// value
																			// nevertheless
																			// .
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}
}
