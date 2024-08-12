/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/PrintApprovalSheetCommand.java,v 1.11 2004/08/12 08:42:26 lyng Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDealSearchResult;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitComparator;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.customer.CategoryCodeConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/08/12 08:42:26 $ Tag: $Name: $
 */
public class PrintApprovalSheetCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "totalAppLimit", "java.lang.String", REQUEST_SCOPE },
				{ "totalActivatedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "totalOpLimit", "java.lang.String", REQUEST_SCOPE },
				{ "dealSummary", "java.util.Collection", REQUEST_SCOPE },
				{ "limitSummary", "java.util.Collection", REQUEST_SCOPE }, });
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

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICMSCustomer customerOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		SearchResult sr = null;
		CommodityDealSearchCriteria objSearch = new CommodityDealSearchCriteria();
		ICommodityDealProxy dealproxy = CommodityDealProxyFactory.getProxy();
		OBTrxContext theOBTrxContext = new OBTrxContext();
		objSearch.setCustomerID(customerOB.getCustomerID());
		objSearch.setLimitProfileID(limitProfileOB.getLimitProfileID());
		try {
			sr = dealproxy.searchDeal(theOBTrxContext, objSearch);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		Collection dealSummary = new ArrayList();

		if (sr != null) {
			Collection col = sr.getResultList();
			if (col != null) {
				OBCommodityDealSearchResult[] deal = (OBCommodityDealSearchResult[]) col
						.toArray(new OBCommodityDealSearchResult[0]);
				for (int i = 0; i < deal.length; i++) {
					OBDealSummary obj = new OBDealSummary();
					obj.setSn(String.valueOf(i + 1));
					String type = CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.DEAL_TYPE,
							deal[i].getDealTypeCode());
					if ((type == null) || (type.length() == 0)) {
						type = "-";
					}
					obj.setType(type);
					String desc = CommodityCategoryList.getInstance().getCommProductItem(deal[i].getCategoryCode(),
							deal[i].getProdTypeCode())
							+ " " + deal[i].getProdSubtypeCode();
					if ((desc == null) || (desc.length() == 0)) {
						desc = "-";
					}
					obj.setCommodityDesc(desc);
					obj
							.setTpDealRef(((deal[i].getDealReferenceNo() == null) || (deal[i].getDealReferenceNo()
									.length() == 0)) ? "-" : deal[i].getDealReferenceNo());
					obj.setDealNo(((deal[i].getDealNo() == null) || (deal[i].getDealNo().length() == 0)) ? "-"
							: deal[i].getDealNo());
					if ((deal[i].getDealAmt() != null) && (deal[i].getDealAmt().getCurrencyCode() != null)) {
						try {
							obj.setDealAmt(deal[i].getDealAmt().getCurrencyCode() + " "
									+ CurrencyManager.convertToString(locale, deal[i].getDealAmt()));
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
					else {
						obj.setDealAmt("-");
					}
					if ((deal[i].getMarketPrice() != null) && (deal[i].getMarketPrice().getCurrencyCode() != null)) {
						obj.setMarketPrice(deal[i].getMarketPrice().getCurrencyCode() + " "
								+ MapperUtil.mapDoubleToString(deal[i].getMarketPrice().getAmount(), 6, locale));
					}
					else {
						obj.setMarketPrice("-");
					}
					if ((deal[i].getQuantity() != null) && (deal[i].getQuantity().getQuantity() != null)
							&& (deal[i].getQuantity().getQuantity().doubleValue() > 0)) {
						try {
							obj.setQty(UIUtil.formatNumber(deal[i].getQuantity().getQuantity(), 4, locale) + " "
									+ deal[i].getQuantity().getUnitofMeasure().getLabel());
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
					else {
						obj.setQty("-");
					}
					if ((deal[i].getDealCMV() != null) && (deal[i].getDealCMV().getCurrencyCode() != null)) {
						try {
							obj.setDealCMV(deal[i].getDealCMV().getCurrencyCode() + " "
									+ CurrencyManager.convertToString(locale, deal[i].getDealCMV()));
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
					else {
						obj.setDealCMV("-");
					}
					if ((deal[i].getDealFSV() != null) && (deal[i].getDealFSV().getCurrencyCode() != null)) {
						try {
							obj.setDealFSV(deal[i].getDealFSV().getCurrencyCode() + " "
									+ CurrencyManager.convertToString(locale, deal[i].getDealFSV()));
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
					}
					else {
						obj.setDealFSV("-");
					}
					if (deal[i].getIsDealSecured()) {
						obj.setDealSecureStatus("Y");
					}
					else {
						obj.setDealSecureStatus("N");
					}
					if (deal[i].getActualCashReqRecdAmt() != null) {
						if ((deal[i].getActualCashReqRecdAmt().getAmount() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (deal[i].getActualCashReqRecdAmt().getCurrencyCode() != null)
								&& deal[i].getActualCashReqRecdAmt().getCurrencyCode().equals("")) {
							obj.setCashReqAmt("Forex Error");
						}
						else {
							try {
								obj.setCashReqAmt(deal[i].getActualCashReqRecdAmt().getCurrencyCode() + " "
										+ CurrencyManager.convertToString(locale, deal[i].getActualCashReqRecdAmt()));
							}
							catch (Exception e) {
								throw new CommandProcessingException(e.getMessage());
							}
						}
					}
					else {
						obj.setCashReqAmt("-");
					}
					dealSummary.add(obj);
				}
			}
		}

		Collection limitSummary = new ArrayList();
		double totalAppLimit = 0;
		double totalActivatedLimit = 0;
		double totalOpLimit = 0;
		ForexHelper fr = new ForexHelper();
		CurrencyCode currencyUSD = new CurrencyCode(IGlobalConstant.DEFAULT_CURRENCY);
		CountryList countryList = CountryList.getInstance();
		if (limitProfileOB != null) {
			ILimit[] limit = limitProfileOB.getLimits();
			if (limit != null) {
				Arrays.sort(limit, new LimitComparator());
				for (int i = 0; i < limit.length; i++) {
					boolean isInnerLimit = ((limit[i].getOuterLimitRef() != null)
							&& (limit[i].getOuterLimitRef().length() > 0) && !limit[i].getOuterLimitRef().equals("0"));
					OBLimitSummary obj = new OBLimitSummary();
					obj.setSn(String.valueOf(i + 1));
					obj.setLimitID(((limit[i].getLimitRef() == null) || (limit[i].getLimitRef().length() == 0)) ? "-"
							: limit[i].getLimitRef());
					String productDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
							CategoryCodeConstant.PRODUCT_DESCRIPTION, limit[i].getProductDesc());
					if ((productDesc == null) || (productDesc.length() == 0)) {
						productDesc = "-";
					}
					obj.setProductDesc(productDesc);
					String country = countryList.getCountryName(limit[i].getBookingLocation().getCountryCode());
					if ((country == null) || (country.length() == 0)) {
						country = "-";
					}
					obj.setLimitBookingLoc(country);
					if ((limit[i].getApprovedLimitAmount() != null)
							&& (limit[i].getApprovedLimitAmount().getCurrencyCode() != null)) {
						try {
							String strAppLimit = limit[i].getApprovedLimitAmount().getCurrencyCode() + " ";
							if (isInnerLimit) {
								strAppLimit += "(";
							}
							strAppLimit += CurrencyManager.convertToString(locale, limit[i].getApprovedLimitAmount());
							if (isInnerLimit) {
								strAppLimit += ")";
							}
							obj.setApprovedLimit(strAppLimit);
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
						if (totalAppLimit != ICMSConstant.DOUBLE_INVALID_VALUE) {
							try {
								totalAppLimit += fr.convertAmount(limit[i].getApprovedLimitAmount(), currencyUSD);
							}
							catch (Exception e) {
								DefaultLogger.error(this, "Forex Conversion exception... ");
								totalAppLimit = ICMSConstant.DOUBLE_INVALID_VALUE;
							}
						}
					}
					else {
						obj.setApprovedLimit("-");
					}
					if ((limit[i].getActivatedLimitAmount() != null)
							&& (limit[i].getActivatedLimitAmount().getCurrencyCode() != null)) {
						try {
							String strActLimit = limit[i].getActivatedLimitAmount().getCurrencyCode() + " ";
							if (isInnerLimit) {
								strActLimit += "(";
							}
							strActLimit += CurrencyManager.convertToString(locale, limit[i].getActivatedLimitAmount());
							if (isInnerLimit) {
								strActLimit += ")";
							}
							obj.setTpOutstandingLimit(strActLimit);
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
						if (totalActivatedLimit != ICMSConstant.DOUBLE_INVALID_VALUE) {
							try {
								totalActivatedLimit += fr
										.convertAmount(limit[i].getActivatedLimitAmount(), currencyUSD);
							}
							catch (Exception e) {
								DefaultLogger.error(this, "Forex Conversion exception... ");
								totalActivatedLimit = ICMSConstant.DOUBLE_INVALID_VALUE;
							}
						}
					}
					else {
						obj.setTpOutstandingLimit("-");
					}
					if ((limit[i].getOperationalLimit() != null)
							&& (limit[i].getOperationalLimit().getCurrencyCode() != null)) {
						try {
							obj.setOperationalLimit(limit[i].getOperationalLimit().getCurrencyCode() + " "
									+ CurrencyManager.convertToString(locale, limit[i].getOperationalLimit()));
						}
						catch (Exception e) {
							throw new CommandProcessingException(e.getMessage());
						}
						if (totalOpLimit != ICMSConstant.DOUBLE_INVALID_VALUE) {
							try {
								totalOpLimit += fr.convertAmount(limit[i].getOperationalLimit(), currencyUSD);
							}
							catch (Exception e) {
								DefaultLogger.error(this, "Forex Conversion exception... ");
								totalOpLimit = ICMSConstant.DOUBLE_INVALID_VALUE;
							}
						}
					}
					else {
						obj.setOperationalLimit("-");
					}

					Collection securityID = new ArrayList();
					Collection securityDesc = new ArrayList();
					Collection securityLoc = new ArrayList();
					if (limit[i].getCollateralAllocations() != null) {
						ICollateralAllocation[] colAllocation = limit[i].getCollateralAllocations();
						for (int j = 0; j < colAllocation.length; j++) {
							// if (colAllocation[j].getCollateral() instanceof
							// ICommodityCollateral) {
							ICollateral col = colAllocation[j].getCollateral();
							securityID.add(String.valueOf(col.getSCISecurityID()));
							securityDesc.add(col.getCollateralType().getTypeName() + "/"
									+ col.getCollateralSubType().getSubTypeName());
							securityLoc.add(countryList.getCountryName(col.getCollateralLocation()));
							// }
						}
					}
					else {
						securityID.add("-");
						securityDesc.add("-");
						securityLoc.add("-");
					}
					obj.setSecurityID(securityID);
					obj.setSecurityDesc(securityDesc);
					obj.setSecurityLoc(securityLoc);

					limitSummary.add(obj);
				}
			}
		}

		String strTotalAppLimit = "-";
		if (totalAppLimit == ICMSConstant.DOUBLE_INVALID_VALUE) {
			strTotalAppLimit = "Forex Error";
		}
		else {
			strTotalAppLimit = IGlobalConstant.DEFAULT_CURRENCY + " "
					+ MapperUtil.mapDoubleToString(totalAppLimit, 0, locale);
		}
		String strTotalActivatedLimit = "-";
		if (totalActivatedLimit == ICMSConstant.DOUBLE_INVALID_VALUE) {
			strTotalActivatedLimit = "Forex Error";
		}
		else {
			strTotalActivatedLimit = IGlobalConstant.DEFAULT_CURRENCY + " "
					+ MapperUtil.mapDoubleToString(totalActivatedLimit, 0, locale);
		}
		String strTotalOpLimit = "-";
		if (totalOpLimit == ICMSConstant.DOUBLE_INVALID_VALUE) {
			strTotalOpLimit = "Forex Error";
		}
		else {
			strTotalOpLimit = IGlobalConstant.DEFAULT_CURRENCY + " "
					+ MapperUtil.mapDoubleToString(totalOpLimit, 0, locale);
		}

		result.put("totalAppLimit", strTotalAppLimit);
		result.put("totalActivatedLimit", strTotalActivatedLimit);
		result.put("totalOpLimit", strTotalOpLimit);
		result.put("dealSummary", dealSummary);
		result.put("limitSummary", limitSummary);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
