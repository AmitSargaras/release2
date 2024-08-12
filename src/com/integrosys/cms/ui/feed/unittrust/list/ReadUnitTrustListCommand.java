package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.UnitTrustFeedGroupException;
import com.integrosys.cms.app.feed.proxy.unittrust.IUnitTrustFeedProxy;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.OBUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * This class implements command
 */
public class ReadUnitTrustListCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ UnitTrustListForm.MAPPER, "com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue",
						FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.util.Integer", SERVICE_SCOPE },
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
		// DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IUnitTrustFeedGroupTrxValue trxValue = null;

		IUnitTrustFeedGroupTrxValue inputValue = (IUnitTrustFeedGroupTrxValue) map.get(UnitTrustListForm.MAPPER);

		IUnitTrustFeedGroup group = inputValue.getUnitTrustFeedGroup();

		String trxId = inputValue.getTransactionID();
		String event = (String) map.get("event");
		try {

			DefaultLogger.debug(this, "the subtype = " + group.getSubType());

			DefaultLogger.debug(this, "the trx id = " + trxId);

			IUnitTrustFeedProxy feedProxy = getUnitTrustFeedProxy();

			if ((trxId == null) || trxId.equals("")) {
				trxValue = feedProxy.getUnitTrustFeedGroup(group.getSubType());
			}
			else {
				trxValue = feedProxy.getUnitTrustFeedGroupByTrxID(Long.parseLong(trxId));
			}

			DefaultLogger.debug(this, "the subtype = " + group.getSubType());
			DefaultLogger.debug(this, "after getting unit trust feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingUnitTrustFeedGroup() == null) {
				trxValue.setStagingUnitTrustFeedGroup((IUnitTrustFeedGroup) CommonUtil.deepClone(trxValue
						.getUnitTrustFeedGroup()));
			}

			if (trxValue.getUnitTrustFeedGroup().getFeedEntries() == null) {
				trxValue.getUnitTrustFeedGroup().setFeedEntries(new IUnitTrustFeedEntry[0]);
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND)
					|| UnitTrustListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingUnitTrustFeedGroup((IUnitTrustFeedGroup) CommonUtil.deepClone(trxValue
						.getUnitTrustFeedGroup()));
			}

			// Sort the staging entries.
			IUnitTrustFeedEntry[] entriesArr = trxValue.getStagingUnitTrustFeedGroup().getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IUnitTrustFeedEntry[0];
				trxValue.getStagingUnitTrustFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
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
		}
		catch (UnitTrustFeedGroupException e) {
			if (e.getErrorCode().equals(IUnitTrustFeedProxy.NO_FEED_GROUP)) {
				DefaultLogger.error(this, "no feed group found.");
				if (trxValue == null) {
					trxValue = new OBUnitTrustFeedGroupTrxValue();
				}
				exceptionMap.put("countryCode", new ActionMessage(FeedConstants.INFO_MISSING_SETUP_DATA));
			}
			else {
				DefaultLogger.error(this, "Exception caught in doExecute()", e);
				exceptionMap.put("application.exception", e);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("unitTrustFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(UnitTrustListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
