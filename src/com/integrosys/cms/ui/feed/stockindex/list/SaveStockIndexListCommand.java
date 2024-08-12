/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stockindex/list/SaveStockIndexListCommand.java,v 1.14 2005/08/30 09:52:42 hshii Exp $
 */

package com.integrosys.cms.ui.feed.stockindex.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexReplicationUtils;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.stockindex.StockIndexCommand;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2005/08/30 09:52:42 $ Tag: $Name: $
 */
public class SaveStockIndexListCommand extends StockIndexCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ StockIndexListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "stockIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "stockIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				// list.
				{ StockIndexListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue",
						FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue",
						REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(StockIndexListForm.MAPPER);

			// The below offset is the first record of the current range to be
			// saved.

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IStockIndexFeedGroup inputGroup = (IStockIndexFeedGroup) inputList.get(1);
			IStockIndexFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IStockIndexFeedGroupTrxValue value = (IStockIndexFeedGroupTrxValue) map.get("stockIndexFeedGroupTrxValue");
			IStockIndexFeedGroup stagingFeedGroup = value.getStagingStockIndexFeedGroup();

			IStockIndexFeedGroup replicatedStagingFeedGroup = StockIndexReplicationUtils
					.replicateStockIndexFeedGroupForCreateStagingCopy(stagingFeedGroup);

			IStockIndexFeedEntry[] entriesArr = replicatedStagingFeedGroup.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (entriesArr[j].getName().equals(inputEntriesArr[i].getName())) {
						entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
						entriesArr[j].setLastUpdatedDate(new Date());
					}
				}
			}

			replicatedStagingFeedGroup.setFeedEntries(entriesArr);
			value.setStagingStockIndexFeedGroup(replicatedStagingFeedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IStockIndexFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getStockIndexFeedProxy().makerUpdateRejectedStockIndexFeedGroup(trxContext, value);
			}
			else {
				resultValue = getStockIndexFeedProxy().makerUpdateStockIndexFeedGroup(trxContext, value,
						value.getStagingStockIndexFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingStockIndexFeedGroup().getFeedEntries();

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IStockIndexFeedEntry entry1 = (IStockIndexFeedEntry) a;
						IStockIndexFeedEntry entry2 = (IStockIndexFeedEntry) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
						// HashMap map =
						// CommonDataSingleton.getCodeCategoryValueLabelMap(
						// FeedConstants.CODE_CATEGORY_RIC);
						// return ((String)map.get(entry1.getRic())).compareTo(
						// (String)map.get(entry2.getRic()));
					}
				});
			}
			resultValue.getStagingStockIndexFeedGroup().setFeedEntries(entriesArr);

			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = StockIndexListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("stockIndexFeedGroupTrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(StockIndexListForm.MAPPER, resultValue);

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
