/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/settlementdetails/ReadSettlementCommand.java,v 1.10 2004/09/07 08:34:09 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.settlementdetails;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBSettlement;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/09/07 08:34:09 $ Tag: $Name: $
 */

public class ReadSettlementCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "settlementObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageSettlement", "com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement", REQUEST_SCOPE },
				{ "actualSettlement", "com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement", REQUEST_SCOPE }, });
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
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<< HSHII: indexID: " + index);
		String from_event = (String) map.get("from_event");
		String previous_event = (String) map.get("previous_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ISettlement objSec = null;

		if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
			ISettlement actualObj = null;
			if (trxValue.getCommodityDeal() != null) {
				actualObj = getItem(trxValue.getCommodityDeal().getSettlements(), index);
			}
			ISettlement stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				stageObj = getItem(trxValue.getStagingCommodityDeal().getSettlements(), index);
				objSec = stageObj;
			}
			if (objSec == null) {
				objSec = actualObj;
			}
			result.put("actualSettlement", actualObj);
			result.put("stageSettlement", stageObj);
		}
		else if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_READ)) {
			if (trxValue.getCommodityDeal() != null) {
				objSec = trxValue.getCommodityDeal().getSettlements()[(int) index];
			}
		}
		else {
			if ((index >= 0) && (trxValue.getStagingCommodityDeal() != null)) {
				objSec = trxValue.getStagingCommodityDeal().getSettlements()[(int) index];
			}
			else {
				objSec = new OBSettlement();
			}
		}

		HashMap settlementMap = new HashMap();

		settlementMap.put("obj", objSec);
		result.put("from_event", from_event);
		result.put("settlementObj", settlementMap);

		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ISettlement getItem(ISettlement temp[], long commonRef) {
		ISettlement item = null;
		if (temp == null) {
			return item;
		}

		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID() == commonRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}
