package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class ApproveTatDocCommand extends TatDocCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", REQUEST_SCOPE },
				{ "tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		String trxId = (String) map.get("trxId");

		ITatDocProxy proxy = getTatDocProxy();

		ITatDocTrxValue trxValue = null;
		trxValue = proxy.getTatDocTrxValueByTrxID(trxId);
		trxValue = proxy.checkerApproveTatDoc(trxContext, trxValue);

		resultMap.put("request.ITrxValue", trxValue);
		resultMap.put("tatDocTrxValue", trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
