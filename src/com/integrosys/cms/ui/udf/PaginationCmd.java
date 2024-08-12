package com.integrosys.cms.ui.udf;

import java.util.HashMap;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {

	private IUdfProxyManager udfProxy;

	public IUdfProxyManager getUdfProxyManager() {
		return udfProxy;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxy) {
		this.udfProxy = udfProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public PaginationCmd() {
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
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
		});
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
			{"udfList", "java.util.ArrayList", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE }
		});
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
		HashMap exceptionMap = new HashMap();
		try{
			String startIdx = (String) map.get("startIndex");
			SearchResult udfList = new SearchResult();
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			udfList = getUdfProxyManager().getAllActualUdf();
			resultMap.put("udfList", udfList);
			resultMap.put("startIndex", startIdx);
			
		} catch (UdfException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
