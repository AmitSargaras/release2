//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetPostDatedChqsCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "branchList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		NatureOfChargeList list = NatureOfChargeList.getInstance();
		result.put("natureOfChargeID", list.getNatureOfChargeListID());
		result.put("natureOfChargeValue", list.getNatureOfChargeListValue());

		return;
	}

}
