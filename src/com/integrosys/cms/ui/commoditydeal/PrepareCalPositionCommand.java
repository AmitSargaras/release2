/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/PrepareCalPositionCommand.java,v 1.7 2006/09/18 03:33:30 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyUtil;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/18 03:33:30 $ Tag: $Name: $
 */
public class PrepareCalPositionCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "productType", "java.lang.String", REQUEST_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "productTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "productTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "uomIDList", "java.util.Collection", REQUEST_SCOPE },
				{ "uomValueList", "java.util.Collection", REQUEST_SCOPE }, });
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

		long securityID = ICMSConstant.LONG_INVALID_VALUE;

		// long limitID = ICMSConstant.LONG_INVALID_VALUE;
		String strSecID = (String) map.get("securityID");
		if ((strSecID != null) && (strSecID.length() > 0)) {
			securityID = Long.parseLong(strSecID);
		}

		String strLimitID = (String) map.get("limitID");
		// if (strLimitID != null && strLimitID.length() > 0) {
		// limitID = Long.parseLong(strLimitID);
		// }

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ITrxContext aTrxContext = (ITrxContext) map.get("theOBTrxContext");

		HashMap commodityLimitMap = new HashMap();

		try {
			ILimit[] newLimits = LimitProxyFactory.getProxy().getFilteredNilColCheckListLimits(aTrxContext,
					limitProfile);
			limitProfile.setLimits(newLimits);

			commodityLimitMap = CollateralProxyUtil.getCommodityLimitMaps(limitProfile);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		commodityLimitMap = CommodityDealUtil.getCustomerLimit(commodityLimitMap);
		HashMap generalInfoMap = CommodityDealUtil.getGeneralInfoDropDownList(commodityLimitMap, securityID,
				strLimitID, false, false);
		Collection secID = (Collection) generalInfoMap.get("secID");
		Collection secValue = (Collection) generalInfoMap.get("secValue");
		Collection limitValues = (Collection) generalInfoMap.get("limitIDValues");
		Collection limitLabels = (Collection) generalInfoMap.get("limitIDLabels");

		result.put("secID", secID);
		result.put("secValue", secValue);
		result.put("limitValues", limitValues);
		result.put("limitLabels", limitLabels);

		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		IProfile profile = (IProfile) map.get("profileService");

		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		if (profile != null) {
			profileID = profile.getProfileID();
		}
		String productType = (String) map.get("productType");

		if (dealCollateral != null) {
			HashMap profileMap = CommodityDealUtil.getProfileDropDownList(dealCollateral, profileID, productType);
			Collection productTypeID = (Collection) profileMap.get("productTypeID");
			Collection productTypeValue = (Collection) profileMap.get("productTypeValue");
			Collection productSubTypeID = (Collection) profileMap.get("productSubTypeID");
			Collection productSubTypeValue = (Collection) profileMap.get("productSubTypeValue");

			result.put("productTypeID", productTypeID);
			result.put("productTypeValue", productTypeValue);
			result.put("productSubTypeID", productSubTypeID);
			result.put("productSubTypeValue", productSubTypeValue);
		}
		else {
			result.put("productTypeID", new ArrayList());
			result.put("productTypeValue", new ArrayList());
			result.put("productSubTypeID", new ArrayList());
			result.put("productSubTypeValue", new ArrayList());
		}

		try {
			HashMap uomMap = CommodityDealUtil.getUOMDropDownListByProfile(profileID);
			Collection uomID = (Collection) uomMap.get("uomID");
			Collection uomValue = (Collection) uomMap.get("uomValue");
			result.put("uomIDList", uomID);
			result.put("uomValueList", uomValue);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
