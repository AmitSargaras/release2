package com.integrosys.cms.ui.holiday;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.proxy.IHolidayProxyManager;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditHolidayCmd extends AbstractCommand implements ICommonEventConstant {


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
	public CheckerApproveEditHolidayCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IHolidayTrxValue", "com.integrosys.cms.app.holiday.trx.IHolidayTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Holiday Trx value
			IHolidayTrxValue trxValueIn = (OBHolidayTrxValue) map.get("IHolidayTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated Holiday Trx
			IHolidayTrxValue trxValueOut = getHolidayProxy().checkerApproveHoliday(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (HolidayException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}



