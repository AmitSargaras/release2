package com.integrosys.cms.ui.limit.facility.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.DomainObjectStatusMapper;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ReadFacilityMainCommand extends FacilityMainCommand {

	private Comparator limitComparator = new Comparator() {

		public int compare(Object thisObject, Object thatObject) {
			ILimit thisLimit = (ILimit) thisObject;
			ILimit thatLimit = (ILimit) thatObject;
			if (thisLimit.getAcfNo() == null) {
				return (thatLimit.getAcfNo() == null) ? (thisLimit.getLimitID() < thatLimit.getLimitID() ? -1
						: (thisLimit.getLimitID() == thatLimit.getLimitID() ? 0 : 1)) : 1;
			}
			return (thatLimit.getAcfNo() == null) ? -1 : thisLimit.getAcfNo().compareTo(thatLimit.getAcfNo());
		}
	};

	private DomainObjectStatusMapper facilityTrxStatusMapper;

	public void setFacilityTrxStatusMapper(DomainObjectStatusMapper facilityTrxStatusMapper) {
		this.facilityTrxStatusMapper = facilityTrxStatusMapper;
	}

	public void setLimitComparator(Comparator limitComparator) {
		this.limitComparator = limitComparator;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "frameRequested", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "listLimits", "java.util.List", SERVICE_SCOPE },
				{ "listLimitWoCollateral", "java.util.List", SERVICE_SCOPE },
				{ "currentTab", "java.lang.String", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "errorMap", "java.util.Map", SERVICE_SCOPE }, { "limitIdStatusMap", "java.util.Map", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		// reset the value of current tab stored in session
		result.put("currentTab", null);
		// reset the value of facilityMasterObj in session to null
		result.put("facilityMasterObj", null);

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		List cmsLimitIdList = new ArrayList();

		ILimit[] limits = limitProfile.getLimits();
		List listLimitWoCollateral = new ArrayList();
		for (int i = 0; i < limits.length; i++) {
			cmsLimitIdList.add(new Long(limits[i].getLimitID()));

			if (limits[i].getNonDeletedCollateralAllocations() == null
					|| (limits[i].getNonDeletedCollateralAllocations()).length == 0) {
				listLimitWoCollateral.add(limits[i]);
			}
		}
		List listLimits = new ArrayList(Arrays.asList(limits));

		Collections.sort(listLimits, limitComparator);
		Collections.sort(listLimitWoCollateral, limitComparator);

		result.put("listLimits", listLimits);
		result.put("listLimitWoCollateral", listLimitWoCollateral);

		Map cmsLimitIdStatusMap = this.facilityTrxStatusMapper.mapStatus((Long[]) cmsLimitIdList.toArray(new Long[0]));

		result.put("limitIdStatusMap", cmsLimitIdStatusMap);

		String frame = (String) map.get("frameRequested");
		if (frame == null) {
			frame = (String) map.get("frame");
		}

		result.put("frame", frame);
		result.put("errorMap", null);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
