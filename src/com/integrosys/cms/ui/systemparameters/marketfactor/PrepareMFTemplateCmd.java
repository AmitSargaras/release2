/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareMFTemplateCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, { "isCreate", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "collateralSubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "MFTemplateForm", "java.lang.Object", FORM_SCOPE },
				{ "returnUrl", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String event = (String) (map.get("event"));
			String isCreate = (String) (map.get("isCreate"));
			MFTemplateUIHelper helper = new MFTemplateUIHelper();
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			IMFTemplateTrxValue trxValue = (IMFTemplateTrxValue) (map.get("MFTemplateTrxObj"));
			IMFTemplate curTemplate = this.getMFTemplate(trxValue, event);

			result.put("MFTemplateForm", curTemplate);

			String returnUrl = getReturnUrl(trxValue, isCreate);
			result.put("returnUrl", returnUrl);

			ArrayList collateralSubTypeFullList = new ArrayList();
			ArrayList collateralSubTypeFullListLabel = new ArrayList();

			helper.setSecuritySubTypeToList(collateralSubTypeFullList, collateralSubTypeFullListLabel, locale);

			// DefaultLogger.debug(this, "collateralSubTypeFullList : " +
			// collateralSubTypeFullList);
			// DefaultLogger.debug(this, "collateralSubTypeFullListLabel : " +
			// collateralSubTypeFullListLabel);

			result.put("collateralSubTypeFullList", collateralSubTypeFullList);
			result.put("collateralSubTypeFullListLabel", collateralSubTypeFullListLabel);

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private String getReturnUrl(IMFTemplateTrxValue trxValue, String isCreate) {
		if (ICMSConstant.STATE_DRAFT.equals(trxValue.getStatus())
				|| ICMSConstant.STATE_REJECTED.equals(trxValue.getStatus())
				|| ICMSConstant.STATE_REJECTED_CREATE.equals(trxValue.getStatus())
				|| ICMSConstant.STATE_REJECTED_DELETE.equals(trxValue.getStatus())) {
			String returnUrl = "ToDo.do";
			return returnUrl;
		}
		else {
			String returnUrl = "";

			if ("Y".equals(isCreate)) {
				returnUrl = "MaintainMFTemplate.do?event=list";
			}
			else {
				String templateId = "" + trxValue.getMFTemplate().getMFTemplateID();
				returnUrl = "MaintainMFTemplate.do?event=read&" + "MFTemplateID=" + templateId;
			}
			return returnUrl;
		}
	}

	private IMFTemplate getMFTemplate(IMFTemplateTrxValue trxValue, String event) {
		if (trxValue == null) {
			return new OBMFTemplate();
		}
		if (EventConstant.EVENT_READ.equals(event)) {
			return trxValue.getMFTemplate();
		}
		else {
			IMFTemplate curTemplate = trxValue.getStagingMFTemplate();
			if (curTemplate == null) {
				curTemplate = new OBMFTemplate();
			}
			return curTemplate;
		}
	}
}
