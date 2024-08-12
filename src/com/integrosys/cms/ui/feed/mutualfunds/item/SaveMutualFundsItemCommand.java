package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class SaveMutualFundsItemCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ MutualFundsItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry", FORM_SCOPE },
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
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

			IMutualFundsFeedEntry entry = (IMutualFundsFeedEntry) map.get(MutualFundsItemForm.MAPPER);
			
			boolean isExists = getMutualFundsFeedProxy().isValidSchemeCode(entry.getSchemeCode());
			
			if(isExists){
				exceptionMap.put("schemeCodeError", new ActionMessage("error.string.schemecode.exist"));
				IMutualFundsFeedGroupTrxValue value = null;
				resultMap.put("request.ITrxValue", value);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}

			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");
			IMutualFundsFeedGroup group = value.getStagingMutualFundsFeedGroup();
			IMutualFundsFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IMutualFundsFeedEntry[0];
			}

			if (exceptionMap.isEmpty()) {
				entry.setMutualFundsFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setMutualFundsFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);

				IMutualFundsFeedEntry[] newEntriesArr = new IMutualFundsFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				// Sort the array.

				Arrays.sort(newEntriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IMutualFundsFeedEntry entry1 = (IMutualFundsFeedEntry) a;
						IMutualFundsFeedEntry entry2 = (IMutualFundsFeedEntry) b;
						if (entry1.getSchemeName() == null) {
							entry1.setSchemeName("");
						}
						if (entry2.getSchemeName() == null) {
							entry2.setSchemeName("");
						}
						return entry1.getSchemeName().compareTo(entry2.getSchemeName());
					}
				});

				group.setFeedEntries(newEntriesArr);

				value.setStagingMutualFundsFeedGroup(group);
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
