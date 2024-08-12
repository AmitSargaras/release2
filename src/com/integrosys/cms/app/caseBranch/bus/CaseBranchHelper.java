package com.integrosys.cms.app.caseBranch.bus;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class CaseBranchHelper {
	public static boolean isCaseBranch(List caseBranchList, Calendar date) {
		boolean isCaseBranch = false;
		if (caseBranchList != null && date != null) {
			Iterator caseBranchIterator =  caseBranchList.iterator();
			OBCaseBranch caseBranch;
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			while (caseBranchIterator.hasNext()) {
				caseBranch = (OBCaseBranch) caseBranchIterator.next();
				//startDate.setTime(caseBranch.getStartDate());
				//endDate.setTime(caseBranch.getEndDate());
				if(startDate != null && endDate != null) {
					if (startDate.equals(date) || endDate.equals(date)
							|| (date.after(startDate) && date.before(endDate))
							|| (date.get(Calendar.DAY_OF_WEEK) == 1)) {
						isCaseBranch = true;
						break;
					}
				}
			}
		}
		return isCaseBranch;
	}
}
