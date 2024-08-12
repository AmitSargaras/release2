package com.integrosys.cms.ui.collateral.valuationfromlos;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 3, 2007 Time: 9:08:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class ReadValuationFromLOSCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				// {"subtype", "java.lang.String", REQUEST_SCOPE},
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				// {"from_event", "java.lang.String", REQUEST_SCOPE},
				{ "formActionName", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "valuationFromLOS", "com.integrosys.cms.app.collateral.bus.IValuation", REQUEST_SCOPE },
				{ "form.valuationFromLOSObject", "java.lang.Object", FORM_SCOPE },
				{ "form.valuationFromLOSObject", "java.lang.Object", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				// {"subtype", "java.lang.String", REQUEST_SCOPE},
				// {"from_event", "java.lang.String", REQUEST_SCOPE},
				{ "formActionName", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Inside in doExecute");

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String indexStr = (String) map.get("indexID");
		// String from_event = (String) map.get("from_event");
		String formActionName = (String) map.get("formActionName");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		ICollateral iCol = null;
		IValuation[] iValuationArray = null;

		if (itrxValue == null) {
			DefaultLogger.debug(this, "ICollateralTrxValue is null :");
		}

		else {
			iCol = (ICollateral) itrxValue.getCollateral();
			iValuationArray = iCol.getValuationFromLOS();
		}

		IValuation valuation = new OBValuation();

		/*
		 * if (from_event != null && from_event.equals("read")) {
		 * System.out.println("valuationFromLOSList = " +
		 * valuationFromLOSList.size()); } else { DefaultLogger.debug(this,
		 * "from event is :" + from_event); }
		 */

		// if ("read".equals(from_event) || "prepare_update".equals(from_event))
		// {
		valuation = getDetails(iValuationArray, indexStr);
		/*
		 * } else { int index = Integer.parseInt(indexStr+""); int index = 1; if
		 * ((int) index >= 0) { valuation = (IValuation) iValuationArray[index];
		 * } else { valuation = null; } }
		 */

		// DefaultLogger.debug(this, "****indexID is :" + indexStr);
		HashMap objMap = new HashMap();
		objMap.put("col", iCol);

		result.put("valuationFromLOS", valuation);

		if (itrxValue != null) {
			result.put("serviceColObj", itrxValue);
		}
		result.put("indexID", map.get("indexID"));
		// result.put("subtype", map.get("subtype"));
		// result.put("from_event", from_event);
		result.put("formActionName", formActionName);

		DefaultLogger.debug(this, "Existing in doExecute");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IValuation getDetails(IValuation[] iValuationArray, String indexStr) {
		IValuation obj = new OBValuation();
		if (iValuationArray == null) {
			return obj;
		}
		for (int counter = 0; counter < iValuationArray.length; counter++) {
			IValuation valuation = (IValuation) iValuationArray[counter];
			if (String.valueOf(valuation.getValuationID()).equals(indexStr)) {
				return valuation;
			}
			else {
				continue;
			}
		}

		return obj;
	}

}
