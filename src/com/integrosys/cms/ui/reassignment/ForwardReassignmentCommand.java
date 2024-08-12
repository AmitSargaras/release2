/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/ForwardReassignmentCommand.java,v 1.3 2004/10/11 06:52:58 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDealSearchResult;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/11 06:52:58 $ Tag: $Name: $
 */
public class ForwardReassignmentCommand extends AbstractCommand {
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "reassignmentSearchSummary", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "reassignmentType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String reassignmentType = (String) map.get("reassignmentType");
		SearchResult summaryObj = (SearchResult) map.get("reassignmentSearchSummary");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICMSTrxValue trxValue = null;
		if (summaryObj != null) {
			if (ReassignmentConstant.TASK_TYPE_DEAL.equals(reassignmentType)) {
				Vector resultList = (Vector) summaryObj.getResultList();
				OBCommodityDealSearchResult deal = (OBCommodityDealSearchResult) resultList.get(0);
				trxValue = deal.getTrxValue();
				trxValue = setToFAMInfo(trxValue);
				ctx = (OBTrxContext) trxValue.getTrxContext();
				try {
					trxValue = CommodityDealProxyFactory.getProxy().officerForwardCommodityDeal(ctx,
							(ICommodityDealTrxValue) trxValue);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
			else {
				ArrayList resultList = (ArrayList) summaryObj.getResultList();
				ICheckListSummary chklistSummary = (ICheckListSummary) resultList.get(0);
				trxValue = chklistSummary.getTrxValue();
				ctx = (OBTrxContext) trxValue.getTrxContext();
				trxValue = setToFAMInfo(trxValue);
				trxValue.setOpDesc(ICMSConstant.ACTION_BACKWARD);
				try {
					trxValue = CheckListProxyManagerFactory.getCheckListProxyManager().officeOperation(ctx,
							(ICheckListTrxValue) trxValue);
				}
				catch (Exception e) {
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}

		resultMap.put("request.ITrxValue", trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private ICMSTrxValue setToFAMInfo(ICMSTrxValue trxValue) {
		trxValue.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
		trxValue.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
		trxValue.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_FAM_USER);
		return trxValue;
	}
}
