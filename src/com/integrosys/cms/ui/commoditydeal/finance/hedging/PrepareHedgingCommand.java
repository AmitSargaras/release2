/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/PrepareHedgingCommand.java,v 1.4 2004/07/15 16:05:52 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/15 16:05:52 $ Tag: $Name: $
 */

public class PrepareHedgingCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, { "globalTreasuryRefNo", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "globalTrRefID", "java.util.Collection", REQUEST_SCOPE },
				{ "globalTrRefValue", "java.util.Collection", REQUEST_SCOPE },
				{ "timeFreqID", "java.util.Collection", REQUEST_SCOPE },
				{ "timeFreqValue", "java.util.Collection", REQUEST_SCOPE }, });
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

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		IHedgingContractInfo[] hedgingInfo = dealCollateral.getHedgingContractInfos();
		String strHedgeContractID = (String) map.get("globalTreasuryRefNo");
		long hedgeContractID = ICMSConstant.LONG_INVALID_VALUE;
		if ((strHedgeContractID != null) && (strHedgeContractID.length() > 0)) {
			hedgeContractID = Long.parseLong(strHedgeContractID);
		}
		Collection globalRefID = new ArrayList();
		Collection globalRefValue = new ArrayList();
		if (hedgingInfo != null) {
			for (int i = 0; i < hedgingInfo.length; i++) {
				globalRefID.add(String.valueOf(hedgingInfo[i].getHedgingContractInfoID()));
				globalRefValue.add(hedgingInfo[i].getGlobalTreasuryReference());
			}
			if (hedgeContractID > 0) {
				if (!globalRefID.contains(String.valueOf(hedgeContractID))) {
					hedgingInfo = dealCollateral.getDeletedHedgeContractInfos();
					if (hedgingInfo != null) {
						boolean found = false;
						for (int i = 0; !found && (i < hedgingInfo.length); i++) {
							if (hedgingInfo[i].getHedgingContractInfoID() == hedgeContractID) {
								found = true;
								globalRefID.add(String.valueOf(hedgingInfo[i].getHedgingContractInfoID()));
								globalRefValue.add(hedgingInfo[i].getGlobalTreasuryReference());
							}
						}
					}
				}
			}
		}
		result.put("globalTrRefID", globalRefID);
		result.put("globalTrRefValue", globalRefValue);

		TimeFreqList timeFreqList = TimeFreqList.getInstance();
		result.put("timeFreqID", timeFreqList.getTimeFreqID());
		result.put("timeFreqValue", timeFreqList.getTimeFreqValue());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
