package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingDaoImpl;
import com.integrosys.cms.app.valuationAmountAndRating.proxy.IValuationAmountAndRatingProxyManager;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;

public class MakerPrepareCreateValuationAmountAndRatingCmd extends AbstractCommand implements
ICommonEventConstant{

	private IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy;

	public IValuationAmountAndRatingProxyManager getValuationAmountAndRatingProxy() {
		return valuationAmountAndRatingProxy;
	}

	public void setValuationAmountAndRatingProxy(IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy) {
		this.valuationAmountAndRatingProxy = valuationAmountAndRatingProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateValuationAmountAndRatingCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
			{"IValuationAmountAndRatingTrxValue", "com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue", SERVICE_SCOPE},
			{ "riskGrade", "java.util.Set", SERVICE_SCOPE }
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue = new OBValuationAmountAndRatingTrxValue();
		Set<String> riskGrade=new HashSet<String>();
		ValuationAmountAndRatingDaoImpl daoValuation = new ValuationAmountAndRatingDaoImpl();
		riskGrade = daoValuation.getRiskGrade();
		resultMap.put("riskGrade", riskGrade);
		resultMap.put("IValuationAmountAndRatingTrxValue", valuationAmountAndRatingTrxValue);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
}
