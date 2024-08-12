package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class PrepareAddCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
                { "selectedId", "java.lang.String", REQUEST_SCOPE }
        });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "legalReference", "java.lang.String", REQUEST_SCOPE },
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
                { "selectedId", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        DefaultLogger.debug(this, "PrepareAddCommand::doExecute...");
        HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			// for ui tab
			/*
			 * String currentTab = (String) map.get("nextTab"); if
			 * (StringUtils.isBlank(currentTab)) { currentTab = "relationship"; }
			 * result.put("currentTab", currentTab);
			 */
			String selectedId = (String) map.get("selectedId");
            String legalReference = null;
			String legalName = null;
			boolean isFound = false;

//            if (selectedId != null && searchResult != null) {
//				List customerList = (List) searchResult.getResultList();
//				for (int i = 0; i < customerList.size(); i++) {
//					OBCustomerSearchResult ob = (OBCustomerSearchResult) customerList.get(i);
//					if (selectedId.equals(ob.getLegalReference())) {
//						legalReference = ob.getLegalReference();
//						legalName = ob.getLegalName();
//						isFound = true;
//						break;
//					}
//				}

//            }
//			if (isFound) {
                result.put("legalReference", legalReference);
				result.put("legalName", legalName);
//			}
            result.put("selectedId", selectedId);
        }
		catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
