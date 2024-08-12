package com.integrosys.cms.ui.udf;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;

public class MakerPrepareCreateUdfCmd extends AbstractCommand implements
ICommonEventConstant{

	private IUdfProxyManager udfProxyManager;

	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateUdfCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
			{"IUdfTrxValue", "com.integrosys.cms.app.udf.trx.OBUdfTrxValue", SERVICE_SCOPE}
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBUdfTrxValue udfTrxValue = new OBUdfTrxValue();

		resultMap.put("IUdfTrxValue", udfTrxValue);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
}
