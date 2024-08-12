package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ListFacilityTypeSummaryCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ListFacilityTypeSummaryCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "contractFinancingTrxValue",
				"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityTypeList",
				"com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");
		DefaultLogger.debug(this, "trxValue = " + trxValue);

		IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

		if (contractFinancingObj != null) {
			IContractFacilityType[] facilityTypeList = contractFinancingObj.getFacilityTypeList();
			resultMap.put("facilityTypeList", facilityTypeList);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
