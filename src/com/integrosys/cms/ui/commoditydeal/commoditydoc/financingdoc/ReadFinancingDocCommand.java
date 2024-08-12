/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/ReadFinancingDocCommand.java,v 1.4 2004/07/03 11:43:55 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBFinancingDoc;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/03 11:43:55 $ Tag: $Name: $
 */

public class ReadFinancingDocCommand extends AbstractCommand {
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
		return (new String[][] {
				{ "financingDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc", FORM_SCOPE },
				{ "stageFinanceDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc", REQUEST_SCOPE },
				{ "actualFinanceDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc", REQUEST_SCOPE }, });
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
		String from_event = (String) map.get("from_event");
		String previous_event = (String) map.get("previous_event");

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IFinancingDoc objSec = new OBFinancingDoc();
		if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
			IFinancingDoc actualObj = null;
			if (trxValue.getCommodityDeal() != null) {
				actualObj = getItem(trxValue.getCommodityDeal().getFinancingDocs(), index);
			}
			IFinancingDoc stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				stageObj = getItem(trxValue.getStagingCommodityDeal().getFinancingDocs(), index);
				objSec = stageObj;
			}
			result.put("actualFinanceDoc", actualObj);
			result.put("stageFinanceDoc", stageObj);
		}
		else if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_READ)) {
			if ((previous_event != null) && previous_event.equals(CommodityDealAction.EVENT_USER_PROCESS)) {
				if (trxValue.getStagingCommodityDeal() != null) {
					objSec = trxValue.getStagingCommodityDeal().getFinancingDocs()[(int) index];
				}
			}
			else {
				if (trxValue.getCommodityDeal() != null) {
					objSec = trxValue.getCommodityDeal().getFinancingDocs()[(int) index];
				}
			}
		}
		else {
			if (trxValue.getStagingCommodityDeal() != null) {
				objSec = trxValue.getStagingCommodityDeal().getFinancingDocs()[(int) index];
			}
		}

		result.put("from_event", from_event);
		result.put("financingDocObj", objSec);

		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IFinancingDoc getItem(IFinancingDoc temp[], long commonRef) {
		IFinancingDoc item = null;
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
