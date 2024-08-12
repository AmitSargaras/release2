/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/CreateApprovedCommCommand.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
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

public class CreateApprovedCommCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
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

		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
		String securityID = (String) map.get("securityID");
		IApprovedCommodityType approvedCommObj = (IApprovedCommodityType) map.get("approvedCommObj");
		IProfile profile = approvedCommObj.getProfile();

		if (col != null) {
			boolean found = false;
			for (int i = 0; !found && (i < col.length); i++) {
				if (col[i].getSCISecurityID().equals(securityID)) {
					IApprovedCommodityType[] tempApp = col[i].getApprovedCommodityTypes();
					if (tempApp != null) {
						for (int j = 0; exceptionMap.isEmpty() && (j < tempApp.length); j++) {
							IProfile tempProfile = tempApp[j].getProfile();
							if (tempProfile.getProfileID() == profile.getProfileID()) {
								exceptionMap.put("productSubType", new ActionMessage(
										"error.collateral.commodity.approvetype.duplicate"));
							}
						}
					}
					if (exceptionMap.isEmpty()) {
						found = true;
						col[i] = addApprovedCommType((OBCommodityCollateral) col[i], approvedCommObj);
					}
				}
			}
		}

		trxValueMap.put("staging", col);

		result.put("commodityMainTrxValue", trxValueMap);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static ICommodityCollateral addApprovedCommType(OBCommodityCollateral iCol,
			IApprovedCommodityType iApprovedComm) {
		IApprovedCommodityType[] existingArray = iCol.getApprovedCommodityTypes();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IApprovedCommodityType[] newArray = new IApprovedCommodityType[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iApprovedComm;

		iCol.setApprovedCommodityTypes(newArray);
		return iCol;
	}
}
