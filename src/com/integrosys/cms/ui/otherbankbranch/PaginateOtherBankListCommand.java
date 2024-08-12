package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;

public class PaginateOtherBankListCommand extends AbstractCommand{
	
	private IOtherBankProxyManager otherBankProxyManager;

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
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
				{ "OtherBankList", "java.util.List", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String event = (String) map.get("event");
			String startInd = (String) map.get("startIndex");
			DefaultLogger.debug(this, "StartIdx: " + startInd);
			SearchResult OtherBankList = getOtherBankProxyManager().getOtherBankList("", "");			
			resultMap.put("OtherBankList", OtherBankList);
            resultMap.put("startIndex", startInd);
            resultMap.put("event", event);            
		} catch (OtherBankException obe) {
            DefaultLogger.error(this, "got exception in doExecute" ,obe);
        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
			cpe.initCause(obe);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
