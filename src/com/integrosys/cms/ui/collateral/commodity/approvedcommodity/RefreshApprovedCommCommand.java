/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/approvedcommodity/RefreshApprovedCommCommand.java,v 1.2 2004/06/04 05:22:08 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.approvedcommodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:08 $ Tag: $Name: $
 */

public class RefreshApprovedCommCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "securityID", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "securitySubType", "java.lang.Strng", REQUEST_SCOPE }, });
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
		String securityID = (String) map.get("securityID");
		String securitySubType = "";
		if ((securityID != null) || (securityID.length() > 0)) {
			// long secID = Long.parseLong(securityID);
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
			boolean found = false;
			for (int i = 0; !found && (i < col.length); i++) {
				if (col[i].getSCISecurityID().equals(securityID)) {
					found = true;
					securitySubType = col[i].getCollateralSubType().getSubTypeCode();
				}
			}
		}

		result.put("securitySubType", securitySubType);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
