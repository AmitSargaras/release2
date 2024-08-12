/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/CloseBondListCommand.java,v 1.8 2005/01/12 06:39:12 hshii Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.proxy.IApprovalMatrixProxy;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/01/12 06:39:12 $ Tag: $Name: $
 */
public class CloseApprovalMatrixCommand extends AbstractCommand {
	
	private IApprovalMatrixProxy approvalMatrixProxy;

	/**
	 * @return the approvalMatrixProxy
	 */
	public IApprovalMatrixProxy getApprovalMatrixProxy() {
		return approvalMatrixProxy;
	}

	/**
	 * @param approvalMatrixProxy the approvalMatrixProxy to set
	 */
	public void setApprovalMatrixProxy(IApprovalMatrixProxy approvalMatrixProxy) {
		this.approvalMatrixProxy = approvalMatrixProxy;
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "approvalMatrixTrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IApprovalMatrixGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");

			// String remarks = (String)map.get(BondListForm.MAPPER);
			// value.setRemarks(remarks);

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			if (ICMSConstant.STATE_DRAFT.equals(value.getStatus())) {
				value = getApprovalMatrixProxy().makerCloseDraftApprovalMatrixGroup(trxContext, value);
			}
			else {
				value = getApprovalMatrixProxy().makerCloseRejectedApprovalMatrixGroup(trxContext, value);
			}

			resultMap.put("request.ITrxValue", value);

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
