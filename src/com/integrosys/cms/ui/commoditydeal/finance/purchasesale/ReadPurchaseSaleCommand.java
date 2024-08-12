/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/ReadPurchaseSaleCommand.java,v 1.6 2004/07/03 11:43:56 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.OBPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/03 11:43:56 $ Tag: $Name: $
 */

public class ReadPurchaseSaleCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "previous_event", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "purchaseSaleObj",
						"com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails", FORM_SCOPE },
				{ "stagePurchaseSale",
						"com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails",
						REQUEST_SCOPE },
				{ "actualPurchaseSale",
						"com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails",
						REQUEST_SCOPE }, });
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

		String from_event = (String) map.get("from_event");
		String previous_event = (String) map.get("previous_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IPurchaseAndSalesDetails objSec = null;
		if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
			IPurchaseAndSalesDetails actualObj = null;
			if (trxValue.getCommodityDeal() != null) {
				actualObj = trxValue.getCommodityDeal().getPurchaseAndSalesDetails();
			}
			IPurchaseAndSalesDetails stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				stageObj = trxValue.getStagingCommodityDeal().getPurchaseAndSalesDetails();
			}
			result.put("actualPurchaseSale", actualObj);
			result.put("stagePurchaseSale", stageObj);
			objSec = stageObj;
			if (objSec == null) {
				objSec = actualObj;
			}
		}
		else if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_READ)) {
			if ((previous_event != null) && previous_event.equals(CommodityDealAction.EVENT_USER_PROCESS)) {
				if (trxValue.getStagingCommodityDeal() != null) {
					objSec = trxValue.getStagingCommodityDeal().getPurchaseAndSalesDetails();
				}
			}
			else {
				if (trxValue.getCommodityDeal() != null) {
					objSec = trxValue.getCommodityDeal().getPurchaseAndSalesDetails();
				}
			}
		}
		else {
			if (trxValue.getStagingCommodityDeal() != null) {
				objSec = trxValue.getStagingCommodityDeal().getPurchaseAndSalesDetails();
			}
		}
		if (objSec == null) {
			objSec = new OBPurchaseAndSalesDetails();
		}
		result.put("from_event", from_event);
		result.put("purchaseSaleObj", objSec);

		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
