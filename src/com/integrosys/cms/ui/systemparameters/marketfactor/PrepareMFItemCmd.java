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
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBMFItem;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareMFItemCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "curItem", "com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			MFTemplateUIHelper helper = new MFTemplateUIHelper();
			String event = (String) (map.get("event"));
			String from_event = (String) (map.get("fromEvent"));

			IMFTemplateTrxValue MFTemplateTrxObj = (IMFTemplateTrxValue) map.get("MFTemplateTrxObj");
			IMFItem curItem = null;
			if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
				curItem = new OBMFItem();
			}
			else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
				int index = Integer.parseInt((String) map.get("indexID"));
				curItem = helper.getCurWorkingMFItem(event, from_event, index, MFTemplateTrxObj);
			}
			result.put("curItem", curItem);

		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
