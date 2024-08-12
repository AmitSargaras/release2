package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 9, 2003 Time: 5:29:26 PM
 */
public class YearOfManufactureList {

	private static HashMap yearList;

	private static YearOfManufactureList thisInstance;

	private static Collection yearLabel = new ArrayList();

	private static Collection yearValue = new ArrayList();

	public synchronized static YearOfManufactureList getInstance() {
		if (thisInstance == null) {
			thisInstance = new YearOfManufactureList();
			thisInstance.getYearList();
		}
		return thisInstance;
	}

	private YearOfManufactureList() {
	}

	public HashMap getYearList() {
		DefaultLogger.debug(this, "Entering YearOfManufactureList");
		if (YearOfManufactureList.yearList == null) {
			YearOfManufactureList.yearList = new HashMap();
		}

		Calendar cal = new GregorianCalendar();
		int curYear = cal.get(Calendar.YEAR);
		int fromYr = curYear - 30;
		for (int i = 0; i < 50; i++) {
			yearList.put(fromYr + "", fromYr + "");
			yearLabel.add(fromYr + "");
			yearValue.add(fromYr + "");
			fromYr++;
		}

		DefaultLogger.debug(this, "Exiting YearOfManufactureList");
		return yearList;
	}

	public Collection getYearLabels() {
		return yearLabel;
	}

	public Collection getYearValues() {
		return yearValue;
	}

}
