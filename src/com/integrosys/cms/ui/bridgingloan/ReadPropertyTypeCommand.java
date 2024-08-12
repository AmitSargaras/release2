package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IPropertyType;
import com.integrosys.cms.app.bridgingloan.bus.OBPropertyType;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 23, 2007 Time: 9:45:09 AM To
 * change this template use File | Settings | File Templates.
 */
public class ReadPropertyTypeCommand extends AbstractCommand {

	public ReadPropertyTypeCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering ReadPropertyTypeCommand()");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "commonRef", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "objStagingPropertyType", "com.integrosys.cms.app.bridgingloan.bus.OBPropertyType", REQUEST_SCOPE },
				{ "objActualPropertyType", "com.integrosys.cms.app.bridgingloan.bus.OBPropertyType", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside ReadPropertyTypeCommand doExecute()");

		try {
			IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
			DefaultLogger.debug(this, "trxValue=" + trxValue);

			IPropertyType[] propertyTypeList = trxValue.getStagingBridgingLoan().getPropertyTypeList();
			DefaultLogger.debug(this, "propertyTypeList=" + propertyTypeList);
			IPropertyType[] propertyTypeActualList = null;
			if (trxValue.getBridgingLoan() != null) { // actual will be null if
														// this is new record
				propertyTypeActualList = trxValue.getBridgingLoan().getPropertyTypeList();
			}

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event=" + event);
			if (PropertyTypeAction.EVENT_VIEW.equals(event)
					|| PropertyTypeAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
				DefaultLogger.debug(this, "objBridgingLoan=" + objBridgingLoan);
				resultMap.put("objBridgingLoan", objBridgingLoan);
			}
			else {
				OBPropertyType objStagingPropertyType = null;
				OBPropertyType objActualPropertyType = null;
				String commonRef = (String) map.get("commonRef");

				DefaultLogger.debug(this, "propertyTypeList=" + propertyTypeList);
				for (int i = 0; i < propertyTypeList.length; i++) {
					if (propertyTypeList[i].getCommonRef() == Long.parseLong(commonRef)) {
						objStagingPropertyType = (OBPropertyType) propertyTypeList[i];
					}
				}
				if (propertyTypeActualList != null) {
					DefaultLogger.debug(this, "propertyTypeActualList=" + propertyTypeActualList);
					for (int i = 0; i < propertyTypeActualList.length; i++) {
						if (propertyTypeActualList[i].getCommonRef() == Long.parseLong(commonRef)) {
							objActualPropertyType = (OBPropertyType) propertyTypeActualList[i];
						}
					}
				}
				resultMap.put("objStagingPropertyType", objStagingPropertyType); // staging
																					// data
				resultMap.put("objActualPropertyType", objActualPropertyType); // actual
																				// data
			}
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}