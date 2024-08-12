package com.integrosys.cms.batch.erosion.schedular;

import java.util.Comparator;

import com.integrosys.cms.app.erosion.bus.OBErosionStatus;

public class ErosionStatusComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		OBErosionStatus status1 = (OBErosionStatus) arg0;
		OBErosionStatus status2 = (OBErosionStatus) arg1;
		return (int) (status1.getId() - status2.getId());
	}
}