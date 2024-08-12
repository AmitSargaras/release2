/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;

 /**
 *@author $Govind: Sahu $
 *Command to read Credit Approval
 */
public class MakerPrepareUploadMutualFundsItemCommand extends AbstractCommand implements ICommonEventConstant {
	
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy;




	/**
	 * @return the mutualFundsFeedProxy
	 */
	public IMutualFundsFeedProxy getMutualFundsFeedProxy() {
		return mutualFundsFeedProxy;
	}

	/**
	 * @param mutualFundsFeedProxy the mutualFundsFeedProxy to set
	 */
	public void setMutualFundsFeedProxy(IMutualFundsFeedProxy mutualFundsFeedProxy) {
		this.mutualFundsFeedProxy = mutualFundsFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadMutualFundsItemCommand() {
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
		return (new String[][] { });
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
		
		  
		  //resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	

}
