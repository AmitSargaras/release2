package com.integrosys.cms.app.tatdoc.bus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;

public class MaintainTatDurationUtil 
{
	public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("d-M-y");
	
	// parameter dd/mm/yyyy
	public static Date getStringToDate(String dateString)
	{
		if(dateString == null || "".equals(dateString) || "null".equals(dateString) || "0".equals(dateString))
			return null;
		
		String date = dateString.split("-")[0];
		String time = dateString.split("-")[1];
		String times[] = time.split(":");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date.split("/")[2]), 
				Integer.parseInt(date.split("/")[1])-1, 
				Integer.parseInt(date.split("/")[0]));
		
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(time.split(":")[2]));
		return cal.getTime(); 
	}
	
	public static String getFormattedDate(Date date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-H:m:s");
		
		if(date == null)
			return null;
		else
			return dateFormat.format(date);
	}
	
	public static Date getDurationDate(ITatDocProxy proxy, String branch, Date startDate, double duration, String durationType)
	{
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		
		if("H".equals(durationType))
		{
			int hour = new Double(duration).intValue();
			double minute = 0;

			if(duration%hour != 0)
				minute  = (duration%hour) * 60;

			start.add(Calendar.HOUR, new Double(hour).intValue());
			start.add(Calendar.MINUTE, new Double(minute).intValue());
		}
		else
		{
			Date dueDate = getDueDate(proxy, branch, startDate, new Double(duration).longValue());
			start.setTime(dueDate);
			/*int i = 0;
			int limit = 0;
			while(i < duration)
			{
				if(!"N".equals(workingDaysMap.get(defaultDateFormat.format(start.getTime()))))
				{
					start.add(Calendar.DAY_OF_MONTH, 1);
					i++;
				}
			}*/
		}
		
		return start.getTime();
	}
	
	private static Date getDueDate(ITatDocProxy proxy, String branch, Date startDate, long durationDay)
	{
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(startDate);
		endDate.add(Calendar.DAY_OF_MONTH, new Long(durationDay).intValue());
		long numOfNonWorkingDay = proxy.getNonWorkingDaysByBranch(branch, startDate, endDate.getTime()).longValue();
		if(numOfNonWorkingDay < 1)
			return endDate.getTime();
		else
			return getDueDate(proxy, branch, endDate.getTime(), numOfNonWorkingDay);
	}
}
