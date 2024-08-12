/**
 * CommonCodeParamEditCommand.java
 *
 * Created on January 29, 2007, 6:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class PrepareEditCCEntryListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.ENTRY_LIST, "java.util.ArrayList", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) map
					.get(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX);
			Integer offset = (Integer) map.get(CommonCodeEntryConstant.OFFSET);
			result.put(CommonCodeEntryConstant.ENTRY_LIST, MaintainCCEntryUtil.getCurrentPageCCEntryList(trxValue,
					offset, false));
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