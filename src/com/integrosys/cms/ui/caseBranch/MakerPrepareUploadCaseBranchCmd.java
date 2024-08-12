/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;

/**
*@author $Author: Abhijit R$
*Command to read CaseBranch
 */
public class MakerPrepareUploadCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseBranchProxyManager caseBranchProxy;

	
	
	
	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(
			ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadCaseBranchCmd() {
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
				{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue", SERVICE_SCOPE},
				
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
		
		  OBCaseBranchTrxValue caseBranchTrxValue = new OBCaseBranchTrxValue();
		  
		  //resultMap.put("ICaseBranchTrxValue", caseBranchTrxValue);
		 // resultMap.put("hubValueList",getHUBList() );

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	

}
