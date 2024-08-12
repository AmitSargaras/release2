//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetBasedCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "secRiskyID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "brandID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "brandValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "modelNoID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "modelNoValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		CommonCodeList commonCode;

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ASSET_MODEL_TYPE);
		result.put("modelNoID", commonCode.getCommonCodeValues());
		result.put("modelNoValue", commonCode.getCommonCodeLabels());

		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.VEHICLE_BRAND);
		result.put("brandID", commonCode.getCommonCodeValues());
		result.put("brandValue", commonCode.getCommonCodeLabels());

		SecEnvRiskyList secRiskyList = SecEnvRiskyList.getInstance();
		result.put("secRiskyID", secRiskyList.getSecEnvRiskyID());
		result.put("secRiskyValue", secRiskyList.getSecEnvRiskyValue());

		return;
	}
}
