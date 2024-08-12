/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/ReadContractCommand.java,v 1.4 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */

public class ReadContractCommand extends AbstractCommand {
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
				{ "contractObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageContract", "com.integrosys.cms.app.collateral.bus.type.commodity.IContract", REQUEST_SCOPE },
				{ "actualContract", "com.integrosys.cms.app.collateral.bus.type.commodity.IContract", REQUEST_SCOPE },
				// {"serviceContract",
				// "com.integrosys.cms.app.collateral.bus.type.commodity.IContract"
				// , SERVICE_SCOPE},
				{ "actualSecID", "java.lang.String", REQUEST_SCOPE },
				{ "stageSecID", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE }, });
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
		IContract objSec = null;
		String securityID = "";
		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PROCESS)) {
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("actual");
			Object[] actual = getItem(col, index);
			IContract actualObj = null;
			String actualSecID = "";
			if (actual != null) {
				actualObj = (IContract) actual[0];
				actualSecID = (String) actual[1];
			}

			col = (ICommodityCollateral[]) trxValueMap.get("staging");
			Object[] stage = getItem(col, index);
			IContract stageObj = null;
			String stageSecID = "";
			if (stage != null) {
				stageObj = (IContract) stage[0];
				stageSecID = (String) stage[1];
			}
			result.put("actualContract", actualObj);
			result.put("stageContract", stageObj);
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
			objSec = col[secIndex].getContracts()[(int) index];
			securityID = String.valueOf(col[secIndex].getSCISecurityID());
		}
		result.put("from_page", from_event);
		HashMap contractMap = new HashMap();

		contractMap.put("obj", objSec);
		contractMap.put("securityID", securityID);
		result.put("contractObj", contractMap);
		result.put("securityID", securityID);
		// result.put("serviceContract", objSec);

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
				IContract[] temp = col[i].getContracts();
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
