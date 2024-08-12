/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/AddParticipantCommand.java,v 1.3 2004/07/26 13:30:10 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IParticipant;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBParticipant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/26 13:30:10 $ Tag: $Name: $
 */
public class AddParticipantCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "loanAgencyObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", FORM_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "loanAgencyObj", "java.util.HashMap", FORM_SCOPE },
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE }, { "serviceSecID", "java.lang.String", SERVICE_SCOPE }, });
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

		HashMap loanAgencyMap = new HashMap();

		String securityID = (String) map.get("securityID");
		loanAgencyMap.put("securityID", securityID);
		result.put("serviceSecID", securityID);

		OBLoanAgency loanObj = (OBLoanAgency) map.get("loanAgencyObj");

		exceptionMap = LoanAgencyCmdValidator.validateParticipant(loanObj, exceptionMap);
		if (exceptionMap.isEmpty()) {
			exceptionMap = LoanAgencyCmdValidator.validateParticipantAmt(loanObj, exceptionMap, true);
		}

		if (exceptionMap.isEmpty()) {
			IParticipant[] participangList = addParticipant(loanObj.getParticipants());
			loanObj.setParticipants(participangList);

			loanAgencyMap.put("obj", loanObj);
			result.put("loanAgencyObj", loanAgencyMap);
			result.put("serviceLoanAgency", loanObj);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static IParticipant[] addParticipant(IParticipant[] existingArray) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}
		IParticipant[] newArray = new IParticipant[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}

		OBParticipant newObj = new OBParticipant();
		newObj.setParticipantID(ICMSConstant.LONG_INVALID_VALUE);
		newArray[arrayLength] = newObj;

		return newArray;
	}
}
