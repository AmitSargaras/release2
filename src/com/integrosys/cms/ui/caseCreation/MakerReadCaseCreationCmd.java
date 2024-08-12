/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseCreation;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.bus.OBHoliday;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;

/**
*@author $Author: Abhijit R$
*Command to read Holiday
 */
public class MakerReadCaseCreationCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IHolidayProxyManager holidayProxy;

	
	
	
	public IHolidayProxyManager getHolidayProxy() {
		return holidayProxy;
	}

	public void setHolidayProxy(
			IHolidayProxyManager holidayProxy) {
		this.holidayProxy = holidayProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadCaseCreationCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				 {"branchCode", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "holidayObj", "com.integrosys.cms.app.holiday.bus.OBHoliday", SERVICE_SCOPE },
				{ "holidayObj", "com.integrosys.cms.app.holiday.bus.OBHoliday", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{"IHolidayTrxValue", "com.integrosys.cms.app.holiday.trx.IHolidayTrxValue", SERVICE_SCOPE}
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IHoliday holiday;
			IHolidayTrxValue trxValue=null;
			String holidayCode=(String) (map.get("branchCode"));
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			String event = (String) map.get("event");
			trxValue = (OBHolidayTrxValue) getHolidayProxy().getHolidayTrxValue(Long.parseLong(holidayCode));
			holiday = (OBHoliday) trxValue.getHoliday();

			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE")))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);	
			resultMap.put("IHolidayTrxValue", trxValue);
			resultMap.put("holidayObj", holiday);
		}catch (HolidayException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	

}
