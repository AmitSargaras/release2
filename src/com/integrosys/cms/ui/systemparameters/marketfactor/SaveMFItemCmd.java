/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveMFItemCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "MFItemForm", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String event = (String) (map.get("event"));
			String from_event = (String) map.get("fromEvent");
			IMFTemplateTrxValue MFTemplateTrxObj = (IMFTemplateTrxValue) map.get("MFTemplateTrxObj");
			IMFTemplate curMFTemplate = MFTemplateTrxObj.getStagingMFTemplate();
			DefaultLogger.debug(this, "Event : " + event);
			IMFItem item = (IMFItem) (map.get("MFItemForm"));
			// DefaultLogger.debug(this,"SaveMFItemCmd,item : "+item);

			boolean isCreate = EventConstant.EVENT_CREATE.equals(event);
			IMFItem[] itemList = curMFTemplate.getMFItemList();

			if (isCreate) {

				int arrayLength = (itemList == null ? 0 : itemList.length);
				IMFItem[] newArray = new IMFItem[arrayLength + 1];
				if (arrayLength != 0) {
					System.arraycopy(itemList, 0, newArray, 0, arrayLength);
				}

				newArray[arrayLength] = item;
				itemList = newArray;
				curMFTemplate.setMFItemList(itemList);
			}

			// DefaultLogger.debug(this,"SaveMFItemCmd,curMFTemplate : "+
			// curMFTemplate);
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
