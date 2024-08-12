package com.integrosys.cms.ui.productMaster;

import java.util.HashMap;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.proxy.IProductMasterProxyManager;
import com.integrosys.cms.app.productMaster.trx.IProductMasterTrxValue;
import com.integrosys.cms.app.productMaster.trx.OBProductMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {

	private IProductMasterProxyManager productMasterProxy;

	public IProductMasterProxyManager getProductMasterProxy() {
		return productMasterProxy;
	}

	public void setProductMasterProxy(IProductMasterProxyManager productMasterProxy) {
		this.productMasterProxy = productMasterProxy;
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
			{"productMasterList", "java.util.ArrayList", REQUEST_SCOPE},
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
			SearchResult productMasterList = new SearchResult();
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			productMasterList = getProductMasterProxy().getAllActualProductMaster();
			resultMap.put("productMasterList", productMasterList);
			resultMap.put("startIndex", startIdx);
			
		} catch (ProductMasterException ex) {
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
