/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/RefreshReleaseCommand.java,v 1.1 2004/09/07 08:32:35 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/07 08:32:35 $ Tag: $Name: $
 */
public class RefreshReleaseCommand extends AbstractCommand {
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
		return (new String[][] { { "releaseObj", "java.util.HashMap", FORM_SCOPE }, });
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

		IReceiptRelease release = (IReceiptRelease) map.get("releaseObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getCommodityDeal();
		int index = Integer.parseInt((String) map.get("indexID"));
		Collection fullReleaseQtyList = new ArrayList();
		try {
			ICommodityDeal stageObj = (ICommodityDeal) AccessorUtil.deepClone(trxValue.getStagingCommodityDeal());
			IReceiptRelease[] releaseList = stageObj.getReceiptReleases();
			if (index == -1) {
				releaseList = ReleaseUtil.addRelease(releaseList, release);
			}
			else {
				releaseList[index] = release;
			}
			stageObj.setReceiptReleases(releaseList);
			fullReleaseQtyList = ReleaseUtil.getFullReleasedQtyWarehouseReceipt(stageObj);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
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
		releaseMap.put("obj", release);
		releaseMap.put("fullReleaseQtyList", fullReleaseQtyList);
		result.put("releaseObj", releaseMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
