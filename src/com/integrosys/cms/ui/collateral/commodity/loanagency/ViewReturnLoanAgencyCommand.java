/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/ViewReturnLoanAgencyCommand.java,v 1.8 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
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
public class ViewReturnLoanAgencyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE }, { "serviceSecID", "java.lang.String", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "loanAgencyObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE },
				{ "actualLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE },
				{ "actualSecID", "java.lang.String", REQUEST_SCOPE },
				{ "stageSecID", "java.lang.String", REQUEST_SCOPE }, });
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
		result.put("from_page", from_event);
		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		String collateralID = (String) map.get("serviceSecID");
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<< HSHII collateralID: " + collateralID);
		String securityID = getSecurityIDByCollateralID((ICommodityCollateral[]) trxValueMap.get("actual"),
				collateralID);

		ILoanAgency loanAgencyObj = (ILoanAgency) map.get("serviceLoanAgency");
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<<<<<" + loanAgencyObj);

		if ((from_event != null) && from_event.equals(LoanAgencyAction.EVENT_PROCESS)) {
			ILoanAgency actualObj = null;
			ILoanAgency stageObj = null;
			String actualSecID = null;
			String stageSecID = null;

			Object[] actual = getItem((ICommodityCollateral[]) trxValueMap.get("actual"), loanAgencyObj.getCommonRef());
			if (actual != null) {
				actualObj = (ILoanAgency) actual[0];
				actualSecID = (String) actual[1];
			}

			Object[] staging = getItem((ICommodityCollateral[]) trxValueMap.get("staging"), loanAgencyObj
					.getCommonRef());
			if (staging != null) {
				stageObj = (ILoanAgency) staging[0];
				stageSecID = (String) staging[1];
			}
			result.put("actualLoanAgency", actualObj);
			result.put("stageLoanAgency", stageObj);
			result.put("actualSecID", actualSecID);
			result.put("stageSecID", stageSecID);
		}

		HashMap loanAgencyMap = new HashMap();
		loanAgencyMap.put("obj", loanAgencyObj);
		loanAgencyMap.put("securityID", securityID);
		result.put("loanAgencyObj", loanAgencyMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private String getSecurityIDByCollateralID(ICommodityCollateral[] col, String colID) {
		String securityID = null;

		if ((colID != null) && (colID.length() > 0)) {
			long collateralID = Long.parseLong(colID);
			if (col != null) {
				boolean found = false;
				for (int i = 0; !found && (i < col.length); i++) {
					if (col[i].getCollateralID() == collateralID) {
						found = true;
						securityID = String.valueOf(col[i].getSCISecurityID());
					}
				}
			}
		}
		return securityID;
	}

	private Object[] getItem(ICommodityCollateral col[], long commonRef) {
		Object[] item = null;
		if (col == null) {
			return item;
		}
		item = new Object[2];
		for (int i = 0; i < col.length; i++) {
			if (col[i] != null) {
				ILoanAgency[] temp = col[i].getLoans();
				if (temp != null) {
					for (int j = 0; j < temp.length; j++) {
						if (temp[j].getCommonRef() == commonRef) {
							item[0] = temp[j];
							item[1] = String.valueOf(col[i].getSCISecurityID());
						}
						else {
							continue;
						}
					}
				}
			}
		}
		return item;
	}
}
