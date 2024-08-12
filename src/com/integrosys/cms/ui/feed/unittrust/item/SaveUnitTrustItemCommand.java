package com.integrosys.cms.ui.feed.unittrust.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.proxy.unittrust.IUnitTrustFeedProxy;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * This class implements command
 */
public class SaveUnitTrustItemCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ UnitTrustItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry", FORM_SCOPE },
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", SERVICE_SCOPE },
				{ "oldRIC", "java.lang.String", SERVICE_SCOPE },
				{ "editIndex", "java.lang.Integer", SERVICE_SCOPE }, 
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } 
		};
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

			IUnitTrustFeedEntry entry = (IUnitTrustFeedEntry) map.get(UnitTrustItemForm.MAPPER);
			String isEdit = (String) map.get("isEdit");
			if (isEdit==null || isEdit.equals("")) isEdit="false";
			String oldRIC = (String) map.get("oldRIC");
			Integer editIndexObj = (Integer) map.get("editIndex"); 
			int editIndex = -1;
			if (editIndexObj!=null) editIndex=(editIndexObj).intValue();

			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IUnitTrustFeedEntry[0];
			}

			// Validate that the ric must not be existing currently in the
			// staging.
			String ric = entry.getRic();
			if (!AbstractCommonMapper.isEmptyOrNull(ric)) {
				if (!isEdit.equals("true")) {
					for (int i = 0; i < entriesArr.length; i++) {
						if (entriesArr[i].getRic().equals(ric)) {
							exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
							break;
						}
					}
				} else {
					for (int i = 0; i < entriesArr.length; i++) {
						if (i!=editIndex && entriesArr[i].getRic().equals(ric)) {
							exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
							break;
						}
					}
				}
			}

			String name = entry.getName();
			if (!isEdit.equals("true")) {
				for (int i = 0; i < entriesArr.length; i++) {
					if (entriesArr[i].getRic().equals(name)) {
						exceptionMap.put("name", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "Unit Trust name"));
						break;
					}
				}
			} else {
				for (int i = 0; i < entriesArr.length; i++) {
					if (i!=editIndex && entriesArr[i].getRic().equals(name)) {
						exceptionMap.put("name", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "Unit Trust name"));
						break;
					}
				}
			}

			if (exceptionMap.isEmpty() && !ric.equals(oldRIC)) {
				// Validate that the ric must not be currently existing in the
				// price feed table.
				IUnitTrustFeedProxy proxy = getUnitTrustFeedProxy();
				if (proxy.getUnitTrustFeedEntryByRic(ric) != null && !AbstractCommonMapper.isEmptyOrNull(ric)) {
					exceptionMap.put("ric", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
				}
			}

			if (exceptionMap.isEmpty()) {

				DefaultLogger.debug(this, "group's subtype = country code = " + group.getSubType());

				entry.setCountryCode(group.getSubType());
				entry.setUnitTrustFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setUnitTrustFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setType(group.getStockType());

				IUnitTrustFeedEntry[] newEntriesArr;
				if (!isEdit.equals("true")) newEntriesArr = new IUnitTrustFeedEntry[entriesArr.length + 1];
				else newEntriesArr = group.getFeedEntries();
	
				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				if (!isEdit.equals("true")) newEntriesArr[newEntriesArr.length - 1] = entry;
				else newEntriesArr[editIndex] = entry;

				Arrays.sort(newEntriesArr, new Comparator() {
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

				group.setFeedEntries(newEntriesArr);
				value.setStagingUnitTrustFeedGroup(group);
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
