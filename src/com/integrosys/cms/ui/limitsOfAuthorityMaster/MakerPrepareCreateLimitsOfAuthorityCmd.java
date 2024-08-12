package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trx.ILimitsOfAuthorityMasterTrxValue;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trx.OBLimitsOfAuthorityMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerPrepareCreateLimitsOfAuthorityCmd  extends AbstractCommand implements ICommonEventConstant,
ILimitsOfAuthorityMasterConstant {
	
	public MakerPrepareCreateLimitsOfAuthorityCmd() {
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ TRX_CONTEXT, OBTrxContext.class.getName(), FORM_SCOPE },
			{ SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE }
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ LIMITS_OF_AUTHORITY_TRX_VAL, ILimitsOfAuthorityMasterTrxValue.class.getName(), SERVICE_SCOPE },
			{ SESSION_RANKING_SEQUENCE_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, List.class.getName(), SERVICE_SCOPE }
		};
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		ILimitsOfAuthorityMasterTrxValue trxValue = new OBLimitsOfAuthorityMasterTrxValue();
		resultMap.put(LIMITS_OF_AUTHORITY_TRX_VAL, trxValue);
		if(map.get(SESSION_RANKING_SEQUENCE_LIST) == null) {
			resultMap.put(SESSION_RANKING_SEQUENCE_LIST, LimitsOfAuthorityMasterHelper.populateRankingOfSequence());
		}
		if(map.get(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE) == null) {
			resultMap.put(SESSION_EMP_GRADE_SORTED_RANKING_SEQUENCE, LimitsOfAuthorityMasterHelper.populateRankingOfSequenceMap());
		}
		
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}