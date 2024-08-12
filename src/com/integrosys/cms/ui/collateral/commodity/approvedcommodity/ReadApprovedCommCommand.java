/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/ReadApprovedCommCommand.java,v 1.4 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */

public class ReadApprovedCommCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
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
				{ "approvedCommObj", "java.util.HashMap", FORM_SCOPE },
				// {"serviceApprovedComm",
				// "com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType"
				// , SERVICE_SCOPE},
				{ "stageAppComm", "com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType",
						REQUEST_SCOPE },
				{ "actualAppComm", "com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType",
						REQUEST_SCOPE }, { "actualSecID", "java.lang.String", REQUEST_SCOPE },
				{ "stageSecID", "java.lang.String", REQUEST_SCOPE }, });
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

		long index = Long.parseLong((String) map.get("indexID"));

		String from_event = (String) map.get("from_page");
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		IApprovedCommodityType objSec = null;
		String securityID = "";
		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PROCESS)) {
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("actual");
			Object[] actual = getItem(col, index);
			IApprovedCommodityType actualObj = null;
			String actualSecID = "";
			if (actual != null) {
				actualObj = (IApprovedCommodityType) actual[0];
				actualSecID = (String) actual[1];
			}

			col = (ICommodityCollateral[]) trxValueMap.get("staging");
			Object[] stage = getItem(col, index);
			IApprovedCommodityType stageObj = null;
			String stageSecID = "";
			if (stage != null) {
				stageObj = (IApprovedCommodityType) stage[0];
				stageSecID = (String) stage[1];
			}
			result.put("actualAppComm", actualObj);
			result.put("stageAppComm", stageObj);
			result.put("actualSecID", actualSecID);
			result.put("stageSecID", stageSecID);
			objSec = stageObj;
			securityID = stageSecID;
			if (stageObj == null) {
				objSec = actualObj;
				securityID = actualSecID;
			}
		}
		else {
			ICommodityCollateral[] col;
			int secIndex = Integer.parseInt((String) map.get("secIndexID"));
			if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_READ)) {
				col = (ICommodityCollateral[]) trxValueMap.get("actual");
			}
			else {
				col = (ICommodityCollateral[]) trxValueMap.get("staging");
			}
			objSec = col[secIndex].getApprovedCommodityTypes()[(int) index];
			securityID = String.valueOf(col[secIndex].getSCISecurityID());
		}
		result.put("from_page", from_event);
		HashMap approvedCommMap = new HashMap();

		approvedCommMap.put("obj", objSec);
		approvedCommMap.put("securityID", securityID);
		result.put("approvedCommObj", approvedCommMap);
		// result.put("serviceApprovedComm", objSec);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private Object[] getItem(ICommodityCollateral col[], long commonRef) {
		Object[] item = null;
		if (col == null) {
			return item;
		}
		item = new Object[2];
		for (int i = 0; i < col.length; i++) {
			if (col[i] != null) {
				IApprovedCommodityType[] temp = col[i].getApprovedCommodityTypes();
				if (temp != null) {
					for (int j = 0; j < temp.length; j++) {
						if (temp[j].getCommonRef() == commonRef) {
							item[0] = temp[j];
							item[1] = String.valueOf(col[i].getSCISecurityID());
						}
						else {
							continue;
						}
					}
				}
			}
		}
		return item;
	}
}
