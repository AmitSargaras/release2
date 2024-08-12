/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ListBondListCommand.java,v 1.2 2003/08/11 12:06:50 btchng Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/11 12:06:50 $ Tag: $Name: $
 */
public class ListApprovalMatrixCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE }, // To
																													// populate
																													// the
																													// form
																													// .
				{ ApprovalMatrixForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IApprovalMatrixTrxValue trxValue = (IApprovalMatrixTrxValue) hashMap.get("approvalMatrixTrxValue");

		resultMap.put("approvalMatrixTrxValue", trxValue);
		resultMap.put(ApprovalMatrixForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
