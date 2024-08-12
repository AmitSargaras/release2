/*
 * Created on Apr 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxyHome;
import com.integrosys.cms.ui.collateral.CollateralUiUtil;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AAUIHelper {
	public SBMIAAProxy getSBMIAAProxy() {
		return (SBMIAAProxy) (BeanController.getEJB(ICMSJNDIConstant.SB_MI_AA_PROXY_JNDI, SBMIAAProxyHome.class
				.getName()));
	}

	public List getOptionList(String source) {
		List result = new ArrayList();
		if ("limit".equals(source)) {
			// result.add(new LabelValueBean("Add Limits", "ADD_LIMIT"));
			result.add(new LabelValueBean("View / Edit Facility", "EDIT_LIMIT"));
		}
		else if ("aa".equals(source)) {
			result.add(new LabelValueBean("View / Edit AA", "EDIT_AA"));
			result.add(new LabelValueBean("Add New AA", "ADD_AA"));
			result.add(new LabelValueBean("Delete AA", "DELETE_AA"));
		}
		return result;
	}

	/**
	 * Refresh or return back to the manual input aa page. Those dropdown
	 * listing value will be reloaded.
	 * 
	 * @param map is of type HashMap
	 * @param resultMap is of type HashMap
	 * @param obLimitProfile is of type ILimitProfile
	 */
	public void refreshListing(HashMap map, HashMap resultMap, ILimitProfile obLimitProfile) {
		refreshListing(map, resultMap, obLimitProfile, null);
	}

	/**
	 * Refresh or return back to the manual input aa page. Those dropdown
	 * listing value will be reloaded.
	 * 
	 * @param map is of type HashMap
	 * @param resultMap is of type HashMap
	 * @param obLimitProfile is of type ILimitProfile
	 * @param countryCodeSelected is of type String
	 */
	public void refreshListing(HashMap map, HashMap resultMap, ILimitProfile obLimitProfile, String countryCodeSelected) {
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));

		String countryCode = null;

		if (countryCodeSelected != null) {
			countryCode = countryCodeSelected;
		}
		else {
			IBookingLocation bookingLoc = obLimitProfile.getOriginatingLocation();
			if (bookingLoc != null) {
				countryCode = bookingLoc.getCountryCode();
			}
		}

//		resultMap.put("countryList", getCountryList(team));
//		resultMap.put("orgList", getOrgList(countryCode, team));
//		resultMap.put("sourceSystemList", getSourceSystemList(countryCode, team));
		resultMap.put("InitialLimitProfile", obLimitProfile);

	}

	public List getCountryList(ITeam team) {
		List lbValList = new ArrayList();
		List idList = (List) (CountryList.getInstance().getCountryValues());
		List valList = (List) (CountryList.getInstance().getCountryLabels());
		for (int i = 0; i < idList.size(); i++) {
			String id = idList.get(i).toString();
			String val = valList.get(i).toString();
			boolean allowAdd = false;
			if (team.getCountryCodes() != null) {
				String[] countryCodes = team.getCountryCodes();
				for (int j = 0; j < countryCodes.length; j++) {
					if (countryCodes[j].equals(id)) {
						allowAdd = true;
						break;
					}
				}
			}
			if (allowAdd) {
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		}
		return lbValList;
	}

	public List getOrgList(String country, ITeam team) {
		//System.out.println("RETRIEVING ORGANISATION************** COUNTRY CODE"
		// + country);
		List lbValList = new ArrayList();
		List idList = new ArrayList();
		List valList = new ArrayList();
		if ((country != null) && !country.trim().equals("")) {
			idList = (List) (CommonCodeList.getInstance(country, ICMSConstant.CATEGORY_CODE_BKGLOC, true)
					.getCommonCodeValues());
			valList = (List) (CommonCodeList.getInstance(country, ICMSConstant.CATEGORY_CODE_BKGLOC, true)
					.getCommonCodeLabels());

			for (int i = 0; i < idList.size(); i++) {
				String nextKey = idList.get(i).toString();
				boolean allowAdd = false;
				if (team.getOrganisationCodes() != null) {
					String[] orgCodes = team.getOrganisationCodes();
					for (int j = 0; j < orgCodes.length; j++) {
						if (orgCodes[j].equals(nextKey)) {
							allowAdd = true;
							break;
						}
					}
				}
				if (allowAdd) {
					LabelValueBean lvBean = new LabelValueBean(valList.get(i).toString(), nextKey);
					lbValList.add(lvBean);
				}
			}// end for
		}
		return lbValList;
	}

	public List getSourceSystemList(String country, ITeam team) {
		// System.out.println(
		// "RETRIEVING SOURCE SYSTEM************** COUNTRY CODE" + country);
		long teamTypeID = team.getTeamType().getTeamTypeID();
		String type = ICMSConstant.AA_TYPE_BANK;
		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			type = ICMSConstant.AA_TYPE_TRADE;
		}

		List lbValList = new ArrayList();
		List idList = new ArrayList();
		List valList = new ArrayList();

		if ((country != null) && !country.trim().equals("")) {
			idList = (List) (CommonCodeList.getInstance(country, null, ICMSUIConstant.AA_SOURCE_CODE, true, type)
					.getCommonCodeValues());
			valList = (List) (CommonCodeList.getInstance(country, null, ICMSUIConstant.AA_SOURCE_CODE, true, type)
					.getCommonCodeLabels());
			for (int i = 0; i < idList.size(); i++) {
				LabelValueBean lvBean = new LabelValueBean(valList.get(i).toString(), idList.get(i).toString());
				lbValList.add(lvBean);
			}
		}
		return lbValList;
	}

	public void canAccess(ITeam team, IBookingLocation bookingLoc) throws AccessDeniedException {

//		List bcaLocationList = new ArrayList();
//		if (bookingLoc != null) {
//			bcaLocationList.add(bookingLoc);
//
//			boolean canAccess = CollateralUiUtil.isCollateralCanRead(team, bcaLocationList);
//			if (!canAccess) {
//				throw new AccessDeniedException("Location is not belongs to team location, " + bookingLoc);
//			}
//		}

	}
}
