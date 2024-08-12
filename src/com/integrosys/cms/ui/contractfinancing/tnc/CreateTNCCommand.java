/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.tnc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class CreateTNCCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "contractFinancingObj", "com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing",
						FORM_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			DefaultLogger.debug(this, "in CreateTNCCommand");

			IContractFinancing contractFinancingObj = (IContractFinancing) hashMap.get("contractFinancingObj");

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			trxValue.setStagingContractFinancing(contractFinancingObj);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
