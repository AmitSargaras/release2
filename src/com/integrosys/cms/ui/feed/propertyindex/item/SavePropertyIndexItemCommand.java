package com.integrosys.cms.ui.feed.propertyindex.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;

/**
 * This class implements command
 */
public class SavePropertyIndexItemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ PropertyIndexItemForm.MAPPER,
						"com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry", FORM_SCOPE },
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
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

			IPropertyIndexFeedEntry entry = (IPropertyIndexFeedEntry) map.get(PropertyIndexItemForm.MAPPER);

			IPropertyIndexFeedGroupTrxValue value = (IPropertyIndexFeedGroupTrxValue) map
					.get("propertyIndexFeedGroupTrxValue");
			IPropertyIndexFeedGroup group = value.getStagingPropertyIndexFeedGroup();
			IPropertyIndexFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {

			}
			else {
				entriesArr = new IPropertyIndexFeedEntry[0];
			}

			if (exceptionMap.isEmpty()) {
				// Only proceed if there are no errors.

				// entry.setLastUpdatedDate(new Date());

				DefaultLogger.debug(this, "group's subtype = country code = " + group.getSubType());

				entry.setCountryCode(group.getSubType());
				entry
						.setPropertyIndexFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry
						.setPropertyIndexFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);

				// String currencyCode = entry.getBuyCurrency();
				// double unitPrice = entry.getBuyRate();
				//
				// IPropertyIndexFeedEntry newEntry = new
				// OBPropertyIndexFeedEntry();
				// newEntry.setPropertyIndexFeedEntryID(com.integrosys.cms.app.
				// common.constant.ICMSConstant.LONG_INVALID_VALUE);
				// newEntry.setBuyCurrency(currencyCode);
				// // @todo what is the selling currency ?
				// newEntry.setBuyRate(unitPrice);

				// Add it as the last item of the array.
				IPropertyIndexFeedEntry[] newEntriesArr = new IPropertyIndexFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Resort the array.
				Arrays.sort(newEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IPropertyIndexFeedEntry entry1 = (IPropertyIndexFeedEntry) a;
						IPropertyIndexFeedEntry entry2 = (IPropertyIndexFeedEntry) b;
						if (entry1.getType() == null) {
							entry1.setType("");
						}
						if (entry2.getType() == null) {
							entry2.setType("");
						}
						return entry1.getType().compareTo(entry2.getType());
					}
				});

				group.setFeedEntries(newEntriesArr);

				value.setStagingPropertyIndexFeedGroup(group);
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
