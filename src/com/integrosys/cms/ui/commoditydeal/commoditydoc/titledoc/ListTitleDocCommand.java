/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/ListTitleDocCommand.java,v 1.6 2006/11/20 09:06:25 jzhan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/11/20 09:06:25 $ Tag: $Name: $
 */
public class ListTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "return_page", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "stagingWRTitleDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						REQUEST_SCOPE },
				{ "actualWRTitleDoc", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						REQUEST_SCOPE }, { "nonNegTitleDoc", "java.util.Collection", REQUEST_SCOPE },
				{ "negTitleDoc", "java.util.Collection", REQUEST_SCOPE }, });
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
		String event = (String) map.get("event");
		if (event.equals(TitleDocAction.EVENT_LIST_TITLE_DOC)
				|| event.equals(TitleDocAction.EVENT_UPDATE)
				|| (event.equals(TitleDocAction.EVENT_VIEW_RETURN) && return_event
						.equals(TitleDocAction.EVENT_LIST_TITLE_DOC))) {
			ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
			ICommodityDeal dealObj;
			/*
			 * ICommodityTitleDocument actualTitleDoc = null;
			 * ICommodityTitleDocument stagingTitleDoc = null;
			 */
			if (from_event.equals(EVENT_READ)) {
				dealObj = trxValue.getCommodityDeal();
			}
			else {
				dealObj = trxValue.getStagingCommodityDeal();
			}
			// boolean isProcess = from_event.equals(EVENT_PROCESS);

			dealObj.setTitleDocsHistory(null);
			ICommodityTitleDocument[] titleDocList = dealObj.getTitleDocsHistory();
			Collection nonNegTitleDoc = new ArrayList();
			Collection negTitleDoc = new ArrayList();
			if (titleDocList != null) {
				for (int i = 0; i < titleDocList.length; i++) {
					if (titleDocList[i].getTitleDocType().getType().equals(ITitleDocument.NON_NEGOTIABLE)) {
						nonNegTitleDoc.add(titleDocList[i]);
					}
					else if (titleDocList[i].getTitleDocType().getType().equals(ITitleDocument.NEGOTIABLE)) {
						negTitleDoc.add(titleDocList[i]);
					}
					/*
					 * if (isProcess && dealObj.getIsAnyWRTitleDoc()) { if
					 * (titleDocList
					 * [i].getTitleDocType().getName().equals(CommodityDealConstant
					 * .DOC_TYPE_WAREHOUSE_RECEIPT)) { stagingTitleDoc =
					 * titleDocList[i]; } }
					 */
				}
			}

			/*
			 * if (isProcess) { dealObj = trxValue.getCommodityDeal(); if
			 * (dealObj != null && dealObj.getIsAnyWRTitleDoc()) { titleDocList
			 * = dealObj.getTitleDocsHistory(); boolean found = false; for (int
			 * i = 0; !found && i < titleDocList.length; i++) { if
			 * (titleDocList[
			 * i].getTitleDocType().getName().equals(CommodityDealConstant
			 * .DOC_TYPE_WAREHOUSE_RECEIPT)) { found = true; actualTitleDoc =
			 * titleDocList[i]; } } } result.put("actualWRTitleDoc",
			 * actualTitleDoc); result.put("stagingWRTitleDoc",
			 * stagingTitleDoc); }
			 */

			result.put("nonNegTitleDoc", nonNegTitleDoc);
			result.put("negTitleDoc", negTitleDoc);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
