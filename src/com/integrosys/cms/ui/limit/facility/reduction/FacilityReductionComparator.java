package com.integrosys.cms.ui.limit.facility.reduction;

import java.util.Comparator;

import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.cms.app.limit.bus.OBFacilityReduction;

public class FacilityReductionComparator implements Comparator {
	public int compare(Object obj1, Object obj2) {
		OBFacilityReduction reduction1 = null;
		OBFacilityReduction reduction2 = null;
		if (obj1 instanceof CompareResult) {
			reduction1 = (OBFacilityReduction)((CompareResult)obj1).getObj();
		} else {
			reduction1 = (OBFacilityReduction)obj1;
		}
		
		if (obj2 instanceof CompareResult) {
			reduction2 = (OBFacilityReduction)((CompareResult)obj2).getObj();
		} else {
			reduction2 = (OBFacilityReduction)obj2;
		}
		
		if (reduction1.getIncrementalNumber() != null &&
				reduction2.getIncrementalNumber() == null)
			return 1;
		
		if (reduction1.getIncrementalNumber() == null && 
				reduction2.getIncrementalNumber() != null)
			return -1;
		
		if (reduction1.getIncrementalNumber() == null &&
				reduction2.getIncrementalNumber() == null)
			return reduction1.getId().compareTo(reduction2.getId());
		
		return reduction1.getIncrementalNumber().compareTo(reduction2.getIncrementalNumber());
	}
}
