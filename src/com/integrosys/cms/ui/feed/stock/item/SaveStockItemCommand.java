package com.integrosys.cms.ui.feed.stock.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * This class implements command
 */
public class SaveStockItemCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ StockItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", FORM_SCOPE },
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE },
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
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IStockFeedEntry entry = (IStockFeedEntry) map.get(StockItemForm.MAPPER);

			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");
			IStockFeedGroup group = value.getStagingStockFeedGroup();
			IStockFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IStockFeedEntry[0];
			}

			boolean isFound = false;
			IStockFeedProxy proxy = getStockFeedProxy();
			String ticker = entry.getTicker();
			
			boolean isExists = proxy.isExistScriptCode(entry.getScriptCode());
			
			
			if(isExists){
				exceptionMap.put("scriptCodeError", new ActionMessage("error.string.scripcode.exist"));
				IStockFeedGroupTrxValue trxValue = null;
				resultMap.put("request.ITrxValue", trxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			if ((ticker != null) && !"".equals(ticker.trim())) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (ticker.equals(entriesArr[i].getTicker())) {
						exceptionMap.put("ticker", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "Stock Code/Ticker"));
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					// must not be currently existing in DB.
					if (proxy.getStockFeedEntry(ticker, 1) != null) {
						exceptionMap.put("ticker",
								new ActionMessage(FeedConstants.ERROR_DUPLICATE, "Stock Code/Ticker"));
					}
				}
			}

			String isinCode = entry.getIsinCode();
			isFound = false;
			if ((isinCode != null) && !"".equals(isinCode.trim())) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (isinCode.equals(entriesArr[i].getIsinCode())) {
						exceptionMap.put("isinCode", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "ISIN Code"));
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					// must not be currently existing in DB.
					if (proxy.getStockFeedEntry(isinCode, 2) != null) {
						exceptionMap.put("isinCode", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "ISIN Code"));
					}
				}

			}

			// Validate that the ric must not be existing currently in the
			// staging.
			String ric = entry.getRic();
			isFound = false;
			if ((ric != null) && !"".equals(ric.trim())) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (ric.equals(entriesArr[i].getRic())) {
						exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					// must not be currently existing in DB.
					if (proxy.getStockFeedEntry(ric, 3) != null) {
						exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
					}
				}
			}

			if (exceptionMap.isEmpty()) {
				// Only proceed if there are no errors.

				// entry.setLastUpdatedDate(new Date());

				DefaultLogger.debug(this, "group's subtype = stock exchange code = " + group.getSubType());

				entry.setExchange(group.getSubType());
				entry.setStockFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setStockFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setType(group.getStockType());

				// String currencyCode = entry.getBuyCurrency();
				// double unitPrice = entry.getBuyRate();
				//
				// IStockFeedEntry newEntry = new OBStockFeedEntry();
				// newEntry.setStockFeedEntryID(com.integrosys.cms.app.common.
				// constant.ICMSConstant.LONG_INVALID_VALUE);
				// newEntry.setBuyCurrency(currencyCode);
				// // @todo what is the selling currency ?
				// newEntry.setBuyRate(unitPrice);

				// Add it as the last item of the array.
				IStockFeedEntry[] newEntriesArr = new IStockFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Resort the array.
				Arrays.sort(newEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IStockFeedEntry entry1 = (IStockFeedEntry) a;
						IStockFeedEntry entry2 = (IStockFeedEntry) b;
						if (entry1.getScriptName() == null) {
							entry1.setScriptName("");
						}
						if (entry2.getScriptName() == null) {
							entry2.setScriptName("");
						}
						return entry1.getScriptName().compareTo(entry2.getScriptName());
					}
				});

				group.setFeedEntries(newEntriesArr);

				value.setStagingStockFeedGroup(group);
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
