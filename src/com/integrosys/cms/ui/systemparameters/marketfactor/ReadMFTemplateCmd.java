/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBMFTemplate;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadMFTemplateCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "MFTemplateID", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "MFTemplateTrxObj", "com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue",
						SERVICE_SCOPE }, { "MFTemplateForm", "java.lang.Object", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "collateralSubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			MFTemplateUIHelper helper = new MFTemplateUIHelper();
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			IPrPaProxyManager proxy = helper.getIPrPaProxyManager();
			IMFTemplateTrxValue MFTemplateTrxObj = null;

			String trxID = (String) (map.get("trxID"));
			String templateId = (String) (map.get("MFTemplateID"));
			if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event)
					|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PROCESS.equals(event)
					|| EventConstant.EVENT_PROCESS_DELETE.equals(event)) {
				DefaultLogger.debug(this, "********** Trasanction ID IS: " + trxID);
				MFTemplateTrxObj = proxy.getMFTemplateTrxValueByTrxID(ctx, trxID);
				result.put("fromEvent", "todo");
			}
			else {
				DefaultLogger.debug(this, "********** Template ID IS: " + templateId);
				MFTemplateTrxObj = proxy.getMFTemplateTrxValue(ctx, Long.parseLong(templateId));
			}

			result.put("MFTemplateTrxObj", MFTemplateTrxObj);
			// DefaultLogger.debug(this, "********** MFTemplateTrxObj IS: " +
			// MFTemplateTrxObj);

			if (CommonUtil.checkWip(event, MFTemplateTrxObj)) {
				result.put("wip", "wip");
			}
			if (CommonUtil.checkDeleteWip(event, MFTemplateTrxObj)) {
				result.put("wip", "wip");
			}

			// for read event render form from original object
			// otherwise reder form from staging object
			// for maker edit limit detail, we need to copy original object to
			// staging
			if (EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
				OBMFTemplate stgTemplate = new OBMFTemplate(MFTemplateTrxObj.getMFTemplate());
				MFTemplateTrxObj.setStagingMFTemplate(stgTemplate);

			}
			IMFTemplate curTemplate = null;
			if (EventConstant.EVENT_READ.equals(event)) {
				curTemplate = MFTemplateTrxObj.getMFTemplate();
			}
			else {
				curTemplate = MFTemplateTrxObj.getStagingMFTemplate();
			}

			result.put("MFTemplateForm", curTemplate);

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
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
