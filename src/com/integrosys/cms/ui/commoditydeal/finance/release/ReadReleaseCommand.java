/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/ReadReleaseCommand.java,v 1.1 2004/09/07 08:32:35 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.SettleWarehouseReceiptComparator;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/07 08:32:35 $ Tag: $Name: $
 */

public class ReadReleaseCommand extends AbstractCommand {
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
				{ "releaseObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageRelease", "com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease", REQUEST_SCOPE },
				{ "actualRelease", "com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease", REQUEST_SCOPE },
				{ "warehouseReceiptMap", "java.util.HashMap", SERVICE_SCOPE }, });
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
		ICommodityDeal dealObj = trxValue.getCommodityDeal();
		Collection fullReleaseQtyList = new ArrayList();
		IReceiptRelease objSec = null;

		if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
			IReceiptRelease actualObj = null;
			SettleWarehouseReceiptComparator c = new SettleWarehouseReceiptComparator();
			if (trxValue.getCommodityDeal() != null) {
				actualObj = getItem(trxValue.getCommodityDeal().getReceiptReleases(), index);
				if (actualObj != null) {
					ISettleWarehouseReceipt[] warehouseRecList = actualObj.getSettleWarehouseReceipts();
					if (warehouseRecList != null) {
						Arrays.sort(warehouseRecList, c);
					}
					actualObj.setSettleWarehouseReceipts(warehouseRecList);
				}
			}
			IReceiptRelease stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				stageObj = getItem(trxValue.getStagingCommodityDeal().getReceiptReleases(), index);
				if (stageObj != null) {
					ISettleWarehouseReceipt[] warehouseRecList = stageObj.getSettleWarehouseReceipts();
					if (warehouseRecList != null) {
						Arrays.sort(warehouseRecList, c);
					}
					stageObj.setSettleWarehouseReceipts(warehouseRecList);
				}
				objSec = stageObj;
			}
			if (objSec == null) {
				objSec = actualObj;
			}
			fullReleaseQtyList = ReleaseUtil.getFullReleasedQtyWarehouseReceipt(trxValue.getStagingCommodityDeal());
			result.put("actualRelease", actualObj);
			result.put("stageRelease", stageObj);
		}
		else if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_READ)) {
			if (trxValue.getCommodityDeal() != null) {
				objSec = trxValue.getCommodityDeal().getReceiptReleases()[(int) index];
				fullReleaseQtyList = ReleaseUtil.getFullReleasedQtyWarehouseReceipt(trxValue.getCommodityDeal());
			}
		}
		else {
			fullReleaseQtyList = ReleaseUtil.getFullReleasedQtyWarehouseReceipt(trxValue.getStagingCommodityDeal());
			if ((index >= 0) && (trxValue.getStagingCommodityDeal() != null)) {
				objSec = trxValue.getStagingCommodityDeal().getReceiptReleases()[(int) index];
			}
			else {
				objSec = new OBReceiptRelease();
			}
		}

		HashMap releaseMap = new HashMap();
		HashMap warehouseReceiptMap = ReleaseUtil.getWarehouseReceiptMap(dealObj);

		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<< HSHII: warehouser receipt size: "
				+ warehouseReceiptMap.size());
		try {
			Collection allWarehouseReceipt = warehouseReceiptMap.keySet();
			releaseMap.put("allReceipt", allWarehouseReceipt);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		if (objSec != null) {
			ISettleWarehouseReceipt[] settleReceipt = objSec.getSettleWarehouseReceipts();
			if (settleReceipt != null) {
				for (int i = 0; i < settleReceipt.length; i++) {
					if (settleReceipt[i].getWarehouseReceipt() != null) {
						IWarehouseReceipt tmp = settleReceipt[i].getWarehouseReceipt();
						warehouseReceiptMap.put(String.valueOf(tmp.getRefID()), tmp.getOrigReceiptNo());
					}
				}
			}
		}

		result.put("warehouseReceiptMap", warehouseReceiptMap);
		releaseMap.put("fullReleaseQtyList", fullReleaseQtyList);
		releaseMap.put("obj", objSec);
		result.put("from_event", from_event);
		result.put("releaseObj", releaseMap);

		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IReceiptRelease getItem(IReceiptRelease temp[], long commonRef) {
		IReceiptRelease item = null;
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
