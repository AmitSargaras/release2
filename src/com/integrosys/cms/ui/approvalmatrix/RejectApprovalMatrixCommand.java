/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/RejectBondListCommand.java,v 1.7 2003/09/22 04:51:48 btchng Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.approvalmatrix.proxy.IApprovalMatrixProxy;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/22 04:51:48 $ Tag: $Name: $
 */
public class RejectApprovalMatrixCommand extends AbstractCommand {
	
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } ,
				{ "remarks", "java.lang.String", REQUEST_SCOPE} };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) map.get("approvalMatrixTrxValue");
			
			String remarks = (String) map.get("remarks");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if(remarks == null || remarks.equals("")){
            	exceptionMap.put("remarks", new ActionMessage("error.reject.remark"));
            	IApprovalMatrixTrxValue approvalMatrixTrxValue = null;
            	resultMap.put("approvalMatrixTrxValue", approvalMatrixTrxValue);
            	resultMap.put("request.ITrxValue", approvalMatrixTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
            }else{
            	value = getApprovalMatrixProxy().checkerRejectApprovalMatrixGroup(trxContext, value);

            	resultMap.put("request.ITrxValue", value);
            }	

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
