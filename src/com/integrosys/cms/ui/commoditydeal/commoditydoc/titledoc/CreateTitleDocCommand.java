/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/CreateTitleDocCommand.java,v 1.7 2006/02/20 06:49:04 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/02/20 06:49:04 $ Tag: $Name: $
 */

public class CreateTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "titleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", FORM_SCOPE },
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
	// Method modified by Pratheepa for CR129
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityTitleDocument titleDoc = (ICommodityTitleDocument) map.get("titleDocObj");
		String docType = ((ITitleDocument) (titleDoc.getTitleDocType())).getType();
		String docName = ((ITitleDocument) (titleDoc.getTitleDocType())).getName();
		DefaultLogger.debug(this, "docType:" + docType);
		DefaultLogger.debug(this, "docName:" + docName);

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		ICommodityTitleDocument[] titleDocArr = null;
		if (ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
			titleDocArr = dealObj.getTitleDocsLatest();
		}
		else {
			titleDocArr = dealObj.getTitleDocsAll();
		}

		if ((docName.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
				|| (docName.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
			DefaultLogger.debug(this, "coming inside loopg");

			if (dealObj.getIsAnyWRTitleDoc()) {
				if (dealObj.getIsAnyWRTitleDoc_N() && "neg".equals(docType)) {
					DefaultLogger.debug(this, "coming inside neg");
					exceptionMap
							.put("docDesc_N", new ActionMessage("error.commodity.deal.titledoc.warehousereceipt_N"));
				}
				if (dealObj.getIsAnyWRTitleDoc_NN() && "non_neg".equals(docType)) {
					DefaultLogger.debug(this, "coming inside nonneg");
					exceptionMap.put("docDesc_NN", new ActionMessage(
							"error.commodity.deal.titledoc.warehousereceipt_NN"));
				}
			}

			dealObj.setIsAnyWRTitleDoc(true);
			if (docName.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N)) {
				DefaultLogger.debug(this, "coming inside neg1");
				dealObj.setIsAnyWRTitleDoc_N(true);
			}
			if (docName.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT)) {
				DefaultLogger.debug(this, "coming inside nonneg1");
				dealObj.setIsAnyWRTitleDoc_NN(true);
			}

		}
		if (exceptionMap.isEmpty()) {
			titleDocArr = addTitleDoc(titleDocArr, titleDoc);
			if ((trxValue.getStatus() == null) || ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
				dealObj.setTitleDocsLatest(titleDocArr);
			}
			else {
				dealObj.setTitleDocsAll(titleDocArr);
			}
			trxValue.setStagingCommodityDeal(dealObj);
		}
		result.put("commodityDealTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static ICommodityTitleDocument[] addTitleDoc(ICommodityTitleDocument[] existingArray,
			ICommodityTitleDocument obj) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ICommodityTitleDocument[] newArray = new ICommodityTitleDocument[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = obj;

		return newArray;
	}
}
