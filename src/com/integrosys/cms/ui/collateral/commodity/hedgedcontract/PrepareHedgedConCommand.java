/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/hedgedcontract/PrepareHedgedConCommand.java,v 1.3 2004/07/20 06:03:04 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.hedgedcontract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/20 06:03:04 $ Tag: $Name: $
 */

public class PrepareHedgedConCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "secID", "java.util.Collection", REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", REQUEST_SCOPE }, });
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
		int secInd = -1;
		String secIndex = (String) map.get("secIndexID");

		if (secIndex != null) {
			secInd = Integer.parseInt(secIndex);
		}

		Collection secID = new ArrayList();
		if (col != null) {
			for (int i = 0; i < col.length; i++) {
				if (((secInd != -1) && (i == secInd))
						|| (!col[i].getStatus().equals(ICMSConstant.STATE_DELETED) && !col[i].getStatus().equals(
								ICMSConstant.STATE_PENDING_DELETE))) {
					secID.add(String.valueOf(col[i].getSCISecurityID()));
				}
			}
		}

		result.put("secID", secID);
		result.put("secValue", secID);

		CurrencyList currList = CurrencyList.getInstance();
		result.put("currencyCode", currList.getCountryValues());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
