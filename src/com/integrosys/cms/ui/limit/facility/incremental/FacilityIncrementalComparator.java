package com.integrosys.cms.ui.limit.facility.incremental;

import java.util.Comparator;

import com.integrosys.cms.app.limit.bus.OBFacilityIncremental;
import com.integrosys.base.techinfra.diff.CompareResult;

public class FacilityIncrementalComparator implements Comparator {

	public int compare(Object obj1, Object obj2) {
		OBFacilityIncremental incremental1 = null;
		OBFacilityIncremental incremental2 = null;
		if (obj1 instanceof CompareResult) {
			incremental1 = (OBFacilityIncremental)((CompareResult)obj1).getObj();
		} else {
			incremental1 = (OBFacilityIncremental)obj1;
		}
		
		if (obj2 instanceof CompareResult) {
			incremental2 = (OBFacilityIncremental)((CompareResult)obj2).getObj();
		} else {
			incremental2 = (OBFacilityIncremental)obj2;
		}
		
		if (incremental1.getIncrementalNumber() != null &&
				incremental2.getIncrementalNumber() == null)
			return 1;
		
		if (incremental1.getIncrementalNumber() == null && 
				incremental2.getIncrementalNumber() != null)
			return -1;
		
		if (incremental1.getIncrementalNumber() == null &&
				incremental2.getIncrementalNumber() == null)
			return incremental1.getId().compareTo(incremental2.getId());
		
		return incremental1.getIncrementalNumber().compareTo(incremental2.getIncrementalNumber());
	}
}
