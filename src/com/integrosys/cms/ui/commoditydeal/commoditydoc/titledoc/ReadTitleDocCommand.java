/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/ReadTitleDocCommand.java,v 1.14 2006/09/19 13:25:21 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.WarehouseReceiptComparator;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/09/19 13:25:21 $ Tag: $Name: $
 */

public class ReadTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "return_page", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "previous_event", "java.lang.String", SERVICE_SCOPE }, });
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
						REQUEST_SCOPE },
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE }, });
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
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		String event = (String) map.get("event");
		String previous_event = (String) map.get("previous_event");

		ICommodityTitleDocument objSec = null;
		if (event.equals(EVENT_PREPARE)) {
			objSec = new OBCommodityTitleDocument();
		}
		else {
			long index = Long.parseLong((String) map.get("indexID"));
			String return_event = (String) map.get("return_page");
			if (return_event.equals(TitleDocAction.EVENT_MAIN_PAGE)) {
				if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
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
					objSec = stageObj;
					if (stageObj == null) {
						objSec = actualObj;
					}
					result.put("actualTitleDoc", actualObj);
					result.put("stageTitleDoc", stageObj);
				}
				else if ((from_event != null) && from_event.equals(TitleDocAction.EVENT_READ)) {
					if ((previous_event != null) && previous_event.equals(TitleDocAction.EVENT_USER_PROCESS)) {
						objSec = trxValue.getStagingCommodityDeal().getTitleDocsLatest()[(int) index];
					}
					else {
						objSec = trxValue.getCommodityDeal().getTitleDocsLatest()[(int) index];
					}
				}
				else {
					if ((trxValue.getStatus() == null) || ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
						if ((from_event != null) && from_event.equals(TitleDocAction.EVENT_TRACK)) {
							objSec = trxValue.getStagingCommodityDeal().getTitleDocsAll()[(int) index];
						}
						else {
							objSec = trxValue.getStagingCommodityDeal().getTitleDocsLatest()[(int) index];
						}
					}
					else {
						objSec = trxValue.getStagingCommodityDeal().getTitleDocsAll()[(int) index];
					}
					DefaultLogger.debug(this, "<<<<<<<<<<<<<<, aHSHII: isAnyWRTitleDoc: "
							+ trxValue.getStagingCommodityDeal().getIsAnyWRTitleDoc());
				}
			}
			else {
				if ((from_event != null) && from_event.equals(TitleDocAction.EVENT_READ)) {
					objSec = getItem(trxValue.getCommodityDeal().getTitleDocsHistory(), index);
				}
				else {
					DefaultLogger.debug(this, "<<<<<<<<<<<<<<, bHSHII: isAnyWRTitleDoc: "
							+ trxValue.getStagingCommodityDeal().getIsAnyWRTitleDoc());
					/*
					 * if
					 * (event.equals(TitleDocAction.EVENT_PREPARE_UPDATE_SUB)) {
					 * objSec =
					 * trxValue.getStagingCommodityDeal().getTitleDocsHistory
					 * ()[(int)index]; } else {
					 */
					objSec = getItem(trxValue.getStagingCommodityDeal().getTitleDocsHistory(), index);
					// }
					if ((from_event != null) && from_event.equals(CommodityDealAction.EVENT_PROCESS)) {
						result.put("actualTitleDoc", objSec);
						result.put("stageTitleDoc", objSec);
					}
				}
			}
		}
		if ((objSec.getTitleDocType() != null) && (objSec.getWarehouseReceipts() != null)) {
			IWarehouseReceipt[] wrList = objSec.getWarehouseReceipts();
			DefaultLogger.debug(this, "wrList length: " + wrList.length);
			Arrays.sort(wrList, new WarehouseReceiptComparator(WarehouseReceiptComparator.BY_WR_ISSUE_DATE));
			objSec.setWarehouseReceipts(wrList);
		}

		result.put("from_event", from_event);
		result.put("titleDocObj", objSec);
		try {
			ICommodityTitleDocument serviceObj = (ICommodityTitleDocument) AccessorUtil.deepClone(objSec);
			result.put("serviceTitleDocObj", serviceObj);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

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
