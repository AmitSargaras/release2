package com.integrosys.cms.ui.feed.unittrust.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * This class implements command
 */
public class PrepareUpdateUnitTrustItemCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "isEdit", "java.lang.String", SERVICE_SCOPE },
				{ "editIndex", "java.lang.Integer", SERVICE_SCOPE }, 
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, 
				{ "unitTrustFeedGroupTrxValue",
					"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
		};
	}

	//
	//
	 public String[][] getResultDescriptor() {
		 return new String[][]{
				{UnitTrustItemForm.MAPPER,"com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedEntry",FORM_SCOPE},
				{ "oldRIC", "java.lang.String", SERVICE_SCOPE },
		 };
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
			String isEdit = (String) map.get("isEdit");
			int editIndex = ((Integer) map.get("editIndex")).intValue();
			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();
			IUnitTrustFeedEntry entry = entriesArr[editIndex];

			resultMap.put(UnitTrustItemForm.MAPPER, entry);
			resultMap.put("oldRIC", entry.getRic());
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
