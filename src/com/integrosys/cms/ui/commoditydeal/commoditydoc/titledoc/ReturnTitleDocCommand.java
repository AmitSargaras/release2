/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/ReturnTitleDocCommand.java,v 1.5 2006/09/19 13:25:21 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/19 13:25:21 $ Tag: $Name: $
 */
public class ReturnTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "return_page", "java.lang.String", REQUEST_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
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
				{ "titleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", FORM_SCOPE },
				{ "stageTitleDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						REQUEST_SCOPE },
				{ "actualTitleDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
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
		String return_event = (String) map.get("return_page");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		long index = Long.parseLong((String) map.get("indexID"));

		ICommodityTitleDocument titleDoc = (ICommodityTitleDocument) map.get("serviceTitleDocObj");
		result.put("titleDocObj", titleDoc);

		if (return_event.equals(TitleDocAction.EVENT_MAIN_PAGE) && from_event.equals(TitleDocAction.EVENT_PROCESS)) {
			ICommodityTitleDocument actualObj = null;
			if (trxValue.getCommodityDeal() != null) {
				actualObj = getItem(trxValue.getCommodityDeal().getTitleDocsLatest(), index);
				if (actualObj == null) {
					actualObj = getItem(trxValue.getCommodityDeal().getTitleDocsHistory(), index);
				}
			}
			ICommodityTitleDocument stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				stageObj = getItem(trxValue.getStagingCommodityDeal().getTitleDocsAll(), index);
			}
			result.put("actualTitleDoc", actualObj);
			result.put("stageTitleDoc", stageObj);
		}
		else if (from_event.equals(TitleDocAction.EVENT_PROCESS)) {
			result.put("actualTitleDoc", titleDoc);
			result.put("stageTitleDoc", titleDoc);
		}

		result.put("from_event", from_event);

		String previous_event = (String) map.get("previous_event");
		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ICommodityTitleDocument getItem(ICommodityTitleDocument temp[], long commonRef) {
		ICommodityTitleDocument item = null;
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
