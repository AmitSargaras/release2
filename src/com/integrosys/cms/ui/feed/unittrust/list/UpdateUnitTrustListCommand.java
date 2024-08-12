/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/unittrust/list/UpdateUnitTrustListCommand.java,v 1.3 2003/09/01 04:33:28 btchng Exp $
 */
package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/01 04:33:28 $ Tag: $Name%
 */
public class UpdateUnitTrustListCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ UnitTrustListForm.MAPPER, "com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup", FORM_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE }, 
				{ "editIndex", "java.lang.String", REQUEST_SCOPE }, 
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Consume the
																	// current
																	// feed
																	// entries
																	// to be
																	// saved as
																	// a whole.
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "isEdit", "java.lang.String", SERVICE_SCOPE }, 
				{ "editIndex", "java.lang.Integer", SERVICE_SCOPE }, 
		};
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
//			IUnitTrustFeedGroup inputFeedGroup = (IUnitTrustFeedGroup) map.get(UnitTrustListForm.MAPPER);
//			IUnitTrustFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			int offset = ((Integer) map.get("offset")).intValue();
			String isEdit = (String) map.get("isEdit");
			int editIndex = Integer.parseInt((String) map.get("editIndex"));

			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();
			IUnitTrustFeedEntry entry = entriesArr[offset];

			resultMap.put("isEdit", isEdit);
			resultMap.put("editIndex", new Integer(editIndex));
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
