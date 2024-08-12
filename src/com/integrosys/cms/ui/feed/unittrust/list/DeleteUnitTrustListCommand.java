package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * This class implements command
 */
public class DeleteUnitTrustListCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				// Consumes the offset, length, feed group OB, chkDeletes in the
				// form of a List.
				{ UnitTrustListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
				// the
				// session
				// scoped
				// trx
				// value.
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
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
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ UnitTrustListForm.MAPPER, "com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue",
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
			List inputList = (List) map.get(UnitTrustListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();
			IUnitTrustFeedGroup inputFeedGroup = (IUnitTrustFeedGroup) inputList.get(0);
			IUnitTrustFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();
			String[] chkDeletesArr = (String[]) inputList.get(1);

			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();

			DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (entriesArr[j].getName().equals(inputEntriesArr[i].getName())) {
						entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
					}
				}
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
				entriesArr[indexDeletesArr[i]] = null;
			}

			// Pack the array of entries, discarding null references.
			IUnitTrustFeedEntry[] newEntriesArr = new IUnitTrustFeedEntry[entriesArr.length - indexDeletesArr.length];
			counter = 0; // reuse
			// Copy only non-null references.
			for (int i = 0; i < entriesArr.length; i++) {
				if (entriesArr[i] != null) {
					newEntriesArr[counter++] = entriesArr[i];
				}
			}

			DefaultLogger.debug(this, "new number of entries = " + newEntriesArr.length);

			group.setFeedEntries(newEntriesArr);
			value.setStagingUnitTrustFeedGroup(group);

			offset = UnitTrustListMapper.adjustOffset(offset, length, newEntriesArr.length);

			resultMap.put("unitTrustFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(offset));
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
