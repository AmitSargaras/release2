package com.integrosys.cms.ui.limit.facility.officer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;
import com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator;
import org.apache.struts.action.ActionErrors;

public class ReadOfficerCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "key", "java.lang.String", REQUEST_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "compareResultsOfficerList", "java.util.List", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ OfficerForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityOfficer", FORM_SCOPE },
				{ "facilityOfficerObj", "com.integrosys.cms.app.limit.bus.IFacilityOfficer", SERVICE_SCOPE },
				{ "facilityOfficerActual", "com.integrosys.cms.app.limit.bus.IFacilityOfficer", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {

			IFacilityMaster facilityMaster = (IFacilityMaster) map.get("facilityMasterObj");
			String key = (String) map.get("key");
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
			if (facilityMaster != null && key != null) {
				IFacilityOfficer facilityOfficerStaging = null;
				IFacilityOfficer facilityOfficerActual = null;
				if (facilityTrxValue == null
						|| !(ICMSConstant.STATE_PENDING_UPDATE.equals(facilityTrxValue.getStatus()) || ICMSConstant.STATE_PENDING_CREATE
								.equals(facilityTrxValue.getStatus()))) {
					Set setOfficers = facilityMaster.getFacilityOfficerSet();
					if (setOfficers != null) {
						setOfficers = FacilityUtil.filterDeletedOfficer(setOfficers);
						IFacilityOfficer[] facilityOfficers = (IFacilityOfficer[]) setOfficers
								.toArray(new IFacilityOfficer[0]);
						Arrays.sort(facilityOfficers, new FacilityOfficerComparator());
						result.put(OfficerForm.MAPPER, facilityOfficers[(Integer.valueOf(key)).intValue()]);
						result.put("facilityOfficerObj", facilityOfficers[(Integer.valueOf(key)).intValue()]);
					}
				}
				else {
					List compareResultsList = (List) map.get("compareResultsOfficerList");
					CompareResult compareResult = (CompareResult) compareResultsList.get(Integer.valueOf(key)
							.intValue());
					facilityOfficerStaging = (IFacilityOfficer) compareResult.getObj();
					facilityOfficerActual = (IFacilityOfficer) compareResult.getOriginal();

					// if deleted then staging = actual, because the data in
					// staging is not exist anymore, jsp always reads from the
					// staging one
					/*if (compareResult.isDeleted()) {
						facilityOfficerStaging = facilityOfficerActual;
					}*/

					result.put(OfficerForm.MAPPER, facilityOfficerStaging);
					result.put("facilityOfficerObj", facilityOfficerStaging);
					result.put("facilityOfficerActual", facilityOfficerActual);
				}

                //validate officer single OB
                ActionErrors errorMessages = OfficerObjectValidator.validateSingleObject((IFacilityOfficer) result.get("facilityOfficerObj"));
                if (errorMessages != null) {
                    returnMap.put(MESSAGE_LIST, errorMessages);
                }
            }
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