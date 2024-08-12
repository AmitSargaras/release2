/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/concentrationreport/MakerEditConcReportCmd.java,v 1.1 2003/09/24 09:55:54 pooja Exp $
 */

package com.integrosys.cms.ui.concentrationreport;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;
import com.integrosys.component.commondata.app.bus.NoSuchBusinessParameterGroupExistsException;
import com.integrosys.component.commondata.app.bus.OBBusinessParameterGroup;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * Command class Edit Systemparameter by maker.
 * @author $Author: pooja $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/24 09:55:54 $ Tag: $Name: $
 */
public class MakerEditConcReportCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerEditConcReportCmd() {
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
				{ "BusinessParameterGroup", "com.integrosys.cms.app.systemparameters.bus.OBSystemParameters",
						FORM_SCOPE },
				{ "SystemParametersTrxValue", "com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
		OBBusinessParameterGroup bpGroup = (OBBusinessParameterGroup) map.get("BusinessParameterGroup");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IBusinessParameterGroupTrxValue busParametersTrxVal = (IBusinessParameterGroupTrxValue) map
				.get("SystemParametersTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  bpGroup = " + bpGroup);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {

			SystemParametersProxy proxy = new SystemParametersProxy();
			ICompTrxResult trxResult = proxy.makerUpdate(trxContext, busParametersTrxVal, bpGroup);
			IBusinessParameterGroupTrxValue trxValue = (IBusinessParameterGroupTrxValue) trxResult.getTrxValue();

			resultMap.put("SystemParametersTrxValue", trxValue);
			resultMap.put("request.ITrxValue", trxValue);

		}
		catch (NoSuchBusinessParameterGroupExistsException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (CommonDataManagerException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
