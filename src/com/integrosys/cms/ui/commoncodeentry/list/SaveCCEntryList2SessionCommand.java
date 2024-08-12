/**
 * CommonCodeEntryConfrimEditCommand.java
 *
 * Created on January 31, 2007, 1:50 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;

public class SaveCCEntryList2SessionCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ CommonCodeEntryConstant.ENTRY_LIST, "java.util.ArrayList", FORM_SCOPE },
				{ CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		try {
			Integer offset = (Integer) map.get(CommonCodeEntryConstant.OFFSET);
			ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) map
					.get(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX);

			ArrayList entryList = (ArrayList) map.get(CommonCodeEntryConstant.ENTRY_LIST);
			HashMap errorMap = MaintainCCEntryUtil.getDuplicateEntryMap(entryList, offset, trxValue);
			if (errorMap.size() > 0) {
				for (int index = 0; index < entryList.size(); index++) {
					OBCommonCodeEntry tmpEntry = (OBCommonCodeEntry) entryList.get(index);
					String entryCode = tmpEntry.getEntryCode();
					if(errorMap.get(entryCode)!=null){
						exceptionMap.put("entryCode_"+index, new ActionMessage("error.entries.duplicate"));
						DefaultLogger.debug(this,"Put in exception Map:entryCode_"+index);
					}
				}
				exceptionMap.put("stop",new ActionMessage ("error.entries.duplicate"));
				DefaultLogger.debug(this, "No of duplicated entries:" + errorMap.size());
			}
			else {
				MaintainCCEntryUtil.updateCurrentPage2EntryList(trxValue, offset, entryList);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this,e.getMessage(),e);
			throw new CommandProcessingException(e.getMessage());
		}
		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}