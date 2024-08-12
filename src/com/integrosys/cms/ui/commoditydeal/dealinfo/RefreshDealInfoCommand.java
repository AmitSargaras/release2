/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/RefreshDealInfoCommand.java,v 1.21 2006/03/23 08:38:59 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.common.DifferentialSign;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2006/03/23 08:38:59 $ Tag: $Name: $
 */

public class RefreshDealInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "conCommProductType", "java.lang.String", REQUEST_SCOPE },
				{ "conCommProductSubType", "java.lang.String", REQUEST_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "dealInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
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
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "dealInfoObj", "java.util.HashMap", FORM_SCOPE },
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityDeal dealObj = (ICommodityDeal) map.get("dealInfoObj");
		IProfile profile = (IProfile) map.get("profileService");
		String productType = (String) map.get("conCommProductType");
		String productSubType = (String) map.get("conCommProductSubType");

		if ((dealObj.getContractID() != ICMSConstant.LONG_INVALID_VALUE) && (dealObj.getContractID() > 0)
				&& (profile != null)) {
			if ((productType != null) && !productType.equals(profile.getProductType())) {
				exceptionMap.put("conCommProductType", new ActionMessage("error.commodity.deal.diffprofile.type"));
			}
			if ((productSubType != null) && !productSubType.equals(String.valueOf(profile.getProfileID()))) {
				exceptionMap
						.put("conCommProductSubType", new ActionMessage("error.commodity.deal.diffprofile.subtype"));
			}
		}

		ICommodityMaintenanceProxy mainProxy = null;
		if (exceptionMap.isEmpty() && (productSubType != null) && (productSubType.length() > 0)) {
			long profileID = Long.parseLong(productSubType);
			DefaultLogger.debug(this, " New Profile Id : " + profileID);
			try {
				// ICommodityMaintenanceProxy mainProxy =
				// CommodityMaintenanceProxyFactory.getProxy();
				mainProxy = CommodityMaintenanceProxyFactory.getProxy();
				profile = mainProxy.getProfileByProfileID(profileID);
				profile = CommodityDealUtil.sortBuyerSupplier(profile);
				if (profile != null) {
					getLatestPrice(mainProxy, dealObj, profile, result);
					getMarketPriceUpdatedDate(mainProxy, profile, result);
				}
			}
			catch (Exception e) {
				throw new CommandProcessingException(e.getMessage());
			}
			result.put("profileService", profile);
		}
		else if (exceptionMap.isEmpty() && (productType != null) && (productType.length() > 0)
				&& ((productSubType == null) || (productSubType.length() == 0))) {
			profile = null;
			result.put("profileService", null);
		}
		DealInfoUtil util = new DealInfoUtil();

		DefaultLogger.debug(this, " - ContractPriceType : " + dealObj.getContractPriceType());
		if (profile != null) {
			dealObj.setContractRIC(profile.getReuterSymbol());
			if (util.isActualPriceRequired(dealObj.getContractPriceType())) {
				dealObj.setActualPrice(profile.getUnitPrice());
				dealObj.setActualMarketPriceDate(profile.getUnitPriceDate());
			}
		}

		if (util.isDifferRequired(dealObj.getContractPriceType())) {
			ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
			CurrencyCode ccyCode = null;
			if (dealObj.getActualPrice() != null) {
				ccyCode = dealObj.getActualPrice().getCurrencyCodeAsObject();
			}
			if ((dealObj.getActualEODCustomerDifferential() == null) && (dealCollateral != null)
					&& (dealCollateral.getApprovedCustomerDifferentialSign() != null)
					&& (dealCollateral.getApprovedCustomerDifferential() != null)) {
				Amount amt = new Amount(dealCollateral.getApprovedCustomerDifferential(), ccyCode);
				DifferentialSign sign = DifferentialSign.valueOf(dealCollateral.getApprovedCustomerDifferentialSign());
				PriceDifferential tmpPriceDiff = new PriceDifferential(amt, sign);
				dealObj.setActualEODCustomerDifferential(tmpPriceDiff);
			}
			if ((dealObj.getActualCommonDifferential() == null) && (profile != null)
					&& (profile.getPriceDifferential() != null) && (profile.getDifferentialSign() != null)) {
				Amount amt = new Amount(profile.getPriceDifferential(), ccyCode);
				DifferentialSign sign = DifferentialSign.valueOf(profile.getDifferentialSign());
				PriceDifferential tmpPriceDiff = new PriceDifferential(amt, sign);
				dealObj.setActualCommonDifferential(tmpPriceDiff);
			}
		}

		// if(profile != null
		// &&IProfile.PRICE_TYPE_NOC_RIC.equals(profile.getRICType())){
		// DefaultLogger.debug(this," - Non-RIC ");
		// //for another price types(cash,futures),the contract price type is
		// attained
		// //from form(in DealInfoMapper : mapFormToOB) when select a
		// particularly price Type
		// //but , for Non-RIC ,once the commodity type point to a Non-RIC
		// profile,the price type
		// //have to be non-RIC , don't need user action to select a price type.
		// dealObj.setContractPriceType(PriceType.NON_RIC_PRICE);
		// dealObj.setActualPrice(profile.getUnitPrice());
		// dealObj.setActualMarketPriceDate(profile.getUnitPriceDate());
		// CurrencyCode ccyCode = null;
		// if (dealObj.getActualPrice() != null) {
		// ccyCode = dealObj.getActualPrice().getCurrencyCodeAsObject();
		// }
		// if (dealObj.getActualCommonDifferential() == null && profile != null
		// &&
		// profile.getPriceDifferential() != null &&
		// profile.getDifferentialSign() != null) {
		// Amount amt = new Amount(profile.getPriceDifferential(), ccyCode);
		// DifferentialSign sign =
		// DifferentialSign.valueOf(profile.getDifferentialSign());
		// PriceDifferential tmpPriceDiff = new PriceDifferential(amt, sign);
		// dealObj.setActualCommonDifferential(tmpPriceDiff);
		// }
		// dealObj.setContractRIC(profile.getReuterSymbol());
		// }

		HashMap dealObjMap = new HashMap();
		dealObjMap.put("profile", profile);
		dealObjMap.put("obj", dealObj);
		result.put("dealInfoObj", dealObjMap);
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
	 * @param proxy ICommodityMaintenanceProxy
	 * @param dealObj ICommodityDeal object
	 * @param profile IProfile - for retrieval of the id of the commodity
	 *        profile
	 * @param result result map to return the results in
	 * @throws CommandProcessingException if there is an error in processing
	 */
	protected void getLatestPrice(ICommodityMaintenanceProxy proxy, ICommodityDeal dealObj, IProfile profile,
			HashMap result) throws CommandProcessingException { // CR141
		Date latestPriceUpdatedDate = null;
		Amount convertedLatestPrice = null;

		try {
			long profileID = profile.getProfileID();
			Amount latestPrice = proxy.getLatestPrice(profileID);
			latestPriceUpdatedDate = proxy.getLatestPriceFirstUpdateDate(profileID);
			DefaultLogger.debug(this, " Latest Price: " + latestPrice);
			DefaultLogger.debug(this, " Latest Price Updated Date: " + latestPriceUpdatedDate);

			try {
				// convert the latest price into local units (e.g. 1 Bag
				// (Market) = 5 Kg (local)) & into local currency
				// latestPrice.setAmount(latestPrice.getAmount()/unitConversion.
				// doubleValue());
				convertedLatestPrice = (dealObj == null) ? latestPrice : AmountConversion.getConversionAmount(
						latestPrice, dealObj.getDealAmtCCyCode());
				DefaultLogger.debug(this, " Converted Latest Price: " + convertedLatestPrice);
			}
			catch (AmountConversionException e) {
				convertedLatestPrice = latestPrice;
				DefaultLogger.debug(this, " Amount Conversion Exception!! Revert to price in foreign currency! ");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("latestPrice", convertedLatestPrice);
		result.put("latestPriceUpdatedDate", latestPriceUpdatedDate);
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
			throws CommandProcessingException {
		Date marketPriceUpdatedDate = null;
		try {
			marketPriceUpdatedDate = proxy.getMarketPriceFirstUpdateDate(profile.getProfileID());
			DefaultLogger.debug(this, " Market Price Updated Date: " + marketPriceUpdatedDate);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("marketPriceUpdatedDate", marketPriceUpdatedDate);
	}

}
