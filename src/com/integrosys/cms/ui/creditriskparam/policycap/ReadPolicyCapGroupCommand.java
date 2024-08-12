/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.PolicyCapComparator;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.StockExchangeList;

/**
 * Purpose: Policy Cap Group Command Class which prepare transaction object
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 31/AUG/2007 $ Tag: $Name: $
 */
public class ReadPolicyCapGroupCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ReadPolicyCapGroupCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "bankEntity", "java.lang.String", REQUEST_SCOPE },
				{ "bankEntityName", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "isRejected", "java.lang.String", REQUEST_SCOPE },
				{ "stockExchangeName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "policyCapGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue", SERVICE_SCOPE },
				{ "policyCapGroupMap", "java.util.HashMap", FORM_SCOPE },
				{ "stockExchangeName", "java.lang.String", REQUEST_SCOPE },
				{ "bankEntityName", "java.lang.String", REQUEST_SCOPE },
				{ "bankEntity", "java.lang.String", REQUEST_SCOPE },
				{ "isBankGroup", "java.lang.String", REQUEST_SCOPE },
				{ "isRejected", "java.lang.String", REQUEST_SCOPE },
				{ "boardDescList", "com.integrosys.cms.ui.common.CommonCodeList", SERVICE_SCOPE }, });
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

		String event = (String) map.get("event");
		String stockExchange = (String) map.get("stockExchange");
		String stockExchangeName = (String) map.get("stockExchangeName");
		String bankEntity = (String) map.get("bankEntity");
		String bankEntityName = (String) map.get("bankEntityName");
		String trxID = (String) map.get("trxID");
		String isRejected = (String) map.get("isRejected");
		boolean isEdit = (isRejected == null);

		IPolicyCapGroupTrxValue trxValue = null;
		String isBankGroup = null;
		try {

			IPolicyCapProxyManager proxy = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			DefaultLogger.debug(this, ">>>>>>>>>>>> trxID field = " + trxID);
			if ((trxID == null) || trxID.equals("")) { // Maker Edit
				DefaultLogger.debug(this, ">>>>>>>>>>>> Getting the Transaction object");
				IPolicyCapGroup policyCapGroup = proxy.getPolicyCapGroupByExchangeBank(stockExchange, bankEntity);
				if ((policyCapGroup != null) && (policyCapGroup.getPolicyCapArray() != null)
						&& (policyCapGroup.getPolicyCapArray().length > 0)) {
					DefaultLogger.debug(this, ">>>>>>>>>>>>> policyCapGroupID = "
							+ policyCapGroup.getPolicyCapGroupID());
					trxValue = proxy.getPolicyCapGroupTrxValue(policyCapGroup.getPolicyCapGroupID());
				}
			}
			else { // Maker/Checher from To-Do
				DefaultLogger.debug(this, ">>>>>>>>>>>> In getting by trxID");
				trxValue = proxy.getPolicyCapGroupTrxValueByTrxID(trxID);
				DefaultLogger.debug(this, ">>>>>>>>>>>> trxValue=" + trxValue);
			}

			if (trxValue != null) {
				if (trxValue.getPolicyCapGroup() != null) {
					stockExchange = trxValue.getPolicyCapGroup().getStockExchange();
					bankEntity = trxValue.getPolicyCapGroup().getBankEntity();
					DefaultLogger.debug(this, "stockExchange=" + stockExchange);
					DefaultLogger.debug(this, "bankEntity=" + bankEntity);
				}

				String trxStatus = trxValue.getStatus();
				DefaultLogger.debug(this, ">>>>>> event = " + event);
				DefaultLogger.debug(this, ">>>>>> trxStatus = " + trxStatus);
				if (PolicyCapAction.EVENT_MAKER_EDIT.equals(event)) {
					DefaultLogger.debug(this, ">>>>>>>>>>> isEdit = " + isEdit);
					if (isEdit && !(ICMSConstant.STATE_ACTIVE.equals(trxStatus))) {
						resultMap.put("wip", "wip");
					}
				}

				// sort the order of the policy cap array in actual policy cap
				// group
				if ((trxValue.getPolicyCapGroup() != null)
						&& (trxValue.getPolicyCapGroup().getPolicyCapArray() != null)) {
					IPolicyCap[] array = trxValue.getPolicyCapGroup().getPolicyCapArray();
					java.util.Arrays.sort(array, new PolicyCapComparator());
					trxValue.getPolicyCapGroup().setPolicyCapArray(array);
				}

				// sort the order of the policy cap array in staging policy cap
				// group
				if ((trxValue.getStagingPolicyCapGroup() != null)
						&& (trxValue.getStagingPolicyCapGroup().getPolicyCapArray() != null)) {
					IPolicyCap[] array = trxValue.getStagingPolicyCapGroup().getPolicyCapArray();
					java.util.Arrays.sort(array, new PolicyCapComparator());
					trxValue.getStagingPolicyCapGroup().setPolicyCapArray(array);
				}

				HashMap policyCapGroupMap = new HashMap();
				policyCapGroupMap.put("policyCapGroup", (isEdit) ? trxValue.getPolicyCapGroup() : trxValue
						.getStagingPolicyCapGroup());

				// determine if the selected entity is the bank group.
				isBankGroup = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP).getCommonCodeLabel(
						bankEntity);
				policyCapGroupMap.put("isBankGroup", isBankGroup);

				resultMap.put("policyCapGroupMap", policyCapGroupMap);

			}

			// Getting stock Exchange name
			DefaultLogger.debug(this, "stockExchangeName before=" + stockExchangeName);
			if (stockExchangeName == null) {
				stockExchangeName = StockExchangeList.getInstance().getStockExchangeName(stockExchange);
				DefaultLogger.debug(this, "stockExchangeName=" + stockExchangeName);
			}

			// Getting bank Entity name
			DefaultLogger.debug(this, "bankEntityName before=" + bankEntityName);
			if (bankEntityName == null) {
				bankEntityName = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY).getCommonCodeLabel(
						bankEntity);

				// See if this belongs to a bank entity group
				if (bankEntityName == null) {
					bankEntityName = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP)
							.getCommonCodeLabel(bankEntity);
				}

				DefaultLogger.debug(this, "bankEntityName=" + bankEntityName);
			}

			CommonCodeList boardDescList = CommonCodeList.getInstance(null, null, "BOARD_TYPE", false, stockExchange);
			resultMap.put("boardDescList", boardDescList);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		resultMap.put("isRejected", isRejected);
		resultMap.put("stockExchangeName", stockExchangeName);
		resultMap.put("bankEntity", bankEntity);
		resultMap.put("bankEntityName", bankEntityName);
		resultMap.put("isBankGroup", isBankGroup);
		resultMap.put("policyCapGroupTrxValue", trxValue);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
