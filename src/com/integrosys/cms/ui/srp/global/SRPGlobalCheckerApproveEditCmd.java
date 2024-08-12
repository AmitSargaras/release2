/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalCheckerApproveEditCmd.java,v 1.2 2003/09/03 13:13:39 dayanand Exp $
 */

package com.integrosys.cms.ui.srp.global;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralSubTypeTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralSubTypeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class for CheckerApproval of Edit(SystemParameters)
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/03 13:13:39 $ Tag: $Name: $
 */
public class SRPGlobalCheckerApproveEditCmd extends AbstractCommand {
	private ICollateralParameterProxy collateralParameterProxy;

	public void setCollateralParameterProxy(ICollateralParameterProxy collateralParameterProxy) {
		this.collateralParameterProxy = collateralParameterProxy;
	}

	public ICollateralParameterProxy getCollateralParameterProxy() {
		return collateralParameterProxy;
	}

	/**
	 * Default Constructor
	 */
	public SRPGlobalCheckerApproveEditCmd() {
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
				{ "CollateralSubTypeTrxValue", OBCollateralSubTypeTrxValue.class.getName(), SERVICE_SCOPE },
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
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICollateralSubTypeTrxValue collateralSubTypesTrxVal = (ICollateralSubTypeTrxValue) map
				.get("CollateralSubTypeTrxValue");
		try {
			getCollateralParameterProxy().checkerApproveUpdateCollateralSubType(trxContext, collateralSubTypesTrxVal);
			resultMap.put("request.ITrxValue", collateralSubTypesTrxVal);
		}
		catch (CollateralException e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
