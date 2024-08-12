/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/UpdateTitleDocCommand.java,v 1.10 2006/02/20 06:56:49 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/02/20 06:56:49 $ Tag: $Name: $
 */

public class UpdateTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "titleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "return_page", "java.lang.String", REQUEST_SCOPE } });
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
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "TitleDocWR", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", SERVICE_SCOPE },
				{ "TitleDocWRNeg", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE } });
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
	// Modified by Pratheepa on 16/01/2006 while fixing R1.5 CR129.Added check
	// for WAREHOUSE_RECEIPT_N also.
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		long index = Long.parseLong((String) map.get("indexID"));
		String return_event = (String) map.get("return_page");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) map.get("titleDocObj");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();

		if (return_event.equals(TitleDocAction.EVENT_LIST_TITLE_DOC)) {
			ICommodityTitleDocument[] titleDocArr = dealObj.getTitleDocsHistory();
			boolean found = false;
			for (int i = 0; !found && (i < titleDocArr.length); i++) {
				if (titleDocArr[i].getRefID() == index) {
					titleDocArr[i] = titleDocObj;
					found = true;
				}
			}

			dealObj.setTitleDocsHistory(titleDocArr);

			if ((titleDocObj.getTitleDocType() != null)
					&& CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N.equals(titleDocObj.getTitleDocType()
							.getName())) {
				result.put("TitleDocWRNeg", titleDocObj);

			}
			if ((titleDocObj.getTitleDocType() != null)
					&& CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT.equals(titleDocObj.getTitleDocType().getName())) {
				result.put("TitleDocWR", titleDocObj);

			}
		}
		else {
			if ((trxValue.getStatus() == null) || ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
				ICommodityTitleDocument[] titleDocArr = dealObj.getTitleDocsLatest();
				titleDocArr[(int) index] = titleDocObj;
				dealObj.setTitleDocsLatest(titleDocArr);
			}
			else {
				ICommodityTitleDocument[] titleDocArr = dealObj.getTitleDocsAll();
				titleDocArr[(int) index] = titleDocObj;
				dealObj.setTitleDocsAll(titleDocArr);
			}
		}
		trxValue.setStagingCommodityDeal(dealObj);

		result.put("commodityDealTrxValue", trxValue);
		result.put("return_page", return_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
