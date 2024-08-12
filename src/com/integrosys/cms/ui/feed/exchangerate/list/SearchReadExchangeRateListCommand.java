package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/05 10:12:13 $
 *        Tag: $Name:  $
 */

/**
 * This class implements command
 */
public class SearchReadExchangeRateListCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "currencyCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "currencyIsoCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "statusSearch", "java.lang.String", REQUEST_SCOPE },
		});
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
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, // Produce the offset.
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
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
		// DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IForexFeedGroupTrxValue trxValue = null;

		try {

			IForexFeedProxy feedProxy = getForexFeedProxy();
			trxValue = feedProxy.getForexFeedGroup();

			DefaultLogger.debug(this, "after getting forex feed group from proxy.");

			String event = (String) map.get("event");
			String currencyCodeSearch = (String) map.get("currencyCodeSearch");
			String currencyIsoCodeSearch = (String) map.get("currencyIsoCodeSearch");
			String statusSearch = (String) map.get("statusSearch");
			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingForexFeedGroup() == null) {
				trxValue.setStagingForexFeedGroup((IForexFeedGroup) CommonUtil.deepClone(trxValue.getForexFeedGroup()));
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND)
					|| ExchangeRateListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingForexFeedGroup((IForexFeedGroup) CommonUtil.deepClone(trxValue.getForexFeedGroup()));
			}

			// Sort the staging entries.
			IForexFeedEntry[] entriesArr = trxValue.getStagingForexFeedGroup().getFeedEntries();

			if (trxValue.getForexFeedGroup().getFeedEntries() == null) {
				trxValue.getForexFeedGroup().setFeedEntries(new IForexFeedEntry[0]);
			}

			// Sort the array.
			if (entriesArr == null) {
				entriesArr = new IForexFeedEntry[0];
				trxValue.getStagingForexFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IForexFeedEntry entry1 = (IForexFeedEntry) a;
						IForexFeedEntry entry2 = (IForexFeedEntry) b;
						if (entry1.getBuyCurrency() == null) {
							entry1.setBuyCurrency("");
						}
						if (entry2.getBuyCurrency() == null) {
							entry2.setBuyCurrency("");
						}
						return entry1.getBuyCurrency().compareTo(entry2.getBuyCurrency());
					}
				});
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("forexFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(ExchangeRateListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
