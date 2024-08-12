/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalMakerReadRejectedCmd.java,v 1.2 2003/09/15 03:54:31 pooja Exp $
 */

package com.integrosys.cms.ui.srp.global;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.proxy.parameter.ICollateralParameterProxy;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralSubTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.srp.SRPHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/15 03:54:31 $ Tag: $Name: $
 */
public class SRPGlobalMakerReadRejectedCmd extends AbstractCommand {
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
	public SRPGlobalMakerReadRejectedCmd() {
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
		return (new String[][] {
				{ "CollateralSubTypeTrxValue", ICollateralSubTypeTrxValue.class.getName(), SERVICE_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "countries.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "InitialCollateralSubType", "java.util.list", FORM_SCOPE } });
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

		ICollateralSubTypeTrxValue collateralSubTypeTrxVal = null;
		try {
			collateralSubTypeTrxVal = getCollateralParameterProxy().getCollateralSubTypeTrxValueByTrxID(trxContext,
					trxId);
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException(
					"failed to retrieve collateral sub type workflow values by transaction id [" + trxId + "]", ex);
		}

		ICollateralSubType[] subtype = collateralSubTypeTrxVal.getStagingCollateralSubTypes();
		Arrays.sort(subtype);
		collateralSubTypeTrxVal.setStagingCollateralSubTypes(subtype);

		// if current status is other than ACTIVE & REJECTED, then show
		// workInProgress.
		// i.e. allow edit only if status is either ACTIVE or REJECTED
		if ((!collateralSubTypeTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
				&& (!collateralSubTypeTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED))) {
			resultMap.put("wip", "wip");
			resultMap.put("InitialCollateralSubType", collateralSubTypeTrxVal.getStagingCollateralSubTypes());
		}
		else {
			resultMap.put("CollateralSubTypeTrxValue", collateralSubTypeTrxVal);
		}

		resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(SRPHelper.TIME_FREQUENCY_CODE));
		resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(SRPHelper.TIME_FREQUENCY_CODE));
		resultMap.put("InitialCollateralSubType", collateralSubTypeTrxVal.getStagingCollateralSubTypes());

		resultMap.put("countries.map", SRPHelper.buildCountryeMap());
		resultMap.put("timefrequencies.map", SRPHelper.buildTimeFrequencyMap());

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
