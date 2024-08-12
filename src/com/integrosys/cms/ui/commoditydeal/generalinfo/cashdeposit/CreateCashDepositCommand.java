/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/cashdeposit/CreateCashDepositCommand.java,v 1.2 2004/06/04 05:07:38 hltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:07:38 $ Tag: $Name: $
 */

public class CreateCashDepositCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "cashDepositObj", "com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityDealTrxValue",
				"com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue", SERVICE_SCOPE }, });
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

		IDealCashDeposit cashDeposit = (IDealCashDeposit) map.get("cashDepositObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		IDealCashDeposit[] cashDepositArr = dealObj.getCashDeposit();

		cashDepositArr = addCashDeposit(cashDepositArr, cashDeposit);
		dealObj.setCashDeposit(cashDepositArr);
		trxValue.setStagingCommodityDeal(dealObj);

		result.put("commodityDealTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static IDealCashDeposit[] addCashDeposit(IDealCashDeposit[] existingArray, IDealCashDeposit obj) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IDealCashDeposit[] newArray = new IDealCashDeposit[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = obj;

		return newArray;
	}
}
