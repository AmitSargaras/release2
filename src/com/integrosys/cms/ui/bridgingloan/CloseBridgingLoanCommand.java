package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.proxy.BridgingLoanProxyManagerFactory;
import com.integrosys.cms.app.bridgingloan.proxy.IBridgingLoanProxyManager;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 15, 2007 Time: 2:18:12 AM To
 * change this template use File | Settings | File Templates.
 */
public class CloseBridgingLoanCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CloseBridgingLoanCommand() {
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
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");

		try {
			IBridgingLoanProxyManager proxy = BridgingLoanProxyManagerFactory.getBridgingLoanProxyManager();
			String fromState = trxValue.getFromState();

			if (fromState.equals(ICMSConstant.STATE_ND) || fromState.equals(ICMSConstant.STATE_PENDING_CREATE)) {
				proxy.makerCloseBridgingLoan(trxContext, trxValue);
			}
			else if (fromState.equals(ICMSConstant.STATE_PENDING_UPDATE)) {
				proxy.makerCloseUpdateBridgingLoan(trxContext, trxValue);
			}
			else if (fromState.equals(ICMSConstant.STATE_DRAFT) || fromState.equals(ICMSConstant.STATE_ACTIVE)) {
				// state_draft maybe from actual or staging
				if (trxValue.getBridgingLoan() != null) { // have actual
					proxy.makerCloseUpdateBridgingLoan(trxContext, trxValue);
				}
				else { // no actual
					proxy.makerCloseBridgingLoan(trxContext, trxValue);
				}
			}
			else if (fromState.equals(ICMSConstant.STATE_PENDING_DELETE)) {
				proxy.makerCloseDeleteBridgingLoan(trxContext, trxValue);
			}
			resultMap.put("request.ITrxValue", trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}