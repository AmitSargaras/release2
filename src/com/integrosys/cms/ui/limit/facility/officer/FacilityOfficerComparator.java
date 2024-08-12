package com.integrosys.cms.ui.limit.facility.officer;

import java.util.Comparator;

import com.integrosys.cms.app.limit.bus.IFacilityOfficer;

public class FacilityOfficerComparator implements Comparator {

	public int compare(Object obj1, Object obj2) {
		IFacilityOfficer facilityOfficer1 = (IFacilityOfficer) obj1;
		IFacilityOfficer facilityOfficer2 = (IFacilityOfficer) obj2;
		return facilityOfficer1.getRelationshipCodeEntryCode().compareTo(facilityOfficer2.getRelationshipCodeEntryCode());
	}
}
