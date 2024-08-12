/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/CheckerReadSystemParametersCmd.java,v 1.3 2003/09/17 08:38:57 pooja Exp $
 */

package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;
import com.integrosys.component.commondata.app.bus.IBusinessParameter;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * Command class to add the new SystemParameter by admin maker on the
 * corresponding event...
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/17 08:38:57 $ Tag: $Name: $
 */
public class CheckerReadCommonCodeTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerReadCommonCodeTypeCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "SystemParametersTrxValue", "com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "BusinessParameterGroup", "java.util.list", FORM_SCOPE } });
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
		String trxId = (String) map.get("TrxId");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);
		try {
			SystemParametersProxy proxy = new SystemParametersProxy();
			IBusinessParameterGroupTrxValue businessParameterGroupTrxVal = proxy.getBusinessParameterGroup(Long
					.parseLong(trxId.trim()));
			resultMap.put("SystemParametersTrxValue", businessParameterGroupTrxVal);
			IBusinessParameter ib[] = businessParameterGroupTrxVal.getBusinessParameterGroup().getBusinessParameters();

			DefaultLogger.debug(this, "setting the value in checker command  = "
					+ businessParameterGroupTrxVal.getBusinessParameterGroup().getBusinessParameters());
			DefaultLogger.debug(this, "setting the value in checker command  = " + ib);
			resultMap.put("BusinessParameterGroup", businessParameterGroupTrxVal.getStagingBusinessParameterGroup());
		}
		catch (CommonDataManagerException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
