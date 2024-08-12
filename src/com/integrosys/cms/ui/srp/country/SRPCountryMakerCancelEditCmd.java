/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryMakerCancelEditCmd.java,v 1.3 2005/09/08 08:58:35 hshii Exp $
 */

package com.integrosys.cms.ui.srp.country;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class to Cancel Edited SystemParameters by maker
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/08 08:58:35 $ Tag: $Name: $
 */
public class SRPCountryMakerCancelEditCmd extends AbstractCommand {
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
	public SRPCountryMakerCancelEditCmd() {
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
				{ "CollateralParameterTrxValue", OBCollateralParameterTrxValue.class.getName(), SERVICE_SCOPE },
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
		ICollateralParameterTrxValue collateralParametersTrxVal = (ICollateralParameterTrxValue) map
				.get("CollateralParameterTrxValue");

		try {
			collateralParametersTrxVal = getCollateralParameterProxy().makerCancelUpdateCollateralParameter(trxContext,
					collateralParametersTrxVal);
			resultMap.put("request.ITrxValue", collateralParametersTrxVal);
		}
		catch (CollateralException e) {
			DefaultLogger.debug(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
