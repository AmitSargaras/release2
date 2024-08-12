/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.ui.common.ContractFinancingFacTypeList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PrepareFacilityTypeCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "contractFinancingTrxValue",
				"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "facTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "facTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			ContractFinancingFacTypeList contractFinancingFacTypeList = ContractFinancingFacTypeList.getInstance();
			List values = new ArrayList(contractFinancingFacTypeList.getContractFinancingFacTypeProperty());
			List labels = new ArrayList(contractFinancingFacTypeList.getContractFinancingFacTypeLabel());

			IContractFinancingTrxValue trxValue = (IContractFinancingTrxValue) hashMap.get("contractFinancingTrxValue");
			if ((trxValue != null) && (trxValue.getStagingContractFinancing() != null)
					&& (trxValue.getStagingContractFinancing().getFacilityTypeList() != null)) {
				IContractFacilityType[] facilityTypeList = trxValue.getStagingContractFinancing().getFacilityTypeList();
				HashMap facilityTypeMap = new HashMap();
				for (int i = 0; i < facilityTypeList.length; i++) {
					if (!facilityTypeList[i].getIsDeleted()) { // not include
																// deleted3
						facilityTypeMap.put(facilityTypeList[i].getFacilityType(), facilityTypeList[i]
								.getFacilityType());
					}
				}
				HashMap hm = filterList(values, labels, facilityTypeMap);
				values = (List) hm.get("values");
				labels = (List) hm.get("labels");
			}

			resultMap.put("facTypeValues", values);
			resultMap.put("facTypeLabels", labels);

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());

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
