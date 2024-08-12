package com.integrosys.cms.ui.feed.stock.list;

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
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.OBStockFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * This class implements command
 */
public class ReadStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ StockListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", FORM_SCOPE },
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
		{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.util.Integer", SERVICE_SCOPE },
				{ StockListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", FORM_SCOPE } });
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
		// DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IStockFeedGroupTrxValue trxValue = null;

		IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get(StockListForm.MAPPER);
		IStockFeedGroup group = value.getStockFeedGroup();
		String trxId = value.getTransactionID();
		String event = (String) map.get("event");

		try {
			DefaultLogger.debug(this, "the subtype = " + group.getSubType());

			DefaultLogger.debug(this, "the trx id = " + trxId);

			IStockFeedProxy feedProxy = getStockFeedProxy();

			if ((trxId == null) || trxId.equals("")) {
				DefaultLogger.debug(this, "getting by subtype.");
				trxValue = feedProxy.getStockFeedGroup(group.getSubType(), group.getStockType());
			}
			else {
				DefaultLogger.debug(this, "getting by trx id.");
				trxValue = feedProxy.getStockFeedGroupByTrxID(Long.parseLong(trxId));
			}

			DefaultLogger.debug(this, "the subtype = " + group.getSubType());
			DefaultLogger.debug(this, "after getting stock feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingStockFeedGroup() == null) {
				trxValue.setStagingStockFeedGroup((IStockFeedGroup) CommonUtil.deepClone(trxValue.getStockFeedGroup()));
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND) || StockListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingStockFeedGroup((IStockFeedGroup) CommonUtil.deepClone(trxValue.getStockFeedGroup()));
			}

			// Sort the staging entries.
			IStockFeedEntry[] entriesArr = trxValue.getStagingStockFeedGroup().getFeedEntries();

			if (trxValue.getStockFeedGroup().getFeedEntries() == null) {
				trxValue.getStockFeedGroup().setFeedEntries(new IStockFeedEntry[0]);
			}

			if (entriesArr == null) {
				entriesArr = new IStockFeedEntry[0];
				trxValue.getStagingStockFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
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
			}
		}
		catch (StockFeedGroupException e) {
			if (IStockFeedProxy.NO_FEED_GROUP.equals(e.getErrorCode())) {
				DefaultLogger.error(this, "no feed group found.");
				if (trxValue == null) {
					trxValue = new OBStockFeedGroupTrxValue();
				}
				exceptionMap.put("subType", new ActionMessage(FeedConstants.INFO_MISSING_SETUP_DATA));
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

		resultMap.put("stockFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(StockListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
