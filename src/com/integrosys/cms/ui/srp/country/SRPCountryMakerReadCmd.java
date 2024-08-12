/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryMakerReadCmd.java,v 1.9 2005/09/30 03:40:55 hshii Exp $
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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.srp.SRPHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/09/30 03:40:55 $ Tag: $Name: $
 */
public class SRPCountryMakerReadCmd extends AbstractCommand {
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
	public SRPCountryMakerReadCmd() {
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
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialCollateralParameter", "com.integrosys.cms.app.collateral.bus.ICollateralParameter",
						FORM_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "CollateralParameterTrxValue", ICollateralParameterTrxValue.class.getName(), SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "InitialCollateralParameter", "java.util.list", FORM_SCOPE } });
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
		ICollateralParameter obCollateralParameter = (ICollateralParameter) map.get("InitialCollateralParameter");

		String event = (String) map.get("event");

		if (!event.equals("maker_add_edit_srpcountry_error")) {
			ICollateralParameterTrxValue collateralParameterTrxVal = null;
			try {
				collateralParameterTrxVal = getCollateralParameterProxy().getCollateralParameterTrxValue(trxContext,
						obCollateralParameter.getCountryIsoCode(), obCollateralParameter.getSecuritySubTypeId());
			}
			catch (CollateralException ex) {
				throw new CommandProcessingException(
						"failed to retrieve collateral parameter workflow value by country iso code ["
								+ obCollateralParameter.getCountryIsoCode() + "] and security subtype id ["
								+ obCollateralParameter.getSecuritySubTypeId() + "]", ex);
			}

			ICollateralParameter[] parameters = collateralParameterTrxVal.getCollateralParameters();
			Arrays.sort(parameters);
			collateralParameterTrxVal.setCollateralParameters(parameters);

			// if current status is other than ACTIVE & REJECTED, then show
			// workInProgress.
			// i.e. allow edit only if status is either ACTIVE or REJECTED
			DefaultLogger.debug(this, "Inside doExecute()  CURRENT State = " + collateralParameterTrxVal.getStatus());
			if (SRPCountryAction.EVENT_VIEW.equals(event)) {

				resultMap.put("CollateralParameterTrxValue", collateralParameterTrxVal);
				resultMap.put("InitialCollateralParameter", collateralParameterTrxVal.getCollateralParameters());
				resultMap.put("timefrequencies.map", SRPHelper.buildTimeFrequencyMap());
			}
			else {
				if (!(collateralParameterTrxVal.getStatus().equals(ICMSConstant.STATE_ND) || collateralParameterTrxVal
						.getStatus().equals(ICMSConstant.STATE_ACTIVE)

				)) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialCollateralParameter", collateralParameterTrxVal
							.getStagingCollateralParameters());
				}
				else {
					resultMap.put("CollateralParameterTrxValue", collateralParameterTrxVal);
				}

				resultMap.put("InitialCollateralParameter", parameters);
			}
		}
		if (CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ) != null) {
			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
		}
		if (CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ) != null) {
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));
		}

		// set countrie map
		resultMap.put("countries.map", SRPHelper.buildCountryeMap());

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}