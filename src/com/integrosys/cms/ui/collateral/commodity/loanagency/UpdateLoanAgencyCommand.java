/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/UpdateLoanAgencyCommand.java,v 1.7 2004/07/26 13:30:10 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/07/26 13:30:10 $ Tag: $Name: $
 */
public class UpdateLoanAgencyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "loanAgencyObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		int index = Integer.parseInt((String) map.get("indexID"));
		int secIndex = Integer.parseInt((String) map.get("secIndexID"));
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
		ILoanAgency loanAgencyObj = (ILoanAgency) map.get("loanAgencyObj");

		try {
			exceptionMap = LoanAgencyCmdValidator.validateInput(loanAgencyObj, exceptionMap);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		exceptionMap = LoanAgencyCmdValidator.validateGlobalAmount(loanAgencyObj, exceptionMap);
		/*
		 * if (loanAgencyObj.getIsEqualInstalments()) {
		 * ((OBLoanAgency)loanAgencyObj).setLoanSchedules(null); }
		 */
		if (exceptionMap.isEmpty()) {
			ILoanAgency[] loanAgencyList = col[secIndex].getLoans();
			loanAgencyList[index] = loanAgencyObj;
			((OBCommodityCollateral) col[secIndex]).setLoans(loanAgencyList);

			trxValueMap.put("staging", col);

			result.put("commodityMainTrxValue", trxValueMap);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
