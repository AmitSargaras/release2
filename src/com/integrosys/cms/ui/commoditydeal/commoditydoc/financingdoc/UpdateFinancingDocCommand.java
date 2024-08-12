/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/UpdateFinancingDocCommand.java,v 1.3 2005/10/24 07:13:45 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/24 07:13:45 $ Tag: $Name: $
 */

public class UpdateFinancingDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "financingDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc", FORM_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityDealTrxValue",
				"com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue", SERVICE_SCOPE }, });
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
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		IFinancingDoc finDocObj = (IFinancingDoc) map.get("financingDocObj");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();

		Date locDueDate = finDocObj.getLOCDueDate();
		Date dealMaturityDate = dealObj.getDealMaturityDate();
		Date extendedDealMaturityDate = dealObj.getExtendedDealMaturityDate();
		dealMaturityDate = (extendedDealMaturityDate != null) ? extendedDealMaturityDate : dealMaturityDate; // if
																												// there
																												// is
																												// extended
																												// deal
																												// maturity
																												// date
																												// then
																												// this
																												// should
																												// be
																												// used
																												// instead
																												// .

		if ((locDueDate != null) && (dealMaturityDate != null)) {
			if (locDueDate.after(dealMaturityDate)) { // do not use
														// "if(dateObj2.before(dateObj1))"
														// 'cos if its the same
														// date, it will return
														// false. It checks for
														// strictly before.
				exceptionMap.put("locDueDate", new ActionMessage("error.date.compareDate.cannotBelater",
						"Due Date to Receive Letter of Credit", "Deal Maturity Date"));
			}
		}

		if (exceptionMap.isEmpty()) {
			IFinancingDoc[] finDocArr = dealObj.getFinancingDocs();

			finDocArr[index] = finDocObj;
			dealObj.setFinancingDocs(finDocArr);
			trxValue.setStagingCommodityDeal(dealObj);

			result.put("commodityDealTrxValue", trxValue);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
