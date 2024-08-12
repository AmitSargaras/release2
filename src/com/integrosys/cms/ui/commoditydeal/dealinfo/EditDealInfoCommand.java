/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/EditDealInfoCommand.java,v 1.15 2005/03/18 03:26:00 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.common.DifferentialSign;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/03/18 03:26:00 $ Tag: $Name: $
 */
public class EditDealInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "dealInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
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

		ICommodityDeal dealObj = (ICommodityDeal) map.get("dealInfoObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IProfile profile = (IProfile) map.get("profileService");
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if ((dealObj.getContractQuantity() != null) && (dealObj.getActualQuantity() != null)) {
			BigDecimal actualQty = dealObj.getActualQuantity().getQuantity();
			BigDecimal maxQty = dealObj.getContractQuantity().getQuantity();
			DefaultLogger.debug(this, "<<<<<<<<<<<< actual Qty: " + actualQty + "\tmaxQty: " + maxQty);
			BigDecimal minQty = null;
			if ((dealObj.getContractQuantityDifferential() != null)
					&& (dealObj.getContractQuantityDifferential().getQuantity() != null)
					&& (dealObj.getContractQuantityDifferential().getQuantity().getQuantity() != null)) {
				try {
					Quantity tmp = dealObj.getContractQuantityDifferential().calculate(dealObj.getContractQuantity());
					if (maxQty.compareTo(tmp.getQuantity()) > 0) {
						minQty = tmp.getQuantity();
					}
					else {
						minQty = maxQty;
						maxQty = tmp.getQuantity();
					}
					if (dealObj.getContractQuantityDifferential().getSign().equals(
							DifferentialSign.NEGATIVE_DIFFERENTIAL)) {
						maxQty = minQty;
					}
				}
				catch (Exception e) {
					throw new CommandProcessingException(e.getMessage());
				}
			}
			if (actualQty != null) {
				/*
				 * if (minQty != null) { DefaultLogger.debug(this,
				 * "actualQty: "+
				 * actualQty+"\tminQty: "+minQty+"\tmaxQty: "+maxQty); if
				 * (actualQty.compareTo(minQty) < 0) { try {
				 * exceptionMap.put("actQtyVolume", new
				 * ActionMessage("error.commodity.deal.dealinfo.actualQty.range"
				 * , UIUtil.formatNumber(minQty, 4, locale),
				 * UIUtil.formatNumber(maxQty, 4, locale))); } catch (Exception
				 * e) { throw new CommandValidationException(e.getMessage()); }
				 * } else if (actualQty.compareTo(maxQty) > 0) { try {
				 * exceptionMap.put("actQtyVolume", new
				 * ActionMessage("error.commodity.deal.dealinfo.actualQty.range"
				 * , UIUtil.formatNumber(minQty, 4, locale),
				 * UIUtil.formatNumber(maxQty, 4, locale))); } catch (Exception
				 * e) { throw new CommandValidationException(e.getMessage()); }
				 * } } else {
				 */
				DefaultLogger.debug(this, "actualQty: " + actualQty + "\tmaxQty: " + maxQty);
				if (actualQty.compareTo(maxQty) > 0) {
					try {
						exceptionMap.put("actQtyVolume",
								new ActionMessage("error.commodity.deal.dealinfo.actualQty.range", UIUtil.formatNumber(
										maxQty, 4, locale)));
					}
					catch (Exception e) {
						throw new CommandValidationException(e.getMessage());
					}
				}
				// }
			}
		}
		if (dealObj.getActualQuantity() != null) {
			Quantity totalWRQty = dealObj.getTotalWRQuantity();
			DefaultLogger.debug(this, "<<<<<<<<<<<<<< total WR Qty: " + totalWRQty);
			if ((dealObj.getActualQuantity() != null) && (dealObj.getActualQuantity().getQuantity() != null)
					&& (totalWRQty != null) && (totalWRQty.getQuantity() != null)) {
				if (totalWRQty.getQuantity().compareTo(dealObj.getActualQuantity().getQuantity()) > 0) {
					exceptionMap.put("actQtyVolume", new ActionMessage(
							"error.commodity.deal.dealinfo.actualQty.more.totalwarehouseqty"));
				}
			}
		}
		if ((dealObj.getContractPriceType() != null)
				&& (dealObj.getContractPriceType().equals(PriceType.EOD_PRICE) || dealObj.getContractPriceType()
						.equals(PriceType.MANUAL_EOD_PRICE))) {
			ICommodityDeal actualDealObj = trxValue.getCommodityDeal();
			boolean isSignValidChange = false;
			boolean isValueValidChange = false;
			if (dealObj.getActualEODCustomerDifferential() != null) {
				if (dealObj.getActualEODCustomerDifferential().getSign() != null) {
					if ((actualDealObj != null)
							&& (actualDealObj.getActualEODCustomerDifferential() != null)
							&& (actualDealObj.getActualEODCustomerDifferential().getSign() != null)
							&& dealObj.getActualEODCustomerDifferential().getSign().equals(
									actualDealObj.getActualEODCustomerDifferential().getSign())) {
						isSignValidChange = true;
					}
					if (!isSignValidChange
							&& (dealCollateral != null)
							&& (dealCollateral.getApprovedCustomerDifferentialSign() != null)
							&& dealCollateral.getApprovedCustomerDifferentialSign().equals(
									dealObj.getActualEODCustomerDifferential().getSign().getName())) {
						isSignValidChange = true;
					}
				}
				if (dealObj.getActualEODCustomerDifferential().getPrice() != null) {
					Amount amount = dealObj.getActualEODCustomerDifferential().getPrice();
					if (amount.getAmountAsBigDecimal() != null) {
						try {
							String amountStr = UIUtil.formatNumber(amount.getAmountAsBigDecimal(), 6, locale);
							if ((actualDealObj != null) && (actualDealObj.getActualEODCustomerDifferential() != null)
									&& (actualDealObj.getActualEODCustomerDifferential().getPrice() != null)) {
								String actualAmtStr = UIUtil.formatNumber(actualDealObj
										.getActualEODCustomerDifferential().getPrice().getAmountAsBigDecimal(), 6,
										locale);
								if (amountStr.equals(actualAmtStr)) {
									isValueValidChange = true;
								}
							}
							if (!isValueValidChange && (dealCollateral != null)
									&& (dealCollateral.getApprovedCustomerDifferential() != null)) {
								if (amount.getAmountAsBigDecimal().compareTo(
										dealCollateral.getApprovedCustomerDifferential()) < 1) {
									isValueValidChange = true;
								}
								/*
								 * String colDiffStr =
								 * UIUtil.formatNumber(dealCollateral
								 * .getApprovedCustomerDifferential(), 6,
								 * locale); if (amountStr.equals(colDiffStr)) {
								 * isValueValidChange = true; }
								 */
							}
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
				}
				else {
					if ((actualDealObj == null) || (actualDealObj.getActualEODCustomerDifferential() == null)
							|| (actualDealObj.getActualEODCustomerDifferential().getPrice() == null)) {
						isValueValidChange = true;
					}
					else if ((dealCollateral != null) && (dealCollateral.getApprovedCustomerDifferential() == null)) {
						isValueValidChange = true;
					}
				}
			}
			else {
				if ((actualDealObj == null) || (actualDealObj.getActualEODCustomerDifferential() == null)) {
					isSignValidChange = true;
					isValueValidChange = true;
				}
				else if ((dealCollateral == null)
						|| ((dealCollateral.getApprovedCustomerDifferentialSign() == null) && (dealCollateral
								.getApprovedCustomerDifferential() == null))) {
					isSignValidChange = true;
					isValueValidChange = true;
				}
			}
			if (!isSignValidChange) {
				exceptionMap.put("actEODCustDiffSign", new ActionMessage("error.commodity.deal.dealinfo.eod.custdiff"));
			}
			else {
				if (!isValueValidChange) {
					exceptionMap.put("actEODCustDiff", new ActionMessage("error.commodity.deal.dealinfo.eod.custdiff"));
				}
			}

			isSignValidChange = false;
			isValueValidChange = false;
			if (dealObj.getActualCommonDifferential() != null) {
				DefaultLogger.debug(this, "<<<<<<<<<<<<< dealobj. common differential: "
						+ dealObj.getActualCommonDifferential().getPrice());
				DefaultLogger.debug(this, "<<<<<<<<<<<<<<<< profile differential: " + profile.getPriceDifferential());
				if (dealObj.getActualCommonDifferential().getSign() != null) {
					if ((actualDealObj != null)
							&& (actualDealObj.getActualCommonDifferential() != null)
							&& (actualDealObj.getActualCommonDifferential().getSign() != null)
							&& dealObj.getActualCommonDifferential().getSign().equals(
									actualDealObj.getActualCommonDifferential().getSign())) {
						isSignValidChange = true;
					}
					if (!isSignValidChange
							&& (profile != null)
							&& (profile.getDifferentialSign() != null)
							&& profile.getDifferentialSign().equals(
									dealObj.getActualCommonDifferential().getSign().getName())) {
						isSignValidChange = true;
					}
				}
				if (dealObj.getActualCommonDifferential().getPrice() != null) {
					Amount amount = dealObj.getActualCommonDifferential().getPrice();
					if (amount.getAmountAsBigDecimal() != null) {
						try {
							String commDiffStr = UIUtil.formatNumber(amount.getAmountAsBigDecimal(), 6, locale);
							if ((actualDealObj != null) && (actualDealObj.getActualCommonDifferential() != null)
									&& (actualDealObj.getActualCommonDifferential().getPrice() != null)) {
								String actualCommDiffStr = UIUtil.formatNumber(actualDealObj
										.getActualCommonDifferential().getPrice().getAmountAsBigDecimal(), 6, locale);
								if (commDiffStr.equals(actualCommDiffStr)) {
									isValueValidChange = true;
								}
							}
							if (!isValueValidChange && (profile != null) && (profile.getPriceDifferential() != null)) {
								if (amount.getAmountAsBigDecimal().compareTo(profile.getPriceDifferential()) < 1) {
									isValueValidChange = true;
								}
								/*
								 * String profileStr =
								 * UIUtil.formatNumber(profile
								 * .getPriceDifferential(), 6, locale); if
								 * (commDiffStr.equals(profileStr)) {
								 * isValueValidChange = true; }
								 */
							}
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
				}
				else {
					if ((actualDealObj == null) || (actualDealObj.getActualCommonDifferential() == null)
							|| (actualDealObj.getActualCommonDifferential().getPrice() == null)) {
						isValueValidChange = true;
					}
					else if ((profile != null) && (profile.getPriceDifferential() == null)) {
						isValueValidChange = true;
					}
				}
			}
			else {
				if ((actualDealObj == null) || (actualDealObj.getActualCommonDifferential() == null)) {
					isSignValidChange = true;
					isValueValidChange = true;
				}
				else if ((profile == null)
						|| ((profile.getDifferentialSign() == null) && (profile.getPriceDifferential() == null))) {
					isSignValidChange = true;
					isValueValidChange = true;
				}
			}
			if (!isSignValidChange) {
				exceptionMap.put("actEODCommDiffSign", new ActionMessage("error.commodity.deal.dealinfo.eod.commdiff"));
			}
			else {
				if (!isValueValidChange) {
					exceptionMap.put("actEODCommDiff", new ActionMessage("error.commodity.deal.dealinfo.eod.commdiff"));
				}
			}
		}
		if (exceptionMap.isEmpty()) {
			trxValue.setStagingCommodityDeal(dealObj);

			if (dealObj.getContractProfileID() > 0) {
				if ((profile == null) || (dealObj.getContractProfileID() != profile.getProfileID())) {
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
			}

			result.put("profileService", profile);
			result.put("commodityDealTrxValue", trxValue);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
