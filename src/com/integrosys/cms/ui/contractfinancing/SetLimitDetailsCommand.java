/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.app.contractfinancing.trx.OBContractFinancingTrxValue;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SetLimitDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "sourceLimit", "java.lang.String", REQUEST_SCOPE },
				{ "productDescription", "java.lang.String", REQUEST_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "limitProfileID", "java.lang.String", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", SERVICE_SCOPE },
				{ "sourceLimit", "java.lang.String", SERVICE_SCOPE },
				{ "productDescription", "java.lang.String", SERVICE_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");

			if (trxValue == null) {
				trxValue = new OBContractFinancingTrxValue();
			}

			if (hashMap.get("limitID") != null) {
				trxValue = new OBContractFinancingTrxValue();
				IContractFinancing newContractFinancing = new OBContractFinancing();
				newContractFinancing.setLimitProfileID(Long.parseLong((String) hashMap.get("limitProfileID")));
				newContractFinancing.setLimitID(Long.parseLong((String) hashMap.get("limitID")));
				newContractFinancing.setSourceLimit((String) hashMap.get("sourceLimit"));
				newContractFinancing.setProductDescription((String) hashMap.get("productDescription"));
				trxValue.setStagingContractFinancing(newContractFinancing);
			}

			resultMap.put("contractFinancingTrxValue", trxValue);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}