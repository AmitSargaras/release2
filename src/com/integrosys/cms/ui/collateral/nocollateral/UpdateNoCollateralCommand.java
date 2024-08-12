package com.integrosys.cms.ui.collateral.nocollateral;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.nocollateral.subtype.INoCollaterala;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class UpdateNoCollateralCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "form.chargeObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "actualCharge", "com.integrosys.cms.app.collateral.bus.ILimitCharge", REQUEST_SCOPE },
				{ "stageCharge", "com.integrosys.cms.app.collateral.bus.ILimitCharge", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		INoCollaterala iNoCollaterala;

		long index = Long.parseLong((String) map.get("indexID"));

		String from_event = (String) map.get("from_event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		if ((from_event != null) && from_event.equals("read")) {
			iNoCollaterala = (INoCollaterala) itrxValue.getCollateral();
		}
		else {
			iNoCollaterala = (INoCollaterala) itrxValue.getStagingCollateral();
			if ((from_event != null) && from_event.equals("process")) {
			}
		}

		result.put("indexID", map.get("indexID"));
		result.put("subtype", map.get("subtype"));
		result.put("from_event", from_event);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
