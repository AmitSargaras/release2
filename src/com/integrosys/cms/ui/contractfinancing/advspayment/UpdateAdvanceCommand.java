/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.IAdvance;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class UpdateAdvanceCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UpdateAdvanceCommand() {
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
				{ "advanceIndex", "java.lang.String", SERVICE_SCOPE },
				{ "obAdvanceForm", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", FORM_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Updates to the contract financing is done.
	 * 
	 * @param hashMap is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			DefaultLogger.debug(this, "in UpdateAdvanceCommand");

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

			IAdvance[] newAdvanceList = contractFinancingObj.getAdvanceList();
			String advanceIndex = (String) hashMap.get("advanceIndex");
			newAdvanceList[Integer.parseInt(advanceIndex)] = (OBAdvance) hashMap.get("obAdvanceForm");

			contractFinancingObj.setAdvanceList(newAdvanceList);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}