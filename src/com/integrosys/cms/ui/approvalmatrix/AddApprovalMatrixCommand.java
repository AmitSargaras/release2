/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/AddBondListCommand.java,v 1.4 2004/03/29 10:45:20 sathish Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;

/**
 * @author $Author: sathish $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/03/29 10:45:20 $ Tag: $Name%
 */
public class AddApprovalMatrixCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ ApprovalMatrixForm.MAPPER, "com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IApprovalMatrixGroup inputFeedGroup = (IApprovalMatrixGroup) map.get(ApprovalMatrixForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IApprovalMatrixEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");
			IApprovalMatrixGroup group = value.getStagingApprovalMatrixGroup();
			IApprovalMatrixEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getRiskGrade() == entriesArr[j].getRiskGrade()) {
							entriesArr[j].setLevel1(inputEntriesArr[i].getLevel1());
							entriesArr[j].setLevel2(inputEntriesArr[i].getLevel2());
							entriesArr[j].setLevel3(inputEntriesArr[i].getLevel3());
							entriesArr[j].setLevel4(inputEntriesArr[i].getLevel4());
						}
					}

				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingApprovalMatrixGroup(group);

			// resultMap.put("bondFeedGroupTrxValue", value);
			// resultMap.put(BondListForm.MAPPER, value);

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
