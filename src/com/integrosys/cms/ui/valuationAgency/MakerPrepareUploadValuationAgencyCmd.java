package com.integrosys.cms.ui.valuationAgency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

public class MakerPrepareUploadValuationAgencyCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IValuationAgencyProxyManager valuationAgencyProxy;

	
	
	
	

	/**
	 * Default Constructor
	 */
	public MakerPrepareUploadValuationAgencyCmd() {
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
				{"IValuationAgencyTrxValue", "com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue", SERVICE_SCOPE},
				
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
		
		  OBValuationAgencyTrxValue valuationAgencyTrxValue = new OBValuationAgencyTrxValue();
		  
		  //resultMap.put("IValuationAgencyTrxValue", valuationAgencyTrxValue);
		 // resultMap.put("hubValueList",getHUBList() );

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}
	
	
	

}
