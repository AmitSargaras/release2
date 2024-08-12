/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/PreparePropertyCommandHelper.java,v 1.8 2003/09/15 09:51:31 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CMSLabelValueBean;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralUiUtil;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.FrequencyList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/09/15 09:51:31 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PreparePropertyCommandHelper {

	public static String[][] getParameterDescriptor() {
		return (new String[][] { { "isOrgChange", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationState", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationDistrict", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "developerNameIDX", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "developerNameMap", "java.util.Map", ICommonEventConstant.SERVICE_SCOPE } });
	}

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "SecEnvRisky", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LandAreaUOMID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LandAreaUOMValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "TenureID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "TenureValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationStateLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationStateValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "landUseLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "landUseValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "realEstateUsageLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "realEstateUsageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyUsageLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyUsageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyCompletionStatusLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyCompletionStatusValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "titleTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "titleTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationDistrictColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationMukimColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "scheduledLocationLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "scheduledLocationValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				//{ "nonPreferredLocationLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				//{ "nonPreferredLocationValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "developerNameColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "alphabetColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyCompletionStageLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "propertyCompletionStageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "commissionLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "commissionValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "lotLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "lotValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "assessmentOptionLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "assessmentOptionValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },				
				{ "developerNameIDX", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "developerNameMap", "java.util.Map", ICommonEventConstant.SERVICE_SCOPE },
				{ "developerGroupCompanyLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "developerGroupCompanyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		IPropertyCollateral col = null;
		String collateralLoc = null;
		String state = null;
		String district = null;
		ArrayList devNameList = null;
		
		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		CommonCodeList commonCode;

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.AREA_UOM);
		result.put("LandAreaUOMValue", commonCode.getCommonCodeLabels());
		result.put("LandAreaUOMID", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TENURE_PROPERTY);
		result.put("TenureValues", commonCode.getCommonCodeLabels());
		result.put("TenureID", commonCode.getCommonCodeValues());
		/*
		 * commonCode =
		 * CommonCodeList.getInstance(CategoryCodeConstant.ENV_RISK_STATUS);
		 * result.put("secRiskyValue", commonCode.getCommonCodeLabels());
		 * result.put("secRiskyID", commonCode.getCommonCodeValues());
		 */
		SecEnvRiskyList secRiskyList = SecEnvRiskyList.getInstance();
		result.put("secRiskyID", secRiskyList.getSecEnvRiskyID());
		result.put("secRiskyValue", secRiskyList.getSecEnvRiskyValue());

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		if (colTrxValue!=null) {
			col = (IPropertyCollateral) ((colTrxValue.getStagingCollateral() == null) ? colTrxValue
				.getCollateral() : colTrxValue.getStagingCollateral());
		

			collateralLoc = (String) map.get("collateralLoc");
			state = null;
			if ((collateralLoc == null) || collateralLoc.trim().equals("")) {
				collateralLoc = col.getCollateralLocation();
				state = col.getLocationState();
				district = col.getLocationDistrict();
			}
			else {
				state = (String) map.get("locationState");
				district = (String) map.get("locationDistrict");
			}		
		}
		
		if ("Y".equals((String) map.get("isOrgChange"))) {
			state = null;
		}
		// System.out.println("Loc:"+collateralLoc);
		// System.out.println("state:"+state);
		// System.out.println("district:"+district);
		// System.out.println("isOrg:"+map.get("isOrgChange"));

		commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.STATE_CATEGORY_CODE);
		result.put("locationStateLabel", commonCode.getCommonCodeLabels());
		result.put("locationStateValue", commonCode.getCommonCodeValues());

		ArrayList districtList = null;
		ArrayList mulimList = null;
		districtList = PreparePropertyCommandHelper.getCCList(state, CategoryCodeConstant.DISTRICT_CATEGORY_CODE);
		mulimList = PreparePropertyCommandHelper.getCCList(state, CategoryCodeConstant.MUKIM_CATEGORY_CODE);
			
		result.put("locationDistrictColl", districtList == null ? new ArrayList() : districtList);
		result.put("locationMukimColl", mulimList == null ? new ArrayList() : mulimList);
