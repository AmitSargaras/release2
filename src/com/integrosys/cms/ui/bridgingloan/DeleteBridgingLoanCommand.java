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
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 18, 2007 Time: 6:07:18 PM To
 * change this template use File | Settings | File Templates.
 */
public class DeleteBridgingLoanCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public DeleteBridgingLoanCommand() {
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
	 * results back into the HashMap. Updates to the contract financing is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxContext);

		try {
			IBridgingLoan objBridgingLoan = trxValue.getStagingBridgingLoan();
			objBridgingLoan.setIsDeletedInd(true);
			trxValue.setTrxContext(trxContext);

			IBridgingLoanProxyManager proxy = BridgingLoanProxyManagerFactory.getBridgingLoanProxyManager();
			proxy.makerDeleteBridgingLoan(trxContext, trxValue);
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