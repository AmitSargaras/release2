/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/RefreshTitleDocCommand.java,v 1.7 2006/02/20 06:53:11 pratheepa Exp $
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
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/02/20 06:53:11 $ Tag: $Name: $
 */
public class RefreshTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE },
				{ "docDesc", "java.lang.String", REQUEST_SCOPE },
				{ "docType", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "serviceTitleDocObj",
				"com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", SERVICE_SCOPE }, });
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
	// Modified by Pratheepag on 16/01/2006 while fixing R1.5 CR129.
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) map.get("serviceTitleDocObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		DefaultLogger.debug(this, "commodityDealTrxValue" + trxValue);
		ICommodityDeal dealObj = (ICommodityDeal) trxValue.getStagingCommodityDeal();
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<< HSHII: deal -- isAnyWRTitleDoc: "
				+ dealObj.getIsAnyWRTitleDoc());
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<< HSHII: deal -- isAnyWRTitleDoc: "
				+ dealObj.getIsAnyWRTitleDoc_N());
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<< HSHII: deal -- isAnyWRTitleDoc: "
				+ dealObj.getIsAnyWRTitleDoc_NN());
		String docDesc = (String) map.get("docDesc");
		String docType = (String) map.get("docType");
		DefaultLogger.debug(this, "docType: " + docType);
		DefaultLogger.debug(this, "docDesc: " + docDesc);
		if (docType != null) {
			if ((docDesc != null) && (docDesc.length() > 0)) {
				long titleDocID = Long.parseLong(docDesc);
				DefaultLogger.debug(this, "titleDocID: " + titleDocID);
				ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
				try {
					ITitleDocument titleDocument = proxy.getTitleDocumentByTitleDocumentID(titleDocID);
					DefaultLogger.debug(this, "titleDocument from proxy: " + titleDocument);
					if (titleDocument.getType().equals(docType)) {
						DefaultLogger.debug(this, "coming inside 1st if");
						if ((titleDocument.getName().equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
								|| (titleDocument.getName().equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
							DefaultLogger.debug(this, "coming inside 2nd if");
							if (dealObj.getIsAnyWRTitleDoc()) {
								if (dealObj.getIsAnyWRTitleDoc_N() && docType.equals("neg")) {
									exceptionMap.put("docDesc_N", new ActionMessage(
											"error.commodity.deal.titledoc.warehousereceipt_N"));
									// titleDocObj.setTitleDocType(null);

								}
								if (dealObj.getIsAnyWRTitleDoc_NN() && docType.equals("non_neg")) {
									exceptionMap.put("docDesc_NN", new ActionMessage(
											"error.commodity.deal.titledoc.warehousereceipt_NN"));
									// titleDocObj.setTitleDocType(null);

								}

								titleDocObj.setTitleDocType(titleDocument);

							}
							else {
								titleDocObj.setTitleDocType(titleDocument);
							}
						}
						else {
							titleDocObj.setTitleDocType(titleDocument);
						}
					}
					else {
						titleDocObj.setTitleDocType(null);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}
		else {
			titleDocObj.setTitleDocType(null);
		}

		result.put("serviceTitleDocObj", titleDocObj);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
