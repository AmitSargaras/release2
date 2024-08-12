package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class SaveTatDocCommand extends TatDocCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { TatDocForm.MAPPER, "com.integrosys.cms.app.tatdoc.bus.OBTatDoc", FORM_SCOPE },
				{ "tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
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

		ITatDoc tatDoc = (ITatDoc) map.get(TatDocForm.MAPPER);
		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		ITatDocTrxValue trxValue = (ITatDocTrxValue) map.get("tatDocTrxValue");

		DefaultLogger.debug(this, "trxContext = " + trxContext);
		ITatDocProxy proxy = getTatDocProxy();
		trxValue = proxy.makerSaveTatDoc(trxContext, trxValue, tatDoc);

		resultMap.put("request.ITrxValue", trxValue);
		resultMap.put("tatDocTrxValue", trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
