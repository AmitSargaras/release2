/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/CreateContractCommand.java,v 1.2 2004/06/04 05:22:18 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:18 $ Tag: $Name: $
 */

public class CreateContractCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "contractObj", "com.integrosys.cms.app.collateral.bus.type.commodity.IContract", FORM_SCOPE },
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
		IContract contractObj = (IContract) map.get("contractObj");

		if (col != null) {
			boolean found = false;
			for (int i = 0; !found && (i < col.length); i++) {
				if (col[i].getSCISecurityID().equals(securityID)) {
					found = true;
					col[i] = addContract((OBCommodityCollateral) col[i], contractObj);
				}
			}
		}

		trxValueMap.put("staging", col);

		result.put("commodityMainTrxValue", trxValueMap);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static ICommodityCollateral addContract(OBCommodityCollateral iCol, IContract iContract) {
		IContract[] existingArray = iCol.getContracts();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IContract[] newArray = new IContract[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iContract;

		iCol.setContracts(newArray);
		return iCol;
	}
}
