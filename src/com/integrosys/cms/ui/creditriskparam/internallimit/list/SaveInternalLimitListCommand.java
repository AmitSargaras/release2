/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class SaveInternalLimitListCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"theILParamTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue",
						SERVICE_SCOPE },
				{ "theILParamFullList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		IInternalLimitParameterTrxValue trxValue = (IInternalLimitParameterTrxValue) map
				.get("theILParamTrxValue");
		List ilParamFullList = (List) map.get("theILParamFullList");
		trxValue.setStagingILPList(ilParamFullList);
		try {
			trxValue = getInternalLimitProxy().makerSaveILP(
					trxContext, trxValue);
			resultMap.put("request.ITrxValue", trxValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

}
