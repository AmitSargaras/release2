package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;

public class MakerPrepareUploadOtherBankCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	private IOtherBankProxyManager otherBankProxyManager;
	

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadOtherBankCmd() {
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
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  
					 
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
		return (new String[][] { 
				{"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue", SERVICE_SCOPE},
				
				 });
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
		
		  OBOtherBankTrxValue otherbankTrxValue = new OBOtherBankTrxValue();
		  
		  //resultMap.put("IOtherBankTrxValue", otherbankTrxValue);
		 // resultMap.put("hubValueList",getHUBList() );

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
