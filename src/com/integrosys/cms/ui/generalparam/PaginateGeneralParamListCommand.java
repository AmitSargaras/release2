/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/PaginateBondListCommand.java,v 1.3 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.generalparam;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class PaginateGeneralParamListCommand extends AbstractCommand {

	private IGeneralParamProxy generalParamProxy;

	public IGeneralParamProxy getGeneralParamProxy() {
		return generalParamProxy;
	}

	public void setGeneralParamProxy(IGeneralParamProxy generalParamProxy) {
		this.generalParamProxy = generalParamProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of offset (String), length
				// (String) and feed group OB.
				{ GeneralParamListForm.MAPPER, "java.util.List", FORM_SCOPE }, // Consume
																		// the
																		// current
																		// feed
																		// entries
																		// to be
																		// saved
																		// as a
																		// whole
																		// .
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "generalParamGroupTrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce the
																	// update of
																	// form. For
																	// save and
																	// list.
				{ GeneralParamListForm.MAPPER, "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(GeneralParamListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IGeneralParamGroup inputGroup = (IGeneralParamGroup) inputList.get(1);
			IGeneralParamEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IGeneralParamGroupTrxValue value = (IGeneralParamGroupTrxValue) map.get("generalParamGroupTrxValue");
			IGeneralParamGroup group = value.getStagingGeneralParamGroup();
			IGeneralParamEntry[] entriesArr = group.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				entriesArr[offset + i].setParamValue(inputEntriesArr[i].getParamValue());
			}

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IGeneralParamEntry entry1 = (IGeneralParamEntry) a;
						IGeneralParamEntry entry2 = (IGeneralParamEntry) b;
						if (entry1.getParamName() == null) {
							entry1.setParamName("");
						}
						if (entry2.getParamName() == null) {
							entry2.setParamName("");
						}
						return entry1.getParamName().compareTo(entry2.getParamName());
					}
				});
			}

			group.setFeedEntries(entriesArr);
			value.setStagingGeneralParamGroup(group);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			targetOffset = GeneralParamListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("generalParamGroupTrxValue", value);
			resultMap.put("request.ITrxValue", value);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(GeneralParamListForm.MAPPER, value);

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
