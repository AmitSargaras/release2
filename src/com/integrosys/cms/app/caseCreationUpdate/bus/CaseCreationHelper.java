package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class CaseCreationHelper {
	public static boolean isHoliday(List holidayList, Calendar date) {
		boolean isHoliday = false;
		if (holidayList != null && date != null) {
			Iterator holidayIterator =  holidayList.iterator();
			OBCaseCreation holiday;
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			while (holidayIterator.hasNext()) {
				holiday = (OBCaseCreation) holidayIterator.next();
				//startDate.setTime(holiday.getStartDate());
				//endDate.setTime(holiday.getEndDate());
				if(startDate != null && endDate != null) {
					if (startDate.equals(date) || endDate.equals(date)
							|| (date.after(startDate) && date.before(endDate))
							|| (date.get(Calendar.DAY_OF_WEEK) == 1)) {
						isHoliday = true;
						break;
					}
				}
			}
		}
		return isHoliday;
	}
}
