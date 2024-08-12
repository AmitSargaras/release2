/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/PrepareCommodityMainCommand.java,v 1.6 2005/07/18 08:11:21 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/18 08:11:21 $ Tag: $Name: $
 */

public class PrepareCommodityMainCreateCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				// {"secLimitMap", "java.util.HashMap", REQUEST_SCOPE},
				{ "secApprovedCommMap", "java.util.HashMap", REQUEST_SCOPE },
				// {"secContractMap", "java.util.HashMap", REQUEST_SCOPE},
				{ "secHedgeContractMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "secLoanAgencyMap", "java.util.HashMap", REQUEST_SCOPE }, });
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

		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		String event = (String) map.get("event");
		if (event.equals(CommodityMainAction.EVENT_PROCESS) || event.equals(CommodityMainAction.EVENT_PROCESS_RETURN)
				|| event.equals(CommodityMainAction.EVENT_PROCESS_VIEW_ERROR)) {
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");

			HashMap secApprovedCommMap = new HashMap();
			HashMap secHedgeContractMap = new HashMap();
			HashMap secLoanAgencyMap = new HashMap();

			if (col != null) {
				for (int i = 0; i < col.length; i++) {
					String securityID = String.valueOf(col[i].getSCISecurityID());

					IApprovedCommodityType[] appCommType = col[i].getApprovedCommodityTypes();
					if (appCommType != null) {
						for (int j = 0; j < appCommType.length; j++) {
							secApprovedCommMap.put(String.valueOf(appCommType[j].getCommonRef()), securityID);
						}
					}

					IHedgingContractInfo[] hedgeContract = col[i].getHedgingContractInfos();
					if (hedgeContract != null) {
						for (int j = 0; j < hedgeContract.length; j++) {
							secHedgeContractMap.put(String.valueOf(hedgeContract[j].getCommonRef()), securityID);
						}
					}
					ILoanAgency[] loanAgency = col[i].getLoans();
					if (loanAgency != null) {
						for (int j = 0; j < loanAgency.length; j++) {
							secLoanAgencyMap.put(String.valueOf(loanAgency[j].getCommonRef()), securityID);
						}
					}
				}
			}
			col = (ICommodityCollateral[]) trxValueMap.get("actual");
			if (col != null) {
				for (int i = 0; i < col.length; i++) {
					String securityID = String.valueOf(col[i].getSCISecurityID());

					IApprovedCommodityType[] appCommType = col[i].getApprovedCommodityTypes();
					if (appCommType != null) {
						for (int j = 0; j < appCommType.length; j++) {
							secApprovedCommMap.put(String.valueOf(appCommType[j].getCommonRef()), securityID);
						}
					}

					IHedgingContractInfo[] hedgeContract = col[i].getHedgingContractInfos();
					if (hedgeContract != null) {
						for (int j = 0; j < hedgeContract.length; j++) {
							secHedgeContractMap.put(String.valueOf(hedgeContract[j].getCommonRef()), securityID);
						}
					}
					ILoanAgency[] loanAgency = col[i].getLoans();
					if (loanAgency != null) {
						for (int j = 0; j < loanAgency.length; j++) {
							secLoanAgencyMap.put(String.valueOf(loanAgency[j].getCommonRef()), securityID);
						}
					}
				}
			}

			result.put("secApprovedCommMap", secApprovedCommMap);
			result.put("secHedgeContractMap", secHedgeContractMap);
			result.put("secLoanAgencyMap", secLoanAgencyMap);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
