package com.integrosys.cms.app.collateral.bus;

import java.util.Comparator;

public class InstrumentComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		if ((o1 == null) && (o2 == null)) {
			return 0;
		}
		else if (o1 == null) {
			return -1;
		}
		else if (o2 == null) {
			return 1;
		}
		return defaultCompare(o1, o2);
	}

	private int defaultCompare(Object o1, Object o2) {
		IInstrument obj1 = (IInstrument) o1;
		IInstrument obj2 = (IInstrument) o2;

		Long id1 = new Long(obj1.getInstrumentID());
		Long id2 = new Long(obj2.getInstrumentID());
		return id1.compareTo(id2);
	}
}
