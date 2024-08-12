/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.proxy.ContractFinancingProxyManagerFactory;
import com.integrosys.cms.app.contractfinancing.proxy.IContractFinancingProxyManager;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class ReadContractFinancingCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadContractFinancingCommand() {
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
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "contractID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "initEvent", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE },
				{ "contractFinancingObj", "com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing",
						FORM_SCOPE }, // Collection of
										// com.integrosys.cms.app.contractfinancing
										// .bus.OBContractFinancing
				{ "contractFinancingObj", "com.integrosys.cms.app.contractfinancing.bus.OBContractFinancing",
						REQUEST_SCOPE }, // need for view page
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "isEdit", "java.lang.String", SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, "in ReadContractFinancingCommand");
		String trxID = (String) map.get("trxID");
		String event = (String) map.get("event");
		String initEvent = (String) map.get("initEvent");
		String contractID = (String) map.get("contractID");

		try {

			IContractFinancing contractFinancingObj;

			IContractFinancingProxyManager proxy = ContractFinancingProxyManagerFactory
					.getContractFinancingProxyManager();

			IContractFinancingTrxValue trxValue;

			if ((trxID == null) || trxID.equals("")) { // Maker Edit
				DefaultLogger.debug(this, ">>>>>>>>>>>> contractID = " + map.get("contractID"));
				if (contractID != null) {
					trxValue = proxy.getContractFinancingTrxValue(Long.parseLong((String) map.get("contractID")));
					trxValue.setStagingContractFinancing(trxValue.getContractFinancing());
				}
				else {
					DefaultLogger.debug(this, "map.get(contractFinancingTrxValue) = "
							+ map.get("contractFinancingTrxValue"));
					trxValue = (IContractFinancingTrxValue) map.get("contractFinancingTrxValue");
				}

			}
			else { // Maker/Checher from To-Do
				trxValue = proxy.getContractFinancingTrxValueByTrxID(trxID);
			}
			contractFinancingObj = trxValue.getStagingContractFinancing();

			String trxStatus = trxValue.getStatus();
			if (event.equals(ContractFinancingAction.EVENT_MAKER_PROCESS)) {
				String fromState = trxValue.getFromState();
				DefaultLogger.debug(this, ">>>>>> fromState = " + fromState);
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					resultMap.put("event", ContractFinancingAction.EVENT_MAKER_PREPARE_CREATE);
					resultMap.put("isEdit", "true");
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					resultMap.put("event", ContractFinancingAction.EVENT_MAKER_PREPARE_UPDATE);
					resultMap.put("isEdit", "true");
				}
				if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					resultMap.put("event", ContractFinancingAction.EVENT_MAKER_PREPARE_DELETE);
					resultMap.put("isEdit", "false");
				}
			}
			if (ContractFinancingAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
					|| ContractFinancingAction.EVENT_MAKER_PREPARE_DELETE.equals(event)) {
				if (!(ICMSConstant.STATE_ACTIVE.equals(trxStatus)) && (contractID != null)) {
					resultMap.put("wip", "wip");
				}
			}

			DefaultLogger.debug(this, ">>>>>> initEvent = " + initEvent);
			if (initEvent.equals(ContractFinancingAction.EVENT_MAKER_PREPARE_CREATE)
					|| initEvent.equals(ContractFinancingAction.EVENT_MAKER_PREPARE_UPDATE)) {
				resultMap.put("isEdit", "true");
			}
			else if (initEvent.equals(ContractFinancingAction.EVENT_VIEW)
					|| initEvent.equals(ContractFinancingAction.EVENT_MAKER_PREPARE_CLOSE)
					|| initEvent.equals(ContractFinancingAction.EVENT_MAKER_PREPARE_DELETE)) {
				resultMap.put("isEdit", "false");
			}

			resultMap.put("contractFinancingTrxValue", trxValue);
			DefaultLogger.debug(this, "end ReadContractFinancingCommand");

			resultMap.put("contractFinancingObj", contractFinancingObj);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}