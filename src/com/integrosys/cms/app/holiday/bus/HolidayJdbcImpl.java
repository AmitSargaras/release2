package com.integrosys.cms.app.holiday.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class HolidayJdbcImpl extends JdbcDaoSupport implements
IHolidayJdbc {

	private static final String SELECT_HOLIDAY_TRX = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by,status from CMS_HOLIDAY where deprecated='N' ORDER BY start_Date asc ";
	private static final String SELECT_RECHOLIDAY_TRX = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by,status from CMS_HOLIDAY where deprecated='N' and IS_RECURRENT='on' ORDER BY start_Date asc ";
	
	//Uma:Start:Prod issue: to get holiday list for eod process which are active.
	private static final String SELECT_HOLIDAY_TRX_EOD = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by,status from CMS_HOLIDAY where deprecated='N'  and status='ACTIVE' ORDER BY start_Date asc ";
	private static final String SELECT_RECHOLIDAY_TRX_EOD = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by,status from CMS_HOLIDAY where deprecated='N' and IS_RECURRENT='on' and status='ACTIVE' ORDER BY start_Date asc ";
	//Uma:End:Prod issue: to get holiday list for eod process which are active.
	
	private static final String SELECT_INSERT_HOLIDAY_TRX = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by from CMS_STAGE_HOLIDAY where deprecated='N' AND ID ";
	
	private static final String SELECT_APP_DATE ="select PARAM_CODE,PARAM_VALUE from cms_general_param where param_code='APPLICATION_DATE'";
	
	private static final  String SELECT_HOLIDAY_TRX_CURR_YEAR = " select id,description,start_Date,end_Date,last_update_date,last_update_by,status from cms_holiday where  to_number( substr( to_char(start_date),8,2))>=to_number( substr((select param_value from cms_general_param where param_code='APPLICATION_DATE'),10,2)) AND deprecated='N' order by start_date ";
	
	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}
	/**
	 * @return List of all Holiday according to the query passed.
	 */

	public SearchResult getAllHoliday(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_HOLIDAY_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new HolidayRowMapper());
			}

		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized Holiday
	 */
	public SearchResult getAllHoliday() {
		List resultList = null;
		try {

			resultList = getJdbcTemplate().query(SELECT_HOLIDAY_TRX,
					new HolidayRowMapper());

		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	public SearchResult getAllHolidayForYear() {
		List resultList = null;	
		
		try {			
			resultList = getJdbcTemplate().query(SELECT_HOLIDAY_TRX_CURR_YEAR,
					new HolidayRowMapper());

			

		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday In JDBC");
		}
		SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return searchresult;
		}
	
	
	/**
	 * @return List of all Holiday according to the query passed.
	 */
	public List getAllHolidaySearch(String login) {
		String SELECT_HOLIDAY_BY_SEARCH = "SELECT id,description,startDate,endDate,last_update_date from CMS_HOLIDAY where deprecated='N' AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_HOLIDAY_TRX,
						new HolidayRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_HOLIDAY_BY_SEARCH,
						new HolidayRowMapper());
			}

		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}
		return resultList;
	}

	public class HolidayRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBHoliday result = new OBHoliday();
			result.setDescription(rs.getString("description"));
			result.setEndDate(rs.getDate("end_date"));
			result.setStartDate(rs.getDate("start_date"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setLastUpdateBy(rs.getString("last_update_by"));
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			return result;
		}
	}
	/**
	 * @return List of all Holiday according to the query passed.
	 */

	public IHoliday listHoliday(long holidayCode)
	throws SearchDAOException,HolidayException {
		String SELECT_HOLIDAY_ID = "SELECT id,description,startDate,endDate,last_update_date from description  where id="
			+ holidayCode;
		IHoliday holiday = new OBHoliday();
		try {
			holiday = (IHoliday) getJdbcTemplate().query(
					SELECT_HOLIDAY_ID,
					new HolidayRowMapper());
		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}

		return holiday;

	}
	
	public List getHolidayListForYear(long year) {
		List resultList = null;
		OBHoliday holiday;
		try {
			resultList = getJdbcTemplate().query(SELECT_HOLIDAY_TRX, new HolidayRowMapper());
		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				holiday = (OBHoliday)iter.next();
				startDate.setTime(holiday.getStartDate());
				endDate.setTime(holiday.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}

	public List getRecurrentHolidayListForYear(long year) {
		List resultList = null;
		OBHoliday holiday;
		try {
			resultList = getJdbcTemplate().query(SELECT_RECHOLIDAY_TRX,	new HolidayRowMapper());
		} catch (Exception e) {
			throw new HolidayException("ERROR-- While retriving Holiday");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				holiday = (OBHoliday)iter.next();
				startDate.setTime(holiday.getStartDate());
				endDate.setTime(holiday.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}
	
	
	public void insertTransaction(long referenceId, long stageRefId, Date appDate) { 
		
		String transInsertQuery = null;
		long transId = 0;

		transId = getSequence("TRX_SEQ"); 
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		//****** Inserting record into Transaction table
		transInsertQuery = "INSERT INTO TRANSACTION " +
				" (TRANSACTION_ID,FROM_STATE,TRANSACTION_TYPE,CREATION_DATE,TRANSACTION_DATE,REFERENCE_ID,STATUS,STAGING_REFERENCE_ID, " +
				" CUSTOMER_ID,LIMIT_PROFILE_ID,LOGIN_ID) " +
				" VALUES ("+transId+",'ACTIVE','HOLIDAY', to_timestamp('"+df.format(appDate)+"','DD-MON-RR HH.MI.SS.FF AM'),to_timestamp('"+df.format(appDate)+"','DD-MON-RR HH.MI.SS.FF AM'),"+referenceId+",'ACTIVE'," +
				" "+stageRefId+",'-999999999','-999999999','SYSTEM')";
		
		
		getJdbcTemplate().execute(transInsertQuery);
		
		
	}
	private long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
	
	
public void deleteOldHolidays(long year) { 
		
		String query = "update cms_holiday set deprecated='Y' , status='INACTIVE' where TO_NUMBER(TO_CHAR(start_date,'YYYY'))="+year+" and TO_NUMBER(TO_CHAR(end_date,'YYYY'))="+year;
		
		
		getJdbcTemplate().execute(query);		
		
	}

public OBGeneralParamEntry getAppDate(){
	OBGeneralParamEntry appDate=null;
	try {
		appDate=(OBGeneralParamEntry) getJdbcTemplate().queryForObject(SELECT_APP_DATE,new GeneralParamRowMapper());
	} catch (Exception e) {
		throw new HolidayException("ERROR-- Unable to get Application Date");
	}
	
	return appDate;
	}

public class GeneralParamRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		OBGeneralParamEntry result = new OBGeneralParamEntry();
		result.setParamCode(rs.getString("PARAM_CODE"));
		result.setParamValue(rs.getString("PARAM_VALUE"));
		return result;
	}
}

//Uma:Start:Prod issue: to get holiday list for eod process which are active.
public List getHolidayListForYearEod(long year) {
	List resultList = null;
	OBHoliday holiday;
	try {
		resultList = getJdbcTemplate().query(SELECT_HOLIDAY_TRX_EOD, new HolidayRowMapper());
	} catch (Exception e) {
		throw new HolidayException("ERROR-- While retriving Holiday for EOD");
	}
	Calendar startDate = Calendar.getInstance();
	Calendar endDate = Calendar.getInstance();
	if (resultList != null) {
		Iterator iter = resultList.iterator();
		while (iter.hasNext()) {
			holiday = (OBHoliday)iter.next();
			startDate.setTime(holiday.getStartDate());
			endDate.setTime(holiday.getEndDate());
			if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
				iter.remove();
			}
		}
	}
	return resultList;
}

public List getRecurrentHolidayListForYearEod(long year) {
	List resultList = null;
	OBHoliday holiday;
	try {
		resultList = getJdbcTemplate().query(SELECT_RECHOLIDAY_TRX_EOD,	new HolidayRowMapper());
	} catch (Exception e) {
		throw new HolidayException("ERROR-- While retriving Holiday for EOD");
	}
	Calendar startDate = Calendar.getInstance();
	Calendar endDate = Calendar.getInstance();
	if (resultList != null) {
		Iterator iter = resultList.iterator();
		while (iter.hasNext()) {
			holiday = (OBHoliday)iter.next();
			startDate.setTime(holiday.getStartDate());
			endDate.setTime(holiday.getEndDate());
			if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
				iter.remove();
			}
		}
	}
	return resultList;
}
//Uma:End:Prod issue: to get holiday list for eod process which are active.
}
