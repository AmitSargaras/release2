package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoveragedtls.proxy.IInsuranceCoverageDtlsProxyManager;

/**
 * @author $Author: Dattatray Thorat $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2011/04/06 04:23:28 $ Tag: $Name: $
 */
public class PrepareCreateInsuranceCoverageDtlsCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	
	private IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager;
	
	/**
	 * @return the insuranceCoverageDtlsProxyManager
	 */
	public IInsuranceCoverageDtlsProxyManager getInsuranceCoverageDtlsProxyManager() {
		return insuranceCoverageDtlsProxyManager;
	}


	/**
	 * @param insuranceCoverageDtlsProxyManager the insuranceCoverageDtlsProxyManager to set
	 */
	public void setInsuranceCoverageDtlsProxyManager(
			IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager) {
		this.insuranceCoverageDtlsProxyManager = insuranceCoverageDtlsProxyManager;
	}


	public PrepareCreateInsuranceCoverageDtlsCmd() {
	}
	

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ICId","java.lang.String",REQUEST_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
				{ "ICId","java.lang.String",REQUEST_SCOPE},
                { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
                { "locationList","java.util.ArrayList",REQUEST_SCOPE}
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
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String icId= (String)map.get("ICId");
			resultMap.put("ICId", icId);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
