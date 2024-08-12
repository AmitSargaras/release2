//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

public class RefreshDropDownCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "locationState", "java.lang.String", REQUEST_SCOPE },
				{ "dropdown_name", "java.lang.String", REQUEST_SCOPE },
				{ "developerNameIDX", "java.lang.String", REQUEST_SCOPE },
				{ "developerNameMap", "java.util.Map", SERVICE_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "dropdown_name", "java.lang.String", REQUEST_SCOPE },
				{ "locationDistrictColl", "java.util.Collection", REQUEST_SCOPE },
				{ "locationMukimColl", "java.util.Collection", REQUEST_SCOPE },
				{ "developerNameColl", "java.util.Collection", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		String dropdown_name = (String) map.get("dropdown_name");
		String locationState2 = (String) map.get("locationState");

		DefaultLogger.debug(this, "dropdown_name : " + dropdown_name);
		DefaultLogger.debug(this, "State : " + locationState2);

		ArrayList districtList = null;
		ArrayList mulimList = null;
		ArrayList devNameList = null;

		if ("locationDistrictColl".equals(dropdown_name)) {
			String locationState = (String) map.get("locationState");
			districtList = PreparePropertyCommandHelper.getCCList(locationState,
					CategoryCodeConstant.DISTRICT_CATEGORY_CODE);
			// mulimList = new ArrayList();
		}
		else if ("locationMukimColl".equals(dropdown_name)) {
			String locationState = (String) map.get("locationState");
			// districtList = new ArrayList();
			mulimList = PreparePropertyCommandHelper.getCCList(locationState, CategoryCodeConstant.MUKIM_CATEGORY_CODE);
		}
		else {
			Map developerNameMap = (Map) map.get("developerNameMap");
			if (developerNameMap != null) {
				String inital = (String) map.get("developerNameIDX");
				// DefaultLogger.debug(this, "inital : " + inital);
				if (inital != null) {
					devNameList = (ArrayList) developerNameMap.get(inital);
				}
			}
		}

		resultMap.put("locationDistrictColl", districtList == null ? new ArrayList() : districtList);
		resultMap.put("locationMukimColl", mulimList == null ? new ArrayList() : mulimList);
		resultMap.put("developerNameColl", devNameList == null ? new ArrayList() : devNameList);
		resultMap.put("dropdown_name", dropdown_name);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
