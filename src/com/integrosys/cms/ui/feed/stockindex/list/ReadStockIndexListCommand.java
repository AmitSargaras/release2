package com.integrosys.cms.ui.feed.stockindex.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedGroupException;
import com.integrosys.cms.app.feed.proxy.stockindex.IStockIndexFeedProxy;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stockindex.OBStockIndexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.stockindex.StockIndexCommand;

/**
 * This class implements command
 */
public class ReadStockIndexListCommand extends StockIndexCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ StockIndexListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue",
						FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
				{ "stockIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.util.Integer", SERVICE_SCOPE },
				{ StockIndexListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue",
						FORM_SCOPE } });
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IStockIndexFeedGroupTrxValue trxValue = null;

		IStockIndexFeedGroupTrxValue value = (IStockIndexFeedGroupTrxValue) map.get(StockIndexListForm.MAPPER);
		IStockIndexFeedGroup group = value.getStockIndexFeedGroup();
		String trxId = value.getTransactionID();
		String event = (String) map.get("event");
		try {
			DefaultLogger.debug(this, "the subtype = " + group.getSubType());

			DefaultLogger.debug(this, "the trx id = " + trxId);

			if ((trxId == null) || trxId.equals("")) {
				DefaultLogger.debug(this, "getting by subtype.");
				trxValue = getStockIndexFeedProxy().getStockIndexFeedGroup(group.getSubType());
			}
			else {
				DefaultLogger.debug(this, "getting by trx id.");
				trxValue = getStockIndexFeedProxy().getStockIndexFeedGroupByTrxID(Long.parseLong(trxId));
			}

			DefaultLogger.debug(this, "the subtype = " + group.getSubType());
			DefaultLogger.debug(this, "after getting stock index feed group from proxy.");

			if (trxValue.getStockIndexFeedGroup().getFeedEntries() == null) {
				trxValue.getStockIndexFeedGroup().setFeedEntries(new IStockIndexFeedEntry[0]);
			}

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingStockIndexFeedGroup() == null) {
				trxValue.setStagingStockIndexFeedGroup((IStockIndexFeedGroup) CommonUtil.deepClone(trxValue
						.getStockIndexFeedGroup()));
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND)
					|| StockIndexListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingStockIndexFeedGroup((IStockIndexFeedGroup) CommonUtil.deepClone(trxValue
						.getStockIndexFeedGroup()));
			}

			// Sort the staging entries.
			IStockIndexFeedEntry[] entriesArr = trxValue.getStagingStockIndexFeedGroup().getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IStockIndexFeedEntry[0];
				trxValue.getStagingStockIndexFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
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
		}
		catch (StockIndexFeedGroupException e) {
			if (e.getErrorCode().equals(IStockIndexFeedProxy.NO_FEED_GROUP)) {
				DefaultLogger.error(this, "no feed group found.");
				if (trxValue == null) {
					trxValue = new OBStockIndexFeedGroupTrxValue();
				}
				exceptionMap.put("countryCode", new ActionMessage(FeedConstants.INFO_MISSING_SETUP_DATA));
			}
			else {
				DefaultLogger.error(this, "Exception caught in doExecute()", e);
				exceptionMap.put("application.exception", e);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("stockIndexFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(StockIndexListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
