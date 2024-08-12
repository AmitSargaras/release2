/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/PaginateBondListCommand.java,v 1.3 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.digitalLibrary;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class PaginateDigitalLibraryCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ DigitalLibraryForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
																		// the
																		// current
																		// feed
																		// entries
																		// to be
																		// saved
																		// as a
																		// whole
																		// .
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ DigitalLibraryForm.MAPPER, "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IDigitalLibraryGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(DigitalLibraryForm.MAPPER);

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IDigitalLibraryGroup inputGroup = (IDigitalLibraryGroup) inputList.get(1);
			IDigitalLibraryEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");
			IDigitalLibraryGroup group = value.getStagingDigitalLibraryGroup();
			IDigitalLibraryEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setClimsDocCategory(inputEntriesArr[i].getClimsDocCategory());
				// entriesArr[offset + i].setLastUpdatedDate(new Date());
				entriesArr[offset + i].setDigiLibDocCategory(inputEntriesArr[i].getDigiLibDocCategory());
			}

			// Sort the array.
			/*if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IDigitalLibraryEntry entry1 = (IDigitalLibraryEntry) a;
						IDigitalLibraryEntry entry2 = (IDigitalLibraryEntry) b;
						if (Integer.toString(entry1.getRiskGrade()) == null) {
							entry1.setRiskGrade(0);
						}
						if (Integer.toString(entry2.getRiskGrade()) == null) {
							entry2.setRiskGrade(0);
						}
						return entry1.getRiskGrade().compareTo(entry2.getRiskGrade());
					}
				});
			}*/

			group.setFeedEntries(entriesArr);
			value.setStagingDigitalLibraryGroup(group);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			targetOffset = DigitalLibraryMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("digitalLibraryTrxValue", value);
			resultMap.put("request.ITrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(DigitalLibraryForm.MAPPER, value);

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
