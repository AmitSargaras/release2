/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/EditGeneralInfoCommand.java,v 1.10 2006/09/26 03:14:01 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/26 03:14:01 $ Tag: $Name: $
 */
public class EditGeneralInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "generalInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
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
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE }, });
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

		ICommodityDeal dealObj = (ICommodityDeal) map.get("generalInfoObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IProfile profile = (IProfile) map.get("profileService");
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		ICollateralLimitMap[] colLimitMapList = dealCollateral.getCollateralLimits();

		if (((dealObj.getCashMarginPct() < 0) || (dealObj.getCashReqPct() < 0)) && (colLimitMapList != null)) {
			boolean found = false;
			for (int i = 0; !found && (i < colLimitMapList.length); i++) {
				if ((dealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER) && (dealObj
						.getLimitID() == colLimitMapList[i].getLimitID()))
						|| (dealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER) && (dealObj
								.getCoBorrowerLimitID() == colLimitMapList[i].getCoBorrowerLimitID()))) {
					found = true;
					if (colLimitMapList[i].getCashReqPct() >= 0) {
						if (dealObj.getCashMarginPct() < 0) {
							exceptionMap.put("marginCash", new ActionMessage("error.integer.mandatory"));
						}
						if (dealObj.getCashReqPct() < 0) {
							exceptionMap.put("cashRequirement", new ActionMessage("error.integer.mandatory"));
						}
					}
				}
			}
		}

		if (exceptionMap.isEmpty()) {
			if (dealObj.getContractProfileID() > 0) {
				try {
					ICommodityMaintenanceProxy mainProxy = CommodityMaintenanceProxyFactory.getProxy();
					profile = mainProxy.getProfileByProfileID(dealObj.getContractProfileID());
					profile = CommodityDealUtil.sortBuyerSupplier(profile);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
			if ((trxValue.getCommodityDeal() == null) && (dealObj.getContractID() > 0)
					&& (trxValue.getStagingCommodityDeal().getContractID() != dealObj.getContractID())) {
				IContract contract = CommodityDealUtil.getContractByID(dealCollateral, dealObj.getContractID());
				// DefaultLogger.debug(this,
				// "<<<< contract id is changed: contract: "+contract);
				if (contract != null) {
					dealObj.setContractPrice(contract.getContractPrice());
					dealObj.setContractQuantity(contract.getContractedQty());
					if ((contract.getContractedQty() != null)
							&& (contract.getContractedQty().getUnitofMeasure() != null)) {
						dealObj.setContractMarketUOMConversionRate(contract.getContractedQty().getUnitofMeasure()
								.getMarketUOMConversionRate());
						dealObj.setContractMetricUOMConversionRate(contract.getContractedQty().getUnitofMeasure()
								.getMetricUOMConversionRate());
					}
					else {
						dealObj.setContractMarketUOMConversionRate(null);
						dealObj.setContractMetricUOMConversionRate(null);
					}
					QuantityDifferential qtyDiff = contract.getQtyDifferential();
					if ((contract.getContractedQty() != null) && (contract.getContractedQty().getQuantity() != null)
							&& (qtyDiff != null) && (qtyDiff.getQuantity() != null)
							&& (qtyDiff.getQuantity().getQuantity() != null)) {
						BigDecimal tmpQtyDiffValue = CommonUtil.calcAfterPercent(contract.getContractedQty()
								.getQuantity(), qtyDiff.getQuantity().getQuantity().doubleValue());
						Quantity tmpQty = new Quantity(tmpQtyDiffValue, qtyDiff.getQuantity().getUnitofMeasure());
						qtyDiff.setQuantity(tmpQty);
					}
					dealObj.setContractQuantityDifferential(qtyDiff);
				}
			}

			result.put("profileService", profile);

			trxValue.setStagingCommodityDeal(dealObj);
		}
		result.put("commodityDealTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
