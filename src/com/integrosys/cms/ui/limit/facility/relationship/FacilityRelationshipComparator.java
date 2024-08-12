package com.integrosys.cms.ui.limit.facility.relationship;

import java.util.Comparator;

import com.integrosys.cms.app.limit.bus.IFacilityRelationship;

public class FacilityRelationshipComparator implements Comparator {

	public int compare(Object obj1, Object obj2) {
		IFacilityRelationship facilityRelationship1 = (IFacilityRelationship) obj1;
		IFacilityRelationship facilityRelationship2 = (IFacilityRelationship) obj2;
		return facilityRelationship1.getCmsLegalEntity().getLEReference().compareTo(
				facilityRelationship2.getCmsLegalEntity().getLEReference());
	}
}