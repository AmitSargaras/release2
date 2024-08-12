package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.proxy.propertyindex.IPropertyIndexFeedProxy;
import com.integrosys.cms.app.feed.proxy.propertyindex.PropertyIndexFeedProxyFactory;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class implements command
 */
public class SubmitPropertyIndexListCommand extends AbstractCommand {

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
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", REQUEST_SCOPE } };
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

			List inputList = (List) map.get(PropertyIndexListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(1));
			// Element at index 2 is the target offset which is not required
			// here.
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

			// Update using the input unit prices.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					entriesArr[offset + i].setUnitPrice(inputEntriesArr[i].getUnitPrice());
					// entriesArr[offset + i].setLastUpdatedDate(new Date());
				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingPropertyIndexFeedGroup(group);

			IPropertyIndexFeedProxy proxy = PropertyIndexFeedProxyFactory.getProxy();

			IPropertyIndexFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = proxy.makerSubmitRejectedPropertyIndexFeedGroup(trxContext, value);
			}
			else {
				resultValue = proxy.makerSubmitPropertyIndexFeedGroup(trxContext, value, value
						.getStagingPropertyIndexFeedGroup());
			}

			resultMap.put("request.ITrxValue", resultValue);

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
