package com.integrosys.cms.app.eod.basel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface IBaselDao {
	public void executeEndOfDayBaselReports() throws Exception ;
	public void executeEndOfMonthBaselReports() throws Exception ;
	public Object insertBaselUpdateDetails(ArrayList resultList , List<ConcurrentMap<String, String>> arrayListMonthlyBaselP2);
	public List<ConcurrentMap<String, String>> catcheDataFromMonthlyBaselP2();
	public boolean getActivityPerformed();
	
	//For FCUBS handoff CR
	public void updateMonthendDateAndFccFlag(String format) throws Exception;
	public boolean getActivityPerformedParamCode(String paramCode);
	public void updateActivityPerfForParamCode(String paramCode,String date);
}
