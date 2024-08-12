package com.integrosys.cms.app.udf.bus;

import java.util.Comparator;

public class UDFComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		OBUdf ob1 = (OBUdf)arg0;
		OBUdf ob2 = (OBUdf)arg1;
		int compareVal = 0;
		if (ob1 == null || ob2 == null) {
			compareVal = 0;
		}
		else {
			compareVal = ob1.getSequence() - ob2.getSequence();
		}
		return compareVal;
	}

}
