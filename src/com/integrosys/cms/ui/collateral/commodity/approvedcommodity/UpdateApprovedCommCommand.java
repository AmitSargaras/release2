/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/UpdateApprovedCommCommand.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:08 $ Tag: $Name: $
 */

public class UpdateApprovedCommCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "approvedCommObj", "com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType",
						FORM_SCOPE }, { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
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

		int index = Integer.parseInt((String) map.get("indexID"));
		int secIndex = Integer.parseInt((String) map.get("secIndexID"));
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
		IApprovedCommodityType approvedCommObj = (IApprovedCommodityType) map.get("approvedCommObj");
		IProfile profile = approvedCommObj.getProfile();

		IApprovedCommodityType[] tempApp = col[secIndex].getApprovedCommodityTypes();
		if (tempApp != null) {
			for (int i = 0; i < tempApp.length; i++) {
				if ((tempApp[i].getApprovedCommodityTypeID() != approvedCommObj.getApprovedCommodityTypeID())
						&& (tempApp[i].getProfile().getProfileID() == profile.getProfileID())) {
					exceptionMap.put("productSubType", new ActionMessage(
							"error.collateral.commodity.approvetype.duplicate"));
				}
			}
		}
		if (exceptionMap.isEmpty()) {
			IApprovedCommodityType[] approvedCommList = col[secIndex].getApprovedCommodityTypes();
			approvedCommList[index] = approvedCommObj;
			((OBCommodityCollateral) col[secIndex]).setApprovedCommodityTypes(approvedCommList);
		}

		trxValueMap.put("staging", col);

		result.put("commodityMainTrxValue", trxValueMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
