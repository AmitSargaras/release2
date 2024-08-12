/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.feed.bond.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;

 /**
 *@author $Govind: Sahu $
 *Command to read Credit Approval
 */
public class MakerPrepareUploadBondItemCommand extends AbstractCommand implements ICommonEventConstant {
	
	
	private IBondFeedProxy bondFeedProxy;



	/**
	 * @return the bondFeedProxy
	 */
	public IBondFeedProxy getBondFeedProxy() {
		return bondFeedProxy;
	}

	/**
	 * @param bondFeedProxy the bondFeedProxy to set
	 */
	public void setBondFeedProxy(IBondFeedProxy bondFeedProxy) {
		this.bondFeedProxy = bondFeedProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadBondItemCommand() {
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
