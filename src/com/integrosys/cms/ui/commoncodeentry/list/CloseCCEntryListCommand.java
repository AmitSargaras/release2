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

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.CommonCodeEntriesException;
import com.integrosys.cms.app.commoncodeentry.proxy.CommonCodeEntriesProxyManagerFactory;
import com.integrosys.cms.app.commoncodeentry.proxy.ICommonCodeEntriesProxy;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CloseCCEntryListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
						"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICommonCodeEntriesTrxValue trxValue = (ICommonCodeEntriesTrxValue) map
					.get(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX);
			OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

			ICommonCodeEntriesProxy proxy = CommonCodeEntriesProxyManagerFactory.getICommonCodeEntriesProxy();
			ICommonCodeEntriesTrxValue value = proxy.makerCancelUpdate(trxContext, trxValue);

			resultMap.put("request.ITrxValue", value);
		}
		catch (CommonCodeEntriesException e) {
			throw new CommandProcessingException(e.getMessage());
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
