package com.integrosys.cms.ui.collateral.guarantees.gtegovtlink;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

public class PrepareGteGovtLinkCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "schemeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "schemeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {

		CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.SCHEME);
		result.put("schemeValue", commonCode.getCommonCodeValues());
		result.put("schemeLabel", commonCode.getCommonCodeLabels());

		return;
	}

}
