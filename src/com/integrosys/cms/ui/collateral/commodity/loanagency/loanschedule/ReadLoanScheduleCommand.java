/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/loanschedule/ReadLoanScheduleCommand.java,v 1.5 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule;

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
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */
public class ReadLoanScheduleCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE }, { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
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
				{ "loanScheduleObj", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", FORM_SCOPE },
				{ "stageLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE },
				{ "actualLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency", REQUEST_SCOPE }, });
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
		ILoanAgency loanAgencyObj = (ILoanAgency) map.get("serviceLoanAgency");

		result.put("from_page", from_event);
		result.put("loanScheduleObj", loanAgencyObj);

		if (from_event.equals(EVENT_PROCESS)) {
			HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("actual");
			ILoanAgency actualObj = getItem(col, loanAgencyObj.getCommonRef());

			col = (ICommodityCollateral[]) trxValueMap.get("staging");
			ILoanAgency stageObj = getItem(col, loanAgencyObj.getCommonRef());

			result.put("actualLoanAgency", actualObj);
			result.put("stageLoanAgency", stageObj);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ILoanAgency getItem(ICommodityCollateral col[], long commonRef) {
		ILoanAgency item = null;
		if (col == null) {
			return item;
		}
		for (int i = 0; i < col.length; i++) {
			if (col[i] != null) {
				ILoanAgency[] temp = col[i].getLoans();
				if (temp != null) {
					for (int j = 0; j < temp.length; j++) {
						if (temp[j].getCommonRef() == commonRef) {
							item = temp[j];
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
