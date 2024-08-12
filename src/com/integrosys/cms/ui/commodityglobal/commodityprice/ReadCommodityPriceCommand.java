/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/ReadCommodityPriceCommand.java,v 1.5 2006/10/12 03:18:29 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue;
import com.integrosys.cms.app.commodity.main.trx.price.OBCommodityPriceTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/10/12 03:18:29 $ Tag: $Name: $
 */

public class ReadCommodityPriceCommand extends AbstractCommand implements CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "category", "java.lang.String", REQUEST_SCOPE },
				{ "commodityType", "java.lang.String", REQUEST_SCOPE },
				{ AN_RIC_TYPE, "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
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
				{ "commPriceTrxValue", "com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue",
						SERVICE_SCOPE }, { "commodityPriceObj", "java.util.Collection", FORM_SCOPE }, });
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

		String event = (String) map.get("event");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		ICommodityPriceTrxValue trxValue = new OBCommodityPriceTrxValue();
		String ricType = (String) map.get(AN_RIC_TYPE);
		DefaultLogger.debug(this, " - event : " + event);
		DefaultLogger.debug(this, " - RICType : " + ricType);
		if (!event.equals(EVENT_PREPARE) && !event.equals(EVENT_READ)) {
			String trxID = (String) map.get("trxID");
			DefaultLogger.debug(this, "trxID: " + trxID);
			try {
				trxValue = proxy.getCommodityPriceTrxValue(ctx, trxID, ricType);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get Commodity Price by trxID");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			String category = (String) map.get("category");
			String commodityType = (String) map.get("commodityType");
			DefaultLogger.debug(this, "Category: " + category);
			DefaultLogger.debug(this, "Product : " + commodityType);
			DefaultLogger.debug(this, "Status : " + trxValue.getStatus());
			try {
				trxValue = proxy.getCommodityPriceTrxValue(ctx, category, commodityType, ricType);
				if (event.equals(EVENT_PREPARE) && !trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
				if (trxValue.getCommodityPrice() != null) {
					ICommodityPrice[] staging = (ICommodityPrice[]) AccessorUtil
							.deepClone(trxValue.getCommodityPrice());
					trxValue.setStagingCommodityPrice(staging);
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get Commodity Price by category and product type");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		result.put("commPriceTrxValue", trxValue);
		if (trxValue.getStagingCommodityPrice() != null) {
			result.put("commodityPriceObj", Arrays.asList(trxValue.getStagingCommodityPrice()));
		}
		else {
			result.put("commodityPriceObj", null);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
