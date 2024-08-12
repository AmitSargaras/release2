package com.integrosys.cms.ui.feed.exchangerate.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * This class implements command
 */
public class SaveExchangeRateItemCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ ExchangeRateItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry", FORM_SCOPE },
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IForexFeedEntry entry = (IForexFeedEntry) map.get(ExchangeRateItemForm.MAPPER);

			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");
			IForexFeedGroup group = value.getStagingForexFeedGroup();
			IForexFeedEntry[] entriesArr = group.getFeedEntries();

			// Validate that the (buy currency, sell currency) pair cannot be
			// the same as one of the existing pairs.
			if (entriesArr != null) {
				String buyCurrencyCode = null;
				String sellCurrencyCode = null;
				String currencyISOCode = null;
				String restrictionType = null;
				String currencyDesc = null;
				for (int i = 0; i < entriesArr.length; i++) {
					buyCurrencyCode = entriesArr[i].getBuyCurrency();
					sellCurrencyCode = entriesArr[i].getSellCurrency();
					restrictionType = entriesArr[i].getRestrictionType();
					currencyDesc = entriesArr[i].getCurrencyDescription();
					DefaultLogger.debug(this, "buy = " + buyCurrencyCode + " sell = " + sellCurrencyCode);
					if ((buyCurrencyCode != null) && buyCurrencyCode.trim().equals(entry.getBuyCurrency())
							&& (sellCurrencyCode != null)
							&& sellCurrencyCode.trim().equals(CommonUtil.getBaseExchangeCurrency())) {
						exceptionMap.put("currencyCode", new ActionMessage(FeedConstants.ERROR_DUPLICATE,
								"currency code"));
						break;
					}
					
					/* Code for Duplicate currency Description added by Sandeep Shinde ( following If Statement )*/
					
					if ( (currencyDesc != null) && currencyDesc.trim().equalsIgnoreCase(entry.getCurrencyDescription()) ) {
						exceptionMap.put("currencyDescriptionError", new ActionMessage(FeedConstants.ERROR_DUPLICATE,
								"currency Description"));
						break;
					}
					
					if ((currencyISOCode != null) && currencyISOCode.trim().equals(entry.getCurrencyIsoCode())) {
						exceptionMap.put("currencyISOCode", new ActionMessage(FeedConstants.ERROR_DUPLICATE,
								"Currency ISO Code"));
						break;
					}					
				}
			}
			else {
				entriesArr = new IForexFeedEntry[0];
			}

			if (exceptionMap.isEmpty()) {
				// Only proceed if there are no errors.

				// entry.setEffectiveDate(new Date());
				entry.setForexFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setForexFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setSellCurrency(CommonUtil.getBaseExchangeCurrency());
				entry.setSellRate(entry.getBuyRate());
				entry.setBuyUnit(1);
				entry.setSellUnit(1);

				// Add it as the last item of the array.
				IForexFeedEntry[] newEntriesArr = new IForexFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Sort the array.
				Arrays.sort(newEntriesArr, new Comparator() {
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

				group.setFeedEntries(newEntriesArr);

				value.setStagingForexFeedGroup(group);
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
