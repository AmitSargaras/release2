/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/PaginateBondListCommand.java,v 1.3 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class PaginateApprovalMatrixCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ ApprovalMatrixForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
																		// the
																		// current
																		// feed
																		// entries
																		// to be
																		// saved
																		// as a
																		// whole
																		// .
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ ApprovalMatrixForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(ApprovalMatrixForm.MAPPER);

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IApprovalMatrixGroup inputGroup = (IApprovalMatrixGroup) inputList.get(1);
			IApprovalMatrixEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");
			IApprovalMatrixGroup group = value.getStagingApprovalMatrixGroup();
			IApprovalMatrixEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setRiskGrade(inputEntriesArr[i].getRiskGrade());
				// entriesArr[offset + i].setLastUpdatedDate(new Date());
				entriesArr[offset + i].setLevel1(inputEntriesArr[i].getLevel1());
				entriesArr[offset + i].setLevel2(inputEntriesArr[i].getLevel2());
				entriesArr[offset + i].setLevel3(inputEntriesArr[i].getLevel3());
				entriesArr[offset + i].setLevel4(inputEntriesArr[i].getLevel4());
			}

			// Sort the array.
			/*if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IApprovalMatrixEntry entry1 = (IApprovalMatrixEntry) a;
						IApprovalMatrixEntry entry2 = (IApprovalMatrixEntry) b;
						if (Integer.toString(entry1.getRiskGrade()) == null) {
							entry1.setRiskGrade(0);
						}
						if (Integer.toString(entry2.getRiskGrade()) == null) {
							entry2.setRiskGrade(0);
						}
						return entry1.getRiskGrade().compareTo(entry2.getRiskGrade());
					}
				});
			}*/

			group.setFeedEntries(entriesArr);
			value.setStagingApprovalMatrixGroup(group);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			targetOffset = ApprovalMatrixMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("approvalMatrixTrxValue", value);
			resultMap.put("request.ITrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(ApprovalMatrixForm.MAPPER, value);

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
