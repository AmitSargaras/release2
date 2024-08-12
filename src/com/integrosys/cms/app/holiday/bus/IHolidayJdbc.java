/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.holiday.bus;

import java.util.Date;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface IHolidayJdbc {
	SearchResult getAllHoliday (String searchBy,String searchText)throws HolidayException;
	SearchResult getAllHoliday()throws HolidayException;
	List getAllHolidaySearch(String login)throws HolidayException;
	IHoliday listHoliday(long branchCode)throws HolidayException;
	public List getHolidayListForYear(long year);	
	public List getRecurrentHolidayListForYear(long year);	
	public void insertTransaction(long referenceId, long stageRefId, Date appDate)throws HolidayException;
	public void deleteOldHolidays(long year);
	public SearchResult getAllHolidayForYear();
	
	//Uma:Start:Prod issue: to get holiday list for eod process which are active.
	 List getHolidayListForYearEod(long year);	
	 List getRecurrentHolidayListForYearEod(long year);	
	//Uma:Start:Prod issue: to get holiday list for eod process which are active.
}
