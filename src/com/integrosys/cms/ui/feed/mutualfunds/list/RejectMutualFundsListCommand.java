/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/RejectBondListCommand.java,v 1.7 2003/09/22 04:51:48 btchng Exp $
 */
package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;
import com.integrosys.cms.ui.feed.stock.list.StockListForm;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class RejectMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{"remarks","java.lang.String",REQUEST_SCOPE},
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			
			String remarks = (String)map.get("remarks");
			value.setRemarks(remarks);
			
			if(remarks == null || remarks.equals("")){
				exceptionMap.put("remarksError", new ActionMessage("error.remark.mandatory"));
				IMutualFundsFeedGroupTrxValue trxValue = null;
            	resultMap.put("IMutualFundsFeedGroupTrxValue", trxValue);
            	resultMap.put("request.ITrxValue", trxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			value = getMutualFundsFeedProxy().checkerRejectMutualFundsFeedGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
