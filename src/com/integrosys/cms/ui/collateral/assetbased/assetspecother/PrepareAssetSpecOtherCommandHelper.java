//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecother;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetSpecOtherCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "otherTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "otherTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

		});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {

		CommonCodeList commonCode;

		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.AB_OTHERS_TYPE);
		result.put("otherTypeID", commonCode.getCommonCodeValues());
		result.put("otherTypeValue", commonCode.getCommonCodeLabels());
		return;
	}

}
