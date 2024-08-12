package com.integrosys.cms.batch.eod;

import java.util.Comparator;

import com.integrosys.cms.app.eod.bus.OBEODStatus;

public class EODStatusComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		OBEODStatus status1 = (OBEODStatus) arg0;
		OBEODStatus status2 = (OBEODStatus) arg1;
		return (int) (status1.getId() - status2.getId());
	}

}
