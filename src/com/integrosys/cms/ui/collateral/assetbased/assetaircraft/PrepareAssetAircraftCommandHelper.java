//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetaircraft;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetAircraftCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "crdtAgencyID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "crdtAgencyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "aircraftLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "aircraftValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, 
				});
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		ExportCrdtAgencyList agencyList = ExportCrdtAgencyList.getInstance();
		result.put("crdtAgencyID", agencyList.getExportCrdtAgencyID());
		result.put("crdtAgencyValue", agencyList.getExportCrdtAgencyValue());

		InsurerList insurList = InsurerList.getInstance();
		result.put("insurID", insurList.getInsurerID());
		result.put("insurValue", insurList.getInsurerValue());

		FrequencyList freqList = FrequencyList.getInstance();
		result.put("freqValue", freqList.getFrequencyLabel());
		result.put("freqID", freqList.getFrequencyProperty());

		// get the custodian list
		CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.AIRCRAFT_TYPE);
		result.put("aircraftLabels", commonCode.getCommonCodeLabels());
		result.put("aircraftValues", commonCode.getCommonCodeValues());
		
		return;
	}

}
