/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/ReturnGeneralInfoCommand.java,v 1.8 2005/10/07 07:09:08 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/10/07 07:09:08 $ Tag: $Name: $
 */

public class ReturnGeneralInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "previous_event", "java.lang.String", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "generalInfoObj", "java.util.HashMap", FORM_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "originalSublimit", "com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit",
						REQUEST_SCOPE }, { "contractNo", "java.lang.String", REQUEST_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE }, });
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
		ICommodityDeal obj;
		if (from_event.equals(GeneralInfoAction.EVENT_READ)) {
			obj = trxValue.getCommodityDeal();
		}
		else {
			obj = trxValue.getStagingCommodityDeal();
		}
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");

		HashMap colLimitMap = (HashMap) map.get("commodityLimitMap");
		HashMap dealObjMap = new HashMap();
		dealObjMap.put("obj", obj);
		dealObjMap.put("commodityLimitMap", colLimitMap);
		dealObjMap.put("dealCollateral", dealCollateral);

		result.put("generalInfoObj", dealObjMap);
		result.put("securityID", String.valueOf(obj.getCollateralID()));

		if (obj.getLimitID() > 0) {
			result.put("limitID", String.valueOf(obj.getLimitID()));
		}
		if (obj.getContractID() > 0) {
			result.put("contractNo", String.valueOf(obj.getContractID()));
		}
		if (obj.getSubLimitID() > 0) {
			result.put("originalSublimit", getSubLimit(obj));
		}
		String previous_event = (String) map.get("previous_event");
		result.put("previous_event", previous_event);

		result.put("tab", CommodityDealConstant.TAB_GENERAL_INFO);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ISubLimit getSubLimit(ICommodityDeal dealObj) throws CommandProcessingException {
		DefaultLogger.debug(this, "getSubLimit - Begin.");
		try {
			ICommodityCollateral dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy()
					.getCollateral(dealObj.getCollateralID(), true);
			ICollateralLimitMap[] colLimitMapArray = dealCollateral.getCollateralLimits();
			long limitId = dealObj.getLimitID();
			long subLimitID = dealObj.getSubLimitID();
			DefaultLogger.debug(this, " - LimitId : " + limitId);
			DefaultLogger.debug(this, " - SubLimitId : " + subLimitID);
			for (int i = 0; i < colLimitMapArray.length; i++) {
				DefaultLogger.debug(this, " ColLmtMap - LimitId : " + colLimitMapArray[i].getLimitID());
				if (limitId == colLimitMapArray[i].getLimitID()) {
					ISubLimit[] slArray = colLimitMapArray[i].getSubLimit();
					DefaultLogger.debug(this, "Len of SL array : " + (slArray == null ? 0 : slArray.length));
					if (slArray != null) {
						for (int index = 0; index < slArray.length; index++) {
							DefaultLogger.debug(this, "ColLmtMap - SubLimitID : " + slArray[index].getSubLimitID());
							if (subLimitID == slArray[index].getSubLimitID()) {
								DefaultLogger.debug(this, "Found the Sub Limit.");
								return slArray[index];
							}
						}
					}
				}
			}
		}
		catch (CollateralException e) {
			throw new CommandProcessingException(e.getMessage());
		}
		finally {
			DefaultLogger.debug(this, "getSubLimit - End.");
		}
		return null;
	}
}