//		result.put("locationDistrictColl", getCCList(state, CategoryCodeConstant.DISTRICT_CATEGORY_CODE));
//		result.put("locationMukimColl", getCCList(district, CategoryCodeConstant.MUKIM_CATEGORY_CODE));

		Map developerNameMap = (Map) map.get("developerNameMap");

		if ((developerNameMap == null) || (collateralLoc!=null && !collateralLoc.equals(developerNameMap.get("collateralLoc")))) {
			developerNameMap = getInitalDevNameMap(collateralLoc);
			developerNameMap.put("collateralLoc", collateralLoc);
		}
		result.put("developerNameMap", developerNameMap);

		String developerNameIDX = null;
		if ("Y".equals((String) map.get("isOrgChange"))) {
			developerNameIDX = "-";// from edit page, refresh page but didnt
									// select devNameIdx,security location
									// changed.
		}
		else {
			developerNameIDX = (String) map.get("developerNameIDX");
		}
		if (developerNameIDX == null && col!=null) {
			// from view page,click edit button
			String devValue = col.getDeveloperName();
			String devLabel = CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.DEVELOPER_CODE, devValue);
			if (devLabel != null) {
				// to handle for cases where dev name does not start with A-z
				char firstChar = devLabel.charAt(0);
				int firstCharInt = (int) firstChar;				
				if (firstCharInt < 65 || firstCharInt > 122) {
					developerNameIDX = ICMSUIConstant.IDX_OTHERS;
				} else {
					developerNameIDX = String.valueOf(firstChar).toUpperCase();
				}
			}
		}
		// System.out.println("developerNameIDX:"+developerNameIDX);
		if (developerNameMap != null) {
			devNameList = (ArrayList) developerNameMap.get(developerNameIDX);
			// System.out.println("Size:"+(devNameList==null?0:devNameList.size()));
			result.put("developerNameColl", devNameList == null ? new ArrayList() : devNameList);
			result.put("developerNameIDX", developerNameIDX);
		}

		ArrayList alphabetColl = new ArrayList();
		for (int index = 65; index < 91; index++) {
			String upper = String.valueOf((char) index);
			String lower = String.valueOf((char) (index + 32));
			ArrayList itemList = new ArrayList();
			itemList.add(upper);
			itemList.add(upper + "(" + lower + ")");
			alphabetColl.add(itemList);
		}

		// put in for "Others" - handle for cases where dev name does not start with A-z
		ArrayList itemList = new ArrayList();
		itemList.add(ICMSUIConstant.IDX_OTHERS);
		itemList.add(ICMSUIConstant.IDX_OTHERS);
		alphabetColl.add(itemList);

		result.put("alphabetColl", alphabetColl);

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TITLE_TYPE);
		result.put("titleTypeValue", commonCode.getCommonCodeValues());
		result.put("titleTypeLabel", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(ICMSConstant.CATEGORY_OF_LAND_USE);
		result.put("landUseLabel", commonCode.getCommonCodeLabels());
		result.put("landUseValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(ICMSConstant.CATEGORY_PROP_TYPE);
		result.put("propTypeLabel", commonCode.getCommonCodeLabels());
		result.put("propTypeValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.REAL_ESTATE_USAGE);
		result.put("realEstateUsageLabel", commonCode.getCommonCodeLabels());
		result.put("realEstateUsageValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PROPERTY_USAGE_LIST);
		result.put("propertyUsageLabel", commonCode.getCommonCodeLabels());
		result.put("propertyUsageValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PROPERTY_COMPLETION_STATUS_LIST);
		result.put("propertyCompletionStatusLabel", commonCode.getCommonCodeLabels());
		result.put("propertyCompletionStatusValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.SCHEDULED_LOCATION);
		result.put("scheduledLocationLabel", commonCode.getCommonCodeLabels());
		result.put("scheduledLocationValue", commonCode.getCommonCodeValues());
/* use back scheduled location
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.NON_PREFERRED_LOCATION);
		result.put("nonPreferredLocationLabel", commonCode.getCommonCodeLabels());
		result.put("nonPreferredLocationValue", commonCode.getCommonCodeValues());
*/
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.COMMISSION_TYPE);
		result.put("commissionLabel", commonCode.getCommonCodeLabels());
		result.put("commissionValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.LOT_NO);
		result.put("lotLabel", commonCode.getCommonCodeLabels());
		result.put("lotValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PROPERTY_COMPLETION_STAGE);
		result.put("propertyCompletionStageLabel", commonCode.getCommonCodeLabels());
		result.put("propertyCompletionStageValue", commonCode.getCommonCodeValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ASSESSMENT_PERIOD);
		result.put("assessmentOptionLabel", commonCode.getCommonCodeLabels());
		result.put("assessmentOptionValue", commonCode.getCommonCodeValues());

		// WLS Aug 9, 2007: set developer group company in request scope
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.DEVELOPER_GROUP_COMPANY);
		result.put("developerGroupCompanyLabel", commonCode.getCommonCodeLabels());
		result.put("developerGroupCompanyValue", commonCode.getCommonCodeValues());

		return;
	}

	public static ArrayList getCCList(String state, String cc) {
		if ((state == null) || "".equals(state.trim()) || (cc == null) || "".equals(cc.trim())) {
			return new ArrayList();
		}
		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, cc, false, state);
		Collection labelList = commonCode.getCommonCodeLabels();
		Collection valueList = commonCode.getCommonCodeValues();
		return CollateralUiUtil.getLVBeanList(labelList, valueList);
	}

	private static Map getInitalDevNameMap(String collateralLoc) {
		Map developerNameMap = new HashMap();
		CommonCodeList commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.DEVELOPER_CODE);
		Collection valueColl = commonCode.getCommonCodeValues();
		Collection labelColl = commonCode.getCommonCodeLabels();
		for (int index = 65; index < 91; index++) {
			developerNameMap.put(String.valueOf((char) index), new ArrayList());
		}
		// put in list for "Others" - all that does not start with A-z
		developerNameMap.put(ICMSUIConstant.IDX_OTHERS, new ArrayList());

		for (Iterator iLabel = labelColl.iterator(), iValue = valueColl.iterator(); iLabel.hasNext();) {
			String label = (String) iLabel.next();
			String value = (String) iValue.next();
			CMSLabelValueBean lvBean = CollateralUiUtil.getLVBean(label, value);
			String inital = String.valueOf(label.charAt(0)).toUpperCase();
			ArrayList vlList = (ArrayList) developerNameMap.get(inital);
			// add to "Others" list - for all that does not start with A-z
			vlList = (vlList == null) ? (ArrayList)developerNameMap.get(ICMSUIConstant.IDX_OTHERS) : vlList;
			vlList.add(lvBean);
		}
		return developerNameMap;
	}
}
