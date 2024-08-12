package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException;
import com.integrosys.cms.app.feed.proxy.propertyindex.IPropertyIndexFeedProxy;
import com.integrosys.cms.app.feed.proxy.propertyindex.PropertyIndexFeedProxyFactory;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.propertyindex.OBPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;

/**
 * This class implements command
 */
public class ReadPropertyIndexListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ PropertyIndexListForm.MAPPER,
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", FORM_SCOPE },
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
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE }, // Produce
																															// the
																															// offset
																															// .
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To populate
																	// the form.
				{ PropertyIndexListForm.MAPPER,
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", FORM_SCOPE } });
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

		IPropertyIndexFeedGroupTrxValue trxValue = null;

		IPropertyIndexFeedGroupTrxValue value = (IPropertyIndexFeedGroupTrxValue) map.get(PropertyIndexListForm.MAPPER);
		IPropertyIndexFeedGroup group = value.getPropertyIndexFeedGroup();
		String trxId = value.getTransactionID();
		String event = (String) map.get("event");
		try {
			DefaultLogger.debug(this, "the subtype = " + group.getSubType());

			DefaultLogger.debug(this, "the trx id = " + trxId);

			IPropertyIndexFeedProxy feedProxy = PropertyIndexFeedProxyFactory.getProxy();

			if ((trxId == null) || trxId.equals("")) {
				DefaultLogger.debug(this, "getting by subtype.");
				trxValue = feedProxy.getPropertyIndexFeedGroup(group.getSubType());
			}
			else {
				DefaultLogger.debug(this, "getting by trx id.");
				trxValue = feedProxy.getPropertyIndexFeedGroupByTrxID(Long.parseLong(trxId));
			}

			DefaultLogger.debug(this, "the subtype = " + group.getSubType());
			DefaultLogger.debug(this, "after getting property index feed group from proxy.");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingPropertyIndexFeedGroup() == null) {
				trxValue.setStagingPropertyIndexFeedGroup((IPropertyIndexFeedGroup) CommonUtil.deepClone(trxValue
						.getPropertyIndexFeedGroup()));
			}

			if (trxValue.getPropertyIndexFeedGroup().getFeedEntries() == null) {
				trxValue.getPropertyIndexFeedGroup().setFeedEntries(new IPropertyIndexFeedEntry[0]);
			}

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND)
					|| PropertyIndexListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingPropertyIndexFeedGroup((IPropertyIndexFeedGroup) CommonUtil.deepClone(trxValue
						.getPropertyIndexFeedGroup()));
			}

			// Sort the staging entries.
			IPropertyIndexFeedEntry[] entriesArr = trxValue.getStagingPropertyIndexFeedGroup().getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IPropertyIndexFeedEntry[0];
				trxValue.getStagingPropertyIndexFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
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
		}
		catch (PropertyIndexFeedGroupException e) {
			if (e.getErrorCode().equals(IPropertyIndexFeedProxy.NO_FEED_GROUP)) {
				DefaultLogger.error(this, "no feed group found.");
				if (trxValue == null) {
					trxValue = new OBPropertyIndexFeedGroupTrxValue();
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

		resultMap.put("propertyIndexFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(PropertyIndexListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
