/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MakerCreateMFTemplateCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "MFTemplateForm", "com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate",
						FORM_SCOPE }, });

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
			IMFTemplate template = (IMFTemplate) (map.get("MFTemplateForm"));
			MFTemplateUIHelper helper = new MFTemplateUIHelper();

			IPrPaProxyManager proxy = helper.getIPrPaProxyManager();

			if (!((MFTemplateTrxObj != null) && (MFTemplateTrxObj.getStagingMFTemplate() != null)
					&& (MFTemplateTrxObj.getStagingMFTemplate().getMFItemList() != null) && (MFTemplateTrxObj
					.getStagingMFTemplate().getMFItemList().length > 0))) {
				exceptionMap.put("itemEmptyError", new ActionMessage("error.mftemplate.itemReq"));
			}
			if (exceptionMap.size() == 0) {
				template.setMFItemList(MFTemplateTrxObj.getStagingMFTemplate().getMFItemList());
				IMFTemplateTrxValue res = proxy.makerCreateMFTemplate(ctx, MFTemplateTrxObj, template);
				result.put("request.ITrxValue", res);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
