/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/ProcessingLoanAgencyCommand.java,v 1.8 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */
public class ProcessingLoanAgencyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "loanAgencyObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", FORM_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE }, });
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

		String from_event = (String) map.get("from_page");
		if (from_event.equals(LoanAgencyAction.EVENT_PREPARE_UPDATE)) {
			ILoanAgency loanAgencyObj = (ILoanAgency) map.get("loanAgencyObj");
			result.put("serviceLoanAgency", loanAgencyObj);
			String securityID = (String) map.get("securityID");
			HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
			String collateralID = getCollateralIDBySecurityID((ICommodityCollateral[]) trxValueMap.get("actual"),
					securityID);
			result.put("serviceSecID", collateralID);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private String getCollateralIDBySecurityID(ICommodityCollateral[] col, String securityID) {
		String collateralID = null;
		if ((securityID != null) && (securityID.length() > 0)) {
			// long secID = Long.parseLong(securityID);
			if (col != null) {
				boolean found = false;
				for (int i = 0; !found && (i < col.length); i++) {
					if (col[i].getSCISecurityID().equals(securityID)) {
						collateralID = String.valueOf(col[i].getCollateralID());
						found = true;
					}
				}
			}
		}
		return collateralID;
	}
}
