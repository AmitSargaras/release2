/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/propertyindex/list/PaginatePropertyIndexListCommand.java,v 1.2 2005/08/30 09:51:18 hshii Exp $
 */
package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/30 09:51:18 $ Tag: $Name: $
 */
public class PaginatePropertyIndexListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ PropertyIndexListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
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
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ PropertyIndexListForm.MAPPER,
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue",
						REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(PropertyIndexListForm.MAPPER);

			// The below offset is the first record of the current range to be
			// saved.

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IPropertyIndexFeedGroup inputGroup = (IPropertyIndexFeedGroup) inputList.get(1);
			IPropertyIndexFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IPropertyIndexFeedGroupTrxValue value = (IPropertyIndexFeedGroupTrxValue) map
					.get("propertyIndexFeedGroupTrxValue");
			IPropertyIndexFeedGroup group = value.getStagingPropertyIndexFeedGroup();
			IPropertyIndexFeedEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setUnitPrice(inputEntriesArr[i].getUnitPrice());
				// entriesArr[offset + i].setLastUpdatedDate(new Date());
			}

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
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
			}

			group.setFeedEntries(entriesArr);
			value.setStagingPropertyIndexFeedGroup(group);

			targetOffset = PropertyIndexListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("request.ITrxValue", value);
			resultMap.put("propertyIndexFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(PropertyIndexListForm.MAPPER, value);

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
