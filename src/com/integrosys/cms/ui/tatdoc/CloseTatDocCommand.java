package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatdoc.proxy.ITatDocProxy;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public class CloseTatDocCommand extends TatDocCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, };
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

		ITatDocTrxValue trxValue = (ITatDocTrxValue) map.get("tatDocTrxValue");
		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

		ITatDocProxy proxy = getTatDocProxy();
		if (ICMSConstant.STATE_ND.equals(trxValue.getFromState())
				|| ICMSConstant.STATE_PENDING_CREATE.equals(trxValue.getFromState())) {
			trxValue = proxy.makerCloseTatDoc(trxContext, trxValue);
		}
		else if (ICMSConstant.STATE_PENDING_UPDATE.equals(trxValue.getFromState())
				|| ICMSConstant.STATE_ACTIVE.equals(trxValue.getFromState())) {
			trxValue = proxy.makerCloseUpdateTatDoc(trxContext, trxValue);
		}
		else if (ICMSConstant.STATE_DRAFT.equals(trxValue.getFromState())
				|| ICMSConstant.STATE_REJECTED.equals(trxValue.getFromState())) {
			if (trxValue.getReferenceID() == null) {
				trxValue = proxy.makerCloseTatDoc(trxContext, trxValue);
			}
			else {
				trxValue = proxy.makerCloseUpdateTatDoc(trxContext, trxValue);
			}
		}

		resultMap.put("request.ITrxValue", trxValue);
		resultMap.put("tatDocTrxValue", trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
