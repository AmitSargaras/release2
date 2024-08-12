//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.REQUEST_DUE_DATE_AND_STOCK_FORM_LIST;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetGenChargeCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				//{ "calculatedDP", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				//{ "fundedShare", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
//				{ "fundedShare", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				
				{ "dpShare", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "stockdocMonth", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "stockdocYear", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
			    { "totalLonable", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "serviceColObj", ICollateralTrxValue.class.getName(), ICommonEventConstant.SERVICE_SCOPE },
				
				{ REQUEST_DUE_DATE_AND_STOCK_FORM_LIST, List.class.getName() , ICommonEventConstant.REQUEST_SCOPE },
			});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		return;
	}

}
