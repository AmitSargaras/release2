package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.bridgingloan.trx.OBBridgingLoanTrxValue;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 16, 2007 Time: 2:25:37 PM To
 * change this template use File | Settings | File Templates.
 */
public class ProcessBridgingLoanCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ProcessBridgingLoanCommand() {
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
				{ "objBridgingLoan", "com.integrosys.cms.app.bridgingloan.bus.OBBridgingLoan", FORM_SCOPE }, // Collection
																												// of
																												// com
																												// .
																												// integrosys
																												// .
																												// cms
																												// .
																												// app
																												// .
																												// bridgingloan
																												// .
																												// bus
																												// .
																												// OBBridgingLoan
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "bridgingLoanTrxValue",
				"com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue", SERVICE_SCOPE }, });
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
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");

		if (trxValue == null) {
			trxValue = new OBBridgingLoanTrxValue();
		}
		IBridgingLoan OBBridgingLoan = (IBridgingLoan) map.get("objBridgingLoan");

		if (OBBridgingLoan != null) {
			trxValue.setStagingBridgingLoan(OBBridgingLoan);
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}