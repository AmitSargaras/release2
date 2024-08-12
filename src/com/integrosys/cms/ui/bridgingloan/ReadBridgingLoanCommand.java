package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.proxy.BridgingLoanProxyManagerFactory;
import com.integrosys.cms.app.bridgingloan.proxy.IBridgingLoanProxyManager;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 11, 2007 Time: 9:44:26 AM To
 * change this template use File | Settings | File Templates.
 */
public class ReadBridgingLoanCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadBridgingLoanCommand() {
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
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "projectID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "initEvent", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE },
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "initEvent", "java.lang.String", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, "Inside ReadBridgingLoanCommand doExecute()");
		String trxID = (String) map.get("trxID");
		String event = String.valueOf(map.get("event"));
		String initEvent = String.valueOf(map.get("initEvent"));
		String projectID = (String) map.get("projectID");

		try {
			IBridgingLoanProxyManager proxy = BridgingLoanProxyManagerFactory.getBridgingLoanProxyManager();
			IBridgingLoanTrxValue trxValue = null;
			IBridgingLoan objBridgingLoan = null;

			if ((trxID == null) || trxID.equals("")) { // Maker Edit
				DefaultLogger.debug(this, ">>>>>>>>>>>> projectID = " + map.get("projectID"));
				if ((projectID != null) && !projectID.equals("")) {
					if (Long.parseLong(projectID) != ICMSConstant.LONG_INVALID_VALUE) {
						trxValue = proxy.getBridgingLoanTrxValue(Long.parseLong((String) map.get("projectID")));
						trxValue.setStagingBridgingLoan(trxValue.getBridgingLoan()); // Get
																						// from
																						// actual
					}
				}
				else {
					trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
				}
			}
			else { // Maker/Checher from To-Do
				trxValue = proxy.getBridgingLoanTrxValueByTrxID(trxID);
			}
			objBridgingLoan = trxValue.getStagingBridgingLoan();

			String trxStatus = trxValue.getStatus();
			String fromState = trxValue.getFromState();
			DefaultLogger.debug(this, ">>>>>> trxStatus = " + trxStatus);
			DefaultLogger.debug(this, ">>>>>> fromState = " + fromState);

			if (event.equals(BridgingLoanAction.EVENT_MAKER_PROCESS)) {
				if (fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
					resultMap.put("event", BridgingLoanAction.EVENT_MAKER_PREPARE_CREATE);
					resultMap.put("isEdit", "true");
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
					resultMap.put("event", BridgingLoanAction.EVENT_MAKER_PREPARE_UPDATE);
					resultMap.put("isEdit", "true");
				}
				else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
					resultMap.put("event", BridgingLoanAction.EVENT_MAKER_PREPARE_DELETE);
					resultMap.put("isEdit", "false");
				}
			}
			if (event.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_UPDATE)
					|| event.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_DELETE)) {
				if (!(ICMSConstant.STATE_ACTIVE.equals(trxStatus)) && (projectID != null)) {
					resultMap.put("wip", "wip");
				}
			}
			DefaultLogger.debug(this, ">>>>>> initEvent = " + initEvent);
			if (!initEvent.equals("")) {
				if (initEvent.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_CREATE)
						|| initEvent.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_UPDATE)) {
					resultMap.put("isEdit", "true");
				}
				else if (initEvent.equals(BridgingLoanAction.EVENT_VIEW)
						|| initEvent.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_CLOSE)
						|| initEvent.equals(BridgingLoanAction.EVENT_MAKER_PREPARE_DELETE)) {
					resultMap.put("isEdit", "false");
				}
			}
			resultMap.put("bridgingLoanTrxValue", trxValue);
			resultMap.put("objBridgingLoan", objBridgingLoan);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			e.printStackTrace();
			throw new CommandProcessingException(e.toString());
		}
	}
}