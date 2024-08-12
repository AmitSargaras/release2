package com.integrosys.cms.batch.eod;

import java.util.Comparator;

import com.integrosys.cms.app.eod.bus.OBAdfRbiStatus;

public class AdfRbiStatusComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		OBAdfRbiStatus status1 = (OBAdfRbiStatus) arg0;
		OBAdfRbiStatus status2 = (OBAdfRbiStatus) arg1;
		return (int) (status1.getId() - status2.getId());
	}

}
