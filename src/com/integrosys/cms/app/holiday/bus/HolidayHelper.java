package com.integrosys.cms.app.holiday.bus;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class HolidayHelper {
	public static boolean isHoliday(List holidayList, Calendar date) {
		boolean isHoliday = false;
		if (holidayList != null && date != null) {
			Iterator holidayIterator =  holidayList.iterator();
			OBHoliday holiday;
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			while (holidayIterator.hasNext()) {
				holiday = (OBHoliday) holidayIterator.next();
				startDate.setTime(holiday.getStartDate());
				endDate.setTime(holiday.getEndDate());
				if(startDate != null && endDate != null) {
					if (startDate.equals(date) || endDate.equals(date)
							|| (date.after(startDate) && date.before(endDate))
							|| (date.get(Calendar.DAY_OF_WEEK) == 1)  || 
							//Added by Uma Khot to consider 2nd and 4th saturday as holiday
							(("Y".equalsIgnoreCase(PropertyManager.getValue("isSaturdayHoliday"))) &&  date.get(Calendar.DAY_OF_WEEK) == 7) && ((date.get(Calendar.WEEK_OF_MONTH) == 2) || (date.get(Calendar.WEEK_OF_MONTH) == 4))) {
						isHoliday = true;
						break;
					}
				}
			}
		}
		return isHoliday;
	}
}
