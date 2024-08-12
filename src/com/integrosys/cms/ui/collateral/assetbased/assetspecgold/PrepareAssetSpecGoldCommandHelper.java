//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * PrepareAssetSpecGoldCommandHelper
 * 
 * Describe this class. Purpose: PrepareAssetSpecGoldCommandHelper Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class PrepareAssetSpecGoldCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldGradeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldGradeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldUOMValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "goldUOMLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leTypeValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });

	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		// NatureOfChargeList list = NatureOfChargeList.getInstance();
		// result.put("natureOfChargeID", list.getNatureOfChargeListID());
		// result.put("natureOfChargeValue", list.getNatureOfChargeListValue());

		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		GoldTypeList goldTypeList = GoldTypeList.getInstance();
		result.put("goldTypeValue", goldTypeList.getGoldTypeValue());
		result.put("goldTypeLabel", goldTypeList.getGoldTypeLabel());

		GoldGradeList goldGradeList = GoldGradeList.getInstance();
		result.put("goldGradeValue", goldGradeList.getGoldGradeValue());
		result.put("goldGradeLabel", goldGradeList.getGoldGradeLabel());

		GoldUOMList goldUOMList = GoldUOMList.getInstance();
		result.put("goldUOMValue", goldUOMList.getGoldUOMValue());
		result.put("goldUOMLabel", goldUOMList.getGoldUOMLabel());

		return;
	}

}
