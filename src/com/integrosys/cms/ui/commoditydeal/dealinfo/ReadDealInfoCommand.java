/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/ReadDealInfoCommand.java,v 1.19 2006/03/23 08:38:59 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.common.DifferentialSign;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/03/23 08:38:59 $ Tag: $Name: $
 */

public class ReadDealInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "previous_event", "java.lang.String", SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE }, });
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
				{ "conCommProductType", "java.lang.String", REQUEST_SCOPE },
				{ "dealInfoObj", "java.util.HashMap", FORM_SCOPE },
				// {"profileService",
				// "com.integrosys.cms.app.commodity.main.bus.profile.IProfile",
				// SERVICE_SCOPE},
				{ "actualProfile", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", REQUEST_SCOPE },
				{ "stageProfile", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", REQUEST_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "latestPrice", "com.integrosys.base.businfra.currency.Amount", REQUEST_SCOPE },
				{ "latestPriceUpdatedDate", "java.util.Date", REQUEST_SCOPE },
				{ "marketPriceUpdatedDate", "java.util.Date", REQUEST_SCOPE },
		// {"latestPriceUOM", "java.lang.String", REQUEST_SCOPE},
		});
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		String from_event = (String) map.get("from_event");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		String previous = (String) map.get("previous_event");

		ICommodityDeal obj;
		if (from_event.equals(DealInfoAction.EVENT_READ)
				&& ((previous == null) || !previous.equals(DealInfoAction.EVENT_USER_PROCESS))) {
			obj = trxValue.getCommodityDeal();
		}
		else {
			obj = trxValue.getStagingCommodityDeal();
			if (from_event.equals(DealInfoAction.EVENT_PROCESS)) {
				if ((trxValue.getCommodityDeal() != null) && (trxValue.getCommodityDeal().getContractProfileID() > 0)) {
					try {
						IProfile profile = proxy.getProfileByProfileID(trxValue.getCommodityDeal()
								.getContractProfileID());
						result.put("actualProfile", profile);
					}
					catch (Exception e) {
						throw new CommandProcessingException(e.getMessage());
					}
				}
				if ((trxValue.getStagingCommodityDeal() != null)
						&& (trxValue.getStagingCommodityDeal().getContractProfileID() > 0)) {
					try {
						IProfile profile = proxy.getProfileByProfileID(trxValue.getStagingCommodityDeal()
								.getContractProfileID());
						result.put("stageProfile", profile);
					}
					catch (Exception e) {
						throw new CommandProcessingException(e.getMessage());
					}
				}
			}
		}

		HashMap dealInfoMap = new HashMap();
		IProfile profile = (IProfile) map.get("profileService");
		// reduce unnecessarily proxy call
		/*
		 * if (obj.getContractProfileID() != ICMSConstant.LONG_INVALID_VALUE &&
		 * obj.getContractProfileID() > 0) { try { profile =
		 * proxy.getProfileByProfileID(obj.getContractProfileID());
		 * result.put("profileService", profile);
		 * result.put("conCommProductType", profile.getProductType());
		 * dealInfoMap.put("profile", profile); } catch (Exception e) {
		 * e.printStackTrace(); throw new
		 * CommandProcessingException(e.getMessage()); } }
		 */

		DealInfoUtil util = new DealInfoUtil();
		if (profile != null) {
			result.put("conCommProductType", profile.getProductType());
			dealInfoMap.put("profile", profile);

			if (from_event.equals(DealInfoAction.EVENT_PREPARE_UPDATE)
					|| DealInfoAction.EVENT_PROCESS_UPDATE.equals(from_event)
					|| DealInfoAction.EVENT_PREPARE_ADD_DEAL.equals(from_event)) {
				obj.setContractRIC(profile.getReuterSymbol());
				if (util.isActualPriceRequired(obj.getContractPriceType())) {
					obj.setActualPrice(profile.getUnitPrice());
					obj.setActualMarketPriceDate(profile.getUnitPriceDate());
				}
			}
		}

		if (util.isDifferRequired(obj.getContractPriceType())) {
			ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
			CurrencyCode ccyCode = null;
			if (obj.getActualPrice() != null) {
				ccyCode = obj.getActualPrice().getCurrencyCodeAsObject();
			}
			if ((obj.getActualEODCustomerDifferential() == null) && (dealCollateral != null)
					&& (dealCollateral.getApprovedCustomerDifferentialSign() != null)
					&& (dealCollateral.getApprovedCustomerDifferential() != null)) {
				Amount amt = new Amount(dealCollateral.getApprovedCustomerDifferential(), ccyCode);
				DifferentialSign sign = DifferentialSign.valueOf(dealCollateral.getApprovedCustomerDifferentialSign());
				PriceDifferential tmpPriceDiff = new PriceDifferential(amt, sign);
				obj.setActualEODCustomerDifferential(tmpPriceDiff);
			}
			if ((obj.getActualCommonDifferential() == null) && (profile != null)
					&& (profile.getPriceDifferential() != null) && (profile.getDifferentialSign() != null)) {
				Amount amt = new Amount(profile.getPriceDifferential(), ccyCode);
				DifferentialSign sign = DifferentialSign.valueOf(profile.getDifferentialSign());
				PriceDifferential tmpPriceDiff = new PriceDifferential(amt, sign);
				obj.setActualCommonDifferential(tmpPriceDiff);
			}
		}

		// ---- CR141; UAT(SIT)1.4, JIRA CMS-2485 Defect#73 -----
		if (profile != null) {
			getLatestPrice(proxy, obj, profile, result);
			getMarketPriceUpdatedDate(proxy, profile, result);
		}

		dealInfoMap.put("obj", obj);
		result.put("dealInfoObj", dealInfoMap);

		String previous_event = (String) map.get("previous_event");
		result.put("previous_event", previous_event);
		result.put("tab", CommodityDealConstant.TAB_DEAL_INFO);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	/**
	 * To retrieve the previous EOD price (termed latest price) from the
	 * commodity price feed for display. The latest price will be converted to
	 * the deal's currency and unit of measurement before display. If there is
	 * no conversion rate between the retrieved latest price original currency
	 * and the deal's currency, then the latest price in its original currency
	 * will be returned.
	 * 
	 * The date of when the price is first updated (to current price) is also
	 * retreived. E.g: Date | Price ---------------- 11/11 | 11.00 12/11 | 11.00
	 * 13/11 | 12.00 14/11 | 12.00 15/11 | 12.00 <-- assuming this is today
	 * 
	 * The date retrieved will be 13/11, since that is when it was first updated
	 * to this current price. (Currently when the price stays the same, it is
	 * assumed that there is NO update.)
	 * 
	 * @param proxy ICommodityMaintenanceProxy
	 * @param obj ICommodityDeal object
	 * @param profile IProfile - for retrieval of the id of the commodity
	 *        profile
	 * @param result result map to return the results in
	 * @throws CommandProcessingException if there is an error in processing
	 */
	protected void getLatestPrice(ICommodityMaintenanceProxy proxy, ICommodityDeal obj, IProfile profile, HashMap result)
			throws CommandProcessingException { // CR141

		Amount latestPrice = null;
		Date latestPriceUpdatedDate = null;
		// String latestPriceUOMValue = null;
		Amount convertedLatestPrice = null;

		try {
			long profileID = profile.getProfileID();
			latestPrice = proxy.getLatestPrice(profileID);
			latestPriceUpdatedDate = proxy.getLatestPriceFirstUpdateDate(profileID);
			DefaultLogger.debug(this, "####################### Latest Price: " + latestPrice);
			DefaultLogger.debug(this, "####################### Latest Price Updated Date: " + latestPriceUpdatedDate);

			/*
			 * //this should never be null. QuantityConversionRate
			 * latestPriceConversionRate =
			 * obj.getContractQuantity().getUnitofMeasure
			 * ().getMarketUOMConversionRate();
			 * 
			 * String latestPriceUOMKey =
			 * latestPriceConversionRate.getKey().getToUnit(); BigDecimal
			 * unitConversion = latestPriceConversionRate.getRate();
			 * DefaultLogger.debug(this,
			 * "####################### Deal's UOM(Market): "
			 * +latestPriceUOMKey); DefaultLogger.debug(this,
			 * "####################### Deal's unitConversionRate: "
			 * +unitConversion);
			 */

			try { // convert the latest price into local units (e.g. 1 Bag
					// (Market) = 5 Kg (local)) & into local currency
				// latestPrice.setAmount(latestPrice.getAmount()/unitConversion.
				// doubleValue());
				convertedLatestPrice = (obj == null) ? latestPrice : AmountConversion.getConversionAmount(latestPrice,
						obj.getDealAmtCCyCode());
				DefaultLogger.debug(this, "####################### Converted Latest Price: " + convertedLatestPrice);
			}
			catch (AmountConversionException e) {
				convertedLatestPrice = latestPrice;
				DefaultLogger
						.debug(this,
								"####################### Amount Conversion Exception!! Revert to price in foreign currency! #######################");
			}

			/*
			 * latestPriceUOMValue =
			 * (CommonDataSingleton.getCodeCategoryLabelByValue("MKT_OTHER_UOM",
			 * latestPriceUOMKey) == null) ?
			 * CommonDataSingleton.getCodeCategoryLabelByValue("MKT_METRIC_UOM",
			 * latestPriceUOMKey) :
			 * CommonDataSingleton.getCodeCategoryLabelByValue("MKT_OTHER_UOM",
			 * latestPriceUOMKey);
			 * 
			 * 
			 * DefaultLogger.debug(this,
			 * "####################### Deal's Market Label (MKT_OTHER_UOM): "
			 * +CommonDataSingleton.getCodeCategoryLabelByValue("MKT_OTHER_UOM",
			 * latestPriceUOMKey)); DefaultLogger.debug(this,
			 * "####################### Deal's Market Label (MKT_METRIC_UOM): "
			 * +CommonDataSingleton
			 * .getCodeCategoryLabelByValue("MKT_METRIC_UOM",
			 * latestPriceUOMKey));
			 */

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		result.put("latestPrice", convertedLatestPrice);
		result.put("latestPriceUpdatedDate", latestPriceUpdatedDate);
		// result.put("latestPriceUOM", latestPriceUOMValue);
	}

	/**
	 * See getLatestPrice() javadoc for the date retrieval logic. Its the same
	 * except it retrieves the date when the price is first updated (to current
	 * closed price). It returns null if current close price does not match the
	 * last updated close price in the staging price table (This scenario will
	 * occur when the batch job runs since the batch job does not update the
	 * staging table).
	 * 
	 * See CommodityPriceDAO.getClosePriceFirstUpdateDate() for more detailed
	 * explanation.
	 * 
	 * @param proxy ICommodityMaintenanceProxy
	 * @param profile IProfile - for retrieval of the id of the commodity
	 *        profile
	 * @param result result map to return the results in
	 * @throws CommandProcessingException if there is an error in processing
	 */
	protected void getMarketPriceUpdatedDate(ICommodityMaintenanceProxy proxy, IProfile profile, HashMap result)
			throws CommandProcessingException { // CR141

		// JIRA 2484 Defect #73 - related to CR141
		Date marketPriceUpdatedDate = null;
		try {
			marketPriceUpdatedDate = proxy.getMarketPriceFirstUpdateDate(profile.getProfileID());
			DefaultLogger.debug(this, "####################### Market Price Updated Date: " + marketPriceUpdatedDate);

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("marketPriceUpdatedDate", marketPriceUpdatedDate);
	}
}
