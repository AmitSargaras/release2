/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.ArrayList;
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
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SG_CreateAdvanceCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "obAdvanceForm", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", FORM_SCOPE },
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

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			IContractFinancing contractFinancingObj = trxValue.getStagingContractFinancing();

			OBAdvance newAdvance = (OBAdvance) hashMap.get("obAdvanceForm");

			ArrayList advanceList = new ArrayList();
			IAdvance[] oldAdvance = contractFinancingObj.getAdvanceList();

			boolean addNew = true;
			if ((oldAdvance != null) && (oldAdvance.length != 0)) {
				for (int i = 0; i < oldAdvance.length; i++) {
					OBAdvance obAdvanceTemp = (OBAdvance) oldAdvance[i];
					advanceList.add(obAdvanceTemp);

					// check duplicate data
					// need?
				}
			}
			if (addNew) {
				advanceList.add(newAdvance);
			}

			contractFinancingObj.setAdvanceList((IAdvance[]) advanceList.toArray(new OBAdvance[0]));

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
