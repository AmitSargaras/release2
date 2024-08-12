package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainCommand;

public class SaveMultiTierFinDetailToSessionCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ MultiTierFinForm.MAPPER, "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", FORM_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "multiTierFinObj", "com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IFacilityMultiTierFinancing multiTierFin = (IFacilityMultiTierFinancing) map.get(MultiTierFinForm.MAPPER);
		IFacilityMultiTierFinancing multiTierFinObj = (IFacilityMultiTierFinancing) map.get("multiTierFinObj");
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		Set multiTierFinSet = facilityMasterObj.getFacilityMultiTierFinancingSet();
		if (multiTierFinObj == null) { // if Create

			if (multiTierFinSet == null) {
				multiTierFinSet = new TreeSet(new FacilityMultiTierFinComparator());
			}

			if (multiTierFinSet.add(multiTierFin)) {
				facilityMasterObj.setFacilityMultiTierFinancingSet(multiTierFinSet);
			}
			else {
				DefaultLogger.warn(this, "FacilityMultiTierFin already exist");
				exceptionMap.put("uniqueCombination", new ActionMessage("error.multitier.already.exists"));
			}
		}
		else { // if Update
			if (multiTierFinSet.contains(multiTierFinObj)) {
				Iterator iter = multiTierFinSet.iterator();
				int i = 0;
				while (iter.hasNext()) {
					IFacilityMultiTierFinancing multiTierFinTemp = (IFacilityMultiTierFinancing) iter.next();
					if (multiTierFinObj.equals(multiTierFinTemp)) {
						multiTierFin.setCmsRefId(multiTierFinTemp.getCmsRefId());
						break;
					}
					i++;
				}
				multiTierFinSet.remove(multiTierFinObj);
				if (!multiTierFinSet.add(multiTierFin)) {
					multiTierFinSet.add(multiTierFinObj);
					exceptionMap.put("uniqueCombination", new ActionMessage("error.multitier.already.exists"));
				}
				facilityMasterObj.setFacilityMultiTierFinancingSet(multiTierFinSet);
			}
		}
		result.put("facilityMasterObj", facilityMasterObj);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
