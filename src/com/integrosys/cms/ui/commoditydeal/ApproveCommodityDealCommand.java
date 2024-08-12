/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/ApproveCommodityDealCommand.java,v 1.10 2004/07/13 05:22:32 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/07/13 05:22:32 $ Tag: $Name: $
 */

public class ApproveCommodityDealCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue", REQUEST_SCOPE } });
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
		ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		trxValue.setCommodityCollateral(dealCollateral);
		try {
			if (trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CLOSE)) {
				ICommodityDeal staging = trxValue.getStagingCommodityDeal();
				staging.setTitleDocsLatest(staging.getTitleDocsAll());
				trxValue.setStagingCommodityDeal(staging);
				trxValue = proxy.checkerApproveCommodityDeal(ctx, trxValue);
			}
			else {
				trxValue.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
				trxValue.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
				trxValue.setToAuthGroupTypeId(ICMSConstant.LONG_INVALID_VALUE);
				trxValue = proxy.officerApproveCommodityDeal(ctx, trxValue);
			}
			result.put("request.ITrxValue", trxValue);
		}
		catch (CommodityDealException e) {
			if (e.getMessage().startsWith("AmountConversionException")) {
				result.put("amountEx", "amountEx");
				result.put("request.ITrxValue", trxValue);
			}
			else {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
