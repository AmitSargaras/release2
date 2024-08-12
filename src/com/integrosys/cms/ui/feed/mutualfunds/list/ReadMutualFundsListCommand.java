package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 * This class implements command
 */
public class ReadMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
		{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE }, // Produce
																													// the
																													// offset
																													// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ MutualFundsListForm.MAPPER, "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", FORM_SCOPE },
				{ "schemeTypeList", "java.util.List", SERVICE_SCOPE }
			});
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

		IMutualFundsFeedGroupTrxValue trxValue = null;
		try {
			String event = (String) map.get("event");
			
			trxValue = getMutualFundsFeedProxy().getMutualFundsFeedGroup();
			
			IMutualFundsFeedGroup mutualFundsFeedGroup = trxValue.getMutualFundsFeedGroup();
			
			DefaultLogger.debug(this, "after getting mutual funds feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingMutualFundsFeedGroup() == null) {
				trxValue.setStagingMutualFundsFeedGroup((IMutualFundsFeedGroup) CommonUtil.deepClone(trxValue.getMutualFundsFeedGroup()));
			}

			if (trxValue.getMutualFundsFeedGroup().getFeedEntries() == null) {
				trxValue.getMutualFundsFeedGroup().setFeedEntries(new IMutualFundsFeedEntry[0]);
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND) || MutualFundsListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingMutualFundsFeedGroup((IMutualFundsFeedGroup) CommonUtil.deepClone(trxValue.getMutualFundsFeedGroup()));
			}

			// Sort the staging entries.
			IMutualFundsFeedEntry[] entriesArr = trxValue.getStagingMutualFundsFeedGroup().getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IMutualFundsFeedEntry[0];
				trxValue.getStagingMutualFundsFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
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
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("mutualFundsFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(MutualFundsListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
	
}
