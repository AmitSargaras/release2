/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/PaginateExchangeRateListCommand.java,v 1.2 2005/08/30 09:50:38 hshii Exp $
 */
package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/30 09:50:38 $ Tag: $Name: $
 */
public class PaginateExchangeRateListCommand extends ExchangeRateCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue", REQUEST_SCOPE } };
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
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "targetOffset", "java.lang.String", REQUEST_SCOPE }, 
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
			String atargetOffset = (String) map.get("targetOffset");
			int targetOffset =0;
			if(inputList!=null){
			targetOffset = Integer.parseInt((String) inputList.get(0));
			}else{
				targetOffset = Integer.parseInt(atargetOffset);
			}
			IForexFeedGroup inputGroup = (IForexFeedGroup) inputList.get(1);
			IForexFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");
			IForexFeedGroup group = value.getStagingForexFeedGroup();
			IForexFeedEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setBuyRate(inputEntriesArr[i].getBuyRate());
				entriesArr[offset + i].setSellRate(inputEntriesArr[i].getSellRate());
				// entriesArr[offset + i].setEffectiveDate(new Date());
			}

			for (int i = 0; i < entriesArr.length; i++) {
				DefaultLogger.debug(this, "before saving, entriesArr[" + i + "] = " + entriesArr[i].getBuyCurrency());
			}

			// Sort the array.

			if ((entriesArr != null) && (entriesArr.length != 0)) {
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

			group.setFeedEntries(entriesArr);
			value.setStagingForexFeedGroup(group);

			targetOffset = ExchangeRateListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("request.ITrxValue", value);
			resultMap.put("forexFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
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
