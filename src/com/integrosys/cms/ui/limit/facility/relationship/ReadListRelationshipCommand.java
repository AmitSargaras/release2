package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.bus.OBFacilityRelationship;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class ReadListRelationshipCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "nextTab", "java.lang.String", REQUEST_SCOPE },
                { "startIdx", "java.lang.String", REQUEST_SCOPE },
                { "errorMap", "java.util.Map", SERVICE_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "facilityRelationships", "[Lcom.integrosys.cms.app.limit.bus.IFacilityRelationship", REQUEST_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "recordSize", "java.lang.String", REQUEST_SCOPE }, { "startIdx", "java.lang.String", REQUEST_SCOPE },
				{ "facilityRelationshipObj", "com.integrosys.cms.app.limit.bus.IFacilityRelationship", SERVICE_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			// for ui tab
			String currentTab = (String) map.get("nextTab");
            //Andy Wong: standandize tab routing
            if(StringUtils.isNotBlank(currentTab)) {
                result.put("currentTab", currentTab);
            }

			// pagination
			String temp = "";
			if (map.get("startIdx") != null) {
				temp = (String) map.get("startIdx");
			}
			else
				temp = "0";
			int startIdx = Integer.parseInt(temp);
			int recordSize = 0;

            //Andy Wong: set relationship action errors into message list
            Map errorMap = (Map) map.get("errorMap");
            if (errorMap != null) {
                ActionErrors errorMessages = (ActionErrors) errorMap.get("relationship");
                if (errorMessages != null) {
                    DefaultLogger.warn(this, "there is error message for facility relationship, "
                            + "please retrieve from the display page.");
                    returnMap.put(MESSAGE_LIST, errorMessages);
                }
            }

			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			if (facilityMaster != null) {
				Set setRelationships = facilityMaster.getFacilityRelationshipSet();
				setRelationships = FacilityUtil.filterDeletedRelationship(setRelationships);
				List listRelationships = new ArrayList();
				if (setRelationships != null) {
					listRelationships.addAll(setRelationships);
				}
				recordSize = listRelationships.size();
				Collections.sort(listRelationships, new FacilityRelationshipComparator());

				if (listRelationships != null && listRelationships.size() != 0) {

					if ((startIdx + FacilityMainAction.RECORD_PER_PAGE) <= listRelationships.size()) {
						listRelationships = listRelationships.subList(startIdx,
								(startIdx + FacilityMainAction.RECORD_PER_PAGE));
					}
					else {
						listRelationships = listRelationships.subList(startIdx, startIdx
								+ ((listRelationships.size() - startIdx) % FacilityMainAction.RECORD_PER_PAGE));
					}
					IFacilityRelationship[] facilityRelationships = (IFacilityRelationship[]) listRelationships
							.toArray(new OBFacilityRelationship[listRelationships.size()]);
					Arrays.sort(facilityRelationships, new FacilityRelationshipComparator());
					result.put("facilityRelationships", facilityRelationships);
					result.put("startIdx", String.valueOf(startIdx));
				}
			}
			result.put("recordSize", String.valueOf(recordSize));
			result.put("facilityRelationshipObj", null);
			result.put("customerList", null);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
