package com.integrosys.cms.batch.eod;

import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.holiday.bus.IHolidayDao;
import com.integrosys.cms.app.holiday.bus.IHolidayJdbc;

public interface IEndOfDayBatchService {
	public StringBuffer performEOD();
	public IGeneralParamDao getGeneralParam();
	public void setGeneralParam(IGeneralParamDao generalParam);
	public IHolidayDao getHolidayDao();
	public void setHolidayDao(IHolidayDao holidayDao);
	public IHolidayJdbc getHolidayJdbc();
	public void setHolidayJdbc(IHolidayJdbc holidayJdbc);
	public StringBuffer performEndOfYearActivities();
	public void checkSystemHandoff();
	public StringBuffer performAdfRbi();
//	public StringBuffer performBCPActivity(Map arg);
	public Object performBaselUpdateReport();
	public Object downloadFcubsFile();
	
}
