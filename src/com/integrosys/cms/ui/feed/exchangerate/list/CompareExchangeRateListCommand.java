/*
 * Copyright Integro Technologies Pte Ltd
 * $header$
 */
package com.integrosys.cms.ui.feed.exchangerate.list;

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
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * @author $Author: jzhai $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/10/31 09:42:28 $ Tag: $Name: $
 */
public class CompareExchangeRateListCommand extends ExchangeRateCommand {

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

		IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		IForexFeedGroup actualFeedGroup = value.getForexFeedGroup();
		IForexFeedGroup stagingFeedGroup = value.getStagingForexFeedGroup();

		/*if ((actualFeedGroup != null) && (actualFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "actual:");
			for (int i = 0; i < actualFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getBuyRate()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getForexFeedEntryRef()));
			}
		}
		if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "staging:");
			for (int i = 0; i < stagingFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getBuyRate()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getForexFeedEntryRef()));
			}
		}*/
		try {
			IForexFeedEntry[] e1 = stagingFeedGroup.getFeedEntries();
			IForexFeedEntry[] e2 = actualFeedGroup.getFeedEntries();

			if (e1.length != 0) {
				Arrays.sort(e1, new Comparator() {
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

			if (e2.length != 0) {
				Arrays.sort(e2, new Comparator() {
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
			List compareResultsList = CompareOBUtil.compOBArray(e1, e2);

			offset = ExchangeRateListMapper.adjustOffset(offset, length, compareResultsList.size());

			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("forexFeedGroupTrxValue", value);
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
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
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
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}
}
