package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.OBNPLInfo;

/**
 * Created by IntelliJ IDEA. User: User Date: Apr 10, 2007 Time: 8:43:07 PM To
 * change this template use File | Settings | File Templates.
 */
public class ViewNPLCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "nplInfo", "java.util.Collection", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] { { "npl", "com.integrosys.cms.app.liquidation.bus.OBNPLInfo", REQUEST_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Liquidation
	 * is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String strIndex = (String) map.get("index");
		long index = 0;
		OBNPLInfo npl = null;
		try {
			index = Long.parseLong(strIndex);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "index invalid [" + strIndex + "], ignored");
		}
		// System.out.println("after trxContext: "+trxContext);
		Collection nplInfo = (Collection) map.get("nplInfo");
		// System.out.println("after obLiquidation: "+obLiquidation);
		if (nplInfo != null) {
			for (Iterator itr = nplInfo.iterator(); itr.hasNext();) {
				npl = (OBNPLInfo) itr.next();
				if (npl.getNplID().longValue() == index) {
					break;
				}
			}
		}
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event);

		resultMap.put("npl", npl);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
