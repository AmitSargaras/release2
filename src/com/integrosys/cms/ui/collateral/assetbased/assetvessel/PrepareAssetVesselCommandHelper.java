package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.FrequencyList;
import com.integrosys.cms.ui.common.YearOfManufactureList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetVesselCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coverageID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "coverageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vesselID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vesselValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "countryValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "yearValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "yearLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "vesselCharterRateUnitValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vesselCharterRateUnitLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "occupancyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "occupancyLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "vesselStateValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "vesselStateLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "insurersValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurersLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

		});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {

		CommonCodeList commonCode;

		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		CoverageTypeList coverageList = CoverageTypeList.getInstance();
		result.put("coverageID", coverageList.getCoverageTypeID());
		result.put("coverageValue", coverageList.getCoverageTypeValue());

		VesselTypeList vesselList = VesselTypeList.getInstance();
		result.put("vesselID", vesselList.getVesselTypeID());
		result.put("vesselValue", vesselList.getVesselTypeValue());

		YearOfManufactureList yearList = YearOfManufactureList.getInstance();
		result.put("yearValue", yearList.getYearValues());
		result.put("yearLabel", yearList.getYearLabels());

		CountryList countryList = CountryList.getInstance();
		result.put("countryLabel", countryList.getCountryLabels());
		result.put("countryValue", countryList.getCountryValues());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.CHARTER_RATE_UNIT_TYPE);
		result.put("vesselCharterRateUnitLabel", commonCode.getCommonCodeValues());
		result.put("vesselCharterRateUnitValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.OCCUPANCY_TYPE);
		result.put("occupancyValue", commonCode.getCommonCodeValues());
		result.put("occupancyLabel", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VESSEL_STATE_TYPE);
		result.put("vesselStateValue", commonCode.getCommonCodeValues());
		result.put("vesselStateLabel", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.INSURER_TYPE);
		result.put("insurersValue", commonCode.getCommonCodeValues());
		result.put("insurersLabel", commonCode.getCommonCodeLabels());

		return;
	}

}
