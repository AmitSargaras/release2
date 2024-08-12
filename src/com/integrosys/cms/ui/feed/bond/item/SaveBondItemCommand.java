package com.integrosys.cms.ui.feed.bond.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.bond.BondCommand;

/**
 * This class implements command
 */
public class SaveBondItemCommand extends BondCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ BondItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", FORM_SCOPE },
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
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

			IBondFeedEntry entry = (IBondFeedEntry) map.get(BondItemForm.MAPPER);

			IBondFeedGroupTrxValue value = (IBondFeedGroupTrxValue) map.get("bondFeedGroupTrxValue");
			IBondFeedGroup group = value.getStagingBondFeedGroup();
			IBondFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IBondFeedEntry[0];
			}
			
			boolean isExists = getBondFeedProxy().isExistBondCode(entry.getBondCode());
			
			if(isExists){
				exceptionMap.put("bondCode", new ActionMessage("error.string.bondcode.exist"));
				IBondFeedGroupTrxValue trxValue = null;
				resultMap.put("request.ITrxValue", trxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			boolean isFound = false;
			String name = entry.getName();
			if (StringUtils.isNotBlank(name)) {
				for (int i = 0; (i < entriesArr.length) && !isFound; i++) {
					if (entriesArr[i].getName().equals(name)) {
						exceptionMap.put("name", new ActionMessage(FeedConstants.ERROR_DUPLICATE, "Bond name"));
						isFound = true;
					}
				}
			}

			// if (ric != null && !"".equals(ric.trim())) {
			// for (int i = 0; i < entriesArr.length; i++) {
			// if (entriesArr[i].getRic().equals(ric)) {
			// exceptionMap.put("ric", new
			// ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
			// isFound = true;
			// break;
			// }
			// }
			//
			// if (!isFound) {
			// Validate that the ric must not be currently existing in the price
			// feed table.
			// IBondFeedProxy proxy = BondFeedProxyFactory.getProxy();
			// if (proxy.getBondFeedEntryByRic(ric) != null) {
			// exceptionMap.put("ric", new
			// ActionMessage(FeedConstants.ERROR_DUPLICATE, "RIC value"));
			// }
			// }
			// }

			if (exceptionMap.isEmpty()) {
				entry.setBondFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setBondFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);

				IBondFeedEntry[] newEntriesArr = new IBondFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Sort the array.

				Arrays.sort(newEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IBondFeedEntry entry1 = (IBondFeedEntry) a;
						IBondFeedEntry entry2 = (IBondFeedEntry) b;
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

				value.setStagingBondFeedGroup(group);
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
