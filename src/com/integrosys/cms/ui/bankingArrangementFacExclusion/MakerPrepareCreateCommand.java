package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.IBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.bankingArrangementFacExclusion.trx.OBBankingArrangementFacExclusionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerPrepareCreateCommand  extends AbstractCommand implements ICommonEventConstant,
IBankingArrangementFacExclusionConstant {
	
	public MakerPrepareCreateCommand() {
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{ "facNameList", List.class.getName(), SERVICE_SCOPE },
			{ "isNew", String.class.getName(), REQUEST_SCOPE }
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ POJO_TRX, IBankingArrangementFacExclusionTrxValue.class.getName(), SERVICE_SCOPE },
			{ "facNameList", List.class.getName(), SERVICE_SCOPE },
			{ "isNew", String.class.getName(), REQUEST_SCOPE }
		};
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		IBankingArrangementFacExclusionTrxValue trxValue = new OBBankingArrangementFacExclusionTrxValue();

		String isNew = (String) map.get("isNew");
		
		if("Y".equals(isNew)) {
			resultMap.put("facNameList", null);
		}
		
		resultMap.put("isNew", isNew);
		resultMap.put("bankingArrFacExclTrxValue", trxValue);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}