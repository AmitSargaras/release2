/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/RedirectCollateralCommand.java,v 1.5 2006/07/19 11:11:27 wltan Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/07/19 11:11:27 $ Tag: $Name: $
 */
public class RedirectCollateralCommand extends AbstractCommand {
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Gets the security sub-type ID for the
	 * required transaction to find out which action to forward to.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		String userName=(String)map.get("userName");
		String trxIDStr = (String) map.get("trxID");
		String event = (String) map.get("event");
		String idStrVal = (String) map.get("collateralID");
		String[] returnStr = null;
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String roleType = (String) map.get(IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID);
			
		try {
			long trxID = Long.parseLong(trxIDStr.trim());
			returnStr = CollateralProxyFactory.getProxy().getSecuritySubTypeForTrxByTrxID(trxID);
		}
		catch (CollateralException e) {
			e.printStackTrace();
			exceptionMap.put("collateral.errror", new ActionMessage("collateral.error"));
		}

		if (returnStr != null) {
			DefaultLogger.debug(this, "subtypeCode: " + returnStr[0]);
			DefaultLogger.debug(this, "trxSubType: " + returnStr[1]);
			resultMap.put("subtypeCode", returnStr[0]);
			resultMap.put("trxSubType", returnStr[1]);
		}
		
		
		
		else{
			resultMap.put("event", event);
		}
		resultMap.put("collateralID", idStrVal);
		resultMap.put("trxID", trxIDStr);
		resultMap.put("serviceColObj", itrxValue);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "leName", "java.lang.String", REQUEST_SCOPE },
						
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				                 { "event", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "subtypeCode", "java.lang.String", REQUEST_SCOPE },
				{"trxID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "leName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },});
	}

}
