/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/UpdateCommodityPriceCommand.java,v 1.4 2006/03/03 01:39:10 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/03 01:39:10 $ Tag: $Name: $
 */

public class UpdateCommodityPriceCommand extends AbstractCommand implements ICMSUIConstant, CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commPriceTrxValue", "com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ AN_RIC_TYPE, CN_STRING, REQUEST_SCOPE }, { "commodityPriceObj", CN_COLLECTION, FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.commodity.main.trx.price.ICommodityPriceTrxValue", REQUEST_SCOPE }, });
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

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setCustomer(null);
		ctx.setLimitProfile(null);

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		ICommodityPriceTrxValue trxValue = (ICommodityPriceTrxValue) map.get("commPriceTrxValue");
		ICommodityPrice[] commPriceList = (ICommodityPrice[]) ((Collection) map.get("commodityPriceObj"))
				.toArray(new ICommodityPrice[0]);
		try {
			if ((commPriceList == null) || (commPriceList.length == 0)) {
				exceptionMap.put("commodityPriceList", new ActionMessage("error.commodityprice.empty"));
			}
			else {
				String ricType = (String) map.get(AN_RIC_TYPE);
				trxValue = proxy.makerSaveCommodityPrice(ctx, trxValue, commPriceList, ricType);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		result.put("request.ITrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
