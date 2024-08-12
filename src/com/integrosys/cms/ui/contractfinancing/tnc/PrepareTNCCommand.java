/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.tnc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.ITNC;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.ui.common.ContractFinancingTermsList;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PrepareTNCCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "contractFinancingTrxValue",
				"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "termsLabels", "java.util.List", REQUEST_SCOPE },
				{ "termsValues", "java.util.List", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			ContractFinancingTermsList contractFinancingTermsList = ContractFinancingTermsList.getInstance();
			List values = new ArrayList(contractFinancingTermsList.getContractFinancingTermsProperty());
			List labels = new ArrayList(contractFinancingTermsList.getContractFinancingTermsLabel());

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			if ((trxValue != null) && (trxValue.getStagingContractFinancing() != null)
					&& (trxValue.getStagingContractFinancing().getTncList() != null)) {
				ITNC[] tncList = trxValue.getStagingContractFinancing().getTncList();
				HashMap tncMap = new HashMap();
				for (int i = 0; i < tncList.length; i++) {
					if (!tncList[i].getIsDeleted()) { // not include deleted
						tncMap.put(tncList[i].getTerms(), tncList[i].getTerms());
					}
				}
				HashMap hm = filterList(values, labels, tncMap);
				values = (List) hm.get("values");
				labels = (List) hm.get("labels");
			}

			resultMap.put("termsValues", values);
			resultMap.put("termsLabels", labels);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	/**
	 * This method is to filter a list, which will remove previous selected
	 * value
	 * 
	 * @param values - list of value
	 * @param labels - list of labels
	 * @param hm - previous selected value
	 * @return HashMap with the Result
	 */
	public HashMap filterList(List values, List labels, HashMap hm) {

		for (int i = values.size() - 1; i > -1; i--) {
			if (hm.get(values.get(i)) != null) {
				values.remove(i);
				labels.remove(i);
			}
		}
		HashMap returnHm = new HashMap();
		returnHm.put("values", values);
		returnHm.put("labels", labels);
		return returnHm;
	}
}