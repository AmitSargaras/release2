/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/ReadSecurityCommand.java,v 1.4 2006/05/22 08:45:12 jzhai Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;

/**
 * Description
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/05/22 08:45:12 $ Tag: $Name: $
 */

public class ReadSecurityCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral", FORM_SCOPE },
				{ "stageSecurity", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						REQUEST_SCOPE },
				{ "actualSecurity", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						REQUEST_SCOPE },
		// {"serviceSecurity",
		// "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral"
		// , SERVICE_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String indexID = (String) map.get("indexID");
		long index = Long.parseLong(indexID);
		String from_event = (String) map.get("from_page");
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral objSec = null;
		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PROCESS)) {
			ICommodityCollateral[] colArr = (ICommodityCollateral[]) trxValueMap.get("actual");
			ICommodityCollateral actualObj = getItem(colArr, indexID);
			colArr = (ICommodityCollateral[]) trxValueMap.get("staging");
			ICommodityCollateral stageObj = getItem(colArr, indexID);
			result.put("actualSecurity", actualObj);
			result.put("stageSecurity", stageObj);
			objSec = stageObj;
			if (objSec == null) {
				objSec = actualObj;
			}
		}
		else if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PREPARE_UPDATE_SUB)) {
			objSec = (ICommodityCollateral) map.get("commSecObj");
		}
		else {
			ICommodityCollateral[] col;
			if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_READ)) {
				col = (ICommodityCollateral[]) trxValueMap.get("actual");
			}
			else {
				col = (ICommodityCollateral[]) trxValueMap.get("staging");
			}
			objSec = col[(int) index];
		}

		result.put("from_page", from_event);
		result.put("commSecObj", objSec);
		// result.put("serviceSecurity", objSec);
		DefaultLogger.debug(this, "at read security command : objsec: " + objSec);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ICommodityCollateral getItem(ICommodityCollateral temp[], String securityID) {
		ICommodityCollateral item = null;
		if (temp == null) {
			return item;
		}

		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getSCISecurityID().equals(securityID)) {
				item = temp[i];
			}
			else {
				continue;
			}
		}

		return item;
	}
}
