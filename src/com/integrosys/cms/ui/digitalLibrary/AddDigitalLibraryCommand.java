/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/AddBondListCommand.java,v 1.4 2004/03/29 10:45:20 sathish Exp $
 */
package com.integrosys.cms.ui.digitalLibrary;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;

/**
 * @author $Author: sathish $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/03/29 10:45:20 $ Tag: $Name%
 */
public class AddDigitalLibraryCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ DigitalLibraryForm.MAPPER, "com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
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
			IDigitalLibraryGroup inputFeedGroup = (IDigitalLibraryGroup) map.get(DigitalLibraryForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IDigitalLibraryEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");
			IDigitalLibraryGroup group = value.getStagingDigitalLibraryGroup();
			IDigitalLibraryEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if ((inputEntriesArr[i].getClimsDocCategory()).equalsIgnoreCase(entriesArr[j].getClimsDocCategory())) {
							entriesArr[j].setDigiLibDocCategory(inputEntriesArr[i].getDigiLibDocCategory());
//							entriesArr[j].setLevel2(inputEntriesArr[i].getLevel2());
//							entriesArr[j].setLevel3(inputEntriesArr[i].getLevel3());
//							entriesArr[j].setLevel4(inputEntriesArr[i].getLevel4());
						}
					}

				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingDigitalLibraryGroup(group);

			// resultMap.put("bondFeedGroupTrxValue", value);
			// resultMap.put(BondListForm.MAPPER, value);

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
