package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-12-12
 * @Tag com.integrosys.cms.ui.common.CountryOrgCodeUtil.java
 */
public class CountryOrgCodeUtil {
	public static void fillCountryOrgCode2Map(HashMap map, IBookingLocation creditLoc, IBookingLocation seniorLoc) {
		ArrayList creditOrgCodeLabels = new ArrayList();
		ArrayList creditOrgCodeValues = new ArrayList();
		ArrayList seniorOrgCodeLabels = new ArrayList();
		ArrayList seniorOrgCodeValues = new ArrayList();
		if (creditLoc != null) {
			fillOrgCode2List(creditLoc.getCountryCode(), creditOrgCodeValues, creditOrgCodeLabels);
		}
		if (seniorLoc != null) {
			fillOrgCode2List(seniorLoc.getCountryCode(), seniorOrgCodeValues, seniorOrgCodeLabels);
		}
		map.put("creditOrgCodeValues", creditOrgCodeValues);
		map.put("creditOrgCodeLabels", creditOrgCodeLabels);
		map.put("seniorOrgCodeValues", seniorOrgCodeValues);
		map.put("seniorOrgCodeLabels", seniorOrgCodeLabels);

		CountryList list = CountryList.getInstance();
		map.put("countryValues", list.getCountryValues());
		map.put("countryLabels", list.getCountryLabels());
	}

	public static void fillOrgCode2List(String countryCode, ArrayList valuesList, ArrayList labelsList) {
		if (countryCode == null) {
			return;
		}
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		try {
			IBookingLocation bkg[] = proxy.getBookingLocationByCountry(countryCode);
			if (bkg != null) {
				DefaultLogger.debug(CountryOrgCodeUtil.class, "Length of BookingLocation : " + bkg.length);
				if (valuesList == null) {
					valuesList = new ArrayList();
				}
				if (labelsList == null) {
					labelsList = new ArrayList();
				}
				for (int i = 0; i < bkg.length; i++) {
					valuesList.add(bkg[i].getOrganisationCode());
					labelsList.add(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.ORG_CODE, bkg[i]
							.getOrganisationCode()));
				}
			}
		}
		catch (LimitException e) {
			e.printStackTrace();
		}
	}
}