/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/PrepareDealInfoCommand.java,v 1.10 2006/03/23 08:38:59 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/03/23 08:38:59 $ Tag: $Name: $
 */

public class PrepareDealInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "conCommProductType", "java.lang.String", REQUEST_SCOPE },
				{ "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
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
		return (new String[][] { { "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "productTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "productTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "priceTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "priceTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "uomID", "java.util.Collection", REQUEST_SCOPE },
				{ "uomValue", "java.util.Collection", REQUEST_SCOPE },
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE }, });
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

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		IProfile serviceProfile = (IProfile) map.get("profileService");
		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		if (serviceProfile != null) {
			profileID = serviceProfile.getProfileID();
		}

		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		String productType = (String) map.get("conCommProductType");
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		DefaultLogger.debug(this, "####################### product Type: " + productType);

		HashMap profileMap = CommodityDealUtil.getProfileDropDownList(dealCollateral, profileID, productType);
		Collection productTypeID = (Collection) profileMap.get("productTypeID");
		Collection productTypeValue = (Collection) profileMap.get("productTypeValue");
		Collection productSubTypeID = (Collection) profileMap.get("productSubTypeID");
		Collection productSubTypeValue = (Collection) profileMap.get("productSubTypeValue");
		boolean foundProfile = ((Boolean) profileMap.get("foundProfile")).booleanValue();

		if (!foundProfile) {
			DefaultLogger.debug(this, "profile: " + serviceProfile);
			if (serviceProfile != null) {
				if (!productTypeID.contains(serviceProfile.getProductType())) {
					productTypeID.add(serviceProfile.getProductType());
					productTypeValue.add(categoryList.getCommProductItem(serviceProfile.getCategory(), serviceProfile
							.getProductType()));
				}
				if ((productType != null) && productType.equals(serviceProfile.getProductType())) {
					if (!productSubTypeID.contains(String.valueOf(serviceProfile.getProfileID()))) {
						productSubTypeID.add(String.valueOf(serviceProfile.getProfileID()));
						productSubTypeValue.add(serviceProfile.getProductSubType());
					}
				}
			}
		}

		result.put("productTypeID", productTypeID);
		result.put("productTypeValue", productTypeValue);
		result.put("productSubTypeID", productSubTypeID);
		result.put("productSubTypeValue", productSubTypeValue);

		Collection priceTypeIdList = new ArrayList();
		Collection priceTypeValueList = new ArrayList();
		if (serviceProfile != null) {
			String priceType = serviceProfile.getPriceType();
			DefaultLogger.debug(this, " PriceType : " + priceType);
			PriceTypeList priceList = null;
			if (IProfile.PRICE_TYPE_NOC_RIC.equals(priceType)) {
				priceList = new PriceTypeList(PriceTypeList.LIST_NON_RIC_ONLY);
			}
			else {
				priceList = new PriceTypeList(PriceTypeList.LIST_RIC_ONLY);
			}
			priceTypeIdList = priceList.getPriceTypeID();
			priceTypeValueList = priceList.getPriceTypeValue();
		}
		result.put("priceTypeID", priceTypeIdList);
		result.put("priceTypeValue", priceTypeValueList);

		HashMap uomMap = (HashMap) map.get("uomMap");
		Collection uomID = new ArrayList();
		Collection uomValue = new ArrayList();

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<< HSHII: profile id: " + profileID);
		try {
			uomMap = new HashMap();
			UOMWrapper[] uomList = proxy.getUnitofMeasure(profileID);
			if (uomList != null) {
				Arrays.sort(uomList, new UOMWrapperComparator());
				for (int i = 0; i < uomList.length; i++) {
					UOMWrapper tempUOM = uomList[i];
					uomID.add(tempUOM.getID());
					uomValue.add(tempUOM.getLabel());
					uomMap.put(tempUOM.getID(), tempUOM);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("uomMap", uomMap);

		result.put("uomID", uomID);
		result.put("uomValue", uomValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
