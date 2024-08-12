package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */

public class PaginateCCEntryListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ CommonCodeEntryConstant.CURRENT_OFFSET_NUMBER, "java.lang.String", REQUEST_SCOPE },
				{ CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE },
				{ "ERROR_ENTRY_MAP", "java.util.HashMap", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			HashMap errorMap = (HashMap) map.get("ERROR_ENTRY_MAP");
			if ((errorMap == null) || (errorMap.size() == 0)) {
				Integer offset = (Integer) map.get(CommonCodeEntryConstant.OFFSET);
				DefaultLogger.debug(this, "befor paging: " + offset.intValue());
				offset = new Integer((String) map.get(CommonCodeEntryConstant.CURRENT_OFFSET_NUMBER));
				DefaultLogger.debug(this, "offset after paging: " + offset.intValue());
				result.put(CommonCodeEntryConstant.OFFSET, offset);
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