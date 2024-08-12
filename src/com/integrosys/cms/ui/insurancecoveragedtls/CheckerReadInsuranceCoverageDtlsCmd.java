/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.OBInsuranceCoverageDtls;
import com.integrosys.cms.app.insurancecoveragedtls.proxy.IInsuranceCoverageDtlsProxyManager;
import com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.trx.OBInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

/**
 *$Author: Dattatray Thorat $
 *Command for checker to read Insurance Coverage Details Trx value
 */
public class CheckerReadInsuranceCoverageDtlsCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager;

	/**
	 * @return the insuranceCoverageDtlsProxyManager
	 */
	public IInsuranceCoverageDtlsProxyManager getInsuranceCoverageDtlsProxyManager() {
		return insuranceCoverageDtlsProxyManager;
	}

	/**
	 * @param insuranceCoverageDtlsProxyManager the insuranceCoverageDtlsProxyManager to set
	 */
	public void setInsuranceCoverageDtlsProxyManager(
			IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager) {
		this.insuranceCoverageDtlsProxyManager = insuranceCoverageDtlsProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerReadInsuranceCoverageDtlsCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "InsuranceCoverageDtlsObj", "com.integrosys.cms.app.insurancecoveragedtls.bus.OBInsuranceCoverageDtls", FORM_SCOPE },
				{"IInsuranceCoverageDtlsTrxValue", "com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue", SERVICE_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
	
			String icCode=(String) (map.get("TrxId"));
			String event=(String) (map.get("event"));

			IInsuranceCoverageDtlsTrxValue trxValue = (OBInsuranceCoverageDtlsTrxValue) getInsuranceCoverageDtlsProxyManager().getInsuranceCoverageDtlsByTrxID(icCode);
			
			// function to get stging value of Insurance Coverage Details trx value
			IInsuranceCoverageDtls insuranceCoverageDtls = (OBInsuranceCoverageDtls) trxValue.getStagingInsuranceCoverageDtls();
			
			resultMap.put("TrxId", icCode);
			resultMap.put("event", event);
			resultMap.put("IInsuranceCoverageDtlsTrxValue", trxValue);
			resultMap.put("InsuranceCoverageDtlsObj", insuranceCoverageDtls);
		} catch (InsuranceCoverageDtlsException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
