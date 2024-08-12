/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/CreateTitleDocWarehouseCommand.java,v 1.7 2006/09/13 11:06:24 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.WarehouseReceiptComparator;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/13 11:06:24 $ Tag: $Name: $
 */

public class CreateTitleDocWarehouseCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE },
				{ "titleDocWarehouseObj", "com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt", FORM_SCOPE },
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		ICommodityTitleDocument titleDoc = null;
		try {
			titleDoc = (ICommodityTitleDocument) AccessorUtil.deepClone((ICommodityTitleDocument) map
					.get("serviceTitleDocObj"));
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		IWarehouseReceipt warehouseRec = (IWarehouseReceipt) map.get("titleDocWarehouseObj");
		IWarehouseReceipt[] receiptArr = titleDoc.getWarehouseReceipts();

		if (receiptArr != null) {
			boolean isSame = false;
			String trimRecNo = warehouseRec.getOrigReceiptNo().trim();
			for (int i = 0; !isSame && (i < receiptArr.length); i++) {
				if (receiptArr[i].getOrigReceiptNo().trim().equals(trimRecNo)) {
					isSame = true;
					exceptionMap.put("origPaperRecNo", new ActionMessage(
							"error.commodity.deal.titledoc.warehousereceipt.duplicate"));
				}
			}
		}
		if (exceptionMap.isEmpty()) {
			receiptArr = addWarehouseReceipt(receiptArr, warehouseRec);
			titleDoc.setWarehouseReceipts(receiptArr);
			// Quantity totalQty
			// =CommodityDealUtil.getTotalWRQuantity(titleDoc);
			// Modified by Pratheepa on 25.08.2006 to fix JIRA CMS-3180
			Quantity totalQty = CommodityDealUtil.getTotalWRQuantity(dealObj, titleDoc);
			DefaultLogger
					.debug(this, ">>>>>>>>> totalQty: " + totalQty + "\tActualQty: " + dealObj.getActualQuantity());

			if ((dealObj != null) && (dealObj.getActualQuantity() != null)) {
				if ((totalQty != null) && (totalQty.getQuantity() != null)
						&& (dealObj.getActualQuantity().getQuantity() == null)) {
					exceptionMap.put("quantity", new ActionMessage(
							"error.commodity.deal.titledoc.warehousereceipt.totalQty"));
				}
				else if ((totalQty != null) && (totalQty.getQuantity() != null)
						&& (dealObj.getActualQuantity().getQuantity().compareTo(totalQty.getQuantity()) < 0)) {
					exceptionMap.put("quantity", new ActionMessage(
							"error.commodity.deal.titledoc.warehousereceipt.totalQty"));
				}
			}
			else if ((totalQty != null) && (totalQty.getQuantity() != null) && (dealObj.getActualQuantity() == null)) {
				exceptionMap.put("quantity", new ActionMessage(
						"error.commodity.deal.titledoc.warehousereceipt.totalQty"));
			}
			if (exceptionMap.isEmpty()) {
				WarehouseReceiptComparator c = new WarehouseReceiptComparator(
						WarehouseReceiptComparator.BY_WR_ISSUE_DATE);
				Arrays.sort(receiptArr, c);
				titleDoc.setWarehouseReceipts(receiptArr);
				result.put("serviceTitleDocObj", titleDoc);
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static IWarehouseReceipt[] addWarehouseReceipt(IWarehouseReceipt[] existingArray, IWarehouseReceipt obj) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IWarehouseReceipt[] newArray = new IWarehouseReceipt[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = obj;

		return newArray;
	}
}
