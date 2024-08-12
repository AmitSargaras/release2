/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/ReadTitleDocWarehouseCommand.java,v 1.7 2006/09/19 13:25:38 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;
import com.integrosys.cms.ui.commoditydeal.commoditydoc.CommDocAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/19 13:25:38 $ Tag: $Name: $
 */

public class ReadTitleDocWarehouseCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "warehouseIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "return_page", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE },
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
				{ "titleDocWarehouseObj", "com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt", FORM_SCOPE },
				{ "stageTitleDocWarehouse", "com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt",
						REQUEST_SCOPE },
				{ "actualTitleDocWarehouse", "com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt",
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

		long index = Long.parseLong((String) map.get("indexID"));
		long warehouseIndex = Long.parseLong((String) map.get("warehouseIndexID"));
		String from_event = (String) map.get("from_event");
		String return_event = (String) map.get("return_page");
		ICommodityTitleDocument titleDoc = (ICommodityTitleDocument) map.get("serviceTitleDocObj");
		DefaultLogger.debug(this, "titleDocObj: " + titleDoc);
		DefaultLogger.debug(this, "warehosueIndex: " + warehouseIndex);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");

		IWarehouseReceipt objSec = null;
		if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)
				&& return_event.equals(CommDocAction.EVENT_MAIN_PAGE)) {
			IWarehouseReceipt actualObj = null;
			ICommodityTitleDocument docObj;
			if (trxValue.getCommodityDeal() != null) {
				docObj = getTitleDoc(trxValue.getCommodityDeal().getTitleDocsLatest(), index);
				if (docObj == null) {
					docObj = getTitleDoc(trxValue.getCommodityDeal().getTitleDocsHistory(), index);
				}
				if (docObj != null) {
					actualObj = getWarehouseReceipt(docObj.getWarehouseReceipts(), warehouseIndex);
				}
			}

			IWarehouseReceipt stageObj = null;
			if (trxValue.getStagingCommodityDeal() != null) {
				docObj = getTitleDoc(trxValue.getStagingCommodityDeal().getTitleDocsAll(), index);
				if (docObj != null) {
					stageObj = getWarehouseReceipt(docObj.getWarehouseReceipts(), warehouseIndex);
				}
			}
			objSec = stageObj;
			if (objSec == null) {
				objSec = actualObj;
			}
			result.put("actualTitleDocWarehouse", actualObj);
			result.put("stageTitleDocWarehouse", stageObj);
		}
		else {
			if (warehouseIndex < 0) {
				objSec = new OBWarehouseReceipt();
			}
			else {
				if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
					objSec = getWarehouseReceipt(titleDoc.getWarehouseReceipts(), warehouseIndex);
				}
				else {
					objSec = titleDoc.getWarehouseReceipts()[(int) warehouseIndex];
				}
			}
		}
		result.put("from_event", from_event);
		result.put("titleDocWarehouseObj", objSec);

		String previous_event = (String) map.get("previous_event");
		result.put("previous_event", previous_event);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IWarehouseReceipt getWarehouseReceipt(IWarehouseReceipt temp[], long commonRef) {
		IWarehouseReceipt item = null;
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

	private ICommodityTitleDocument getTitleDoc(ICommodityTitleDocument temp[], long commonRef) {
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
