package com.integrosys.cms.ui.feed.stockindex.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.stockindex.StockIndexCommand;

/**
 * This class implements command
 */
public class SaveStockIndexItemCommand extends StockIndexCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ StockIndexItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry",
						FORM_SCOPE },
				{ "stockIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	// /**
	// * Defines a two dimensional array with the result list to be
	// * expected as a result from the doExecute method using a HashMap
	// * syntax for the array is (HashMapkey,classname,scope)
	// * The scope may be request,form or service
	// *
	// * @return the two dimensional String array
	// */
	// public String[][] getResultDescriptor() {
	// return (new String[][]{{"myOutput", "MyType", REQUEST_SCOPE}});
	// }

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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IStockIndexFeedEntry entry = (IStockIndexFeedEntry) map.get(StockIndexItemForm.MAPPER);

			IStockIndexFeedGroupTrxValue value = (IStockIndexFeedGroupTrxValue) map.get("stockIndexFeedGroupTrxValue");
			IStockIndexFeedGroup group = value.getStagingStockIndexFeedGroup();
			IStockIndexFeedEntry[] entriesArr = group.getFeedEntries();

			// Validation for ric starts.

			String ric = entry.getRic();

			// Validate that the ric code must not be one of the existing ones
			// under the staging group.
			if (entriesArr != null) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (entriesArr[i].getRic().equals(ric)) {
						exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "index value"));
						break;
					}
				}
			}
			else {
				entriesArr = new IStockIndexFeedEntry[0];
			}

			if (exceptionMap.isEmpty()) {
				// Validate that the ric must not be currently existing in the
				// price feed table.
				if (getStockIndexFeedProxy().getStockIndexFeedEntryByRic(ric) != null) {
					exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
				}
			}

			// Validation for ric ends.

			String name = entry.getName();

			// Validate that the name must not be one of the existing ones
			// under the staging group.
			if (entriesArr != null) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (entriesArr[i].getName().toUpperCase().equals(name.toUpperCase())) {
						exceptionMap.put("name", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "index name"));
						break;
					}
				}
			}

			if (exceptionMap.isEmpty()) {
				// Only proceed if there are no errors.

				// entry.setLastUpdatedDate(new Date());

				DefaultLogger.debug(this, "group's subtype = country code = " + group.getSubType());

				entry.setCountryCode(group.getSubType());
				entry.setStockIndexFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setStockIndexFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);

				// String currencyCode = entry.getBuyCurrency();
				// double unitPrice = entry.getBuyRate();
				//
				// IStockIndexFeedEntry newEntry = new OBStockIndexFeedEntry();
				//newEntry.setStockIndexFeedEntryID(com.integrosys.cms.app.common
				// .constant.ICMSConstant.LONG_INVALID_VALUE);
				// newEntry.setBuyCurrency(currencyCode);
				// // @todo what is the selling currency ?
				// newEntry.setBuyRate(unitPrice);

				// Add it as the last item of the array.
				IStockIndexFeedEntry[] newEntriesArr = new IStockIndexFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Resort the array.
				Arrays.sort(newEntriesArr, new Comparator() {
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

				group.setFeedEntries(newEntriesArr);

				value.setStagingStockIndexFeedGroup(group);
			}
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
