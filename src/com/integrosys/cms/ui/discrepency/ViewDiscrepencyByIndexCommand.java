package com.integrosys.cms.ui.discrepency;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

	/**
	 * This Class is used for Viewing a perticular Country
	 * 
	 * @author $Author: Sandeep Shinde
	 * @version 2.0
	 * @since $Date: 08/04/2011 01:41:00 $ Tag: $Name: $
	 */

public class ViewDiscrepencyByIndexCommand extends AbstractCommand{

	private IDiscrepencyProxyManager discrepencyProxy;
		
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public ViewDiscrepencyByIndexCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "discrepencyId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});

	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "discrepencyObj","com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of Discrepency is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		String event = (String) map.get("checkerEvent");
		String startIdx = (String) map.get("startIndex");
		long discrepencyId = Long.parseLong((String) map.get("discrepencyId"));
		
		DefaultLogger.debug(this, "============ ViewDiscrepencyCommandByIndex ()" + discrepencyId);
		try {
			/*IDiscrepencyTrxValue discrepencyTrx = getDiscrepencyProxy().getDiscrepencyById(discrepencyId);
			IDiscrepency discrepency = discrepencyTrx.getActualDiscrepency();
        	resultMap.put("discrepencyObj", discrepency);
        	resultMap.put("checkerEvent", event);
        	resultMap.put("startIndex", startIdx);*/
			
		} catch (NoSuchDiscrepencyException nsde) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsde.getMessage());
			cpe.initCause(nsde);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
//		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
