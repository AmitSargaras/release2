/*
 * Created on Sep 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.commodity.secapportion;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadReturnSecCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });
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

		HashMap mapInMap = (HashMap) (map.get("commodityMainTrxValue"));
		String indexID = (String) (map.get("indexID"));
		ICollateralTrxValue[] trxValList = (ICollateralTrxValue[]) (mapInMap.get("trxValue"));
		ICollateralTrxValue itrxValue = null;
		String from_event = (String) map.get("from_event");

		if ((from_event != null) && from_event.equals("process")) {
			itrxValue = getCol(trxValList, indexID);
		}
		else {
			itrxValue = trxValList[Integer.parseInt(indexID)];
		}

		String from_page = (String) (map.get("from_page"));
		if (itrxValue != null) {

			result.put("commodityMainTrxValue", mapInMap);
			if ((from_page != null) && from_page.equals("read")) {
				result.put("commSecObj", itrxValue.getCollateral());
			}
			else {
				result.put("commSecObj", itrxValue.getStagingCollateral());
			}
		}
		result.put("from_event", map.get("from_page"));

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ICollateralTrxValue getCol(ICollateralTrxValue[] temp, String securityID) {
		ICollateralTrxValue item = null;
		if (temp == null) {
			return item;
		}

		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getStagingCollateral().getSCISecurityID().equals(securityID)) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

}
