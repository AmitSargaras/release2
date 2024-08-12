package com.integrosys.cms.ui.feed.gold.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;
import com.integrosys.cms.ui.feed.exchangerate.list.ExchangeRateListForm;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class PaginateGoldListCommand extends GoldCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save
				// and
				// list.
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
				// the
				// update of
				// form. For
				// save and
				// list.
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue",
						FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of target offset and
				// feed group OB.
				{ ExchangeRateListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
				// the
				// current
				// feed
				// entries
				// to
				// be
				// saved
				// as
				// a
				// whole
				// .
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(ExchangeRateListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IGoldFeedGroup inputGroup = (IGoldFeedGroup) inputList.get(1);
			IGoldFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which
			// is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");
			IGoldFeedGroup group = value.getStagingGoldFeedGroup();
			IGoldFeedEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setUnitPrice(inputEntriesArr[i].getUnitPrice());
				// entriesArr[offset + i].setEffectiveDate(new Date());
			}

			for (int i = 0; i < entriesArr.length; i++) {
				DefaultLogger.debug(this, "before saving, entriesArr[" + i + "] = " + entriesArr[i].getCurrencyCode());
			}

			// Sort the array.

			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IGoldFeedEntry entry1 = (IGoldFeedEntry) a;
						IGoldFeedEntry entry2 = (IGoldFeedEntry) b;
						if (entry1.getCurrencyCode() == null) {
							entry1.setCurrencyCode("");
						}
						if (entry2.getCurrencyCode() == null) {
							entry2.setCurrencyCode("");
						}
						return entry1.getCurrencyCode().compareTo(entry2.getCurrencyCode());
					}
				});
			}

			group.setFeedEntries(entriesArr);
			value.setStagingGoldFeedGroup(group);

			targetOffset = GoldListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("request.ITrxValue", value);
			resultMap.put("goldFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(GoldListForm.MAPPER, value);

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
