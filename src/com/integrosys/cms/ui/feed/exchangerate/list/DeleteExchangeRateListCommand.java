package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * This class implements command
 */
public class DeleteExchangeRateListCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				// Consumes the feed group OB, chkDeletes in the form of a List.
				{ ExchangeRateListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
																				// the
																				// session
																				// scoped
																				// trx
																				// value
																				// .
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				// Produce the updated session-scoped trx value.
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			List inputList = (List) map.get(ExchangeRateListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			IForexFeedGroup inputFeedGroup = (IForexFeedGroup) inputList.get(0);
			IForexFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();
			String[] chkDeletesArr = (String[]) inputList.get(1);

			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");
			IForexFeedGroup group = value.getStagingForexFeedGroup();
			IForexFeedEntry[] entriesArr = group.getFeedEntries();

			DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setBuyRate(inputEntriesArr[i].getBuyRate());
			}

			// chkDeletesArr contains Strings which index into the entries
			// array of trx value, 0-based.

			int counter = 0;
			int[] indexDeletesArr = new int[chkDeletesArr.length];
			for (int i = 0; i < chkDeletesArr.length; i++) {
				indexDeletesArr[counter++] = Integer.parseInt(chkDeletesArr[i]);
			}

			DefaultLogger.debug(this, "number of entries to remove = " + chkDeletesArr.length);
			for (int i = 0; i < indexDeletesArr.length; i++) {
				DefaultLogger.debug(this, "must remove entry " + indexDeletesArr[i]);
			}

			// indexDeletesArr contains the indexes of entriesArr for entries
			// that are to be removed.
			// Null all the array element references for entries that are to be
			// removed.
			for (int i = 0; i < indexDeletesArr.length; i++) {
			// Commented By Abhijit R
			//	entriesArr[indexDeletesArr[i]] = null;
				if(entriesArr[indexDeletesArr[i]].getStatus().trim().equals("ENABLE")){
					entriesArr[indexDeletesArr[i]].setStatus("DISABLE");
				}else{
					entriesArr[indexDeletesArr[i]].setStatus("ENABLE");
				}
			}

			// Pack the array of entries, discarding null references.
			// Commented By Abhijit R
			//IForexFeedEntry[] newEntriesArr = new IForexFeedEntry[entriesArr.length - indexDeletesArr.length];
			
			IForexFeedEntry[] newEntriesArr = new IForexFeedEntry[entriesArr.length];
			counter = 0; // reuse
			// Copy only non-null references.
			for (int i = 0; i < entriesArr.length; i++) {
				if (entriesArr[i] != null) {
					newEntriesArr[counter++] = entriesArr[i];
				}
			}

			DefaultLogger.debug(this, "new number of entries = " + newEntriesArr.length);

			group.setFeedEntries(newEntriesArr);
			value.setStagingForexFeedGroup(group);

			offset = ExchangeRateListMapper.adjustOffset(offset, length, newEntriesArr.length);

			resultMap.put("forexFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(offset));
			resultMap.put(ExchangeRateListForm.MAPPER, value);

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
