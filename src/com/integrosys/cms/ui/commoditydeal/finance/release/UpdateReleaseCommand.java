/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/UpdateReleaseCommand.java,v 1.3 2004/09/29 09:40:35 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/09/29 09:40:35 $ Tag: $Name: $
 */

public class UpdateReleaseCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "releaseObj", "com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "selectedWRList", "java.util.Collection", SERVICE_SCOPE }, });
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
						SERVICE_SCOPE }, { "warehouseReceiptMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "selectedWRList", "java.util.Collection", SERVICE_SCOPE }, });
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

		int index = Integer.parseInt((String) map.get("indexID"));
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IReceiptRelease release = (IReceiptRelease) map.get("releaseObj");
		Collection selectedWRList = new ArrayList();
		try {
			selectedWRList = (Collection) AccessorUtil.deepClone(map.get("selectedWRList"));
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		IReceiptRelease[] releaseArr = null;
		try {
			releaseArr = (IReceiptRelease[]) AccessorUtil.deepClone(dealObj.getReceiptReleases());
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		IReceiptRelease oldRelease = releaseArr[index];
		Collection oldSettleWRList = new ArrayList();
		if (oldRelease.getSettleWarehouseReceipts() != null) {
			ISettleWarehouseReceipt[] oldSettleWR = oldRelease.getSettleWarehouseReceipts();
			for (int i = 0; i < oldSettleWR.length; i++) {
				oldSettleWRList.add(String.valueOf(oldSettleWR[i].getWarehouseReceipt().getRefID()));
			}
		}

		if (release.getSettleWarehouseReceipts() != null) {
			ISettleWarehouseReceipt[] settleWR = release.getSettleWarehouseReceipts();
			for (int i = 0; i < settleWR.length; i++) {
				String warehouseID = String.valueOf(settleWR[i].getWarehouseReceipt().getRefID());
				if (!selectedWRList.contains(warehouseID)) {
					selectedWRList.add(warehouseID);
				}
				oldSettleWRList.remove(warehouseID);
			}
			selectedWRList.removeAll(oldSettleWRList);
		}

		releaseArr[index] = release;
		if (ReleaseUtil.isExceedActualQty(releaseArr, dealObj.getActualQuantity())) {
			exceptionMap.put("totalQuantityRelease", new ActionMessage(
					"error.commodity.deal.finance.release.totalqtyrelease"));
		}
		else {
			dealObj.setReceiptReleases(releaseArr);
			trxValue.setStagingCommodityDeal(dealObj);

			result.put("selectedWRList", selectedWRList);
			result.put("commodityDealTrxValue", trxValue);
			result.put("warehouseReceiptMap", null);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
