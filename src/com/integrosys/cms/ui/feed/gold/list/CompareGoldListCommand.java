package com.integrosys.cms.ui.feed.gold.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class CompareGoldListCommand extends GoldCommand {

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

		IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		IGoldFeedGroup actualFeedGroup = value.getGoldFeedGroup();
		IGoldFeedGroup stagingFeedGroup = value.getStagingGoldFeedGroup();

		if ((actualFeedGroup != null) && (actualFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "actual:");
			for (int i = 0; i < actualFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "unit price " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getUnitPrice()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getGoldFeedEntryRef()));
			}
		}
		if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "staging:");
			for (int i = 0; i < stagingFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "unit price " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getUnitPrice()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getGoldFeedEntryRef()));
			}
		}
		try {
			IGoldFeedEntry[] staging = stagingFeedGroup.getFeedEntries();
			IGoldFeedEntry[] actual = actualFeedGroup.getFeedEntries();

			if (staging.length != 0) {
				Arrays.sort(staging, new Comparator() {
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

			if (actual.length != 0) {
				Arrays.sort(actual, new Comparator() {
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

			List compareResultsList = CompareOBUtil.compOBArray(staging, actual);

			offset = GoldListMapper.adjustOffset(offset, length, compareResultsList.size());

			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("goldFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(offset));

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
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
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {// Produce the comparision results list.
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
				// the
				// trx
				// value
				// nevertheless
				// .
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}
}
