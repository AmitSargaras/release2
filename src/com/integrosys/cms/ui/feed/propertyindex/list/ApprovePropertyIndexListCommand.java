/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/propertyindex/list/ApprovePropertyIndexListCommand.java,v 1.9 2005/08/30 09:51:18 hshii Exp $
 */
package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.proxy.propertyindex.IPropertyIndexFeedProxy;
import com.integrosys.cms.app.feed.proxy.propertyindex.PropertyIndexFeedProxyFactory;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/30 09:51:18 $ Tag: $Name: $
 */
public class ApprovePropertyIndexListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IPropertyIndexFeedGroupTrxValue value = (IPropertyIndexFeedGroupTrxValue) map
					.get("propertyIndexFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IPropertyIndexFeedGroup group = value.getPropertyIndexFeedGroup();
			IPropertyIndexFeedGroup stageGroup = value.getStagingPropertyIndexFeedGroup();
			IPropertyIndexFeedEntry[] actualEntries = null;
			IPropertyIndexFeedEntry[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingPropertyIndexFeedGroup(stageGroup);

			IPropertyIndexFeedProxy proxy = PropertyIndexFeedProxyFactory.getProxy();
			value = proxy.checkerApprovePropertyIndexFeedGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private IPropertyIndexFeedEntry[] updateLastUpdateDate(IPropertyIndexFeedEntry[] actualEntries,
			IPropertyIndexFeedEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getPropertyIndexFeedEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IPropertyIndexFeedEntry actual = (IPropertyIndexFeedEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getPropertyIndexFeedEntryRef()));
				if ((actual == null) || (actual.getUnitPrice() != stageEntries[i].getUnitPrice())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}
}
