/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryAddCommand.java
 *
 * Created on February 2, 2007, 10:48 AM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.item;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.ui.commoncodeentry.list.MaintainCCEntryUtil;

public class ConfirmAddCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "aCommonCodeEntryObj", "com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry", FORM_SCOPE },
				{ "commonCodeType", "com.integrosys.cms.app.commoncode.bus.ICommonCodeType", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBCommonCodeEntry entry = (OBCommonCodeEntry) map.get("aCommonCodeEntryObj");
		ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) map
				.get(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX);
		if (MaintainCCEntryUtil.isDuplicateEntry(entry, trxValue)) {
			exceptionMap.put("entryCode", new ActionMessage("error.entries.duplicate"));
		}
		else {
			ArrayList allEntryList = (ArrayList) trxValue.getStagingCommonCodeEntries().getEntries();
			ICommonCodeType ccType = (ICommonCodeType) map.get("commonCodeType");
			entry.setCategoryCode(ccType.getCommonCategoryCode());
			entry.setCategoryCodeId(ccType.getCommonCategoryId());
			allEntryList.add(entry);
			MaintainCCEntryUtil.sortCCEntryList(trxValue);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
