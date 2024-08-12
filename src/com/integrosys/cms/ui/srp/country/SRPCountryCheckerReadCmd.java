/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryCheckerReadCmd.java,v 1.3 2003/09/15 02:57:08 pooja Exp $
 */

package com.integrosys.cms.ui.srp.country;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.srp.SRPHelper;

/**
 * Command class to add the new SystemParameter by admin maker on the
 * corresponding event...
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/15 02:57:08 $ Tag: $Name: $
 */
public class SRPCountryCheckerReadCmd extends AbstractCommand {
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
	public SRPCountryCheckerReadCmd() {
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
				{ "CollateralParameterTrxValue", OBCollateralParameterTrxValue.class.getName(), SERVICE_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
		trxId = trxId.trim();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		ICollateralParameterTrxValue collateralParameterTrxVal = null;
		try {
			collateralParameterTrxVal = getCollateralParameterProxy().getCollateralParameterTrxValue(trxContext, trxId);
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException(
					"failed to retrieve collateral parameter workflow value by transaction id [" + trxId + "]", ex);
		}

		ICollateralParameter[] parameters = collateralParameterTrxVal.getStagingCollateralParameters();

		Arrays.sort(parameters);
		collateralParameterTrxVal.setStagingCollateralParameters(parameters);

		parameters = collateralParameterTrxVal.getCollateralParameters();
		Arrays.sort(parameters);
		collateralParameterTrxVal.setCollateralParameters(parameters);

		resultMap.put("CollateralParameterTrxValue", collateralParameterTrxVal);

		resultMap.put("countries.map", SRPHelper.buildCountryeMap());
		resultMap.put("timefrequencies.map", SRPHelper.buildTimeFrequencyMap());

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
