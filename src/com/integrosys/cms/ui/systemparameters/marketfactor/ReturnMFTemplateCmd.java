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
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReturnMFTemplateCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateForm", "java.lang.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			IMFTemplateTrxValue MFTemplateTrxValue = (IMFTemplateTrxValue) (map.get("MFTemplateTrxObj"));
			String fromEvent = (String) map.get("fromEvent");
			// for read event render form from original object
			// otherwise reder form from staging object
			IMFTemplate curTemplate = null;

			if (EventConstant.EVENT_READ.equals(fromEvent)) {
				curTemplate = MFTemplateTrxValue.getMFTemplate();
			}
			else {
				curTemplate = MFTemplateTrxValue.getStagingMFTemplate();
			}
			result.put("MFTemplateForm", curTemplate);
			if ((fromEvent != null) || !fromEvent.trim().equals("")) {
				result.put("fromEvent", fromEvent);
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
