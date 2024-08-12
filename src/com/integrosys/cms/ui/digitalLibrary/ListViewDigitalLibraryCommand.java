/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ListViewBondListCommand.java,v 1.1 2003/09/05 09:45:27 btchng Exp $
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
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/05 09:45:27 $ Tag: $Name: $
 */
public class ListViewDigitalLibraryCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ DigitalLibraryForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = ((Integer) map.get(DigitalLibraryForm.MAPPER)).intValue();

			// Session-scoped trx value.
			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");
			IDigitalLibraryGroup group = value.getStagingDigitalLibraryGroup();
			IDigitalLibraryEntry[] entriesArr = group.getFeedEntries();

			targetOffset = DigitalLibraryMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("offset", new Integer(targetOffset));

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
