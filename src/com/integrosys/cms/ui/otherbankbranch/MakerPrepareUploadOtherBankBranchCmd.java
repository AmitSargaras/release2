package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;

public class MakerPrepareUploadOtherBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	

	
	private IOtherBranchProxyManager otherBranchProxyManager;
	
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadOtherBankBranchCmd() {
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
				  {"otherBankId","java.lang.String",REQUEST_SCOPE},
				  {"OtherBankId","java.lang.String",REQUEST_SCOPE}
				  
					 
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
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue", SERVICE_SCOPE},
				{"otherBankId","java.lang.String",REQUEST_SCOPE},
				
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
		String bankId = (String) map.get("otherBankId");
		OBOtherBankBranchTrxValue otherbankTrxValue = new OBOtherBankBranchTrxValue();
		  
		  //resultMap.put("IOtherBankTrxValue", otherbankTrxValue);
		 // resultMap.put("hubValueList",getHUBList() );
		resultMap.put("otherBankId", bankId);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
