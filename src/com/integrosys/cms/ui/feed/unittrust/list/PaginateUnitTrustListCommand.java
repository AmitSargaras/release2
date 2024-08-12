/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/unittrust/list/PaginateUnitTrustListCommand.java,v 1.2 2005/08/30 09:53:20 hshii Exp $
 */
package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/30 09:53:20 $ Tag: $Name: $
 */
public class PaginateUnitTrustListCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ UnitTrustListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
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
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ UnitTrustListForm.MAPPER, "com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue",
						FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue",
						REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(UnitTrustListForm.MAPPER);

			// The below offset is the first record of the current range to be
			// saved.

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IUnitTrustFeedGroup inputGroup = (IUnitTrustFeedGroup) inputList.get(1);
			IUnitTrustFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setUnitPrice(inputEntriesArr[i].getUnitPrice());
				// entriesArr[offset + i].setLastUpdatedDate(new Date());
			}

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IUnitTrustFeedEntry entry1 = (IUnitTrustFeedEntry) a;
						IUnitTrustFeedEntry entry2 = (IUnitTrustFeedEntry) b;
						if (entry1.getName() == null) {
							entry1.setName("");
						}
						if (entry2.getName() == null) {
							entry2.setName("");
						}
						return entry1.getName().compareTo(entry2.getName());
					}
				});
			}

			group.setFeedEntries(entriesArr);
			value.setStagingUnitTrustFeedGroup(group);

			targetOffset = UnitTrustListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("request.ITrxValue", value);
			resultMap.put("unitTrustFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(UnitTrustListForm.MAPPER, value);

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
