/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/ApproveStockListCommand.java,v 1.9 2005/08/30 09:52:01 hshii Exp $
 */
package com.integrosys.cms.ui.feed.stock.list;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/30 09:52:01 $ Tag: $Name: $
 */
public class ApproveStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IStockFeedGroup group = value.getStockFeedGroup();
			IStockFeedGroup stageGroup = value.getStagingStockFeedGroup();
			IStockFeedEntry[] actualEntries = null;
			IStockFeedEntry[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingStockFeedGroup(stageGroup);

			value = getStockFeedProxy().checkerApproveStockFeedGroup(trxContext, value);

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

	private IStockFeedEntry[] updateLastUpdateDate(IStockFeedEntry[] actualEntries, IStockFeedEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getStockFeedEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IStockFeedEntry actual = (IStockFeedEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getStockFeedEntryRef()));
				if ((actual == null) || (actual.getUnitPrice() != stageEntries[i].getUnitPrice())
						|| isStringChange(actual.getBlackListed(), stageEntries[i].getBlackListed())
						|| isStringChange(actual.getSuspended(), stageEntries[i].getSuspended())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}

	private boolean isStringChange(String actualStr, String stageStr) {
		if (((actualStr == null) || (actualStr.trim().length() == 0)) && (stageStr != null)
				&& (stageStr.trim().length() > 0)) {
			return true;
		}
		if ((actualStr != null) && (actualStr.trim().length() > 0)
				&& ((stageStr == null) || (stageStr.trim().length() == 0))) {
			return true;
		}
		if ((actualStr != null) && (stageStr != null) && !actualStr.equals(stageStr)) {
			return true;
		}
		return false;
	}
}
