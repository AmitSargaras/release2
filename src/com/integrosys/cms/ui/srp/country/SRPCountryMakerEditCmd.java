/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryMakerEditCmd.java,v 1.2 2003/09/10 13:33:10 dayanand Exp $
 */

package com.integrosys.cms.ui.srp.country;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.OBCollateralParameter;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralParameterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.srp.SRPHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Command class Edit Systemparameter by maker.
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/10 13:33:10 $ Tag: $Name: $
 */
public class SRPCountryMakerEditCmd extends AbstractCommand {
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
	public SRPCountryMakerEditCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "CollateralParameters", "java.util.Collection", FORM_SCOPE },
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
		return (new String[][] { { "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
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

		Collection paramsCol = (Collection) map.get("CollateralParameters");
		OBCollateralParameter[] obCollateralParameters = null;
		obCollateralParameters = new OBCollateralParameter[paramsCol.size()];
		obCollateralParameters = (OBCollateralParameter[]) paramsCol.toArray(obCollateralParameters);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		ICollateralParameterTrxValue collateralParametersTrxVal = (ICollateralParameterTrxValue) map
				.get("CollateralParameterTrxValue");

		try {
			ICollateralParameterTrxValue trxValue = getCollateralParameterProxy().makerUpdateCollateralParameter(
					trxContext, collateralParametersTrxVal, obCollateralParameters);

			resultMap.put("CollateralParameterTrxValue", trxValue);

			resultMap.put("timefrequency.labels", CommonDataSingleton
					.getCodeCategoryLabels(SRPHelper.TIME_FREQUENCY_CODE));
			resultMap.put("timefrequency.values", CommonDataSingleton
					.getCodeCategoryValues(SRPHelper.TIME_FREQUENCY_CODE));

			resultMap.put("countries.map", SRPHelper.buildCountryeMap());
			resultMap.put("request.ITrxValue", trxValue);
			resultMap.put("timefrequencies.map", SRPHelper.buildTimeFrequencyMap());

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
