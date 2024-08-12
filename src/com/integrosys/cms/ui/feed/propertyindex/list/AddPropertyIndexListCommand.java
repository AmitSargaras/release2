/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/propertyindex/list/AddPropertyIndexListCommand.java,v 1.2 2003/09/01 09:28:23 btchng Exp $
 */
package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/01 09:28:23 $ Tag: $Name%
 */
public class AddPropertyIndexListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ PropertyIndexListForm.MAPPER,
						"com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Consume the
																	// current
																	// feed
																	// entries
																	// to be
																	// saved as
																	// a whole.
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IPropertyIndexFeedGroup inputFeedGroup = (IPropertyIndexFeedGroup) map.get(PropertyIndexListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IPropertyIndexFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IPropertyIndexFeedGroupTrxValue value = (IPropertyIndexFeedGroupTrxValue) map
					.get("propertyIndexFeedGroupTrxValue");
			IPropertyIndexFeedGroup group = value.getStagingPropertyIndexFeedGroup();
			IPropertyIndexFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					entriesArr[offset + i].setUnitPrice(inputEntriesArr[i].getUnitPrice());
				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingPropertyIndexFeedGroup(group);

			// resultMap.put("propertyIndexFeedGroupTrxValue", value);
			// resultMap.put(PropertyIndexListForm.MAPPER, value);

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
