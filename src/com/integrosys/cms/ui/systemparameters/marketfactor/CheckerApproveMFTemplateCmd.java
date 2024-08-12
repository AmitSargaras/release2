/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckerApproveMFTemplateCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IMFTemplateTrxValue MFTemplateTrxObj = (IMFTemplateTrxValue) map.get("MFTemplateTrxObj");
			MFTemplateUIHelper helper = new MFTemplateUIHelper();

			IPrPaProxyManager proxy = helper.getIPrPaProxyManager();

			if (MFTemplateTrxObj.getStatus().equals(ICMSConstant.STATE_PENDING_DELETE)) {
				IMFTemplateTrxValue res = proxy.checkerApproveDeleteMFTemplate(ctx, MFTemplateTrxObj);
				result.put("request.ITrxValue", res);
			}
			else {
				IMFTemplateTrxValue res = proxy.checkerApproveUpdateMFTemplate(ctx, MFTemplateTrxObj);
				result.put("request.ITrxValue", res);

			}
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
